package com.imooc.sell.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Slf4j
public class WhuService {

    /**
     * 执行对应python脚本，获取Whu官网的通知
     * @return 通知的Json数组
     * @throws IOException
     */
    public JSONArray getWhuNotice() throws IOException {
        String startCmd = String.format("python3 /usr/java/wyxyScript/WhuNotice.py");
        String sh[] = {"/bin/sh","-c",startCmd};
        StringBuilder sb =new StringBuilder();
        Process process = Runtime.getRuntime().exec(sh);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line=bufferedReader.readLine())!=null) {
            sb.append(line);
        }
        return JSONArray.parseArray(sb.toString());
    }
}
