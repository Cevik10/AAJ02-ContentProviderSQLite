package com.hakancevik.artbookcontentprovidersqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hakancevik.artbookcontentprovidersqlite.R;
import com.hakancevik.artbookcontentprovidersqlite.databinding.RecyclerRowBinding;
import com.hakancevik.artbookcontentprovidersqlite.model.ArtModel;

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
                Navigation.findNavController(v).navigate(R.id.action_artListFragment_to_showArtFragment);
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
