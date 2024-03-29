package com.example.week2weekendcelebrities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week2weekendcelebrities.model.celebrity.Celebrity;

import java.util.ArrayList;

import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CelebrityEntry.CELEBRITY_CONTENT_URI;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_FAVORITE;


public class CelebrityAdapter extends RecyclerView.Adapter<CelebrityAdapter.ViewHolder>{
    private ArrayList<Celebrity> celebList;

    public CelebrityAdapter(ArrayList<Celebrity> celebList) {
        this.celebList = celebList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list_item,parent,false);
        return new ViewHolder(inflatedItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Celebrity currentCeleb = celebList.get(position);
        //fill views here
        holder.tvName.setText(currentCeleb.getName());
        holder.tvHeight.setText(currentCeleb.getHeight());
        holder.tvBorn.setText(currentCeleb.getBorn());
        holder.tvNationality.setText(currentCeleb.getNationality());
        holder.togFavorite.setChecked(currentCeleb.getFavorite()==1);
        holder.setItemCeleb(currentCeleb);
    }

    @Override
    public int getItemCount() {
        return celebList.size();
    }

    public void onDatabaseChange(ArrayList<Celebrity> list){
        celebList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvName, tvHeight, tvBorn, tvNationality;
        private Celebrity itemCeleb;
        private ToggleButton togFavorite;

        public ViewHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvHeight = itemView.findViewById(R.id.tvHeight);
            tvBorn = itemView.findViewById(R.id.tvBorn);
            tvNationality = itemView.findViewById(R.id.tvNationality);
            togFavorite = itemView.findViewById(R.id.togFavorite);
            itemView.setOnClickListener(this);

            togFavorite.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(togFavorite.isChecked())
                        itemCeleb.setFavorite(1);
                    else
                        itemCeleb.setFavorite(0);
                    ContentValues cv = new ContentValues();
                    cv.put(COL_FAVORITE, itemCeleb.getFavorite());
                    view.getContext().getContentResolver().update(CELEBRITY_CONTENT_URI, cv, null, new String[]{itemCeleb.getName()});
                    notifyDataSetChanged();
                }
            });
        }

        public void setItemCeleb(Celebrity celeb){this.itemCeleb = celeb;}

        @Override
        public void onClick(View view) {
            //go to update activity
            Intent detailsIntent = new Intent(view.getContext(), ViewCelebrityActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("celeb", itemCeleb);
            detailsIntent.putExtras(bundle);
            view.getContext().startActivity(detailsIntent);

        }
    }
}
