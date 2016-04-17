package sha.com.ind.labapp.customcomponents.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.custom.components.CountDownTimerButton;
import sha.com.ind.labapp.utils.DateUtils;

/**
 * Created by sreepolavarapu on 12/03/16.
 */
public class CoundownTimerButtonFragment extends BaseFragment {

    private CountDownTimerButton mCoundowntimerBtn;

    public static CoundownTimerButtonFragment getInstance()
    {
        return new CoundownTimerButtonFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_coundown_timer_button, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCoundowntimerBtn = (CountDownTimerButton) view.findViewById(R.id.countdowntimer);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final int millisInterval = 300;
        final int totalMinutes = 1;
        final long totalMillis = totalMinutes * DateUtils.MILLIS_PER_MINUTE;

        // Countdown timer for updating the remaining time
        CountDownTimer timer = new CountDownTimer(totalMillis, millisInterval) {

            @Override
            public void onTick(long millisUntilFinished) {

                long millisCompleted = totalMillis - millisUntilFinished;
                float percent = ((millisCompleted ) / (float)(totalMillis)) * 100;
                mCoundowntimerBtn.setPercent((int) percent);


                String formattedTime = DateUtils.formatRemainingTimeToHHMMSS(millisUntilFinished);
                if(formattedTime != null)
                {
                    formattedTime = String.format(getString(R.string.time_remaining), formattedTime);
                    mCoundowntimerBtn.setText(formattedTime);
                }
            }

            @Override
            public void onFinish() {

            }

        };
        timer.start();
    }
}
