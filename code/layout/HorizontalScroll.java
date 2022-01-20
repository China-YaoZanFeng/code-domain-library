package org.domain.code.layout;

import android.widget.HorizontalScrollView;
import org.domain.code.App;
import org.domain.code.Convert;
import org.domain.code.widget.ViewGroup;

public class HorizontalScroll extends ViewGroup {

    /*
     *控件：横向滚动布局
     *@天才工作
     */

    public HorizontalScroll() {
        if (App.action() != null) {
            VIEW = new HorizontalScrollView(App.action());
        }
    }

    public void to(Object x, Object y) {
        if ((x = Convert.to(Convert.INT, x)) != null && (y = Convert.to(Convert.INT, y)) != null) {
            view().scrollTo((int) x, (int) y);
        }
    }

    //滚动条显示
    public void bars(Object status) {
        if (status != null && (status = Convert.to(Convert.BOOLEAN, status)) != null) {
            view().setVerticalScrollBarEnabled((boolean) status);
        }
    }

    //完全视图
    public void fill(Object status) {
        if (status != null && (status = Convert.to(Convert.BOOLEAN, status)) != null) {
            view().setFillViewport((boolean) status);
        }
    }

    @Override
    public HorizontalScrollView view() {
        return (HorizontalScrollView) VIEW;
    }
}
