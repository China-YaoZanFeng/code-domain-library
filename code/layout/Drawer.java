package org.domain.code.layout;

import android.view.Gravity;
import org.domain.code.App;
import org.domain.code.Convert;
import org.domain.code.widget.ViewGroup;
import org.domain.code.layout.drawer.DrawerLayout;

public class Drawer extends ViewGroup {

    private Event event;

    /*
     *控件：侧滑布局
     *@天才工作
     */

    public Drawer() {
        if (App.action() != null) {
            VIEW = new DrawerLayout(App.action());

            view().setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(android.view.View drawerView, float slideOffset) {
                }

                @Override
                public void onDrawerOpened(android.view.View drawerView) {
                    //侧滑打开
                    eventShow();
                }

                @Override
                public void onDrawerClosed(android.view.View drawerView) {
                    //侧滑关闭
                    eventDismiss();
                }

                @Override
                public void onDrawerStateChanged(int newState) {
                }
            });
        }
    }

    //阴影
    public void elevation(Object value) {
        if (view() != null && (value = Convert.to(Convert.INT, value)) != null) {
            view().setElevation((int) value);
        }
    }

    //左侧布局
    public void left(Object view) {
        if (view != null) {
            if (view instanceof org.domain.code.widget.View || view instanceof ViewGroup) {
                view = ((org.domain.code.widget.View) view).view();
            }
            DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) ((android.view.View) view).getLayoutParams();
            params.gravity = Gravity.LEFT;
            ((android.view.View) view).setLayoutParams(params);
        }
    }

    //右侧布局
    public void right(Object view) {
        if (view != null) {
            if (view instanceof org.domain.code.widget.View || view instanceof ViewGroup) {
                view = ((org.domain.code.widget.View) view).view();
            }
            DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) ((android.view.View) view).getLayoutParams();
            params.gravity = Gravity.RIGHT;
            ((android.view.View) view).setLayoutParams(params);
        }
    }

    //显示
    public void show() {
        show(Gravity.LEFT);
    }

    public void show(Object value) {
        if (value != null) {
            if (Convert.to(Convert.INT, value) != null) {
                value = Convert.to(Convert.INT, value);
                view().openDrawer((int) value);
            } else if (value.toString().equals("left")) {
                show(Gravity.LEFT);
            } else if (value.toString().equals("girht")) {
                show(Gravity.RIGHT);
            }
        }
    }

    //关闭
    public void dismiss() {
        view().closeDrawers();
    }

    public void dismiss(Object value) {
        if (value != null) {
            if (Convert.to(Convert.INT, value) != null) {
                value = Convert.to(Convert.INT, value);
                view().closeDrawer((int) value);
            } else if (value.toString().equals("left")) {
                dismiss(Gravity.LEFT);
            } else if (value.toString().equals("girht")) {
                dismiss(Gravity.RIGHT);
            }
        }
    }


    public DrawerLayout view() {
        return (DrawerLayout) VIEW;
    }

    public void event(Event Event) {
        if (Event != null) {
            event = Event;
        }
    }

    private void eventShow() {
        if (event != null) {
            event.show();
        }
    }

    private void eventDismiss() {
        if (event != null) {
            event.dismiss();
        }
    }

    public interface Event {
        void show();

        void dismiss();
    }

}
