package com.example.lxc.wangyiyunmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.activitys.AlbumListActivity;
import com.example.lxc.wangyiyunmusic.activitys.PlayMusicActivity;
import com.example.lxc.wangyiyunmusic.model.MusicModel;

import java.util.List;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.ViewHolder> {

    private Context context;
    private View mItemView;
    private RecyclerView mRecyclerView;
    private boolean isSetRecyclerViewHeight = false;    //是否设置了RecyclerView的高度

    private List<MusicModel> musicList;

    public HotAdapter(Context context, RecyclerView recyclerView, List<MusicModel> list) {
        this.context = context;
        this.mRecyclerView = recyclerView;
        musicList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(context).inflate(R.layout.item_list_hot, parent, false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setRecyclerViewHeight();
        MusicModel model = musicList.get(position);

        holder.tvName.setText(model.getName());
        holder.tvAuthor.setText(model.getAuthor());
        Glide
                .with(context)
                .load(model.getPoster())
                .into(holder.imageView);


        //设置itemView的点击事件，跳转到播放音乐Activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                intent.putExtra(PlayMusicActivity.MUSIC_ID, model.getMusicId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View itemView;
        TextView tvName, tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.hot_list_img);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }

    /**
     * 设置RecyclerView的高度：
     * 单个ItemView的高度  *  ItemView的数量 = RecyclerView的高度
     */
    private void setRecyclerViewHeight() {
        if (isSetRecyclerViewHeight || mRecyclerView == null) return;
        isSetRecyclerViewHeight = true;
        int itemViewNumber = getItemCount();
        int itemViewHeight = mItemView.getLayoutParams().height;
        int recyclerViewHeight = itemViewNumber * itemViewHeight;

        //先将RecyclerView的LayoutParams取出来，设置到LayoutParams的高度后，再设置回去
        LinearLayout.LayoutParams lllp = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
        lllp.height = recyclerViewHeight;
        mRecyclerView.setLayoutParams(lllp);
    }
}