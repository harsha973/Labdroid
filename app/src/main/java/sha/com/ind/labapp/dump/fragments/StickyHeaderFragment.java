package sha.com.ind.labapp.dump.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.dump.adapters.StickyHeaderRecyclerAdapter;
import sha.com.ind.labapp.dump.models.NamesModel;
import sha.com.ind.labapp.dump.models.RecyclerHeaderModel;
import sha.com.ind.labapp.dump.models.SectionModel;

/**
 * Created by sreepolavarapu on 29/11/16.
 * Dummy fragment for sticky header experiments
 */

public class StickyHeaderFragment extends BaseFragment {

    public static String TAG = StickyHeaderFragment.class.getSimpleName();

    private StickyHeaderRecyclerAdapter mStickyHeaderRecylerAdapter;

    public static StickyHeaderFragment newInstance() {

        Bundle args = new Bundle();

        StickyHeaderFragment fragment = new StickyHeaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_junk, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStickyHeaderRecylerAdapter = new StickyHeaderRecyclerAdapter();

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_junk);
        recyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        recyclerView.setAdapter(mStickyHeaderRecylerAdapter);

        mStickyHeaderRecylerAdapter.updateData(prepareData());
    }

    public ArrayList<SectionModel> prepareData()
    {
        SectionModel recyclerHaderModel = new SectionModel("Header");
        recyclerHaderModel.addSubListName(new RecyclerHeaderModel("This is RECYCLER HEADER"));
        ArrayList<SectionModel> sectionsAL = new ArrayList<>();

        String[] names = getResources().getStringArray(R.array.names);
        ArrayList<String> namesAL = new ArrayList<>(Arrays.asList(names));

        for (String name : namesAL)
        {
            String firstChar = name.substring(0,1);

            SectionModel sectionModel = getSectionModel(sectionsAL, firstChar);
            sectionModel.addSubListName(new NamesModel(name));
        }

        sectionsAL.add(0, recyclerHaderModel);
        return sectionsAL;
    }

    private SectionModel getSectionModel(ArrayList<SectionModel> sectionsAL, final String firstChar)
    {
        Predicate<SectionModel> sectionPredicate = new Predicate<SectionModel>() {
            @Override
            public boolean apply(SectionModel input) {
                return input.getSectionHeaderName().startsWith(firstChar);
            }
        };

        Iterator<SectionModel> filteredIterator = Iterables.filter(sectionsAL, sectionPredicate).iterator();

        if(filteredIterator.hasNext())
        {
            return filteredIterator.next();
        }

        SectionModel sectionModel = new SectionModel(firstChar);
        sectionsAL.add(sectionModel);
        return sectionModel;
    }


}
