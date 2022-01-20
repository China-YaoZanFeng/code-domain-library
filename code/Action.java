package org.domain.code;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

public class Action extends AppCompatActivity {

    public String ACTION_NAME = "Main";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            init();
            create(bundle);
        } catch (Exception exception) {
            App.Log.send(exception);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }



    public static boolean end() {
        return App.end();
    }

    private void init() {
        //更新界面上下文
        ACTION_NAME = this.toString().substring(this.toString().lastIndexOf(".") + 1, this.toString().indexOf("@"));
        App.action(this);
        Context.action(this);
    }

    protected void create(Bundle bundle) {
    }

    //状态栏
    public static class StatusBar {

        //状态栏颜色
        public static void color(Object color) {
            try {
                if (App.action() != null && ((int) (color = Color.parse(color))) != -1) {
                    App.action().getWindow().setStatusBarColor((int) color);
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
        }

        //状态栏黑色样式
        public static void uiBlack(Object state) {
            try {
                if (App.action() != null) {
                    state = Convert.to(Convert.BOOLEAN, state);
                    if ((boolean) state) {
                        App.action().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        App.action().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
        }

        //状态拦沉浸
        public static void immersive(Object state) {
            try {
                if (App.action() != null) {
                    state = Convert.to(Convert.BOOLEAN, state);
                    App.action().setImmersive((boolean) state);
                    if ((boolean) state) {
                        App.action().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    } else {
                        App.action().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
        }
    }

    //导航栏
    public static class NavigationBar {

        //导航栏颜色
        public static void color(Object color) {
            try {
                if (App.action() != null && ((int) (color = Color.parse(color))) != -1) {
                    App.action().getWindow().setNavigationBarColor((int) color);
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
        }

        //导航拦沉浸
        public static void immersive(Object state) {
            try {
                if (App.action() != null) {
                    state = Convert.to(Convert.BOOLEAN, state);
                    App.action().setImmersive((boolean) state);
                    if ((boolean) state) {
                        App.action().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    } else {
                        App.action().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    }
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
        }
    }

}