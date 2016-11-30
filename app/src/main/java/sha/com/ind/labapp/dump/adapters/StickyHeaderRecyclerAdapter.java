package sha.com.ind.labapp.dump.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.dump.models.BaseItemViewModel;
import sha.com.ind.labapp.dump.models.NamesModel;
import sha.com.ind.labapp.dump.models.RecyclerHeaderModel;
import sha.com.ind.labapp.dump.models.SectionModel;

/**
 * Created by sreepolavarapu on 29/11/16.
 */

public class StickyHeaderRecyclerAdapter extends SectioningAdapter {

    private final int VIEW_TYPE_RECYCLER_HEADER = 1;
    private final int VIEW_TYPE_RECYCLER_DEFAULT = 2;

    private ArrayList<SectionModel> mSectionModelArrayList;

    public StickyHeaderRecyclerAdapter()
    {
        mSectionModelArrayList = new ArrayList<>();
    }

    public void updateData(ArrayList<SectionModel> sectionModelAL)
    {
        mSectionModelArrayList = sectionModelAL;
        notifyAllSectionsDataSetChanged();
    }

    private static class ItemViewHolder extends SectioningAdapter.ItemViewHolder{

        private TextView titleTV;

        ItemViewHolder(View itemView)
        {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.tv_sub_item);
        }

        public void setText(String text)
        {
            titleTV.setText(text);
        }
    }

    private static class RecyclerHeaderViewHolder extends SectioningAdapter.ItemViewHolder{

        private TextView titleTV;

        RecyclerHeaderViewHolder(View itemView)
        {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.tv_sub_item);
        }

        public void setText(String text)
        {
            titleTV.setText(text);
        }
    }

    private static class SectionViewHolder extends SectioningAdapter.HeaderViewHolder{

        private TextView titleTV;
        SectionViewHolder(View itemView)
        {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.tv_section);
        }

        public void setText(String text)
        {
            titleTV.setText(text);
        }
    }

    @Override
    public int getSectionItemUserType(int sectionIndex, int itemIndex) {
        if( getItem(sectionIndex, itemIndex) instanceof RecyclerHeaderModel)
        {
            return VIEW_TYPE_RECYCLER_HEADER;
        }else
        {
            return VIEW_TYPE_RECYCLER_DEFAULT;
        }
    }

    private BaseItemViewModel getItem(int sectionIndex, int itemIndex)
    {
        return mSectionModelArrayList.get(sectionIndex).getSectionSubListNames().get(itemIndex);
    }

    @Override
    public int getNumberOfSections() {
        return mSectionModelArrayList.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return mSectionModelArrayList.get(sectionIndex).getSectionSubListNames().size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        switch (getSectionItemUserType(sectionIndex, 0))
        {
            case VIEW_TYPE_RECYCLER_HEADER:
                return false;
            default:
                return true;
        }
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerUserType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_section, parent, false);
        return new SectionViewHolder(v);
    }

    @Override
    public SectioningAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemUserType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_sub_title, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemUserType) {
        String name = "";
        switch (itemUserType)
        {
            case VIEW_TYPE_RECYCLER_DEFAULT:
                name = ((NamesModel)getItem(sectionIndex, itemIndex)).getName();
                break;
            case VIEW_TYPE_RECYCLER_HEADER:
                name = ((RecyclerHeaderModel)getItem(sectionIndex, itemIndex)).getHeaderText();
                break;
        }
        ((ItemViewHolder)viewHolder).setText(name);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int sectionIndex, int headerUserType) {
        String section = mSectionModelArrayList.get(sectionIndex).getSectionHeaderName();
        ((SectionViewHolder)viewHolder).setText(section);
    }
}
