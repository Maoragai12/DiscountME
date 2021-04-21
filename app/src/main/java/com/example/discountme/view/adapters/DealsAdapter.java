package com.example.discountme.view.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discountme.data.local.AppLocalDb;
import com.example.discountme.data.local.DealDao;
import com.example.discountme.data.remote.AuthFirebase;
import com.example.discountme.model.Deal;
import com.example.discountme.R;
import com.example.discountme.model.DealRepository;
import com.example.discountme.view.activities.LoginActivity;
import com.example.discountme.view.fragments.DealDetailsFragment;
import com.example.discountme.view.fragments.DealsListFragment;
import com.example.discountme.view.fragments.DealsListFragmentDirections;
import com.example.discountme.viewmodels.DealViewModel;
import com.example.discountme.viewmodels.DealsListViewModel;
import com.example.discountme.viewmodels.factory.DealViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ItemViewHolder>{

    private ArrayList<Deal> mDeals;
    private Fragment mFragment;
    private DealViewModel dealViewModel;

    public DealsAdapter(ArrayList<Deal> deals, Fragment fragment) {
        mDeals = deals;
        mFragment = fragment;
    }

    public void setData(ArrayList<Deal> deals){
        mDeals.clear();;
        mDeals.addAll(deals);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DealsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_deal_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealsAdapter.ItemViewHolder holder, int position) {
        Deal deal = mDeals.get(position);
        holder.bind(deal);

    }

    @Override
    public int getItemCount() {
        return mDeals.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView dealNameText;
        private TextView descriptionText;
        private TextView userNameText;
        private ImageView userImageView;
        private ImageButton addToFavButton;

        public ItemViewHolder(View itemView) {
            super(itemView);

            dealNameText = itemView.findViewById(R.id.deal_name);
            userNameText = itemView.findViewById(R.id.txt_user_name);
            userImageView = itemView.findViewById(R.id.iv_user);
            descriptionText = itemView.findViewById(R.id.txt_deal_description);
            addToFavButton = itemView.findViewById(R.id.add_to_fav_button);

        }

        public void bind(Deal deal) {
            dealNameText.setText(deal.getTitle());
            descriptionText.setText(deal.getDescription());

            String currUserId = FirebaseAuth.getInstance().getUid();
            boolean likedByUser = deal.likedByUser(currUserId);


            addToFavButton.setImageResource(
                    likedByUser ? R.drawable.ic_in_fav : R.drawable.ic_not_fav
            );

            AuthFirebase.getUserFromFirebase(deal.getUserId(), user -> {
                userNameText.setText(user.getName());

                if(user.getImage().isEmpty()){
                    Picasso.get().load(R.drawable.selfprofile)
                            .centerCrop()
                            .fit()
                            .into(userImageView);
                }
                else {

                    Picasso.get().load(user.getImage())
                            .placeholder(R.drawable.selfprofile)
                            .error(R.drawable.selfprofile)
                            .centerCrop()
                            .fit()
                            .into(userImageView);
                }
            });

            this.itemView.setOnClickListener(v -> {
                NavDirections action = DealsListFragmentDirections.showDetailsAction(deal);
                Navigation.findNavController(v).navigate(action);
            });

            DealDao dealDao = AppLocalDb.db.dealDao();
            DealRepository repository = DealRepository.getInstance(dealDao);

            addToFavButton.setOnClickListener(v -> {

                if(likedByUser) {

                    repository.removeDealFromFavs(deal, new DealRepository.Listener<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {

                        }
                    });
                }
                else {

                    repository.addDealToFavs(deal, new DealRepository.Listener<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {

                        }
                    });
                }
            });

        }
    }
}
