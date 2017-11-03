package com.jun.timer.utils.httpClient;

import com.jun.timer.utils.httpClient.json.JsonFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * Created by jun on 16/7/2.
 */
public class ResponseUtils{
    Log log= LogFactory.getLog(ResponseUtils.class);
    private HttpResponse httpResponse;
    public ResponseUtils(HttpResponse httpResponse) {
        Objects.requireNonNull(httpResponse);
        this.httpResponse = httpResponse;
    }

    public String respToString() throws IOException {
        return EntityUtils.toString(this.httpResponse.getEntity());
    }
    public String respToString(String charSet) throws IOException {
        return EntityUtils.toString(this.httpResponse.getEntity(), charSet);
    }
    public byte[] respToByte() throws IOException {
        return EntityUtils.toByteArray(this.httpResponse.getEntity());
    }
    public void writeToFile(String dest) throws IOException {

        Path path= Paths.get(dest);
        Files.copy(this.httpResponse.getEntity().getContent(),path, StandardCopyOption.REPLACE_EXISTING);
    }
    public void writeToOutputStream(OutputStream out) throws IOException {
        this.httpResponse.getEntity().writeTo(out);

    }
    public void printConsole(String... charSet) throws IOException {
        String content = null;
        HttpEntity entity = this.httpResponse.getEntity();
            if (charSet != null && charSet.length > 0) {
                content = EntityUtils.toString(entity, charSet[0]);
            } else {
                content = EntityUtils.toString(entity);
            }

        System.out.println(content);
    }

    public <T> T respToObj(Type type, Charset charset) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = this.httpResponse.getEntity().getContent();
            return JsonFactory.getInstance().lookup()
                    .fromJson(new InputStreamReader(inputStream, charset), type);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

    }
    public <T> T respToObj(Type type) throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        InputStream inputStream = null;
        return respToObj(type,charset);
    }
    public <T> T respToObj(Class<T> cls) throws IOException {
        return respToObj((Type) cls);
    }



}
