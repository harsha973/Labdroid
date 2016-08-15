package sha.com.ind.labapp.tabs.bottombar.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseListFragment;
import sha.com.ind.labapp.home.adapters.GenericListAdapter;
import sha.com.ind.labapp.manager.ActivityManagerUtils;
import sha.com.ind.labapp.tabs.bottombar.CustomBottomBarActivity;
import sha.com.ind.labapp.tabs.bottombar.MaterialBottomBarActivity;
import sha.com.ind.labapp.tabs.bottombar.RoughikeBottomNavigationActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class BottomBarListFragment extends BaseListFragment{


    public BottomBarListFragment() {
    }

    public static BottomBarListFragment getInstance() {
        return new BottomBarListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init()
    {
        getListView().setAdapter(new GenericListAdapter(getActivity(), getResources().getStringArray(R.array.bottombar)));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (position)
        {
            //  Rough hike Lib
            case 0 :
                ActivityManagerUtils.startActivity(getActivity() , RoughikeBottomNavigationActivity.class);

                break;

            //  Material bottom lib
            case 1 :
                ActivityManagerUtils.startActivity(getActivity() , MaterialBottomBarActivity.class);
                break;

            //  Custom - Mine .. Yay  :) .. With or without you .. ha ha
            case 2 :
                ActivityManagerUtils.startActivity(getActivity() , CustomBottomBarActivity.class);
                break;
        }
    }

}
