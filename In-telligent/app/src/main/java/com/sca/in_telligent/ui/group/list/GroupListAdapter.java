package com.sca.in_telligent.ui.group.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import com.bumptech.glide.Glide;
import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.ui.base.BaseViewHolder;
import com.sca.in_telligent.ui.group.list.GroupListAdapter;
import com.sca.in_telligent.util.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int GRAY_HEADER = 3;
    public static final int VIEW_TYPE_EMPTY = 4;
    public static final int VIEW_TYPE_NORMAL = 2;
    public static final int VIEW_TYPE_SUGGESTED_HEADER = 0;
    public static final int VIEW_TYPE_SUGGESTED_ITEM = 1;
    private List<Building> buildings;
    private Context context;
    private boolean expanded = false;
    private Callback mCallback;
    private List<Building> suggestedBuildings;

    public interface Callback {
        void onAboutClicked(int i, boolean z);

        void onConnectClicked(String str, boolean z, boolean z2);

        void onContactClicked(int i);

        void onIgnoreClicked(String str, int i);
    }

    public class EmptyViewHolder_ViewBinding implements Unbinder {
        private EmptyViewHolder target;

        public EmptyViewHolder_ViewBinding(EmptyViewHolder emptyViewHolder, View view) {
            this.target = emptyViewHolder;
            emptyViewHolder.messageTextView = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.group_list_no_message_text, "field 'messageTextView'", TextView.class);
        }

        public void unbind() {
            EmptyViewHolder emptyViewHolder = this.target;
            if (emptyViewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            emptyViewHolder.messageTextView = null;
        }
    }

    public class GrayViewHolder_ViewBinding implements Unbinder {
        private GrayViewHolder target;

        public GrayViewHolder_ViewBinding(GrayViewHolder grayViewHolder, View view) {
            this.target = grayViewHolder;
            grayViewHolder.headerText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.gray_header_text, "field 'headerText'", TextView.class);
        }

        public void unbind() {
            GrayViewHolder grayViewHolder = this.target;
            if (grayViewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            grayViewHolder.headerText = null;
        }
    }

    public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.groupName = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.group_name, "field 'groupName'", TextView.class);
            viewHolder.itemAboutText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.group_item_about_text, "field 'itemAboutText'", TextView.class);
            viewHolder.groupImage = (ImageView) Utils.findRequiredViewAsType(view, (int) R.id.group_normal_item_image, "field 'groupImage'", ImageView.class);
            viewHolder.itemConnectText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.group_item_connect_text, "field 'itemConnectText'", TextView.class);
        }

        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            viewHolder.groupName = null;
            viewHolder.itemAboutText = null;
            viewHolder.groupImage = null;
            viewHolder.itemConnectText = null;
        }
    }

    public class SuggestedItemViewHolder_ViewBinding implements Unbinder {
        private SuggestedItemViewHolder target;

        public SuggestedItemViewHolder_ViewBinding(SuggestedItemViewHolder suggestedItemViewHolder, View view) {
            this.target = suggestedItemViewHolder;
            suggestedItemViewHolder.groupName = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.group_name, "field 'groupName'", TextView.class);
            suggestedItemViewHolder.suggestedImage = (ImageView) Utils.findRequiredViewAsType(view, (int) R.id.suggested_image, "field 'suggestedImage'", ImageView.class);
            suggestedItemViewHolder.suggestedSubscribeText = (RelativeLayout) Utils.findRequiredViewAsType(view, (int) R.id.group_subscribe_text_layout, "field 'suggestedSubscribeText'", RelativeLayout.class);
            suggestedItemViewHolder.suggestedIgnoreText = (RelativeLayout) Utils.findRequiredViewAsType(view, (int) R.id.group_ignore_text_layout, "field 'suggestedIgnoreText'", RelativeLayout.class);
        }

        public void unbind() {
            SuggestedItemViewHolder suggestedItemViewHolder = this.target;
            if (suggestedItemViewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            suggestedItemViewHolder.groupName = null;
            suggestedItemViewHolder.suggestedImage = null;
            suggestedItemViewHolder.suggestedSubscribeText = null;
            suggestedItemViewHolder.suggestedIgnoreText = null;
        }
    }

    public class SuggestedHeaderViewHolder_ViewBinding implements Unbinder {
        private SuggestedHeaderViewHolder target;
        private View view7f0802be;

        public SuggestedHeaderViewHolder_ViewBinding(final SuggestedHeaderViewHolder suggestedHeaderViewHolder, View view) {
            this.target = suggestedHeaderViewHolder;
            suggestedHeaderViewHolder.headerText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.header_text, "field 'headerText'", TextView.class);
            suggestedHeaderViewHolder.suggestedItemArrow = (ImageView) Utils.findRequiredViewAsType(view, (int) R.id.suggested_item_arrow, "field 'suggestedItemArrow'", ImageView.class);
            View findRequiredView = Utils.findRequiredView(view, (int) R.id.suggested_groups_header_container, "method 'onCollapseExpandSuggestedGroups'");
            this.view7f0802be = findRequiredView;
            findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.group.list.GroupListAdapter.SuggestedHeaderViewHolder_ViewBinding.1
                public void doClick(View view2) {
                    suggestedHeaderViewHolder.onCollapseExpandSuggestedGroups();
                }
            });
        }

        public void unbind() {
            SuggestedHeaderViewHolder suggestedHeaderViewHolder = this.target;
            if (suggestedHeaderViewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            suggestedHeaderViewHolder.headerText = null;
            suggestedHeaderViewHolder.suggestedItemArrow = null;
            this.view7f0802be.setOnClickListener(null);
            this.view7f0802be = null;
        }
    }

    public List<Building> getBuildings() {
        return this.buildings;
    }

    public GroupListAdapter(List<Building> list, List<Building> list2, Context context) {
        this.buildings = list;
        this.context = context;
        this.suggestedBuildings = list2;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void addItems(List<Building> list, List<Building> list2) {
        this.buildings.clear();
        this.buildings.addAll(list);
        this.suggestedBuildings.clear();
        this.suggestedBuildings.addAll(list2);
        notifyDataSetChanged();
        expand();
    }

    public void updateItems(List<Building> listObjects) {
        Observable.fromIterable(buildings).filter(
                        building -> building.getType() != Building.Type.NORMAL && building.getType() != Building.Type.GRAY_HEADER)
                .toList().subscribe(buildingFiltered -> buildings = buildingFiltered);

        Observable.fromIterable(listObjects).filter(
                        building -> building.getType() == Building.Type.NORMAL || building.getType() == Building.Type.GRAY_HEADER)
                .toList().subscribe(listObjectsFiltered -> buildings.addAll(listObjectsFiltered));
//    buildings.addAll(listObjects);
        notifyDataSetChanged();
    }


    public void updateSuggestedItems(List<Building> listObjects) {
        suggestedBuildings.clear();
        suggestedBuildings.addAll(listObjects);

        Observable.fromIterable(buildings).filter(building -> building.getType() != Building.Type.SUGGESTED_ITEM)
                .toList().subscribe(buildingFiltered -> buildings = buildingFiltered);

        for (int i = 0; i < suggestedBuildings.size(); i++) {
            buildings.add(i + 1, suggestedBuildings.get(i));
        }
        notifyDataSetChanged();
    }




    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
            default:
                return new GroupListAdapter.ViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.group_normal_list_item, parent, false));
            case VIEW_TYPE_SUGGESTED_HEADER:
                return new GroupListAdapter.SuggestedHeaderViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.group_suggested_header_item, parent, false));
            case VIEW_TYPE_SUGGESTED_ITEM:
                return new GroupListAdapter.SuggestedItemViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.group_suggested_list_item, parent, false));
            case GRAY_HEADER:
                return new GroupListAdapter.GrayViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.group_gray_header_item, parent, false));
            case VIEW_TYPE_EMPTY:
                return new GroupListAdapter.EmptyViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.group_list_empty_view, parent, false));


        }
    }

    public void onBindViewHolder(BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    public int getItemCount() {
        List<Building> list = this.buildings;
        if (list == null || list.size() <= 1) {
            return 1;
        }
        return this.buildings.size();
    }

    public int getItemViewType(int i) {
        List<Building> list = this.buildings;
        if (list == null || list.size() <= 1) {
            return 4;
        }
        return this.buildings.get(i).getType().getNumber();
    }

    public Building getItem(int i) {
        return this.buildings.get(i);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.group_normal_item_image)
        ImageView groupImage;
        @BindView(R.id.group_name)
        TextView groupName;
        @BindView(R.id.group_item_about_text)
        TextView itemAboutText;
        @BindView(R.id.group_item_connect_text)
        TextView itemConnectText;

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        protected void clear() {
        }

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        public void onBind(int i) {
            super.onBind(i);
            final Building building = (Building) GroupListAdapter.this.buildings.get(i);
            this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.group.list.GroupListAdapter$ViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ViewHolder.this.m206x5d023e57(building, view);
                }
            });
            this.itemAboutText.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.group.list.GroupListAdapter$ViewHolder$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ViewHolder.this.m207xdb634236(building, view);
                }
            });
            this.itemConnectText.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.group.list.GroupListAdapter$ViewHolder$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ViewHolder.this.m208x59c44615(building, view);
                }
            });
            this.groupName.setText(((Building) GroupListAdapter.this.buildings.get(i)).getName());
            if (building.getImageUrl() != null) {
                Glide.with(this.itemView).load(building.getImageUrl()).thumbnail(0.1f).into(this.groupImage);
            } else {
                Glide.with(this.itemView).load(Integer.valueOf(CommonUtils.getDefaultImage(building.getId()))).thumbnail(0.1f).into(this.groupImage);
            }
            if (building.getSubscriberId() != null) {
                this.itemConnectText.setText(GroupListAdapter.this.context.getResources().getString(R.string.contact));
            } else if (building.isOther()) {
                this.itemConnectText.setText(GroupListAdapter.this.context.getResources().getString(R.string.connect));
            } else {
                this.itemConnectText.setText(GroupListAdapter.this.context.getResources().getString(R.string.disconnect));
            }
        }

        /* renamed from: lambda$onBind$0$com-sca-in_telligent-ui-group-list-GroupListAdapter$ViewHolder  reason: not valid java name */
        public /* synthetic */ void m206x5d023e57(Building building, View view) {
            onAboutClick(building);
        }

        /* renamed from: lambda$onBind$1$com-sca-in_telligent-ui-group-list-GroupListAdapter$ViewHolder  reason: not valid java name */
        public /* synthetic */ void m207xdb634236(Building building, View view) {
            onAboutClick(building);
        }

        /* renamed from: lambda$onBind$2$com-sca-in_telligent-ui-group-list-GroupListAdapter$ViewHolder  reason: not valid java name */
        public /* synthetic */ void m208x59c44615(Building building, View view) {
            if (building.getSubscriberId() != null) {
                GroupListAdapter.this.mCallback.onContactClicked(getAdapterPosition());
            } else if (building.isOther()) {
                if (building.getPassword() != null) {
                    GroupListAdapter.this.showPasswordDialog(building);
                } else {
                    GroupListAdapter.this.mCallback.onConnectClicked(Integer.toString(building.getId()), true, false);
                }
            } else {
                GroupListAdapter.this.mCallback.onConnectClicked(Integer.toString(building.getId()), false, false);
            }
        }

        private void onAboutClick(Building building) {
            int adapterPosition;
            if (GroupListAdapter.this.expanded) {
                adapterPosition = getAdapterPosition() - GroupListAdapter.this.suggestedBuildings.size();
            } else {
                adapterPosition = getAdapterPosition();
            }
            GroupListAdapter.this.mCallback.onAboutClicked(adapterPosition, building.getSubscriberId() != null);
        }
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class SuggestedHeaderViewHolder extends BaseViewHolder {
        @BindView(R.id.header_text)
        TextView headerText;
        @BindView(R.id.suggested_item_arrow)
        ImageView suggestedItemArrow;

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        protected void clear() {
        }

        public SuggestedHeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.suggested_groups_header_container})
        void onCollapseExpandSuggestedGroups() {
            if (GroupListAdapter.this.expanded) {
                GroupListAdapter.this.collapse();
                this.suggestedItemArrow.setImageResource(R.drawable.icon_down_carrot);
                return;
            }
            GroupListAdapter.this.expand();
            this.suggestedItemArrow.setImageResource(R.drawable.icon_up_carrot);
        }
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class SuggestedItemViewHolder extends BaseViewHolder {
        @BindView(R.id.group_name)
        TextView groupName;
        @BindView(R.id.group_ignore_text_layout)
        RelativeLayout suggestedIgnoreText;
        @BindView(R.id.suggested_image)
        ImageView suggestedImage;
        @BindView(R.id.group_subscribe_text_layout)
        RelativeLayout suggestedSubscribeText;

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        protected void clear() {
        }

        public SuggestedItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        public void onBind(final int i) {
            super.onBind(i);
            final Building building = (Building) GroupListAdapter.this.buildings.get(i);
            this.suggestedSubscribeText.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.group.list.GroupListAdapter$SuggestedItemViewHolder$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SuggestedItemViewHolder.this.m204x93e60241(building, view);
                }
            });
            this.suggestedIgnoreText.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.group.list.GroupListAdapter$SuggestedItemViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SuggestedItemViewHolder.this.m205x94b480c2(i, building, view);
                }
            });
            this.groupName.setText(building.getName());
            if (building.getImageUrl() != null && !building.getImageUrl().equals("")) {
                Picasso.get().load(building.getImageUrl()).into(this.suggestedImage);
            } else {
                this.suggestedImage.setImageResource(CommonUtils.getDefaultImage(building.getId()));
            }
        }

        /* renamed from: lambda$onBind$0$com-sca-in_telligent-ui-group-list-GroupListAdapter$SuggestedItemViewHolder  reason: not valid java name */
        public /* synthetic */ void m204x93e60241(Building building, View view) {
            if (building.getPassword() != null) {
                GroupListAdapter groupListAdapter = GroupListAdapter.this;
                groupListAdapter.showPasswordDialog((Building) groupListAdapter.buildings.get(getAdapterPosition()));
                return;
            }
            GroupListAdapter.this.mCallback.onConnectClicked(Integer.toString(building.getId()), true, true);
        }

        /* renamed from: lambda$onBind$1$com-sca-in_telligent-ui-group-list-GroupListAdapter$SuggestedItemViewHolder  reason: not valid java name */
        public /* synthetic */ void m205x94b480c2(int i, Building building, View view) {
            int i2 = i - 1;
            Callback callback = GroupListAdapter.this.mCallback;
            String str = building.getId() + "";
            if (i2 < 0) {
                i2 = 0;
            }
            callback.onIgnoreClicked(str, i2);
        }
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class GrayViewHolder extends BaseViewHolder {
        @BindView(R.id.gray_header_text)
        TextView headerText;

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        protected void clear() {
        }

        public GrayViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        public void onBind(int i) {
            super.onBind(i);
            this.headerText.setText(((Building) GroupListAdapter.this.buildings.get(i)).getName());
        }
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class EmptyViewHolder extends BaseViewHolder {
        @BindView(R.id.group_list_no_message_text)
        TextView messageTextView;

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        protected void clear() {
        }

        public EmptyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void expand() {
        int i = 0;
        while (i < this.suggestedBuildings.size()) {
            int i2 = i + 1;
            this.buildings.add(i2, this.suggestedBuildings.get(i));
            notifyItemInserted(i2);
            i = i2;
        }
        this.expanded = !this.expanded;
    }

    public void collapse() {
        for (int i = 0; i < this.suggestedBuildings.size(); i++) {
            this.buildings.remove(1);
            notifyItemRemoved(1);
        }
        this.expanded = !this.expanded;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPasswordDialog(final Building building) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle(R.string.password);
        builder.setMessage(this.context.getResources().getString(R.string.enter_password));
        final EditText editText = new EditText(this.context);
        editText.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        builder.setView(editText);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.group.list.GroupListAdapter$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GroupListAdapter.this.m199x34dc9ec8(editText, building, dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.group.list.GroupListAdapter$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    /* renamed from: lambda$showPasswordDialog$6$com-sca-in_telligent-ui-group-list-GroupListAdapter  reason: not valid java name */
    public /* synthetic */ void m199x34dc9ec8(EditText editText, Building building, DialogInterface dialogInterface, int i) {
        if (editText.getText().toString().equals(building.getPassword())) {
            this.mCallback.onConnectClicked(Integer.toString(building.getId()), true, false);
            return;
        }
        Context context = this.context;
        Toast.makeText(context, context.getResources().getString(R.string.invalid_password), Toast.LENGTH_LONG).show();
    }
}
