package com.invest.yocle;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by pong2 on 10/31/2016.
 */

public class SwipeWebView extends WebView {
    Context context = null;
    private SwipeRefreshLayout swipe;

    public SwipeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public SwipeWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }
    public SwipeWebView(Context context) {
        super(context);
    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if(clampedX || clampedY){
            swipe = ((SwipeWebViewInterface) context).returnSwipeObject();
            swipe.setRefreshing(true);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if(event.getActionMasked()==MotionEvent.ACTION_UP){
            swipe = ((SwipeWebViewInterface) context).returnSwipeObject();
            swipe.setRefreshing(false);
            return false;
        }

        return false;
    }
}