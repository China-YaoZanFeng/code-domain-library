package org.domain.code.layout;

import android.view.View;
import org.domain.code.App;
import org.domain.code.Convert;
import org.domain.code.widget.ViewGroup;
import org.domain.code.layout.page.ArrayPageAdapter;
import org.domain.code.layout.page.PageView;

import java.util.ArrayList;
import java.util.List;

public class Page extends ViewGroup {

    private ArrayPageAdapter adapter;
    private final List<OnPageChangeListener> listeners = new ArrayList<>();
    private Event event;

    public Page() {
        if (App.action() != null) {
            VIEW = new PageView(App.action());
            adapter = new ArrayPageAdapter();
            view().setAdapter(adapter);
            view().setOnPageChangeListener(new PageView.OnPageChangeListener() {
                @Override
                public void onPageChange(View view, int position) {
                    for (OnPageChangeListener listener : listeners) {
                        listener.onPageChange(view, position);
                    }
                    //页面被改变
                    eventChang(position);
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    for (OnPageChangeListener listener : listeners) {
                        listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                    //页面被滑动
                    eventScrocll(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    for (OnPageChangeListener listener : listeners) {
                        listener.onPageSelected(position);
                    }
                    //页面被选中
                    eventSelect(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    for (OnPageChangeListener listener : listeners) {
                        listener.onPageScrollStateChanged(state);
                    }
                    //滑动状态改变
                }

            });

        }
    }

    /*
     *控件：分页布局
     *@天才工作
     */

    public void addOnPageChangeListener(OnPageChangeListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    //页数
    public int count() {
        return adapter.getCount();
    }

    //页边距
    public void margin(Object margin) {
        if ((margin = Convert.to(Convert.INT, margin)) != null) {
            view().setPageMargin((int) margin);
        }
    }

    //添加页
    @Override
    public void add(Object view) {
        if (view != null) {
            if (view instanceof org.domain.code.widget.View || view instanceof ViewGroup) {
                view = ((org.domain.code.widget.View) view).view();
            }
            adapter.add((View) view);
            adapter.notifyDataSetChanged();
        }
    }

    //插入页
    public void insert(Object index, Object view) {
        if ((index = Convert.to(Convert.INT, index)) != null && view != null) {
            if (view instanceof org.domain.code.widget.View || view instanceof ViewGroup) {
                view = ((org.domain.code.widget.View) view).view();
            }
            adapter.insert((int) index, (View) view);
            adapter.notifyDataSetChanged();
        }
    }

    //删除页
    public void delete(Object index) {
        if ((index = Convert.to(Convert.INT, index)) != null) {
            adapter.remove((int) index);
            adapter.notifyDataSetChanged();
        }
    }

    //显示页面
    public void show(Object index) {
        if ((index = Convert.to(Convert.INT, index)) != null) {
            view().showPage((int) index);
        }
    }

    public PageView view() {
        return (PageView) VIEW;
    }

    public void event(Event Event) {
        if (Event != null) {
            event = Event;
        }
    }

    private void eventChang(int index) {
        if (event != null) {
            event.chang(index);
        }
    }

    private void eventSelect(int index) {
        if (event != null) {
            event.select(index);
        }
    }

    private void eventScrocll(int index, float pt, int ps) {
        if (event != null) {
            event.scroll(index, pt, ps);
        }
    }

    public interface OnPageChangeListener {

        void onPageChange(View view, int position);

        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    public interface Event {
        //页面改变
        void chang(int index);

        //页面选中
        void select(int index);

        //页面滑动
        void scroll(int index, float pt, int ps);
    }
}
