package org.domain.code.widget;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import org.domain.code.App;

import java.util.ArrayList;
import java.util.List;

public class Spinner extends View {

    /*
     *控件：下拉列表框
     *@天才工作
     */

    private final List<String> item = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private Event event;

    public Spinner() {

        try {

            if (App.action() != null) {
                VIEW = new android.widget.Spinner(App.action());

                adapter = new ArrayAdapter<String>(App.action(), android.R.layout.simple_list_item_1, item);
                view().setAdapter(adapter);

                view().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                        eventSelected(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public void add(Object text) {
        try {
            if (text != null) {
                item.add(text.toString());
                adapter.notifyDataSetChanged();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public void delete(Object index) {
        try {
            if (index != null) {

            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public int get() {
        int result = 0;
        return result;
    }

    public void clear() {

    }

    public int size() {
        int result = -1;
        try {
            result = item.size();
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public void event(Event Event) {
        if (Event != null) {
            event = Event;
        }
    }

    public android.widget.Spinner view() {
        return (android.widget.Spinner) VIEW;
    }

    private void eventSelected(int index) {
        if (event != null) {
            event.selected(index);
        }
    }

    public interface Event {
        void selected(int index);//选中
    }

}
