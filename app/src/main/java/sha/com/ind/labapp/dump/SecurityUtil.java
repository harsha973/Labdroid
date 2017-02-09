package sha.com.ind.labapp.dump;

import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import sha.com.ind.labapp.utils.Constants;
import sha.com.ind.labapp.utils.SharedPrefsUtils;

/**
 * Created by sreepolavarapu on 7/02/17.
 */

@RequiresApi(Build.VERSION_CODES.M)
public class SecurityUtil {

    private static final String TAG = SecurityUtil.class.getSimpleName();

    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private Context mContext;

    public static final String DEFAULT_KEY_NAME = "key_default";

    private static final String KEY_ALGORITHM_AES = "AES";
    private static final String BLOCK_MODE_CBC = "CBC";
    private static final String ENCRYPTION_PADDING_PKCS7 = "PKCS7Padding";

    public SecurityUtil(Context context)
    {
        mContext = context;

        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException("Failed to get an instance of KeyStore", e);
        }
        try {
            mKeyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get an instance of KeyGenerator", e);
        }

        KeyguardManager keyguardManager = mContext.getSystemService(KeyguardManager.class);
        FingerprintManager fingerprintManager = mContext.getSystemService(FingerprintManager.class);

        if (!keyguardManager.isKeyguardSecure()) {
            // Show a message that the user hasn't set up a fingerprint or lock screen.
            Toast.makeText(mContext,
                    "Secure lock screen hasn't set up.\n"
                            + "Go to 'Settings -> Security -> Fingerprint' to set up a fingerprint",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Now the protection level of USE_FINGERPRINT permission is normal instead of dangerous.
        // See http://developer.android.com/reference/android/Manifest.permission.html#USE_FINGERPRINT
        // The line below prevents the false positive inspection from Android Studio
        // noinspection ResourceType
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            // This happens when no fingerprints are registered.
            Toast.makeText(mContext,
                    "Go to 'Settings -> Security -> Fingerprint' and register at least one fingerprint",
                    Toast.LENGTH_LONG).show();
            return;
        }

        createKey(DEFAULT_KEY_NAME);
    }

    /**
     * Creates a symmetric key in the Android Key Store which can only be used after the user has
     * authenticated with fingerprint.
     *
     * @param keyName the name of the key to be created
     * @param invalidatedByBiometricEnrollment if {@code false} is passed, the created key will not
     *                                         be invalidated even if a new fingerprint is enrolled.
     *                                         The default value is {@code true}, so passing
     *                                         {@code true} doesn't change the behavior
     *                                         (the key will be invalidated if a new fingerprint is
     *                                         enrolled.). Note that this parameter is only valid if
     *                                         the app works on Android N developer preview.
     *
     */
//    private void createKey(String keyName, boolean invalidatedByBiometricEnrollment) {
//        // The enrolling flow for fingerprint. This is where you ask the user to set up fingerprint
//        // for your flow. Use of keys is necessary if you need to know if the set of
//        // enrolled fingerprints has changed.
//        try {
//            mKeyStore.load(null);
//            // Set the alias of the entry in Android KeyStore where the key will appear
//            // and the constrains (purposes) in the constructor of the Builder
//
//            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(keyName,
//                    KeyProperties.PURPOSE_ENCRYPT |
//                            KeyProperties.PURPOSE_DECRYPT)
//                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
//                    // Require the user to authenticate with a fingerprint to authorize every use
//                    // of the key
//                    .setUserAuthenticationRequired(true)
//                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
//
//            // This is a workaround to avoid crashes on devices whose API level is < 24
//            // because KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment is only
//            // visible on API level +24.
//            // Ideally there should be a compat library for KeyGenParameterSpec.Builder but
//            // which isn't available yet.
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment);
//            }
//            mKeyGenerator.init(builder.build());
//            mKeyGenerator.generateKey();
//        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
//                | CertificateException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    /**
//     * Initialize the {@link Cipher} instance with the created key in the
//     * {@link #createKey(String, boolean)} method.
//     *
//     * @param keyName the key name to init the cipher
//     * @return {@code true} if initialization is successful, {@code false} if the lock screen has
//     * been disabled or reset after the key was generated, or if a fingerprint got enrolled after
//     * the key was generated.
//     */
//    public boolean initCipher(Cipher cipher, String keyName) {
//        try {
//            mKeyStore.load(null);
//            SecretKey key = (SecretKey) mKeyStore.getKey(keyName, null);
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//            return true;
//        } catch (KeyPermanentlyInvalidatedException e) {
//            return false;
//        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
//                | NoSuchAlgorithmException | InvalidKeyException e) {
//            throw new RuntimeException("Failed to init Cipher", e);
//        }
//    }
//
//
//   /**
//     * Initialize the {@link Cipher} instance with the created key in the
//     * {@link #createKey(String, boolean)} method.
//     *
//     * @param keyName the key name to init the cipher
//     * @return {@code true} if initialization is successful, {@code false} if the lock screen has
//     * been disabled or reset after the key was generated, or if a fingerprint got enrolled after
//     * the key was generated.
//     */
//    public boolean initCipherToDecrypt(Cipher cipher, String keyName) {
//        try {
//            mKeyStore.load(null);
//
//            SecureRandom r = new SecureRandom();
//            byte[] ivBytes = new byte[16];
//            r.nextBytes(ivBytes);
//
//            SecretKey key = (SecretKey) mKeyStore.getKey(keyName, null);
////            cipher.init(Cipher.DECRYPT_MODE, key, AlgorithmParameters.getInstance(KeyProperties.KEY_ALGORITHM_AES, new IvParameterSpec(ivBytes)), r);
//            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
//            return true;
//        } catch (KeyPermanentlyInvalidatedException e) {
//            return false;
//        } catch ( InvalidAlgorithmParameterException | /*NoSuchProviderException |*/ KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
//                | NoSuchAlgorithmException | InvalidKeyException e) {
//            throw new RuntimeException("Failed to init Cipher", e);
//        }
//    }



    /**
     * Tries to encrypt some data with the generated key in {@link #createKey} which is
     * only works if the user has just authenticated via fingerprint.
     */
    public String tryEncrypt(Context context, Cipher cipher, String secretMessage) {
        try {
            byte[] encrypted = cipher.doFinal(secretMessage.getBytes());
            IvParameterSpec ivParams = cipher.getParameters().getParameterSpec(IvParameterSpec.class);
            String iv = Base64.encodeToString(ivParams.getIV(), Base64.DEFAULT);
            SharedPrefsUtils.setStringPreference(mContext, Constants.PREF_PASSWORD_IV, iv);

            return Base64.encodeToString(encrypted, 0 /* flags */);
//            showConfirmation(encrypted);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidParameterSpecException e) {
            Toast.makeText(context, "Failed to encrypt the data with the generated key. "
                    + "Retry the purchase", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failed to encrypt the data with the generated key." + e.getMessage());
        }

        return null;
    }

    /**
     * Tries to encrypt some data with the generated key in {@link #createKey} which is
     * only works if the user has just authenticated via fingerprint.
     */
    public String tryDecrypt(Context context, Cipher cipher, String secretMessage) {
        try {
//            byte[] decrypted = cipher.doFinal(Base64.decode(secretMessage.getBytes(), Base64.DEFAULT));
//            return new String(decrypted);
//            showConfirmation(encrypted);

            byte[] encodedData = Base64.decode(secretMessage, Base64.DEFAULT);
            byte[] decodedData = cipher.doFinal(encodedData);
            return new String(decodedData);

        } catch (BadPaddingException | IllegalBlockSizeException e) {
            Toast.makeText(context, "Failed to encrypt the data with the generated key. "
                    + "Retry the purchase", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failed to encrypt the data with the generated key." + e.getMessage());
        }

        return null;
    }


    public Cipher getCipher(String keyName, int mode) {
        Cipher cipher;

        try {
            mKeyStore.load(null);
            byte[] iv;
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            IvParameterSpec ivParams;
            if (mode == Cipher.ENCRYPT_MODE) {
                cipher.init(mode, getKey(keyName));

            } else {
                SecretKey key = (SecretKey) mKeyStore.getKey(keyName, null);
                iv = Base64.decode(SharedPrefsUtils.getStringPreference(mContext, Constants.PREF_PASSWORD_IV), Base64.DEFAULT);
                ivParams = new IvParameterSpec(iv);
                cipher.init(mode, key, ivParams);
            }
            return cipher;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private SecretKey getKey(String keyName) {
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(keyName, null);
            if (key != null) return key;
            return createKey(keyName);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SecretKey createKey(String keyname) {
        try {

            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder
            mKeyGenerator.init(new KeyGenParameterSpec.Builder(keyname,
                    KeyProperties.PURPOSE_DECRYPT | KeyProperties.PURPOSE_ENCRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    // Require the user to authenticate with a fingerprint to authorize every use
                    // of the key
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            return mKeyGenerator.generateKey();

        } catch (Exception e) {

        }
        return null;
    }
}
