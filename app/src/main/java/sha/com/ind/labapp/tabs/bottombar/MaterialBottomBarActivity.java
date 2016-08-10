package sha.com.ind.labapp.tabs.bottombar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.custom.components.BottomBarItemLL;

/**
 * Created by sreepolavarapu on 9/08/16.
 */
public class MaterialBottomBarActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_nav_mat);
        setupActionBar(R.string.bottom_navigation_mat);
        enableDisplayHomeasUp();

    }

}
