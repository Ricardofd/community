package com.newcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommunityUtil {
    //整一些静态的东西
    //生成随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("_","");
    }
    //密码MD5加密,如密码hello，用hello+随机字符串 -》md5码
    public static String md5(String key){
        if(StringUtils.isBlank(key))return null;
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    //封装数据到json格式
    public static String getJSONString(int code, String msg, Map<String, Object> map){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        if(map!=null){
            for(String key:map.keySet()){
                json.put(key,map.get(key));
            }
        }
        return json.toJSONString();
    }
    public static String getJSONString(int code, String msg){
        return(getJSONString(code,msg,null));
    }
    public static String getJSONString(int code){
        return(getJSONString(code,null,null));
    }

    public static void main(String[] args){
        Map<String,Object> map = new HashMap<>();
        map.put("name","lwy");
        map.put("age",20);
        System.out.println(getJSONString(0,"ok",map));
    }
}
