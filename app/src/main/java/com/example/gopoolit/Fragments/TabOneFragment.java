package com.example.gopoolit.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.gopoolit.R;
import com.example.gopoolit.databinding.FragmentTabOneBinding;
import com.example.gopoolit.util.AddWaterMark;

public class TabOneFragment extends Fragment {
    private FragmentTabOneBinding binding;
    private int selectedType = 0;
    public static String filePath = null;
    public static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 445;
    private Uri selectedMediaUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_one, container, false);
        setClickListener();
        return binding.getRoot();
    }

    private void setClickListener() {

        binding.one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = 1;
                checkPermission();
            }
        });
        binding.two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = 2;
                checkPermission();
            }
        });
        binding.three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = 3;
                checkPermission();
            }
        });
        binding.four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = 4;
                checkPermission();
            }
        });
        binding.five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = 5;
                checkPermission();
            }
        });
    }

    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {
            openGallery();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                openGallery();

            }

        }
    }

    private void openGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/* video/*");
        startActivityForResult(pickIntent, 123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == getActivity().RESULT_OK) {
            selectedMediaUri = data.getData();
            if (selectedMediaUri.toString().contains("image")) {
                try {
                    filePath = selectedMediaUri.getPath();
                    new AsyncTaskRunner().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (selectedMediaUri.toString().contains("video")) {
                Toast.makeText(getActivity(), "Not working", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, Bitmap> {

        ProgressDialog progressDialog;

        @Override
        protected Bitmap doInBackground(String... strings) {
            // Let's read picked image data - its URI

            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedMediaUri, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();

            return AddWaterMark.addWatermark(getResources(), bitmap);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressDialog.dismiss();
            switch (selectedType) {
                case 1: {
                    binding.one.setImageBitmap(bitmap);
                    break;
                }
                case 2: {
                    binding.two.setImageBitmap(bitmap);
                    break;
                }
                case 3: {
                    binding.three.setImageBitmap(bitmap);
                    break;
                }
                case 4: {
                    binding.four.setImageBitmap(bitmap);
                    break;
                }
                case 5: {
                    binding.five.setImageBitmap(bitmap);
                    break;
                }


            }

            super.onPostExecute(bitmap);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "ProgressDialog", "Please Wait....");
            super.onPreExecute();
        }

    }
}