package com.mamawaestate.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mamawaestate.android.models.UserLocation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingCartActivity extends AppCompatActivity {
    UserLocation userLocation;
    @BindView(R.id.imageViewShoppingCart)
    ImageView backButton;
    @BindView(R.id.textViewPrice)
    TextView mPrice;
    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);
        ButterKnife.bind(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCartList = ShoppingCartHelper.getCartList();
        int price = 0;
        for (Product product :
                mCartList) {
            price += product.price;
        }

        // Make sure to clear the selections
        for (int i = 0; i < mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }

        mPrice.setText("Price in Kshs " + price);

        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        FloatingActionButton proceedToCheckout = (FloatingActionButton) findViewById(R.id.floatingActionViewCheckout);
        proceedToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCartActivity.this, CheckOutActivity.class);
                startActivity(intent);
            }
        });
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true);
        listViewCatalog.setAdapter(mProductAdapter);

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                FragmentManager fm = getSupportFragmentManager();
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(ShoppingCartHelper.PRODUCT_INDEX, position);
                productDetailsFragment.setArguments(bundle);
                productDetailsFragment.show(fm, "Product Details");
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the data
        if (mProductAdapter != null) {
            mProductAdapter.notifyDataSetChanged();
        }
    }

}