package sha.com.ind.labapp.dump.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.manager.ActivityManagerUtils;
import sha.com.ind.labapp.other.fingerprint.FingerprintActivity;
import sha.com.ind.labapp.utils.Constants;
import sha.com.ind.labapp.utils.SharedPrefsUtils;

/**
 * Created by sreepolavarapu on 7/02/17.
 */
public class FingerprintSettingsFragment extends BaseFragment {

    public static final String TAG = FingerprintSettingsFragment.class.getSimpleName();

    @BindView(R.id.switch_fingerprint)
    Switch fingerPrintSwitch;

    public static FingerprintSettingsFragment newInstance() {
        
        Bundle args = new Bundle();

        FingerprintSettingsFragment fragment = new FingerprintSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isChecked = SharedPrefsUtils.getBooleanPreference(getContext(), Constants.PREF_KEY_IS_FINGERPRINT_ENABLED, false);
        fingerPrintSwitch.setChecked(isChecked);
    }

    @OnCheckedChanged(R.id.switch_fingerprint)
    void onFingerPrintSwitchChanged()
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.PREF_TO_ENCRYPT, true);
        ActivityManagerUtils.startActivityWithExtras(getActivity() ,
                FingerprintActivity.class,
                bundle,
                ActivityManagerUtils.EnterAnimation.NONE,
                ActivityManagerUtils.ExitAnimation.NONE);

    }
}
