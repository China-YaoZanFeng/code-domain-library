package org.domain.code.layout;

import android.widget.LinearLayout;
import org.domain.code.App;
import org.domain.code.Convert;
import org.domain.code.widget.ViewGroup;

public class Linear extends ViewGroup {

    /*
     *控件：线性布局
     *@天才工作
     */

    public void Linear() {
        if (App.action() != null) {
            VIEW = new LinearLayout(App.action());
        }
    }

    //方向
    public void orientation(Object value) {
        if (value != null) {
            if (Convert.to(Convert.INT, value) != null) {
                view().setOrientation((int) Convert.to(Convert.INT, value));
            }
        } else if (value.toString().equals("vertical")) {
            orientation(1);
        } else if (value.toString().equals("horizontal")) {
            orientation(0);
        }
    }

    @Override
    public LinearLayout view() {
        return (LinearLayout) VIEW;
    }
}
