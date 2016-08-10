package sha.com.ind.labapp.tabs.bottombar;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.custom.components.BottomBarItemLL;

/**
 * Created by sreepolavarapu on 9/08/16.
 */
public class CustomBottomBarActivity extends BaseActivity implements View.OnClickListener{

    private String TAG_I_AM_SELECTED = "TAG_I_AM_SELECTED";

    private LinearLayout mBottomNavLL;

    //  Home
    private BottomBarItemLL mFirstTabLL;
    //
    private BottomBarItemLL mSecondTabLL;
    //  Card
    private BottomBarItemLL mThirdTabLL;
    //
    private BottomBarItemLL mFourthTabLL;
    //  More
    private BottomBarItemLL mFifthTabLL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_bar_custom);
        setupActionBar(R.string.bottom_navigation_custom);
        enableDisplayHomeasUp();

        mBottomNavLL = (LinearLayout) findViewById(R.id.ll_bottom_nav);
        prepareTabs();

    }


    private void prepareTabs(){

        for (int index = 0; index < mBottomNavLL.getChildCount(); index++)
        {
            BottomBarItemLL tabLL = (BottomBarItemLL)mBottomNavLL.getChildAt(index);
            tabLL.setOnClickListener(this);
            switch (tabLL.getId())
            {
                case R.id.tab_one:
                    mFirstTabLL = tabLL;
                    prepareTab(mFirstTabLL, "Home", R.drawable.ic_home_white_24dp );

                    break;

                case R.id.tab_two:
                    mSecondTabLL = tabLL;
                    prepareTab(mSecondTabLL,  "Stores", R.drawable.ic_face_white_24dp);
                    break;

                case R.id.tab_three:
                    mThirdTabLL = tabLL;
                    prepareTab(mThirdTabLL,  "Card", R.drawable.ic_credit_card_white_24dp);
                    break;

                case R.id.tab_four:
                    mFourthTabLL = tabLL;
                    prepareTab(mFourthTabLL,  "Spend pts", R.drawable.ic_feedback_white_24dp);
                    break;

                case R.id.tab_five:
                    mFifthTabLL = tabLL;
                    prepareTab(mFifthTabLL,  "More", R.drawable.ic_more_horiz_white_24dp);
                    break;
            }
        }

        tabSelected(0);

    }

    private void prepareTab(BottomBarItemLL tabItem, String text , @DrawableRes int resID){


        TextView subTabTV = (TextView)tabItem.findViewById(R.id.tv_sub_tab);
        subTabTV.setText(text);

        ImageView subTabIV = (ImageView)tabItem.findViewById(R.id.iv_sub_tab);
        subTabIV.setImageResource(resID);
//        subTabIV.setColorFilter(ContextCompat.getColor(this, isSelected ?  R.color.white : R.color.grey_dark));

    }

    private void tabSelected(int position)
    {
        BottomBarItemLL tabLL = (BottomBarItemLL)mBottomNavLL.getChildAt(position);
        tabSelected(tabLL);
    }

    private void modifyTabState(BottomBarItemLL tabItem, boolean isSelected)
    {
        TextView subTabTV = (TextView)tabItem.findViewById(R.id.tv_sub_tab);
        ImageView subTabIV = (ImageView)tabItem.findViewById(R.id.iv_sub_tab);

        subTabTV.setVisibility(isSelected ? View.VISIBLE : View.GONE);
        subTabTV.setTextColor(ContextCompat.getColor(this, R.color.white));
        subTabIV.setColorFilter(ContextCompat.getColor(this, isSelected ?  R.color.white : R.color.grey_dark));

    }

    private void tabSelected(View view)
    {
        if(view instanceof BottomBarItemLL)
        {
            BottomBarItemLL tabLL = (BottomBarItemLL)view;

            for (int index = 0; index < mBottomNavLL.getChildCount(); index++)
            {
                BottomBarItemLL child = (BottomBarItemLL)mBottomNavLL.getChildAt(index);

                if(child.getId() != tabLL.getId())
                {
                    child.setActiveState(false);
                    modifyTabState(child, false);

                    String tag = (String)child.getTag();
                    if(!TextUtils.isEmpty(tag) &&
                            tag.equals(TAG_I_AM_SELECTED))
                    {
                        child.setTag(null);
                    }
                }else
                {
                    modifyTabState(child, true);
                    child.setActiveState(true);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

        tabSelected(view);

    }

}