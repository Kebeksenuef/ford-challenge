package com.bcsg.mytestapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.bcsg.mytestapplication.BuildConfig;
import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.sdl.fragments.ConseFragment;
import com.bcsg.mytestapplication.sdl.fragments.HomeFragment;
import com.bcsg.mytestapplication.sdl.fragments.LogoutFragment;
import com.bcsg.mytestapplication.sdl.fragments.SettingFragment;
import com.bcsg.mytestapplication.sdl.sdl.SdlReceiver;
import com.bcsg.mytestapplication.sdl.sdl.SdlService;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";
    private Long mBackPressed;
    private static final int TIME_INTERNAL = 2000;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    public static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        active = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        //If we are connected to a module we want to start our SdlService
        if(BuildConfig.TRANSPORT.equals("MULTI") || BuildConfig.TRANSPORT.equals("MULTI_HB")) {
            SdlReceiver.queryForConnectedService(this);
            Log.i(TAG,"Conectando por MULTI_HB");
        }else if(BuildConfig.TRANSPORT.equals("TCP")) {
            Intent proxyIntent = new Intent(this, SdlService.class);
            startService(proxyIntent);
        }

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, fragment, "Home");
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if(id == R.id.home){

            Intent intent = new Intent(getApplicationContext(), CheckActivity.class);
            startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            HomeFragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
            fragmentTransaction.commit();

        }else if(id == R.id.consecionárias){
            ConseFragment fragment = new ConseFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Consecionárias");
            fragmentTransaction.commit();
        }else if (id == R.id.setting){
            SettingFragment fragment = new SettingFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Configurações");
            fragmentTransaction.commit();
        }else if (id == R.id.logout){
            LogoutFragment fragment = new LogoutFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Logout");
            fragmentTransaction.commit();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed(){
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void OnStart(){
        super.onStart();
        active = true;
    }

    public void OnStop(){
        super.onStop();
        active = false;
    }

    public void onResume(){
        super.onResume();
    }

}
