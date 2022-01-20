package org.domain.code;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AlertDialog;

public class Alert {

    protected AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    public Alert() {
        try {
            builder = new AlertDialog.Builder(App.action());
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //透明度
    public boolean alpha(Object alpha) {
        boolean result = false;
        if(alpha != null) {
            
            Window window = alertDialog.getWindow();
            WindowManager.LayoutParams LP = window.getAttributes();
            if(alpha instanceof Float) {
            } else if (alpha instanceof Integer) {
                alpha = new Integer(Convert.to(Convert.STRING,alpha).toString()).floatValue();
            }

            LP.alpha = (float) alpha;
            window.setAttributes(LP);
            result = true;
        }
        return result;
    }

    //动画资源ID
    public boolean anim(Object animId) {
        boolean result = false;

        try {
            
            if (animId != null && (animId = Convert.to(Convert.INT, animId)) != null) {
                Window window = alertDialog.getWindow();
                window.setWindowAnimations((int) animId);
            }
            result = true;
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    //背景
    public boolean background(Object background) {
        boolean result = false;

        try {
            if (background == null) {
                
                Window window = alertDialog.getWindow();
                if(Convert.to(Convert.INT,background) != null) {
                    window.setBackgroundDrawable(new ColorDrawable((int) Convert.to(Convert.INT,background)));
                } else if (background instanceof String || background instanceof CharSequence && (background.toString().trim().startsWith("#"))) {
                background(Color.parse(background));
                } else if (background instanceof String || background instanceof CharSequence && (background.toString().trim().startsWith("/"))) {
                    window.setBackgroundDrawable( new BitmapDrawable(BitmapFactory.decodeFile(background.toString())));
                } else {
                    Drawable drawable = Drawable.createFromStream(App.action().getAssets().open(background.toString()),background.toString());
                }

                result = true;
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    public boolean icon(Object icon) {
        boolean result = false;
        icon = Convert.to(Convert.STRING,icon).toString();

        try {
            if(icon != null) {
                
                if (App.res(icon) != -1) {
                    //引用资源
                    builder.setIcon(App.res(icon));
                } else {
                    //路径
                    if (icon.toString().startsWith("/")) {
                        builder.setIcon(Drawable.createFromPath(icon.toString()));
                    } else {
                        builder.setIcon(Drawable.createFromStream(App.action().getAssets().open(icon.toString()), icon.toString()));
                    }
                }
                result = true;
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    public boolean title(Object title) {
        boolean result = false;
        title = Convert.to(Convert.STRING,title);

        try {
            if(title != null) {
                
                if (App.res(title) != -1) {
                    //引用资源
                    builder.setTitle(App.res(title));
                } else {
                    builder.setTitle(title.toString());
                }
                result = true;
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    public boolean msg(Object msg) {
        boolean result = false;
        msg = Convert.to(Convert.STRING,msg);
        try {
            if(msg != null) {
                
                if (App.res(msg) != -1) {
                    //引用资源
                    builder.setMessage(App.res(msg));
                } else {
                    builder.setMessage(msg.toString());
                }
                result = true;
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    //按钮
    public boolean button(Object index,Object text) {
        boolean result = false;
        index = (int) Convert.to(Convert.INT,index);
        text = Convert.to(Convert.STRING,text).toString();

        try {
            if(text != null) {
                
                if (App.res(text) != -1) {
                    //引用资源
                    text = App.resString(text);
                }

                if((int) index == 0) {
                    builder.setPositiveButton(text.toString(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                } else if((int) index == 1) {
                    builder.setNegativeButton(text.toString(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                } else if((int) index == 2) {
                    builder.setNeutralButton(text.toString(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }
                builder.setMessage(text.toString());
                result = true;
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    //控件
    public boolean view(Object view) {
        boolean result = false;

        try {
            if(view != null) {
                
                if(App.res(view) != -1) {
                    builder.setView(App.res(view));
                    result = true;
                } else if(view instanceof android.view.View) {
                    builder.setView((android.view.View) view);
                    result = true;
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //可取消
    public boolean cancel(Object cancel) {
        boolean result = false;

        try {
            if(cancel != null) {
                
                builder.setCancelable((boolean) Convert.to(Convert.BOOLEAN,cancel));
                result = true;
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    //显示
    public boolean show() {
        boolean result = false;

        try {
            alertDialog = builder.create();
            alertDialog.show();
            result = true;
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    //隐藏
    public boolean hide() {
        boolean result = false;

        try {
            alertDialog.hide();
            result = true;
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    //关闭
    public boolean dismiss() {
        boolean result = false;

        try {
            alertDialog.dismiss();
            result = true;
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    private void createAlert() {
        try {
            if(alertDialog == null) {
                alertDialog = builder.create();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }



}
