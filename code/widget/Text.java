package org.domain.code.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.*;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.widget.TextView;
import androidx.annotation.NonNull;
import org.domain.code.App;
import org.domain.code.Color;
import org.domain.code.Convert;

public class Text extends View {

    public String textColors = null;//文本渐变颜色值
    public boolean textColorsState = false;//文本渐变颜色状态
    private Event event;

    /*
     *控件：文本框
     *@天才工作
     */

    public Text() {
        try {
            if (App.action() != null) {
                VIEW = new TextView(App.action());
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    @Override
    public TextView view() {
        return (TextView) VIEW;
    }

    //文本
    public String text() {
        Object result = org.domain.code.Text.EMPTY;

        try {
            result = view() == null ? org.domain.code.Text.EMPTY : view().getText().toString();
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result.toString();
    }

    public void text(Object text) {
        try {
            text = Convert.to(Convert.STRING, text);
            if (App.resString(text) != null) {
                text = App.resString(text);
            }
            ((TextView) VIEW).setText(text.toString());

            if (textColorsState) {
                textColor(textColors);//更新颜色
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //文本大小
    public int textSize() {
        int result = -1;
        try {
            if (view() != null) {
                result = (int) view().getTextSize();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public void textSize(Object size) {
        try {
            if (view() != null && (size = Convert.px(size)) != null) {
                view().setTextSize((int) size);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //文本颜色
    public int textColor() {
        int result = -1;
        try {
            if (view() != null) {
                result = view().getTextColors().getDefaultColor();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public void textColor(Object color) {
        try {
            if (color.toString().contains(",")) {
                //渐变文本颜色
                int[] colors = new int[color.toString().split(",").length];
                int i = 0;
                for (String value : color.toString().split(",")) {
                    colors[i] = Color.parse(value);
                    i++;
                }
                LinearGradient linearGradient = new LinearGradient(0f, 0f, (view().getPaint().getTextSize() * view().getText().length()), 0f, colors, new float[]{0f, 0.7f, 1.0f}, Shader.TileMode.CLAMP);
                view().getPaint().setShader(linearGradient);
                view().invalidate();
                textColors = color.toString();
                textColorsState = true;
            } else {
                view().setTextColor(Color.parse(color));
                textColorsState = false;
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //显示行数
    public void lines(Object line) {
        try {
            if (view() != null && (line = Convert.to(Convert.INT, line)) != null) {
                view().setLines((int) line);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //最大显示行数
    public void maxLine(Object line) {
        try {
            if (view() != null && (line = Convert.to(Convert.INT, line)) != null) {
                view().setMaxLines((int) line);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //最小显示行数
    public void minLine(Object line) {
        try {
            if (view() != null && (line = Convert.to(Convert.INT, line)) != null) {
                view().setMinLines((int) line);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //行间距
    public void lineSpacing(Object spacing) {
        try {
            if (view() != null && spacing instanceof Float) {
                view().setLineSpacing(0f, (float) spacing);
            } else if (view() != null && !(spacing instanceof Float) && (spacing = Convert.to(Convert.INT, spacing)) != null) {
                view().setLineSpacing(0f, (int) spacing);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //可选择文本
    public void selectable(Object state) {
        try {
            if (view() != null && (state = Convert.to(Convert.BOOLEAN, state)) != null) {
                view().setTextIsSelectable((boolean) state);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //字体
    public void typeFace(Object typeface) {
        try {
            if ((typeface = Convert.to(Convert.STRING, typeface)) != null) {
                if (typeface.toString().startsWith("/")) {
                    view().setTypeface(Typeface.createFromAsset(App.action().getAssets(), (String) typeface));
                } else {
                    view().setTypeface(Typeface.createFromFile((String) typeface));
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //字型
    @SuppressLint("WrongConstant")
    public void textStyle(Object style) {
        try {
            if ((style = Convert.to(Convert.STRING, style)) != null) {
                if (style.toString().equals("bold")) {
                    //粗体
                    view().setTypeface(view().getTypeface(), 1);
                } else if (style.toString().equals("italic")) {
                    //斜体
                    view().setTypeface(view().getTypeface(), 2);
                } else if (style.toString().equals("bold-italic") || style.toString().trim().equals("bold,italic")) {
                    //粗斜体
                    view().setTypeface(view().getTypeface(), 3);
                }
            } else {
                //取消字型
                view().setTypeface(view().getTypeface(), 0);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //跑马灯
    public void marquee(Object status) {
        try {
            if (view() != null && (status = Convert.to(Convert.BOOLEAN, status)) != null) {
                if ((boolean) status) {
                    view().setMarqueeRepeatLimit(Integer.MAX_VALUE);
                    view().setFocusable(true);
                    view().setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    view().setSingleLine();
                    view().setFocusableInTouchMode(true);
                    view().setHorizontallyScrolling(true);
                } else {
                    view().setMarqueeRepeatLimit(Integer.MIN_VALUE);
                    view().setFocusable(false);
                    view().setEllipsize(TextUtils.TruncateAt.START);
                    view().setSingleLine(false);
                    view().setFocusableInTouchMode(false);
                    view().setHorizontallyScrolling(false);
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //省略显示
    public void ellipsize(Object mode) {
        if (mode instanceof Integer || Convert.to(Convert.INT, mode) instanceof Integer) {
            mode = Convert.to(Convert.INT, mode);
            if ((int) mode == 0) {
                //省略开头
                view().setEllipsize(TextUtils.TruncateAt.START);
            } else if ((int) mode == 1) {
                //省略中间
                view().setEllipsize(TextUtils.TruncateAt.MIDDLE);
            } else if ((int) mode == 2) {
                //省略尾部
                view().setEllipsize(TextUtils.TruncateAt.END);
            } else if ((int) mode == 3) {
                //跑马灯方式
                marquee(true);
            }
        } else if (VIEW != null && mode != null) {
            mode = Convert.to(Convert.STRING, mode);
            if (mode.toString().equals("start")) {
                ellipsize(0);
            } else if (mode.toString().equals("middle")) {
                ellipsize(1);
            } else if (mode.toString().equals("end")) {
                ellipsize(2);
            } else if (mode.toString().equals("marquee")) {
                ellipsize(3);
            }
        }
    }

    //加载超文本(html)
    public void html(Object html) {
        if (VIEW != null && Convert.to(Convert.INT, html) != null) {
            view().setText(Html.fromHtml(html.toString()));
            view().invalidate();
        }
    }

    //显示超文本(html)链接
    public void htmlLink(Object status) {
        view().setAutoLinkMask(Linkify.WEB_URLS);
        htmlLinkLine(true);
    }

    //显示超文本(html)链接下划线
    public void htmlLinkLine(Object status) {
        if (VIEW != null && Convert.to(Convert.BOOLEAN, status) != null) {

            if ((boolean) status) {
                CustomUrlSpan customUrlSpan;
                view().setMovementMethod(LinkMovementMethod.getInstance());
                CharSequence text = view().getText();
                if (text instanceof Spannable) {
                    int end = text.length();
                    Spannable spannable = (Spannable) view().getText();
                    URLSpan[] urlSpans = spannable.getSpans(0, end, URLSpan.class);
                    if (urlSpans.length == 0) {
                        return;
                    }
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
                    // 循环遍历并拦截 所有http://开头的链接
                    for (URLSpan uri : urlSpans) {
                        String url = uri.getURL();
                        if (url.indexOf("http://") == 0 || url.indexOf("https://") == 0) {
                            customUrlSpan = new CustomUrlSpan(App.action(), url);
                            spannableStringBuilder.setSpan(customUrlSpan, spannable.getSpanStart(uri), spannable.getSpanEnd(uri), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                    }
                    view().setText(spannableStringBuilder);
                }

            } else {
                //取消显示超文本(html)链接下划线
                CharSequence text = view().getText();
                if (text instanceof Spannable) {
                    Spannable spannable = (Spannable) view().getText();
                    spannable.setSpan(new NoUnderlineSpan(), 0, text.length(), Spanned.SPAN_MARK_MARK);
                }
            }

        }
    }

    private void eventLinkClick(String url) {
        if (event != null && url != null) {
            event.linkClick(Convert.to(Convert.STRING, url).toString());
        }
    }

    public void event(Event Event) {
        event = Event;
    }

    public interface Event {
        void linkClick(String url);
    }

    private class CustomUrlSpan extends ClickableSpan {

        private final Context context;
        private final String url;

        public CustomUrlSpan(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(@NonNull android.view.View view) {
            eventLinkClick(this.url);
        }
    }

    private class NoUnderlineSpan extends UnderlineSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }
}
