package sha.com.ind.labapp.custom.components;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.utils.FastBlur;

/**
 * Created by sreepolavarapu on 9/08/16.
 *
 * Draw a hole in the transparent background to highlight under lying views
 */
public class TranspBGLL extends LinearLayout {

    boolean isRect;

    private Rect rect;

    private float centerX;
    private float centerY;
    private float radius;

    private Bitmap bitmap;
    private Bitmap tranpBitmap;

    public TranspBGLL(Context context) {
        super(context);
        init();
    }

    public TranspBGLL(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TranspBGLL(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setWillNotDraw(false);

        if(tranpBitmap == null)
        {

            Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getResources().getResourcePackageName(R.drawable.translucent_bg)
                    + '/' + getResources().getResourceTypeName(R.drawable.translucent_bg) + '/' + getResources().getResourceEntryName(R.drawable.translucent_bg) );

            Glide.with(getContext())
                    .load(imageUri)
                    .asBitmap()
                    .override(100, 100)
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            tranpBitmap = bitmap;
                            invalidate();
                        }
                    });
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (bitmap == null) {
            createWindowFrame();
        }
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    protected void createWindowFrame() {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//        bitmap.eraseColor(ContextCompat.getColor(getContext(), R.color.transparent50));
//        bitmap = blur(bitmap);
        Canvas osCanvas = new Canvas(bitmap);

//        RectF outerRectangle = new RectF(0, 0, getWidth(), getHeight());

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if(tranpBitmap != null)
        {
            Rect source = new Rect(0, 0, tranpBitmap.getWidth(), tranpBitmap.getHeight());
            Rect dest = new Rect(0, 0, getWidth(), getHeight());
            osCanvas.drawBitmap(tranpBitmap, source, dest, paint);
//            osCanvas.drawBitmap(tranpBitmap, 0 , 0, paint);
        }

//        paint.setColor(ContextCompat.getColor(getContext(), R.color.transparent50));
//        osCanvas.drawRect(outerRectangle, paint);

        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        if(isRect)
        {
            osCanvas.drawRect(rect, paint);
        }else
        {
            osCanvas.drawCircle(centerX, centerY, radius, paint);
        }
    }

    private Bitmap blur(View view) {
        float scaleFactor = 1;
        float radius = 20;
//        if (downScale.isChecked()) {
//            scaleFactor = 8;
//            radius = 2;
//        }

        Bitmap overlay = Bitmap.createBitmap((int) (view.getWidth()/scaleFactor),
                (int) (view.getHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        RectF outerRectangle = new RectF(0, 0, getWidth(), getHeight());
        paint.setColor(ContextCompat.getColor(getContext(), R.color.transparent50));
        canvas.drawRect(outerRectangle, paint);

//        paint.setFlags(Paint.FILTER_BITMAP_FLAG);

//        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int)radius, true);
        return overlay;
//        view.setBackground(new BitmapDrawable(getResources(), overlay));
    }

    private static final float BITMAP_SCALE = 1f;
    private static final float BLUR_RADIUS = 20f;

    public  Bitmap blur(Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(getContext());
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;

    }

//    private void blur(Bitmap bkg, View view) {
//        float scaleFactor = 1;
//        float radius = 20;
////        if (downScale.isChecked()) {
////            scaleFactor = 8;
////            radius = 2;
////        }
//
//        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()/scaleFactor),
//                (int) (view.getMeasuredHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(overlay);
//        canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
//        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
//        Paint paint = new Paint();
//        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
//        canvas.drawBitmap(bkg, 0, 0, paint);
//
//        overlay = FastBlur.doBlur(overlay, (int)radius, true);
//        view.setBackground(new BitmapDrawable(getResources(), overlay));
//    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        bitmap = null;

    }

    public void setCircleCenter(int centerX, int centerY, int diameter){

        isRect = false;

        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = diameter/2;

        bitmap = null;
        invalidate();
    }

    public void setRect(Rect rect){

        isRect = true;
        this.rect = rect;

        bitmap = null;
        invalidate();
    }
}
