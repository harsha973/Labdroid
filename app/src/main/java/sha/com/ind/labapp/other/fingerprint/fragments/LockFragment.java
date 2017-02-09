package sha.com.ind.labapp.other.fingerprint.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.crypto.Cipher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.other.fingerprint.keys.KeyTools;
import sha.com.ind.labapp.other.fingerprint.keys.KeyToolsException;
import sha.com.ind.labapp.other.fingerprint.keys.KeyToolsFingerPrint;
import sha.com.ind.labapp.utils.Constants;
import sha.com.ind.labapp.utils.SharedPrefsUtils;

import static sha.com.ind.labapp.other.fingerprint.keys.CipherHelpers.getIVParams;
import static sha.com.ind.labapp.other.fingerprint.keys.CipherHelpers.tryDecrypt;
import static sha.com.ind.labapp.other.fingerprint.keys.CipherHelpers.tryEncrypt;
import static sha.com.ind.labapp.other.fingerprint.keys.KeyTools.KEY_ALIAS_PIN;

/**
 * Created by sreepolavarapu on 7/02/17.
 */

public class LockFragment extends BaseFragment{

    private final static String TAG = LockFragment.class.getSimpleName();

    @BindView(R.id.fingerprint_icon)
    ImageView fingerPrintIV;
    @BindView(R.id.fingerprint_status)
    TextView statusTV;
    @BindView(R.id.et_pin)
    TextView pinET;

    private boolean toEncrypt;

    private FingerprintManagerCompat fingerprintManager;
    private AuthenticationCallback authenticationCallback = new AuthenticationCallback();
    private KeyToolsFingerPrint keyToolsFingerPrint;
    private CancellationSignal cancellationSignal = null;


    public static LockFragment newInstance(boolean encrypt) {
        
        Bundle args = new Bundle();
        args.putBoolean(Constants.PREF_TO_ENCRYPT, encrypt);
        
        LockFragment fragment = new LockFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
        {
            toEncrypt = getArguments().getBoolean(Constants.PREF_TO_ENCRYPT);
        }
        else if(savedInstanceState != null)
        {
            toEncrypt = savedInstanceState.getBoolean(Constants.PREF_TO_ENCRYPT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lock, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  We are going for fingerprint
        if(isTargetVersionAboveSDK_M())
        {
            postOnCreateViewFingerPrint();
        }


    }


    @Override
    public void onResume() {
        super.onResume();

        if(isTargetVersionAboveSDK_M())
        {
            if (!fingerprintManager.isHardwareDetected()) {
                showError(R.string.no_fingerprint_hardware);
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                showError(R.string.no_fingerprints_enrolled);
            } else if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                showError("Permission not granted");
            } else {
                authenticate();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
    }

    //  ----------------------------
    //  ------- FINGER PRINT -------
    //  ----------------------------

    @RequiresApi(Build.VERSION_CODES.M)
    private void postOnCreateViewFingerPrint(){
        try {
            keyToolsFingerPrint = KeyToolsFingerPrint.newInstance(getContext());
        }catch (KeyToolsException ke)
        {
            ke.printStackTrace();
        }
        fingerprintManager = FingerprintManagerCompat.from(getContext());
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private void authenticate() {
        Cipher cipher;
        try {
            if(toEncrypt)
            {
                cipher = keyToolsFingerPrint.getUserAuthEncryptCipher();
            }else
            {
                cipher = keyToolsFingerPrint.getUserAuthDecryptCipher();
            }
        } catch (KeyToolsException e) {
            e.printStackTrace();
            showError(e.getMessage());
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        FingerprintManagerCompat.CryptoObject crypto = new FingerprintManagerCompat.CryptoObject(cipher);
        cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(crypto, 0, cancellationSignal, authenticationCallback, null);
    }


    private void validate(Cipher cipher) {
        if(toEncrypt)
        {
            String encrypted = tryEncrypt(cipher, "Bingo this is what I need");
            if(!TextUtils.isEmpty(encrypted)){

                String ivParams = getIVParams(cipher);
                SharedPrefsUtils.setStringPreference(getContext(), Constants.PREF_PASSWORD_IV, ivParams);
                SharedPrefsUtils.setStringPreference(getContext(), Constants.PREF_ENCRYPTED_MESSAGE, encrypted);
                SharedPrefsUtils.setBooleanPreference(getContext(), Constants.PREF_KEY_IS_FINGERPRINT_ENABLED, true);

                fingerPrintIV.setImageResource(R.drawable.ic_fingerprint_success);
                statusTV.setTextColor(
                        ContextCompat.getColor(getContext(), R.color.finger_print_success_color));
                statusTV.setText(
                        statusTV.getResources().getString(R.string.fingerprint_success));
            }else
            {
                showError(R.string.validation_error);
            }
        }
        else
        {
            String encrypted = SharedPrefsUtils.getStringPreference(getContext(), Constants.PREF_ENCRYPTED_MESSAGE);
            String decrypted = tryDecrypt(cipher, encrypted);

            if(!TextUtils.isEmpty(decrypted)){
                Log.d(TAG, "Decrypted string is :"+decrypted);
            }else
            {
                showError(R.string.validation_error);
            }
        }

    }

    private void showError(@StringRes int stringResID)
    {
        showError(getString(stringResID));
    }

    private void showError(String error)
    {
        statusTV.setText(error);
    }


    private boolean isTargetVersionAboveSDK_M()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private class AuthenticationCallback extends FingerprintManagerCompat.AuthenticationCallback {
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            validate(result.getCryptoObject().getCipher());
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            showError(errString.toString());
        }

        @Override
        public void onAuthenticationFailed() {
            showError(R.string.identification_failed);
        }
    }

    //  ----------------------------
    //  ------- END FINGER PRINT ---
    //  ----------------------------


    //  ---------------------------
    //  ----------  PIN  ----------
    //  ---------------------------

    @OnTextChanged(R.id.et_pin)
    void onPinEntered()
    {
        String text = pinET.getText().toString();

        //  Pin entered
        if(text.length() == 4)
        {
            if(toEncrypt)
            {
                try{
                    KeyTools keyTools = KeyTools.newInstance(getContext());

                    keyTools.generateRSAKeyPair(KEY_ALIAS_PIN);
                    keyTools.generateAndStoreAESKey(KEY_ALIAS_PIN);

                    String encrypted = keyTools.encrypt(KEY_ALIAS_PIN, text.getBytes());
                    String decrypted = new String(keyTools.decrypt(KEY_ALIAS_PIN, Base64.decode(encrypted.getBytes(), Base64.DEFAULT)));

                    Log.d(TAG, "Encrypted is "+encrypted + " decrypted is "+decrypted);

                }catch (KeyToolsException kte)
                {
                    Log.d(TAG, "Key tool exception : "+kte.getMessage());
                }
            }
        }
    }
    //  ---------------------------
    //  ----------  END PIN  ------
    //  ---------------------------

    /**
     * Switches to backup (password) screen. This either can happen when fingerprint is not
     * available or the user chooses to use the password authentication method by pressing the
     * button. This can also happen when the user had too many fingerprint attempts.
     */
    private void goToBackup() {
//        mStage = Stage.PASSWORD;
//        updateStage();
//        mPassword.requestFocus();
//
//        // Show the keyboard.
//        mPassword.postDelayed(mShowKeyboardRunnable, 500);
//
//        // Fingerprint is not used anymore. Stop listening for it.
//        mFingerprintUiHelper.stopListening();
    }


//    @Override
//    public void onAuthenticated() {
//
//        if(isTargetVersionAboveSDK_M())
//        {
//            String encrypted = securityUtil.tryEncrypt(getContext(), defaultCipher, "Bingo");
//            Log.d(TAG, "Encrypted stuff is : "+encrypted);
//
////            try {
//
//                if(initDecryptCipher(SecurityUtil.DEFAULT_KEY_NAME))
//                {
//                    String decrypted = securityUtil.tryDecrypt(getContext(), mDecryptCipher, encrypted);
//                    Log.d(TAG, "Decrypted stuff is : "+decrypted);
//                }
////            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
////                throw new RuntimeException("Failed to get an instance of Cipher", e);
////            }
//
//        }
//
//    }

//    public boolean initEncryptCipher(String keyname) {
//
//        defaultCipher = securityUtil.getCipher(keyname, Cipher.ENCRYPT_MODE);
//        if (defaultCipher == null) {
//            // try again after recreating the keystore
//            securityUtil.createKey(keyname);
//            defaultCipher = securityUtil.getCipher(keyname, Cipher.ENCRYPT_MODE);
//        }
//        return (defaultCipher != null);
//
//    }
//
//    public boolean initDecryptCipher(String keyname) {
//        mDecryptCipher = securityUtil.getCipher(keyname, Cipher.DECRYPT_MODE);
//        return (mDecryptCipher != null);
//    }

//    @Override
//    public void onError() {
//        Log.d(TAG, "ERRRORRR");
//    }
}
