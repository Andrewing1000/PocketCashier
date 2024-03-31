package com.example.pocketcashier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pocketcashier.ui.MenuTitle;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ColorFilter iconFilter = new PorterDuffColorFilter(ContextCompat.getColor(context, R.color.button_orange), PorterDuff.Mode.SRC_IN);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();

        MenuItem posTitle = menu.findItem(R.id.POS_title);
        posTitle.setActionView(new MenuTitle(context, "Punto de Venta", null));

        MenuItem saleNav = menu.findItem(R.id.nav_sales);
        saleNav.getIcon().setAlpha(150);

        MenuItem histNav = menu.findItem(R.id.nav_history);
        histNav.getIcon().setAlpha(255);

        MenuItem invNav = menu.findItem(R.id.nav_inventory);
        invNav.getIcon().setAlpha(255);

        MenuItem purchaseNav = menu.findItem(R.id.nav_purchase);
        purchaseNav.getIcon().setAlpha(255);

        MenuItem clientNav= menu.findItem(R.id.nav_clients);
        clientNav.getIcon().setAlpha(255);

        MenuItem invTitle = menu.findItem(R.id.Inv_title);
        invTitle.setActionView(new MenuTitle(context, "Contabilidad", null));


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InventoryFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_inventory);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemSelectedId = item.getItemId();

        if(itemSelectedId == R.id.nav_sales){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SalesFragment()).commit();
            //onBackPressed();
        }
        else if(itemSelectedId == R.id.nav_inventory){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InventoryFragment()).commit();
            //onBackPressed();
        }
        else if(itemSelectedId == R.id.nav_purchase){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PurchaseFragment()).commit();
            //drawerLayout.closeDrawer();
        }


        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}