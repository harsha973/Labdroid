package sha.com.ind.labapp.custom.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;

import sha.com.ind.labapp.R;

public class CountDownTimerButton extends Button
{

    private boolean mIsSizeChanged;
    private Paint mPaint;
    private int mPercent ;
    private RectF mArcRect;
    Rect boundingRect = new Rect();

    private int mHeight;
    private int mCircleMargin;


    public CountDownTimerButton(Context context) {
        super(context);
        init();
    }

    public CountDownTimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CountDownTimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public CountDownTimerButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init()
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        setBackgroundResource(R.drawable.selectable_background_button_accent);
    }

    /**
     * Set the percentage of the progress
     * @param percent
     */
    public void setPercent(int percent)
    {
        mPercent = percent;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        final float startAngle = 270f;
        final float drawTo =  (mPercent * 360/100);

        if(mArcRect == null || mIsSizeChanged)
        {
            getDrawingRect(boundingRect);
            mHeight = getHeight();

            //  Left circle which is drawn a bit inside to the button
            //  Offset is the value to move inside
            mCircleMargin = (int) (mHeight * 0.1);
            //  Arc which is drawn inside with a bit of margin
            //  Offset is the value to move inside
            int mArcMargin = (int) (mHeight * 0.2);

            mArcRect = new RectF();
            mArcRect.top = boundingRect.top + mArcMargin;
            mArcRect.bottom = boundingRect.top +  mHeight - mArcMargin;
            mArcRect.left =  boundingRect.left + mArcMargin;
            mArcRect.right =   boundingRect.left + mHeight - mArcMargin;

        }

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(boundingRect.left + mHeight/2, boundingRect.top + mHeight/2, mHeight/2 - mCircleMargin, mPaint);

        mPaint.setColor(getResources().getColor(R.color.colorAccentDisabled));
        canvas.drawArc(mArcRect, startAngle, drawTo, true, mPaint);

        //  Draw a line on Y-Axis, which might help user when progress is 0.
        canvas.drawLine(mArcRect.right - (mArcRect.right - mArcRect.left)/2,
                mArcRect.top,
                mArcRect.right - (mArcRect.right - mArcRect.left)/2,
                mArcRect.bottom - (mArcRect.bottom - mArcRect.top)/2,
                mPaint);

        mIsSizeChanged = false;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mIsSizeChanged = true;
        super.onSizeChanged(w, h, oldw, oldh);
    }

}