package com.lulu.folatingvideodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.ListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
/**
 * Created by Lulu on 2016/10/22.
 *
 */
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.main_surface_view)
    private SurfaceView mSurfaceView;
    @ViewInject(R.id.main_list_view)
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);


    }
}
