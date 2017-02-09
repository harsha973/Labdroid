package sha.com.ind.labapp.home;

import android.os.Bundle;
import android.util.Log;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.home.fragments.HomeFragment;
import sha.com.ind.labapp.manager.ActivityManagerUtils;
import sha.com.ind.labapp.other.fingerprint.FingerprintActivity;
import sha.com.ind.labapp.utils.Constants;
import sha.com.ind.labapp.utils.SharedPrefsUtils;

public class HomeActivity extends BaseActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        setupActionBar(R.string.app_name);

        boolean isFingerPrintEnabled = SharedPrefsUtils.
                getBooleanPreference(this, Constants.PREF_KEY_IS_FINGERPRINT_ENABLED, false);
        if(isFingerPrintEnabled)
        {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container, LockFragment.newInstance())
//                    .commit();

            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.PREF_TO_ENCRYPT, false);
            ActivityManagerUtils.startActivityWithExtras(this ,
                    FingerprintActivity.class,
                    bundle,
                    ActivityManagerUtils.EnterAnimation.NONE,
                    ActivityManagerUtils.ExitAnimation.NONE);
        }else
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, HomeFragment.getInstance())
                    .commit();
        }

        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            Log.i("CRYPTO","provider: "+provider.getName());
            Set<Provider.Service> services = provider.getServices();
            for (Provider.Service service : services) {
                Log.i("CRYPTO","  algorithm: "+service.getAlgorithm());
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e(TAG, "Low memory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e(TAG, "Trim memory : Level : "+level);
    }
}
