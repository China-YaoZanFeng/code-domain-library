package org.domain.code.layout;

import android.widget.GridLayout;
import org.domain.code.App;
import org.domain.code.Convert;
import org.domain.code.widget.ViewGroup;

public class Grid extends ViewGroup {

    /*
     *控件：宫格布局
     *@天才工作
     */

    public Grid() {
        if (App.action() != null) {
            VIEW = new GridLayout(App.action());
        }
    }

    //列数
    public int column() {
        return view().getColumnCount();
    }

    public void column(Object count) {
        if ((count = Convert.to(Convert.INT, count)) != null) {
            view().setColumnCount((int) count);
        }
    }

    //行数
    public int row() {
        return view().getRowCount();
    }

    public void row(Object count) {
        if ((count = Convert.to(Convert.INT, count)) != null) {
            view().setRowCount((int) count);
        }
    }

    public GridLayout view() {
        return (GridLayout) VIEW;
    }
}
