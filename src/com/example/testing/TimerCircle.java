package com.example.testing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsoluteLayout.LayoutParams;

@SuppressLint("DrawAllocation")
public class TimerCircle extends View {

	private float angle = 0;
	private float startAngle = -90;
	Paint paintGreen = new Paint();
	Paint paintDark = new Paint();
	Paint paint = new Paint();
	RectF oval;
	RectF circle;
	
	public TimerCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		int widthPx = getResources().getDisplayMetrics().widthPixels;
		int heightPx = getResources().getDisplayMetrics().heightPixels;
		final float density = getResources().getDisplayMetrics().density;
		int radius = widthPx / 2 - (int) (32 * density);
		
		
		paintGreen.setAntiAlias(true);
		paintGreen.setStyle(Paint.Style.STROKE);
		paintGreen.setStrokeWidth(10*density);
		paintGreen.setColor(0xFF16A085);
		paintGreen.setStrokeCap(Cap.ROUND);
		paintGreen.setStrokeJoin(Join.ROUND);
		
		paintDark.setAntiAlias(true);
		paintDark.setStyle(Paint.Style.STROKE);
		paintDark.setColor(0xFF507091);
		paintDark.setStrokeWidth(10*density);
		
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(0xFF507091);
		paint.setStrokeWidth(2*density);
		
		oval = new RectF(widthPx/2 -radius, heightPx/2 - radius, widthPx/2 + radius, heightPx/2 + radius);
		
		radius = widthPx / 2 - (int) (39 * density);
		circle = new RectF(widthPx/2 -radius, heightPx/2 - radius, widthPx/2 + radius, heightPx/2 + radius);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas); 

		//рисуем синию дугу
		canvas.drawArc(oval, startAngle, angle, false, paintGreen);
		
		//рисуем темную дугу
		canvas.drawArc(oval, startAngle, -360+angle, false, paintDark);
		
		//рисуем темный тонкий кружок
		canvas.drawArc(circle, 0, 360, false,  paint);
		
	}
	
	public void setAngle(float newAngle){
		angle = newAngle;
		invalidate();
		//requestLayout();
	}
}
