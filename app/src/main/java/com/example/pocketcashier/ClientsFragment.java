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

import com.example.pocketcashier.model.Client;
import com.example.pocketcashier.model.Product;
import com.example.pocketcashier.model.Sale;
import com.example.pocketcashier.utilitaries.SpaceItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.JaccardDistance;

public class ClientsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ClientAdapter adapter;
    private ArrayList<Client> productList;

    EditText searchBox;

    private class NameComparator implements Comparator<Client> {
        String target;
        private NameComparator(String target){
            this.target = target;
        }
        @Override
        public int compare(Client i1, Client i2) {
            Client p1 = i1;
            Client p2 = i2;

            String name1 = p1.getCi();
            String name2 = p2.getCi();

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


    public ClientsFragment(ArrayList<Client> collection){
        super();
        productList = collection;


        Log.d("papapa", productList.toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clients, container, false);
        // Initialize RecyclerView and layout manager
        recyclerView = rootView.findViewById(R.id.recycler_view_products);
        searchBox = rootView.findViewById(R.id.search_item_field);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize product list and adapter
        adapter = new ClientAdapter(getActivity(), productList);
        recyclerView.setAdapter(adapter);

        int space = getResources().getDimensionPixelSize(R.dimen.item_spacing); // Adjust this dimension as needed
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));




        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is being changed.
                // You can perform actions here when the text changes.
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = searchBox.getText().toString();
                if(validString(search)){
                    productList.sort(new NameComparator(search));
                    adapter.notifyDataSetChanged();
                }
            }
        });


        // Notify adapter of data change
        adapter.notifyDataSetChanged();

        return rootView;
    }

    public void addClient(Client newSale) {
        productList.add(newSale);
        adapter.notifyItemInserted(productList.size() - 1);

    }




    public void setAdapter(ClientAdapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public ClientAdapter getAdapter(){
        return adapter;
    }

}
