package com.example.pocketcashier;

//import static com.example.pocketcashier.utilitaries.MenuTitle.iconFilter;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketcashier.model.Product;
import com.example.pocketcashier.utilitaries.SpaceItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ClientsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;


    FloatingActionButton addProduct;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clients, container, false);


        // Initialize RecyclerView and layout manager
        recyclerView = rootView.findViewById(R.id.recycler_view_clients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize product list and adapter
        productList = new ArrayList<>();
        adapter = new ProductAdapter(getActivity(), productList);
        recyclerView.setAdapter(adapter);

        int space = getResources().getDimensionPixelSize(R.dimen.item_spacing); // Adjust this dimension as needed
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));



        // Dummy data - Replace with your actual data retrieval logic
        productList.add(new Product(1, "Camisa", 25.99, "ABC123", 50));
        productList.add(new Product(2, "Pantalón", 39.99, "DEF456", 30));
        productList.add(new Product(3, "Zapatos", 59.99, "GHI789", 20));
        productList.add(new Product(4, "Sombrero", 14.99, "JKL012", 40));
        productList.add(new Product(5, "Bufanda", 9.99, "MNO345", 60));
        productList.add(new Product(6, "Guantes", 12.99, "PQR678", 25));
        productList.add(new Product(7, "Gorra", 19.99, "STU901", 35));
        productList.add(new Product(8, "Calcetines", 6.99, "VWX234", 45));
        productList.add(new Product(9, "Chaqueta", 89.99, "YZA567", 15));
        productList.add(new Product(10, "Falda", 29.99, "BCD890", 55));
        productList.add(new Product(11, "Vestido", 49.99, "EFG123", 10));
        productList.add(new Product(12, "Traje", 99.99, "HIJ456", 5));
        productList.add(new Product(13, "Blusa", 34.99, "KLM789", 40));
        productList.add(new Product(14, "Jeans", 44.99, "NOP012", 30));
        productList.add(new Product(15, "Shorts", 19.99, "QRS345", 20));
        productList.add(new Product(16, "Sweater", 29.99, "TUV678", 25));
        productList.add(new Product(17, "Abrigo", 79.99, "WXY901", 15));
        productList.add(new Product(18, "Botas", 69.99, "ZAB234", 20));
        productList.add(new Product(19, "Sandalias", 39.99, "BCD567", 30));
        productList.add(new Product(20, "Chaleco", 22.99, "EFG890", 40));

        // Notify adapter of data change
        adapter.notifyDataSetChanged();

        return rootView;
    }
}
