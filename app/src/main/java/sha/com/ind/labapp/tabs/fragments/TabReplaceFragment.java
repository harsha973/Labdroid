package sha.com.ind.labapp.tabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.utils.Constants;
import sha.com.ind.labapp.utils.GlideUtils;

/**
 * Created by sreepolavarapu on 21/06/16.
 */
public class TabReplaceFragment extends BaseFragment {

    public static TabReplaceFragment getInstance()
    {
        return new TabReplaceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_replace_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView happyIV = (ImageView)view.findViewById(R.id.iv_happy);

        GlideUtils.loadWithGlideASGIF(getContext(),
                Constants.GIF_URL_HAPPY,
                happyIV,
                R.mipmap.placeholder_user);

    }
}
