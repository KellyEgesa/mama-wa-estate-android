package com.mamawaestate.android;

import android.app.Activity;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.alexzh.circleimageview.CircleImageView;
import com.mamawaestate.android.Fragments.HomeFragment;


public class SlideNavigation {
    int fragmnetholder;
    TextView user_name, home,  cart, logout;
    CircleImageView user_img;


    public SlideNavigation(int fragmnetholder) {
        this.fragmnetholder = fragmnetholder;
    }


    public void initSlideMenu(final Activity activity, final FragmentManager fragmentManager, final DrawerLayout drawerLayout) {

        user_img = activity.findViewById(R.id.user_img);
        home = activity.findViewById(R.id.menu_home);

        cart = activity.findViewById(R.id.menu_cart);
        logout = activity.findViewById(R.id.menu_logout);
//        user_name = activity.findViewById(R.id.user_name);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new HomeFragment(), "HomeFragment")
                        .commit();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
