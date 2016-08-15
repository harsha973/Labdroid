package sha.com.ind.labapp.custom.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sha.com.ind.labapp.R;

/**
 * Created by sreepolavarapu on 10/08/16.
 *
 * Represents a tab in bottom tab bar. It is used along with {@link BottomBarLL}
 */
public class BottomTabItem extends LinearLayout {

    private ImageView mTitleIV;
    private TextView mTitleTV;

    public BottomTabItem(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BottomTabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BottomTabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BottomTabItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        addChildren();

        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        setWillNotDraw(false);
    }

    /**
     * Adds children (Title and Icon) to this layout.
     */
    private void addChildren()
    {
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(VERTICAL);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

        mTitleIV = (ImageView)LayoutInflater.from(getContext()).inflate(R.layout.subtab_icon, this, false);
        mTitleTV = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.subtab_text, this, false);
        mTitleIV.setColorFilter(ContextCompat.getColor(getContext(), R.color.grey_dark));

        addView(mTitleIV);
        addView(mTitleTV);
    }

    public ImageView getTitleIV()
    {
        return mTitleIV;
    }

    public TextView getTitleTV()
    {
        return mTitleTV;
    }

}
