package sha.com.ind.labapp.dump.fragments;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import sha.com.ind.labapp.BuildConfig;
import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.misc.CustomFileProvider;

/**
 * Created by sree on 5/05/17.
 */

public class FilesFragment extends BaseFragment {

    public static final String TAG = FilesFragment.class.getSimpleName();

    @BindView(R.id.ll_parent)
    LinearLayout parentLL;

    ShareActionProvider mShareActionProvider;

    Intent mShareIntent;

    public static FilesFragment newInstance() {

        Bundle args = new Bundle();

        FilesFragment fragment = new FilesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareShareIntentAtt2("image/png");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
        MenuItem shareMenuItem  = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);
        mShareActionProvider.setShareIntent(mShareIntent);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                return false;
        }

        return super.onOptionsItemSelected(item);

    }

    private void prepareShareIntent(String contentType) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(contentType);


        try {
            Uri contentUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID+".fileprovider", getFile());
//            Uri contentUri = Uri.fromFile(getFile());
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            updateGrantPermissions(shareIntent, contentUri);
//            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

//            shareIntent = ShareCompat.IntentBuilder.from(getActivity())
//                    .setType(contentType)
//                    .setStream(contentUri)
//                    .getIntent();

            shareIntent.setData(contentUri);
//            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mShareIntent = shareIntent;
    }

    private void prepareShareIntentAtt2(String contentType) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            Uri contentUri = CustomFileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID+".fileprovider", getFile());
//            Uri contentUri = Uri.fromFile(getFile());
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            updateGrantPermissions(shareIntent, contentUri);
            shareIntent.setType(contentType);

            mShareIntent = shareIntent;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void updateGrantPermissions(Intent imageCaptureIntent, Uri imageCaptureFileProviderUri)
    {
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
//            imageCaptureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
//        else if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN) {

//            ClipData clip=
//                    ClipData.newUri(getActivity().getContentResolver(), "Receipt", imageCaptureFileProviderUri);

            String mimeTypes[] = {"image/png"};
            ClipDescription clipDescription = new ClipDescription(null , mimeTypes);
            ClipData clip = new ClipData(clipDescription, new ClipData.Item(imageCaptureFileProviderUri));

            imageCaptureIntent.setClipData(clip);
            imageCaptureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
    }

    //  WORKING ----
//
//    private void updateGrantPermissions(Intent imageCaptureIntent, Uri imageCaptureFileProviderUri)
//    {
//            String mimeTypes[] = {"image/png"};
//            ClipDescription clipDescription = new ClipDescription(null , mimeTypes);
//            ClipData clip = new ClipData(clipDescription, new ClipData.Item(imageCaptureFileProviderUri));
//
//            imageCaptureIntent.setClipData(clip);
//            imageCaptureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//    }

    private File getFile() throws IOException{
//        File receiptsPath = new File(ContextCompat.getExternalFilesDirs(getActivity(), null)[0], "receipts");
//        File receiptsPath = new File(getContext().getFilesDir(), "receipts");
        File receiptsPath = new File(Environment.getExternalStorageDirectory(), "receipts");
        receiptsPath.mkdir();
        String filename = "receipt.png";
        File file = new File(receiptsPath, filename);
        file.createNewFile();
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(file);
            save(output);
            output.flush();

            Log.d("saved receipt to ",  file.getAbsolutePath());
        } catch (IOException ex) {
            Log.e("error saving receipt", ex.toString());
            throw ex;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ex) {
                    Log.d("error closing receipt", ex.toString());
                    throw ex;
                }
            }
        }

        return file;
    }

    protected void save(OutputStream outputStream) throws IOException {
//        Bitmap bitmap = getBitmapFromView(parentLL);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.hand);

        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
}
