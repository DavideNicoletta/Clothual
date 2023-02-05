package com.example.clothual.UI.core.adapter;

import android.app.Application;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.R;
import com.example.clothual.UI.core.Photo.PhotoModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class RecyclerViewPhotoAdapter extends RecyclerView.Adapter<RecyclerViewPhotoAdapter.PhotoViewHolder> {

    private List<Image> imageList;
    private final OnItemClickListener onItemClickListener;
    private final Application application;
    private final ContentResolver contentResolver;
    public interface OnItemClickListener{
        void delete();
    }

    public RecyclerViewPhotoAdapter(List<Image> imageList, OnItemClickListener onItemClickListener,
                                    Application application, ContentResolver contentResolver){
        this.imageList = imageList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
        this.contentResolver = contentResolver;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_image_layout,
                parent, false);
        return  new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.bind(imageList.get(position).getUri());
    }

    @Override
    public int getItemCount() {
        if(imageList != null){
            return imageList.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{

        private final ImageView imageView;
        private final PhotoModel model;

        private final RelativeLayout cardViewPhoto;

        private final Button yes;

        private final Button no;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewAdapter);
            cardViewPhoto = itemView.findViewById(R.id.cardViewPhoto);
            yes = itemView.findViewById(R.id.yes);
            no = itemView.findViewById(R.id.no);
            model = new PhotoModel(application);
            itemView.setOnLongClickListener(this);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);

        }

        public void bind(String uri){
            try {
                imageView.setImageBitmap(importImageFromMemory(contentResolver, Uri.parse(uri)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }


        @Override
        public boolean onLongClick(View view) {
            cardViewPhoto.setVisibility(View.VISIBLE);
            return true;
        }


        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.yes){
                int index = imageList.get(getAdapterPosition()).getID();
                //Delite
                model.deleteElement(Uri.parse(imageList.get(getAdapterPosition()).getUri()), index);
                //Delite
                model.deliteImage(imageList.get(getAdapterPosition()));
                List<Clothual> listClothual = model.getAllClothual();
                for(int i = 0; i < listClothual.size(); i++){
                    if(listClothual.get(i).getIdImage() == index){
                        model.deliteClothual(listClothual.get(i));
                    }
                }
                imageList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            }else{
                cardViewPhoto.setVisibility(View.INVISIBLE);
            }

        }
    }

    public Bitmap importImageFromMemory(ContentResolver contentResolver, Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }
}
