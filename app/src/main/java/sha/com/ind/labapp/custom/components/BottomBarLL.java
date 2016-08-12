package sha.com.ind.labapp.custom.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.utils.AnimationUtils;
import sha.com.ind.labapp.utils.GeneralUtils;

/**
 * Created by sreepolavarapu on 12/08/16.
 */
public class BottomBarLL extends LinearLayout implements View.OnClickListener{

    private String TAG_I_AM_SELECTED = "TAG_I_AM_SELECTED";
    public static final int MAX_TABS = 5;
    public static final int ANIM_DURATION = 250;

    //  Everything in pixels
    private int mSelectedTabWidth;
    private int mUnSelectedTabWidth;
    private int mScreenWidth;

    //  Colors
    private int mSelectedTabForegroundColor;
    private int mUnSelectedTabForegroundColor;

    private int mSelectedTabTopPadding;
    private int mUnSelectedTabTopPadding;

    private LayoutInflater mLayoutInflater;

    public BottomBarLL(Context context) {
        super(context);
        init();
    }

    public BottomBarLL(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomBarLL(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public BottomBarLL(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init()
    {
        setWillNotDraw(false);
        ViewCompat.setElevation(this,getResources().getDimensionPixelSize(R.dimen.elevation_8dp));

        mLayoutInflater = LayoutInflater.from(getContext());

        //  Init Paddings
        mSelectedTabTopPadding = getResources().getDimensionPixelOffset(R.dimen.padding_short);
        mUnSelectedTabTopPadding = getResources().getDimensionPixelOffset(R.dimen.padding_standard);

        //  Init colors
        mSelectedTabForegroundColor = ContextCompat.getColor(getContext(), R.color.white);
//        mUnSelectedTabForegroundColor = ContextCompat.getColor(getContext(), R.color.grey_medium);
        mUnSelectedTabForegroundColor = ContextCompat.getColor(getContext(), R.color.white);

        //  Calculating widths
        mScreenWidth = GeneralUtils.getScreenWidthInPixels(getContext());
        int inActiveMinWidth = getResources().getDimensionPixelOffset(R.dimen.inactive_view_min_width);
        int activeMaxWidth =  getResources().getDimensionPixelOffset(R.dimen.active_view_max_width);

        int remainingSpace =  mScreenWidth - (inActiveMinWidth * (MAX_TABS - 1));

        //  All tabs share equal space
        if(remainingSpace > activeMaxWidth)
        {
            mSelectedTabWidth = mScreenWidth / 5;
            mUnSelectedTabWidth = mSelectedTabWidth;
        }else
        {
            mSelectedTabWidth = remainingSpace;
            mUnSelectedTabWidth = inActiveMinWidth;
        }

        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary));
    }

    /**
     * Add a new Tab to the Layout
     * @param title Title for the tab
     * @param icon  Icon for the tab
     * @param isSelected    State of the tab
     */
    public void addNewTab(String title, @DrawableRes int icon, boolean isSelected)
    {
        //  Create Tab item
        BottomTabItem2 tabItem = new BottomTabItem2(getContext());
        tabItem.getTitleTV().setText(title);
        tabItem.getTitleIV().setImageResource(icon);

        //  Add Tab item to this view
        addView(tabItem);

        //  Update Layout params height to match parent
        LayoutParams tabParams = (LinearLayout.LayoutParams)tabItem.getLayoutParams();
        tabParams.height = LayoutParams.MATCH_PARENT;
        tabItem.setLayoutParams(tabParams);

        //  Update the Tab view based on the state
        if(isSelected)
        {
            updateLayoutForSelectedTab(tabItem, false);
        }else{
            //  Add listener only if its not selected
            tabItem.setOnClickListener(this);
        }

        updateTabWidth(tabItem, isSelected);
    }

    private void updateTabWidth(BottomTabItem2 bottomBarItemLL, boolean isActive)
    {
        LayoutParams layoutParams = (LayoutParams) bottomBarItemLL.getLayoutParams();
        if(isActive)
        {
            layoutParams.width = mSelectedTabWidth;
        }else
        {
            layoutParams.width = mUnSelectedTabWidth;
        }

        bottomBarItemLL.setLayoutParams(layoutParams);
    }

    private void updateTabWidthWithAnim(BottomTabItem2 bottomBarItemLL, boolean isSelected)
    {
        if(isSelected)
        {
            AnimationUtils.widthValueAnimator(bottomBarItemLL,
                    mUnSelectedTabWidth, mSelectedTabWidth, ANIM_DURATION);
        }else
        {
            AnimationUtils.widthValueAnimator(bottomBarItemLL,
                    mSelectedTabWidth, mUnSelectedTabWidth, ANIM_DURATION);
        }
    }

    private void selectTab(BottomTabItem2 newTab)
    {
        BottomTabItem2 oldTab = getOldTab();

        //  Update old tab
        updateLayoutForUnSelectedTab(oldTab);

        //  Update new tab
        updateLayoutForSelectedTab(newTab, true);
    }

    /**
     * Does all the props update, calls methods to set new widths and handles some animations
     * @param newTab    The new Tab which is selected.
     */
    private void updateLayoutForSelectedTab(BottomTabItem2 newTab, boolean shouldAnimate){
        //  Update new tab
        newTab.setTag(TAG_I_AM_SELECTED);

        ImageView tabIV = newTab.getTitleIV();

        //  Color
        tabIV.setColorFilter( mSelectedTabForegroundColor);

        if(shouldAnimate)
        {
            updateTabWidthWithAnim(newTab, true);

            //  Update padding for icon
            AnimationUtils.paddingTopValueAnimator(newTab.getTitleIV(), mUnSelectedTabTopPadding,
                    mSelectedTabTopPadding, ANIM_DURATION);

            //  Alpha animation
            AnimationUtils.alphaValueAnimator(newTab.getTitleIV(), 0.5f, 1, ANIM_DURATION);
            AnimationUtils.alphaValueAnimator(newTab.getTitleTV(), 0.5f, 1, ANIM_DURATION);

//            AnimationUtils.colorFilterValueAnimator( newTab.getTitleIV(),
//                    mUnSelectedTabForegroundColor,
//                    mSelectedTabForegroundColor,
//                    ANIM_DURATION);
        }else
        {
            updateTabWidth(newTab, true);

            tabIV.setPadding(tabIV.getPaddingStart(),
                    mSelectedTabTopPadding, tabIV.getPaddingEnd(), tabIV.getPaddingBottom());

        }

        newTab.getTitleTV().setVisibility(VISIBLE);
        newTab.getTitleTV().setTextColor(mSelectedTabForegroundColor);

//        newTab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary));

        //  Remove listener to avoid multiple Selects
        newTab.setOnClickListener(null);

    }

    /**
     * Does all the props update, calls methods to set new widths and handles some animations
     * @param oldTab    The old Tab which was previously selected.
     */
    private void updateLayoutForUnSelectedTab(BottomTabItem2 oldTab){

        //  Update old tab
        if(oldTab != null){
            oldTab.setTag(null);

            updateTabWidthWithAnim(oldTab, false);

            AnimationUtils.paddingTopValueAnimator(oldTab.getTitleIV(), mSelectedTabTopPadding,
                    mUnSelectedTabTopPadding, ANIM_DURATION);

            //  Color animation
//            AnimationUtils.colorFilterValueAnimator( oldTab.getTitleIV(),
//                    mSelectedTabForegroundColor,
//                    mUnSelectedTabForegroundColor,
//                    ANIM_DURATION);


            ImageView tabIV = oldTab.getTitleIV();
            tabIV.setColorFilter(mUnSelectedTabForegroundColor);

            //  Alpha animation
            AnimationUtils.alphaValueAnimator(tabIV, 1f, 0.7f, ANIM_DURATION);
            AnimationUtils.alphaValueAnimator(oldTab.getTitleTV(), 1f, 0, ANIM_DURATION);

//            oldTab.getTitleTV().setVisibility(GONE);
//            oldTab.getTitleTV().setTextColor(mUnSelectedTabForegroundColor);

//            oldTab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

            //  Update click listener to listen to tab select
            oldTab.setOnClickListener(this);
        }
    }

    private  @Nullable BottomTabItem2 getOldTab() {

        for (int index = 0; index < getChildCount(); index++){
            BottomTabItem2 tab = (BottomTabItem2) getChildAt(index);

            if(isTabSelected(tab))
            {
                return tab;
            }
        }

        return null;
    }

    /**
     * Method to check if the tab is in selected state based on the Tag
     * @param tabItem   Tab item to check
     * @return  true if it is selected
     */
    private boolean isTabSelected(BottomTabItem2 tabItem){
        String tag = (String)tabItem.getTag();
        return !TextUtils.isEmpty(tag) &&
                tag.equalsIgnoreCase(TAG_I_AM_SELECTED);
    }

    @Override
    public void onClick(View view) {
        selectTab((BottomTabItem2)view);
    }
}
