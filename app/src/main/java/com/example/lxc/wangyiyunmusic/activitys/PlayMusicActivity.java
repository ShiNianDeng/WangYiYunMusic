package com.example.lxc.wangyiyunmusic.activitys;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.helps.MyMediaPlayerHelp;
import com.example.lxc.wangyiyunmusic.helps.RealmHelp;
import com.example.lxc.wangyiyunmusic.model.MusicModel;
import com.example.lxc.wangyiyunmusic.views.PlayMusicView;

public class PlayMusicActivity extends BaseActivity {
    public static final String MUSIC_ID = "musicId";
    private ImageView ivIcon;
    private PlayMusicView playMusicView;
    private String musicId;
    private RealmHelp help;
    private MusicModel model;
    private TextView tvName,tvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏Activity最上方的任务栏
        //方法一：
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //方法二：隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_music);
        initData();
        initView();
    }

    private void initData() {
        musicId = getIntent().getStringExtra(MUSIC_ID);
        help = new RealmHelp();
        model = help.getMusicFromId(musicId);
    }

    private void initView() {
        ivIcon = fd(R.id.iv_icon);
        tvName = fd(R.id.tv_name);
        tvAuthor = fd(R.id.tv_author);
        tvName.setText(model.getName());
        tvAuthor.setText(model.getAuthor());

        Glide.with(this).load(model.getPoster()).into(ivIcon);

        playMusicView = findViewById(R.id.play_music_view);
        playMusicView.setMusicModel(model);
        playMusicView.playMusic();
    }

    /**
     * 点击音乐界面的后退事件
     *
     * @param view
     */
    public void onBackPressed(View view) {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        help.close();
        playMusicView.destory();
    }
}