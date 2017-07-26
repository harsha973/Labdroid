package sha.com.ind.labapp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by sreepolavarapu on 16/06/16.
 */
public class BaseRecylerViewHolder extends RecyclerView.ViewHolder {

    public BaseRecylerViewHolder( ViewGroup parent,  int layoutResID) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutResID, parent, false));
    }
}
