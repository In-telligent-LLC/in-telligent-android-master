package com.sca.in_telligent.ui.group.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sca.in_telligent.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupListSpinnerAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> items;


    @Override
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

    static class ViewHolder {
        @BindView(R.id.spinner_item_text)
        TextView name;

        public ViewHolder(View view) {
            name = view.findViewById(R.id.spinner_item_text);

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
