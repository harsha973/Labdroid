package sha.com.ind.labapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import sha.com.ind.labapp.R;

/**
 * Created by sreepolavarapu on 19/12/15.
 */
public class ComponentUtils {

    /**
     * Method to create bitmap placeholder with the size provided. Width and height are same in this case
     * Draws text center aligned at <code>textStartX<code/> and <code>textStartY</code>
     * @param context   Context from which it is called
     * @param text  Text to draw
     * @param textStartX    X point to draw. Drawn center aligned to this point
     * @param textStartY    Y point to draw.  Drawn center aligned to this point
     * @param textSize  Font size
     * @param drawableSize  Placeholder size
     * @param shouldAddBounds   This will add height bounds
     * @return
     */
    public static Drawable getPlaceHolder(Context context, String text, int textStartX, int textStartY, int textSize,
                                    int drawableSize, boolean shouldAddBounds)
    {
        Bitmap b = Bitmap.createBitmap(drawableSize, drawableSize, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        paint.setStyle(Paint.Style.FILL);

        Canvas c = new Canvas(b);
        c.drawRect(0, 0, drawableSize, drawableSize, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);

        if(shouldAddBounds)
        {
            //  This will center align the text height
            Rect bounds = new Rect();
            paint.getTextBounds("a", 0, 1, bounds);
            textStartY += (bounds.height() >> 1);
        }
        c.drawText(text, textStartX, textStartY ,paint);
        return  new BitmapDrawable(context.getResources(), b);
    }

    /**
     * Max characters sent back is 2
     * @param name  The name
     * @return intitals extracted from name. Ex : returns "SH" if <code>name</code> is "Sree Harsha"
     */
    public static String getIntials(String name)
    {
        String[] nameArr = name.split(" ");
        if(nameArr.length == 0)
        {
            return name.substring(0,1);
        }

        StringBuilder intialChar = new StringBuilder();
        int iterations = nameArr.length == 1 ? nameArr.length : 2;

        for(int index = 0; index < iterations; index++)
        {
            intialChar.append(nameArr[index].trim().substring(0,1));
        }

        return intialChar.toString();
    }
}
