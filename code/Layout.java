package org.domain.code;

import org.domain.code.layout.*;

public class Layout {

    public static Class<Linear> Linear() {
        return Linear.class;
    }

    public static Class<Relative> Relative() {
        return Relative.class;
    }

    public static Class<ReboundScroll> Scroll() {
        return ReboundScroll.class;
    }

    public static Class<HorizontalScroll> HorizontalScroll() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        return HorizontalScroll.class;
    }

    public static Class<Page> Page() {
        return Page.class;
    }

    public static Class<Drawer> Drawer() {
        return Drawer.class;
    }

    public static Class<Grid> Grid() {
        return Grid.class;
    }


}
