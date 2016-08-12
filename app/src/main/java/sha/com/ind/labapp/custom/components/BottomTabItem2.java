package sha.com.ind.labapp.custom.components;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sha.com.ind.labapp.R;

/**
 * Created by sreepolavarapu on 10/08/16.
 */
public class BottomTabItem2 extends LinearLayout {

    private int mWidth;

    private int screenWidth;

    private  boolean isActive;
    private ImageView mTitleIV;
    private TextView mTitleTV;

    public BottomTabItem2(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BottomTabItem2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BottomTabItem2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BottomTabItem2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        isActive = false;

        if(attrs != null)
        {
            TypedArray a = context.getTheme().obtainStyledAttributes( attrs, R.styleable.BottomTabItem, defStyle , 0);

            try {
                isActive = a.getBoolean(R.styleable.BottomTabItem_isActive, false);
            } finally {
                a.recycle();
            }
        }

        addChildren();

//        initWidths();

        setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        setWillNotDraw(false);
    }

    private void addChildren()
    {
//        setPadding(0, getResources().getDimensionPixelOffset(R.dimen.padding_short),
//                0 , getResources().getDimensionPixelOffset(R.dimen.padding_10dp));

        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(VERTICAL);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

        mTitleIV = (ImageView)LayoutInflater.from(getContext()).inflate(R.layout.subtab_icon, this, false);
        mTitleTV = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.subtab_text, this, false);
        mTitleIV.setColorFilter(ContextCompat.getColor(getContext(), R.color.grey_dark));

        addView(mTitleIV);
        addView(mTitleTV);

    }

    public ImageView getTitleIV()
    {
        return mTitleIV;
    }

    public TextView getTitleTV()
    {
        return mTitleTV;
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//
//        int width;
//
//        //Measure Width
//        if (widthMode == MeasureSpec.EXACTLY &&
//                mWidth != 0) {
//            //Must be this size
//            width = this.mWidth;
//        }
//        else {
//            width = widthSize;
//        }
//
//        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
//        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, measureMode);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

//    public void setWidth(int width)
//    {
//        mWidth = width;
//
//        invalidate();
//    }

//    public void setActiveState( boolean isActive)
//    {
//        this.isActive = isActive;
//
//        initWidths();
//        initLayoutParams();
//        initBG();
//
//        invalidate();
//    }

//    private void initBG()
//    {
//        if(isActive)
//        {
//            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary));
//        }else
//        {
//            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
//        }
//    }

}
