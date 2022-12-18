package com.hakancevik.artbookcontentprovidersqlite.adapter;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hakancevik.artbookcontentprovidersqlite.ArtListFragmentDirections;
import com.hakancevik.artbookcontentprovidersqlite.R;
import com.hakancevik.artbookcontentprovidersqlite.databinding.RecyclerRowBinding;
import com.hakancevik.artbookcontentprovidersqlite.model.ArtModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ArtHolder> {

    ArrayList<ArtModel> artList;

    public ArtAdapter(ArrayList<ArtModel> artList) {
        this.artList = artList;
    }

    @NonNull
    @Override
    public ArtAdapter.ArtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArtHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtAdapter.ArtHolder holder, int position) {


        holder.binding.recyclerViewTextView.setText(artList.get(holder.getAdapterPosition()).getArtName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArtListFragmentDirections.ActionArtListFragmentToShowArtFragment action = ArtListFragmentDirections.actionArtListFragmentToShowArtFragment();

                action.setArtName(artList.get(holder.getAdapterPosition()).getArtName());
                action.setArtistName(artList.get(holder.getAdapterPosition()).getArtistName());
                action.setArtYear(artList.get(holder.getAdapterPosition()).getArtYear());
                action.setArtImage(artList.get(holder.getAdapterPosition()).getArtImage());

                Navigation.findNavController(v).navigate(action);
            }
        });


    }

    @Override
    public int getItemCount() {
        return artList.size();
    }


    public class ArtHolder extends RecyclerView.ViewHolder {

        private RecyclerRowBinding binding;

        public ArtHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
