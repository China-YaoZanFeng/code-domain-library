package org.domain.code.widget;

import org.domain.code.App;
import org.domain.code.Convert;

import java.util.ArrayList;

public class ViewGroup extends View {

    public ArrayList<android.view.View> viewList = new ArrayList<>();

    public ViewGroup() {
    }

    //控件数量
    public int size() {
        int result = -1;
        try {
            result = viewList.size();
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //添加控件
    public void add(Object view) {
        if (view != null && view instanceof android.view.View) {
            view().addView(((View) view).view());
            viewList.add((android.view.View) view);
        }
    }

    //移除指定控件
    public void remove(Object view) {
        if (view() != null && (view = Convert.to(Convert.INT, view)) != null) {
            view().removeViewAt((int) view);
            viewList.remove((int) view);
        } else if (view() != null && view instanceof android.view.View) {
            view().removeView(((View) view).view());
            viewList.remove(((View) view).view());
        }
    }

    //移除所有控件
    public void removeALl(Object id) {
        if (view() != null) {
            view().removeAllViews();
            viewList.clear();
        }
    }

    public android.view.ViewGroup view() {
        return (android.view.ViewGroup) VIEW;
    }

}
