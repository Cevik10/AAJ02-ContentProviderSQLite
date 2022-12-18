package com.hakancevik.artbookcontentprovidersqlite;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hakancevik.artbookcontentprovidersqlite.databinding.FragmentAddArtBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class AddArtFragment extends Fragment {

    private FragmentAddArtBinding binding;

    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    Bitmap selectedImage;

    NavController navController;




    public AddArtFragment() {
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
        binding = FragmentAddArtBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);


        registerLauncher();


        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton();
            }
        });

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButton();
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButton();
            }
        });


    }


    public void selectImage() {
        //photo permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Snackbar.make(requireView(), "Permission needed for gallery.", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //request permission

                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                    }
                }).show();


            } else {
                // request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

            }

        } else {
            // go to gallery
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentToGallery);

        }
    }


    private void saveButton() {
        String artName = binding.artNameText.getText().toString();
        String artistName = binding.artistNameText.getText().toString();
        String artYear = binding.yearText.getText().toString();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
        byte[] bytes = outputStream.toByteArray();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ArtContentProvider.NAME, artName);
        contentValues.put(ArtContentProvider.ARTIST_NAME, artistName);
        contentValues.put(ArtContentProvider.ART_YEAR, artYear);
        contentValues.put(ArtContentProvider.IMAGE, bytes);

        getActivity().getApplicationContext().getContentResolver().insert(ArtContentProvider.CONTENT_URI, contentValues);

        navController.navigate(R.id.action_addArtFragment_to_artListFragment);

        //Toast.makeText(requireContext(), "saved.", Toast.LENGTH_SHORT).show();
        //Navigation.findNavController(requireView()).navigate(R.id.action_addArtFragment_to_artListFragment);

    }

    public void deleteButton() {


    }

    public void updateButton() {
    }


    public void registerLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intentFromResult = result.getData();
                    if (intentFromResult != null) {
                        Uri imageData = intentFromResult.getData();
                        //binding.imageView.setImageURI(imageData); // we want to data not source for database
                        try {

                            if (Build.VERSION.SDK_INT >= 28) {
                                ImageDecoder.Source source = ImageDecoder.createSource(requireContext().getContentResolver(), imageData);
                                selectedImage = ImageDecoder.decodeBitmap(source);
                                binding.imageView.setImageBitmap(selectedImage);

                            } else {
                                selectedImage = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageData);
                                binding.imageView.setImageBitmap(selectedImage);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });


        //permissions process
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {
                    //permission granted
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);

                } else {
                    //permission denied
                    Toast.makeText(requireContext(), "Permission needed!", Toast.LENGTH_LONG).show();
                }
            }

        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}