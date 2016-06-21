package sha.com.ind.labapp.customcomponents.fragments;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.utils.ComponentUtils;
import sha.com.ind.labapp.utils.GlideUtils;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class FBStyleProfilePicWithIntialsFragment extends BaseFragment {

    public static final String TAG = FBStyleProfilePicWithIntialsFragment.class.getSimpleName();
    private ImageView mLeftIV;
    private ImageView mTopRightIV;
    private ImageView mBottomRightIV;

    private int textSize;

    public static FBStyleProfilePicWithIntialsFragment getInstance()
    {
        return new FBStyleProfilePicWithIntialsFragment();
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
        textSize = (int) getActivity().getResources().getDimension(R.dimen.font_size_xlarge);

        String kiwiurl =   "http://i.imgur.com/6v2swdT.jpg";
        String caturl = ""; //"http://i.imgur.com/O5I8T9h.jpg";
        String dogurl = ""; //"http://i.imgur.com/Ci07mND.jpg";

        Drawable leftErrorPlaceHolder = getPlaceHolderLeft(ComponentUtils.getIntials("Sree Harsha"));
        Drawable topRightErrorPlaceHolder = getPlaceHolderTopRight(ComponentUtils.getIntials("Matt Dammon"));
        Drawable bottomRightErrorPlaceHolder = getPlaceHolderBottomRight(ComponentUtils.getIntials("Will Smith"));

        ColorDrawable placeholder = new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.accent));

        GlideUtils.loadWithGlideCenterCrop(getActivity(), kiwiurl, mLeftIV, leftErrorPlaceHolder, placeholder);
        GlideUtils.loadWithGlideCenterCrop(getActivity(), caturl, mTopRightIV, topRightErrorPlaceHolder, placeholder);
        GlideUtils.loadWithGlideCenterCrop(getActivity(), dogurl, mBottomRightIV, bottomRightErrorPlaceHolder, placeholder);
    }

    private Drawable getPlaceHolderLeft(String text)
    {
        int size = (int)getContext().getResources().getDimension(R.dimen.profile_pic_size);

        return ComponentUtils.getPlaceHolder(getActivity(), text, size >> 1, size >> 1, textSize, size, true);

    }

    private Drawable getPlaceHolderTopRight(String text)
    {
        int size = (int)getContext().getResources().getDimension(R.dimen.profile_pic_size_half);

        return ComponentUtils.getPlaceHolder(getActivity(), text, getChordLength(size) >> 1, size * 3/4, textSize, size, false);
    }

    private Drawable getPlaceHolderBottomRight(String text)
    {
        int size = (int)getContext().getResources().getDimension(R.dimen.profile_pic_size_half);

        return ComponentUtils.getPlaceHolder(getActivity(), text, getChordLength(size) >> 1, size / 2, textSize, size, false);
    }


    private int getChordLength(int diameter)
    {
        //  c = sqrt(b2 - c2)
        return (int)Math.sqrt(Math.pow( diameter , 2) - Math.pow(diameter >> 1, 2));
    }
}
