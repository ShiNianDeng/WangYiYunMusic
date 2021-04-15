package com.example.lxc.wangyiyunmusic.helps;

import android.content.Context;
import android.media.ImageReader;
import android.widget.Toast;

import com.example.lxc.wangyiyunmusic.migration.Migration;
import com.example.lxc.wangyiyunmusic.model.AlbumMusicModel;
import com.example.lxc.wangyiyunmusic.model.MusicModel;
import com.example.lxc.wangyiyunmusic.model.MusicSourceModel;
import com.example.lxc.wangyiyunmusic.model.UserModel;
import com.example.lxc.wangyiyunmusic.utils.DataUtils;

import java.io.FileNotFoundException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelp {

    private Realm mRealm;

    public RealmHelp() {
        this.mRealm = Realm.getDefaultInstance();
    }

    /**
     * 通知Realm数据库迁移数据，并且为Realm设置最新配置
     */
    public static void migration() {
        RealmConfiguration conf = getRealmConfig();
        Realm.setDefaultConfiguration(conf);
        try {
            Realm.migrateRealm(conf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到Realm数据库配置
     */
    private static RealmConfiguration getRealmConfig() {
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new Migration())
                .build();
    }

    /**
     * 注册用户
     *
     * @param userModel
     * @return
     */
    public void registerUser(UserModel userModel) {
        mRealm.beginTransaction();
        mRealm.insert(userModel);
//        mRealm.insertOrUpdate(userModel);
        mRealm.commitTransaction();
    }


    /**
     * 返回所有用户
     */
    public List<UserModel> getAllUsere() {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        RealmResults<UserModel> results = query.findAll();
        return results;
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }

    /**
     * 用户登录 true:用户账号密码正确   false:手机号或者密码错误
     */
    public boolean userLogin(String phone, String password) {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        query = query.equalTo("phone", phone).equalTo("password", password);
        UserModel model = query.findFirst();
        if (model != null) {
//            如果model不等于空，则表示该用户存在于数据库中
            return true;
        }

        return false;
    }

    /**
     * 修改密码
     */
    public void userChangePassword(String password) {
        mRealm.beginTransaction();
        getCurrentUser().setPassword(password);
        mRealm.commitTransaction();
    }

    /**
     * 获取当前用户
     */
    public UserModel getCurrentUser() {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel = query.equalTo("phone", UserHelp.geteInstance().getPhone()).findFirst();
        return userModel;
    }

    /**
     * 获取音乐数据源
     */
    public void getMusicSource(Context context) {
        String musicDataJson = DataUtils.getDataFromAssets(context, "DataSource.json");
        mRealm.beginTransaction();
        mRealm.createObjectFromJson(MusicSourceModel.class, musicDataJson);
        mRealm.commitTransaction();
    }

    /**
     * 删除音乐数据
     */
    public void removeMusicSource() {
        mRealm.beginTransaction();
//        mRealm.deleteAll();
        mRealm.delete(MusicSourceModel.class);
        mRealm.delete(MusicModel.class);
        mRealm.delete(AlbumMusicModel.class);
        mRealm.commitTransaction();
    }

    /**
     * 查询音乐源数据
     */
    public MusicSourceModel queryMusicSource(){
        MusicSourceModel model = null;
        mRealm.beginTransaction();
        model = mRealm.where(MusicSourceModel.class).findFirst();
        mRealm.commitTransaction();
        return model;
    }

    /**
     * 根据歌单Id查询歌单中的歌曲
     */
    public AlbumMusicModel getAlbumFromId(String ablumId){
        AlbumMusicModel model  = null;
        mRealm.beginTransaction();
        model = mRealm.where(AlbumMusicModel.class).equalTo("albumId",ablumId).findFirst();
        mRealm.commitTransaction();
        return model;
    }

    /**
     * 根据音乐Id查询音乐
     */
    public MusicModel getMusicFromId(String musicId){
        MusicModel model  = null;
        mRealm.beginTransaction();
        model = mRealm.where(MusicModel.class).equalTo("musicId",musicId).findFirst();
        mRealm.commitTransaction();
        return model;
    }
}
