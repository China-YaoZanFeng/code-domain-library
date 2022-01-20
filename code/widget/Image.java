package org.domain.code.widget;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import org.domain.code.App;
import org.domain.code.Color;
import org.domain.code.Convert;

import java.io.InputStream;

public class Image extends View {


    private static int scaleType = -1;


    public Image() {
        if (App.action() != null) {
            VIEW = new ImageView(App.action());
        }
    }

    //透明度
    public int alpha() {
        int result = 0;
        try {
            result = view().getImageAlpha();
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public void alpha(Object alpha) {
        if (alpha == null) {
            view().setImageAlpha(255);
        } else {
            if ((alpha = Convert.to(Convert.INT, alpha)) != null) {
                view().setImageAlpha((int) alpha);
            }
        }
    }

    //资源
    public void src(Object src) {
        try {
            if (src != null) {
                if (App.res(src) != -1) {
                    //引用资源
                    view().setImageResource(App.res(src));
                } else if ((src = Convert.to(Convert.STRING, src)) != null) {
                    if (src.toString().startsWith("/")) {
                        view().setImageBitmap(BitmapFactory.decodeFile(src.toString()));
                    } else if (src instanceof byte[]) {
                        view().setImageBitmap(BitmapFactory.decodeByteArray((byte[]) src, 0, ((byte[]) src).length));
                    } else {
                        InputStream inputStream = App.action().getAssets().open(src.toString());
                        view().setImageDrawable(Drawable.createFromStream(inputStream, src.toString()));
                    }
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //拉伸方式
    public void scale(Object type) {
        if (type != null) {
            if (Convert.to(Convert.INT, type) != null) {
                type = Convert.to(Convert.INT, type);
                if ((int) type == 0) {
                    view().setScaleType(ScaleType.CENTER);
                } else if ((int) type == 1) {
                    view().setScaleType(ScaleType.CENTER_CROP);
                } else if ((int) type == 2) {
                    view().setScaleType(ScaleType.CENTER_INSIDE);
                } else if ((int) type == 3) {
                    view().setScaleType(ScaleType.FIT_CENTER);
                } else if ((int) type == 4) {
                    view().setScaleType(ScaleType.FIT_START);
                } else if ((int) type == 5) {
                    view().setScaleType(ScaleType.FIT_END);
                } else if ((int) type == 6) {
                    view().setScaleType(ScaleType.FIT_XY);
                } else if ((int) type == 7) {
                    view().setScaleType(ScaleType.MATRIX);
                }
                scaleType = (int) type;

            } else {
                if (type.toString().equals("center")) {
                    //居中
                    scale(0);
                } else if (type.toString().equals("center-crop")) {
                    //显示图片中间部分
                    scale(1);
                } else if (type.toString().equals("center-inside")) {
                    //占满中间里面
                    scale(2);
                } else if (type.toString().equals("fit-center")) {
                    //占满中间
                    scale(3);
                } else if (type.toString().equals("fit-start")) {
                    //占满顶部
                    scale(4);
                } else if (type.toString().equals("fit-end")) {
                    //占满底部
                    scale(5);
                } else if (type.toString().equals("fit-xy")) {
                    //占满
                    scale(6);
                } else if (type.toString().equals("matrix")) {
                    //自适应
                    scale(7);
                }
            }
        }
    }

    //着色
    public void filter(Object color) {
        if (color == null) {
            view().clearColorFilter();
        } else {
            color = Color.parse(color);
            view().setColorFilter((int) color);
        }
    }

    public ImageView view() {
        return (ImageView) VIEW;
    }
}
