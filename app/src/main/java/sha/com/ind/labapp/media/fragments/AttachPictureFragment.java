package sha.com.ind.labapp.media.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;

import java.io.File;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseMediaFragment;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class AttachPictureFragment extends BaseMediaFragment {

    public static final String TAG = AttachPictureFragment.class.getSimpleName();
    private ImageView mPictureIV;
    private Button mAddPictureBtn;

    private PopupMenu mPopupMenu;

    public static AttachPictureFragment getInstance()
    {
        return new AttachPictureFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shouldScaleImage = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_attach_media, null);

        mPictureIV = (ImageView)view.findViewById(R.id.iv_attach_pic);
        mAddPictureBtn = (Button) view.findViewById(R.id.btn_upload_photo);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init()
    {

        mAddPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
    }


    /**
     * Show the popup to pick the photo
     */
    private void showPopupWindow()
    {
        if(mPopupMenu == null)
        {
            mPopupMenu = new PopupMenu(getContext(), mAddPictureBtn);
            mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_pick_photo: {
                            reqPermissionsAndopenImagePickerIntent();
                            return true;
                        }
                        case R.id.menu_item_take_photo: {
                            requestPermissionsAndDispatchPictureIntent();
                            return true;
                        }
                        default:
                            return false;
                    }
                }
            });
            mPopupMenu.inflate(R.menu.menu_add_pic);
        }

        mPopupMenu.show();
    }

    @Override
    protected void postImageSelected(String mediaFilePath) {

        Glide.with(getActivity())
                .load(new File(mediaFilePath))
                .asBitmap()
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_edit)
                .into(mPictureIV);


        File file = new File(mediaFilePath);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "image/*");
//        startActivity(intent);

    }
}
