package sha.com.ind.labapp.custom.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.tabs.bottombar.interfaces.OnBottomTabbarCallBack;
import sha.com.ind.labapp.utils.AnimationUtils;
import sha.com.ind.labapp.utils.GeneralUtils;

/**
 * Created by sreepolavarapu on 12/08/16.
 *
 * Bottom bar navigation bar which holds maximum of {@link BottomNavigationTabBar#MAX_TABS} {@link BottomTabItem}.
 *
 * Uses the callback to notify the tab selected state. Does support Fixed width, Variable width, Dark theme.
 */
public class BottomNavigationTabBar extends LinearLayout implements View.OnClickListener{

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
    private int mTranspBGColor;

    //  Padding
    private int mSelectedTabTopPadding;
    private int mUnSelectedTabTopPadding;

    //  alpha
    private float mSelectedAlpha;
    private float mUnSelectedAlpha;

    //  Themes
    private boolean hasFixedTabs;
    private boolean isDarkTheme;

    //  Callback
    private OnBottomTabbarCallBack mOnBottomTabbarCallBack;


    public BottomNavigationTabBar(Context context) {
        super(context);
        init();
    }

    public BottomNavigationTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomNavigationTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public BottomNavigationTabBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init()
    {
        setWillNotDraw(false);
        ViewCompat.setElevation(this,getResources().getDimensionPixelSize(R.dimen.elevation_8dp));

        //  Init alphas
        mSelectedAlpha = 1f;
        mUnSelectedAlpha = 0.6f;

        //  Transparant color
        mTranspBGColor = ContextCompat.getColor(getContext(), android.R.color.transparent);

        //  Calculating widths
        mScreenWidth = GeneralUtils.getScreenWidthInPixels(getContext());

        updateVars();
        calculateAndInitTabWidths();

//        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary));
    }

    public void updateBottomTabCallback(OnBottomTabbarCallBack onBottomTabbarCallBack)
    {
        mOnBottomTabbarCallBack = onBottomTabbarCallBack;
    }

    /**
     * Add a new Tab to the Layout
     * @param title Title for the tab
     * @param icon  Icon for the tab
     * @param isSelected    State of the tab
     */
    public void addNewTab(String title, @DrawableRes int icon, boolean isSelected)
    {
        if(mOnBottomTabbarCallBack == null)
        {
            throw new RuntimeException("Configure onBottomTabbarCallBack listener before adding tabs");
        }

        if(getChildCount() >= MAX_TABS){
            throw new RuntimeException("Added more than max tabs");
        }

        //  Create Tab item
        BottomTabItem tabItem = new BottomTabItem(getContext());
        tabItem.getTitleTV().setText(title);
        tabItem.getTitleIV().setImageResource(icon);

        tabItem.setTag(R.id.tab_position, getChildCount());

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
            updateLayoutForUnSelectedTab(tabItem, false);
        }
//        updateTabWidth(tabItem, isSelected);
    }

    /**
     * Update Tab width
     * @param tabItem   The Tab item
     * @param isSelected  if its selected or not
     */
    private void updateTabWidth(BottomTabItem tabItem, boolean isSelected)
    {
        LayoutParams layoutParams = (LayoutParams) tabItem.getLayoutParams();
        if(isSelected)
        {
            layoutParams.width = mSelectedTabWidth;
        }else
        {
            layoutParams.width = mUnSelectedTabWidth;
        }

        tabItem.setLayoutParams(layoutParams);
    }

    /**
     * Update the tab width with applying animation.
     * @param tabItem   The tab item
     * @param isSelected    if is selected or not
     */
    private void updateTabWidthWithAnim(BottomTabItem tabItem, boolean isSelected)
    {
        if(isSelected)
        {
            AnimationUtils.widthValueAnimator(tabItem,
                    mUnSelectedTabWidth, mSelectedTabWidth, ANIM_DURATION);
        }else
        {
            AnimationUtils.widthValueAnimator(tabItem,
                    mSelectedTabWidth, mUnSelectedTabWidth, ANIM_DURATION);
        }
    }

    /**
     * Selects ( applies layout changes ) the tab passed in Param.
     * Updates the old tab with unselected state.
     *
     *  @param newTab The new tab which is selected.
     */
    private void selectTab(BottomTabItem newTab)
    {
        int newTabPosition = getTabPosition(newTab);
        mOnBottomTabbarCallBack.onTabSelected(newTabPosition);

        BottomTabItem oldTab = getOldTab();

        //  Update old tab
        updateLayoutForUnSelectedTab(oldTab, true);

        //  Update new tab
        updateLayoutForSelectedTab(newTab, true);
    }


    /**
     *
     * Selects the tab at position. Can be used along with  ViewPager.
     * Updates the old tab with unselected state.
     *
     *  @param position The position of new tab which is selected.
     */
    public void selectTab(int position)
    {
        BottomTabItem newTab = (BottomTabItem)getChildAt(position);

        selectTab(newTab);
    }

    /**
     * Does all the props update, calls methods to set new widths and handles some animations
     * @param newTab    The new Tab which is selected.
     */
    private void updateLayoutForSelectedTab(BottomTabItem newTab, boolean shouldAnimate){
        //  Update new tab
        newTab.setTag(R.id.tab_selected, TAG_I_AM_SELECTED);

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
            AnimationUtils.alphaValueAnimator(newTab.getTitleIV(), mUnSelectedAlpha, mSelectedAlpha, ANIM_DURATION);
            AnimationUtils.alphaValueAnimator(newTab.getTitleTV(), mUnSelectedAlpha, mSelectedAlpha, ANIM_DURATION);

        }else
        {
            updateTabWidth(newTab, true);
            tabIV.setPadding(tabIV.getPaddingStart(),
                    mSelectedTabTopPadding, tabIV.getPaddingEnd(), tabIV.getPaddingBottom());

            tabIV.setAlpha(mSelectedAlpha);
            newTab.getTitleTV().setAlpha(mSelectedAlpha);
        }

        newTab.getTitleTV().setVisibility(VISIBLE);
        newTab.getTitleTV().setTextColor(mSelectedTabForegroundColor);

        newTab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary));
        if(isDarkTheme)
        {
            newTab.setBackgroundColor(mTranspBGColor);
        }

        //  Remove listener to avoid multiple Selects
        newTab.setOnClickListener(null);
    }

    /**
     * Does all the props update, calls methods to set new widths and handles some animations
     * @param oldTab    The old Tab which was previously selected.
     */
    private void updateLayoutForUnSelectedTab(BottomTabItem oldTab, boolean shouldAnimate){

        //  Update old tab
        if(oldTab != null){
            oldTab.setTag(R.id.tab_selected, null);

            ImageView tabIV = oldTab.getTitleIV();
            TextView tabTV = oldTab.getTitleTV();

            if(shouldAnimate)
            {
                //  ANIM
                updateTabWidthWithAnim(oldTab, false);
                AnimationUtils.paddingTopValueAnimator(tabIV, mSelectedTabTopPadding,
                        mUnSelectedTabTopPadding, ANIM_DURATION);
                //  Alpha animation
                AnimationUtils.alphaValueAnimator(tabIV, mSelectedAlpha, mUnSelectedAlpha, ANIM_DURATION);
                AnimationUtils.alphaValueAnimator(tabTV, mSelectedAlpha, mUnSelectedAlpha, ANIM_DURATION);

            }else
            {
                //  without ANIM
                updateTabWidth(oldTab, false);
                tabIV.setPadding(oldTab.getPaddingStart(),
                        mUnSelectedTabTopPadding,
                        oldTab.getPaddingEnd(),
                        oldTab.getPaddingBottom());
                tabIV.setAlpha(mUnSelectedAlpha);
                tabTV.setAlpha(mUnSelectedAlpha);
            }

            tabIV.setColorFilter(mUnSelectedTabForegroundColor);

            tabTV.setVisibility(hasFixedTabs ? VISIBLE : GONE);
            tabTV.setTextColor(mUnSelectedTabForegroundColor);

            oldTab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            if(isDarkTheme)
            {
                oldTab.setBackgroundColor(mTranspBGColor);
            }

            //  Update click listener to listen to tab select
            oldTab.setOnClickListener(this);
        }
    }


    /**
     * @return the previously selected tab. can be null, if nothing is selected by then??
     */
    @Nullable
    private BottomTabItem getOldTab() {

        for (int index = 0; index < getChildCount(); index++){
            BottomTabItem tab = (BottomTabItem) getChildAt(index);

            if(isTabSelected(tab))
            {
                return tab;
            }
        }

        return null;
    }


    /**
     * @return the previously selected tab. can be null, if nothing is selected by then??
     */
    @Nullable
    private int getTabPosition(BottomTabItem tabItem) {
        return  (int)tabItem.getTag(R.id.tab_position);
    }

    /**
     * Method to check if the tab is in selected state based on the Tag
     * @param tabItem   Tab item to check
     * @return  true if it is selected
     */
    private boolean isTabSelected(BottomTabItem tabItem){
        String tag = (String)tabItem.getTag(R.id.tab_selected);
        return !TextUtils.isEmpty(tag) &&
                tag.equalsIgnoreCase(TAG_I_AM_SELECTED);
    }

    /**
     * Calculate the tab widths based on configuration and Screen widths.
     */
    private void calculateAndInitTabWidths()
    {
        int noOfTabs = getNoOfTabs();

        //  Init all tabs with equal width
        mSelectedTabWidth = mScreenWidth / noOfTabs;
        mUnSelectedTabWidth = mSelectedTabWidth;

        int inActiveMinWidth = getResources().getDimensionPixelOffset(R.dimen.inactive_bottom_tab_min_width);
        int activeMaxWidth =  getResources().getDimensionPixelOffset(R.dimen.active_bottom_tab_max_width);

        int remainingSpace =  mScreenWidth - (inActiveMinWidth * (noOfTabs - 1));

        //  If config does not say fixed width and remaining space is less than guideline max width,
        //  Assign Selected width with remaining space.
        if(!hasFixedTabs &&
                remainingSpace <= activeMaxWidth)
        {
            mSelectedTabWidth = remainingSpace;
            mUnSelectedTabWidth = inActiveMinWidth;
        }
    }

    /**
     *
     * Checks the number children (tabs).
     * If there are no children assumes {@link BottomNavigationTabBar#MAX_TABS} as no of tabs.
     *
     * @return number of tabs for this layout.
     */
    private int getNoOfTabs()
    {
        if(getChildCount() > 0)
        {
            return getChildCount();
        }

        return MAX_TABS;
    }

    /**
     * Update variables based on the theme selected.
     */
    private void updateVars()
    {
        //  Init PADDINGS
        mSelectedTabTopPadding = getResources().getDimensionPixelSize(R.dimen.padding_short);
        mUnSelectedTabTopPadding = getResources().getDimensionPixelSize(R.dimen.padding_standard);

        //  If it has fixed tabs, Top padding never change and its always short.
        if(hasFixedTabs)
        {
            mUnSelectedTabTopPadding = mSelectedTabTopPadding;
        }

        //  Colors
        mSelectedTabForegroundColor = ContextCompat.getColor(getContext(), R.color.white);
        mUnSelectedTabForegroundColor = ContextCompat.getColor(getContext(), R.color.grey_medium);
        if(isDarkTheme)
        {
            //  Init colors
            mUnSelectedTabForegroundColor = mSelectedTabForegroundColor;
        }

        //  Navigation bar background color
        int bottomBarBGColor = ContextCompat.getColor(getContext(), R.color.white);
        if(isDarkTheme)
        {
            bottomBarBGColor = ContextCompat.getColor(getContext(), R.color.primary);
        }
        setBackgroundColor(bottomBarBGColor);
    }

    @Override
    public void onClick(View view) {
        selectTab((BottomTabItem)view);
    }

    //  --  CONFIGS

    /**
     * Update the tabs as fixed widths.
     */
    public void setAsFixedTabs()
    {
        hasFixedTabs = true;

        calculateAndInitTabWidths();
        updateVars();

        invalidate();
    }

    /**
     * Update the tabs as Shifting widths. Like the google designs
     */
    public void setAsShiftingTabs()
    {
        hasFixedTabs = false;

        calculateAndInitTabWidths();
        updateVars();

        invalidate();
    }

    /**
     * Applies dark theme to background of nav bar
     */
    public void enableDarkTheme()
    {
        isDarkTheme = true;

        updateVars();

        invalidate();
    }

    /**
     * Disables dark theme.
     */
    public void disableDarkTheme()
    {
        isDarkTheme = false;

        updateVars();

        invalidate();
    }

    @Override
    public void invalidate() {
        super.invalidate();

        //  Hacky way - Have to look for a better soln
        int count = getChildCount();
        for(int childIndex = 0; childIndex < count; childIndex++){

            BottomTabItem tabItem = (BottomTabItem) getChildAt(childIndex);

            if(isTabSelected(tabItem))
            {
                updateLayoutForSelectedTab(tabItem, true);
            }else
            {
                updateLayoutForUnSelectedTab(tabItem, true);
            }
        }
    }
}
