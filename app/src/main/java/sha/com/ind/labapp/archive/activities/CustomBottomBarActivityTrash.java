package sha.com.ind.labapp.archive.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;
import sha.com.ind.labapp.archive.components.BottomTabItemTrash;

/**
 * Created by sreepolavarapu on 9/08/16.
 */
public class CustomBottomBarActivityTrash extends BaseActivity implements View.OnClickListener{

    private String TAG_I_AM_SELECTED = "TAG_I_AM_SELECTED";
    private int ANIMATE_TIME = 150;

    private LinearLayout mBottomNavLL;

    //  Home
    private BottomTabItemTrash mFirstTabLL;
    //
    private BottomTabItemTrash mSecondTabLL;
    //  Card
    private BottomTabItemTrash mThirdTabLL;
    //
    private BottomTabItemTrash mFourthTabLL;
    //  More
    private BottomTabItemTrash mFifthTabLL;

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
            BottomTabItemTrash tabLL = (BottomTabItemTrash)mBottomNavLL.getChildAt(index);
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

    private void prepareTab(BottomTabItemTrash tabItem, String text , @DrawableRes int resID){


//        TextView subTabTV = (TextView)tabItem.findViewById(R.id.tv_sub_tab);
        tabItem.getTitleTV().setText(text);
        tabItem.getTitleTV().setTextColor(ContextCompat.getColor(this, R.color.white));

//        ImageView subTabIV = (ImageView)tabItem.findViewById(R.id.iv_sub_tab);
        tabItem.getTitleIV().setImageResource(resID);
//        subTabIV.setColorFilter(ContextCompat.getColor(this, isSelected ?  R.color.white : R.color.grey_dark));

    }

    private void tabSelected(int position)
    {
        BottomTabItemTrash tabLL = (BottomTabItemTrash)mBottomNavLL.getChildAt(position);
        tabSelected(tabLL);
    }

    private void modifyTabState(BottomTabItemTrash tabItem, boolean isSelected)
    {
        final ImageView icon = tabItem.getTitleIV();
        final TextView title = tabItem.getTitleTV();
//        TextView subTabTV = (TextView)tabItem.findViewById(R.id.tv_sub_tab);
//        ImageView subTabIV = (ImageView)tabItem.findViewById(R.id.iv_sub_tab);

        tabItem.getTitleTV().setVisibility(isSelected ? View.VISIBLE : View.GONE);
//        tabItem.getTitleTV().setTextColor(ContextCompat.getColor(this, R.color.white));
        tabItem.getTitleIV().setColorFilter(ContextCompat.getColor(this, isSelected ?  R.color.white : R.color.grey_dark));

//        int scale = isSelected ? 1  : 0;
//
//        ViewPropertyAnimatorCompat titleAnimator = ViewCompat.animate(tabItem.getTitleTV())
//                .setDuration(150)
//                .scaleX(scale)
//                .scaleY(scale);
//
//        titleAnimator.alpha(scale);
//
//        titleAnimator.start();

        if(isSelected)
        {
//            animateBGSelected(tabItem);

//            Animation animation   =    AnimationUtils.loadAnimation(this, R.anim.scale_grow_anim);
//            animation.setDuration(ANIMATE_TIME);
//            tabItem.setAnimation(animation);

//            ViewPropertyAnimatorCompat titleAnimator = ViewCompat.animate(tabItem.getTitleTV())
//                    .setDuration(ANIMATE_TIME)
//                    .scaleX(1)
//                    .scaleY(1);
//
//            titleAnimator.alpha(1);
//            titleAnimator.start();

            int shortDp = getResources().getDimensionPixelOffset(R.dimen.padding_short);
            int tenDp = getResources().getDimensionPixelOffset(R.dimen.padding_10dp);

            paddingAnimTop(icon.getPaddingTop(), shortDp, icon);

//            paddingAnimBottom(title.getPaddingBottom(), tenDp, title);

            ViewCompat.animate(icon)
                    .setDuration(ANIMATE_TIME)
                    .alpha(1f)
                    .start();

        }else
        {
//            animateBGUnSelected(tabItem);

//            Animation animation   =    AnimationUtils.loadAnimation(this, R.anim.scale_shrink_anim);
//            animation.setDuration(ANIMATE_TIME);
//            tabItem.setAnimation(animation);

//            ViewPropertyAnimatorCompat titleAnimator = ViewCompat.animate(tabItem.getTitleTV())
//                    .setDuration(ANIMATE_TIME)
//                    .scaleX(0)
//                    .scaleY(0);
//
//            titleAnimator.alpha(0);
//            titleAnimator.start();

            int mSixteenDp = getResources().getDimensionPixelOffset(R.dimen.padding_standard);

            paddingAnimTop(icon.getPaddingTop(), mSixteenDp, icon);

//            paddingAnimBottom(tabItem.getPaddingBottom(), 0, tabItem);

            ViewCompat.animate(icon)
                    .setDuration(ANIMATE_TIME)
                    .alpha(0.6f)
                    .start();
        }

    }


    private void paddingAnimTop(int start, int end, final View imageview)
    {
        ValueAnimator paddingAnimator = ValueAnimator.ofInt(start, end);
        paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imageview.setPadding(imageview.getPaddingLeft(), (Integer) animation.getAnimatedValue(),
                        imageview.getPaddingRight(), imageview.getPaddingBottom());
            }
        });
        paddingAnimator.setDuration(ANIMATE_TIME);
        paddingAnimator.start();
    }

    private void paddingAnimBottom(int start, int end, final View imageview)
    {
        ValueAnimator paddingAnimator = ValueAnimator.ofInt(start, end);
        paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imageview.setPadding(imageview.getPaddingLeft(), imageview.getPaddingEnd(),
                        imageview.getPaddingRight(), (Integer) animation.getAnimatedValue());
            }
        });
        paddingAnimator.setDuration(ANIMATE_TIME);
        paddingAnimator.start();
    }


    private void animateBGSelected(final BottomTabItemTrash tabItem)
    {
        int colorFrom = ContextCompat.getColor(this, R.color.white);
        int colorTo = ContextCompat.getColor(this, R.color.primary);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(ANIMATE_TIME); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                tabItem.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    private void animateBGUnSelected(final BottomTabItemTrash tabItem)
    {
        int colorFrom = ContextCompat.getColor(this, R.color.primary);
        int colorTo = ContextCompat.getColor(this, R.color.white);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(ANIMATE_TIME); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                tabItem.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    private void tabSelected(View view)
    {
        if(view instanceof BottomTabItemTrash)
        {
            BottomTabItemTrash tabLL = (BottomTabItemTrash)view;

            for (int index = 0; index < mBottomNavLL.getChildCount(); index++)
            {
                BottomTabItemTrash child = (BottomTabItemTrash)mBottomNavLL.getChildAt(index);

                boolean isSelected = child.getId() == tabLL.getId();

                String tag = (String)child.getTag();

                boolean isPreviouslySelected = !isSelected && !TextUtils.isEmpty(tag);

                if(isPreviouslySelected || isSelected)
                {
                    modifyTabState(child, isSelected);
                    child.setActiveState(isSelected);
                    child.setTag(isSelected ? TAG_I_AM_SELECTED : null);
                }


            }
        }
    }

    @Override
    public void onClick(View view) {

        tabSelected(view);

    }

}