package sha.com.ind.labapp.other.fingerprint.keys;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

import sha.com.ind.labapp.BuildConfig;
import sha.com.ind.labapp.utils.Constants;
import sha.com.ind.labapp.utils.SharedPrefsUtils;

/**
 * Created by sreepolavarapu on 9/02/17.
 */

public final class KeyTools {

    private static final String TAG = KeyTools.class.getSimpleName();
    public static final String KEY_ALIAS_PIN = BuildConfig.APPLICATION_ID+".KEY_ALIAS_PIN";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";

    private static final String ALGORITHM = "RSA";
    private static final String RSA_MODE =  "RSA/ECB/PKCS1Padding";
    private static final String AES_MODE = "AES/ECB/PKCS7Padding";

    private KeyStore keyStore;
    private Context mContext;

    public static KeyTools newInstance(Context context) throws KeyToolsException{
        return new KeyTools(context);
    }

    private KeyTools(Context context) throws KeyToolsException {
        mContext = context;
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);
        } catch (Exception e) {
            throw new KeyToolsException("Error initializing keystore: ", e);
        }

    }

    public void generateRSAKeyPair(String keyAlias) throws KeyToolsException{
        // Generate the RSA key pairs
        try {
            if (!keyStore.containsAlias(keyAlias)) {
                // Generate a key pair for encryption
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 30);
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(mContext)
                        .setAlias(keyAlias)
                        .setSubject(new X500Principal("CN=" + keyAlias))
                        .setSerialNumber(BigInteger.TEN)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM, ANDROID_KEY_STORE);
                kpg.initialize(spec);
                kpg.generateKeyPair();
            }
        }catch (Exception ex)
        {
            throw new KeyToolsException("Error creating cipher for " + keyAlias, ex);
        }
    }

    private byte[] rsaEncrypt(String keyAlias, byte[] secret) throws KeyToolsException{
        try {

            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyAlias, null);
            // Encrypt the text
            Cipher inputCipher = Cipher.getInstance(RSA_MODE, "AndroidKeyStoreBCWorkaround");
            inputCipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.getCertificate().getPublicKey());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, inputCipher);
            cipherOutputStream.write(secret);
            cipherOutputStream.close();

            return  outputStream.toByteArray();

        }catch (Exception ex)
        {
            throw new KeyToolsException("Unable to Encrypt AES key for  " + keyAlias, ex);
        }
    }

    private  byte[]  rsaDecrypt(String keyAlias, byte[] encrypted) throws KeyToolsException {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(keyAlias, null);
            Cipher output = Cipher.getInstance(RSA_MODE, "AndroidKeyStoreBCWorkaround");
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());
            CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(encrypted), output);
            ArrayList<Byte> values = new ArrayList<>();
            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte)nextByte);
            }

            byte[] bytes = new byte[values.size()];
            for(int i = 0; i < bytes.length; i++) {
                bytes[i] = values.get(i).byteValue();
            }
            return bytes;
        }catch (Exception ex)
        {
            throw new KeyToolsException("Unable to decrypt AES key for  " + keyAlias, ex);
        }
    }

    public void generateAndStoreAESKey(String keyAlias) throws KeyToolsException
    {
        String encryptedKeyB64 = SharedPrefsUtils.getStringPreference(mContext, Constants.PREF_AES_ENCRYPTED_KEY_FOR_PIN);
        if (encryptedKeyB64 == null) {

            try {
                byte[] key = new byte[16];
                SecureRandom secureRandom = new SecureRandom();
                secureRandom.nextBytes(key);
                byte[] encryptedKey = rsaEncrypt(keyAlias, key);
                encryptedKeyB64 = Base64.encodeToString(encryptedKey, Base64.DEFAULT);
                SharedPrefsUtils.setStringPreference(mContext,Constants.PREF_AES_ENCRYPTED_KEY_FOR_PIN, encryptedKeyB64);
            }catch (KeyToolsException ex)
            {
                throw new KeyToolsException("Error creating AES key for " + keyAlias, ex);
            }
        }
    }

    private Key getSecretKey(String keyAlias) throws KeyToolsException{

        String enryptedKeyB64 = SharedPrefsUtils.getStringPreference(mContext, Constants.PREF_AES_ENCRYPTED_KEY_FOR_PIN);
        // need to check null, omitted here
        byte[] encryptedKey = Base64.decode(enryptedKeyB64, Base64.DEFAULT);
        byte[] key = rsaDecrypt(keyAlias, encryptedKey);
        return new SecretKeySpec(key, "AES");

    }

    public String encrypt( String keyAlias, byte[] input) throws KeyToolsException{

        try {
            Cipher c = Cipher.getInstance(AES_MODE, "BC");
            c.init(Cipher.ENCRYPT_MODE, getSecretKey(keyAlias));
            byte[] encodedBytes = c.doFinal(input);
            return  Base64.encodeToString(encodedBytes, Base64.DEFAULT);
        }catch (Exception ex)
        {
            throw new KeyToolsException("Error creating AES key for " + keyAlias, ex);
        }
    }

    public byte[] decrypt(String keyAlias, byte[] encrypted) throws KeyToolsException{
        try {
            Cipher c = Cipher.getInstance(AES_MODE, "BC");
            c.init(Cipher.DECRYPT_MODE, getSecretKey(keyAlias));
            return c.doFinal(encrypted);
        }catch (Exception ex)
        {
            throw new KeyToolsException("Error creating AES key for " + keyAlias, ex);
        }
    }
}
