package org.domain.code.widget;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import org.domain.code.App;
import org.domain.code.Convert;

public class View {

    public android.view.View VIEW = null;
    public ViewGroup.LayoutParams LP;
    protected int WIDTH = 0;
    protected int HEIGHT = 0;

    public View() {
    }

    //初始化
    private void init() {
        if (VIEW != null)
            if (VIEW.getLayoutParams() == null) {
                LP = new LinearLayout.LayoutParams(-2, -2);
            } else {
                LP = VIEW.getLayoutParams();
            }
        VIEW.setLayoutParams(LP);

    }

    //索引
    public void id(Object id) {
        try {
            VIEW.setId((int) Convert.to(Convert.INT, id));
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //宽度
    public int width() {
        int result = -1;
        try {
            if (VIEW != null) {
                result = VIEW.getWidth();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public void width(Object width) {
        try {
            WIDTH = Convert.px(width);
            LP.width = Convert.px(WIDTH);
            VIEW.setLayoutParams(LP);
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //高度
    public int height() {
        int result = -1;
        try {
            if (VIEW != null) {
                result = VIEW.getHeight();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public void height(Object height) {
        try {
            if (VIEW != null) {
                HEIGHT = Convert.px(height);
                LP.height = HEIGHT;
                VIEW.setLayoutParams(LP);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public android.view.View view() {
        return new android.view.View(App.action());
    }


}

