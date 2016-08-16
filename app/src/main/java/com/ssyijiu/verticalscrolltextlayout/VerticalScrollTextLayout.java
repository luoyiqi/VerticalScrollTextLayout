package com.ssyijiu.verticalscrolltextlayout;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssyijiu on 2016/8/16.
 * Github: ssyijiu
 * E-mail: lxmyijiu@163.com
 */
public class VerticalScrollTextLayout extends FrameLayout{

	/** Text集合 */
	private List<String> mTextList = new ArrayList<>();

	/** 滚动时间 */
	private int mScrollTime = 500;

	/** 静止时间 */
	private int mStaticTime = 0;

	private TextView mTextViewBottom;

	private TextView mTextViewTop;

	/** TextView的高度*/
	private int mTextViewHeight;

	/** 当前Text集合索引 */
	private int mIndex = 0;

	/** 滚出动画*/
	private TranslateAnimation outAnim;

	/** mTextViewTop滚入动画*/
	private TranslateAnimation topInAnim;

	/** mTextViewBottom滚入动画*/
	private TranslateAnimation bottomInAnim;

	private boolean isAnimInited = false;


	private Handler mHandler = new Handler() ;

	public VerticalScrollTextLayout(Context context) {
		this(context,null);
	}

	public VerticalScrollTextLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public VerticalScrollTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	/**
	 * 初始化滚入滚出动画
	 */
	private void initAnimation() {


		outAnim = new TranslateAnimation(0,0,0,-mTextViewHeight);
		outAnim.setDuration(mScrollTime);
		outAnim.setFillAfter(true);
		topInAnim = new TranslateAnimation(0,0, mTextViewHeight,0);
		topInAnim.setDuration(mScrollTime);
		topInAnim.setFillAfter(true);

		bottomInAnim = new TranslateAnimation(0,0, mTextViewHeight,0);
		bottomInAnim.setDuration(mScrollTime);
		bottomInAnim.setFillAfter(true);

		topInAnim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mHandler.postDelayed(start, mStaticTime);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		bottomInAnim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mHandler.postDelayed(end, mStaticTime);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		isAnimInited = true;
	}


	/**
	 * 设置要展示的数据集合，集合为 null或者size=0 什么也不显示
	 * 这个方法在调用后，数据集中的数据开始滚动显示。
	 * @param textList
	 * @return
     */
	public VerticalScrollTextLayout setTextList(List<String> textList) {
		if (this.mTextList != null || this.mTextList.size() == 0) {
			this.mTextList.clear();
		}
		this.mTextList.addAll(textList);
		if(mTextList == null || mTextList.size() == 0) {
			return this;
		}

		if(mTextList.size() < 2){
			mTextViewTop.setText(mTextList.get(0));
			mTextViewBottom.setVisibility(View.GONE);
		} else{
			mIndex = mIndex % mTextList.size();
			mTextViewTop.setText(mTextList.get(mIndex));
		}

		return this;
	}

	public void startScroll() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!isAnimInited);
				mHandler.postDelayed(start, mStaticTime);
			}
		}).start();


	}



	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mTextViewBottom = (TextView) this.getChildAt(0);
		mTextViewTop = (TextView) this.getChildAt(1);

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mTextViewHeight = mTextViewTop.getHeight();
		initAnimation();
	}

	Runnable start = new Runnable(){
		@Override
		public void run() {

			if(mTextList.size() < 2) {
				return;
			}

			mTextViewTop.startAnimation(outAnim);

			mIndex ++;
			mIndex = mIndex % mTextList.size();
			mTextViewBottom.setText(mTextList.get(mIndex));
			mTextViewBottom.startAnimation(bottomInAnim);
		}

	};

	Runnable end = new Runnable(){
		@Override
		public void run() {
			mTextViewBottom.startAnimation(outAnim);

			mIndex ++;
			mIndex = mIndex % mTextList.size();
			mTextViewTop.setText(mTextList.get(mIndex));
			mTextViewTop.startAnimation(topInAnim);
		}

	};


	public List<String> getTextList() {
		return this.mTextList;
	}

	public int getScrollTime() {
		return mScrollTime;
	}

	public VerticalScrollTextLayout setScrollTime(int scrollTime) {
		this.mScrollTime = scrollTime;
		if(mScrollTime < 0) {
			mScrollTime = 0;
		}
		return this;
	}

	public int getStaticTime() {
		return mStaticTime;
	}

	/**
	 * 设置文字静止时间
	 * @param staticTime 静止毫秒
	 * @return
     */
	public VerticalScrollTextLayout setStaticTime(int staticTime) {
		this.mStaticTime = staticTime;
		if(mStaticTime < 0) {
			mStaticTime = 0;
		}
		return this;
	}

	public int getIndex() {
		return mIndex;
	}

	public String getIndexText() {
		return mTextList.get(mIndex);
	}

	public void stopScroll() {
		mHandler.removeCallbacksAndMessages(null);
	}
}
