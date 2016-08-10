package sha.com.ind.labapp.custom.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import sha.com.ind.labapp.R;

/**
 * Created by sreepolavarapu on 10/08/16.
 */
public class BottomBarItemLL extends LinearLayout {

    private int mMaxWidth;
    private int mMinWidth;

    private  boolean isActive;

    public BottomBarItemLL(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BottomBarItemLL(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BottomBarItemLL(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BottomBarItemLL(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        isActive = false;

        if(attrs != null)
        {
            TypedArray a = context.getTheme().obtainStyledAttributes( attrs, R.styleable.BottomBarItemLL, defStyle , 0);

            try {
                isActive = a.getBoolean(R.styleable.BottomBarItemLL_isActive, false);
            } finally {
                a.recycle();
            }
        }

        initWidths();

        setWillNotDraw(false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (mMaxWidth > 0 && mMaxWidth < measuredWidth) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, measureMode);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //  TODO - If this logic doesnt work, have a look at this implementation as well
        //  http://stackoverflow.com/a/12267248/726625
    }

    public void setActiveState( boolean isActive)
    {
        this.isActive = isActive;

        initWidths();
        initBG();

        invalidate();
    }

    private void initBG()
    {
        if(isActive)
        {
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary));
        }else
        {
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        }
    }

    private void initWidths(){

        if(isActive)
        {
            mMaxWidth = getResources().getDimensionPixelSize(R.dimen.active_view_max_width);
            mMinWidth = getResources().getDimensionPixelSize(R.dimen.active_view_min_width);
        }else
        {
            mMaxWidth = getResources().getDimensionPixelSize(R.dimen.inactive_view_max_width);
            mMinWidth = getResources().getDimensionPixelSize(R.dimen.inactive_view_min_width);
        }

        setMinimumWidth(mMinWidth);
    }
}
