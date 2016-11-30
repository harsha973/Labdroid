package sha.com.ind.labapp.archive.components;

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
 *
 * This was initially developed as a BottomBarItem based on gravity. But it failed while applying
 * animations. Its not well documented
 */
public class BottomTabItemTrash extends LinearLayout {

    private int mMaxWidth;
    private int mMinWidth;

    private int screenWidth;

    private  boolean isActive;
    private ImageView mTitleIV;
    private TextView mTitleTV;

    public BottomTabItemTrash(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BottomTabItemTrash(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BottomTabItemTrash(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BottomTabItemTrash(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        isActive = false;
        screenWidth = getScreenWidthInPixels(getContext());

        if(attrs != null)
        {
            TypedArray a = context.getTheme().obtainStyledAttributes( attrs, R.styleable.BottomTabItemTrash, defStyle , 0);

            try {
                isActive = a.getBoolean(R.styleable.BottomTabItemTrash_isActive, false);
            } finally {
                a.recycle();
            }
        }

        addChildren();

        initWidths();

        setWillNotDraw(false);
    }

    private void addChildren()
    {
//        setPadding(0, getResources().getDimensionPixelOffset(R.dimen.padding_short),
//                0 , getResources().getDimensionPixelOffset(R.dimen.padding_10dp));

        ViewCompat.setElevation(this,getResources().getDimensionPixelSize(R.dimen.elevation_8dp));
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
//        if(!isActive)
//        {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            return;
//        }
//
//        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
//        if (mMaxWidth > 0 && mMaxWidth < measuredWidth) {
//            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, measureMode);
//        }
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        //  TODO - If this logic doesnt work, have a look at this implementation as well
//        //  http://stackoverflow.com/a/12267248/726625
//    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        if(!isActive)
//        {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            return;
//        }
//
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//
//        int width;
//
//        //Measure Width
//        if (widthMode == MeasureSpec.EXACTLY) {
//            //Must be this size
//            width = getCalculatedWidth(widthSize);
////            width = mMaxWidth;
//
//        }
//        else if (widthMode == MeasureSpec.AT_MOST) {
//            //Can't be bigger than...
//            width = getCalculatedWidth(mMaxWidth); //Math.min(desiredWidth, widthSize);
//
//        } else {
//            //Be whatever you want
////            width = desiredWidth;
//            //Can't be bigger than...
//            width = getCalculatedWidth(mMaxWidth);
//        }
//
//        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
//        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, measureMode);
//
////        int desiredHeight = getResources().getDimensionPixelOffset(R.dimen.bbn_bottom_navigation_height);
////        heightMeasureSpec = MeasureSpec.makeMeasureSpec(desiredHeight, heightMeasureSpec);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    private int getCalculatedWidth(int originalWidth)
    {
        int eachTabWidth = (screenWidth - originalWidth)/ 5;

        if(eachTabWidth > originalWidth)
        {
            originalWidth = screenWidth/5;
//            originalWidth = eachTabWidth;
        }

        return originalWidth;
    }

    public void setActiveState( boolean isActive)
    {
        this.isActive = isActive;

        initWidths();
        initLayoutParams();
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

        mMaxWidth = getResources().getDimensionPixelSize(R.dimen.active_bottom_tab_max_width);
        mMinWidth = getResources().getDimensionPixelSize(R.dimen.active_bottom_tab_min_width);


        if(!isActive)
        {
//            mMaxWidth = (screenWidth - mMaxWidth) / 4;
            mMinWidth = getResources().getDimensionPixelSize(R.dimen.inactive_bottom_tab_min_width);

        }

//        setMinimumWidth(mMinWidth);

//        if(!isActive)
//        {
//            mMaxWidth = (screenWidth - mMaxWidth) / 4;
//            mMinWidth = getResources().getDimensionPixelSize(R.dimen.inactive_view_min_width);
//
//            setMinimumWidth(mMaxWidth);
//        }

//        else
//        {
//            mMaxWidth = getResources().getDimensionPixelSize(R.dimen.inactive_view_max_width);
//            mMinWidth = getResources().getDimensionPixelSize(R.dimen.inactive_view_min_width);
//        }

    }

    private void initLayoutParams()
    {

        LayoutParams myParams = (LayoutParams)getLayoutParams();

        int eachTabWidth = (screenWidth - mMaxWidth)/ 5;
//        if(isActive && !shouldTabsHaveEqualWidths())

        if(isActive)
        {
//            myParams.weight = 0;

//            animateWeightReduce(100, 0);

            setMinimumWidth(mMinWidth);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
//            lp.width = LayoutParams.WRAP_CONTENT;
//            lp.weight = 0;
//            setLayoutParams(lp);

            int futureWidth = screenWidth - getWidth()*4;
            int currentWidth = getWidth();

            if(futureWidth < 0 || currentWidth <= 0)
            {
                lp.width = LayoutParams.WRAP_CONTENT;
                lp.weight = 0;
                setLayoutParams(lp);
                return;
            }
            Log.d("Future width", ""+futureWidth);

            int endWeight = (int) (((float)futureWidth / currentWidth) * 100);

            ExpandAnimation anim = new ExpandAnimation(lp.weight , endWeight);
            anim.setDuration(150);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
                    lp.width = 0;
                    setLayoutParams(lp);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
//                    lp.width = LayoutParams.WRAP_CONTENT;
////                    lp.weight = 0;
//                    setLayoutParams(lp);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            startAnimation(anim);

//            setPadding(0, getResources().getDimensionPixelOffset(R.dimen.padding_short),
//                    0 , getResources().getDimensionPixelOffset(R.dimen.padding_10dp));
        }else
        {

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
            myParams.width = 0;
            myParams.weight = 100;
            setLayoutParams(lp);


//            ExpandAnimation anim = new ExpandAnimation(currentWeight , 100);
//            anim.setDuration(1500);
//            anim.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
////                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
////                    setLayoutParams(lp);
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            startAnimation(anim);


//            animateWeightChange(0, 100);

//            setPadding(0, 0, 0 , 0);
        }

//        setPadding(0, 0, 0 , 0);

    }

    private void animateWeightChange(int start, int end)
    {
        final LayoutParams myParams = (LayoutParams)getLayoutParams();

        ValueAnimator paddingAnimator = ValueAnimator.ofInt(start, end);
        paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (Integer) animation.getAnimatedValue();
                Log.d("Value Anim" , "" +val);
                myParams.weight = val;

            }

        });

        paddingAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                myParams.width = 0;

            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        paddingAnimator.setDuration(1500);
        paddingAnimator.setCurrentPlayTime(100);
        paddingAnimator.start();

    }

    private void animateWeightReduce(int start, int end)
    {
        final LayoutParams myParams = (LayoutParams)getLayoutParams();

        ValueAnimator paddingAnimator = ValueAnimator.ofInt(start, end);
        paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (Integer) animation.getAnimatedValue();
                Log.d("Value Anim" , "" +val);
                myParams.weight = val;

            }

        });

        paddingAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                myParams.width = LayoutParams.WRAP_CONTENT;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        paddingAnimator.setDuration(1500);
        paddingAnimator.start();

    }
    private boolean shouldTabsHaveEqualWidths(){
        int eachTabWidth = (screenWidth - mMaxWidth)/ 5;

        return mMaxWidth <  eachTabWidth;
    }


    private class ExpandAnimation extends Animation {

        private final float mStartWeight;
        private final float mDeltaWeight;

        public ExpandAnimation(float startWeight, float endWeight) {
            mStartWeight = startWeight;
            mDeltaWeight = endWeight - startWeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int weight = (int)(mStartWeight + (mDeltaWeight * interpolatedTime));
            Log.d("Weight", ""+weight);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
            lp.weight = weight;
            setLayoutParams(lp);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }

    }

    private class CollapseAnimation extends Animation {

        private final float mStartWeight;
        private final float mDeltaWeight;

        public CollapseAnimation(float startWeight, float endWeight) {
            mStartWeight = startWeight;
            mDeltaWeight = startWeight - endWeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int weight = (int)(mStartWeight + (mDeltaWeight * interpolatedTime));
            Log.d("Weight", ""+weight);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
            lp.weight = weight;
            setLayoutParams(lp);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    /**
     * Returns screen width.
     *
     * @param context Context to get resources and device specific display metrics
     * @return screen width
     */
    protected static int getScreenWidthInPixels(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        return (int) (displayMetrics.widthPixels / displayMetrics.density);
        return displayMetrics.widthPixels;
    }
}
