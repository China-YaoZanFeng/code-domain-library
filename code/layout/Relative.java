package org.domain.code.layout;

import android.widget.RelativeLayout;
import org.domain.code.App;
import org.domain.code.widget.ViewGroup;

public class Relative extends ViewGroup {

    /*
     *控件：相对布局
     *@天才工作
     */

    public Relative() {
        if (App.action() != null) {
            VIEW = new RelativeLayout(App.action());
        }
    }

    @Override
    public RelativeLayout view() {
        return (RelativeLayout) VIEW;
    }
}
