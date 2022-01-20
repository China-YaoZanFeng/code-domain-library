package org.domain.code;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import androidx.documentfile.provider.DocumentFile;

import java.io.*;
import java.util.ArrayList;

public class File extends java.io.File {

    public static String STORAGE_PATH;
    public static String STORAGE_DATA_PATH;
    public static String ENCODING_UTF8;
    public static String ENCODING_GBK;

    static {
        if((STORAGE_PATH = "") != null) {
        }
        STORAGE_PATH = "/storage/emulated/0/";
        STORAGE_DATA_PATH = STORAGE_PATH + "Android/data/";
        ENCODING_UTF8 = "UTF-8";
        ENCODING_GBK = "GBK";
    }

    public File(Object pathname) {
        super(pathname.toString());
    }

    //创建空文件或目录
    public static boolean make(Object path) {
        boolean result = false;

        try {
            path = Convert.to(Convert.STRING, path);

            if (path != null || !path.toString().isEmpty()) {

                //Android11及Android新版本，且路径为android/data/*...
                if (newAbove() && isAndroidDataPath(path) && Document.state(STORAGE_DATA_PATH)) {
                    DocumentFile documentFile = null;

                    if (!exists((new File(path.toString()).getParent()))) {
                        make(new File(path.toString()).getParent() + "/");
                    }

                    if (!Document.file(path.toString()).exists()) {
                        documentFile = Document.dir(new File(path.toString()).getParent());

                        if (path.toString().endsWith("/")) {
                            //创建目录
                            result = documentFile.createDirectory(new File(path.toString()).getName()).exists();
                        } else {
                            //创建空文件
                            result = documentFile.createFile(new File(path.toString()).getParent(), new File(path.toString()).getName()).exists();
                        }
                    }
                } else {
                    //Android旧版本
                    if (path.toString().endsWith("/")) {
                        //创建目录
                        result = new File(path.toString()).mkdirs();
                        App.toast(result);
                    } else {
                        //创建空文件
                        result = new File(path.toString()).createNewFile();
                    }
                }
            }
        } catch (Exception exception) {
            result = false;
            App.Log.send(exception);
        }
        return result;
    }

    //删除文件
    public static boolean delete(Object path) {
        boolean result = false;
        try {
            if (path != null || !path.toString().isEmpty()) {
                path = Convert.to(Convert.STRING, path);
                //Android11及Android新版本，且路径为android/data/*...
                if (newAbove() && isAndroidDataPath(path) && Document.state(STORAGE_DATA_PATH)) {
                    DocumentFile documentFile = Document.file(path.toString());
                    result = documentFile.delete();
                }
            } else {
                //Android旧版本
                result = new File(path).delete();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //移动文件
    public static boolean move(Object path, Object newPath) {
        boolean result = false;

        try {
            result = (copy(path, newPath) && delete(path));
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //复制文件
    public static boolean copy(Object path, Object newPath) {
        boolean result = false;

        try {
            path = Convert.to(Convert.STRING, path);
            newPath = Convert.to(Convert.STRING, newPath);

            if ((path != null || !path.toString().isEmpty()) && (newPath != null || !newPath.toString().isEmpty())) {
                if (!exists(newPath)) {
                    make(newPath);
                }
                result = write(newPath, input(path));
            }
        } catch (Exception exception) {
            result = false;
            App.Log.send(exception);
        }
        return result;
    }

    //判断文件或目录是否存在
    public static boolean exists(Object path) {
        boolean result = false;

        try {
            path = Convert.to(Convert.STRING, path);
            if (path != null || !path.toString().isEmpty()) {
                //Android11及Android新版本，且路径为android/data/*...
                if (newAbove() && isAndroidDataPath(path) && Document.state(STORAGE_DATA_PATH)) {
                    result = Document.file(path.toString()).exists();
                }
            } else {
                //Android旧版本
                result = new File(path.toString()).exists();
            }
        } catch (Exception exception) {
            result = false;
            App.Log.send(exception);
        }
        return result;
    }

    //重命名
    public static boolean rename(Object path, Object newName) {
        boolean result = false;

        try {
            if (path != null && newName != null) {
                path = Convert.to(Convert.STRING, path);
                newName = Convert.to(Convert.STRING, newName);
                //Android11及Android新版本，且路径为android/data/*...
                if (newAbove() && isAndroidDataPath(path) && Document.state(STORAGE_DATA_PATH)) {
                    result = Document.file(path.toString()).renameTo(newName.toString());
                } else {
                    //Android旧版本
                    result = new File(path.toString()).renameTo(new File(new File(path).getParent() + "/" + newName.toString()));
                }
            }
        } catch (Exception exception) {

        }
        return result;
    }

    //读取文件数据
    public static String read(Object path) {
        return read(path, null);
    }

    public static String read(Object path, Object encoding) {
        String result = Text.EMPTY;

        try {
            path = Convert.to(Convert.STRING, path);
            encoding = Convert.to(Convert.STRING, encoding);

            if (path != null || !path.toString().isEmpty()) {
                encoding = encoding == null ? ENCODING_UTF8 : encoding;
                result = inputToString(input(path.toString()), encoding.toString());
            }

        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    //写入文件数据
    public static boolean write(Object path, Object data) {
        return write(path, data, null);
    }

    public static boolean write(Object path, Object data, Object encoding) {
        boolean result = false;

        try {
            path = Convert.to(Convert.STRING, path);
            encoding = Convert.to(Convert.STRING, encoding);

            if (path != null || !path.toString().isEmpty() || data != null) {
                if (!exists(path)) {
                    make(path);
                }
                encoding = encoding == null ? ENCODING_UTF8 : encoding;
                if ((data instanceof InputStream)) {
                    data = Convert.bytes(data);
                } else if (!(data instanceof Byte[])) {
                    data = Convert.to(Convert.STRING, data).toString().getBytes(encoding.toString());
                }

                if (data != null) {
                    OutputStream outputStream = output(path);
                    outputStream.write((byte[]) data);
                    outputStream.flush();
                    outputStream.close();
                    result = true;
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    //获取目录全部列表
    public static String[] list(Object path) {
        String[] result = new String[0];
        try {
            if (path != null) {
                ArrayList<String> arrayList = new ArrayList<>();
                list(arrayList, path);
                result = arrayList.toArray(result);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }


    //获取目录全部列表
    public static String[] lists(Object path) {
        return lists(path, null);
    }

    public static String[] lists(Object path, Object nameState) {
        String[] result = new String[0];
        try {
            if (path != null) {
                ArrayList<String> arrayList = new ArrayList<>();
                lists(arrayList, path, nameState);
                result = arrayList.toArray(result);
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }


    //动态链接库权限
    public static boolean exe(Object path, Object status) {
        boolean result = false;
        try {
            status = Convert.to(Convert.BOOLEAN, status);
            result = new File(path.toString()).setExecutable((boolean) status);
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public static InputStream input(Object path) {
        InputStream result = null;

        try {
            if (path != null) {
                path = Convert.to(Convert.STRING, path);
                //Android11及Android新版本，且路径为android/data/*...
                if (newAbove() && isAndroidDataPath(path) && Document.state(STORAGE_DATA_PATH)) {
                    DocumentFile documentFile = Document.file(path.toString());
                    result = App.action().getContentResolver().openInputStream(documentFile.getUri());
                } else {
                    //Android旧版本
                    result = new FileInputStream(new File(path));
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    public static OutputStream output(Object path) {
        OutputStream result = null;

        try {
            if (path != null) {
                path = Convert.to(Convert.STRING, path);
                //Android11及Android新版本，且路径为android/data/*...
                if (newAbove() && isAndroidDataPath(path) && Document.state(STORAGE_DATA_PATH)) {
                    DocumentFile documentFile = Document.file(path.toString());
                    result = App.action().getContentResolver().openOutputStream(documentFile.getUri());
                } else {
                    //Android旧版本
                    result = new FileOutputStream(new File(path.toString()));
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //是否新的安卓版本
    private static boolean newAbove() {
        boolean result = false;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                result = true;
            } else{
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return true;
    }

    private static boolean isAndroidDataPath(Object path) {
        boolean result = false;
        try {
            if (path.toString().startsWith(STORAGE_DATA_PATH)) {
                result = true;
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //获取目录列表
    private static void list(ArrayList<String> arrayList, Object path) {
        try {
            if (!path.toString().endsWith("/")) {
                path = path + "/";
            }
            //Android11及Android新版本，且路径为android/data/*...
            if (newAbove() && isAndroidDataPath(path) && Document.state(STORAGE_DATA_PATH)) {
                for (DocumentFile documentFile : Document.dir(path.toString()).listFiles()) {
                    if (Document.file(path + documentFile.getName()).exists()) {
                        //在该目录
                        arrayList.add(documentFile.getName());
                    }
                }
            } else {
                //安卓旧版本
                for (String name : new File(path.toString()).list()) {
                    arrayList.add(name);
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //获取目录全部列表
    private static void lists(ArrayList<String> arrayList, Object path, Object nameState) {
        try {
            nameState = Convert.to(Convert.BOOLEAN, nameState);
            if (!path.toString().endsWith("/")) {
                path = path + "/";
            }
            //Android11及Android新版本，且路径为android/data/*...
            if (newAbove() && isAndroidDataPath(path) && Document.state(STORAGE_DATA_PATH)) {
                for (DocumentFile documentFile : Document.dir(path.toString()).listFiles()) {
                    if (documentFile.isDirectory()) {
                        //目录
                        lists(arrayList, path.toString() + documentFile.getName(), (boolean) nameState);
                    }
                    if ((boolean) nameState) {
                        arrayList.add(documentFile.getName());
                    } else {
                        arrayList.add(path.toString() + documentFile.getName());
                    }

                }
            } else {
                //安卓旧版本
                for (java.io.File file : new File(path.toString()).listFiles()) {
                    if (file.isDirectory()) {
                        //目录
                        lists(arrayList, path.toString() + file.getName(), (boolean) nameState);
                    }
                    if ((boolean) nameState) {
                        arrayList.add(file.getName());
                    } else {
                        arrayList.add(path.toString() + file.getName());
                    }
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    //输入流转文本
    private static String inputToString(InputStream input, String encoding) {
        String result = Text.EMPTY;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input, encoding));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            boolean first = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (first) {
                    first = false;
                    stringBuffer.append(line);
                } else {
                    stringBuffer.append('\n').append(line);
                }
            }
            bufferedReader.close();
            result = stringBuffer.toString();
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //文件文档
    public static class Document {

        public static String CONTEXT_TREE= "content://com.android.externalstorage.documents/tree/primary%3A";
        public static String CONTEXT_TREE_DOUCMENT = "content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata/document/primary%3A";
        public static String CONTEXT_TREE_ANDROID_DATA = "content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata";

        //获取目录权限状态
        public static boolean state(Object path) {
            boolean result = false;
            try {
                if(path != null) {
                    path = path.toString().startsWith(STORAGE_PATH) ? path.toString().replace(STORAGE_PATH,Text.EMPTY) : path;
                    path = path.toString().endsWith("/") ? path.toString().substring(0,path.toString().length() - 1) : path;
                    path = !path.toString().startsWith(CONTEXT_TREE) ? path.toString().replaceAll("/","%2F") : path;
                    path = !path.toString().startsWith(CONTEXT_TREE) ? CONTEXT_TREE + path : path;
                    App.toast(path);
                for (UriPermission persistedUriPermission : App.action().getContentResolver().getPersistedUriPermissions()) {
                    if (persistedUriPermission.isReadPermission() && persistedUriPermission.getUri().toString().equals(path)) {
                        result = true;
                    }
                }
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
            return result;
        }

        public static String uri(String path) {
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
            String path2 = path.replace(STORAGE_PATH, "").replace("/", "%2F");
            return CONTEXT_TREE_DOUCMENT + path2;
        }

        //文件对象
        public static DocumentFile file(Object path) {
            DocumentFile result = null;

            try {
                if (path != null) {
                    path = Convert.to(Convert.STRING, path);

                    if (path.toString().endsWith("/")) {
                        path = path.toString().substring(0, path.toString().length() - 1);
                    }
                    path = path.toString().replace(STORAGE_PATH, "").replace("/", "%2F");
                    result = DocumentFile.fromSingleUri(App.action(), Uri.parse(CONTEXT_TREE_DOUCMENT + path));
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
            return result;
        }

        //目录对象
        public static DocumentFile dir(Object path) {
            DocumentFile result = null;
            try {
                if (path != null) {
                    path = Convert.to(Convert.STRING, path);

                    if (path.toString().endsWith("/")) {
                        path = path.toString().substring(0, path.toString().length() - 1);
                    }
                    path = path.toString().replace(STORAGE_PATH, "").replace("/", "%2F");
                    result = DocumentFile.fromTreeUri(App.action(), Uri.parse(CONTEXT_TREE_DOUCMENT + path));
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
            return result;
        }

        //获取数据分区目录的权限
        public static void get(Object path) {
            try {
                if (path == null) {
                    path = STORAGE_DATA_PATH;
                    get(path.toString());
                } else if (path instanceof String) {
                    String uri = uri(path.toString());
                    Uri parse = Uri.parse(uri);
                    Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
                    intent.addFlags(
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                                    | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                                    | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, parse);
                    }
                    App.action().startActivityForResult(intent, 0);
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
        }


        //保存权限
        @SuppressLint("WrongConstant")
        public static boolean save(Intent data) {
            boolean result = false;
            try {
                if (data != null) {
                    Uri uri;
                    if ((uri = data.getData()) != null) {
                        App.action().getContentResolver().takePersistableUriPermission(uri, data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
                    }
                }
                result = true;
            } catch (Exception exception) {
                App.Log.send(exception);
            }
            return result;
        }
    }
}
