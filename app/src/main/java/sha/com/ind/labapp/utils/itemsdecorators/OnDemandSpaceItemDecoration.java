package sha.com.ind.labapp.utils.itemsdecorators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sree on 2/07/17.
 */

public class OnDemandSpaceItemDecoration extends SpaceItemDecoration {

    private boolean mTopPaddingEnabled;
    private Paint mPaint;
    private int mAlpha;

    public OnDemandSpaceItemDecoration(Context context, int dimenRes) {
        super(context, dimenRes);
    }

    public OnDemandSpaceItemDecoration(int left, int right, int bottom, int top) {
        super(left, right, bottom, top);
        setBgColor();
    }

    public void setTopPaddingEnabled(boolean enabled) {
        mTopPaddingEnabled = enabled;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final RecyclerView.Adapter adapter = parent.getAdapter();
        if(adapter instanceof OnDemandSpaceListener) {
            OnDemandSpaceListener onDemandSpaceListener = (OnDemandSpaceListener) adapter;
            if(onDemandSpaceListener.shouldAddSpace(parent.getChildAdapterPosition(view))) {
                if(mTopPaddingEnabled) {
                    outRect.left = getLeftSpace();
                    outRect.right = getRightSpace();
                    outRect.bottom = geBottomSpace();
                    outRect.top = getTopSpace();
                } else {
                    super.getItemOffsets(outRect, view, parent, state);
                }
            }
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        final int offset = (int) mPaint.getStrokeWidth();

        for(int i=0; i<parent.getChildCount(); i++){
            final View child = parent.getChildAt(i);
            OnDemandSpaceListener onDemandSpaceListener = (OnDemandSpaceListener) parent.getAdapter();
            if(onDemandSpaceListener.shouldAddSpace(parent.getChildAdapterPosition(child))) {
                mPaint.setAlpha((int) (child.getAlpha() * mAlpha));
                c.drawRect(layoutManager.getDecoratedLeft(child),
                        child.getTop() + child.getTranslationY(),
                        layoutManager.getDecoratedRight(child),
                        child.getTop()+ getTopSpace() + child.getTranslationY(),
                        mPaint);
            }
        }
    }

    public void setBgColor() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(getTopSpace());
        mAlpha = mPaint.getAlpha();
    }

    public interface OnDemandSpaceListener {
        boolean shouldAddSpace(int position);
    }
}
