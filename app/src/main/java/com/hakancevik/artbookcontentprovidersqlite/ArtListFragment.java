package com.hakancevik.artbookcontentprovidersqlite;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hakancevik.artbookcontentprovidersqlite.adapter.ArtAdapter;
import com.hakancevik.artbookcontentprovidersqlite.databinding.FragmentArtListBinding;
import com.hakancevik.artbookcontentprovidersqlite.model.ArtModel;

import java.util.ArrayList;


public class ArtListFragment extends Fragment {

    private FragmentArtListBinding binding;


    ArrayList<ArtModel> artList;

    ArtAdapter adapter;


    public ArtListFragment() {
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

        binding = FragmentArtListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);


        artList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ArtAdapter(artList);
        binding.recyclerView.setAdapter(adapter);



        String Url = ArtContentProvider.URL;
        Uri artUri = Uri.parse(Url);

        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();

        Cursor cursor = contentResolver.query(artUri, null, null, null, "name");

        if (cursor != null) {

            while (cursor.moveToNext()) {

                String artName = cursor.getString(cursor.getColumnIndexOrThrow(ArtContentProvider.NAME));
                String artistName = cursor.getString(cursor.getColumnIndexOrThrow(ArtContentProvider.ARTIST_NAME));
                String artYear = cursor.getString(cursor.getColumnIndexOrThrow(ArtContentProvider.ART_YEAR));

                byte[] bytes = cursor.getBlob(cursor.getColumnIndexOrThrow(ArtContentProvider.IMAGE));
                Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                artList.add(new ArtModel(artName, artistName, artYear, image));

                adapter.notifyDataSetChanged();
            }

        }



        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.action_artListFragment_to_addArtFragment);
            }
        });


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}