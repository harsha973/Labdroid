package sha.com.ind.labapp.base;

import android.app.Application;
import android.util.Log;

/**
 * Created by sreepolavarapu on 10/12/15.
 */
public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "Low memory kicked");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        Log.d(TAG, "Trim memory Level : "+level);

    }
}
