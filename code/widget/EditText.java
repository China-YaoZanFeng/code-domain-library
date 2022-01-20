package org.domain.code.widget;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import org.domain.code.App;
import org.domain.code.Color;
import org.domain.code.Convert;

public class EditText extends Text {

    /*
     *控件：编辑框
     *@天才工作
     */

    private Event event = null;

    public EditText() {
        if (App.action() != null) {
            VIEW = new android.widget.EditText(App.action());
            //文本改变事件
            view().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
                }

                @Override
                public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                    eventTextChange(p1.toString());
                    if (textColorsState) {
                        textColor(textColors);
                    }
                }

                @Override
                public void afterTextChanged(Editable p1) {

                }
            });

        }
    }

    //提示文本
    public String hint() {
        Object result = "";
        if (view() != null) {
            result = view().getHint();
            if (result == null) {
                result = "";
            }
        }
        return result.toString();
    }

    public void hint(Object text) {
        if (VIEW != null) {
            if (text == null) {
                text = "null";
            }
            view().setHint(text.toString());
        }
    }

    //提示文本颜色
    public void hintColor(Object color) {
        if (view() != null) {
            view().setHintTextColor(Color.parse(color));
        }
    }

    //输入类型
    public int inputType() {
        int result = -1;
        if (view() != null) {
            result = view().getInputType();
        }
        return result;
    }

    public void inputType(Object type) {
        try {
            type = Convert.to(Convert.INT, type);
            if (view() != null && type != null) {
                view().setInputType((int) type);
            }
        } catch (Exception exception) {
        }
    }

    //光标位置
    public int selection() {
        int result = -1;
        if (view() != null) {
            result = view().getSelectionStart();
        }
        return result;
    }

    public void selection(Object location) {
        location = Convert.to(Convert.INT, location);
        if (view() != null && location != null) {
            view().setSelection((int) location);
        }
    }

    //选择文本
    public void selection(Object start, Object end) {
        if (view() != null) {
            if (Convert.to(Convert.INT, start) == null) {
                start = 0;
            }
            if (Convert.to(Convert.INT, end) == null) {
                end = view().getText().toString().length();
            }
            view().setSelection((int) start, (int) end);
        }
    }

    //选择全部文本
    public void selectionAll() {
        selection(null, null);
    }

    //插入文本
    public void insert(Object location, Object text) {
        if (view() != null && (location = Convert.to(Convert.INT, location)) != null) {
            if (text == null) {
                text = "null";
            }
            view().getText().insert((int) location, text.toString());
        }
    }

    //追加文本
    public void append(Object text) {
        if (view() != null) {
            if (text == null) {
                text = "null";
            }
            view().getText().append(Convert.to(Convert.STRING, text).toString());
        }
    }

    //密码输入
    public void password(Object status) {
        if (view() != null) {
            boolean result = false;
            if (!(Convert.to(Convert.BOOLEAN, status) == null)) {
                result = (boolean) Convert.to(Convert.BOOLEAN, status);
            }
            if (result) {
                inputType(0x81);
            } else {
                inputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);
            }
        }
    }

    //单行输入
    public void singLeLine(Object status) {
        boolean result = false;
        if (view() != null) {
            if (!(Convert.to(Convert.BOOLEAN, status) == null)) {
                result = (boolean) Convert.to(Convert.BOOLEAN, status);
            }
            view().setSingleLine(result);
        }
    }

    //删除文本
    public void delete(Object start, Object end) {
        if (view() != null) {

            if (start == null || Convert.to(Convert.INT, start) == null) {
                start = 0;
            }
            if (end == null || Convert.to(Convert.INT, start) == null) {
                end = view().getText().toString().length();
            }
            view().getText().delete((int) start, (int) end);
        }
    }

    //显示输入法
    @SuppressLint("WrongConstant")
    public void inputMethod(Object status) {
        if (view() != null && Convert.to(Convert.BOOLEAN, status) != null) {
            if ((boolean) status) {
                ((InputMethodManager) App.action().getSystemService("input_method")).showSoftInput(view(), 0);
            } else {
                ((InputMethodManager) App.action().getSystemService("input_method")).hideSoftInputFromWindow(view().getApplicationWindowToken(), 0);
            }
        }
    }

    public void event(Event Event) {
        event = Event;
    }

    private void eventTextChange(String text) {
        if (event != null && text != null)
            event.text(text);
    }

    @Override
    public android.widget.EditText view() {
        return (android.widget.EditText) VIEW;
    }

    public interface Event {
        void text(String text);
    }

}
