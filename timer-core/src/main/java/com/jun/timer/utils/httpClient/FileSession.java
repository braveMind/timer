package com.jun.timer.utils.httpClient;

import com.jun.timer.utils.httpClient.service.HttpEntityProviderService;
import com.jun.timer.utils.httpClient.service.SessionService;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * function:
 * Created by jun
 * 16/11/8 下午6:52.
 */
public class FileSession extends Session implements SessionService {
    public FileSession(HttpEntityProviderService providerService) {
        super(providerService);
    }
    public Session uploadFile(File file,String filename,String appName,String dirName){

        MultipartEntityBuilder multipartEntity= (MultipartEntityBuilder) this.getProviderService().provider();
        multipartEntity.addBinaryBody("file",file);
        multipartEntity.addTextBody("dirName", dirName);
        multipartEntity.addTextBody("appName",appName);
        multipartEntity.addTextBody("filename",filename);

        return this;
    }


    public Session uploadFile(byte [] bytes,String filename,String appName,String dirName){
        MultipartEntityBuilder multipartEntity= (MultipartEntityBuilder) this.getProviderService().provider();
        ContentBody byteArrayBody = new ByteArrayBody(bytes, filename);
        multipartEntity.addPart("file", byteArrayBody);
        multipartEntity.addTextBody("dirName", dirName);
        multipartEntity.addTextBody("filename", filename);
        multipartEntity.addTextBody("appName",appName);
        return this;
    }

    public Session uploadFile(InputStream stream,String filename,String appName,String dirName){
        MultipartEntityBuilder multipartEntity= (MultipartEntityBuilder) this.getProviderService().provider();
        multipartEntity.addBinaryBody("stream",stream);
        multipartEntity.addTextBody("dirName", dirName);
        multipartEntity.addTextBody("filename", filename);
        multipartEntity.addTextBody("appName",appName);
        return this;
    }

    public Session addParams(Map<String,String> map){
        MultipartEntityBuilder multipartEntity= (MultipartEntityBuilder) this.getProviderService().provider();

        for(String key:map.keySet()){
            multipartEntity.addTextBody(key,map.get(key));
        }
        return this;
    }


    public Session addParams(Map<String,String> map,File file){
        MultipartEntityBuilder multipartEntity= (MultipartEntityBuilder) this.getProviderService().provider();
        for(String key:map.keySet()){
            multipartEntity.addTextBody(key,map.get(key));
        }
        multipartEntity.addBinaryBody(file.getName(),file);
        return this;
    }


}
