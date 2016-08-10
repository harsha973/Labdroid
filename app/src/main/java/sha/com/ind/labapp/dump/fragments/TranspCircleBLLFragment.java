package sha.com.ind.labapp.dump.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseFragment;
import sha.com.ind.labapp.custom.components.TranspBGLL;

/**
 * Created by sreepolavarapu on 9/08/16.
 *
 * TODO - Make it look good
 */
public class TranspCircleBLLFragment extends BaseFragment implements View.OnClickListener{

    public static final String TAG = TranspCircleBLLFragment.class.getSimpleName();

    private TranspBGLL mTraspBGLL;
    private Button mDummyBtn;
    private Button mRectBtn;

    public static TranspCircleBLLFragment newInstance() {

        Bundle args = new Bundle();

        TranspCircleBLLFragment fragment = new TranspCircleBLLFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transp_bg, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTraspBGLL = (TranspBGLL) view.findViewById(R.id.ll_transp_bg);
        mDummyBtn = (Button) view.findViewById(R.id.btn_dummy);
        mRectBtn = (Button) view.findViewById(R.id.btn_choose_pic);

        mDummyBtn.setOnClickListener(this);
        mRectBtn.setOnClickListener(this);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int centerX = (int) mDummyBtn.getX() + mDummyBtn.getWidth()  / 2;
                int centerY = (int) mDummyBtn.getY() + mDummyBtn.getHeight() / 2;
                mTraspBGLL.setCircleCenter(centerX, centerY, mDummyBtn.getWidth());

                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setTitle(R.string.crop_BG);
    }

    public void rect()
    {
        mTraspBGLL.setRect(
                new Rect((int)mRectBtn.getX(),
                        (int)mRectBtn.getY(),
                        (int)mRectBtn.getX() + mRectBtn.getWidth(),
                        (int)mRectBtn.getY() + mRectBtn.getHeight()));
    }

    public  void circle()
    {
        int centerX = (int) mDummyBtn.getX() + mDummyBtn.getWidth()  / 2;
        int centerY = (int) mDummyBtn.getY() + mDummyBtn.getHeight() / 2;
        mTraspBGLL.setCircleCenter(centerX, centerY, mDummyBtn.getWidth());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_dummy:
                circle();
                break;
            case R.id.btn_choose_pic:
                rect();
                break;
        }
    }

}
