package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = "PhotoGalleryFragment";
    private RecyclerView mPhotoRecyclerView;
    private PhotoAdapter mPhotoAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int mNextPage;
    private List<GalleryItem> mItems = new ArrayList<>();
    private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;

    public static Fragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mNextPage = 1;
        new FetchItemTask().execute();

        Handler responseHandler = new Handler();
        mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);
        mThumbnailDownloader.setThumbnailDownloadListener(new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
            @Override
            public void onThumbnailDownloaded(PhotoHolder target, Bitmap thumbnail) {
                Drawable drawable = new BitmapDrawable(getResources(), thumbnail);
                target.bindDrawable(drawable);
            }
        });
        Log.i(TAG, "Przed uruchomieniem wątku tła");
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Wątek tła został uruchomiony");

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mPhotoRecyclerView.setLayoutManager(mGridLayoutManager);
        mPhotoRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int numberOfColumns = 1;
                        int width = mPhotoRecyclerView.getWidth();
                        int widthOfSingleElement = 240;
                        if (width > widthOfSingleElement) {
                            numberOfColumns = width / widthOfSingleElement;
                        }
                        mGridLayoutManager.setSpanCount(numberOfColumns);
                        Log.i(TAG, "numberOfColumns:" + numberOfColumns);
                    }
                }
        );
        setUpAdapter();
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setUpAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloader.quit();
        Log.i(TAG, "Wątek tła został zniszczony");
    }

    private void setUpAdapter() {
        if (isAdded()) {
            if (mNextPage == 1) {
                mPhotoAdapter = new PhotoAdapter(mItems);
                mPhotoRecyclerView.setAdapter(mPhotoAdapter);
            } else {
                mPhotoAdapter.updateDataSet(mItems);
            }

        }
    }

    private class FetchItemTask extends AsyncTask<Void, Void, List<GalleryItem>> {

        @Override
        protected List<GalleryItem> doInBackground(Void... voids) {
            return new FlickrFetcher().fetchItems(mNextPage);
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
//            metoda wykonywana już przez watek głowny -> dlatego możemy już updatować model aplikacji
            if (mNextPage == 1) {
                mItems = items;
            } else {
                mItems.addAll(items);
            }
            setUpAdapter();
            //mNextPage += 1;
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        }

        public void bindDrawable(Drawable drawable) {
            mImageView.setImageDrawable(drawable);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        public void updateDataSet(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_gallery, parent, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
            if (position + 50 > mGalleryItems.size()) {
                new FetchItemTask().execute();
            }
            Drawable drawable = getResources().getDrawable(R.drawable.josie_marion);
            holder.bindDrawable(drawable);
            mThumbnailDownloader.queueThumbnail(holder, mGalleryItems.get(position).getUrl());
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }
}
