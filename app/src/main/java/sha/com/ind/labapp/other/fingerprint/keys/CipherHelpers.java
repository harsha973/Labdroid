package sha.com.ind.labapp.other.fingerprint.keys;

import android.support.annotation.Nullable;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by sreepolavarapu on 8/02/17.
 *
 * Container for some helper functionalities around Cipher
 */
public final class CipherHelpers {

    /**
     * Encrypt the plain text with the cipher.
     * @param cipher    Cipher which is set to Encryption Mode
     * @param plainText Text to encrypt
     * @return  Encrypted text if everything is successful / null  otherwise
     */
    public static @Nullable String tryEncrypt(Cipher cipher, String plainText) {
        try {
            //  You have encode the encrypted string or there might be chance of loosing some bytes.
            //  Check this - http://stackoverflow.com/a/21712394/726625
            byte base64Encrypted[] = Base64.encode(cipher.doFinal(plainText.getBytes()), Base64.DEFAULT);
            return new String(base64Encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypt's the encrypted text.
     * @param cipher    Cipher to decrypt and should be set to Decryption Mode.
     * @param encryptedMessage  Encrypted message
     * @return  Plain text in case of success or null
     */
    public static @Nullable String tryDecrypt(Cipher cipher, String encryptedMessage) {
        try {
            return new String(cipher.doFinal(Base64.decode(encryptedMessage.getBytes(), Base64.DEFAULT)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts the IV parameters from the Cipher object
     * @param cipher    Cipher
     * @return  Base64 encoded iv params. Null in case of failure
     */
    public static @Nullable String getIVParams(Cipher cipher)
    {
        try{
            IvParameterSpec ivParams = cipher.getParameters().getParameterSpec(IvParameterSpec.class);
            return Base64.encodeToString(ivParams.getIV(), Base64.DEFAULT);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }
}
