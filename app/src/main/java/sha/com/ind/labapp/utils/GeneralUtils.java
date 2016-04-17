package sha.com.ind.labapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.UUID;

/**
 * Created by sreepolavarapu on 19/12/15.
 */
public class GeneralUtils {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }

    public static float spToPixel(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }

    /**
     *	Helper to check if activity is active.
     * @param activity	The context to check
     * @return	true if activity is active, else false.
     */
    public static boolean isContextActive(Activity activity){
        if(activity != null && !activity.isFinishing()){
            return true;
        }
        return false;
    }

    /**
     *	Helper to check if activity is active.
     * @param context	The context to check
     * @return	true if activity is active, else false.
     */
    public static boolean isContextActive(Context context){

        if(context != null)
        {
            if(context instanceof Activity)
            {
                return isContextActive((Activity)context);
            }

            return true;
        }

        return false;
    }

    /**
     * Helper to generate Random UUID without dashes
     * @return  32 digit UUID without dashes
     */
    public static String generateUUIDWithoutDashes()
    {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
