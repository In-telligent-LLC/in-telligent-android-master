package com.sca.in_telligent.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.NavListItem;
import com.sca.in_telligent.ui.base.BaseViewHolder;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Callback mCallback;
    private final Context mContext;
    private final List<NavListItem> navListObjects;

    public interface Callback {
        void onItemClicked(int i);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.item_navigation_view, parent, false));
    }

    public NavigationDrawerAdapter(List<NavListItem> navList, Context context) {
        navListObjects = navList;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return navListObjects.size();
    }

    public void addItems(List<NavListItem> listObjects) {
        navListObjects.addAll(listObjects);
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.item_navigation_text)
        TextView menuText;

        @BindView(R.id.item_navigation_image)
        ImageView menuImage;

        @BindView(R.id.item_navigation_divider)
        View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        public void onBind(final int position) {
            super.onBind(position);

            String name = navListObjects.get(position).getName();
            menuText.setText(name);

            menuImage.setImageDrawable(
                    ContextCompat.getDrawable(mContext, navListObjects.get(position).getImage()));

            if (position == getItemCount() - 1) {
                divider.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onItemClicked(position);
                }
            });
        }
    }

}
