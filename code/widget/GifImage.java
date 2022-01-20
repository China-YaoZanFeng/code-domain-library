package org.domain.code.widget;

import org.domain.code.App;
import org.domain.code.Convert;
import org.domain.code.widget.gif.GIFView;

public class GifImage extends View {

    public GifImage() {
        if (App.action() != null) {
            VIEW = new GIFView(App.action());
        }
    }

    //资源
    public void src(Object src) {
        try {
            if (src != null) {
                if (App.res(src) != -1) {
                    //资源值
                    view().setMovieResource((int) Convert.to(Convert.INT, src));
                } else if ((src = Convert.to(Convert.STRING, src)) != null) {
                    if (src.toString().startsWith("/")) {
                        view().setMovieResource(src.toString());
                    } else {
                        view().setMovieResource(App.action().getAssets().open(src.toString()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //状态
    public boolean state() {
        return !view().isPaused();
    }

    public void state(Object state) {
        if ((state = Convert.to(Convert.BOOLEAN, state)) != null) {
            view().setPaused((boolean) state);
        }
    }

    //时长
    public int time() {
        return view().getMovieTime();
    }

    public void time(Object time) {
        if ((time = Convert.to(Convert.INT, time)) != null) {
            view().setMovieTime((int) time);
        }
    }

    public GIFView view() {
        return (GIFView) VIEW;
    }
}
