package sha.com.ind.labapp.home.fragments;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.dump.SecurityUtil;
import sha.com.ind.labapp.utils.FingerprintUiHelper;

/**
 * Created by sreepolavarapu on 7/02/17.
 */

public class LockFragment extends BaseFragment implements FingerprintUiHelper.Callback{

    private final static String TAG = LockFragment.class.getSimpleName();

    private FingerprintUiHelper mFingerprintUiHelper;

    @BindView(R.id.fingerprint_icon)
    ImageView fingerPrintIV;
    @BindView(R.id.fingerprint_status)
    TextView statusTV;

    FingerprintManager.CryptoObject mCryptoObject;
    private SecurityUtil securityUtil;
    private Cipher defaultCipher;
    Cipher mDecryptCipher;

    public static LockFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LockFragment fragment = new LockFragment();
        fragment.setArguments(args);
        return fragment;
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

        if(isTragetVersionAboveSDK_M())
        {
            securityUtil = new SecurityUtil(getContext());

            defaultCipher = securityUtil.getCipher(SecurityUtil.DEFAULT_KEY_NAME, Cipher.ENCRYPT_MODE);
            if(initEncryptCipher(SecurityUtil.DEFAULT_KEY_NAME))
            {
                mCryptoObject = new FingerprintManager.CryptoObject(defaultCipher);
            }

            mFingerprintUiHelper = new FingerprintUiHelper(
                    getContext().getSystemService(FingerprintManager.class),
                    fingerPrintIV, statusTV, this);

            // If fingerprint authentication is not available, switch immediately to the backup
            // (password) screen.
            if (!mFingerprintUiHelper.isFingerprintAuthAvailable()) {
                goToBackup();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if(mCryptoObject != null && isTragetVersionAboveSDK_M())
        {
            mFingerprintUiHelper.startListening(mCryptoObject);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(mCryptoObject != null && isTragetVersionAboveSDK_M())
        {
            mFingerprintUiHelper.stopListening();
        }
    }

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


    @Override
    public void onAuthenticated() {

        if(isTragetVersionAboveSDK_M())
        {
            String encrypted = securityUtil.tryEncrypt(getContext(), defaultCipher, "Bingo");
            Log.d(TAG, "Encrypted stuff is : "+encrypted);

//            try {

                if(initDecryptCipher(SecurityUtil.DEFAULT_KEY_NAME))
                {
                    String decrypted = securityUtil.tryDecrypt(getContext(), mDecryptCipher, encrypted);
                    Log.d(TAG, "Decrypted stuff is : "+decrypted);
                }
//            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
//                throw new RuntimeException("Failed to get an instance of Cipher", e);
//            }

        }

    }

    public boolean initEncryptCipher(String keyname) {

        defaultCipher = securityUtil.getCipher(keyname, Cipher.ENCRYPT_MODE);
        if (defaultCipher == null) {
            // try again after recreating the keystore
            securityUtil.createKey(keyname);
            defaultCipher = securityUtil.getCipher(keyname, Cipher.ENCRYPT_MODE);
        }
        return (defaultCipher != null);

    }

    public boolean initDecryptCipher(String keyname) {
        mDecryptCipher = securityUtil.getCipher(keyname, Cipher.DECRYPT_MODE);
        return (mDecryptCipher != null);
    }

    @Override
    public void onError() {
        Log.d(TAG, "ERRRORRR");
    }

    private boolean isTragetVersionAboveSDK_M()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
