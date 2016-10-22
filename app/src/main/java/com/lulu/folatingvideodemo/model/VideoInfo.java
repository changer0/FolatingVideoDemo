package com.lulu.folatingvideodemo.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lulu on 2016/10/22.
 *
 */
public class VideoInfo {

    private int width;
    private int height;
    private List<String> urls;


    public static VideoInfo createFromJson(JSONObject json){
        VideoInfo ret = null;
        if (json != null) {
            try {
                ret = new VideoInfo();
                ret.width = json.getInt("width");
                ret.height = json.getInt("height");
                JSONArray array = json.getJSONArray("url_list");
                ret.urls = new ArrayList<>();
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    JSONObject ulo = array.getJSONObject(i);
                    ret.urls.add(ulo.getString("url"));
                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 得到url信息
     * @return
     */
    public String getFirstUrl(){
        String ret = null;
        if (urls != null) {
            if(!urls.isEmpty()){
                ret = urls.get(0);
            }
        }
        return ret;
    }

}
