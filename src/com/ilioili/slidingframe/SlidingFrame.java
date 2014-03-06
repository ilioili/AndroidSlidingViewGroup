package com.ilioili.slidingframe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlidingFrame extends ViewGroup{
	
	private int animationDuration = 400;
	private View centreView;
	private float firstX,firstY;
	/**
	 * �Ƿ�����������View
	 */
	private boolean hasLeftView;
	/**
	 * �Ƿ��������Ҳ��View
	 */
	private boolean hasRightView;
	private boolean interceptFlag;
	private int lastPosition;
	private View leftShadow;
	
	private int leftShadowWidth;
	private View leftView;
	
	/**
	 * ���View�Ŀ��
	 */
	private int leftWidth;
	
	/**
	 * �м�View���Ͻǵ�X����
	 */
	private int position = 0;
	private View rightShadow;
	private int rightShadowWidth;
	private View rightView;
	/**
	 * �ұ�View�Ŀ��
	 */
	private int rightWidth;
	private Scroller scroller;
	final private int STATE_CENTRE_IDLE = 3;
	final private int STATE_CENTRE_TO_LEFT = 4;
	final private int STATE_CENTRE_TO_RIGHT = 6;
	final private int STATE_LEFT_IDLE = 1;
	final private int STATE_LEFT_TO_CENTRE = 5;
	final private int STATE_RIGHT_IDLE = 2;
	final private int STATE_RIGHT_TO_CENTRE = 7;
	private int slidingState = STATE_CENTRE_IDLE;
	public SlidingFrame(Context context) {
		super(context);
		init(context);
	}
	public SlidingFrame(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void addAllViews() {
		addView(leftView);
		addView(rightView);
		addView(leftShadow);
		addView(rightShadow);
		addView(centreView);
	}
	
	@Override
	public void computeScroll() {
		if(scroller.computeScrollOffset()){
			position = scroller.getCurrX();
			if(position>=leftWidth){
				position = leftWidth;
				scroller.forceFinished(true);
				slidingState = STATE_RIGHT_IDLE;
				if(null!=listener) listener.toTheRight();
			}else if(position<=-rightWidth){
				scroller.forceFinished(true);
				position = -rightWidth;
				slidingState = STATE_LEFT_IDLE;
				if(null!=listener) listener.toTheLeft();
			}else if(position==0){
				scroller.forceFinished(true);
				slidingState = STATE_CENTRE_IDLE;
				if(null!=listener) listener.toTheCentre();
			}
			layoutChildren();
			postInvalidate();//�����ÿ��ܿ���������Ч������Ҫ����invalidate()
		}else{
			if(position==0){
				slidingState = STATE_CENTRE_IDLE;
			}
		}
	}
	@SuppressLint("NewApi")
	private void layoutChildren(){
		boolean canAlpha = android.os.Build.VERSION.SDK_INT>10;
		int r = getMeasuredWidth();
		int b = getMeasuredHeight();
		
		if(position<0){
			leftView.setVisibility(View.GONE);
			leftShadow.setVisibility(View.GONE);
			rightView.setVisibility(View.VISIBLE);
			rightShadow.setVisibility(View.VISIBLE);
			rightView.layout(r+position/2-rightWidth/2, 0, r+position/2+rightWidth/2, b);
			if(rightShadowWidth!=0){
				rightShadow.layout(position+r, 0, position+rightShadowWidth+r, b);
			}
			if(canAlpha) rightView.setAlpha((-position*0.9f)/rightWidth+0.1f);
		}else if(position>0){
			rightView.setVisibility(View.GONE);
			rightShadow.setVisibility(View.GONE);
			leftView.setVisibility(View.VISIBLE);
			leftShadow.setVisibility(View.VISIBLE);
			leftView.layout(-leftWidth/2+position/2, 0, leftWidth/2+position/2, b);
			if(leftShadowWidth!=0){
				leftShadow.layout(position-leftShadowWidth, 0, position, b);
			}
			if(canAlpha) leftView.setAlpha(position*0.9f/leftWidth+0.1f);
		}else{
			leftView.setVisibility(View.GONE);
			leftShadow.setVisibility(View.GONE);
			rightView.setVisibility(View.GONE);
			rightShadow.setVisibility(View.GONE);
			
		}
		centreView.layout(position, 0, r+position, b);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}
	
	private void init(Context c){
		leftView = new View(c);
		centreView = new View(c);
		rightView = new View(c);
		leftShadow = new View(c);
		rightShadow = new View(c);
		setBackgroundColor(Color.BLACK);
		addAllViews();
		scroller = new Scroller(c);
	}
	
	/*	private int measureWidth(int measureSpec) {
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		// Default size if no limits are specified.
		int result = screenWidth;
		if (specMode == MeasureSpec.AT_MOST){
			// Calculate the ideal size of your control
			// within this maximum size.
			// If your control fills the available space
			// return the outer bound.
			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY){
			result = specSize;
		}
		return result;
	}*/
	
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//		System.out.println("SlidingFrame onInterceptTouchEvent()");
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			firstX = ev.getX();
			firstY = ev.getY();
			lastPosition = position;
			interceptFlag=false;
			break;
		case MotionEvent.ACTION_MOVE:
			if(!interceptFlag){
				float dx = ev.getX()-firstX;
				float dy = ev.getY()-firstY;
				if(Math.abs(dx)+Math.abs(dy)>1 && Math.abs(dx)>Math.abs(dy)){
					interceptFlag=true;
				}
			}
		}
		return interceptFlag;
	}
	
	
	
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		layoutChildren();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		System.out.println("onMeasure()");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int childWidhtMeasureSpec;
		if(hasLeftView){
			measureMyChild(leftView, widthMeasureSpec, heightMeasureSpec);
			leftWidth = leftView.getMeasuredWidth();
		}
		if(hasRightView){
			measureMyChild(rightView, widthMeasureSpec, heightMeasureSpec);
			rightWidth = rightView.getMeasuredWidth();
		}
		childWidhtMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, getMeasuredWidth());
		centreView.measure(childWidhtMeasureSpec, heightMeasureSpec);
	}
	
	private void measureMyChild(View childView, int widthMeasureSpec, int heightMeasureSpec){
		int childWidthMeasureSpec = 0;
		if(childView.getLayoutParams().width == LayoutParams.WRAP_CONTENT){
			childWidthMeasureSpec = getChildMeasureSpec(LayoutParams.WRAP_CONTENT, 0, childView.getLayoutParams().width);
		}else {
			childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childView.getLayoutParams().width, MeasureSpec.EXACTLY);
		}
		measureChild(childView, childWidthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		System.out.println("SlidingFrame onTouchEvent()");
		switch(ev.getAction()){
		case MotionEvent.ACTION_UP:
			smoothScroll();
			return false;
		case MotionEvent.ACTION_DOWN://onInterceptTouchEvent ����true��������
			if(!scroller.isFinished()){
				scroller.forceFinished(true);
			}
			firstX = ev.getX();
			return true;
		case MotionEvent.ACTION_MOVE:
			if(!scroller.isFinished()){
				scroller.forceFinished(true);
			}
			float distanceX = ev.getX()-firstX;
			float dx = position-lastPosition-distanceX;
			if(dx<1&dx>-1) return false;
//			System.out.println("position="+position);
			if(slidingState==STATE_LEFT_IDLE && dx>0){
				return true;
			}else if(slidingState==STATE_RIGHT_IDLE && dx<0){
				return true;
			}
			position = (int) (lastPosition+distanceX);//dx>0 ���󻬶�
			if(position>leftWidth){
				position = leftWidth;
			}else if(position<-rightWidth){
				position = -rightWidth;
			}
			if(position>0){
				if(dx>0){
					slidingState = STATE_RIGHT_TO_CENTRE;
				}else{
					slidingState = STATE_CENTRE_TO_RIGHT;
				}
			}else if(position<0){
				if(dx>0){
					slidingState = STATE_CENTRE_TO_LEFT;
				}else{
					slidingState = STATE_LEFT_TO_CENTRE;
				}
			}
			requestLayout();
			return true;
		}
		return false;
	}
	/**
	 * ���ö�������ʱ�䣬��λ����
	 */
	public void setAnimationDuration(int duration){
		animationDuration = duration;
	}
	/**
	 * �����м��View
	 */
	public void setCentreView(View v){
		removeAllViews();
		centreView = v;
		addAllViews();
	}
	/**
	 * ���������Ӱ
	 * @param resid ������ԴID
	 * @param width ��Ӱ��ȣ���λ����
	 */
	public void setLeftShadow(int resid, int width){
		leftShadow.setBackgroundResource(resid);
		leftShadowWidth = width;
	}
	/**
	 * ��������View
	 */
	public void setLeftView(View v){
		removeAllViews();
		hasLeftView = true;
		leftView = v;
		addAllViews();
	}
	/**
	 * �����Ҳ���Ӱ
	 * @param resid ������ԴID
	 * @param width ��Ӱ��ȣ���λ����
	 */
	public void setRightShadow(int resid, int width){
		rightShadow.setBackgroundResource(resid);
		rightShadowWidth = width;
	}
	/**
	 * �����Ҳ��View
	 */
	public void setRightView(View v){
		removeAllViews();
		hasRightView = true;
		rightView = v;
		addAllViews();
	}
	/**
	 * ��������м��򻬶����м�
	 */
	public void slideToCentre(){
		if(position>0){
			scroller.forceFinished(true);
			slidingState = STATE_RIGHT_TO_CENTRE;
			smoothScroll();
		}else if(position<0){
			scroller.forceFinished(true);
			slidingState = STATE_LEFT_TO_CENTRE;
			smoothScroll();
		}
	}
	/**
	 * ���м�״̬ʱ�����ô˺����Ử������ࡣ
	 */
	public void slideToLeft(){
		if(hasRightView&&slidingState==STATE_CENTRE_IDLE){
			scroller.forceFinished(true);
			slidingState = STATE_CENTRE_TO_LEFT;
			smoothScroll();
		}
	}
	/**
	 * ���м�״̬ʱ�����ô˺����Ử�����Ҳࡣ
	 */
	public void slideToRight(){
		if(hasLeftView&&slidingState==STATE_CENTRE_IDLE){
			scroller.forceFinished(true);
			slidingState = STATE_CENTRE_TO_RIGHT;
			smoothScroll();
		}
	}
	private void smoothScroll(){
		switch(slidingState){
		case STATE_CENTRE_TO_RIGHT:
			scroller.startScroll(position, 0, leftWidth-position, 0, animationDuration);
			break;
		case STATE_RIGHT_TO_CENTRE:
			scroller.startScroll(position, 0, -position, 0, animationDuration);
			break;
		case STATE_LEFT_TO_CENTRE:
			scroller.startScroll(position, 0, -position, 0, animationDuration);
			break;
		case STATE_CENTRE_TO_LEFT:
			scroller.startScroll(position, 0, -rightWidth-position, 0, animationDuration);
			break;
		}
		invalidate();//�����ÿ���������Ч��
	}
	
	/**
	 *������ֹͣʱ�Ļص������ͣ�£��Ҳ�ͣ�£��м�ͣ��
	 */
	public interface SlidingStateListener{
		/**
		 * ���������ͣ��
		 */
		void toTheLeft();
		/**
		 * �������Ҳ�ͣ��
		 */
		void toTheRight();
		/**
		 * �������м�ͣ��
		 */
		void toTheCentre();
	}
	private SlidingStateListener listener;
	
	/**
	 * @param listener ������ֹͣʱ�Ļص������ͣ�£��Ҳ�ͣ�£��м�ͣ��
	 */
	public void setSlidingStateListener(SlidingStateListener listener){
		this.listener = listener;
	}
}
