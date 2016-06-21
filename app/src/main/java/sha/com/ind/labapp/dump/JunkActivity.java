package sha.com.ind.labapp.dump;

import android.os.Bundle;
import android.util.Log;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.dump.fragments.JunkListFragment;

/**
 * Created by sreepolavarapu on 15/06/16.
 */
public class JunkActivity extends BaseActivity{

    private static final String TAG = JunkActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);
        setupActionBar(R.string.dump);
        enableDisplayHomeasUp();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, JunkListFragment.getInstance())
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
