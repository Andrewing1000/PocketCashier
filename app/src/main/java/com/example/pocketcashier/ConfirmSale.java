package com.example.pocketcashier;

//import static com.example.pocketcashier.utilitaries.MenuTitle.iconFilter;

import static com.example.pocketcashier.MainActivity.validString;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketcashier.model.Product;
import com.example.pocketcashier.model.Sale;
import com.example.pocketcashier.utilitaries.SpaceItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

public class ConfirmSale extends Fragment {

    private RecyclerView recyclerView;
    private SaleListAdapter adapter;
    LinkedHashMap<Product, Integer> cart;
    private ImageButton backButton;

    FloatingActionButton addProduct;

    public ConfirmSale(LinkedHashMap<Product, Integer> cart){
        this.cart = cart;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sake_confirmation, container, false);


        // Initialize RecyclerView and layout manager
        recyclerView = rootView.findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayout confirmSale = rootView.findViewById(R.id.sell_button);
        LinearLayout cancelSale = rootView.findViewById(R.id.cancel_button);
        TextView totalField = rootView.findViewById(R.id.total_field);
        EditText ciField = rootView.findViewById(R.id.client_spinner);
        EditText nameField = rootView.findViewById(R.id.client_name_field);
        backButton = rootView.findViewById(R.id.back_button);


        backButton.setOnClickListener(e -> {
            ((MainActivity)getActivity()).switchToPOS();
        });

        confirmSale.setOnClickListener(e -> {
            String nameString = nameField.getText().toString();
            String ciString = ciField.getText().toString();


            if(!validString(ciString)){
                Toast.makeText(getContext(), "Llene el CI/NIT", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!validString(nameString)) {
                Toast.makeText(getContext(), "Llene el nombre del cliente", Toast.LENGTH_SHORT).show();
                return;
            }

            int stock;
            try{
                stock = Integer.valueOf(ciString);
            }
            catch (Exception ex){
                Toast.makeText(getContext(), "El ci ser un numero", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar currentDateTime = Calendar.getInstance();

            Sale tobe = new Sale(1, currentDateTime+"", getTotal(), cart, Integer.parseInt(ciString));
            tobe.setClient(ciString, nameString);
            Sale result = ((MainActivity) getActivity()).makeSale(tobe);

            if(result == null){
                Toast.makeText(getContext(), "Revise los datos", Toast.LENGTH_SHORT).show();
            }
            else{
                ((MainActivity)getActivity()).switchToInventory();
                Toast.makeText(getContext(), "Compra exitosa", Toast.LENGTH_SHORT).show();
            }






        });

        cancelSale.setOnClickListener(e -> {
            Calendar currentDateTime = Calendar.getInstance();
            Sale notobe = new Sale(1, currentDateTime+"", getTotal(), cart, 0);
            ((MainActivity)getActivity()).cancelSale(notobe);
        });


        adapter = new SaleListAdapter(getActivity(), cart);
        recyclerView.setAdapter(adapter);


        Double total = adapter.getTotal();

        totalField.setText(getTotal() + " Bs.");

        adapter.notifyDataSetChanged();

        return rootView;
    }


    public double getTotal(){
        double res = 0;
        for(Product p : cart.keySet()){
            res += p.getUnitPrice()*cart.get(p);
        }
        return res;
    }
}
