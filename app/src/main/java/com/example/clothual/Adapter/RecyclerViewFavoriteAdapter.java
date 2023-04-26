package com.example.clothual.Adapter;


import android.app.Application;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class RecyclerViewFavoriteAdapter extends RecyclerView.Adapter<RecyclerViewFavoriteAdapter.ShoesViewHolder> {

    private final List<Clothual> clothualList;
    private final  List<Image> imageList;
    private final ContentResolver contentResolver;
    private OnItemClickListener onItemClickListener;
    private Application application;
    public interface OnItemClickListener{
        void buttonFavorite(String favorite);

        void change(Clothual clothual, Image image);
    }

    public RecyclerViewFavoriteAdapter(List<Clothual> clothualList, List<Image> imageList, ContentResolver contentResolver,
                                       OnItemClickListener onItemClickListener, Application application){
        this.clothualList = clothualList;
        this.imageList = imageList;
        this.contentResolver = contentResolver;
        this.onItemClickListener = onItemClickListener;
        this.application = application;
    }

    @NonNull
    @Override
    public ShoesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothual_total_adapter,
                parent, false);
        return  new ShoesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoesViewHolder holder, int position) {
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

    public class ShoesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageView;

        private final TextView brand;
        private final TextView template;
        private final TextView color;
        private final TextView type;
        private final TextView description;
        private final ImageButton delite;
        private final ImageButton favorite;
        private final ImageButton edit;
        private final CategoryModel model;

        public ShoesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewAdapterClothual);
            brand = itemView.findViewById(R.id.brand);
            template = itemView.findViewById(R.id.template);
            color = itemView.findViewById(R.id.color);
            edit = itemView.findViewById(R.id.edit);
            type = itemView.findViewById(R.id.type);
            description = itemView.findViewById(R.id.descritpion);
            delite = itemView.findViewById(R.id.delite);
            model = new CategoryModel(application);
            favorite = itemView.findViewById(R.id.favorite);
            itemView.setOnClickListener(this);
            favorite.setOnClickListener(this);

        }

        public void bind(Clothual clothual, String uri){
            edit.setVisibility(View.INVISIBLE);
            delite.setVisibility(View.INVISIBLE);
            favorite.setImageResource(R.drawable.favorite_fil_48px);
            brand.setText(clothual.getBrand());
            System.out.println(clothual.getBrand());
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
            if(view.getId() == R.id.favorite){
                clothualList.get(getAdapterPosition()).setPreferite(false);
                model.updateClothaulElement(clothualList.get(getAdapterPosition()));
                clothualList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                onItemClickListener.buttonFavorite("Removed by Favorite");
            }else{
                onItemClickListener.change(clothualList.get(getAdapterPosition()), imageList.get(getAdapterPosition()));
            }
        }
    }
}
