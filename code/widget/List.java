package org.domain.code.widget;

import android.widget.*;
import org.domain.code.App;

import java.util.ArrayList;

public class List extends View {

    private ArrayAdapter adapter = null;
    private ArrayList<String> item = null;
    private Event event;
    private boolean stop = false;

    public List() {
        try {
            if (App.action() != null) {
                VIEW = new ListView(App.action());
                item = new ArrayList<String>();
                adapter = new ArrayAdapter<>(App.action(), android.R.layout.simple_list_item_1, item);
                view().setAdapter(adapter);
                view().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {

                    }
                });
                view().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                        return false;
                    }
                });
                view().setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (stop) {
                            //判断顶部底部
                            if (firstVisibleItem == 0) {
                                android.view.View firstVisibleItemView = view().getChildAt(0);
                                if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                                    upScroll();
                                }
                            } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                                android.view.View lastVisibleItemView = view().getChildAt(view().getChildCount() - 1);
                                if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == view().getHeight()) {
                                    lowScroll();
                                }
                            }
                            //判断滑动
                            if (firstVisibleItem < view().getLastVisiblePosition()) {
                                upScrollIng();
                            } else if (firstVisibleItem > view().getLastVisiblePosition()) {
                                lowScrollIng();
                            }
                        }
                    }

                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        switch (scrollState) {
                            case SCROLL_STATE_IDLE:
                                stop = true;
                                break;
                            default:
                                stop = false;
                                break;
                        }
                        scrollIngChang();
                    }

                });
                view().setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);

            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public interface Event {
        //项目点击
        void itemClick(int index);

        //项目长按
        void itemLongClick(int index);

        //滚动状态改变
        void scrollIngChang();

        //滚动到顶部
        void upScroll();

        //往顶部滚动中
        void upScrollIng();

        //滚动到底部
        void lowScroll();

        //往底部滚动中
        void lowScrollIng();
    }

    public void itemClick(int index) {
        try {
            if (event != null) {
                event.itemClick(index);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public void itemLongClick(int index) {
        try {
            if (event != null) {
                event.itemLongClick(index);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public void upScroll() {
        try {
            if (event != null) {
                event.upScroll();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public void upScrollIng() {
        try {
            if (event != null) {
                event.upScrollIng();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public void lowScroll() {
        try {
            if (event != null) {
                event.lowScroll();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public void lowScrollIng() {
        try {
            if (event != null) {
                event.lowScrollIng();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public void scrollIngChang() {
        try {
            if (event != null) {
                event.scrollIngChang();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }


    public void event(Event Event) {
        if (Event != null) {
            event = Event;
        }
    }

    public ListView view() {
        return (ListView) VIEW;
    }

}
