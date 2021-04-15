package com.example.lxc.wangyiyunmusic;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.activitys.BaseActivity;
import com.example.lxc.wangyiyunmusic.adapters.RecommendAdapter;
import com.example.lxc.wangyiyunmusic.helps.RealmHelp;
import com.example.lxc.wangyiyunmusic.model.AlbumMusicModel;
import com.example.lxc.wangyiyunmusic.model.MusicSourceModel;
import com.example.lxc.wangyiyunmusic.views.GridSpaceItemDecoration;
import com.example.lxc.wangyiyunmusic.adapters.HotAdapter;

import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView rvRecommend, rvHot;
    private RecommendAdapter mRecommendAdapter;
    private HotAdapter mHotAdapter;
    private MusicSourceModel model;
    private RealmHelp help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        help = new RealmHelp();
        model = help.queryMusicSource();
    }

    private void initView() {
        initNavBar(false, "网易云音乐", true);

        //推荐歌单RecyclerView
        rvRecommend = fd(R.id.rv_recommend);
        rvRecommend.setLayoutManager(new GridLayoutManager(this, 3));
        mRecommendAdapter = new RecommendAdapter(this, model.getAlbum());
        rvRecommend.setAdapter(mRecommendAdapter);
//        设置Android系统默认的RecyclerView分割线
//        rvRecommend.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        rvRecommend.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.albumMarginSize), rvRecommend));
//        禁用该View嵌套滚动
        rvRecommend.setNestedScrollingEnabled(false);


        //热门音乐RecyclerView
        rvHot = fd(R.id.rv_hot);
        rvHot.setLayoutManager(new LinearLayoutManager(this));
        mHotAdapter = new HotAdapter(this, rvHot,model.getHot());
        rvHot.setAdapter(mHotAdapter);
        rvHot.setNestedScrollingEnabled(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        help.close();
    }
}