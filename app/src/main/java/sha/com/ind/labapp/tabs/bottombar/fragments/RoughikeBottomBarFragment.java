package sha.com.ind.labapp.tabs.bottombar.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;

/**
 * Created by sreepolavarapu on 9/08/16.
 */
public class RoughikeBottomBarFragment extends BaseFragment {

    private BottomBar mBottomBar;
    private @IdRes int mPrevMenuID;

    public static RoughikeBottomBarFragment newInstance() {

        Bundle args = new Bundle();

        RoughikeBottomBarFragment fragment = new RoughikeBottomBarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPrevMenuID = R.id.bottomBarItemOne;
        mBottomBar = BottomBar.attach(view, savedInstanceState);

        darkMode(null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void clearBackcolor()
    {
        mBottomBar.findViewById(mPrevMenuID).setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
//        for (int index = 0; index < mBottomBar.getChildCount(); index++)
//        {
//            mBottomBar.getChildAt(index).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, R.color.white));
//        }
    }

    public void fixedMode3Tabs(View view){

        mBottomBar.useFixedMode();
        mBottomBar.setActiveTabColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        mBottomBar.setItems(R.menu.menu_bottom_bar_3);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {

            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user selected item number one.

                    //                    mBottomBar.findViewById(R.id.bottomBarItemOne).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, android.R.color.holo_blue_dark));
                }

                clearBackcolor();
                mBottomBar.findViewById(menuItemId).setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark));

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
        mBottomBar.setActiveTabColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        mBottomBar.setItems(R.menu.menu_bottom_bar);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {

            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user selected item number one.

                    //                    mBottomBar.findViewById(R.id.bottomBarItemOne).setBackgroundColor(ContextCompat.getColor(RoughikeBottomNavigationActivity.this, android.R.color.holo_blue_dark));
                }

                clearBackcolor();
                mBottomBar.findViewById(menuItemId).setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark));

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
        mBottomBar.setShiftingIconColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        mBottomBar.setItems(R.menu.menu_bottom_bar);
        mBottomBar.getBar().setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark));

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

}
