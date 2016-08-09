package sha.com.ind.labapp.tabs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.utils.GeneralUtils;

// Instances of this class are fragments representing a single
// object in our collection. 
public class DemoTabFragment extends BaseFragment {

    public static final String ARG_OBJECT = "object";

    private int mValueTosShow;

    public static DemoTabFragment getInstance(int value)
    {
        DemoTabFragment demoObjFragment = new DemoTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_OBJECT, value);
        demoObjFragment.setArguments(bundle);
        return demoObjFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle  = getArguments() == null ? savedInstanceState : getArguments();
        mValueTosShow = bundle.getInt(ARG_OBJECT);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_OBJECT, mValueTosShow);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated 
        // properly. 
        View rootView = inflater.inflate(
                R.layout.fragment_demo_object, container, false);

        ((TextView) rootView.findViewById(R.id.tv_demo_obj)).setText(String.valueOf(mValueTosShow));
        rootView.setBackgroundColor(GeneralUtils.getRandomColor());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}