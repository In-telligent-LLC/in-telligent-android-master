package com.sca.in_telligent.ui.contact.message;


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

public class ContactMessageSpinnerAdapter extends ArrayAdapter<String> {

  private List<String> items;
  private Context context;

  @Override
  public View getDropDownView(int position, View view,
      ViewGroup parent) {
    ContactMessageSpinnerAdapter.ViewHolder holder;
    if (view == null) {
      view = LayoutInflater.from(context).inflate(R.layout.spinner_list_item, parent, false);
      holder = new ContactMessageSpinnerAdapter.ViewHolder(view);
      view.setTag(holder);
    } else {
      holder = (ContactMessageSpinnerAdapter.ViewHolder) view.getTag();
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

  public ContactMessageSpinnerAdapter(Context context,
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
  public int getCount() {
    int count = super.getCount();
    return count > 0 ? count - 1 : count;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {

    ContactMessageSpinnerAdapter.ViewHolder holder;
    if (view == null) {
      view = LayoutInflater.from(context).inflate(R.layout.spinner_dropdown_item, parent, false);
      holder = new ContactMessageSpinnerAdapter.ViewHolder(view);
      view.setTag(holder);
    } else {
      holder = (ContactMessageSpinnerAdapter.ViewHolder) view.getTag();
    }

    holder.name.setText(items.get(position));
    return view;
  }
}
