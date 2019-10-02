package com.bcsg.mytestapplication.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.dao.TarefaConsultarItens;
import com.bcsg.mytestapplication.sdl.fragments.ConseFragment;
import com.bcsg.mytestapplication.sdl.fragments.HomeFragment;
import com.bcsg.mytestapplication.sdl.fragments.LogoutFragment;
import com.bcsg.mytestapplication.sdl.fragments.SettingFragment;
import com.google.android.material.navigation.NavigationView;

public class CheckActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "CheckFragment";
    public static boolean active = false;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    public void OnStart(){
        super.onStart();
        active = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        active = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();
        RecyclerView recyclerView = findViewById(R.id.recycle_view_itens);

        TarefaConsultarItens tarefaConsultarItens = new TarefaConsultarItens(context, recyclerView);
        tarefaConsultarItens.execute();

        mDrawerLayout = findViewById(R.id.drawerLayoutCheck);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.home){

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    public void onResume(){
        super.onResume();
    }

    public void OnStop(){
        super.onStop();
        active = false;
    }
}
