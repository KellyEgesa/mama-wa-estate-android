package com.mamawaestate.android;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mamawaestate.android.models.UserLocation;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckOutActivity extends AppCompatActivity {
    UserLocation userLocation;
    @BindView(R.id.textViewCurrentLocation)
    TextView mTextViewCurrentLocation;
    @BindView(R.id.totalPrice)
    TextView mTotalPrice;
    @BindView(R.id.textProductsPrice)
    TextView mTextProductsPrice;
    @BindView(R.id.imageViewCheckout)
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userLocation = Parcels.unwrap(getIntent().getParcelableExtra("userLocation"));
        int price = getIntent().getExtras().getInt("totalPrice");
        mTextProductsPrice.setText(String.valueOf(price));
        mTotalPrice.setText(String.valueOf(price));
        if (userLocation.getAddress() != null) {
            mTextViewCurrentLocation.setText(userLocation.getAddress());
        } else {
            mTextViewCurrentLocation.setText(userLocation.getName());
        }

    }
}