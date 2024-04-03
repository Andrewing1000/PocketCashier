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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pocketcashier.model.DBManager;
import com.example.pocketcashier.model.Product;
import com.example.pocketcashier.utilitaries.MenuTitle;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Context context;

    private POSFragment posFragment;
    private InventoryFragment inventoryFragment;
    private PurchasesFragment purchaseFragment;
    private SalesFragment salesFragment;
    private ClientsFragment clientsFragment;
    private DBManager dataBase;
    private ArrayList<Product> inventory;
    private static String PRODUCT_IMAGES_PATH = "porduct_images";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inventory = new ArrayList<>();

        dataBase = new DBManager(this);
        innit();

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


        posFragment = new POSFragment();
        salesFragment = new SalesFragment();
        inventoryFragment = new InventoryFragment(inventory);
        purchaseFragment = new PurchasesFragment();
        clientsFragment = new ClientsFragment();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, inventoryFragment).commit();
            navigationView.setCheckedItem(R.id.nav_inventory);
        }
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

    void innit(){
        inventory.addAll(dataBase.getAllProducts());
    }

    public void startAddProduct(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddProductFragment()).commit();
    }

    public Product addProduct(Product newProduct, Uri imagePath){

        Bitmap bitmap = null;
        if(imagePath != null){
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(imagePath);
                if (inputStream != null) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                }
            } catch (FileNotFoundException e) {
                bitmap = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(bitmap == null){
            Toast.makeText(this, "No se encontró la imagen", Toast.LENGTH_SHORT).show();
            newProduct.setImagePath(null);
        }
        else{
            String savedPath = dataBase.getNextProductId()+"";
            this.saveImageToInternalStorage(bitmap, savedPath);
            newProduct.setImagePath(savedPath);
        }

        try {
            dataBase.addProduct(newProduct);
            newProduct.setId(dataBase.getNextProductId()-1);
            inventoryFragment.addProduct(newProduct);
            return newProduct;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteProduct(Product product){
        try {
            dataBase.deleteProduct(product);
            inventoryFragment.removeProduct(product);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public void cancelAddProduct(){
        switchToInventory();
    }

    public void startEditProduct(Product product){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditProductFragment(product)).commit();
    }

    public void cancelEditProduct(){
        switchToInventory();
    }

    public Product editProduct(Product currentProduct, Product changedProduct, Uri imagePath){

        Bitmap bitmap = null;
        if(imagePath != null){
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(imagePath);
                if (inputStream != null) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                }
            } catch (FileNotFoundException e) {
                bitmap = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(bitmap == null){
            Toast.makeText(this, "No se encontró la imagen", Toast.LENGTH_SHORT).show();
            changedProduct.setImagePath(null);
        }
        else{
            String savedPath = dataBase.getNextProductId()+"";
            this.saveImageToInternalStorage(bitmap, savedPath);
            changedProduct.setImagePath(savedPath);
        }

        try {
            dataBase.updateProduct(changedProduct);
            inventoryFragment.updateProduct(currentProduct, changedProduct);
            return changedProduct;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
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
    }

    public void switchToClients(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, clientsFragment).commit();
    }

    public void switchToPurchases(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, purchaseFragment).commit();
    }


    public void updateInventory(){

    }

    public void saveImageToInternalStorage(Bitmap bitmapImage, String filename) {
        FileOutputStream fos = null;
        try {
            File directory = new File(context.getFilesDir(), PRODUCT_IMAGES_PATH);
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


    public Bitmap loadImageFromInternalStorage(String filename) {
        try {
            File directory = new File(context.getFilesDir(), PRODUCT_IMAGES_PATH);
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



}