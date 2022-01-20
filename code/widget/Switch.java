package org.domain.code.widget;

import android.widget.CompoundButton;
import org.domain.code.App;
import org.domain.code.Convert;
import org.domain.code.Text;

public class Switch extends Button {

    /*
     *控件：开关
     *@天才工作
     */

    private Event event;

    public Switch() {
        if (App.action() != null) {
            VIEW = new android.widget.Switch(App.action());
            view().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton button, boolean checked) {
                    eventChecked(checked);
                }
            });

        }
    }

    //选中状态
    public boolean checked() {
        boolean result = false;
        result = view().isChecked();
        return result;
    }

    public void checked(Object state) {
        if ((state = Convert.to(Convert.BOOLEAN, state)) != null) {
            view().setChecked((boolean) state);
        }
    }

    //打开文本
    public String textOn() {
        return view().getTextOn().toString();
    }

    public void textOn(Object text) {
        text = (text == null) ? Text.NULL : text;
        view().setTextOn(text.toString());
    }

    //关闭文本
    public String textOff() {
        return view().getTextOff().toString();
    }

    public void textOff(Object text) {
        text = (text == null) ? Text.NULL : text;
        view().setTextOff(text.toString());
    }


    public android.widget.Switch view() {
        return (android.widget.Switch) VIEW;
    }

    public void event(Event Event) {
        if (Event != null) {
            event = Event;
        }
    }

    public void eventChecked(boolean state) {
        if (event != null) {
            event.checked(state);
        }
    }

    public interface Event {
        void checked(boolean state);//选中状态事件
    }


}
