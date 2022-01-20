package org.domain.code.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    ArrayList list = null;
    Object[] comment = null;
    Event event = null;

    @Override
    public int getCount() {
        return comment.length == 0 ? 0 : comment.length;
    }

    @Override
    public Object getItem(int position) {
        return comment.length == 0 ? null : comment[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return load(position);
    }

    public Adapter(Object dataList) {
        if(dataList instanceof ArrayList) {
            list = (ArrayList) dataList;
            comment = ((ArrayList) dataList).toArray(new Object[0]);
        } else if(dataList instanceof String[]) {
            comment = (String[]) dataList;
        }
    }

    public int count() {
        return getCount();
    }


    public interface Event {
        View load(int index);
    }

    private View load(int index) {
        return event.load(index);
    }

    public void event(Event Event) {
        if(Event != null) {
            event = Event;
        }
    }


}
