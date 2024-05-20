package com.sca.in_telligent.ui.group.list;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sca.in_telligent.R;
import java.util.List;
import java.util.Locale;

public class GroupListSpinnerAdapter extends ArrayAdapter<String> {

  private List<String> items;
  private Context context;

  @Override
  public View getDropDownView(int position, View view,
      ViewGroup parent) {
    GroupListSpinnerAdapter.ViewHolder holder;
    if (view == null) {
      view = LayoutInflater.from(context).inflate(R.layout.spinner_list_item, parent, false);
      holder = new GroupListSpinnerAdapter.ViewHolder(view);
      view.setTag(holder);
    } else {
      holder = (GroupListSpinnerAdapter.ViewHolder) view.getTag();
    }

    holder.name.setText(items.get(position));

    return view;
  }

  static class ViewHolder {

    @BindView(R.id.spinner_item_text)
    TextView name;

    public ViewHolder(View view) {
      ButterKnife.bind(this, view);
    }
  }

  public GroupListSpinnerAdapter(Context context,
      List<String> items) {
    super(context, R.layout.spinner_list_item, items);
    this.items = items;
    this.context = context;
  }

  public void addItems(List<String> listObjects) {
    items.addAll(listObjects);
    notifyDataSetChanged();
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {

    GroupListSpinnerAdapter.ViewHolder holder;
    if (view == null) {
      view = LayoutInflater.from(context).inflate(R.layout.spinner_dropdown_item, parent, false);
      holder = new GroupListSpinnerAdapter.ViewHolder(view);
      view.setTag(holder);
    } else {
      holder = (GroupListSpinnerAdapter.ViewHolder) view.getTag();
    }

    String filterString = getContext().getResources()
        .getString(R.string.filter, items.get(position));

    holder.name.setText(filterString);
    return view;
  }
}
