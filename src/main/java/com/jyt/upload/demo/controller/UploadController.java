package com.jyt.upload.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyt.upload.demo.bean.AppReqBean;
import com.jyt.upload.demo.bean.BaseHttpParamsBean;
import com.jyt.upload.demo.util.MockClient;
import com.jyt.upload.demo.util.aes.AesHelper;
import com.jyt.upload.demo.util.common.HttpClient431Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class UploadController {

    private final static String BOUNDARY = UUID.randomUUID().toString()
            .toLowerCase().replaceAll("-", "");// 边界标识
    private final static String PREFIX = "--";// 必须存在
    private final static String LINE_END = "\r\n";

    @RequestMapping("/")
    public String index() {
        return "upload";
    }


    @RequestMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("token")String token, Map<String,Object> map) throws Exception{
        if (file.isEmpty()) {
            map.put("message", "文件不能为空");
            return "uploadStatus";
        }

        HttpURLConnection conn = null;
        InputStream input = null;
        OutputStream os = null;
        BufferedReader br = null;
        StringBuffer buffer = null;
        try {
            URL url = new URL("http://10.10.10.167:8000/jyt-atb-app/uploadFile");
//            URL url = new URL("http://127.0.0.1:9080/app/uploadFile");
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(1000 * 10);
            conn.setReadTimeout(1000 * 10);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.connect();

            Map<String,MultipartFile> requestFile = new HashMap<>();
            requestFile.put("file",file);

            Map<String,String> requestText = new HashMap<>();
            requestText.put("token",token);
            requestText.put("fileName",file.getOriginalFilename());

            // 往服务器端写内容 也就是发起http请求需要带的参数
            os = new DataOutputStream(conn.getOutputStream());
            // 请求参数部分
            writeParams(requestText, os);
            // 请求上传文件部分
            writeFile(requestFile, os);
            // 请求结束标志
            String endTarget = PREFIX + BOUNDARY + PREFIX + LINE_END;
            os.write(endTarget.getBytes());
            os.flush();

            // 读取服务器端返回的内容
            System.out.println("======================响应体=========================");
            System.out.println("ResponseCode:" + conn.getResponseCode()
                    + ",ResponseMessage:" + conn.getResponseMessage());
            if(conn.getResponseCode()==200){
                input = conn.getInputStream();
            }else{
                map.put("message", "文件上传失败,详情为: " + conn.getResponseCode() + "--"+ conn.getResponseMessage());
                return "uploadStatus";
            }

            br = new BufferedReader(new InputStreamReader( input, "UTF-8"));
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            //......
            System.out.println("返回报文:" + buffer.toString());
            BaseHttpParamsBean baseHttpParamsBean = JSON.parseObject(buffer.toString(), BaseHttpParamsBean.class);

            //开始解密响应信息
            MockClient mockClient = new MockClient();
            byte[] resKey = mockClient.decryptKey(baseHttpParamsBean.getKeyEnc());
            String json = AesHelper.decryptMsg(baseHttpParamsBean.getEnc(), resKey, AesHelper.CIPHER_ALGORITHM_CBC);
            System.out.println("解密后的响应报文"+json);
            map.put("message", "文件上传成功,得到的响应为.. '" + json + "'");
        } catch (Exception e) {
            map.put("message", "文件上传失败,得到的响应为.. '" + e + "'");
            return "uploadStatus";
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                    conn = null;
                }

                if (os != null) {
                    os.close();
                    os = null;
                }

                if (br != null) {
                    br.close();
                    br = null;
                }
            } catch (IOException ex) {
                throw new Exception(ex);
            }
        }
        return "uploadStatus";
    }


    /**
     * 对post参数进行编码处理并写入数据流中
     * @throws Exception
     *
     * @throws IOException
     *
     * */
    private static void writeParams(Map<String, String> requestText,
                                    OutputStream os) throws Exception {
        try{
            String msg = "请求参数部分:\n";
            if (requestText == null || requestText.isEmpty()) {
                msg += "空";
            } else {
                StringBuilder requestParams = new StringBuilder();
                Set<Map.Entry<String, String>> set = requestText.entrySet();
                Iterator<Map.Entry<String, String>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = it.next();
                    requestParams.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    requestParams.append("Content-Disposition: form-data; name=\"")
                            .append(entry.getKey()).append("\"").append(LINE_END);
                    requestParams.append("Content-Type: text/plain; charset=utf-8")
                            .append(LINE_END);
                    requestParams.append("Content-Transfer-Encoding: 8bit").append(
                            LINE_END);
                    requestParams.append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容
                    requestParams.append(entry.getValue());
                    requestParams.append(LINE_END);
                }
                os.write(requestParams.toString().getBytes());
                os.flush();

                msg += requestParams.toString();
            }

            //System.out.println(msg);
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    /**
     * 对post上传的文件进行编码处理并写入数据流中
     *
     * @throws IOException
     *
     * */
    private static void writeFile(Map<String, MultipartFile> requestFile,
                                  OutputStream os) throws Exception {
        InputStream is = null;
        try{
            String msg = "请求上传文件部分:\n";
            if (requestFile == null || requestFile.isEmpty()) {
                msg += "空";
            } else {
                StringBuilder requestParams = new StringBuilder();
                Set<Map.Entry<String, MultipartFile>> set = requestFile.entrySet();
                Iterator<Map.Entry<String, MultipartFile>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry<String, MultipartFile> entry = it.next();
                    if(entry.getValue() == null){//剔除value为空的键值对
                        continue;
                    }
                    requestParams.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    requestParams.append("Content-Disposition: form-data; name=\"")
                            .append(entry.getKey()).append("\"; filename=\"")
                            .append(entry.getValue().getOriginalFilename()).append("\"")
                            .append(LINE_END);
                    requestParams.append("Content-Type:")
                            .append(entry.getValue().getContentType())
                            .append(LINE_END);
                    requestParams.append("Content-Transfer-Encoding: 8bit").append(
                            LINE_END);
                    requestParams.append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容

                    os.write(requestParams.toString().getBytes());
                    os.write(entry.getValue().getBytes());

                    os.write(LINE_END.getBytes());
                    os.flush();

                    msg += requestParams.toString();
                }
            }
            //System.out.println(msg);
        }catch(Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(is!=null){
                    is.close();
                }
            }catch(Exception e){
                throw new Exception(e);
            }
        }
    }

}
