package sha.com.ind.labapp.dump.viewholders;

import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseRecylerViewHolder;

/**
 * Created by sreepolavarapu on 16/06/16.
 */
public class ViewPagerViewHolder extends BaseRecylerViewHolder{

    private ViewPager mViewPager;

    public ViewPagerViewHolder(ViewGroup parent) {
        super(parent, R.layout.list_item_view_pager);
        mViewPager = (ViewPager)itemView.findViewById(R.id.vp_list_item);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }
}
