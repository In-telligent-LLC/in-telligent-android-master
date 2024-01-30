package com.sca.in_telligent.ui.base;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    private int mCurrentPosition;

    protected abstract void clear();

    public BaseViewHolder(View view) {
        super(view);
    }

    public void onBind(int i) {
        this.mCurrentPosition = i;
        clear();
    }

    public int getCurrentPosition() {
        return this.mCurrentPosition;
    }
}
