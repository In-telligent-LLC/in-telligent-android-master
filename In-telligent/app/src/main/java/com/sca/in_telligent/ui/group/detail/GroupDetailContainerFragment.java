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
import com.sca.in_telligent.ui.group.detail.created.CreatedGroupDetailFragment;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.annotations.Nullable;


public class GroupDetailContainerFragment extends BaseFragment {

    public static final String TAG = GroupDetailContainerFragment.class.getSimpleName();
    private static final String ARG_KEY_SUBSCRIBER = "ARG_KEY_SUBSCRIBER";
    private static final String ARG_KEY_GROUPS = "ARG_KEY_GROUPS";
    private static final String ARG_KEY_POSITION = "ARG_KEY_POSITION";


    private List<Building> groups;
    private Subscriber subscriber;
    private int position;

    GroupDetailPagerAdapter adapter;

    @BindView(R.id.viewpager_container)
    ViewPager viewPagerContainer;

    public GroupDetailContainerFragment() {

    }

    public static GroupDetailContainerFragment newInstance(Subscriber subscriber, ArrayList<Building> groups, int position) {
        GroupDetailContainerFragment fragment = new GroupDetailContainerFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable(ARG_KEY_SUBSCRIBER, subscriber);
        bundle.putSerializable(ARG_KEY_GROUPS, groups);
        bundle.putInt(ARG_KEY_POSITION, position);

        fragment.setArguments(bundle);

        return fragment;
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

    //Groups usually contain the header item which should be ignored
    private void adjustPosition() {
        if (position - 1 > -1) {
            position--;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_detail_container,
                container,
                false);
        setUnBinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    protected void setUp(View view) {
        adapter = new GroupDetailPagerAdapter(this.groups, this.subscriber, getChildFragmentManager());
        viewPagerContainer.setAdapter(adapter);
        viewPagerContainer.setCurrentItem(position);
    }

    public void goToPosition(int position) {
        viewPagerContainer.setCurrentItem(position);
    }

    public void goLeft() {
        if (viewPagerContainer.getCurrentItem() > 0) {
            viewPagerContainer.setCurrentItem(viewPagerContainer.getCurrentItem() - 1);
        }
    }

    public void goRight() {
        if (viewPagerContainer.getCurrentItem() < adapter.getCount()) {
            viewPagerContainer.setCurrentItem(viewPagerContainer.getCurrentItem() + 1);
        }
    }

    public static class GroupDetailPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Building> groups;
        private final Subscriber subscriber;

        public GroupDetailPagerAdapter(List<Building> groups, Subscriber subscriber, FragmentManager fm) {
            super(fm);
            this.groups = groups;
            this.subscriber = subscriber;
        }

        @Override
        public Fragment getItem(int position) {
            Building building = this.groups.get(position);
            if (isPersonalCommunity(building)) {
                return CreatedGroupDetailFragment.newInstance(building, position);
            } else {
                return GroupDetailFragment.newInstance(building, position);
            }
        }

        private boolean isPersonalCommunity(Building building) {
            boolean isPersonal =
                    building.getSubscriberId() != null &&
                            (building.getSubscriberId().intValue() == subscriber.getId());
            return isPersonal;
        }

        @Override
        public int getCount() {
            return groups.size();
        }


    }
}
