package com.lulu.folatingvideodemo;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.lulu.folatingvideodemo.model.VideoInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by Lulu on 2016/10/22.
 *
 */
public class VideoListAdapter extends BaseAdapter {
    private Context mContext;
    private List<VideoInfo> mInfos;

    private static MediaPlayer sMediaPlayer;
    //当前播放的位置
    private static int sPlayingPosition;
    private LayoutInflater mInflater;

    public VideoListAdapter(Context context, List<VideoInfo> infos) {
        mContext = context;
        mInfos = infos;
        mInflater = LayoutInflater.from(mContext);
        sMediaPlayer = new MediaPlayer();
        sPlayingPosition = -1;

    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_video, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.bindView(position, mInfos.get(position));

        return convertView;
    }

    /**
     * 释放资源
     */
    public void destroy() {
        if (sMediaPlayer != null) {
//            sMediaPlayer.stop();
//            sMediaPlayer.reset();
            sMediaPlayer.release();
            sMediaPlayer = null;
        }
    }


    private static class ViewHolder implements SurfaceHolder.Callback, View.OnClickListener {
        private SurfaceView mSurfaceView;
        private Button mButton;
        private int itemPosition;
        private VideoInfo mInfo;
        private ImageView mCover;

        public ViewHolder(View itemView) {
            mSurfaceView = (SurfaceView) itemView.findViewById(R.id.video_surface_view);
            mButton = (Button) itemView.findViewById(R.id.video_play_btn);
            mCover = (ImageView)itemView.findViewById(R.id.video_cover);
            mSurfaceView.getHolder().addCallback(this);
            mButton.setOnClickListener(this);
        }

        void bindView(int position, VideoInfo info) {
            mInfo = info;
            itemPosition = position;

            if (sMediaPlayer.isPlaying()){
                if (position == sPlayingPosition) {
                    //当前加载与当前播放位置相同时
                    sMediaPlayer.setDisplay(mSurfaceView.getHolder());
                    mCover.setVisibility(View.INVISIBLE);
                } else {
                    mCover.setVisibility(View.VISIBLE);
                }
            }

        }




        ///////////////////////////////////////////////////////////////////////////
        // ViewHolder内部SurfaceView回调
        ///////////////////////////////////////////////////////////////////////////
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

        ///////////////////////////////////////////////////////////////////////////
        // ViewHolder内部事件监听
        ///////////////////////////////////////////////////////////////////////////
        @Override
        public void onClick(View v) {
            //点击播放视频按钮
            if (sMediaPlayer.isPlaying()) {
                sMediaPlayer.stop();
            }
            //封面隐藏
            mCover.setVisibility(View.INVISIBLE);
            //播放器重置
            sMediaPlayer.reset();
            // 并切换到显示到SurfaceView
            sMediaPlayer.setDisplay(mSurfaceView.getHolder());
            // 设置视频地址, 开始加载
            String url = mInfo.getFirstUrl();

            if (url != null) {
                try {
                    sMediaPlayer.setDataSource(url);
                    //异步准备, 因为是网络数据的原因
                    sMediaPlayer.prepareAsync();
                    //设置当前的播放位置
                    sPlayingPosition = itemPosition;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }



}
