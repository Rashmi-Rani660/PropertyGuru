package com.example.propertyguru;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.*;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.util.*;

public class PhotosFragment extends Fragment {

    private static final int MAX_PHOTOS = 10;
    private static final int MAX_VIDEOS = 5;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 1001;

    private Uri cameraImageUri;
    private final List<Uri> mediaUris = new ArrayList<>();
    private RecyclerView recyclerView;
    private MediaAdapter adapter;

    private Button btnContinue;

    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ActivityResultLauncher<Intent> videoPickerLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        recyclerView = view.findViewById(R.id.recyclerMedia);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        adapter = new MediaAdapter(mediaUris, uri -> {
            mediaUris.remove(uri);
            adapter.notifyDataSetChanged();
            toggleContinueButton();
        });

        recyclerView.setAdapter(adapter);

        MaterialButton btnUploadPhoto = view.findViewById(R.id.btnGallery);
        MaterialButton btnUploadVideo = view.findViewById(R.id.btnVideo);
        MaterialButton btnCamera = view.findViewById(R.id.btnCamera);
        btnContinue = view.findViewById(R.id.btnContinue);

        btnCamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                openCamera();
            }
        });

        // Photo Picker
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        if (getPhotoCount() < MAX_PHOTOS + MAX_VIDEOS) {
                            mediaUris.add(selectedImage);
                            adapter.notifyDataSetChanged();
                            toggleContinueButton();
                        } else {
                            Toast.makeText(getContext(), "Max limit reached", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Video Picker
        videoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedVideo = result.getData().getData();
                        if (getPhotoCount() < MAX_PHOTOS + MAX_VIDEOS) {
                            mediaUris.add(selectedVideo);
                            adapter.notifyDataSetChanged();
                            toggleContinueButton();
                        } else {
                            Toast.makeText(getContext(), "Max limit reached", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btnUploadPhoto.setOnClickListener(v -> {
            if (getPhotoCount() < MAX_PHOTOS) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
            } else {
                Toast.makeText(getContext(), "Maximum 10 images allowed", Toast.LENGTH_SHORT).show();
            }
        });

        btnUploadVideo.setOnClickListener(v -> {
            if (getVideoCount() < MAX_VIDEOS) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                videoPickerLauncher.launch(intent);
            } else {
                Toast.makeText(getContext(), "Maximum 5 videos allowed", Toast.LENGTH_SHORT).show();
            }
        });


        btnContinue.setOnClickListener(v -> {
            if (mediaUris == null || mediaUris.isEmpty()) {
                Toast.makeText(getContext(), "Please upload at least one photo or video.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Step 1: Role pass karo – yahan example ke liye broker
            AmenitiesFragment fragment = new AmenitiesFragment();
            Bundle bundle = new Bundle();
            bundle.putString("role", "broker"); // 👈 Change this to "owner" or "builder" as needed
            fragment.setArguments(bundle);

            // Step 2: Fragment replace karo
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.propertyDetailsContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });


        btnContinue.setVisibility(View.GONE); // Hide initially

        return view;
    }

    private void toggleContinueButton() {
        if (mediaUris.size() > 0) {
            btnContinue.setVisibility(View.VISIBLE);
        } else {
            btnContinue.setVisibility(View.GONE);
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
                File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                photoFile = new File(storageDir, fileName);
                cameraImageUri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".fileprovider", photoFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && cameraImageUri != null) {
            mediaUris.add(cameraImageUri);
            adapter.notifyItemInserted(mediaUris.size() - 1);
            toggleContinueButton();
        }
    }

    private int getPhotoCount() {
        int count = 0;
        for (Uri uri : mediaUris) {
            if (uri.toString().contains("image")) count++;
        }
        return count;
    }

    private int getVideoCount() {
        int count = 0;
        for (Uri uri : mediaUris) {
            if (uri.toString().contains("video")) count++;
        }
        return count;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
