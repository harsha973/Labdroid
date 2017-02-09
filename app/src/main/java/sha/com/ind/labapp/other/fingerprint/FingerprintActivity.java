package sha.com.ind.labapp.other.fingerprint;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.other.fingerprint.fragments.LockFragment;
import sha.com.ind.labapp.utils.Constants;

@RequiresApi(Build.VERSION_CODES.M)
public class FingerprintActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_generic);
//        boolean isFingerPrintEnabled = SharedPrefsUtils.
//                getBooleanPreference(this, Constants.PREF_KEY_IS_FINGERPRINT_ENABLED, false);
//        if(isFingerPrintEnabled)
//        {
            boolean toEncrypt = getIntent().getExtras().getBoolean(Constants.PREF_TO_ENCRYPT);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, LockFragment.newInstance(toEncrypt))
                    .commit();
//        }else
//        {
//
//        }
    }


}