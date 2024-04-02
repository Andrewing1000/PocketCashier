package com.example.pocketcashier;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketcashier.model.Category;
import com.example.pocketcashier.model.Product;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private Context context;

    public CategoryAdapter(Context context, List<Category> productList) {
        this.context = context;
        this.categoryList = productList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category);
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView serialTextView;
        private Category category;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_category_name);
            serialTextView = itemView.findViewById(R.id.text_serial_number);
        }

        public void bind(Category product) {
            nameTextView.setText(product.getName());
            serialTextView.setText(product.getId()+"");

            this.category = category;
        }
    }
}
