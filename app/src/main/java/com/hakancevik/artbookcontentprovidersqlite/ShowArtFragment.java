package com.hakancevik.artbookcontentprovidersqlite;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hakancevik.artbookcontentprovidersqlite.databinding.FragmentAddArtBinding;
import com.hakancevik.artbookcontentprovidersqlite.databinding.FragmentShowArtBinding;


public class ShowArtFragment extends Fragment {

    private FragmentShowArtBinding binding;

    public ShowArtFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShowArtBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null){
            String getArtName = ShowArtFragmentArgs.fromBundle(getArguments()).getArtName();
            String getArtistName = ShowArtFragmentArgs.fromBundle(getArguments()).getArtistName();
            String getArtYear = ShowArtFragmentArgs.fromBundle(getArguments()).getArtYear();
            Bitmap getImage = ShowArtFragmentArgs.fromBundle(getArguments()).getArtImage();

            binding.imageView2.setImageBitmap(getImage);
            binding.textView2.setText("ArtName: " + getArtName + "ArtistName: " + getArtistName + "Year: " + getArtYear);
        }



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
