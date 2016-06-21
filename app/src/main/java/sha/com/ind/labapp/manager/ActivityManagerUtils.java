package sha.com.ind.labapp.manager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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

    /*
     * Inner classes, interfaces, enums
     */
    @IntDef( {EnterAnimation.SLIDE_IN_RIGHT, EnterAnimation.FADE_IN, EnterAnimation.NONE} )
    @Retention(RetentionPolicy.SOURCE)
    public @interface EnterAnimation {

        int NONE = 0;
        int FADE_IN = 1;
        int SLIDE_IN_RIGHT = 2;
    }

    @IntDef( {ExitAnimation.SLIDE_OUT_RIGHT, ExitAnimation.NONE} )
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExitAnimation{

        int NONE = 0;
        int SLIDE_OUT_RIGHT = 1;
    }
}
