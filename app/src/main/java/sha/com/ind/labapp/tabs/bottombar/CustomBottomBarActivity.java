package sha.com.ind.labapp.tabs.bottombar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.custom.components.BottomNavigationTabBar;
import sha.com.ind.labapp.tabs.bottombar.interfaces.OnBottomTabbarCallBack;
import sha.com.ind.labapp.tabs.fragments.DemoTab2Fragment;

/**
 * Created by sreepolavarapu on 9/08/16.
 *
 * Activity to demo the Bottom nav bar
 */
public class CustomBottomBarActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, OnBottomTabbarCallBack {

    private BottomNavigationTabBar mBottomNavLL;
    private ViewPager mViewPager;

    private static final int MAX_TAB_COUNT = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_bottom_bar_custom2);
        setupActionBar(R.string.bottom_navigation_custom);
        enableDisplayHomeasUp();

        mBottomNavLL = (BottomNavigationTabBar) findViewById(R.id.ll_bottom_nav);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), "title");
        mViewPager.setAdapter(pagerAdapter);

        CheckBox mFixedTabsCB = (CheckBox) findViewById(R.id.cb_fixed_tabs);
        CheckBox mDarkThemeCB = (CheckBox) findViewById(R.id.cb_dark_theme);

        mFixedTabsCB.setOnCheckedChangeListener(this);
        mDarkThemeCB.setOnCheckedChangeListener(this);
        mBottomNavLL.updateBottomTabCallback(this);
        prepareTabs();
    }

    private void prepareTabs(){

        for (int index = 0; index < 5; index++)
        {
            switch (index)
            {
                case 0:
                    mBottomNavLL.addNewTab( "Home", R.drawable.ic_home_white_24dp, true);
                    break;

                case 1:
                    mBottomNavLL.addNewTab( "Stores", R.drawable.ic_face_white_24dp, false);
                    break;

                case 2:
                    mBottomNavLL.addNewTab( "Card", R.drawable.ic_credit_card_white_24dp, false);
                    break;

                case 3:
                    mBottomNavLL.addNewTab( "Spend pts", R.drawable.ic_feedback_white_24dp, false);
                    break;

                case 4:
                    mBottomNavLL.addNewTab( "More", R.drawable.ic_more_horiz_white_24dp, false);
                    break;
            }
        }

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

        switch (compoundButton.getId())
        {
            case R.id.cb_fixed_tabs:
                if(checked)
                {
                    mBottomNavLL.setAsFixedTabs();
                }else
                {
                    mBottomNavLL.setAsShiftingTabs();
                }
                break;

            case R.id.cb_dark_theme:
                if(checked)
                {
                    mBottomNavLL.enableDarkTheme();
                }
                else
                {
                    mBottomNavLL.disableDarkTheme();
                }
                break;
        }
    }

    @Override
    public void onTabSelected(int position) {
        mViewPager.setCurrentItem(position);
    }


    public static class PagerAdapter extends FragmentStatePagerAdapter {

        private String mTitle;
        public PagerAdapter(FragmentManager fm, String title) {
            super(fm);
            mTitle = title;
        }

        @Override
        public Fragment getItem(int i) {
            return DemoTab2Fragment.getInstance(mTitle);
        }

        @Override
        public int getCount() {
            return MAX_TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab " + (position + 1);
        }

    }

}