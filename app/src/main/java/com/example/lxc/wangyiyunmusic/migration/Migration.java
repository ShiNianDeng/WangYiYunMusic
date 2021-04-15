package com.example.lxc.wangyiyunmusic.migration;

import com.example.lxc.wangyiyunmusic.model.AlbumMusicModel;
import com.example.lxc.wangyiyunmusic.model.MusicModel;

import javax.xml.validation.Schema;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {


    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        //第一次迁移
        if (oldVersion == 0) {

            schema.create("MusicModel")
                    .addField("musicId", String.class, null)
                    .addField("name", String.class,null)
                    .addField("poster", String.class, null)
                    .addField("author", String.class, null)
                    .addField("path", String.class,null);

            schema.create("AlbumMusicModel")
                    .addField("albumId", String.class, null)
                    .addField("name", String.class, null)
                    .addField("poster", String.class,null)
                    .addField("playNum", String.class,null)
                    .addRealmListField("list", schema.get("MusicModel"));

            schema.create("MusicSourceModel")
                    .addRealmListField("album", schema.get("AlbumMusicModel"))
                    .addRealmListField("hot", schema.get("MusicModel"));


            oldVersion = newVersion;
        }
    }
}
