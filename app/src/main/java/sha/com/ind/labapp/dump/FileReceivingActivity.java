package sha.com.ind.labapp.dump;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import sha.com.ind.labapp.base.BaseActivity;

/**
 * Created by sree on 9/05/17.
 */

public class FileReceivingActivity extends BaseActivity {

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
//                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
//                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    private void handleSendImage(Intent intent){

        Bundle bundle = intent.getExtras();
        Uri stream = bundle.getParcelable(Intent.EXTRA_STREAM);
        Log.d("Stream", " "+stream);
    }
}
