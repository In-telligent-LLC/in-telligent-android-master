package com.sca.in_telligent.ui.group.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.sca.in_telligent.R;
import java.util.List;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class GroupListSpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> items;

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.name = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.spinner_item_text, "field 'name'", TextView.class);
        }

        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            viewHolder.name = null;
        }
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.spinner_list_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(this.items.get(i));
        return view;
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    static class ViewHolder {
        @BindView(R.id.spinner_item_text)
        TextView name;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public GroupListSpinnerAdapter(Context context, List<String> list) {
        super(context, (int) R.layout.spinner_list_item, list);
        this.items = list;
        this.context = context;
    }

    public void addItems(List<String> list) {
        this.items.addAll(list);
        notifyDataSetChanged();
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.spinner_dropdown_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(getContext().getResources().getString(R.string.filter, this.items.get(i)));
        return view;
    }
}
