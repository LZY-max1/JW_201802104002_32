package util;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUtil {
    public static String getJson(HttpServletRequest request) throws IOException {
        ServletInputStream requestStream = request.getInputStream();
        InputStreamReader requestStreamReader = new InputStreamReader(requestStream, "UTF-8");
        BufferedReader streamReader = new BufferedReader(requestStreamReader);
        StringBuilder params_json = new StringBuilder();
        String line;
        while ((line = streamReader.readLine()) != null){
            params_json.append(line);
        }
        return params_json.toString();

    }
}
