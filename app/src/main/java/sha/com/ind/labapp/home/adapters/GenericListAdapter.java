package sha.com.ind.labapp.home.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import sha.com.ind.labapp.R;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class GenericListAdapter extends ArrayAdapter<String> {

    public GenericListAdapter(Context context,  String[] objects) {
        super(context, R.layout.list_item_generic, objects);
    }
}
