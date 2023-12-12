package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityMainBinding implements ViewBinding {
    public final ImageView adFooterImage;
    public final BottomNavigationView bottomNavigation;
    public final FrameLayout contentFrame;
    public final DrawerLayout drawerLayout;
    public final RecyclerView navigationViewListview;
    private final DrawerLayout rootView;
    public final RelativeLayout totalSilenceBottomLayout;
    public final TextView totalSilenceLabel;
    public final TextView totalSilenceNumber;
    public final TextView totalSilenceOff;
    public final TextView totalSilenceOn;
    public final TextView versionNumber;

    private ActivityMainBinding(DrawerLayout drawerLayout, ImageView imageView, BottomNavigationView bottomNavigationView, FrameLayout frameLayout, DrawerLayout drawerLayout2, RecyclerView recyclerView, RelativeLayout relativeLayout, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5) {
        this.rootView = drawerLayout;
        this.adFooterImage = imageView;
        this.bottomNavigation = bottomNavigationView;
        this.contentFrame = frameLayout;
        this.drawerLayout = drawerLayout2;
        this.navigationViewListview = recyclerView;
        this.totalSilenceBottomLayout = relativeLayout;
        this.totalSilenceLabel = textView;
        this.totalSilenceNumber = textView2;
        this.totalSilenceOff = textView3;
        this.totalSilenceOn = textView4;
        this.versionNumber = textView5;
    }

    public DrawerLayout getRoot() {
        return this.rootView;
    }

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_main, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivityMainBinding bind(View view) {
        int i = R.id.ad_footer_image;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.ad_footer_image);
        if (imageView != null) {
            i = R.id.bottom_navigation;
            BottomNavigationView bottomNavigationView = (BottomNavigationView) ViewBindings.findChildViewById(view, (int) R.id.bottom_navigation);
            if (bottomNavigationView != null) {
                i = R.id.content_frame;
                FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, (int) R.id.content_frame);
                if (frameLayout != null) {
                    DrawerLayout drawerLayout = (DrawerLayout) view;
                    i = R.id.navigation_view_listview;
                    RecyclerView findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.navigation_view_listview);
                    if (findChildViewById != null) {
                        i = R.id.total_silence_bottom_layout;
                        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.total_silence_bottom_layout);
                        if (relativeLayout != null) {
                            i = R.id.total_silence_label;
                            TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.total_silence_label);
                            if (textView != null) {
                                i = R.id.total_silence_number;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.total_silence_number);
                                if (textView2 != null) {
                                    i = R.id.total_silence_off;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.total_silence_off);
                                    if (textView3 != null) {
                                        i = R.id.total_silence_on;
                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.total_silence_on);
                                        if (textView4 != null) {
                                            i = R.id.version_number;
                                            TextView textView5 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.version_number);
                                            if (textView5 != null) {
                                                return new ActivityMainBinding(drawerLayout, imageView, bottomNavigationView, frameLayout, drawerLayout, findChildViewById, relativeLayout, textView, textView2, textView3, textView4, textView5);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
