package sha.com.ind.labapp.other.fingerprint.keys;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;

@RequiresApi(Build.VERSION_CODES.M)
class UserAuthKeySpecGenerator implements KeySpecGenerator {
    private final String blockMode;
    private final String padding;
 
    UserAuthKeySpecGenerator(String blockMode, String padding) {
        this.blockMode = blockMode;
        this.padding = padding;
    } 
 
    @Override 
    public KeyGenParameterSpec generate(String keyName) {
        KeyGenParameterSpec.Builder builder =
                new KeyGenParameterSpec.Builder(keyName, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(blockMode)
                .setEncryptionPaddings(padding)
                .setUserAuthenticationRequired(true);

        // This is a workaround to avoid crashes on devices whose API level is < 24
        // because KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment is only
        // visible on API level +24.
        // Ideally there should be a compat library for KeyGenParameterSpec.Builder but
        // which isn't available yet.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setInvalidatedByBiometricEnrollment(true);
        }

        return builder.build();
    } 
} 