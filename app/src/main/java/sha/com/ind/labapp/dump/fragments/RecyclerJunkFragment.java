package sha.com.ind.labapp.dump.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.dump.adapters.JunkRecyclerAdapter;
import sha.com.ind.labapp.dump.adapters.JunkRecyclerAdapter.RecyclerAdapterListener;
import sha.com.ind.labapp.dump.models.BaseViewModel;
import sha.com.ind.labapp.dump.models.DefaultStringViewModel;
import sha.com.ind.labapp.dump.models.ViewPagerViewModel;
import sha.com.ind.labapp.utils.itemsdecorators.OnDemandSpaceItemDecoration;

/**
 * Created by sreepolavarapu on 15/06/16.
 */
public class RecyclerJunkFragment extends BaseFragment implements RecyclerAdapterListener{

    public static final String TAG = RecyclerJunkFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private JunkRecyclerAdapter mAdapter;

    public static RecyclerJunkFragment getInstance() {
        return new RecyclerJunkFragment();
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

        ArrayList<BaseViewModel> arrayList = new ArrayList<>();

        for(int index = 0; index < 30; index++)
        {
            if(index % 5 == 0)
            {
                arrayList.add(new ViewPagerViewModel());
            }else
            {
                arrayList.add(new DefaultStringViewModel());
            }

        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        });
        OnDemandSpaceItemDecoration spaceItemDecoration = new OnDemandSpaceItemDecoration(0, 0, 0, 15);
        spaceItemDecoration.setTopPaddingEnabled(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), RecyclerView.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.addItemDecoration(spaceItemDecoration);

        mAdapter = new JunkRecyclerAdapter((BaseActivity)getActivity(), arrayList);
        mAdapter.setAdapterListener(this);

        mRecyclerView.setAdapter(mAdapter);
    }


    //  -------------------------------------
    //  ----- ADAPTER IMPLEMENTATIONS  ------
    //  -------------------------------------

    @Override
    public void onItemDeleted(int adapterPosition) {
        if(adapterPosition != RecyclerView.NO_POSITION) {
            mAdapter.deleteItemAt(adapterPosition);
        }
    }

    @Override
    public void onAddItem(int adapterPosition) {
        if(adapterPosition != RecyclerView.NO_POSITION) {
            DefaultStringViewModel viewModel = new DefaultStringViewModel();
            mAdapter.getItems().add(adapterPosition, viewModel);
            mAdapter.notifyItemInserted(adapterPosition);
        }

    }
}
