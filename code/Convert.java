package org.domain.code;

import java.io.*;

public class Convert {

    public static int INT = 0;
    public static int LONG = 1;
    public static int CHAR = 2;
    public static int STRING = 3;
    public static int DOUBLE = 4;
    public static int BOOLEAN = 5;

    @SuppressWarnings("CatchMayIgnoreException")
    public static Object to(Object type, Object data) {
        Object result = null;

        try {
            /*
             *先将类型转为整数
             *防止下面代码强转整数类型出错
             */

            if (type instanceof Integer) {
            } else if (type instanceof String) {
                type = Integer.parseInt(type.toString());
            } else if (type instanceof Double) {
                type = (int) (double) type;
            } else if (data instanceof Float) {
                type = (int) (float) type;
            } else {
                type = -1;
            }

            if (data != null) {
                if ((int) type == INT) {

                    //整数
                    if (data instanceof Integer) {
                        result = (int) data;
                    } else if (data instanceof String) {
                        result = Integer.parseInt(data.toString());
                    } else if (data instanceof Double) {
                        result = (int) (double) data;
                    } else if (data instanceof Float) {
                        result = (int) (float) data;
                    } else {
                        //转整数失败
                        App.Log.send("Convert:to 'int' failure.");
                    }

                } else if ((int) type == LONG) {

                    //长整数
                    if (data instanceof Long) {
                        result = (long) data;
                    } else if (data instanceof String) {
                        result = Long.parseLong(data.toString());
                    } else if (data instanceof Double) {
                        result = (long) (double) data;
                    } else if (data instanceof Float) {
                        result = (long) (float) data;
                    } else {
                        //转长整数失败
                        App.Log.send("Convert:to 'long' failure.");
                    }

                } else if ((int) type == CHAR) {

                    //字符
                    if (data instanceof Character) {
                        result = (char) data;
                    } else if (data instanceof String) {
                        result = data.toString().toCharArray()[0];
                    } else {
                        //转字符失败
                        App.Log.send("Convert:to 'char' failure.");
                    }

                } else if ((int) type == STRING) {
                    //字符串
                    if(data != null) {
                        if (data instanceof String) {
                            result = (Object) data;//不这样做不可调用toString()
                        } else if (data instanceof Integer) {
                            result = new Integer(data.toString()).toString();
                        } else if (data instanceof Throwable) {
                            Throwable TAB = (Throwable) data;
                            StringWriter writer = new StringWriter();
                            PrintWriter print = new PrintWriter(writer);
                            TAB.printStackTrace(print);
                            result = writer.toString();
                        } else {
                            result = String.valueOf(data);
                        }
                    } else {
                        result = Text.NULL;
                    }

                } else if ((int) type == DOUBLE) {

                    //精度
                    try {
                        result = Double.parseDouble(data.toString());
                    } catch (Exception exception) {
                        //转精度失败
                        App.Log.send("Convert:to 'double' failure.");
                    }

                } else if ((int) type == BOOLEAN) {
                    //布尔
                    if (data instanceof Boolean) {
                        return (boolean) data;
                    }
                } else {
                    if (data.toString().equals("true")) {
                        return true;
                    } else if (data.toString().equals("false")) {
                        return false;
                    } else {
                        //未知转换类型
                        App.Log.send("Convert:null type!");
                    }
                }
            } else if((int) type == STRING && data == null) {
               result = Text.NULL;
            }

        } catch (Exception exception){
            App.Log.send(exception);
        }

        return result;
    }

    //dx,dip,sp
    public static byte[] bytes(Object data) {
        byte[] result = null;

        try {
            if(data instanceof byte[]) {
                result = (byte[]) data;
            }
        else if(data instanceof InputStream) {
            result = inputToBytes((InputStream) data);
        } else {
            result = Convert.to(Convert.STRING,data).toString().getBytes();
        }

        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return  result;
    }

    //dx,dip,sp
    public static int px(Object value){
        int result =0;

        try {
            if (App.action() != null && value != null) {
                if (value.toString().endsWith("dp")) {
                    result = (int) ((App.action().getResources().getDisplayMetrics().density * Float.parseFloat(value.toString().substring(0, value.toString().length() - 2)) + 0.5f));
                }
                else if (value.toString().endsWith("dip")) {
                    result = (int) ((App.action().getResources().getDisplayMetrics().density * Float.parseFloat(value.toString().substring(0, value.toString().length() - 3)) + 0.5f));
                }
                else if (value.toString().endsWith("sp")) {
                    result = (int) ((App.action().getResources().getDisplayMetrics().scaledDensity * Float.parseFloat(value.toString().substring(0, value.toString().length() - 2)) + 0.5f));
                } else {
                    result = (int) Double.parseDouble(value.toString());
                }
            }
        } catch (Exception exception){
            App.Log.send(exception);
        }

        return  result;
    }


    //输入流转字节
    private static byte[] inputToBytes(InputStream input) {
        byte[] result = new byte[1024];
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int n = 0;
            byte[] bytes = new byte[1024 * 4];
            while (-1 != (n = input.read(bytes))) {
                output.write(bytes, 0, n);
            }

            result = output.toByteArray();
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }
}