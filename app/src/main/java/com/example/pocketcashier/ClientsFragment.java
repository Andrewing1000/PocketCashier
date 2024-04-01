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
        productList.add(new Product("Product 1", "Red", 10.99));
        productList.add(new Product("Product 2", "Blue", 15.99));
        productList.add(new Product("Product 3", "Green", 20.99));
        productList.add(new Product("Product 4", "Yellow", 25.99));
        productList.add(new Product("Product 5", "Orange", 30.99));

        // Notify adapter of data change
        adapter.notifyDataSetChanged();

        return rootView;
    }
}
