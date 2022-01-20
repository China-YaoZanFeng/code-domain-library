package org.domain.code.layout;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class ReboundScroll extends ScrollView {
    private static final int ANIM_TIME = 300;
    private static final float MOVE_DELAY = 0.3f;
    private static final String TAG = "ReboundScrollView";
    private boolean canPullDown;
    private boolean canPullUp;
    private int changeY;
    private View childView;
    private boolean havaMoved;
    private MyScrollListener listener;
    private Rect originalRect = new Rect();
    private float startY;

    public interface MyScrollListener {
        void onMyScrollEvent(int i, float f);
    }

    public ReboundScroll(Context context) {
        super(context);
    }

    public ReboundScroll(Context context, AttributeSet attributeSet) {
		super(context,attributeSet);
    }

    public ReboundScroll(Context context, AttributeSet attributeSet, int i) {
		super(context,attributeSet,i);
    }

    private boolean isCanPullDown() {
        return getScrollY() == 0 || this.childView.getHeight() < getHeight() + getScrollY();
    }

    private boolean isCanPullUp() {
        return this.childView.getHeight() <= getHeight() + getScrollY();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int i = 0;
        if (this.childView == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        switch (action) {
            case 0:
                this.canPullDown = isCanPullDown();
                this.canPullUp = isCanPullUp();
                this.startY = motionEvent.getY();
                break;
            case 1:
            case 3:
                if (this.havaMoved) {
                    TranslateAnimation translateAnimation = new TranslateAnimation(0.0f,0.0f, (float) this.childView.getTop(), (float) this.originalRect.top);
                    translateAnimation.setDuration((long) ANIM_TIME);
                    this.childView.startAnimation(translateAnimation);
                    this.canPullDown = false;
                    this.canPullUp = false;
                    this.havaMoved = false;
                    resetViewLayout();
                    if (this.listener != null) {
                        this.listener.onMyScrollEvent(action, (float) this.changeY);
                        break;
                    }
                }
                break;
            case 2:
                if (!this.canPullDown && !this.canPullUp) {
                    this.startY = motionEvent.getY();
                    this.canPullDown = isCanPullDown();
                    this.canPullUp = isCanPullUp();
                    break;
                }
                action = (int) (motionEvent.getY() - this.startY);
                this.changeY = action;
                if ((this.canPullDown && action > 0) || ((this.canPullUp && action < 0) || (this.canPullUp && this.canPullDown))) {
                    i = 1;
                }
                if (i != 0) {
                    i = (int) (((float) action) * MOVE_DELAY);
                    this.childView.layout(this.originalRect.left, this.originalRect.top + i, this.originalRect.right, i + this.originalRect.bottom);
                    this.havaMoved = true;
                    break;
                }
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override
    public void fling(int i) {
        super.fling(i / 2);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
    }

    @Override
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        Log.d(TAG, "onlayout");
        if (getChildCount() > 0) {
            this.childView = getChildAt(0);
        }
        if (this.childView != null) {
            this.originalRect.set(this.childView.getLeft(), this.childView.getTop(), this.childView.getRight(), this.childView.getBottom());
        }
    }

    @Override
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        if (this.listener != null) {
            this.listener.onMyScrollEvent(-1, (float) i2);
        }
        super.onOverScrolled(i, i2, z, z2);
    }

    public void resetViewLayout() {
        this.childView.layout(this.originalRect.left, this.originalRect.top, this.originalRect.right, this.originalRect.bottom);
    }

    public void setListener(MyScrollListener myScrollListener) {
        this.listener = myScrollListener;
    }
}
