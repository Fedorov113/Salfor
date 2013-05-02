package com.example.testing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class Draw extends View {

	protected double bmi_number;

	public Draw(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray tp = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.BmiBar, 0, 0);

		try {
			bmi_number = tp
					.getFloat(R.styleable.BmiBar_bmiNumber, (float) 21.5);
		} finally {
			tp.recycle();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);

		int widthPx = getResources().getDisplayMetrics().widthPixels;
		final float density = getResources().getDisplayMetrics().density;

		widthPx = widthPx - (int) (32 * density);
		float delta = (float) (widthPx / 10.0);
		int left = 0;
		int right = Math.round(3 * delta);
		int bottom = Math.round((6 * density));
		int top = Math.round((2 * density));

		/*---------------------------draw_underweight--------------------------*/
		paint.setColor(0xFFC7C7C7);
		canvas.drawRect(new Rect(left, top, right, bottom), paint);

		/*---------------------------draw_normal_weight--------------------------*/
		left = right;
		right = Math.round(7 * delta);
		paint.setColor(0xFF99CC00);
		canvas.drawRect(new Rect(left, top, right, bottom), paint);

		/*---------------------------draw_overweight--------------------------*/
		left = right;
		right = Math.round(10 * delta);
		paint.setColor(0xFFC7C7C7);
		canvas.drawRect(new Rect(left, top, right, bottom), paint);

		/*---------------------------draw_pointer--------------------------*/
		/*---------------------------set_left_and_right-------------------------*/
		if (bmi_number < 18.5) {
			int widthOfOneTenth = (int) Math.round((3 * delta) / 18.5);
			left = (int) Math
					.round((widthOfOneTenth * bmi_number - (4 * density)));
			right = Math.round((left + 4 * density));
		} else if ((bmi_number >= 18.5) && (bmi_number <= 25.0)) {
			int width_of_one_tenth = (int) Math
					.round((4 * delta) / (25 - 18.5));
			left = (int) Math.round((3 * delta)
					+ (width_of_one_tenth * (bmi_number - 18.5)));
			right = Math.round((left + 4 * density));
		} else if ((bmi_number > 25) && (bmi_number <= 43.5)) {
			int width_of_one_tenth = (int) Math.round((3 * delta) / 18.5);
			left = (int) Math.round((7 * delta)
					+ (width_of_one_tenth * (bmi_number - 25.0))
					- (4 * density));
			right = Math.round((left + 4 * density));
		} else if (bmi_number > 43.5) {
			left = (int) Math.round(widthPx - (4 * density));
			right = Math.round((left + 4 * density));
		}
		bottom = Math.round((8 * density));
		top = 0;
		paint.setColor(0xF033B5E5);
		canvas.drawRect(new Rect(left, top, right, bottom), paint);
	}

	public void setBmi(double bmi) {
		bmi_number = bmi;
		invalidate();
		requestLayout();
	}
}
