package sha.com.ind.labapp.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.utils.Constants;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class ActivityManagerUtils {

    /**
     * Starts Activity by creating an intent
     * @param context
     * @param clazz
     */
    public static void startActivity(Context context, Class clazz)
    {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    /**
     * Starts the Intent with the animation specified.
     * @param context
     * 				The context from which it is being called
     * @param clazz
     * 				Activity Class to start
     * @param enterAnimation
     * 				{@link EnterAnimation} for {@link sha.com.ind.labapp.base.BaseActivity} to enter.
     * 				Can specify {@link EnterAnimation#NONE}
     */
    public static void startActivityWithExtras(Context context,
                                     Class clazz,
                                     Bundle bundle,
                                     @EnterAnimation int enterAnimation,
                                     @ExitAnimation int exitAnim)
    {
        bundle.putInt(Constants.KEY_ACTIVITY_EXIT_ANIMATION, exitAnim);

        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);

        startActivityWithAnim(context, intent, enterAnimation);
    }

    /**
     * Starts the Intent with the animation specified.
     * @param context
     * 				The context from which it is being called
     * @param clazz
     * 				Activity Class to start
     * @param enterAnimation
     * 				{@link EnterAnimation} for {@link sha.com.ind.labapp.base.BaseActivity} to enter.
     * 				Can specify {@link EnterAnimation#NONE}
     */
    public static void startActivity(Context context,
                                     Class clazz,
                                     @EnterAnimation int enterAnimation,
                                     @ExitAnimation int exitAnim)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_ACTIVITY_EXIT_ANIMATION, exitAnim);

        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);

        startActivityWithAnim(context, intent, enterAnimation);
    }

    /**
     * Starts the Intent with the animation specified.
     * @param context
     * 				The context from which it is being called
     * @param intent
     * 				Intent used to start ativity
     * @param enterAnimation
     * 				{@link EnterAnimation} for {@link BaseActivity} to enter. Can specify {@link EnterAnimation#NONE}
     */
    private static void startActivityWithAnim(Context context, Intent intent, @EnterAnimation int enterAnimation)
    {
        context.startActivity(intent);

        if(context instanceof BaseActivity)
        {
            BaseActivity activity = (BaseActivity) context;
            switch (enterAnimation)
            {
                case EnterAnimation.SLIDE_IN_RIGHT:
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case EnterAnimation.FADE_IN:
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
//                case EnterAnimation.ZOOM_IN:
//                    activity.overridePendingTransition(R.anim.zoom_in, android.R.anim.fade_out);
//                    break;
                case EnterAnimation.NONE:
                    break;
            }
        }

    }

    /*
     * Inner classes, interfaces, enums
     */
    @IntDef( {EnterAnimation.SLIDE_IN_RIGHT, EnterAnimation.FADE_IN, EnterAnimation.NONE/**, EnterAnimation.ZOOM_IN*/} )
    @Retention(RetentionPolicy.SOURCE)
    public @interface EnterAnimation {

        int NONE = 0;
        int FADE_IN = 1;
        int SLIDE_IN_RIGHT = 2;
//        int ZOOM_IN = 3;
    }

    @IntDef( {ExitAnimation.SLIDE_OUT_RIGHT, ExitAnimation.NONE/**, ExitAnimation.ZOOM_OUT*/} )
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExitAnimation{

        int NONE = 0;
        int SLIDE_OUT_RIGHT = 1;
//        int ZOOM_OUT = 2;
    }
}
