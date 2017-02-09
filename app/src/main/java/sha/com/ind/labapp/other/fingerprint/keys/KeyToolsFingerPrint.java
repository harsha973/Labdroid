package sha.com.ind.labapp.other.fingerprint.keys;
 
import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Collections;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import sha.com.ind.labapp.BuildConfig;
import sha.com.ind.labapp.utils.Constants;
import sha.com.ind.labapp.utils.SharedPrefsUtils;

@RequiresApi(Build.VERSION_CODES.M)
/**
 * This is helper class to generate keys for Fingerprint.
 * Fingerprint authentication process involves generating AES symmetric key which is then stored in keystore.
 * This key is heart of the process and is used to encrypt and decrypt our plain message.
 * We then generate a cipher with the key, but as an additional step for security we are making sure
 * that the Crypto object needs to be authenticated.
 *
 * After all these steps, Cipher is allowed to decrypt our secret message.
 *
 * It requires at least Build version grater than {@link Build.android.os.Build.VERSION_CODES.M}.
 *
 * References -
 * https://blog.stylingandroid.com/user-identity-part-2/    -   Main guidance
 * https://github.com/mcnerthney/android-fingerprint-decrypt    -   Mainly used for decryption techniques
 * https://github.com/googlesamples/android-FingerprintDialog   -   Android official sample, covers lots of negative cases
 */
public final class KeyToolsFingerPrint {

    private static final String PROVIDER_NAME = "AndroidKeyStore";
    private static final String USER_AUTH_KEY_NAME = BuildConfig.APPLICATION_ID + ".KEY_USER_AUTH";
 
    private static final String ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    private static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    private static final String PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    private static final String TRANSFORMATION = ALGORITHM + "/" + BLOCK_MODE + "/" + PADDING;

    private Context mContext;
    private final KeyStore keyStore;
    private final Map<String, KeySpecGenerator> generators;
 
    public static KeyToolsFingerPrint newInstance(Context context) throws KeyToolsException {
        KeyStore keyStore;
        try { 
            keyStore = KeyStore.getInstance(PROVIDER_NAME);
            keyStore.load(null);
        } catch (Exception e) {
            throw new KeyToolsException("Error initializing keystore: ", e);
        } 
        Map<String, KeySpecGenerator> generators = new ArrayMap<>();
        generators.put(USER_AUTH_KEY_NAME, new UserAuthKeySpecGenerator(BLOCK_MODE, PADDING));
        return new KeyToolsFingerPrint(context, keyStore, Collections.unmodifiableMap(generators));
    } 
 
    private KeyToolsFingerPrint(Context context, KeyStore keyStore, Map<String, KeySpecGenerator> generators) {
        this.mContext = context;
        this.keyStore = keyStore;
        this.generators = generators;
    }

    /**
     * Gets the Cipher object for encryption. Creates one if needed.
     * @return  Cipher object with ENCRYPTION_MODE
     * @throws KeyToolsException    Of course, it throws if it finds any difficulties.
     */
    public Cipher getUserAuthEncryptCipher() throws KeyToolsException {
        try { 
            return getCipher(USER_AUTH_KEY_NAME, Cipher.ENCRYPT_MODE);
        } catch (Exception e) {
            throw new KeyToolsException("Error creating cipher", e);
        } 
    }

    /**
     * Gets the Cipher object for encryption. Creates one if needed.
     * @return  Cipher object with DECRYPTION_MODE
     * @throws KeyToolsException    Of course, it throws if it finds any difficulties.
     */
    public Cipher getUserAuthDecryptCipher() throws KeyToolsException {
        try {
            return getCipher(USER_AUTH_KEY_NAME, Cipher.DECRYPT_MODE);
        } catch (Exception e) {
            throw new KeyToolsException("Error creating cipher", e);
        }
    }

    /**
     * Get the cipher for the keyname and mode
     * @param keyName   Name of the key
     * @param mode  Mode - one of Encryption or Decryption
     * @return  Cipher related to that
     * @throws KeyToolsException
     * @throws UserNotAuthenticatedException
     * @throws IllegalStateException
     */
    private Cipher getCipher(String keyName, int mode) throws KeyToolsException, UserNotAuthenticatedException, IllegalStateException {
        try { 
            return getCypher(keyName, mode,  true);
        } catch (UserNotAuthenticatedException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new KeyToolsException("Error creating cipher for " + keyName, e);
        } 
    }

    /**
     *
     * Get the cipher for the key name and mode
     * @param keyName   Name of the key
     * @param mode  Mode - one of Encryption or Decryption
     * @param retry In case of failure - Deletes old key and does recursion again with no retries
     * @return  Cipher
     * @throws KeyToolsException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     */
    private Cipher getCypher(String keyName, int mode, boolean retry) throws KeyToolsException, NoSuchAlgorithmException, KeyStoreException,
            UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        try { 
            SecretKey secretKey = getKey(keyName);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            //  Check for Mode
            if (mode == Cipher.ENCRYPT_MODE) {
                cipher.init(mode, secretKey);
            }else
            {
                //  Get the IV params of the encrypted Cipher. Or else it will break saying it needs ivParams to decrypt
                byte[] iv;
                iv = Base64.decode(SharedPrefsUtils.getStringPreference(mContext, Constants.PREF_PASSWORD_IV), Base64.DEFAULT);
                IvParameterSpec ivParams = new IvParameterSpec(iv);
                cipher.init(mode, secretKey, ivParams);
            }
            return cipher;
        } catch (KeyPermanentlyInvalidatedException e) {
            //  Here is deleting and retrying code
            if (retry) {
                keyStore.deleteEntry(keyName);
                return getCypher(keyName, mode, false);
            } else { 
                throw e;
            } 
        } 
    }

    /**
     *
     * @param keyName
     * @return
     * @throws KeyStoreException
     * @throws KeyToolsException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     */
    private SecretKey getKey(String keyName) throws KeyStoreException, KeyToolsException, UnrecoverableKeyException, NoSuchAlgorithmException {
        if (!keyStore.isKeyEntry(keyName)) {
            createKey(keyName);
        } 
       return (SecretKey) keyStore.getKey(keyName, null);
    } 
 
    private void createKey(String keyName) throws KeyToolsException, IllegalStateException {
        try { 
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM, PROVIDER_NAME);
            KeyGenParameterSpec spec = getKeyGenParameterSpec(keyName);
            keyGenerator.init(spec);
            keyGenerator.generateKey();
        } catch (InvalidAlgorithmParameterException e) {
            if (e.getCause() instanceof IllegalStateException) {
                throw (IllegalStateException) e.getCause();
            } 
            throw new KeyToolsException("Error creating key for " + keyName, e);
        } catch (Exception e) {
            throw new KeyToolsException("Error creating key for " + keyName, e);
        } 
    } 
 
    private KeyGenParameterSpec getKeyGenParameterSpec(String keyName) {
        return generators.get(keyName).generate(keyName);
    } 

} 