package sha.com.ind.labapp.tabs.bottombar;

import android.os.Bundle;
import android.util.Log;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.home.HomeFragment;

/**
 * Created by sreepolavarapu on 9/08/16.
 */
public class BottomBarListActivity extends BaseActivity {

    private static final String TAG = BottomBarListActivity.class.getSimpleName();

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
