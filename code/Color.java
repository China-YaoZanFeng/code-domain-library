package org.domain.code;

public class Color {

    public static int BLACK = 0x000000;

    public static int parse(Object color){
        int result = -1;

        try {
            if (color != null) {
                if (color.toString().startsWith("#")) {
                    try {
                        result = android.graphics.Color.parseColor(color.toString());
                    } catch (Exception exception) {
                        App.Log.send(exception);
                    }
                } else if (Convert.to(Convert.INT, color) != null) {
                    result = (int) Convert.to(Convert.INT,color);
                }
            }
        } catch (Exception exception){
            App.Log.send(exception);
        }

        return result;
    }

}
