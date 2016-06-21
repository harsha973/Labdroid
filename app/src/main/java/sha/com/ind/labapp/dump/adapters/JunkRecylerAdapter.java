package sha.com.ind.labapp.dump.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.dump.models.DefaultStringModel;
import sha.com.ind.labapp.dump.models.ViewPagerModel;
import sha.com.ind.labapp.dump.viewholders.DefaultViewHolder;
import sha.com.ind.labapp.dump.viewholders.ViewPagerViewHolder;

/**
 * Created by sreepolavarapu on 15/06/16.
 */
public class JunkRecylerAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_PAGER = 0;
    private static final int VIEW_TYPE_DEFAULT = 1;

    private ArrayList<Object> mRecylerList;
    private BaseActivity mContext;
    private LayoutInflater inflater;

    public JunkRecylerAdapter(BaseActivity context, ArrayList<Object> recylerList)
    {
        mRecylerList = recylerList;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {

        Object object = mRecylerList.get(position);

        if(object instanceof ViewPagerModel)
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
                return new ViewPagerViewHolder(mContext, parent, R.layout.list_item_view_pager);
            default:
                return new DefaultViewHolder(mContext, parent, R.layout.list_item_recyler_default);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DefaultViewHolder)
        {
            DefaultStringModel stringModel = (DefaultStringModel)getItem(position);
            ((DefaultViewHolder) holder).getNameTextView().setText(stringModel.getText());
        }else
        {
            ViewPagerModel viewPagerModel = (ViewPagerModel)getItem(position);
            ViewPager viewPager = ((ViewPagerViewHolder)holder).getViewPager();
            viewPager.setClipToPadding(false);
            viewPager.setPadding(50,0,50,0);

            viewPager.setAdapter(new ViewPagerAdapter(viewPagerModel));
        }
    }

    @Override
    public int getItemCount() {
        return mRecylerList.size();
    }

    private Object getItem(int position)
    {
        return mRecylerList.get(position);
    }

    private class ViewPagerAdapter extends PagerAdapter
    {
        private ViewPagerModel mViewPagerModel;

        private ViewPagerAdapter(ViewPagerModel model)
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
}
