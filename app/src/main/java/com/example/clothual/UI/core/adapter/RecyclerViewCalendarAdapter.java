package com.example.clothual.UI.core.adapter;

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
import com.example.clothual.Model.Converters;
import com.example.clothual.Model.Image;
import com.example.clothual.Model.Outfit;
import com.example.clothual.R;
import com.example.clothual.UI.core.Calendar.CalendarModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class RecyclerViewCalendarAdapter extends RecyclerView.Adapter<RecyclerViewCalendarAdapter.CalendarViewHolder> {


    private final List<Clothual> clothualList;
    private final List<Image> imageList;
    private final ContentResolver contentResolver;
    private final OnItemClickListener onItemClickListener;
    private final Application application;
    private final String data;

    public interface OnItemClickListener{
        void buttonAddRemove(boolean delite, String cancel);
    }

    public RecyclerViewCalendarAdapter(List<Clothual> clothualList, List<Image> imageList, ContentResolver contentResolver,
                                        OnItemClickListener onItemClickListener, Application application, String data){
        this.clothualList = clothualList;
        this.imageList = imageList;
        this.data = data;
        this.contentResolver = contentResolver;
        this.onItemClickListener = onItemClickListener;
        this.application = application;
    }

    @NonNull
    @Override
    public RecyclerViewCalendarAdapter.CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothual_calendar,
                parent, false);
        return  new RecyclerViewCalendarAdapter.CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCalendarAdapter.CalendarViewHolder holder, int position) {
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

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageView;
        private final TextView brand;
        private final TextView template;
        private final TextView color;
        private final TextView description;
        private final ImageButton add;
        private final CalendarModel model;
        private final TextView type;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewAdapterClothual);
            brand = itemView.findViewById(R.id.brand);
            template = itemView.findViewById(R.id.template);
            color = itemView.findViewById(R.id.color);
            description = itemView.findViewById(R.id.descritpion);
            add = itemView.findViewById(R.id.add);
            type = itemView.findViewById(R.id.type);
            model = new CalendarModel(application);
            itemView.setOnClickListener(this);
            add.setOnClickListener(this);
        }

        public void bind(Clothual clothual, String uri){
            add.setImageResource(R.drawable.remove_30px);
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
                    type.setText(application.getString(R.string.jackets));
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

            if(view.getId() == R.id.add){
                Outfit  outfit = model.getOutfitByDate(data);

                if(outfit.getClothualString().length() == 1){
                    outfit.setClothualString("");
                    model.updateOutfit(outfit);
                    clothualList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                }else {
                    if (outfit != null) {
                        List<String> listIdClothual = Converters.fromString(outfit.getClothualString());

                        for (int i = 0; i < listIdClothual.size(); i++) {
                            outfit.setClothualList(model.getClothualByID(Integer.parseInt(listIdClothual.get(i))));
                        }


                        for(int i = 0; i < outfit.getClothualList().size(); i++){
                            if(outfit.getClothualList().get(i).getId() == clothualList.get(getAdapterPosition()).getId()){
                                List<Clothual> support = outfit.getClothualList();
                                support.remove(i);
                                outfit.setClothualListByList(support);
                            }
                        }
                        outfit.converter();
                        outfit.removeClothualList();
                        model.updateOutfit(outfit);
                        clothualList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                }

                if(outfit.getClothualString().length() == 0){
                    onItemClickListener.buttonAddRemove(true, "Clothual eliminato dall'aoutfit del " + data);
                }else{
                    onItemClickListener.buttonAddRemove(false, "Clothual eliminato dall'aoutfit del " + data);
                }

            }
        }
    }

}
