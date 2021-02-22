package com.mamawaestate.android;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import java.util.List;

public class ProductDetailsFragment extends DialogFragment {

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.productdetails, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        List<Product> catalog = ShoppingCartHelper.getCatalog(getResources());
        Bundle bundle = this.getArguments();
        int productIndex = bundle.getInt(
                ShoppingCartHelper.PRODUCT_INDEX);
        final Product selectedProduct = catalog.get(productIndex);

        // Set the proper image and text
        ImageView productImageView = (ImageView) rootView.findViewById(R.id.ImageViewProduct);
        productImageView.setImageDrawable(selectedProduct.productImage);
        TextView productTitleTextView = (TextView) rootView.findViewById(R.id.TextViewProductTitle);
        productTitleTextView.setText(selectedProduct.title);
        TextView productDetailsTextView = (TextView) rootView.findViewById(R.id.TextViewProductDetails);
        productDetailsTextView.setText(selectedProduct.description);

        // Save a reference to the quantity edit text
        final EditText editTextQuantity = (EditText) rootView.findViewById(R.id.editTextQuantity);

        CardView add =  (CardView) rootView.findViewById(R.id.cardAdd);
        CardView minus =  (CardView) rootView.findViewById(R.id.cardMinus);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = 0;
                quantity = Integer.parseInt(editTextQuantity.getText()
                        .toString());
                editTextQuantity.setText(String.valueOf(quantity+1));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = 0;
                quantity = Integer.parseInt(editTextQuantity.getText()
                        .toString());
                editTextQuantity.setText(String.valueOf(quantity-1));
            }
        });

        // Update the current quantity in the cart
        TextView textViewCurrentQuantity = (TextView) rootView.findViewById(R.id.textViewCurrentlyInCart);
        textViewCurrentQuantity.setText("Currently in Cart: "
                + ShoppingCartHelper.getProductQuantity(selectedProduct));



        Button addToCartButton = (Button) rootView.findViewById(R.id.ButtonAddToCart);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check to see that a valid quantity was entered
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(editTextQuantity.getText()
                            .toString());

                    if (quantity < 0) {
                        Toast.makeText(getContext(),
                                "Please enter a quantity of 0 or higher",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(),
                            "Please enter a numeric quantity",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                // If we make it here, a valid quantity was entered
                ShoppingCartHelper.setQuantity(selectedProduct, quantity);

                // Close the activity
                getDialog().dismiss();
            }
        });


        return rootView;
    }

}
