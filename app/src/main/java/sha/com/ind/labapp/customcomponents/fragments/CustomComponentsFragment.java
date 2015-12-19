package sha.com.ind.labapp.customcomponents.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.base.BaseListFragment;
import sha.com.ind.labapp.customcomponents.CustomComponentsActivity;
import sha.com.ind.labapp.home.adapters.GenericListAdapter;
import sha.com.ind.labapp.manager.IntentManager;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class CustomComponentsFragment extends BaseListFragment {

    public static CustomComponentsFragment getInstance()
    {
        CustomComponentsFragment fragment = new CustomComponentsFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init()
    {
        getListView().setAdapter(new GenericListAdapter(getActivity(), getResources().getStringArray(R.array.custom_components)));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (position)
        {
            //  FB style profile pic fragment
            case 0 :
                getFragmentManager().beginTransaction().replace(R.id.container, FBStyleProfilePicFragment.getInstance()).addToBackStack(null).commit();
                break;

            //  FB style profile pic fragment
            case 1 :
                getFragmentManager().beginTransaction().replace(R.id.container, FBStyleProfilePicWithIntialsFragment.getInstance()).addToBackStack(null).commit();
                break;
        }
    }
}
