package sha.com.ind.labapp.customcomponents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.customcomponents.fragments.CustomComponentsFragment;
import sha.com.ind.labapp.utils.FragmentManagerUtils;


/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class CustomComponentsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_generic);
        setupActionBar(R.string.custom_components);
        enableDisplayHomeasUp();

        FragmentManagerUtils.addFragment(this,
                CustomComponentsFragment.getInstance(),
                CustomComponentsFragment.TAG,
                FragmentManagerUtils.Animation.NONE);
    }
}
