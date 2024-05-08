package com.sca.in_telligent.ui.group.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.annotations.Nullable;

public class GroupDetailContainerFragment extends BaseFragment {
    private static final String ARG_KEY_GROUPS = "ARG_KEY_GROUPS";
    private static final String ARG_KEY_POSITION = "ARG_KEY_POSITION";
    private static final String ARG_KEY_SUBSCRIBER = "ARG_KEY_SUBSCRIBER";
    public static final String TAG = "GroupDetailContainerFragment";
    GroupDetailPagerAdapter adapter;
    private List<Building> groups;
    private int position;
    private Subscriber subscriber;
    @BindView(R.id.viewpager_container)
    ViewPager viewPagerContainer;

    public static GroupDetailContainerFragment newInstance(Subscriber subscriber, ArrayList<Building> arrayList, int i) {
        GroupDetailContainerFragment groupDetailContainerFragment = new GroupDetailContainerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_KEY_SUBSCRIBER, subscriber);
        bundle.putSerializable(ARG_KEY_GROUPS, arrayList);
        bundle.putInt(ARG_KEY_POSITION, i);
        groupDetailContainerFragment.setArguments(bundle);
        return groupDetailContainerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groups = ((List<Building>) getArguments().getSerializable(ARG_KEY_GROUPS))
                .stream()
                .filter(building -> building.getType() == Building.Type.NORMAL)
                .collect(Collectors.toList());

        subscriber = (Subscriber) getArguments().getSerializable(ARG_KEY_SUBSCRIBER);
        position = getArguments().getInt(ARG_KEY_POSITION);

        adjustPosition();
    }

    private void adjustPosition() {
        int i = this.position;
        if (i - 1 > -1) {
            this.position = i - 1;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_group_detail_container, viewGroup, false);
        setUnBinder(ButterKnife.bind(this, inflate));
        viewPagerContainer = inflate.findViewById(R.id.viewpager_container);

        return inflate;
    }

    @Override
    protected void setUp(View view) {
        GroupDetailPagerAdapter groupDetailPagerAdapter = new GroupDetailPagerAdapter(this.groups, this.subscriber, getChildFragmentManager());
        this.adapter = groupDetailPagerAdapter;
        this.viewPagerContainer.setAdapter(groupDetailPagerAdapter);
        this.viewPagerContainer.setCurrentItem(this.position);
    }

    public void goToPosition(int position) {
        this.viewPagerContainer.setCurrentItem(position);
    }

    public void goLeft() {
        if (this.viewPagerContainer.getCurrentItem() > 0) {
            ViewPager viewPager = this.viewPagerContainer;
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public void goRight() {
        if (this.viewPagerContainer.getCurrentItem() < this.adapter.getCount()) {
            ViewPager viewPager = this.viewPagerContainer;
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public static class GroupDetailPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Building> groups;
        private final Subscriber subscriber;

        public GroupDetailPagerAdapter(List<Building> list, Subscriber subscriber, FragmentManager fragmentManager) {
            super(fragmentManager);
            this.groups = list;
            this.subscriber = subscriber;
        }

        private boolean isPersonalCommunity(Building building) {
            boolean isPersonal =
                    building.getSubscriberId() != null &&
                            (building.getSubscriberId().intValue() == subscriber.getId());
            return isPersonal;
        }

        public Fragment getItem(int position) {
            return GroupDetailFragment.newInstance(this.groups.get(position), position, this.groups.size());
        }

        public int getCount() {
            return this.groups.size();
        }
    }
}
