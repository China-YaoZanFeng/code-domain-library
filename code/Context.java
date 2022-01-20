package org.domain.code;

public class Context {

    public static android.content.Context ACTION;
    public static android.content.Context GLOBAL;

    public static android.content.Context get() {
        android.content.Context context;
        if ((context = action()) == null) {
            //没有界面上下文，获取全局上下文
            context = global();
        }

        return context;
    }

    public static android.content.Context action() {
        return ACTION;
    }

    public static void action(Object context) {
        try {
            if (context != null && context instanceof android.content.Context) {
                GLOBAL = (android.content.Context) context;
            } else {
                //获取主程序(天才工作)界面上下文
                GLOBAL = (android.content.Context) App.Dex.var(null, App.Dex.get("org.domain.code.Context"), "ACTION");
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    public static android.content.Context global() {
        global(null);
        return GLOBAL;
    }

    public static void global(Object context) {
        try {
            if (context != null && context instanceof android.content.Context) {
                GLOBAL = (android.content.Context) context;
            } else {
                //获取主程序(天才工作)全局上下文
                GLOBAL = (android.content.Context) App.Dex.var(null, App.Dex.get("org.domain.code.Context"), "GLOBAL");
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }
}