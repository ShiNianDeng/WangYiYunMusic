package com.example.lxc.wangyiyunmusic.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;
    private RecyclerView parent;
    public GridSpaceItemDecoration(int mSpace,RecyclerView recyclerView) {
        super();
        parent = recyclerView;
        this.mSpace = mSpace;setRecyclerViewoffsets(parent);

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace;

        setRecyclerViewoffsets(parent);
    }

    private void setRecyclerViewoffsets(RecyclerView parent) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) parent.getLayoutParams();
        params.leftMargin = -mSpace;
        parent.setLayoutParams(params);
    }


}
