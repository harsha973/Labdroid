package sha.com.ind.labapp.customcomponents.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.base.BaseListFragment;
import sha.com.ind.labapp.home.adapters.GenericListAdapter;
import sha.com.ind.labapp.utils.FragmentManagerUtils;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class CustomComponentsFragment extends BaseListFragment {

    public static String TAG = CustomComponentsFragment.class.getSimpleName();

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
                FragmentManagerUtils.replaceFragmentAndAddToBackStack(
                        (BaseActivity)getActivity(),
                        FBStyleProfilePicFragment.getInstance(),
                        FBStyleProfilePicFragment.TAG,
                        FragmentManagerUtils.Animation.SLIDE_IN_RIGHT);
                break;

            //  FB style profile pic fragment
            case 1 :
                FragmentManagerUtils.replaceFragmentAndAddToBackStack(
                        (BaseActivity)getActivity(),
                        FBStyleProfilePicWithIntialsFragment.getInstance(),
                        FBStyleProfilePicWithIntialsFragment.TAG,
                        FragmentManagerUtils.Animation.SLIDE_IN_RIGHT);
                break;

            //  Countdown timer button fragment
            case 2 :
                FragmentManagerUtils.replaceFragmentAndAddToBackStack(
                        (BaseActivity)getActivity(),
                        CoundownTimerButtonFragment.getInstance(),
                        CoundownTimerButtonFragment.TAG,
                        FragmentManagerUtils.Animation.SLIDE_IN_RIGHT);
                break;
        }
    }
}
