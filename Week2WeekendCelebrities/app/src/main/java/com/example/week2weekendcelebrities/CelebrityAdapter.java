package com.example.week2weekendcelebrities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week2weekendcelebrities.model.celebrity.Celebrity;

import java.util.ArrayList;


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

        private TextView tvName, tvHeight;
        private Celebrity itemCeleb;

        public ViewHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvHeight = itemView.findViewById(R.id.tvHeight);
            itemView.setOnClickListener(this);
        }

        public void setItemCeleb(Celebrity celeb){this.itemCeleb = celeb;}

        @Override
        public void onClick(View view) {
            //go to update activity
            Intent detailsIntent = new Intent(view.getContext(), ViewCelebrityActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("animal", itemCeleb);
            detailsIntent.putExtras(bundle);
            view.getContext().startActivity(detailsIntent);

        }
    }
}
