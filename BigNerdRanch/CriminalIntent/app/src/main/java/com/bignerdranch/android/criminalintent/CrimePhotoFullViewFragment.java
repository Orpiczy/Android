package com.bignerdranch.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.File;

public class CrimePhotoFullViewFragment extends DialogFragment {
    private static final String ARG_FILE = "photo_file";
    private ImageView mImageView;


    public static CrimePhotoFullViewFragment newInstance(File file){
        Bundle args = new Bundle();
        args.putString(ARG_FILE,file.getPath());
        CrimePhotoFullViewFragment fragment = new CrimePhotoFullViewFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String pathPhoto = (String) getArguments().getString(ARG_FILE);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.image_view_dialog,null);
        mImageView = (ImageView) v.findViewById(R.id.crime_photo_full);
        if(pathPhoto != null){
            Bitmap bitmap = PictureUtils.getScaledBitmap(pathPhoto,getActivity());
            mImageView.setImageBitmap(bitmap);
        }
        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }
}

