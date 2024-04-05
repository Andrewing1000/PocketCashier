package com.example.pocketcashier;

import static com.example.pocketcashier.MainActivity.validString;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
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

import java.security.Permission;


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
    private EditText cantField;

    private ImageButton uploadImg;

    private ImageButton takeImg;
    private ImageView image;
    private View rootView;

    private Uri imagePath;

    ActivityResultLauncher<Intent> takePictureLauncher;

    ActivityResultLauncher<String> pickPictureLauncher;
    private static final int PICK_IMAGE_REQUEST = 1;
    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_product, container, false);
        imagePath = null;

        backButton = rootView.findViewById(R.id.back_button);
        saveButton = rootView.findViewById(R.id.save_button);
        nameField = rootView.findViewById(R.id.edit_product_name_field);
        priceField = rootView.findViewById(R.id.edit_product_pri_field);
        serialField = rootView.findViewById(R.id.edit_product_ser_field);
        cantField = rootView.findViewById(R.id.edit_product_cant_field);
        uploadImg = rootView.findViewById(R.id.upload_product_image);
        takeImg = rootView.findViewById(R.id.take_product_image);
        image = rootView.findViewById(R.id.product_image);

        nameField.setHint("Nombre del producto");
        priceField.setHint("Precio unitario");
        serialField.setHint("# de serie");
        cantField.setHint("Stock");

        backButton.setOnClickListener(e -> {
            ((MainActivity)getActivity()).cancelAddProduct();
        });

        saveButton.setOnClickListener(e -> {

            String nameString = nameField.getText().toString();
            String priceString = priceField.getText().toString();
            String serialString = serialField.getText().toString();
            String cantString = cantField.getText().toString();

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
            if(!validString(cantString)){
                Toast.makeText(getContext(), "Llene el stock", Toast.LENGTH_SHORT).show();
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

            int stock;
            try{
                stock = Integer.valueOf(priceString);
            }
            catch (Exception ex){
                Toast.makeText(getContext(), "La cantidad debe ser un numero real", Toast.LENGTH_SHORT).show();
                return;
            }

            Product result = ((MainActivity)getActivity()).addProduct(new Product(1, nameString, price, serialString, stock), imagePath);
            if(result == null){
                Toast.makeText(getContext(), "Revise los datos", Toast.LENGTH_SHORT).show();
            }
            else{
                ((MainActivity)getActivity()).switchToInventory();
                Toast.makeText(getContext(), "Adición exitosa", Toast.LENGTH_SHORT).show();
            }
        });


        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // Photo taken, handle it here
                Bundle extras = result.getData().getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                // Do something with the imageBitmap (e.g., display it in an ImageView)
                image.setImageBitmap(imageBitmap);
            }
        });


        pickPictureLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                // Image picked, handle it here
                imagePath = result;
                // Do something with the selectedImageUri (e.g., display it in an ImageView)
            }
        });

        uploadImg.setOnClickListener(e -> {
            dispatchPickImageIntent();
        });


        /*takeImg.setOnClickListener(e -> {

                if (checkCameraPermission()) {
                    dispatchTakePictureIntent();
                } else {
                    requestCameraPermission();
                }
            }
        );*/




        return rootView;
    }

    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;

    private boolean checkCameraPermission() {

        return true;//ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION) == PackageManager.PERMISSION_GRANTED;
    }

    // Method to request camera permission
    private void requestCameraPermission() {
        //ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION}, REQUEST_CAMERA_PERMISSION);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            takePictureLauncher.launch(takePictureIntent);
        }
    }

    // Method to open the file picker
    private void dispatchPickImageIntent() {
        Intent pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickImageIntent.setType("image/*");
        if (pickImageIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Start the file picker activity
            pickPictureLauncher.launch("image/*");
        }
    }




}