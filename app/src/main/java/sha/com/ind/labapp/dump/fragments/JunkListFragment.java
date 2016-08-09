package sha.com.ind.labapp.dump.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.base.BaseListFragment;
import sha.com.ind.labapp.home.adapters.GenericListAdapter;
import sha.com.ind.labapp.manager.ActivityManagerUtils;
import sha.com.ind.labapp.tabs.bottombar.BottomBarListActivity;
import sha.com.ind.labapp.tabs.TabViewPagerActivity;
import sha.com.ind.labapp.utils.FragmentManagerUtils;

/**
 * Created by sreepolavarapu on 21/06/16.
 */
public class JunkListFragment extends BaseListFragment {

    public static JunkListFragment getInstance() {
        return new JunkListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init()
    {
        getListView().setAdapter(new GenericListAdapter(getActivity(), getResources().getStringArray(R.array.junk)));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (position)
        {
            //  Junk
            case 0 :
                FragmentManagerUtils.replaceFragmentAndAddToBackStack(
                        (BaseActivity)getActivity(),
                        JunkFragment.getInstance(),
                        JunkFragment.TAG,
                        FragmentManagerUtils.Animation.SLIDE_IN_RIGHT);
                break;

            //  Recycler
            case 1 :

                FragmentManagerUtils.replaceFragmentAndAddToBackStack(
                        (BaseActivity)getActivity(),
                        RecylerJunkFragment.getInstance(),
                        RecylerJunkFragment.TAG,
                        FragmentManagerUtils.Animation.SLIDE_IN_RIGHT);
                break;

            // Tabs with Viewpager
            case 2 :
                ActivityManagerUtils.startActivity(getActivity() , TabViewPagerActivity.class);
                break;

            // Bottom navigation fragment
            case 3 :
                ActivityManagerUtils.startActivity(getActivity() , BottomBarListActivity.class);
                break;

            // Transp Circle Frag
            case 4 :
                FragmentManagerUtils.replaceFragmentAndAddToBackStack(
                        (BaseActivity)getActivity(),
                        TranspCircleBLLFragment.newInstance(),
                        TranspCircleBLLFragment.TAG,
                        FragmentManagerUtils.Animation.SLIDE_IN_RIGHT);
                break;
        }
    }
}
