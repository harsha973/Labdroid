package sha.com.ind.labapp.manager;

import android.content.Context;
import android.content.Intent;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class IntentManager {

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
}
