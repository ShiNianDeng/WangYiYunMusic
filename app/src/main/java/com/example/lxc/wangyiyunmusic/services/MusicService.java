package com.example.lxc.wangyiyunmusic.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.activitys.WelcomeActivity;
import com.example.lxc.wangyiyunmusic.helps.MyMediaPlayerHelp;
import com.example.lxc.wangyiyunmusic.model.MusicModel;

public class MusicService extends Service {

    private static final int NOTIFICATION_ID = 1;

    private MyMediaPlayerHelp helper;
    private MusicModel mMusicModel;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化helper
        helper = MyMediaPlayerHelp.getInstance(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        /**
         * PlayMusicView
         * 播放音乐、暂停音乐
         * 开启服务、绑定服务、解绑服务
         * <p>
         * MediaPlayerHelp
         * 播放音乐、暂停音乐
         * 监听音乐播放完成，停止服务
         */
        public void startMusic() {
            /**
             * 判断当前是否已经正在播放音乐
             *          如果当前播放的音乐路径和需要播放的音乐路径一致，则直接执行helper.playMusic()方法
             *          如果当前没有正在播放的音乐，或者当前播放的音乐和需要播放的音乐路径不一致，则先调用helper.setPath(path)方法,
             *              再调用helper.playMusic()方法
             */
            if (helper.getCurrentPlayPath() != null && helper.getCurrentPlayPath().equals(mMusicModel.getPath())) {
                helper.playMusic();
            } else {
                helper.setPath(mMusicModel.getPath());
                helper.setCurrentPlayPath(mMusicModel.getPath());
                helper.setOnMyMediaPlayerHelperPreparedListener(new MyMediaPlayerHelp.OnMyMediaPlayerHelperPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        helper.playMusic();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }
                });
            }
        }

        public void pauseMusic() {
            helper.pauseMusic();
        }

        public void setMusic(MusicModel musicModel) {
            mMusicModel = musicModel;
            startForeground();
        }
    }



    /**
     * 系统默认不允许不可见的后台服务播放音乐，
     * 设置服务在前台可见
     */
    private void startForeground() {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, WelcomeActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = null;

        /**
         * Android 26以上Notification特性适配
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = createNotificationChannel();
            notification = new Notification.Builder(this, channel.getId())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setContentIntent(pendingIntent)
                    .build();
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        } else {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.logo)
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setContentIntent(pendingIntent)
                    .build();
        }

        startForeground(NOTIFICATION_ID,notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createNotificationChannel() {
        String channelId = "wangyiyun";
        String channelName = "WangyiyunMusicService";
        String description = "WangyiyunMusic";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(description);
        channel.setLightColor(Color.RED);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        channel.setShowBadge(false);

        return channel;
    }


}