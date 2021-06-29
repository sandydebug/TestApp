package com.example.assignmentapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import static android.content.ContentValues.TAG;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

     private List<POJO> placeslist;
     private Context mContext;
     private String borders,languages;

    public PlaceAdapter(Context mContext, List<POJO> placeslist) {
        this.mContext = mContext;
        this.placeslist = placeslist;
    }


    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_tab,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final PlaceViewHolder holder, int position) {

        final POJO placeModel = placeslist.get(position);
        for(int i = 0 ;i<placeModel.getLanguages().size();i++ ){
            if(languages.isEmpty())
                languages = placeModel.getLanguages().get(i).name;
            else
                languages = languages +","+placeModel.getLanguages().get(i).name;
        }
        Glide.with(mContext).load(placeModel.getFlag()).listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                        Target target, boolean isFirstResource) {

                Log.e("tagile", e.getMessage(), e);

                // You can also log the individual causes:
                for (Throwable t : e.getRootCauses()) {
                    Log.e("tag123", "Caused by", t);
                }
                // Or, to log all root causes locally, you can use the built in helper method:
                e.logRootCauses("tag");

                return false; // Allow calling onLoadFailed on the Target.
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                //Toast.makeText(mContext,"image load",Toast.LENGTH_SHORT).show();
                return false;
            }
        }).into(holder.imageView);
        borders = placeModel.getBorders().toString().substring(1,placeModel.getBorders().toString().length()-1);
        //Picasso.get().load(placeModel.getFlag()).into(holder.imageView);

        holder.title.setText("Name - "+placeModel.getName());
        holder.capital.setText("Capital - "+placeModel.getCapital());
        holder.region.setText("Region - "+placeModel.getRegion());
        holder.subregion.setText("SubRegion - "+placeModel.getSubregion());
        holder.population.setText("Population - "+placeModel.getPopulation());
        holder.borders.setText("Borders - "+borders);
        holder.languages.setText("Languages - "+languages);


    }

    @Override
    public int getItemCount() {
        return placeslist.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView title,capital,region,subregion,population,borders,languages;


        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.flag);
            title = itemView.findViewById(R.id.name);
            capital = itemView.findViewById(R.id.capt);
            region = itemView.findViewById(R.id.region);
            subregion = itemView.findViewById(R.id.subregion);
            population = itemView.findViewById(R.id.popu);
            borders = itemView.findViewById(R.id.borders);
            languages = itemView.findViewById(R.id.lang);


        }

    }
}
