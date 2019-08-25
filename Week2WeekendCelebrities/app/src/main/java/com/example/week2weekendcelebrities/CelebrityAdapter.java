package com.example.week2weekendcelebrities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week2weekendcelebrities.model.celebrity.Celebrity;

import java.util.ArrayList;

public class CelebrityAdapter {
    private ArrayList<Celebrity> celebList;


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvName;
        private Celebrity itemCeleb;

        public ViewHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
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

//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setMessage("Delete animal for database?").setPositiveButton("Yes", confirmation)
//                        .setNegativeButton("No",confirmation).show();

        }
    }
}
