package com.imooc.sell.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WhuService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 执行对应python脚本，获取Whu官网的通知
     * @return 通知的Json数组
     * @throws IOException
     */
    public JSONArray getWhuNotice(int page) throws IOException {
        String startCmd = String.format("python3 /usr/java/wyxyScript/WhuNotice.py "+page);
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

    /**
     * 获取Whu讲座列表
     * @param pageable 分页查询
     * @return
     */
    public JSONArray getLectureSum(Pageable pageable){
        JSONArray result = new JSONArray();
        List<Map<String, Object>> page = jdbcTemplate.queryForList("SELECT DISTINCT title,time FROM whu_lecture limit "
                +pageable.getPageSize()+" offset "+pageable.getOffset());
        for(Map whuLectureMap : page){
            JSONObject lecture = new JSONObject();
            lecture.put("title",whuLectureMap.get("title"));
            lecture.put("time",whuLectureMap.get("time"));
            result.add(lecture);
        }
        return result;
    }

    /**
     * 获取讲座信息（包括讲座的图片、发布学院）
     * @param time 讲座（贴）发布时间
     * @return
     */
    public JSONArray getLectureInfo(String time){
        List<Map<String, Object>> lectureInfoMaps = jdbcTemplate.queryForList("select url,college from whu_lecture where time = '" + time + "';");
        JSONArray result = new JSONArray();
        for(Map lectureInfo : lectureInfoMaps){
            JSONObject lecture = new JSONObject();
            lecture.put("imageUrl",lectureInfo.get("url"));
            lecture.put("college",lectureInfo.get("college"));
            result.add(lecture);
        }
        return result;
    }
}
