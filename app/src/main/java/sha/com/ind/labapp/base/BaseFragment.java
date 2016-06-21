package sha.com.ind.labapp.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import rx.subscriptions.CompositeSubscription;
import sha.com.ind.labapp.R;
import sha.com.ind.labapp.utils.GeneralUtils;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class BaseFragment extends Fragment {

    protected MaterialProgressBar mProgressBar;

    //  Register all subscriptions in this fragment to variable.
    //  This has code to unsubscribe in onDestroy()
    protected CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = (MaterialProgressBar) view.findViewById(R.id.material_circular_progress);
    }


    public void onDestroy() {

        //  Release all observers for
        if( mCompositeSubscription.hasSubscriptions() &&
                !mCompositeSubscription.isUnsubscribed())
        {
            mCompositeSubscription.unsubscribe();
        }

        super.onDestroy();
    }
    /**
     * Method to show circular loading progress. Will reference progress
     * named {R.id.material_circular_progress}
     * @param shouldBlockTouches    Will block all the UI touches if it is set to true
     */
    protected void showProgress(boolean shouldBlockTouches)
    {
        if(mProgressBar != null)
        {
            mProgressBar.setVisibility(View.VISIBLE);

            //  BLOCK TOUCHES IF NEEDED
            if(shouldBlockTouches &&
                    GeneralUtils.isContextActive(getActivity()))
            {
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }

    }

    /**
     * Method to hide circular loading progress.
     */
    protected void hideProgress()
    {
        if(mProgressBar != null)
        {
            mProgressBar.setVisibility(View.GONE);

            // UNBLOCK TOUCHES
            if(GeneralUtils.isContextActive(getActivity()))
            {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }
    }

    /**
     * Back pressed for the Fragment. Override this method in child classes of needed.
     *
     * @return  False, if it can be passed to activity to handle.
     * True, if fragment handled backpress
     *
     * <p><b>Note</b></p> Its not good to have logic in back pressed as it may lag user to navigate back.
     * But in some cases we have to handle. No other option :(
     */
    public boolean onBackPressed()
    {
        return false;
    }
}
