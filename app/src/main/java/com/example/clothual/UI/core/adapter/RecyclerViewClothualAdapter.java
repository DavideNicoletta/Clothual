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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.R;
import com.example.clothual.UI.core.Categories.CategoryModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class RecyclerViewClothualAdapter extends RecyclerView.Adapter<RecyclerViewClothualAdapter.ClothualViewHolder> {

    private final List<Clothual> clothualList;
    private final  List<Image> imageList;
    private final ContentResolver contentResolver;
    private final OnItemClickListener onItemClickListener;
    private final Application application;

    public interface OnItemClickListener{
        void buttonDelete(String notify);
        void buttonFavorite(String favorite);
        void buttonEdit(String uri, int action , int id);
        void changeView(boolean change, int ID);
    }

    public RecyclerViewClothualAdapter(List<Clothual> clothualList, List<Image> imageList, ContentResolver contentResolver,
                                       OnItemClickListener onItemClickListener, Application application){
        this.clothualList = clothualList;
        this.imageList = imageList;
        this.contentResolver = contentResolver;
        this.onItemClickListener = onItemClickListener;
        this.application = application;
    }

    @NonNull
    @Override
    public ClothualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothual_total_adapter,
                parent, false);
        return  new ClothualViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothualViewHolder holder, int position) {
        int image = clothualList.get(position).getIdImage();
        if(imageList.get(position).getID() == image){
            holder.bind(clothualList.get(position), imageList.get(position).getUri());
        }
    }

    @Override
    public int getItemCount() {
        if(clothualList != null){
            return clothualList.size();
        }
        return 0;
    }

    public class ClothualViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageView;
        private String favoriteString;
        private final TextView brand;
        private final TextView template;
        private final TextView color;
        private final TextView description;
        private final ImageButton delite;
        private final CategoryModel model;
        private final TextView type;
        private final ImageButton favorite;
        private final ImageButton edit;

        private final RelativeLayout cardView;

        private final Button yes;

        private final Button no;

        public ClothualViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewAdapterClothual);
            brand = itemView.findViewById(R.id.brand);
            template = itemView.findViewById(R.id.template);
            color = itemView.findViewById(R.id.color);
            description = itemView.findViewById(R.id.descritpion);
            delite = itemView.findViewById(R.id.delite);
            type = itemView.findViewById(R.id.type);
            favorite = itemView.findViewById(R.id.favorite);
            edit = itemView.findViewById(R.id.edit);
            model = new CategoryModel(application);
            itemView.setOnClickListener(this);
            delite.setOnClickListener(this);
            favorite.setOnClickListener(this);
            edit.setOnClickListener(this);
            yes = itemView.findViewById(R.id.yesTotal);
            no = itemView.findViewById(R.id.noTotal);
            cardView = itemView.findViewById(R.id.cardViewTotal);
            cardView.setOnClickListener(this);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);
        }

        public void bind(Clothual clothual, String uri){
            if(clothual.isPreferite()){
                favorite.setImageResource(R.drawable.favorite_fil_48px);
            }else{
                favorite.setImageResource(R.drawable.favorite_48px);
            }

            brand.setText(clothual.getBrand());
            template.setText(clothual.getTemplate());
            color.setText(clothual.getColor());
            description.setText(clothual.getDescription());
            switch (clothual.getType()){
                case 1:
                    type.setText(application.getString(R.string.shoes));
                    break;
                case 2:
                    type.setText(application.getString(R.string.trousers));
                    break;
                case 3:
                    type.setText(application.getString(R.string.tshirt));
                    break;
                case 4:
                    type.setText(application.getString(R.string.sweatshirt));
                    break;
                case 5:
                    type.setText(application.getString(R.string.jeans));
                    break;
            }
            try {
                imageView.setImageBitmap(importImageFromMemory(contentResolver, Uri.parse(uri)));
            } catch (FileNotFoundException e) {
            }
        }

        public Bitmap importImageFromMemory(ContentResolver contentResolver, Uri imageUri) throws FileNotFoundException {
            InputStream inputStream = contentResolver.openInputStream(imageUri);
            return BitmapFactory.decodeStream(inputStream);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.delite:
                    cardView.setVisibility(View.VISIBLE);
                    break;

                case R.id.favorite:
                    if(!clothualList.get(getAdapterPosition()).isPreferite()){
                        favorite.setImageResource(R.drawable.favorite_fil_48px);
                        clothualList.get(getAdapterPosition()).setPreferite(true);
                        model.updateClothaulElement(clothualList.get(getAdapterPosition()));
                        notifyItemChanged(getAdapterPosition());
                        favoriteString = "Add to Preferite";
                    }else{
                        favorite.setImageResource(R.drawable.favorite_fil_48px);
                        clothualList.get(getAdapterPosition()).setPreferite(false);
                        model.updateClothaulElement(clothualList.get(getAdapterPosition()));
                        notifyItemChanged(getAdapterPosition());
                        favoriteString = "Removed by Preferite";
                    }
                    onItemClickListener.buttonFavorite(favoriteString);
                    break;

                case R.id.edit:
                    onItemClickListener.buttonEdit(imageList.get(getAdapterPosition()).getUri(), 1,
                            clothualList.get(getAdapterPosition()).getId());
                    break;

                case R.id.yesTotal:
                    String string = clothualList.get(getAdapterPosition()).getBrand() + " " +
                            clothualList.get(getAdapterPosition()).getTemplate() + " has been deleted";

                    try {
                        model.deleteElement(contentResolver, Uri.parse(imageList.get(getAdapterPosition()).getUri()), clothualList.get(getAdapterPosition()).getId());
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    model.deleteImage(imageList.get(getAdapterPosition()));
                    model.deleteClothual(clothualList.get(getAdapterPosition()));
                    imageList.remove(getAdapterPosition());
                    clothualList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    onItemClickListener.buttonDelete(string);
                    break;

                case R.id.noTotal:
                    cardView.setVisibility(View.INVISIBLE);
                    break;

                case R.id.imageViewAdapterClothual:
                    onItemClickListener.changeView(true, clothualList.get(getAdapterPosition()).getId());
                default:
                    break;
            }
        }
    }
}
