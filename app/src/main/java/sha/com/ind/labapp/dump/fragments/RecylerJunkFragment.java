package sha.com.ind.labapp.dump.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.dump.adapters.JunkRecylerAdapter;
import sha.com.ind.labapp.dump.models.DefaultStringModel;
import sha.com.ind.labapp.dump.models.ViewPagerModel;

/**
 * Created by sreepolavarapu on 15/06/16.
 */
public class RecylerJunkFragment extends BaseFragment {

    public static final String TAG = RecylerJunkFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;

    public static RecylerJunkFragment getInstance() {
        return new RecylerJunkFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_junk, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_junk);

        ArrayList<Object> arrayList = new ArrayList<>();

        for(int index = 0; index < 30; index++)
        {
            if(index % 5 == 0)
            {
                arrayList.add(new ViewPagerModel());
            }else
            {
                arrayList.add(new DefaultStringModel());
            }

        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        JunkRecylerAdapter recylerAdapter = new JunkRecylerAdapter((BaseActivity)getActivity(), arrayList);
        mRecyclerView.setAdapter(recylerAdapter);
    }
}
