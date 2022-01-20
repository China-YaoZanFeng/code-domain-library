package org.domain.code.net;

import androidx.annotation.Nullable;
import org.domain.code.App;
import org.domain.code.Convert;
import org.domain.code.Text;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

public class Http {

    public static String GET = "GET";
    public static String POST = "POST";
    public static String ENCODING_UTF8 = "UTF-8";
    public static String DOWNLOAD_METHOD = POST;
    public static String UPLOAD_METHOD = POST;

    @Nullable
    public static String get(Object url) {
        return get(url, null, null, null);
    }

    public static String get(Object url, Object encoding) {
        return get(url, encoding, null, null);
    }

    public static String get(Object url, Object encoding, Object cookie) {
        return get(url, encoding, cookie, null);
    }

    @Nullable
    public static String get(Object url, Object encoding, Object cookie, Object header) {
        String result = null;
        try {
            if (url != null) {
                encoding = encoding == null ? ENCODING_UTF8 : encoding.toString();
                cookie = cookie == null ? Text.EMPTY : cookie.toString();
                header = header == null ? Text.EMPTY : cookie.toString();

                URL Url = new URL(url.toString());
                HttpURLConnection conn = httpURLConnection(url.toString());
                conn.setConnectTimeout(120000);
                conn.setFollowRedirects(true);
                conn.setDoInput(true);

                conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
                conn.setRequestProperty("Accept-Charset", encoding.toString());
                conn.setRequestProperty("Cookie", cookie.toString());
                conn.setRequestMethod(GET);
                header(conn, header.toString());//设置请求头
                conn.connect();

                int code = conn.getResponseCode();
                Map<String, List<String>> hs = conn.getHeaderFields();
                if (code >= 200 && code < 400) {
                    List<String> cs = hs.get("Set-Cookie");
                    StringBuffer cok = new StringBuffer();
                    if (cs != null)
                        for (String s : cs) {
                            cok.append(s + ";");
                        }

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input, encoding.toString()));
                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }
                    input.close();
                    result = new String(buffer);
                } else {
                    result = new String(conn.getResponseMessage());
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public static String post(Object url, Object data) {
        return post(url, data, null, null, null);
    }

    public static String post(Object url, Object data, Object encoding) {
        return post(url, data, encoding, null, null);
    }

    public static String post(Object url, Object data, Object encoding, Object cookie) {
        return post(url, data, encoding, cookie, null);
    }

    public static String post(Object url, Object data, Object encoding, Object cookie, Object header) {
        String result = null;
        try {
            if (url != null) {
                data = data == null ? Text.EMPTY : data.toString();
                encoding = encoding == null ? ENCODING_UTF8 : encoding.toString();
                cookie = cookie == null ? Text.EMPTY : cookie.toString();
                header = header == null ? Text.EMPTY : cookie.toString();

                URL Url = new URL(url.toString());
                HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                if (url.toString().startsWith("https://")) {
                    conn = (HttpsURLConnection) Url.openConnection();
                    ssl();
                }
                conn.setConnectTimeout(120000);
                conn.setFollowRedirects(true);
                conn.setDoInput(true);

                conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
                conn.setRequestProperty("Accept-Charset", encoding.toString());
                conn.setRequestProperty("Cookie", cookie.toString());
                conn.setRequestMethod(POST);
                byte[] bytes = formData(new String[]{data.toString()}, encoding.toString());
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-length", Text.EMPTY + bytes.length);

                header(conn, header.toString());//设置请求头
                conn.connect();

                OutputStream output = conn.getOutputStream();
                output.write(bytes);


                int code = conn.getResponseCode();
                Map<String, List<String>> hs = conn.getHeaderFields();
                if (code >= 200 && code < 400) {
                    List<String> cookieList = hs.get("Set-Cookie");
                    StringBuffer cookieBuffer = new StringBuffer();
                    if (cookieList != null)
                        for (String cookieData : cookieList) {
                            cookieBuffer.append(cookieData + ";");
                        }

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input, encoding.toString()));
                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }
                    input.close();
                    result = new String(buffer);
                } else {
                    result = new String(conn.getResponseMessage());
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    //下载文件
    public static boolean download(Object url, Object path) {
        return downloads(url, null, path, GET, true, null, null, null);
    }

    public static boolean download(Object url, Object path, Object repeat) {
        return downloads(url, null, path, GET, repeat, null, null, null);
    }

    public static boolean download(Object url, Object path, Object repeat, Object encoding) {
        return downloads(url, null, path, GET, repeat, encoding, null, null);
    }

    public static boolean download(Object url, Object path, Object repeat, Object encoding, Object cookie) {
        return downloads(url, null, path, GET, repeat, encoding, cookie, null);
    }

    public static boolean download(Object url, Object path, Object repeat, Object encoding, Object cookie, Object header) {
        return downloads(url, null, path, GET, repeat, encoding, cookie, header);
    }

    //下载文件(自定义请求方法)
    public static boolean downloads(Object url, Object data, Object path) {
        return downloads(url, data, path, null, null, null, null, null);
    }

    public static boolean downloads(Object url, Object data, Object path, Object method) {
        return downloads(url, data, path, method, null, null, null, null);
    }

    public static boolean downloads(Object url, Object data, Object path, Object method, Object repeat) {
        return downloads(url, data, path, method, repeat, null, null, null);
    }

    public static boolean downloads(Object url, Object data, Object path, Object method, Object repeat, Object encoding) {
        return downloads(url, data, path, method, repeat, encoding, null, null);
    }

    public static boolean downloads(Object url, Object data, Object path, Object method, Object repeat, Object encoding, Object cookie) {
        return downloads(url, data, path, method, repeat, encoding, cookie, null);
    }

    public static boolean downloads(Object url, Object data, Object path, Object method, Object repeat, Object encoding, Object cookie, Object header) {
        boolean result = false;

        try {
            if (url != null) {
                data = data == null ? Text.EMPTY : data.toString();
                path = path == null ? Text.EMPTY : path.toString();
                method = method == null ? POST : method.toString();
                repeat = Convert.to(Convert.BOOLEAN, repeat);
                encoding = encoding == null ? ENCODING_UTF8 : encoding.toString();
                cookie = cookie == null ? Text.EMPTY : cookie.toString();
                header = header == null ? Text.EMPTY : cookie.toString();

                if (!(boolean) repeat) {
                    //不覆盖原文件下载
                    if (new org.domain.code.File(path.toString()).exists()) {
                        result = true;
                    }
                } else {

                    URL Url = new URL(url.toString());
                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                    if (url.toString().startsWith("https://")) {
                        conn = (HttpsURLConnection) Url.openConnection();
                        Http.ssl();
                    }
                    conn.setConnectTimeout(120000);
                    conn.setFollowRedirects(true);
                    conn.setDoInput(true);
                    conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
                    conn.setRequestProperty("Accept-Charset", encoding.toString());
                    conn.setRequestProperty("Cookie", cookie.toString());
                    conn.setRequestMethod(method.toString());
                    byte[] bytes = new byte[1024];
                    if (method.toString().equals(POST) && data != null) {
                        bytes = formData(new String[]{data.toString()}, encoding.toString());
                        conn.setDoOutput(true);
                        conn.setRequestProperty("Content-length", Text.EMPTY + bytes.length);
                    }

                    header(conn, header.toString());//设置请求头
                    conn.connect();

                    if (method.toString().equals(POST)) {
                        OutputStream outputStream = conn.getOutputStream();
                        outputStream.write(bytes);
                    }

                    int code = conn.getResponseCode();
                    Map<String, List<String>> hs = conn.getHeaderFields();
                    if (code >= 200 && code < 400) {
                        List<String> cs = hs.get("Set-Cookie");
                        StringBuffer cok = new StringBuffer();
                        if (cs != null)
                            for (String s : cs) {
                                cok.append(s + ";");
                            }

                        if (path.toString().toString().endsWith("/")) {
                            String[] urlCharArray = url.toString().split("/");
                            String fileName = Text.EMPTY;
                            for (String name : urlCharArray) {
                                fileName = name;
                            }
                            path = path + URLDecoder.decode(fileName, encoding.toString());
                        }
                        InputStream input = conn.getInputStream();
                        org.domain.code.File.write(path, input);
                    }
                    result = true;
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    public static String upload(Object url, Object pathArray) {
        return upload(url, pathArray, null, null, null, null);
    }

    public static String upload(Object url, Object pathArray, Object key) {
        return upload(url, pathArray, key, null, null, null);
    }

    public static String upload(Object url, Object pathArray, Object key, Object encoding) {
        return upload(url, pathArray, key, encoding, null, null);
    }

    public static String upload(Object url, Object pathArray, Object key, Object encoding, Object cookie) {
        return upload(url, pathArray, key, encoding, cookie, null);
    }

    public static String upload(Object url, Object pathArray, Object key, Object encoding, Object cookie, Object header) {
        String result = null;
        try {
            key = (key == null ? "file" : key.toString());
            key = pathArray.toString().contains(",") ? key + "[]" : key;
            encoding = (encoding == null ? ENCODING_UTF8 : encoding.toString());
            cookie = (cookie == null ? Text.EMPTY : cookie.toString());
            header = (header == null ? Text.EMPTY : header.toString());
            String BOUNDARY = "--******";//边界标识
            String LINE_END = "\r\n";
            String CONTENT_TYPE = "multipart/form-data"; //内容类型
            URL Url = new URL(url.toString());
            HttpURLConnection conn = httpURLConnection(url.toString());
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(20000);
            conn.setDoInput(true); //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false); //不允许使用缓存
            conn.setRequestMethod(UPLOAD_METHOD); //请求方式
            conn.setRequestProperty("Charset", encoding.toString());
            conn.setRequestProperty("Cookie", cookie.toString());
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE +
                    ";" +
                    "boundary" +
                    "=" +
                    BOUNDARY.replaceAll("--", Text.EMPTY)
            );
            header(conn, header.toString());//设置请求头
            OutputStream output = conn.getOutputStream();
            DataOutputStream dataOutput = new DataOutputStream(output);

            int len;
            for(String path : Text.split(pathArray,",")) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(BOUNDARY);
                buffer.append(LINE_END);
                buffer.append("Content-Disposition: form-data; name=\"" + key.toString() + "\"; ");
                buffer.append("filename" +
                        "=" +
                        "\"" +
                        new org.domain.code.File(path.toString()).getName() +
                        "\""
                );
                buffer.append(LINE_END);
                buffer.append("Content-Type: " + (URLConnection.getFileNameMap()).getContentTypeFor(new org.domain.code.File(path).getName()));
                buffer.append(LINE_END);
                buffer.append(LINE_END);

                dataOutput.write(buffer.toString().getBytes());
                InputStream input = org.domain.code.File.input(path);
                byte[] bytes = new byte[1024];

                while ((len = input.read(bytes)) != -1) {
                    dataOutput.write(bytes, 0, len);
                }
                input.close();
                dataOutput.write(LINE_END.getBytes());
                dataOutput.flush();
            }

            dataOutput.write((BOUNDARY + "--") .getBytes());

            ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
            InputStream resultStream = conn.getInputStream();
            len = -1;
            byte[] byteBuffer = new byte[1024 * 8];
            while ((len = resultStream.read(byteBuffer)) != -1) {
                byteArrayOutput.write(byteBuffer, 0, len);
            }
            resultStream.close();
            byteArrayOutput.flush();
            byteArrayOutput.close();
            result = byteArrayOutput.toString();

        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return result;
    }

    public static HttpServer server(Object port) {
        return new HttpServer(port);
    }

    private static byte[] formData(Object[] p1, String encoding) throws UnsupportedEncodingException, IOException {
        byte[] bytes = null;
        if (p1.length == 1) {
            Object obj = p1[0];
            if (obj instanceof String) {
                bytes = ((String) obj).getBytes(encoding);
            } else if (obj.getClass().getComponentType() == byte.class) {
                bytes = (byte[]) obj;
            } else if (obj instanceof java.io.File) {
                bytes = inputToByte(new FileInputStream((java.io.File) obj));
            } else if (obj instanceof org.domain.code.File) {
                bytes = inputToByte(new FileInputStream((org.domain.code.File) obj));
            }
        }
        return bytes;
    }

    private static byte[] inputToByte(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
        byte[] buffer = new byte[2 ^ 32];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        byte[] ret = output.toByteArray();
        output.close();
        return ret;
    }

    //加密协议支持
    private static void ssl() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    private static void header(HttpURLConnection conn, String header) {
        try {
            if (header != null && !header.isEmpty()) {
                String[] headerArray = header.split(",");
                for (String str : headerArray) {
                    String[] names = str.split("=");
                    for (int i = 0; i < names.length; i++) {
                        App.toast(names[0] + names[1]);
                        conn.setRequestProperty(names[0], names[1]);
                    }
                }
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
    }

    private static HttpURLConnection httpURLConnection(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) (new URL(url)).openConnection();
            if (url.toString().startsWith("https://")) {
                ssl();
                conn = (HttpsURLConnection) (new URL(url)).openConnection();
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }
        return conn;
    }

}