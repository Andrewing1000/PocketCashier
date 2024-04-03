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

import java.util.List;

public class SaleListAdapter extends RecyclerView.Adapter<SaleListAdapter.SaleListViewHolder> {

    private List<Product> productList;
    private Context context;

    public SaleListAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public SaleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_list_item, parent, false);
        return new SaleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleListViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
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

        private ImageView imageView;
        private Product product;



        public SaleListViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_product_name);
            serialTextView = itemView.findViewById(R.id.text_serial_number);
            cantTextView = itemView.findViewById(R.id.text_product_cant);
            costTextView = itemView.findViewById(R.id.text_product_cost);
        }

        public void bind(Product product) {
            nameTextView.setText(product.getName());
            serialTextView.setText(product.getSerialNumber()+"");
            cantTextView.setText(product.getQuantity() + "");
            costTextView.setText(product.getUnitPrice() + " Bs.");


            this.product = product;
        }
    }
}
