package com.sca.in_telligent.ui.main;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class MainActivity_ViewBinding implements Unbinder {
    private MainActivity target;
    private View view7f0802a1;
    private View view7f0802fe;
    private View view7f0802ff;

    public MainActivity_ViewBinding(MainActivity mainActivity) {
        this(mainActivity, mainActivity.getWindow().getDecorView());
    }

    public MainActivity_ViewBinding(final MainActivity mainActivity, View view) {
        this.target = mainActivity;
        mainActivity.navigationViewListView = (RecyclerView) Utils.findRequiredViewAsType(view, (int) R.id.navigation_view_listview, "field 'navigationViewListView'", RecyclerView.class);
        mainActivity.drawerLayout = (DrawerLayout) Utils.findRequiredViewAsType(view, (int) R.id.drawer_layout, "field 'drawerLayout'", DrawerLayout.class);
        View findRequiredView = Utils.findRequiredView(view, (int) R.id.sos_button, "field 'buttonSos' and method 'sosClick'");
        mainActivity.buttonSos = (ImageView) Utils.castView(findRequiredView, (int) R.id.sos_button, "field 'buttonSos'", ImageView.class);
        this.view7f0802a1 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.main.MainActivity_ViewBinding.1
            public void doClick(View view2) {
                mainActivity.sosClick(view2);
            }
        });
        mainActivity.frameLayout = (FrameLayout) Utils.findRequiredViewAsType(view, (int) R.id.content_frame, "field 'frameLayout'", FrameLayout.class);
        mainActivity.bottomNavigationView = (BottomNavigationView) Utils.findRequiredViewAsType(view, (int) R.id.bottom_navigation, "field 'bottomNavigationView'", BottomNavigationView.class);
        mainActivity.totalSilenceNumber = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.total_silence_number, "field 'totalSilenceNumber'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, (int) R.id.total_silence_off, "field 'totalSilenceOff' and method 'silenceOffClick'");
        mainActivity.totalSilenceOff = (TextView) Utils.castView(findRequiredView2, (int) R.id.total_silence_off, "field 'totalSilenceOff'", TextView.class);
        this.view7f0802fe = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.main.MainActivity_ViewBinding.2
            public void doClick(View view2) {
                mainActivity.silenceOffClick(view2);
            }
        });
        mainActivity.version_name = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.version_number, "field 'version_name'", TextView.class);
        View findRequiredView3 = Utils.findRequiredView(view, (int) R.id.total_silence_on, "field 'totalSilenceOn' and method 'silenceOnClick'");
        mainActivity.totalSilenceOn = (TextView) Utils.castView(findRequiredView3, (int) R.id.total_silence_on, "field 'totalSilenceOn'", TextView.class);
        this.view7f0802ff = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.main.MainActivity_ViewBinding.3
            public void doClick(View view2) {
                mainActivity.silenceOnClick(view2);
            }
        });
        mainActivity.adImageView = (ImageView) Utils.findRequiredViewAsType(view, (int) R.id.ad_footer_image, "field 'adImageView'", ImageView.class);
    }

    public void unbind() {
        MainActivity mainActivity = this.target;
        if (mainActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        mainActivity.navigationViewListView = null;
        mainActivity.drawerLayout = null;
        mainActivity.buttonSos = null;
        mainActivity.frameLayout = null;
        mainActivity.bottomNavigationView = null;
        mainActivity.totalSilenceNumber = null;
        mainActivity.totalSilenceOff = null;
        mainActivity.version_name = null;
        mainActivity.totalSilenceOn = null;
        mainActivity.adImageView = null;
        this.view7f0802a1.setOnClickListener(null);
        this.view7f0802a1 = null;
        this.view7f0802fe.setOnClickListener(null);
        this.view7f0802fe = null;
        this.view7f0802ff.setOnClickListener(null);
        this.view7f0802ff = null;
    }
}
