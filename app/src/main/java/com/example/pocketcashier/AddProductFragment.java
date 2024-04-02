package com.example.pocketcashier;

import static com.example.pocketcashier.MainActivity.validString;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocketcashier.model.Product;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView backButton;
    private LinearLayout saveButton;

    private EditText nameField;

    private EditText priceField;

    private EditText serialField;
    private TextView cantField;

    private ImageButton uploadImg;
    private View rootView;

    private static final int PICK_IMAGE_REQUEST = 1;
    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProductFragment newInstance(String param1, String param2) {
        EditProductFragment fragment = new EditProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_product, container, false);

        backButton = rootView.findViewById(R.id.back_button);
        saveButton = rootView.findViewById(R.id.save_button);
        nameField = rootView.findViewById(R.id.edit_product_name_field);
        priceField = rootView.findViewById(R.id.edit_product_pri_field);
        serialField = rootView.findViewById(R.id.edit_product_ser_field);
        cantField = rootView.findViewById(R.id.edit_product_cant_field);
        uploadImg = rootView.findViewById(R.id.upload_product_image);

        nameField.setHint("Nombre del producto");
        priceField.setHint("Precio unitario");
        serialField.setHint("# de serie");

        cantField.setText("0");

        backButton.setOnClickListener(e -> {
            ((MainActivity)getActivity()).cancelAddProduct();
        });

        saveButton.setOnClickListener(e -> {

            String nameString = nameField.getText().toString();
            String priceString = priceField.getText().toString();
            String serialString = serialField.getText().toString();

            if(!validString(nameString)){
                Toast.makeText(getContext(), "Llene el nombre", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!validString(priceString)){
                Toast.makeText(getContext(), "Llene el precio", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!validString(serialString)){
                Toast.makeText(getContext(), "Llene el número de serie", Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            try{
                price = Double.valueOf(priceString);
            }
            catch (Exception ex){
                Toast.makeText(getContext(), "El precio debe ser un número real", Toast.LENGTH_SHORT).show();
                return;
            }

            ((MainActivity)getActivity()).addProduct(new Product(1, nameString, price, serialString, null));
        });

        uploadImg.setOnClickListener(e -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*"); // Allow only image files
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });



        return rootView;
    }


}