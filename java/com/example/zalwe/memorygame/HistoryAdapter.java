package com.example.zalwe.memorygame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<Image>{

    Context context;
    int resource;

    private static class ViewHolder{
        ImageView imageView;
    }


    HistoryAdapter(Context context, int resource, List<Image> imageList) {
        super(context, resource,imageList);
        this.context = context;
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        byte[] img = getItem(position).getImage();
        Image image = new Image(img);
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.icon);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(image.getImage(),
                0,image.getImage().length ));

        return convertView;
    }

}

