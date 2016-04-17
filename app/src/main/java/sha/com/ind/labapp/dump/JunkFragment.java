package sha.com.ind.labapp.dump;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.custom.components.CountDownTimerButton;
import sha.com.ind.labapp.custom.components.touchabletextview.TouchableTextView;
import sha.com.ind.labapp.custom.transformations.CropSemiCircleGlideTransformation;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class JunkFragment extends BaseFragment implements ActivityCompat.OnRequestPermissionsResultCallback{

//    FragmentMainBinding fragmentMainBinding;

    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    private static final String TAG = JunkFragment.class.getSimpleName();
    Button camera;
    Button imagechooser;

    public static JunkFragment getInstance()
    {
        return new JunkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_junk, container, false);

        camera = (Button)view.findViewById(R.id.btn_camera);
        imagechooser = (Button)view.findViewById(R.id.btn_choose_pic);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
        ((ImageView)getView().findViewById(R.id.iv_hex_drawable)).setImageDrawable(new HexDrawable());
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check for the camera permission before accessing the camera.  If the
                // permission is not granted yet, request permission.
                int rc = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                if (rc == PackageManager.PERMISSION_GRANTED) {
                    cameraImageChooserJunk();
                } else {
                    requestCameraPermission();
                }
            }
        });

        imagechooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooserJunk();
            }
        });
//        User user = new User("Sree Harsha", "Polavarapu");
//        fragmentMainBinding = DataBindingUtil.setContentView(this.getActivity(), R.layout.fragment_list_generic);
//
//        fragmentMainBinding.setUser(user);

        glideJunk();
        linkTextJunk();
//        new UploadImageAsyncTask().execute();

        customProgressJunk();
        circleProgressTimerJunk();
    }

    /**
     * Play with glide in this method
     */
    private void glideJunk()
    {
//        RoundedImageView glideIV = (RoundedImageView) getView().findViewById(R.id.iv_glide);
//
//        CropSemiCircleGlideTransformation transform1 = new CropSemiCircleGlideTransformation(getActivity());
//
//        Glide.with(getActivity()).load(R.drawable.background_border_round_rect)//"http://goo.gl/gEgYUd")
//                .placeholder(R.drawable.news_selectedairforceyellow)
////                .transform(new CircleTransform(getActivity()))
//                .bitmapTransform(transform1)
//                .into(glideIV);

//        glideIV.setBorderWidth(0.0f);

        if(getView() != null)
        {
//            Glide.get(getActivity()).clearDiskCache();
//            Glide.get(getActivity()).clearMemory();

            RoundedImageView imageView = (RoundedImageView) getView().findViewById(R.id.iv_picassio);

            CropSemiCircleGlideTransformation transform = new CropSemiCircleGlideTransformation(getActivity());

            Glide.with(getActivity())
                    .load("http://goo.gl/gEgYUd")//("http://i.imgur.com/DvpvklR.png")
                    .asBitmap()
                    .placeholder(R.mipmap.placeholder_user)
                    .transform(transform)
                    .into(imageView);
        }

//        imageView.setBorderWidth(0.0f);

    }

    /**
     * Play with custom view glide in this method
     */
    private void customGlideIV()
    {

        if(getView() != null)
        {
//            RoundedImageView imageView = (RoundedImageView) getView().findViewById(R.id.iv_group);

//            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.audio);
//            Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.liked);
//
//            bitmap1 = Bitmap.createScaledBitmap(bitmap1, 100, 100, false);
//            bitmap2 = Bitmap.createScaledBitmap(bitmap2, 100, 100, false);
//            bitmap3 = Bitmap.createScaledBitmap(bitmap3, 100, 100, false);
//
//            imageView.setImageBitmap(getCombinedBitmap(bitmap1, bitmap2, bitmap3));
//            CropSemiCircleGlideTransformation transform = new CropSemiCircleGlideTransformation(getActivity());
//
//            Glide.with(getActivity())
//                    .load("http://i.imgur.com/DvpvklR.png")
//                    .asBitmap()
//                    .placeholder(R.drawable.news_selectedairforceyellow)
//                    .transform(transform)
//                    .into(imageView);
        }

    }

    private class UploadImageAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            Glide.get(getActivity()).clearDiskCache();
            Glide.get(getActivity()).clearMemory();
            return null;
        }

        @Override
        protected void onPostExecute(Void aBoolean) {
            super.onPostExecute(aBoolean);

            customGlideIV();
        }
    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }

    public Bitmap getCombinedBitmap(Bitmap bmp1, Bitmap b2, Bitmap b3) {
        Bitmap drawnBitmap = null;

        try {
            int width = 100;
            int height = 100;

            drawnBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(drawnBitmap);
            // JUST CHANGE TO DIFFERENT Bitmaps and coordinates .
//            canvas.drawBitmap(bitmap, 0, 0, null);
//            canvas.drawBitmap(b2, 0, 0, null);
            //for more images :
            // canvas.drawBitmap(b3, 0, 0, null);
            // canvas.drawBitmap(b4, 0, 0, null);

            Paint paint = new Paint();
//            paint.setColor(Color.GRAY);
//            paint.setStrokeWidth(2);
//            paint.setStyle(Paint.Style.STROKE);
            BitmapShader shader =
                    new BitmapShader(bmp1, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            if (width != 0 || height != 0) {
                // source isn't square, move viewport to center
                Matrix matrix = new Matrix();
                matrix.setTranslate(-width, -height);
                shader.setLocalMatrix(matrix);
            }
            paint.setShader(shader);
            paint.setAntiAlias(true);

            //  Draw first bitmap
            Rect srcRect = new Rect(0, 0,
                    drawnBitmap.getWidth() , drawnBitmap.getHeight());

            Rect destRect = new Rect(-bmp1.getWidth() / 2, 0, bmp1.getWidth() / 2, bmp1.getHeight());

            canvas.drawBitmap(bmp1, srcRect, destRect, paint);

//            canvas.drawLine(drawnBitmap.getWidth()/2f, 0f, drawnBitmap.getWidth()/2f, (float) drawnBitmap.getHeight(), paint);

            //  Draw second bitmap
            destRect = new Rect(b2.getWidth()/2 + 1, 0,
                    b2.getWidth() + b2.getWidth()/2, b2.getHeight()/2);
            canvas.drawBitmap(b2, srcRect, destRect, paint);

//            canvas.drawLine(drawnBitmap.getWidth()/2f, drawnBitmap.getHeight()/2, drawnBitmap.getWidth(), (float) drawnBitmap.getHeight()/2, paint);

            //  Draw third bitmap
            srcRect = new Rect(b3.getWidth()/4, b3.getHeight()/4, b3.getWidth() * 3/4,  b3.getHeight() * 3/4);

            destRect = new Rect(b3.getWidth()/2 + 1, b3.getHeight()/2 + 1,
                    b3.getWidth(), b3.getHeight());

            canvas.drawBitmap(b3, srcRect, destRect, paint);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return drawnBitmap;
    }

    public static class CircleTransform extends BitmapTransformation {

        public CircleTransform(Context context) {
            super(context);
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName();
        }
    }

    private void requestCameraPermission()
    {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(getActivity(), permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = getActivity();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(camera, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, listener)
                .show();
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");

            cameraImageChooserJunk();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));


    }

    private void circleProgressTimerJunk()
    {
        final CountDownTimerButton view = (CountDownTimerButton)  getView().findViewById(R.id.circle_progress);

        CountDownTimer timer  = new CountDownTimer(60 * 1000 , 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                view.setPercent((int) (100 - ((millisUntilFinished/1000)* 100 /60)) );
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    private void customProgressJunk()
    {
        final ProgressBar view = (ProgressBar)  getView().findViewById(R.id.progress_offer_details);

        CountDownTimer timer  = new CountDownTimer(20 * 1000 , 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (100 - ((millisUntilFinished/1000)* 100 /60));

                if(android.os.Build.VERSION.SDK_INT >= 11){
                    // will update the "progress" propriety of seekbar until it reaches progress
                    ObjectAnimator animation = ObjectAnimator.ofInt(view, "progress", progress);
                    animation.setDuration(500); // 0.5 second
                    animation.setInterpolator(new DecelerateInterpolator());
                    animation.start();
                }else
                {
                    view.setProgress(progress);
                }
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

//    ImagePicker imagePicker;
//    private void imageChooserJunk()
//    {
//
//        View view = getView();
//        final ImageView imageChooserIV = (ImageView)view.findViewById(R.id.iv_image_chooser);
//
//        imagePicker = new ImagePicker(this);
//        imagePicker.setImagePickerCallback(
//                new ImagePickerCallback() {
//                    @Override
//                    public void onImagesChosen(List<ChosenImage> images) {
//                        // Display images
//                        String path = images.get(0).getOriginalPath();
//                        Picasso.with(getContext()).load(new File(path)).into(imageChooserIV);
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        // Do error handling
//                    }
//                }
//        );
//
//        imagePicker.pickImage();
//    }
//
//    CameraImagePicker cameraImagePicker;
//    private String outPath;
//    private void cameraImageChooserJunk()
//    {
//
//        View view = getView();
//        final ImageView imageChooserIV = (ImageView)view.findViewById(R.id.iv_image_chooser);
//
//        File file = new File(getContext().getFilesDir(), UUID.randomUUID()+"");
//
//        outPath = file.getPath();
//        Bundle bundle = new Bundle();
//        bundle.putString(MediaStore.EXTRA_OUTPUT, outPath);
//        cameraImagePicker = new CameraImagePicker(this, outPath);
////        cameraImagePicker.setExtras(bundle);
//        cameraImagePicker.setImagePickerCallback(
//                new ImagePickerCallback() {
//                    @Override
//                    public void onImagesChosen(List<ChosenImage> images) {
//                        // Display images
//                        String path = images.get(0).getOriginalPath();
//                        Picasso.with(getContext()).load(new File(path)).into(imageChooserIV);
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        // Do error handling
//                    }
//                }
//        );
//       cameraImagePicker.pickImage();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(resultCode == Activity.RESULT_OK) {
//            if(requestCode == Picker.PICK_IMAGE_DEVICE) {
//                imagePicker.submit(data);
//            }
//            else if(requestCode == Picker.PICK_IMAGE_CAMERA) {
//                cameraImagePicker.submit(data);
//            }
//        }
//    }

    private void imageChooserJunk()
    {
        EasyImage.configuration(getActivity())
                .saveInAppExternalFilesDir();

        EasyImage.openGallery(this, 0);
    }


    private void cameraImageChooserJunk()
    {

        EasyImage.configuration(getActivity())
                .saveInAppExternalFilesDir();

        EasyImage.openCamera(this, 0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
                onPhotoReturned(imageFile);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getActivity());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private void onPhotoReturned(final File photoFile) {

        View view = getView();
        ImageView imageChooserIV = (ImageView)view.findViewById(R.id.iv_image_chooser);

//        Picasso.with(getActivity())
//                .load(photoFile)
//                .fit()
//                .centerCrop()
//                .into(imageChooserIV);

        Glide.with(getContext())
                .load(photoFile)
                .centerCrop()
//                .resize(imageChooserIV.getWidth(), imageChooserIV.getHeight())
                .into(imageChooserIV);
    }

    private void linkTextJunk()
    {
        View view = getView();
        if (view != null)
        {
            TextView textView = (TextView)view.findViewById(R.id.tv_text_link);

            textView.setText(Html.fromHtml("www.youtube.com<br>tset<br>sf<br>dsf<br>sdf<br>dsfds"));
            Linkify.addLinks(textView, Linkify.ALL);
        }
    }

}
