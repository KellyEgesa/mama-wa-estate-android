package com.mamawaestate.android.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mamawaestate.android.CatalogActivity;
import com.mamawaestate.android.R;
import com.mamawaestate.android.models.Vendors;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListVendorsAdapter extends RecyclerView.Adapter<ListVendorsAdapter.ListVendorsHolder> {
    private Context mContext;
    private List<Vendors> listVendors;

    public ListVendorsAdapter(Context context, List<Vendors> listVendors) {
        mContext = context;
        this.listVendors = listVendors;
    }


    @NonNull
    @Override
    public ListVendorsAdapter.ListVendorsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_home, parent, false);
        ListVendorsAdapter.ListVendorsHolder viewHolder = new ListVendorsHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListVendorsAdapter.ListVendorsHolder holder, int position) {
        holder.bindListVendors(listVendors.get(position));
    }

    @Override
    public int getItemCount() {
        return listVendors.size();
    }

    public class ListVendorsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vendorsName)
        TextView mVendorsName;
        @BindView(R.id.vendorsLocation)
        TextView mVendorsLocation;
        @BindView(R.id.vendorsCategory)
        TextView mVendorsCategory;
        @BindView(R.id.cardVendor)
        CardView card;
Context mContext;

        public ListVendorsHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

        }

        public void bindListVendors(Vendors vendors) {
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CatalogActivity.class);
                    intent.putExtra("vendors", Parcels.wrap(vendors));
                    mContext.startActivity(intent);
                }
            });
            mVendorsName.setText(vendors.getName());
            mVendorsLocation.setText(vendors.getLocation());
            mVendorsCategory.setText(vendors.getCategory());
        }
    }
}
