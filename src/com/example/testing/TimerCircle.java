package com.example.testing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class TimerCircle extends View {

	private float angle = 0;
	private float startAngle = -90;
	private float radius;
	private Paint paintGreen = new Paint();
	private Paint paintDark = new Paint();
	private Paint paint = new Paint();
	private RectF oval;
	private RectF circle;
	
	public TimerCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		
		int widthPx = this.getResources().getDisplayMetrics().widthPixels;
		int heightPx = this.getResources().getDisplayMetrics().heightPixels;
		final float density = getResources().getDisplayMetrics().density;
		radius = widthPx / 2 -  (16 * density);
		
		//для зелёной дуги
		paintGreen.setAntiAlias(true);
		paintGreen.setStyle(Paint.Style.STROKE);
		paintGreen.setStrokeWidth(10*density);
		paintGreen.setColor(0xFF1ABC9C);
		//paintGreen.setShadowLayer(8 * density, 0.0f, 2 * density, 0xFF000000);
		
		//для синие дуги
		paintDark.setAntiAlias(true);
		paintDark.setStyle(Paint.Style.STROKE);
		paintDark.setColor(0xFF507091);
		paintDark.setStrokeWidth(10*density);
		//paintDark.setShadowLayer(8 * density, 0.0f, 2 * density, 0xFF000000);
		
		//для внутренностей
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xFF354a60);
		paint.setStrokeWidth(2*density);
		
		oval = new RectF(widthPx/2 -radius, heightPx/2 - radius, widthPx/2 + radius, heightPx/2 + radius);
		
		radius = widthPx / 2 - (int) (23 * density);
		circle = new RectF(widthPx/2 -radius, heightPx/2 - radius, widthPx/2 + radius, heightPx/2 + radius);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas); 

		//рисуем синию дугу
		canvas.drawArc(oval, startAngle, angle, false, paintGreen);
		
		//рисуем темную дугу
		canvas.drawArc(oval, startAngle, -360+angle, false, paintDark);
		
		//рисуем темные внутренности
		canvas.drawArc(circle, 0, 360, false,  paint);
		
	}
	
	public void setAngle(float newAngle){
		angle = newAngle;
		invalidate();
	}
	
	public float getRadius(){
		return radius;
	}
	
	public float getY(){
		return radius;
	}
	
	
	//public void getPostionOfAngle(float whatAngle){
		
	//}
}
