package com.example.pocketcashier;

//import static com.example.pocketcashier.utilitaries.MenuTitle.iconFilter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketcashier.model.Product;
import com.example.pocketcashier.utilitaries.SpaceItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ConfirmSale extends Fragment {

    private RecyclerView recyclerView;
    private SaleListAdapter adapter;
    private List<Product> cart;


    FloatingActionButton addProduct;

    public ConfirmSale(ArrayList<Product> cart){
        this.cart = cart;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sake_confirmation, container, false);


        // Initialize RecyclerView and layout manager
        recyclerView = rootView.findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayout confirmSale = rootView.findViewById(R.id.cancel_button);
        LinearLayout cancelSale = rootView.findViewById(R.id.sell_button);

        confirmSale.setOnClickListener(e -> {
            ((MainActivity)getActivity()).makeSale();
        });

        confirmSale.setOnClickListener(e -> {
            ((MainActivity)getActivity()).cancelSale();
        });


        adapter = new SaleListAdapter(getActivity(), cart);
        recyclerView.setAdapter(adapter);

        int space = getResources().getDimensionPixelSize(R.dimen.item_spacing); // Adjust this dimension as needed
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));

        // Notify adapter of data change
        adapter.notifyDataSetChanged();

        return rootView;
    }
}
