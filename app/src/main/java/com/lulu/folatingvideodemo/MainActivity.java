package com.lulu.folatingvideodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.ListView;

import com.lulu.folatingvideodemo.model.VideoInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lulu on 2016/10/22.
 * 使用浮动播放窗口
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @ViewInject(R.id.main_surface_view)
    private SurfaceView mSurfaceView;
    @ViewInject(R.id.main_list_view)
    private ListView mListView;


    List<VideoInfo> mVideoInfos;
    private VideoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        mVideoInfos = new ArrayList<>();
        mAdapter = new VideoListAdapter(this, mVideoInfos);
        mListView.setAdapter(mAdapter);

        String url = "http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=-104&message_cursor=-1&loc_time=1432654641&latitude=40.0522901291784&longitude=116.23490963616668&city=北京&count=30&screen_width=800&iid=2767929313&device_id=2757969807&ac=wifi&channel=baidu2&aid=7&app_name=joke_essay&version_code=400&device_platform=android&device_type=KFTT&os_api=15&os_version=4.0.3&openudid=b90ca6a3a19a78d6";

        RequestParams params = new RequestParams(url);
        requestVideoList(params);

    }

    @Override
    protected void onDestroy() {
        mAdapter.destroy();
        super.onDestroy();
    }
    
    private void requestVideoList(RequestParams requestParams){
        x.http().get(requestParams, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                mVideoInfos.clear();

                //解析视频猎豹
                try {
                    JSONObject outerData = result.getJSONObject("data");
                    JSONArray innerData = outerData.getJSONArray("data");
                    int len = innerData.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject itemJson = innerData.getJSONObject(i);
                        int type = itemJson.getInt("type");
                        if (type == 1) {
                            JSONObject group = itemJson.getJSONObject("group");
                            JSONObject video = group.getJSONObject("360p_video");
                            VideoInfo info = VideoInfo.createFromJson(video);
                            mVideoInfos.add(info);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
