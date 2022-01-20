package org.domain.code.widget;

import org.domain.code.App;

public class Button extends Text {

    public Button() {
        if (App.action() != null) {
            VIEW = new android.widget.Button(App.action());
        }
    }

    public android.widget.Button view() {
        return (android.widget.Button) VIEW;
    }

}
