package sha.com.ind.labapp.dump;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.makeramen.roundedimageview.RoundedImageView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.custom.transformations.CropSemiCircleGlideTransformation;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class JunkFragment extends BaseFragment implements ActivityCompat.OnRequestPermissionsResultCallback{

//    FragmentMainBinding fragmentMainBinding;

    private final int REQUEST_CAMERA = 0;

    Button camera;

    public static JunkFragment getInstance()
    {
        return new JunkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_junk, container, false);

        camera = (Button)view.findViewById(R.id.btn_camera);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestCameraPermission();
                } else {
                    Snackbar.make(getView(), "Have permission", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

//        User user = new User("Sree Harsha", "Polavarapu");
//        fragmentMainBinding = DataBindingUtil.setContentView(this.getActivity(), R.layout.fragment_list_generic);
//
//        fragmentMainBinding.setUser(user);

        glideJunk();
//        new UploadImageAsyncTask().execute();
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
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA))
        {
            Snackbar.make(getView(), "Request Permission for camera", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                        }
                    })
                    .show();;
        }
        else
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CAMERA:
                if(grantResults != null && grantResults.length > 0)
                {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        Snackbar.make(getView(), "Permission granteed",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
