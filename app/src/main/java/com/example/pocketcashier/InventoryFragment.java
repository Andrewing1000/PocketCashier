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
import com.example.pocketcashier.utilitaries.SpaceItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.JaccardDistance;

public class InventoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<Product> productList;

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


    public InventoryFragment(ArrayList<Product> collection){
        super();
        productList = collection;

        Log.d("papapa", productList.toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);

        addProduct = rootView.findViewById(R.id.fab_add_product);
        searchBox = rootView.findViewById(R.id.search_item_field);

        GradientDrawable ovalShape = new GradientDrawable();
        ovalShape.setShape(GradientDrawable.OVAL); // Set shape to oval
        ovalShape.setColor(ContextCompat.getColor(container.getContext(), R.color.transparent));

        addProduct.setBackground(ovalShape);
        //addProduct.setColorFilter(iconFilter);

        addProduct.setOnClickListener(e -> {
            if(getActivity() instanceof MainActivity){
                ((MainActivity) getActivity()).startAddProduct();
            }
        });

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

        // Initialize RecyclerView and layout manager
        recyclerView = rootView.findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize product list and adapter
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

    public void addProduct(Product newProduct) {
        productList.add(newProduct);
        adapter.notifyItemInserted(productList.size() - 1);

    }

    public void removeProduct(Product oldProduct) {

        int positionToRemove = productList.indexOf(oldProduct);
        if (positionToRemove >= 0 && positionToRemove < productList.size()) {
            productList.remove(positionToRemove);
            adapter.notifyItemRemoved(positionToRemove);
        }

    }

    public void updateProduct(Product oldProduct, Product updatedProduct) {

        int positionToUpdate = productList.indexOf(oldProduct);
        if (positionToUpdate >= 0 && positionToUpdate < productList.size()) {
            productList.set(positionToUpdate, updatedProduct);
            adapter.notifyItemChanged(positionToUpdate);
        }

    }


    public void setAdapter(ProductAdapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public ProductAdapter getAdapter(){
        return adapter;
    }

}
