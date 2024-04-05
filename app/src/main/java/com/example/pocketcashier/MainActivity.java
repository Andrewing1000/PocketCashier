package com.example.pocketcashier;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
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

import com.example.pocketcashier.model.Client;
import com.example.pocketcashier.model.DBManager;
import com.example.pocketcashier.model.Product;
import com.example.pocketcashier.model.Sale;
import com.example.pocketcashier.utilitaries.MenuTitle;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

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
    private ArrayList<Client> clients;
    private ArrayList<Sale> sales;

    private LinkedHashMap<Product, Integer> cart;
    private static String PRODUCT_IMAGES_PATH = "product_images";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inventory = new ArrayList<>();
        sales = new ArrayList<>();
        clients = new ArrayList<>();

        cart = new LinkedHashMap<>();
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

        MenuItem clientNav= menu.findItem(R.id.nav_clients);
        clientNav.getIcon().setAlpha(255);

        MenuItem invTitle = menu.findItem(R.id.Inv_title);
        invTitle.setActionView(new MenuTitle(context, "Contabilidad", null));


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);


        //posFragment.setAdapter(inventoryFragment.getAdapter());
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, inventoryFragment).commit();
            navigationView.setCheckedItem(R.id.nav_inventory);
        }
    }

    private int itemSelectedId = R.id.nav_inventory;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.itemSelectedId = item.getItemId();

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
        else if(itemSelectedId == R.id.nav_clients){
            switchToClients();
            drawerLayout.closeDrawer(GravityCompat.START);
        }


        return true;
    }

    void innit(){

        posFragment = new POSFragment(inventory);
        salesFragment = new SalesFragment(sales);
        inventoryFragment = new InventoryFragment(inventory);
        purchaseFragment = new PurchasesFragment();
        clientsFragment = new ClientsFragment(clients);

        inventory.addAll(dataBase.getAllProducts());
        sales.addAll(dataBase.getAllSales());
        clients.addAll(dataBase.getAllClients());

        for(Sale sale :  sales){
            int cid = sale.getClientId();

            int i = -1;
            for(int j = 0; j <  clients.size(); j++){
                if(clients.get(j).getId() == cid){
                    i = j;
                    break;
                }
            }

            sale.setClient(clients.get(i));
        }
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

        if(this.itemSelectedId == R.id.nav_pos){

            if(product.getQuantity() <= 0){
                Toast.makeText(this, "No hay suficiente Stock", Toast.LENGTH_SHORT ).show();
                return;
            }

            if(cart.containsKey(product)){
                Integer cc = cart.get(product);
                cart.put(product, cc+1);

                Product newProduct = product;
                newProduct.setQuantity(product.getQuantity()-1);
                editProduct(product, newProduct, null);
            }
            else{
                cart.put(product, 1);
            }

            Toast.makeText(this, "Producto Añadido", Toast.LENGTH_SHORT ).show();
        }
        else getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditProductFragment(product)).commit();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConfirmSale(cart)).commit();
    }

    public void cancelSale(Sale sale){
        cart.clear();
        switchToPOS();

        for (Map.Entry<Product, Integer> entry : sale.cart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            Product newly = product;
            newly.setQuantity(product.getQuantity() + quantity);
            editProduct(product, newly, null);

        }


        Toast.makeText(this, "Compra Cancelada", Toast.LENGTH_SHORT).show();
    }

    public Sale makeSale(Sale sale){

        ContentValues saleProductsValues = new ContentValues();
        boolean f = true;
        for (Map.Entry<Product, Integer> entry : sale.cart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if(product.getQuantity() < quantity){
                f = false;
                break;
            }
        }

        if(f){
            cart.clear();
            switchToPOS();
            dataBase.addSale(sale);
            salesFragment.addSale(sale);
            Toast.makeText(this, "Compra Completada", Toast.LENGTH_SHORT).show();
        }
        else{
            cart.clear();
            switchToPOS();
            cancelSale(sale);
            Toast.makeText(this, "No hay suficiente inventario", Toast.LENGTH_SHORT).show();
        }

        salesFragment.addSale(sale);
        return sale;
    }


    public void addClient(Client client){
        clientsFragment.addClient(client);
    }

    public void startPurchase(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewPurchaseFragment()).commit();
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

    public void switchToClients(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, clientsFragment).commit();
    }

    public void switchToPurchases(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, purchaseFragment).commit();
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