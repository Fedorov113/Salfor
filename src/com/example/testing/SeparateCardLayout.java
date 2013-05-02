package com.example.testing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class SeparateCardLayout extends LinearLayout implements
		OnGlobalLayoutListener {

	public SeparateCardLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayoutObserver();

	}

	public SeparateCardLayout(Context context) {
		super(context);
		initLayoutObserver();
	}

	private void initLayoutObserver() {
		// force vertical orientation and add observer
		setOrientation(LinearLayout.VERTICAL);
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@SuppressWarnings("deprecation")
	public void onGlobalLayout() {
		getViewTreeObserver().removeGlobalOnLayoutListener(this);

		final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;

		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);

			int[] location = new int[2];

			child.getLocationOnScreen(location);

			if (location[1] > heightPx) {
				break;
			}

			child.startAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.slide_up_right));

		}

	}

}