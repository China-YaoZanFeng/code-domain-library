package org.domain.code.layout;

import android.widget.LinearLayout;
import org.domain.code.App;
import org.domain.code.widget.View;

public class Layouter extends View  {

    public Layouter() {
        VIEW = new LinearLayout(App.action());
    }

    public boolean add(Object pid,Object id,Object type) {
        boolean result = false;
        try {
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public boolean add(Object pid,Object id,Object type,Object attr) {
        boolean result = false;
        try {
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public boolean delete(Object id) {
        boolean result = false;
        try {
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public boolean move(Object oldId,Object newId,Object attr) {
        boolean result = false;
        try {
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }




    @Override
    public LinearLayout view() {
        return (LinearLayout)VIEW;
    }

    public interface Event {
        void chang();
    }

}
