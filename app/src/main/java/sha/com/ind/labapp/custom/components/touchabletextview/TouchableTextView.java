package sha.com.ind.labapp.custom.components.touchabletextview;

import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by dallaskuhn on 21/05/15.
 */
public class TouchableTextView extends TextView
{
	private static TouchableSpan pressedSpan;

	private boolean allowNonUrlTouches = true;
	private boolean linkTouched;

	public TouchableTextView(Context context)
	{
		super(context);
	}

	public TouchableTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public TouchableTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		linkTouched = false;

		boolean res = super.onTouchEvent(event);

		if (allowNonUrlTouches)
		{
			return linkTouched;
		}

		return res;
	}

	public void setTextViewHTML(String html)
	{
		CharSequence sequence = Html.fromHtml(html);

		SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);

		setText(strBuilder);
	}

	public static class LocalLinkMovementMethod extends LinkMovementMethod
	{
		private static LocalLinkMovementMethod sInstance;

		public static LocalLinkMovementMethod getInstance()
		{
			if (sInstance == null)
			{
				sInstance = new LocalLinkMovementMethod();
			}

			return sInstance;
		}

		@Override
		public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event)
		{
			int action = event.getAction();

			if (action == MotionEvent.ACTION_DOWN)
			{
				pressedSpan = getPressedSpan(textView, spannable, event);

				if (pressedSpan != null)
				{
					pressedSpan.setPressed(true);

					Selection.setSelection(spannable, spannable.getSpanStart(pressedSpan), spannable.getSpanEnd(pressedSpan));

					//return true;
				}
				else
				{
					Selection.removeSelection(spannable);

					Touch.onTouchEvent(textView, spannable, event);

					return false;
				}
			}
			else if (action == MotionEvent.ACTION_MOVE)
			{
				TouchableSpan touchedSpan = getPressedSpan(textView, spannable, event);

				if (pressedSpan != null && touchedSpan != pressedSpan)
				{
					pressedSpan.setPressed(false);

					pressedSpan = null;

					Selection.removeSelection(spannable);
				}
			}
			else
			{
				if (pressedSpan != null)
				{
					pressedSpan.setPressed(false);

					super.onTouchEvent(textView, spannable, event);

					pressedSpan = null;

					Selection.removeSelection(spannable);

					return true;
				}
				else
				{
					Selection.removeSelection(spannable);

					Touch.onTouchEvent(textView, spannable, event);

					return false;
				}
			}

			return true;
		}

		private static TouchableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event)
		{
			int x = (int) event.getX(); int y = (int) event.getY();

			x -= textView.getTotalPaddingLeft(); y -= textView.getTotalPaddingTop();

			x += textView.getScrollX(); y += textView.getScrollY();

			Layout layout = textView.getLayout();

			int line = layout.getLineForVertical(y);
			int off = layout.getOffsetForHorizontal(line, x);

			TouchableSpan[] link = spannable.getSpans(off, off, TouchableSpan.class);
			TouchableSpan touchedSpan = null;

			if (link.length > 0)
			{
				touchedSpan = link[0];

				if (textView instanceof TouchableTextView)
				{
					((TouchableTextView) textView).linkTouched = true;
				}
			}

			return touchedSpan;
		}
	}
}