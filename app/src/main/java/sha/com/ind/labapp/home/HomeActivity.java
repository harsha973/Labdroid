package sha.com.ind.labapp.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.customcomponents.fragments.CustomComponentsFragment;

public class HomeActivity extends BaseActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        setupActionBar(R.string.app_name);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, HomeFragment.getInstance())
                .commit();
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
