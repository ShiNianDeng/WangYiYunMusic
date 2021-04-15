package com.example.lxc.wangyiyunmusic.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.adapters.HotAdapter;
import com.example.lxc.wangyiyunmusic.helps.RealmHelp;
import com.example.lxc.wangyiyunmusic.model.AlbumMusicModel;
import com.example.lxc.wangyiyunmusic.model.MusicSourceModel;

public class AlbumListActivity extends BaseActivity {
    public static final String ALBUM_ID = "albumId";

    private RecyclerView mRvAlbum;
    private HotAdapter adapter;
    private AlbumMusicModel model;
    private RealmHelp help;
    private String albumId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        initData();
        initView();
    }

    private void initData() {
        albumId = getIntent().getStringExtra(ALBUM_ID);
        help = new RealmHelp();
        model = help.getAlbumFromId(albumId);
    }

    private void initView() {
        initNavBar(true, "专辑列表", false);
        mRvAlbum = fd(R.id.rv_album);
        mRvAlbum.setLayoutManager(new LinearLayoutManager(this));
        mRvAlbum.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new HotAdapter(this, null, model.getList());
        mRvAlbum.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        help.close();
    }
}