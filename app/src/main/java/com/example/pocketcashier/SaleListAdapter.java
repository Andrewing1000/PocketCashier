package com.example.pocketcashier;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketcashier.model.Product;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

public class SaleListAdapter extends RecyclerView.Adapter<SaleListAdapter.SaleListViewHolder> {

    private LinkedHashMap<Product, Integer> productList;
    private Context context;

    private Double total;

    public SaleListAdapter(Context context,LinkedHashMap<Product, Integer> productList) {
        this.context = context;
        this.productList = productList;
        this.total = new Double(0.0);
    }

    @NonNull
    @Override
    public SaleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_list_item, parent, false);
        return new SaleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleListViewHolder holder, int position) {

        Object[] keys = productList.keySet().toArray();
        Product product = (Product) keys[position];
        Integer cc = productList.get(product);

        holder.bind(product, cc);
    }


    public Double getTotal(){
        return total;
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class SaleListViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView cantTextView;
        private TextView costTextView;

        private TextView serialTextView;

        private TextView partialPrice;

        private TextView totalTextView;
        private Product product;



        public SaleListViewHolder(@NonNull View itemView) {
            super(itemView);
            partialPrice = itemView.findViewById(R.id.text_product_parcial);
            nameTextView = itemView.findViewById(R.id.text_product_name);
            serialTextView = itemView.findViewById(R.id.text_serial_number);
            costTextView = itemView.findViewById(R.id.text_product_cost);
            cantTextView = itemView.findViewById(R.id.text_product_cant);
            totalTextView = itemView.findViewById(R.id.text_product_parcial);
        }

        public void bind(Product product, Integer cc) {
            nameTextView.setText(product.getName());
            serialTextView.setText(product.getSerialNumber()+"");
            costTextView.setText(product.getUnitPrice() + " Bs.");
            cantTextView.setText(cc + "");

            double pp = cc*product.getUnitPrice();
            partialPrice.setText(pp + "");

            total += pp;

            this.product = product;
        }
    }
}
