package sha.com.ind.labapp.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseListFragment;
import sha.com.ind.labapp.customcomponents.CustomComponentsActivity;
import sha.com.ind.labapp.dump.JunkFragment;
import sha.com.ind.labapp.home.adapters.GenericListAdapter;
import sha.com.ind.labapp.manager.IntentManager;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends BaseListFragment{


    public HomeFragment() {
    }

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init()
    {
        getListView().setAdapter(new GenericListAdapter(getActivity(), getResources().getStringArray(R.array.main)));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (position)
        {
            //  Junk
            case 0 :
                getFragmentManager().beginTransaction().add(R.id.container, JunkFragment.getInstance()).addToBackStack(null).commit();
                break;

            //  Custom components
            case 1 :
                IntentManager.startActivity(getActivity() , CustomComponentsActivity.class);
                break;
        }
    }


}
