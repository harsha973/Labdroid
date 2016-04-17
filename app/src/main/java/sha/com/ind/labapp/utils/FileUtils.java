package sha.com.ind.labapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import sha.com.ind.labapp.R;

/**
 * Created by sreepolavarapu on 16/03/16.
 */
public class FileUtils {

    public static String TAG = FileUtils.class.getSimpleName();

    public static final long MINIMUM_SPACE_BYTES = 50 * 1024 * 1024;
    /**
     * Checks the storage and displays toast if device is on low storage
     * @param context   Context of this action
     * @return  true if device is on low storage
     */
    public static boolean hasLowStorageAndDisplayToast(Context context)
    {
        // Check for low storage.  If there is low storage, the native library will not be
        // downloaded, so detection will not become operational.
        IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
        boolean hasLowStorage = context.registerReceiver(null, lowstorageFilter) != null;

        if (hasLowStorage) {
            Toast.makeText(context, R.string.error_low_storage, Toast.LENGTH_LONG).show();
            Log.w(TAG, context.getString(R.string.error_low_storage));
        }

        return hasLowStorage;
    }


    /**
     * Creates the image file with the given name.
     *
     * @param context   Context
     * @param fileName  Name of the file. Mainly UUID for this App
     * @return  File constructed from the name. Creates on in internal storage if it has free space,
     * else opts for external storage. Uses Apps file Directory
     */
    public static File createImageFile(Context context, String fileName)
    {
        String imageFileName;

        if (fileName != null && !fileName.isEmpty())
        {
            imageFileName = fileName;
        }
        else
        {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            imageFileName = "JPEG_" + timeStamp + ".jpg";
        }

//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File dir = context.getFilesDir();

        if(!isFreeSpaceAvailable(dir))
        {
            dir = context.getExternalFilesDir(null);
        }

        return new File(dir, imageFileName);
    }

    /**
     * MEthod to calculate if free space is available on the specified file location.
     * This methid is used to get the space available is sufficient
     * @param file  File obhjecy
     * @return true if free space is more than {@link FileUtils#MINIMUM_SPACE_BYTES}
     */
    public static boolean isFreeSpaceAvailable(File file)
    {
        float availableSpace = spaceAvaiableinMB(file);

        return availableSpace > MINIMUM_SPACE_BYTES;
    }

    public static float spaceAvaiableinMB(File f) {
        StatFs stat = new StatFs(f.getPath());
        long bytesAvailable = (long)stat.getBlockSizeLong() * (long)stat.getAvailableBlocksLong();
        return bytesAvailable / (1024.f * 1024.f);
    }

    /**
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getFilePathFromFilesDir(Context context, String fileName)
    {
        File dir = new File(context.getFilesDir(), fileName);

        if(!dir.exists())
        {
            dir = new File(context.getExternalFilesDir(null), fileName);
        }


        if(dir.exists())
        {
            return dir.getAbsolutePath();
        }

        return null;
    }
}
