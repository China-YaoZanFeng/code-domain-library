package org.domain.code;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import dalvik.system.DexClassLoader;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class App extends Application {

    public static Action ACTION = null;
    public static ArrayList<Action> ALL_ACTION = new ArrayList<Action>();

    public static Action action() {
        return action(null);
    }

    public static Action action(Object action) {
        Action result = null;

        try {
            if (action == null) {
                if ((result = ACTION) == null) {
                    //找不到界面，尝试使用主程序(天才工作)界面
                    result = (Action) Dex.var(null, Dex.get("org.domain.app.GeniusApp"), "ACTION");
                }
            } else if (action != null && action instanceof Action) {
                ACTION = (Action) action;
                if (!ALL_ACTION.contains(action)) {
                    ALL_ACTION.add((Action) action);
                }
                result = (Action) action;
            } else if (action != null && action instanceof String) {
                //返回指定界面
                for (int i = 0; i <= ALL_ACTION.size(); i++) {
                    if (ALL_ACTION.get(i).ACTION_NAME.equals(action.toString())) {
                        result = ALL_ACTION.get(i);
                        break;
                    }
                }
                if (!ALL_ACTION.contains(action)) {
                    ALL_ACTION.add((Action) action);
                }
                ;
            }
        } catch (Exception exception) {
            Log.send(exception);
        }

        return result;
    }

    public static Action[] allAction() {
        Action[] result = new Action[0];
        try {
            if (ALL_ACTION.size() != 0) {
                result = ALL_ACTION.toArray(result);
            }
        } catch (Exception exception) {
            Log.send(exception);
        }

        return result;
    }

    //关闭当前窗口
    public static boolean end() {
        return end(null);
    }

    //关闭指定窗口
    public static boolean end(Object action) {
        boolean result = false;

        try {
            if (action == null) {
                result = end(action());
            } else if (action instanceof String) {
                //使用界面名称查找界面
                action = action(action.toString());
                if (action != null) {
                    result = end(action);
                } else {
                    result = false;
                }
            } else if (action instanceof Action) {
                ((Action) action).finish();
                if (ALL_ACTION.contains((Action) action)) {
                    ALL_ACTION.remove((Action) action);
                }
                result = true;
            }
        } catch (Exception exception) {
            Log.send(exception);
        }

        return result;
    }

    //

    //界面方向
    public static void orientation(Object type) {
        try {
            if (action() != null && Convert.to(Convert.INT, type) == null && !(type = Convert.to(Convert.STRING, type)).toString().isEmpty()) {
                if (((String) type).equals("horizontal")) {
                    orientation(0);
                } else if (((String) type).equals("vertical")) {
                    orientation(1);
                } else {
                    orientation(1);
                }
            } else if (action() != null && (type = Convert.to(Convert.INT, type)) != null) {
                App.action().setRequestedOrientation((int) type);
            }

        } catch (Exception exception) {
            Log.send(exception);
        }
    }

    public static Toast toast(Object text) {
        return toast(text, null);
    }

    public static Toast toast(Object text,Object time) {
        Toast result = null;
        try {
            result = Toast.makeText(Context.get(), null, (int) (Convert.to(Convert.INT, time) == null ? 0 : Convert.to(Convert.INT, time)));
            result.setText(Convert.to(Convert.STRING,text).toString());
            result.show();
        } catch (Exception exception) {
            Log.send(exception);
        }

        return result;
    }

    public static boolean stop(Object time) {
        boolean result = false;

        try {
            if ((time = Convert.to(Convert.INT, time)) != null) {
                Thread.sleep((int) time);
                result = true;
            }
        } catch (Exception exception) {
            Log.send(exception);
        }

        return result;
    }

    public static int res(Object id) {
        int result = -1;

        if (id != null) {
            if (id instanceof Integer) {
                result = (int) Convert.to(Convert.INT, id);
            } else {
                id = id.toString().trim();
                String[] resArray = id.toString().split("");
                String[] resArrays = id.toString().split(".");
                if (resArray[0].equals("R") && resArray[1].equals(".")) {
                    result = App.action().getResources().getIdentifier(resArrays[2], resArrays[1], App.action().getPackageName());
                }
            }
        }

        return result;
    }

    public static String resString(Object id) {
        String result = null;

        try {
            if (App.action() != null) {
                result = App.action().getResources().getString(res(id));
            }
        } catch (Exception exception) {
            result = null;
        }

        return result;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Context.global(getApplicationContext());
        } catch (Exception exception) {
            Log.send(exception);
        }
    }

    public static boolean go(Object data) {
        boolean reuslt  = false;
        try {
            if(action() != null && data != null) {
                if(data instanceof Action) {
                    reuslt = go(new Intent(action(),((Action) data).getClass()));
                } else if (data instanceof String) {
                    if (Dex.newClass(data) != null) {
                        //完整类窗口
                        Class activity = (Class) Dex.newClass(data);
                        action().startActivity(new Intent(action(), activity));
                        reuslt = true;
                    } else {
                        //包名窗口
                        if (Dex.newClass(action().getPackageName() + "." + data.toString()) != null) {
                            Class activity = (Class) Dex.newClass(action().getPackageName() + "." + data.toString());
                           reuslt = go(new Intent(action(), activity));
                        }
                    }
                } else if (data instanceof Intent) {
                    //意图
                    action().startActivity((Intent) data);
                    reuslt = true;
                } else {
                    reuslt = false;
                }
            }
        } catch (Exception exception) {
            Log.send(exception);
        }
        return reuslt;
    }

    //Dex

    public static class Dex {

        private final static List< String > loadedPackages = new ArrayList<String>();
        private final static Class < ? >[] primitiveClasses = new Class < ? >[]{int.class,byte.class,char.class,long.class,short.class,double.class,float.class,void.class,boolean.class};
        private final static Class < ? >[] numberClasses = new Class < ? >[]{int.class,long.class,short.class,double.class,float.class,char.class,byte.class};
        private final static Class < ? >[] wrapperClasses = new Class < ? >[]{Integer.class,Long.class,Short.class,Double.class,Float.class,Character.class,Byte.class};
        private final static Class < ? >[] integerNumberClasses = new Class < ? >[]{Integer.class,Character.class,Byte.class,Long.class,Short.class,int.class,short.class,long.class,char.class,byte.class};

        static{
            clear();
        }

        public static void add(Object packageName){
            try {
                if (packageName != null) {
                    if (!loadedPackages.contains(packageName.toString())) {
                        loadedPackages.add(packageName.toString());
                    }
                }
            } catch (Exception exception) {
                Log.send(exception);
            }
        }

        public static void remove(Object packageName){
            try {

                if (packageName != null) {
                    if (loadedPackages.contains(packageName.toString())) {
                        loadedPackages.remove(packageName.toString());
                    }
                }
            } catch (Exception exception) {
                Log.send(exception);
            }
        }

        public static void clear(){
            try {
                loadedPackages.clear();
                loadedPackages.add("java.lang");
                loadedPackages.add("android.app");
                loadedPackages.add("android.content");
            } catch (Exception exception) {
                Log.send(exception);
            }
        }

        public static Object newClass(Object classObject,Object... args) {
            Object result = null;

            try {
                Class targetClass = getClass(classObject);
                Constructor cons = null;
                int mark = - 1;
                for (Constructor c : targetClass.getDeclaredConstructors()) {
                    int newMark = getMatchCount(args, c.getParameterTypes());
                    if (newMark > mark) {
                        cons = c;
                        mark = newMark;
                    }
                }
                if (cons == null) {
                    throw new NoSuchMethodException();
                }
                cons.setAccessible(true);
                CastArg(args, cons.getParameterTypes());
                result = cons.newInstance(args);
            } catch (Exception exception) {
                Log.send(exception);
            }

            return result;
        }

        public static Object var(Object newClass, Object classObject, Object name){
            Object result = null;

            try {
                Class < ? > klass = getClass(classObject);
                Field field = klass.getDeclaredField(String.valueOf(name));
                field.setAccessible(true);
                result = field.get(newClass);
            } catch (Exception exception) {
                Log.send(exception);
            }

            return result;

        }

        public static boolean var(Object newClass, Object classObject, Object name,Object value){
            boolean result =  false;

            try {
                Class klass = getClass(classObject);
                Field field = klass.getDeclaredField(String.valueOf(name));
                Object[] arr = new Object[]{value};
                CastArg(arr, new Class[]{field.getType()});
                field.setAccessible(true);
                field.set(newClass,arr[0]);
                result = true;
            }
            catch (Exception exception) {
                Log.send(exception);
                result = false;
            }

            return result;
        }

        public static Object[] info(Object classObject,Object type){
            Object[] result = new Object[0];

            try {
                if(classObject != null && type != null) {
                    Class c = getClass(classObject);

                    if (Convert.to(Convert.INT, type) != null) {

                        switch ((int) type) {
                            case 0:
                                //init 构造器
                                return c.getDeclaredConstructors();
                            case 1:
                                //var 变量
                                return c.getDeclaredFields();
                            case 2:
                                //fun 方法
                                return c.getDeclaredMethods();
                        }
                    } else {
                        if (type.toString().equals("init")) {
                            info(classObject, 0);
                        } else if (type.toString().equals("var")) {
                            info(classObject, 1);
                        } else if (type.toString().equals("fun")) {
                            info(classObject, 2);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return result;
        }

        public static DexClassLoader load(Object path) {
            DexClassLoader loader = null;
            try {
                if(path != null) {
                    loader = new DexClassLoader(path.toString(), App.action().getCodeCacheDir().getAbsolutePath(), null, App.action().getClassLoader());
                }
            } catch (Exception exception) {
                Log.send(exception);
            }

            return loader;
        }

        public static Class get(Object packageName){

            Class result = null;

            try {
                if (packageName != null) {
                    result = getClass(packageName.toString());
                }
            } catch (Exception exception) {
                Log.send(exception);
            }

            return result;
        }
        public static Class get(Object dexObject,Object packageName){

            Class result = null;

            try {
                if ((dexObject != null && dexObject instanceof ClassLoader) && packageName != null) {
                    result = getClassByName((ClassLoader) dexObject, packageName.toString());
                }
            } catch (Exception exception) {
                Log.send(exception);
            }

            return result;
        }

        public static Object invoke(Object newClass,Object classObject,Object funName,Object... funArgs){
            try {
                Class < ? > targetClass = getClass(classObject);
                if (funName == null) {
                    throw new IllegalArgumentException("method name null");
                }
                String method = String.valueOf(funName);
                Class[] params = new Class[funArgs.length / 2];
                Object[] passArgs = new Object[funArgs.length / 2];
                if (funArgs.length % 2 != 0) {
                    throw new IllegalArgumentException("extra argument");
                }
                for (int i = 0,j = 0;i < funArgs.length;i += 2,j++) {
                    params[j] = getClass(funArgs[i]);
                }
                for (int i = 1,j = 0;i < funArgs.length;i += 2,j++) {
                    passArgs[j] = funArgs[i];
                }
                Method good = targetClass.getDeclaredMethod(method, params);
                good.setAccessible(true);
                CastArg(passArgs, params);
                return good.invoke(newClass, passArgs);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }


        protected static Class < ? > getClass(Object clazz) throws ClassNotFoundException {
            Class targetClass = null;
            if (clazz == null) {
                return null;
            }
            if (clazz instanceof CharSequence) {
                targetClass = getClassByName(clazz.toString());
            }
            else if (clazz instanceof Class) {
                targetClass = (Class)clazz;
            }
            else {
                throw new IllegalArgumentException("Not a valid class description argument");
            }
            return (Class < ? >)targetClass;
        }

        protected static Class < ? > getClassByName(String name) throws ClassNotFoundException {
            return getClassByName(null, name);
        }

        protected static Class < ? > getClassByName(ClassLoader cl, String name) throws ClassNotFoundException {
            int count = 0;
            while (name.endsWith("[]")) {
                count++;
                name = name.substring(0, name.length() - 2);
            }
            Class < ? > klass = null;
            for (int i=0;i < primitiveClasses.length;i++) {
                if (name.equals(primitiveClasses[i].getSimpleName())) {
                    klass = primitiveClasses[i];
                    break;
                }
            }
            if (klass == null) {
                try {
                    klass = (cl != null) ? cl.loadClass(name) : Class.forName(name);
                }
                catch (ClassNotFoundException e) {
                    for (int i=0;i < loadedPackages.size();i++) {
                        try {
                            klass = (cl != null) ? cl.loadClass(loadedPackages.get(i) + "."  + name) : Class.forName(loadedPackages.get(i) + "." + name);
                        }
                        catch (ClassNotFoundException e0) {
                        }
                        if (klass != null) {
                            break;
                        }
                    }
                }
            }
            if (klass == null) {
                throw new ClassNotFoundException(name);
            }
            while (count > 0) {
                Object obj = Array.newInstance(klass, 0);
                klass = obj.getClass();
                count--;
            }
            return klass;
        }

        protected static boolean isMemberOf(Object[] collection, Object element) {
            if (collection == null) {
                return false;
            }
            for (Object obj : collection) {
                if (obj == element) {
                    return true;
                }
            }
            return false;
        }

        protected static boolean isNumberClass(Class c) {
            return isMemberOf(numberClasses, c);
        }

        protected static boolean isWrapperClass(Class c) {
            return isMemberOf(wrapperClasses, c);
        }

        protected static Class < ? > getWrapperClass(Class < ? > c) {
            if (c == null) {
                return null;
            }
            for (int i=0;i < numberClasses.length;i++) {
                if (numberClasses[i] == c || wrapperClasses[i] == c) {
                    return wrapperClasses[i];
                }
            }
            return null;
        }

        protected static String trimIfNeed(String num, Class to) {
            if (isMemberOf(integerNumberClasses, to)) {
                int i = num.indexOf('.');
                if (i != - 1) {
                    num = num.substring(0, i);
                }
            }
            return num;
        }

        protected static void CastArg(Object[] a, Class < ? >[] b) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, InstantiationException, InvocationTargetException, SecurityException {
            CastArg(a, b, true);
        }

        protected static void CastArg(Object[] objs, Class < ? >[] cls, boolean modify) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, InstantiationException {
            for (int i=0;i < objs.length && i < cls.length;i++) {
                Object obj = objs[i];
                Class < ? > klass = cls[i];
                if (obj == null) {
                    if (isNumberClass(klass)) {
                        throw new IllegalArgumentException();
                    }
                    else {
                        continue;
                    }
                }
                else {
                    Class < ? > objClass = obj.getClass();
                    if ((getWrapperClass(objClass) != null || objClass == String.class)) {
                        if (isNumberClass(klass)) {
                            String str = trimIfNeed(String.valueOf(obj), klass);
                            Class < ? > wrapper = getWrapperClass(klass);
                            if (modify)
                                if (klass == char.class) {
                                    objs[i] = (char) new Double(str).intValue();
                                }
                                else if (klass == int.class) {
                                    objs[i] = Integer.parseInt(str);
                                }
                                else {
                                    objs[i] = wrapper.getMethod("parse" + wrapper.getSimpleName(), String.class).invoke(null, str);
                                }
                            continue;
                        }
                        else if (klass == String.class) {
                            if (modify)
                                objs[i] = obj.toString();
                            continue;
                        }
                        else if (isWrapperClass(klass)) {
                            String str = trimIfNeed(String.valueOf(obj), klass);
                            if (modify)
                                if (klass == Character.class) {
                                    objs[i] = new Character((char)new Double(String.valueOf(obj)).intValue());
                                }
                                else {
                                    objs[i] = getWrapperClass(klass).getConstructor(String.class).newInstance(str);
                                }
                            continue;
                        }
                        if (klass == String.class) {
                            if (modify)
                                objs[i] = String.valueOf(obj);
                            continue;
                        }
                        if (klass == Boolean.class) {
                            String str = obj.toString().toLowerCase();
                            switch (str) {
                                case "true":
                                    if (modify)
                                        objs[i] = new Boolean(true);
                                    continue;
                                case "false":
                                    if (modify)
                                        objs[i] = new Boolean(false);
                                    continue;
                                default:
                                    throw new IllegalArgumentException();
                            }
                        }
                        if (klass == boolean.class) {
                            String str = obj.toString().toLowerCase();
                            switch (str) {
                                case "true":
                                    if (modify)
                                        objs[i] = true;
                                    continue;
                                case "false":
                                    if (modify)
                                        objs[i] = false;
                                    continue;
                                default:
                                    throw new IllegalArgumentException();
                            }
                        }
                    }
                    if (klass.isInstance(obj)) {
                        continue;
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }

        protected static Object getUnsafe() {
            try {
                return var(null, "sun.misc.Unsafe", "theUnsafe");
            }
            catch (Exception e) {
                return null;
            }
        }

        private static int getMatchCount(Object[] args, Class[] types) {
            if (args.length != types.length) {
                return - 1;
            }
            try {
                CastArg(args, types, false);
            }
            catch (Exception e) {
                return - 1;
            }
            int mark = 0;
            for (int i=0;i < types.length;i++) {
                if (args[i] == null) {
                    mark++;
                }
                else if (args[i].getClass() == types[i]) {
                    mark++;
                }
            }
            return mark;
        }

        public static void forceThrow(Throwable ex) {
            App.Dex.ThrowableContainer con = new App.Dex.ThrowableContainer();
            try {
                Field f = App.Dex.ThrowableContainer.class.getDeclaredField("exception");
                long l = (long)javaxt(getUnsafe(), "sun.misc.Unsafe", "objectFieldOffset", f);
                javaxt(getUnsafe(), "sun.misc.Unsafe", "putObject", con, l, ex);
            }
            catch (Exception e) {}
            con.throwNow();
        }

        public static Object javat(Object obj, CharSequence desc, Object... args) throws IllegalAccessException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException, SecurityException {
            String str = desc.toString();
            int i = str.lastIndexOf('.');
            if (i == - 1) {
                throw new IllegalArgumentException();
            }
            return javaxt(obj, desc.subSequence(0, i), desc.subSequence(i + 1, desc.length()), args);
        }

        public static Object javaxt(Object obj, Object clazz, Object name, Object... args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, SecurityException {
            Class targetClass = getClass(clazz);
            if (name == null) {
                throw new IllegalArgumentException("null.method name");
            }
            String m_name = String.valueOf(name);
            Method method = null;
            int mark = - 1;
            for (Method meth : targetClass.getDeclaredMethods()) {
                if (meth.getName().equals(m_name)) {
                    int newMark = getMatchCount(args, meth.getParameterTypes());
                    if (newMark > mark) {
                        mark = newMark;
                        method = meth;
                    }
                }
            }
            if (method == null) {
                throw new NoSuchMethodException(m_name);
            }
            method.setAccessible(true);
            CastArg(args, method.getParameterTypes());
            return method.invoke(obj, args);
        }

        public static Object javacb(Object clazz, App.Dex.Callback callback) throws ClassNotFoundException {
            return javacb(clazz, callback, false);
        }

        public static Object javacb(Object clazz, App.Dex.Callback callback, boolean wrap) throws ClassNotFoundException {
            Class targetClass = getClass(clazz);
            if (!targetClass.isInterface()) {
                throw new IllegalArgumentException("Not a interface");
            }
            if (callback == null) {
                throw new NullPointerException();
            }
            return Proxy.newProxyInstance(targetClass.getClassLoader(), new Class[]{targetClass}, wrap ? new App.Dex.JavaWrappedCallback(callback) : new App.Dex.JavaCallback(callback));
        }

        private static class ThrowableContainer {
            private RuntimeException exception;

            private ThrowableContainer() {

            }

            public void throwNow() {
                if (exception != null) {
                    throw exception;
                }
            }

        }

        public static class JavaWrappedCallback implements InvocationHandler {

            private App.Dex.Callback cb;

            private JavaWrappedCallback(App.Dex.Callback callback) {
                if (callback == null) {
                    throw new NullPointerException();
                }
                cb = callback;
            }

            @Override
            public Object invoke(Object target, Method method, Object[] args) throws Throwable {
                Object rt = cb.onCallMethod(target, method, args);
                if (rt == null) {
                    switch (method.getReturnType().getName()) {
                        case "boolean":
                            rt = false;
                            break;
                        case "int":
                        case "char":
                        case "long":
                        case "short":
                        case "double":
                        case "float":
                        case "byte":
                            rt = 0;
                            break;
                    }
                }
                else {
                    if (method.getReturnType() == void.class) {
                        return null;
                    }
                    try {
                        Object[] obj = new Object[]{rt};
                        CastArg(obj, new Class[]{method.getReturnType()});
                        rt = obj[0];
                    }
                    catch (Throwable t) {
                        //无力回天
                    }
                }
                return rt;
            }
        }

        private static class JavaCallback implements InvocationHandler {

            private App.Dex.Callback cb;

            private JavaCallback(App.Dex.Callback callback) {
                if (callback == null) {
                    throw new NullPointerException();
                }
                cb = callback;
            }

            @Override
            public Object invoke(Object target, Method method, Object[] args) throws Throwable {
                return cb.onCallMethod(target, method, args);
            }

        }

        public interface Callback {

            Object onCallMethod(Object target, Method proxyMethod, Object[] args);

        }

    }

    //日志
    public static class Log {

        

        public static void send(Object exception) {
            System.out.println(exception);
            App.action().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    App.toast(exception);
                }
            });
        }

    }

    //权限

    public static class Permission {

        //申请权限
        public static boolean applyFor(Object permissionName) {
            boolean result = false;
            String[] permission = new String[0];
            try {
                if(action() != null) {
                    if (permissionName == null) {
                        permission = List();
                    } else {
                        permissionName = Convert.to(Convert.STRING,permissionName);
                        permission[0] = permissionName.toString();
                    }
                    ActivityCompat.requestPermissions(action(), permission, 0);
                    result = true;
                }
            } catch (Exception exception) {
                Log.send(exception);
            }

            return result;
        }

        //获取清单所有权限列表
        public static String[] List() {
            String[] result = new String[0];

            try {
                if(action() != null) {
                    PackageManager manager = action().getPackageManager();
                        PackageInfo info = manager.getPackageInfo(action().getPackageName(), 0);
                        String pkgName = info.packageName;
                        PackageInfo packageInfo = manager.getPackageInfo(pkgName, PackageManager.GET_PERMISSIONS);
                        result = packageInfo.requestedPermissions;
                }
            } catch (Exception exception) {
                Log.send(exception);
            }

            return result;
        }

    }
}



