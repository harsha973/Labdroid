package sha.com.ind.labapp.customcomponents.fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.utils.GlideUtils;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class FBStyleProfilePicFragment extends BaseFragment {

    public static final String TAG = FBStyleProfilePicFragment.class.getSimpleName();
    private ImageView mLeftIV;
    private ImageView mTopRightIV;
    private ImageView mBottomRightIV;

    public static FBStyleProfilePicFragment getInstance()
    {
        return new FBStyleProfilePicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fb_style_profile_pic, null);

        mLeftIV = (ImageView)view.findViewById(R.id.iv_left);
        mTopRightIV = (ImageView)view.findViewById(R.id.iv_top_right);
        mBottomRightIV = (ImageView)view.findViewById(R.id.iv_bottom_right);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init()
    {
        String kiwiurl = "http://i.imgur.com/6v2swdT.jpg";//"http://i.imgur.com/DvpvklR.png";
        String caturl = "http://i.imgur.com/O5I8T9h.jpg";
        String dogurl = "http://i.imgur.com/Ci07mND.jpg";

        GlideUtils.loadWithGlideCenterCrop(getActivity(), kiwiurl, mLeftIV, R.mipmap.placeholder_user);
        GlideUtils.loadWithGlideCenterCrop(getActivity(), caturl, mTopRightIV, R.mipmap.placeholder_user);
        GlideUtils.loadWithGlideCenterCrop(getActivity(), dogurl, mBottomRightIV, R.mipmap.placeholder_user);
    }

}
