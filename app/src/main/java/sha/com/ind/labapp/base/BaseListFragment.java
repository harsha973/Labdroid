package sha.com.ind.labapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sha.com.ind.labapp.R;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class BaseListFragment extends ListFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_generic, null);
    }
}
