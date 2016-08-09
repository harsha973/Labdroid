package sha.com.ind.labapp.tabs.bottombar;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;

/**
 * Created by sreepolavarapu on 8/08/16.
 */
public class RoughikeBottomNavigationActivity extends BaseActivity {
    private BottomBar mBottomBar;
    private @IdRes int mPrevMenuID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_nav);
        setupActionBar(R.string.bottom_navigation);
        enableDisplayHomeasUp();

        mPrevMenuID = R.id.bottomBarItemOne;
        mBottomBar = BottomBar.attach(this, savedInstanceState);

//        fixedMode3Tabs(null);
//        fixedMode(null);
        darkMode(null);

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
//        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.accent));
//        mBottomBar.mapColorForTab(1, 0xFF5D4037);
//        mBottomBar.mapColorForTab(2, "#7B1FA2");
//        mBottomBar.mapColorForTab(3, "#FF5252");
//        mBottomBar.mapColorForTab(4, "#FF9800");

        //  Not working.
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, RoughikeBottomBarFragment.newInstance())
//                .commit();

    }

    private void clearBackcolor()
    {
        mBottomBar.findViewById(mPrevMenuID).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, R.color.white));
//        for (int index = 0; index < mBottomBar.getChildCount(); index++)
//        {
//            mBottomBar.getChildAt(index).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, R.color.white));
//        }
    }

    public void fixedMode3Tabs(View view){

        mBottomBar.useFixedMode();
        mBottomBar.setActiveTabColor(ContextCompat.getColor(this, android.R.color.white));
        mBottomBar.setItems(R.menu.menu_bottom_bar_3);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {

            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user selected item number one.

                    //                    mBottomBar.findViewById(R.id.bottomBarItemOne).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, android.R.color.holo_blue_dark));
                }

                clearBackcolor();
                mBottomBar.findViewById(menuItemId).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, android.R.color.holo_blue_dark));

                mPrevMenuID = menuItemId;
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });

    }

    public void fixedMode(View view){

        mBottomBar.useFixedMode();
        mBottomBar.setActiveTabColor(ContextCompat.getColor(this, android.R.color.white));
        mBottomBar.setItems(R.menu.menu_bottom_bar);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {

            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user selected item number one.

                    //                    mBottomBar.findViewById(R.id.bottomBarItemOne).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, android.R.color.holo_blue_dark));
                }

                clearBackcolor();
                mBottomBar.findViewById(menuItemId).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, android.R.color.holo_blue_dark));

                mPrevMenuID = menuItemId;
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });
    }

    public void darkMode(View view){

        //  Active tab color doesn't work with mode than 3 tabs
        mBottomBar.setShiftingIconColor(ContextCompat.getColor(this, android.R.color.white));
        mBottomBar.setItems(R.menu.menu_bottom_bar);
        mBottomBar.getBar().setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, android.R.color.holo_blue_dark));

        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {

             @Override
             public void onMenuTabSelected(@IdRes int menuItemId) {
                 if (menuItemId == R.id.bottomBarItemOne) {
                     // The user selected item number one.

    //                    mBottomBar.findViewById(R.id.bottomBarItemOne).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, android.R.color.holo_blue_dark));
                 }

//                 clearBackcolor();
//                 mBottomBar.findViewById(menuItemId).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, android.R.color.holo_blue_dark));

                 mPrevMenuID = menuItemId;
             }

             @Override
             public void onMenuTabReSelected(@IdRes int menuItemId) {
                 if (menuItemId == R.id.bottomBarItemOne) {
                     // The user reselected item number one, scroll your content to top.
                 }
             }
         });

    }

    public void darkMode3Tabs(View view){

    }

    private void showCaseView(){



    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }
}
