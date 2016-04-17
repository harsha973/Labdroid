package sha.com.ind.labapp.custom.components.touchabletextview;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * Created by dallaskuhn on 20/05/15.
 */
public abstract class TouchableSpan extends ClickableSpan
{
	private boolean isPressed;
	private int normalTextColor;
	private int pressedTextColor;
	private int pressedBackgroundColor = -1;

	public TouchableSpan(int normalTextColor, int pressedTextColor)
	{
		this.normalTextColor = normalTextColor;
		this.pressedTextColor = pressedTextColor;
	}

	public TouchableSpan(int normalTextColor, int pressedTextColor, int pressedBackgroundColor)
	{
		this.normalTextColor = normalTextColor;
		this.pressedTextColor = pressedTextColor;
		this.pressedBackgroundColor = pressedBackgroundColor;
	}

	public void setPressed(boolean isSelected)
	{
		isPressed = isSelected;
	}

	public boolean isPressed()
	{
		return isPressed;
	}

	@Override
	public void updateDrawState(TextPaint textPaint)
	{
		super.updateDrawState(textPaint);

		textPaint.setColor(isPressed ? pressedTextColor : normalTextColor);

		if (pressedBackgroundColor != -1)
		{
			textPaint.bgColor = isPressed ? pressedBackgroundColor : 0xffeeeeee;
		}

		textPaint.setUnderlineText(false);
	}
}