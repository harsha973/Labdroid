package sha.com.ind.labapp.custom.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import sha.com.ind.labapp.R;

/**
 * Created by sreepolavarapu on 9/08/16.
 */
public class TranspBGLL extends LinearLayout {

//    private Canvas temp;
//    private Paint paint;
//    private Paint p = new Paint();
//    private Paint transparentPaint;

    boolean isRect;

    private Rect rect;

    private float centerX;
    private float centerY;
    private float radius;

    private Bitmap bitmap;

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
    }

//    private void init(){
//        temp = new Canvas(bitmap);
//        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
//        paint = new Paint();
//        paint.setColor(0xcc000000);
//        transparentPaint = new Paint();
//        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
//        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//    }
//
//
//    protected void onDraw(Canvas canvas) {
//        temp.drawRect(0, 0, temp.getWidth(), temp.getHeight(), paint);
//        temp.drawCircle(catPosition.x + radius / 2, catPosition.y + radius / 2, radius, transparentPaint);
//        canvas.drawBitmap(bitmap, 0, 0, p);
//    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (bitmap == null) {
            createWindowFrame();
        }
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        if (bitmap == null) {
//            createWindowFrame();
//        }
//        canvas.drawBitmap(bitmap, 0, 0, null);
//    }

    protected void createWindowFrame() {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas osCanvas = new Canvas(bitmap);

        RectF outerRectangle = new RectF(0, 0, getWidth(), getHeight());

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(android.R.color.transparent));
        paint.setAlpha(99);
        osCanvas.drawRect(outerRectangle, paint);

        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//        centerX = getWidth() / 2;
//        centerY = getHeight() / 2;
//        float radius = getResources().getDimensionPixelSize(R.dimen.radius);
//        radius = 100;
        if(isRect)
        {
            osCanvas.drawRect(rect, paint);
        }else
        {
            osCanvas.drawCircle(centerX, centerY, radius, paint);
        }
    }

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
