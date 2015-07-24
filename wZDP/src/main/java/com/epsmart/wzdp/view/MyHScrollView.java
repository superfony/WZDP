package com.epsmart.wzdp.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/*
 * 自定义的 滚动控件
 * */
public class MyHScrollView extends HorizontalScrollView {
	ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();

	public MyHScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyHScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyHScrollView(Context context) {
		super(context);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		/*
		 * 当滚动条移动后，引发 滚动事件。�?知给观察者，观察者会传达给其他的�?
		 */
		if (mScrollViewObserver != null /*&& (l != oldl || t != oldt)*/) {
			mScrollViewObserver.NotifyOnScrollChanged(l, t, oldl, oldt);
		
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}

	/*
	 * 订阅 本控�?�?滚动条变化事�?
	 * */
	public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
		mScrollViewObserver.AddOnScrollChangedListener(listener);
	}

	/*
	 * 取消 订阅 本控�?�?滚动条变化事�?
	 * */
	public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
		mScrollViewObserver.RemoveOnScrollChangedListener(listener);
	}

	/*
	 * 当发生了滚动事件�?
	 */
	public interface OnScrollChangedListener {
		void onScrollChanged(int l, int t, int oldl, int oldt);
		
	}

	/*
	 * 观察�?
	 */
	public static class ScrollViewObserver {
		List<OnScrollChangedListener> mList;

		public ScrollViewObserver() {
			super();
			mList = new ArrayList<OnScrollChangedListener>();
		}

		public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
			mList.add(listener);
		}

		public void RemoveOnScrollChangedListener(
				OnScrollChangedListener listener) {
			mList.remove(listener);
		}

		public void NotifyOnScrollChanged(int l, int t, int oldl, int oldt) {
			if (mList == null || mList.size() == 0) {
				return;
			}
			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i) != null) {
					mList.get(i).onScrollChanged(l, t, oldl, oldt);
				}
			}
		}
	}
}
