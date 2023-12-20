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
    private Context mContext;
    private List<NavListItem> navListObjects;

    public interface Callback {
        void onItemClicked(int i);
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.menuText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.item_navigation_text, "field 'menuText'", TextView.class);
            viewHolder.menuImage = (ImageView) Utils.findRequiredViewAsType(view, (int) R.id.item_navigation_image, "field 'menuImage'", ImageView.class);
            viewHolder.divider = Utils.findRequiredView(view, (int) R.id.item_navigation_divider, "field 'divider'");
        }

        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            viewHolder.menuText = null;
            viewHolder.menuImage = null;
            viewHolder.divider = null;
        }
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_navigation_view, viewGroup, false));
    }

    public NavigationDrawerAdapter(List<NavListItem> list, Context context) {
        this.navListObjects = list;
        this.mContext = context;
    }

    public void onBindViewHolder(BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    public int getItemCount() {
        return this.navListObjects.size();
    }

    public void addItems(List<NavListItem> list) {
        this.navListObjects.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_navigation_divider)
        View divider;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_navigation_image)
        ImageView menuImage;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_navigation_text)
        TextView menuText;

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        protected void clear() {
        }

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        public void onBind(final int i) {
            super.onBind(i);
            this.menuText.setText(((NavListItem) NavigationDrawerAdapter.this.navListObjects.get(i)).getName());
            this.menuImage.setImageDrawable(ContextCompat.getDrawable(NavigationDrawerAdapter.this.mContext, ((NavListItem) NavigationDrawerAdapter.this.navListObjects.get(i)).getImage()));
            if (i == NavigationDrawerAdapter.this.getItemCount() - 1) {
                this.divider.setVisibility(View.GONE);
            }
            this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.main.NavigationDrawerAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    NavigationDrawerAdapter.this.mCallback.onItemClicked(i);
                }
            });
        }
    }
}
