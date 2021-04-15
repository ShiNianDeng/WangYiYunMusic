package com.example.lxc.wangyiyunmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.activitys.AlbumListActivity;
import com.example.lxc.wangyiyunmusic.model.AlbumMusicModel;
import com.example.lxc.wangyiyunmusic.views.WEqualsHImageView;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {
    private Context context;
    private View mView;
    private List<AlbumMusicModel> albumList;

    public RecommendAdapter(Context context, List<AlbumMusicModel> list) {
        this.context = context;
        albumList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(context).inflate(R.layout.item_grid_music, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlbumMusicModel model = albumList.get(position);
        holder.tvSongName.setText(model.getName());
        holder.ivIcon.getTvNumber().setText(model.getPlayNum());
        Glide
                .with(context)
                .load(model.getPoster())
                .into(holder.ivIcon.getIvImg());

        //设置mView的点击事件，跳转到专辑列表Activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlbumListActivity.class);
                intent.putExtra(AlbumListActivity.ALBUM_ID, model.getAlbumId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        WEqualsHImageView ivIcon;
        TextView tvSongName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_ewh);
            tvSongName = itemView.findViewById(R.id.tv_song_list);
        }
    }
}
