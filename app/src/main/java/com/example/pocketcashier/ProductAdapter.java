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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        String imagePath = product.getImagePath();

        if(imagePath != null){
            holder.setImage(((MainActivity)context).loadImageFromInternalStorage(imagePath));
        }

        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView cantTextView;
        private TextView costTextView;

        private TextView serialTextView;

        private ImageView imageView;
        private Product product;

        private Bitmap image;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_product_name);
            serialTextView = itemView.findViewById(R.id.text_serial_number);
            cantTextView = itemView.findViewById(R.id.text_product_cant);
            costTextView = itemView.findViewById(R.id.text_product_cost);
            imageView = itemView.findViewById(R.id.product_image);
        }

        public void bind(Product product) {
            nameTextView.setText(product.getName());
            serialTextView.setText(product.getSerialNumber()+"");
            cantTextView.setText(product.getQuantity() + "");
            costTextView.setText(product.getUnitPrice() + " Bs.");

            if(image != null) imageView.setImageBitmap(image);
            else imageView.setImageResource(R.drawable.image_place_holder);

            this.product = product;

            super.itemView.setOnClickListener(e -> {
                ((MainActivity)context).startEditProduct(this.product);
            });
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }
    }
}
