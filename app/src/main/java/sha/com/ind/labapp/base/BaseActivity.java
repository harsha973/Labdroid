package sha.com.ind.labapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.manager.ActivityManagerUtils;
import sha.com.ind.labapp.utils.Constants;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Used as an exit navigation
     * This should be one of the {@link android.support.annotation.IntDef} in {@link ActivityManagerUtils.ExitAnimation}.
     * TYPE of animation is played in onDestroy() of the Activity.
     */
    private @ActivityManagerUtils.ExitAnimation int mExitAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();

        if(bundle != null)
        {
            //noinspection WrongConstant
            mExitAnim = bundle.getInt(Constants.KEY_ACTIVITY_EXIT_ANIMATION, ActivityManagerUtils.ExitAnimation.NONE);
        }
    }

    /**
     * Helper to initalise the toolbar and sets it to action bar
     * @param titleResID    Resource id of the string
     */
    protected void setupActionBar(@StringRes int titleResID)
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            setActionBarTitle(titleResID);
        }
    }

    /**
     * Helper to set title
     * @param titleResID    ResourceID of string
     */
    protected  void setActionBarTitle(@StringRes int titleResID)
    {
       setActionBarTitle(getString(titleResID));
    }

    /**
     * Helper to set title
     * @param title    title of the activity
     */
    protected  void setActionBarTitle(String title)
    {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setTitle(title);
        }
    }

    /**
     * Enables back button
     */
    protected void enableDisplayHomeasUp()
    {
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        //	Get the Top fragment and check if the fragment handles the back pressed event.
        Fragment fragment = getCurrentFragment();

        if( fragment != null &&
                fragment instanceof BaseFragment)
        {
            boolean isBackPressHandledByFragment = ((BaseFragment)fragment).onBackPressed();

            if(isBackPressHandledByFragment)
            {
                //	Do not handle back press if it i already handled in fragment.
                return;
            }
        }

        //	Fragment does not handle back pressed if you are here.
        super.onBackPressed();

        switch (mExitAnim)
        {
            case ActivityManagerUtils.ExitAnimation.SLIDE_OUT_RIGHT:
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case ActivityManagerUtils.ExitAnimation.NONE:
            default:
                break;
        }
    }

    /**
     *
     * @return The current fragment attached to id {@link R.id#fragment_container}
     *
     * <p><b>BIG  NOTE: </b></p> This considers our activity is using {@link R.id#fragment_container}
     */
    private Fragment getCurrentFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.findFragmentById(R.id.fragment_container);
    }
}
