package sha.com.ind.labapp.dump.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.dump.models.BaseViewModel;
import sha.com.ind.labapp.dump.models.DefaultStringViewModel;
import sha.com.ind.labapp.dump.models.ViewPagerViewModel;
import sha.com.ind.labapp.dump.viewholders.DefaultViewHolder;
import sha.com.ind.labapp.dump.viewholders.ViewPagerViewHolder;
import sha.com.ind.labapp.utils.itemsdecorators.OnDemandSpaceItemDecoration;

/**
 * Created by sreepolavarapu on 15/06/16.
 */
public class JunkRecyclerAdapter extends RecyclerView.Adapter implements OnDemandSpaceItemDecoration.OnDemandSpaceListener {

    private static final int VIEW_TYPE_PAGER = 0;
    private static final int VIEW_TYPE_DEFAULT = 1;

    private ArrayList<BaseViewModel> mRecyclerList;
    private BaseActivity mContext;
    private LayoutInflater inflater;
    private RecyclerAdapterListener mAdapterListener;

    public JunkRecyclerAdapter(BaseActivity context, ArrayList<BaseViewModel> recyclerList)
    {
        mRecyclerList = recyclerList;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setAdapterListener(RecyclerAdapterListener listener) {
        mAdapterListener = listener;
    }

    public ArrayList<BaseViewModel> getItems() {
        return mRecyclerList;
    }

    @Override
    public int getItemViewType(int position) {

        Object object = mRecyclerList.get(position);

        if(object instanceof ViewPagerViewModel)
        {
            return VIEW_TYPE_PAGER;
        }else
        {
            return VIEW_TYPE_DEFAULT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType)
        {
            case VIEW_TYPE_PAGER:
                return new ViewPagerViewHolder(parent);
            default:
                return new DefaultViewHolder( parent, mAdapterListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DefaultViewHolder)
        {
            DefaultStringViewModel stringModel = (DefaultStringViewModel)getItem(position);
            ((DefaultViewHolder) holder).bind(stringModel);
        }else
        {
            ViewPagerViewModel viewPagerModel = (ViewPagerViewModel)getItem(position);
            ViewPager viewPager = ((ViewPagerViewHolder)holder).getViewPager();
            viewPager.setClipToPadding(false);
            viewPager.setPadding(50,0,50,0);

            viewPager.setAdapter(new ViewPagerAdapter(viewPagerModel));
        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }

    public void deleteItemAt(int position) {
        mRecyclerList.remove(position);
        notifyItemRemoved(position);
    }

    private BaseViewModel getItem(int position)
    {
        return mRecyclerList.get(position);
    }

    @Override
    public boolean shouldAddSpace(int position) {
        if(position != RecyclerView.NO_POSITION) {
            return getItemViewType(position) == VIEW_TYPE_PAGER;
        }

        return false;
    }

    private class ViewPagerAdapter extends PagerAdapter
    {
        private ViewPagerViewModel mViewPagerModel;

        private ViewPagerAdapter(ViewPagerViewModel model)
        {
            mViewPagerModel = model;
        }

        @Override
        public int getCount() {
            return mViewPagerModel.getStringsAL().size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = inflater.inflate(R.layout.view_pager_item, container, false);
            TextView pagerTextView = (TextView)view.findViewById(R.id.tv_viewpager);

            pagerTextView.setText(getItem(position));
            container.addView(view);
            return view;
        }

        private String getItem(int position)
        {
            return  mViewPagerModel.getStringsAL().get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

    public interface RecyclerAdapterListener{

        void onItemDeleted(int adapterPosition);

        void onAddItem(int adapterPosition);
    }
}
