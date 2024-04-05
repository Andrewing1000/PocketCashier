package com.example.pocketcashier;

//import static com.example.pocketcashier.utilitaries.MenuTitle.iconFilter;

import static com.example.pocketcashier.MainActivity.validString;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketcashier.model.Product;
import com.example.pocketcashier.model.Sale;
import com.example.pocketcashier.utilitaries.SpaceItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.JaccardDistance;

public class SalesFragment extends Fragment {

    private RecyclerView recyclerView;
    private SalesAdapter adapter;
    private ArrayList<Sale> productList;

    EditText searchBox;

    private class NameComparator implements Comparator<Product> {
        String target;
        private NameComparator(String target){
            this.target = target;
        }
        @Override
        public int compare(Product i1, Product i2) {
            Product p1 = i1;
            Product p2 = i2;

            String name1 = p1.getName();
            String name2 = p2.getName();

            return (int)(1000*(NameDistance(name1, this.target) - NameDistance(name2, this.target)));
        }
        private double NameDistance(String shot, String target){
            LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
            JaccardDistance jaccardDistance = new JaccardDistance();
            Integer maxLen = Math.max(target.length(), shot.length());
            return 0.7*(levenshteinDistance.apply(target, shot)/maxLen) + 0.3*jaccardDistance.apply(target, shot);
        }
    }

    FloatingActionButton addProduct;


    public SalesFragment(ArrayList<Sale> collection){
        super();
        productList = collection;

        Log.d("papapa", productList.toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sales, container, false);
        // Initialize RecyclerView and layout manager
        recyclerView = rootView.findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize product list and adapter
        adapter = new SalesAdapter(getActivity(), productList);
        recyclerView.setAdapter(adapter);

        int space = getResources().getDimensionPixelSize(R.dimen.item_spacing); // Adjust this dimension as needed
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));


        // Notify adapter of data change
        adapter.notifyDataSetChanged();

        return rootView;
    }

    public void addSale(Sale newSale) {
        productList.add(newSale);
        adapter.notifyItemInserted(productList.size() - 1);

    }




    public void setAdapter(SalesAdapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public SalesAdapter getAdapter(){
        return adapter;
    }

}
