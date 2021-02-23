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
import com.mamawaestate.android.models.Vendors;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatalogActivity extends AppCompatActivity {

    @BindView(R.id.vendorsNameCatalog)
    TextView mVendorsNameCatalog;
    @BindView(R.id.vendorsLocationCatalog)
    TextView mVendorsLocationCatalog;
    @BindView(R.id.vendorsCategoryCatalog)
    TextView mVendorsCategoryCatalog;
    @BindView(R.id.imageViewCatalog)
    ImageView backButton;
    private List<Product> mProductList;
    private Vendors vendor;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog);
        ButterKnife.bind(this);

        vendor = Parcels.unwrap(getIntent().getParcelableExtra("vendors"));
        mVendorsNameCatalog.setText(vendor.getName());
        mVendorsLocationCatalog.setText(vendor.getLocation());
        mVendorsCategoryCatalog.setText(vendor.getCategory());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Obtain a reference to the product catalog
        mProductList = ShoppingCartHelper.getCatalog(getResources());

        // Create the list
        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        listViewCatalog.setAdapter(new ProductAdapter(mProductList, getLayoutInflater(), false));

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

        FloatingActionButton viewShoppingCart = (FloatingActionButton) findViewById(R.id.floatingActionViewCart);
        viewShoppingCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewShoppingCartIntent = new Intent(getBaseContext(), ShoppingCartActivity.class);
                startActivity(viewShoppingCartIntent);
            }
        });

    }
}