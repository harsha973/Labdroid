package sha.com.ind.labapp.media;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.customcomponents.fragments.CustomComponentsFragment;
import sha.com.ind.labapp.media.fragments.AttachPictureFragment;


/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class MediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_generic);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AttachPictureFragment.getInstance())
                .commit();
    }
}
