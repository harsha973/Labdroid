package sha.com.ind.labapp.dump;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;

public class HexDrawable extends Drawable {
 
    private Path hexagonPath;
    private float mWidth, mHeight;
    private int mBackgroundColor;
    private int mStrokeColor;
    private int mStrokeWidth;
 
    public HexDrawable(){ 
        init(); 
    } 
 
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    } 
 
    public void setStrokeWidth(int width) {
        mStrokeWidth = width;
    } 
 
    public void setStrokeColor(int color) {
        mStrokeColor = color;
    } 
 
    @Override 
    public int getIntrinsicHeight() { 
        return 60; 
    } 
 
    @Override 
    public int getIntrinsicWidth() { 
        return 60; 
    } 
 
    private void init() { 
        hexagonPath = new Path();
        mBackgroundColor = Color.BLUE;
        mStrokeColor = Color.GREEN;
        mStrokeWidth = 4;
    } 
 
    private void calculatePath() { 
        float p = mStrokeWidth / 2;
        float w = mWidth - 2 * p;
        float h = mHeight - 2 * p;
        float r = h / 2;
        float a = (float) (r / Math.sqrt(3));
        PointF X = new PointF(p + a + r / 2, p);
        PointF Y = new PointF(p + a + r , p);
        PointF A = new PointF(p + a, p + 0f);
        PointF B = new PointF(p + 0f, p + r);
        PointF C = new PointF(p + a, p + 2 * r);
        PointF D = new PointF(p + w - a, p + 2 * r);
        PointF E = new PointF(p + w, p + r);
        PointF F = new PointF(p + w - a, p + 0);
        hexagonPath.moveTo(Y.x, Y.y);
        hexagonPath.lineTo(A.x, A.y);
        hexagonPath.lineTo(B.x, B.y);
        hexagonPath.lineTo(C.x, C.y);
        hexagonPath.lineTo(D.x, D.y);
        hexagonPath.lineTo(E.x, E.y);
        hexagonPath.lineTo(F.x, F.y);
        hexagonPath.lineTo(X.x, X.y);
    } 
 
    @Override 
    protected void onBoundsChange(Rect bounds) {
        mWidth = bounds.width();
        mHeight = bounds.height();
        calculatePath(); 
    } 
 
    @Override 
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(mStrokeColor);                    // set the color
        paint.setStrokeWidth(mStrokeWidth);               // set the size
        paint.setDither(true);                    // set the dither to true
        paint.setStyle(Paint.Style.STROKE);       // set to STOKE
        paint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        paint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        paint.setPathEffect(new CornerPathEffect(mStrokeWidth));   // set the path effect when they join.
        paint.setAntiAlias(true);
        canvas.drawPath(hexagonPath, paint);
        canvas.clipPath(hexagonPath, Region.Op.INTERSECT);
        canvas.drawColor(mBackgroundColor);
        canvas.drawPath(hexagonPath, paint);
        canvas.save();
    } 
 
    @Override 
    public void setAlpha(int alpha) {
 
    } 
 
    @Override 
    public void setColorFilter(ColorFilter colorFilter) {
 
    } 
 
    @Override 
    public int getOpacity() { 
        return 0; 
    } 
} 