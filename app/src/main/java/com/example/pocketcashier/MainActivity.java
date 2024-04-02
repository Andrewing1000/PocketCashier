package com.example.pocketcashier;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pocketcashier.model.Product;
import com.example.pocketcashier.utilitaries.MenuTitle;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Context context;

    private POSFragment posFragment;
    private CategoriesFragment categoriesFragment;
    private InventoryFragment inventoryFragment;
    private PurchasesFragment purchaseFragment;
    private SalesFragment salesFragment;
    private ClientsFragment clientsFragment;

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

        MenuItem saleNav = menu.findItem(R.id.nav_pos);
        saleNav.getIcon().setAlpha(150);

        MenuItem histNav = menu.findItem(R.id.nav_history);
        histNav.getIcon().setAlpha(255);

        MenuItem invNav = menu.findItem(R.id.nav_inventory);
        invNav.getIcon().setAlpha(255);

        MenuItem purchaseNav = menu.findItem(R.id.nav_purchases);
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

        posFragment = new POSFragment();
        salesFragment = new SalesFragment();
        inventoryFragment = new InventoryFragment();
        categoriesFragment = new CategoriesFragment();
        purchaseFragment = new PurchasesFragment();
        clientsFragment = new ClientsFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemSelectedId = item.getItemId();

        if(itemSelectedId == R.id.nav_pos){
            switchToPOS();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(itemSelectedId == R.id.nav_history){
            switchToHistory();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(itemSelectedId == R.id.nav_inventory){
            switchToInventory();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(itemSelectedId == R.id.nav_purchases){
            switchToPurchases();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(itemSelectedId == R.id.nav_clients){
            switchToClients();
            drawerLayout.closeDrawer(GravityCompat.START);
        }


        return true;
    }

    public void startAddProduct(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddProductFragment()).commit();
    }

    public void addProduct(Product newProduct){

    }

    public void deleteProduct(Product product){

    }

    public void cancelAddProduct(){
        switchToInventory();
    }

    public void startEditProduct(Product product){

    }

    public void cancelEditProduct(){

    }

    public boolean editProduct(Product currentProduct, Product changedProduct){

        return false;
    }

    public void startAddCategory(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddCategoryFragment()).commit();

    }

    public void addCategory(){

    }

    public void startSale(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConfirmSale()).commit();
    }

    public void cancelSale(){

    }

    public boolean makeSale(){
        return false;
    }

    public void startPurchase(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewPurchaseFragment()).commit();
    }

    public void cancelPurchase(){

    }

    public boolean makePurchase(){
        return false;
    }

    public void switchToPOS(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, posFragment).commit();
    }

    public void switchToHistory(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, salesFragment).commit();
    }

    public void switchToInventory(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, inventoryFragment).commit();
    }

    public void switchToCategories(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, categoriesFragment).commit();
    }

    public void switchToClients(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, clientsFragment).commit();
    }

    public void switchToPurchases(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, purchaseFragment).commit();
    }


    private void saveImageToInternalStorage(Bitmap bitmapImage, String filename) {
        FileOutputStream fos = null;
        try {
            File directory = new File(context.getFilesDir(), "images");
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory, filename);
            fos = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private Bitmap loadImageFromInternalStorage(String filename) {
        try {
            File directory = new File(context.getFilesDir(), "images");
            File file = new File(directory, filename);
            FileInputStream fis = new FileInputStream(file);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean validString(String x){
        if(x.equals("") || x == null){
            return false;
        }
        return true;
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the URI of the selected file
            Uri uri = data.getData();

            // Now you can save the image file locally in your app using the URI
            // For example, you can use ContentResolver to open an InputStream and copy the file
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                // Now you can save the inputStream to a file in your app's local storage
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}