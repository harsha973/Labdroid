package sha.com.ind.labapp.other.fingerprint.keys;


import android.security.keystore.KeyGenParameterSpec;

interface KeySpecGenerator {
    KeyGenParameterSpec generate(String keyName);
} 