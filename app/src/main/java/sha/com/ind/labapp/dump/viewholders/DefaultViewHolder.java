package sha.com.ind.labapp.dump.viewholders;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseRecylerViewHolder;

/**
 * Created by sreepolavarapu on 16/06/16.
 */
public class DefaultViewHolder extends BaseRecylerViewHolder {

    private TextView mNameTextView;

    public DefaultViewHolder(Context context, ViewGroup parent, int layoutResID) {
        super(context, parent, layoutResID);
        mNameTextView = (TextView) itemView.findViewById(R.id.tv_list_item_recyler_default);
    }

    public TextView getNameTextView() {
        return mNameTextView;
    }
}
