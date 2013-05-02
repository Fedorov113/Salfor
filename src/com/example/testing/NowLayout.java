package com.example.testing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.view.View.OnTouchListener;

@SuppressWarnings("deprecation")
public class NowLayout extends AbsoluteLayout implements
		OnGlobalLayoutListener, OnTouchListener {

	AbsoluteLayout chooseAimLayout;
	View[] child = null;
	int child_count, delta;

	public NowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayoutObserver();

	}

	public NowLayout(Context context) {
		super(context);
		initLayoutObserver();
	}

	private void initLayoutObserver() {
		// force vertical orientation and add observer
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	public void onGlobalLayout() {
		getViewTreeObserver().removeGlobalOnLayoutListener(this);
		final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;
		final float density = getResources().getDisplayMetrics().density;
		child_count = getChildCount();
		delta = Math.round(70 * density);
		int x = 0, y = 0;
		Animation slide_from_left = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_right);
		chooseAimLayout = (AbsoluteLayout) findViewById(R.id.chooseAimLayout);
		child = new View[child_count];
		for (int i = 0; i < child_count; i++) {
			child[i] = getChildAt(i);
			AbsoluteLayout.LayoutParams params = (LayoutParams) child[i].getLayoutParams();
			params.x = x;
			params.y = y + delta * i;
			int[] location = new int[2];
			
			child[i].getLocationOnScreen(location);
			if (location[1] > heightPx) {
				break;
			}
			child[i].setLayoutParams(params);
			child[i].startAnimation(slide_from_left);
			
			child[i].setOnTouchListener(this);
		}
	}

	public boolean onTouch(View v, MotionEvent me) {
		// TODO Auto-generated method stub
		int location[] = new int [2];
		chooseAimLayout.getLocationOnScreen(location);
		int touch_y = (int) me.getRawY();
		
		if ((me.getAction() == MotionEvent.ACTION_DOWN) & (me.getAction() != MotionEvent.ACTION_MOVE)) {
			if ((touch_y > location[1]) & (touch_y < (location[1] + delta * (child_count - 1)))) {
				swapCards(touch_y);
			}
		}
		return false;
	}
	
	public void swapCards(int y){
		int[] layout_start_coordinates = new int[2];
		int i = 0, j = (child_count - 1), k = (child_count - 2);
		chooseAimLayout.getLocationOnScreen(layout_start_coordinates);
		int target_y = layout_start_coordinates[1];
		/*------------------Определяем, какой элемент был нажат-------------------------------*/
		while(target_y < y){
			i++;
			target_y = target_y + delta;
		}
		/*------------------устанавливаем нижний элемент последним, а выбранный первым--------*/
		while(j >= (i - 1)){
			chooseAimLayout.bringChildToFront(child[j]);
			j--;
		}
		/*------------------устанавливаем элементы между нижним и выбранным на свои места------*/
		j++;
		while(j <= k){
			chooseAimLayout.bringChildToFront(child[j]);
			j++;
		}
		chooseAimLayout.bringChildToFront(child[i - 1]);//возвращаем выбранный элемент на первое место
		
		/*----------------------меняем позиции нижнего и выбранного элемента---------*/
		int where_to_move = (child_count - i) * delta;
		int from_where_to_move = Math.abs(((i - child_count) * delta));
		
		TranslateAnimation slideDown = new TranslateAnimation (0, 0, 0, where_to_move);
		slideDown.setDuration(550);
		slideDown.setFillEnabled(true);
		child[i-1].startAnimation(slideDown);
		
		final int s = i;
		TranslateAnimation slideUp = new TranslateAnimation (0, 0, 0, -from_where_to_move);
		slideUp.setDuration(550);
		slideUp.setFillEnabled(true);
		slideUp.setAnimationListener(new Animation.AnimationListener(){
		    public void onAnimationStart(Animation slideUp) {
		    }           
		    public void onAnimationRepeat(Animation slideUp) {
		    }           
		    public void onAnimationEnd(Animation slideUp) {
		    	swapViews(s);
		    }
		});
		child[child_count - 1].startAnimation(slideUp);
	}
	
	public void swapViews(int i){
		if(i > 0){
			int chosen_y = (int) child[i - 1].getY();
			int lowest_y = (int) child[child_count - 1].getY();
			View swap;
			swap = child[i-1];
			child[i-1] = child[child_count - 1];
			child[child_count - 1] = swap;
			child[i - 1].setY(chosen_y);
			child[child_count - 1].setY(lowest_y);
		}
	}

}