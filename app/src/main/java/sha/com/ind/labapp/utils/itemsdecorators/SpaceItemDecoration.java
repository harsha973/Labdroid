package sha.com.ind.labapp.utils.itemsdecorators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mLeft;
    private int mRight;
    private int mBottom;
    private int mTop;

    public SpaceItemDecoration(Context context, int dimenRes) {
        final int dimenPixel = context.getResources().getDimensionPixelOffset(dimenRes);
        mLeft = dimenPixel;
        mRight = dimenPixel;
        mBottom = dimenPixel;
        mTop = dimenPixel;
    }

    public SpaceItemDecoration(int left, int right, int bottom, int top) {
        mLeft = left;
        mRight = right;
        mBottom = bottom;
        mTop = top;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = getLeftSpace();
        outRect.right = getRightSpace();
        outRect.bottom = geBottomSpace();

        // Add top margin only for the first item to avoid double space between items
        if(parent.getChildAdapterPosition(view) == 0) {
            outRect.top = getTopSpace();
        }
    }

    public int getLeftSpace() {
        return mLeft;
    }

    public int getRightSpace() {
        return mRight;
    }

    public int getTopSpace() {
        return mTop;
    }

    public int geBottomSpace() {
        return mBottom;
    }
}