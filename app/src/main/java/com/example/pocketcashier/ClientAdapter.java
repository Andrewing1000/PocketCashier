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

import com.example.pocketcashier.model.Client;
import com.example.pocketcashier.model.Product;
import com.example.pocketcashier.model.Sale;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ProductViewHolder> {

    private List<Client> productList;
    private Context context;

    public ClientAdapter(Context context, List<Client> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Client product = productList.get(position);
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
        private Client product;

        private Bitmap image;

        private TextView tag;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_product_name);
            serialTextView = itemView.findViewById(R.id.text_serial_number);
            cantTextView = itemView.findViewById(R.id.text_product_cant);
            costTextView = itemView.findViewById(R.id.text_product_cost);
            imageView = itemView.findViewById(R.id.product_image);
            tag = itemView.findViewById(R.id.priceTag);

            tag.setText("CI cliente: ");
        }

        public void bind(Client product) {

            Client client = product;

            nameTextView.setText(client.getName() + "");
            cantTextView.setText(client.getId() + "");

            imageView.setImageBitmap(null);

            this.product = product;
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }
    }
}
