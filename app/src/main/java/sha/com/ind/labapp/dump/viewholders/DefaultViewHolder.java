package sha.com.ind.labapp.dump.viewholders;

import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseRecylerViewHolder;
import sha.com.ind.labapp.dump.adapters.JunkRecyclerAdapter.RecyclerAdapterListener;
import sha.com.ind.labapp.dump.models.DefaultStringViewModel;

/**
 * Created by sreepolavarapu on 16/06/16.
 */
public class DefaultViewHolder extends BaseRecylerViewHolder {

    @BindView(R.id.tv_list_item_recyler_default)
    TextView mNameTextView;

    private RecyclerAdapterListener mAdapterListener;

    public DefaultViewHolder(ViewGroup parent, RecyclerAdapterListener listener) {
        super( parent, R.layout.list_item_recyler_default);
        ButterKnife.bind(this, itemView);
        mAdapterListener = listener;
    }

    @OnClick(R.id.ib_remove_recycler_item)
    void onDeleteClicked() {
        mAdapterListener.onItemDeleted(getAdapterPosition());
    }

    @OnClick(R.id.ib_add_recycler_item)
    void onAddClicked() {
        mAdapterListener.onAddItem(getAdapterPosition());
    }

    public void bind(DefaultStringViewModel dataModel){
        mNameTextView.setText(dataModel.getText());
    }

    public static DefaultViewHolder newInstance(ViewGroup parent, RecyclerAdapterListener listener) {
        return new DefaultViewHolder(parent, listener);
    }
}
