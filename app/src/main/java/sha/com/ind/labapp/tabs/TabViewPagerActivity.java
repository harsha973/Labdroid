package sha.com.ind.labapp.tabs;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.tabs.fragments.DemoTab2Fragment;
import sha.com.ind.labapp.tabs.fragments.DemoTabFragment;
import sha.com.ind.labapp.tabs.fragments.TabReplaceFragment;
import sha.com.ind.labapp.utils.AnimationUtils;

/**
 * Created by sreepolavarapu on 21/06/16.
 */
public class TabViewPagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private static final String TAG = TabViewPagerActivity.class.getSimpleName();

    private Switch mMapToggleSwitch;
    private DemoCollectionPagerAdapter mPagerAdapter;
    private static final int TOGGLE_ENABLE_POS = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_with_tabs);

        setupActionBar(R.string.view_pager_and_tabs);
        init();

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


    private void init()
    {
        mMapToggleSwitch = (Switch)findViewById(R.id.switch_map);

        mPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager(), mMapToggleSwitch);
        final ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setPageTransformer(true, new AnimationUtils.ZoomOutPageTransformer());
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(this);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mMapToggleSwitch.setVisibility(position == TOGGLE_ENABLE_POS ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * http://stackoverflow.com/a/12155594/726625
     */
    // Since this is an object collection, use a FragmentStatePagerAdapter,
    // and NOT a FragmentPagerAdapter.
    public static class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        private SwitchFragListener mSwitchFragListener;
        private Switch mToggle;
        private int pagerAdapterPosChanged = POSITION_UNCHANGED;

        public DemoCollectionPagerAdapter(FragmentManager fm, Switch toggle) {
            super(fm);
            mToggle = toggle;

            mSwitchFragListener = new SwitchFragListener();
            mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mSwitchFragListener.onSwitchToNextFragment();
                }
            });
        }

        @Override
        public Fragment getItem(int i) {
            switch (i)
            {
                case TOGGLE_ENABLE_POS:
                    if(mToggle.isChecked())
                    {
                        return TabReplaceFragment.getInstance();
                    }else
                    {
                        return DemoTab2Fragment.getInstance(i);
                    }

                default:
                    return DemoTabFragment.getInstance(i);
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab " + (position + 1);
        }

        @Override
        public int getItemPosition(Object object) {

            //	This check make sures getItem() is called only for the required Fragment
            if (object instanceof TabReplaceFragment
                    ||  object instanceof DemoTab2Fragment)
                return pagerAdapterPosChanged;

            return POSITION_UNCHANGED;
        }

        /**
         * Switch fragments Interface implementation
         */
        private final class SwitchFragListener implements
                SwitchFragInterface {

            SwitchFragListener() {}

            public void onSwitchToNextFragment() {

                pagerAdapterPosChanged = POSITION_NONE;
                notifyDataSetChanged();
            }
        }

        /**
         * Interface to switch frags
         */
        private interface SwitchFragInterface{
            void onSwitchToNextFragment();
        }
    }


}