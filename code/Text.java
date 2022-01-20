package org.domain.code;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text {

    public final static String EMPTY = "";
    public final static String NULL = "null";

    //字母转大写
    public static String toUp(Object text) {
        String result = EMPTY;

        try {
            if(text != null) {
                result = Convert.to(Convert.STRING, text).toString().toUpperCase();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //字母转小写
    public static String toLow(Object text) {
        String result = EMPTY;

        try {
            if(text != null) {
                result = Convert.to(Convert.STRING, text).toString().toLowerCase();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //获取字符位置
    public static int indexOf(Object text,Object chars) {
        int result = -1;

        try {
            if(text != null && chars != null) {
                chars = Convert.to(Convert.STRING, chars).toString();
                result = Convert.to(Convert.STRING, text).toString().indexOf(chars.toString());
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //获取文本长度
    public static int length(Object text) {
        int result = 0;

        try {
            if(text != null) {
                result = Convert.to(Convert.STRING, text).toString().length();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //翻转文本
    public static String reverse(Object text) {
        String result = EMPTY;

        try {
            if(text != null) {
                text = Convert.to(Convert.STRING, text);
                result = new StringBuffer(text.toString()).reverse().toString();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //分割文本
    public static String[] split(Object text,Object separator) {
        String[] result = new String[0];

        try {
            if(text != null) {
                text = Convert.to(Convert.STRING, text);
                separator = separator == null ? EMPTY : Convert.to(Convert.STRING, separator);
                if(separator.toString().isEmpty() && (result = text.toString().split(EMPTY)) != null) {

                } else {
                    text = separator.toString() + text;
                    if (!text.toString().endsWith(separator.toString())) {
                        text = text + separator.toString();
                    }
                    result = Regex.compile(text, "(?<=\\Q" + separator.toString() + "\\E).*?(?=\\Q" + separator.toString() + "\\E)");
                }

            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //删除文本前面后所有空格
    public static String trim(Object text) {
        String result = EMPTY;

        try {
            if(text != null) {
                text = Convert.to(Convert.STRING, text);
               result = text.toString().trim();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //替换文本
    public static String replaceAll(Object text,Object oldText,Object newText) {
        String result = EMPTY;

        try {
            if(text != null) {
                text = Convert.to(Convert.STRING, text);
                if(oldText != null) {
                    oldText = Convert.to(Convert.STRING, oldText);
                } if(newText != null) {
                    newText = Convert.to(Convert.STRING, newText);
                }

                if(oldText != null && newText != null) {
                    result = text.toString().replaceAll("\\Q" + oldText.toString() + "\\E" , newText.toString());
                } else if(oldText != null && newText == null) {
                    result = replaceAll(text,oldText,EMPTY);
                } else {
                    result = text.toString();
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //截取文本
    public static String substring(Object text,Object start) {
        return substring(text,start,null);
    }

    public static String substring(Object text,Object start,Object end) {
        String result = EMPTY;

        try {
            if(text != null) {
                text = Convert.to(Convert.STRING, text);
                if(start != null) {
                    start = Convert.to(Convert.STRING, start);
                } if(end != null) {
                    end = Convert.to(Convert.STRING, end);
                }

                if(start == null && end == null) {
                } else if(start == null && end != null) {
                    try {
                        result = text.toString().substring(text.toString().indexOf(end.toString()));
                    } catch (Exception exception) {
                        result = text.toString().substring(text.toString().length());
                    }
                } else if(start != null && end == null) {
                        result = text.toString().substring(text.toString().indexOf(start.toString()) + start.toString().length(),text.toString().length());
                    } else {
                    result = Regex.compile(text,"(?<=\\Q" + start.toString() + "\\E).*?(?=\\Q" + end.toString() + "\\E)")[0];
                }
                }
            } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //正则匹配
    public static class Regex {

        private Matcher matcher = null;
        private Pattern pattern = null;

        public static String[] compile(Object text,Object regex) {
            String[] result = new String[0];

            try {
                if(text != null && regex != null) {
                    Matcher matcher1 = Pattern.compile(Convert.to(Convert.STRING,regex).toString(),40).matcher(Convert.to(Convert.STRING,text).toString());
                    List<String> list = new ArrayList<>();

                    while (matcher1.find()) {
                        list.add(matcher1.group());
                    }

                    result = list.toArray(new String[list.size()]);
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
            return result;
        }

        //创建表达式
        public void compile (Object regex) {

            try {
                if (regex != null) {
                    pattern = Pattern.compile(Convert.to(Convert.STRING, regex).toString());
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
        }

        //分割文本
        public String[] split(Object text) {
            String[] result = new String[0];

            try {
                if(pattern != null) {
                    result = pattern.split(Convert.to(Convert.STRING,text).toString());
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
            return result;
        }

        //匹配文本
        public void matcher(Object text) {

            try {
               matcher = pattern.matcher(Convert.to(Convert.STRING,text).toString());
            } catch (Exception exception) {
                App.Log.send(exception);
            }
        }

        //替换文本
        public String replaceAll(Object text) {
            String result = null;
            try {
                if(matcher != null) {
                   result = matcher.replaceAll(Convert.to(Convert.STRING,text).toString());

                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }

            return result;
        }

        //匹配下一个
        public boolean find() {
            boolean result = false;

            try {
                if(matcher != null) {
                    result = matcher.find();
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }

            return result;
        }

        //匹配开始位置
        public int start() {
            int result = -1;
            try {
                if(matcher != null) {
                    result = matcher.start();

                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }

            return result;
        }

        //匹配结束位置
        public int end() {
            int result = -1;
            try {
                if(matcher != null) {
                    result = matcher.end();

                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }

            return result;
        }

        //子匹配数量
        public int groupCount() {
            int result = -1;
            try {
                if(matcher != null) {
                    result = matcher.groupCount();

                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }

            return result;
        }

        //子匹配文本
        public String group(Object text) {
            String result = null;
            try {
                if(matcher != null && text != null) {
                    result = matcher.group((int) Convert.to(Convert.INT,text));
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }

            return result;
        }

    }
}
