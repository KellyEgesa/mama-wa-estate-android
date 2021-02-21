package com.mamawaestate.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mamawaestate.android.userLocation.UserLocation;

import org.parceler.Parcels;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {
    UserLocation userLocation;
    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);
        userLocation = Parcels.unwrap(getIntent().getParcelableExtra("userLocation"));

        mCartList = ShoppingCartHelper.getCartList();

        // Make sure to clear the selections
        for (int i = 0; i < mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }

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