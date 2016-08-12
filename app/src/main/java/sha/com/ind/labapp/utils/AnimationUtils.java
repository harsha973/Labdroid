package sha.com.ind.labapp.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.media.Image;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by sreepolavarapu on 21/06/16.
 */
public class AnimationUtils {

    /**
     * Page Transformer animation for Viewpager
     */
    public static class ZoomOutPageTransformer implements ViewPager.PageTransformer
    {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position)
        {
            int pageWidth = view.getWidth(); int pageHeight = view.getHeight();

            if (position < -1) // [-Infinity,-1)
            {
                // This page is way off-screen to the left.
                view.setAlpha(0);
            }
            else if (position <= 1) // [-1,1]
            {
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;

                if (position < 0)
                {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                }
                else
                {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor); view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            }
            else // (1,+Infinity]
            {
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    /**
     * Updates the padding top for the view in the specified amount of duration.
     * @param view  View to update
     * @param startPadding  Start value of the padding top
     * @param endPadding    end value of the padding top
     * @param durationToAnimate Duration
     */
    public static void paddingTopValueAnimator(final View view, int startPadding, int endPadding, int durationToAnimate)
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startPadding, endPadding);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setPadding(view.getPaddingStart(), (Integer)valueAnimator.getAnimatedValue(),
                        view.getPaddingEnd(), view.getPaddingBottom());
            }
        });

        valueAnimator.setDuration(durationToAnimate);
        valueAnimator.start();
    }


    /**
     * Updates the padding top for the view in the specified amount of duration.
     * @param view  View to update
     * @param start  Start value of the padding top
     * @param end    end value of the padding top
     * @param durationToAnimate Duration
     */
    public static void alphaValueAnimator(final View view, float start, float end, int durationToAnimate)
    {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setAlpha( (Float) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.setDuration(durationToAnimate);
        valueAnimator.start();
    }


    /**
     * Sets color filter for the image view
     * @param view  Image view to change the color filter
     * @param start Start value
     * @param end   End color value
     * @param durationToAnimate time to animate
     */
    public static void colorFilterValueAnimator( final ImageView view,
                                                 int start, int end, int durationToAnimate)
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setColorFilter( (Integer) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.setDuration(durationToAnimate);
        valueAnimator.start();
    }

    public static void widthValueAnimator(final View view, int startWidth, int endWidth, int duration)
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startWidth, endWidth);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.width = (Integer)valueAnimator.getAnimatedValue();
                view.setLayoutParams(layoutParams);
            }
        });

        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


}
