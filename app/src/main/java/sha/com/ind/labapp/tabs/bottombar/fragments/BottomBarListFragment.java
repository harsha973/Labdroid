package sha.com.ind.labapp.tabs.bottombar.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseListFragment;
import sha.com.ind.labapp.home.adapters.GenericListAdapter;
import sha.com.ind.labapp.manager.ActivityManagerUtils;
import sha.com.ind.labapp.tabs.bottombar.CustomBottomBarActivity;
import sha.com.ind.labapp.tabs.bottombar.CustomBottomBarActivity2;
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
            //  Junk
            case 0 :
//                getFragmentManager().beginTransaction().add(R.id.container, JunkFragment.getInstance()).addToBackStack(null).commit();
//                ActivityManagerUtils.startActivity(getActivity() ,
//                        JunkActivity.class,
//                        ActivityManagerUtils.EnterAnimation.ZOOM_IN,
//                        ActivityManagerUtils.ExitAnimation.ZOOM_OUT);

                ActivityManagerUtils.startActivity(getActivity() , RoughikeBottomNavigationActivity.class);

                break;

            //  Custom components
            case 1 :
                ActivityManagerUtils.startActivity(getActivity() , MaterialBottomBarActivity.class);
                break;
//
//            // Media
            case 2 :
                ActivityManagerUtils.startActivity(getActivity() , CustomBottomBarActivity2.class);
                break;
        }
    }

}
