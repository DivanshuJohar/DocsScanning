package com.example.docsscanning.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsscanning.R;

import java.util.List;

import MODEL.MODEL_DocmentList;

public class RecyclerView_DocumentList extends RecyclerView.Adapter<RecyclerView_DocumentList.ViewHolder> {
    List<MODEL_DocmentList> model_docmentList ;

    public RecyclerView_DocumentList(List<MODEL_DocmentList> model_docmentList){
        this.model_docmentList = model_docmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.documentlistview,parent,false);
        return new RecyclerView_DocumentList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return model_docmentList == null ? 0 :model_docmentList.size();
    }

   public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name ;
        TextView date ;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.documentList_name);
            date = itemView.findViewById(R.id.documentList_date);
         // image = itemView.findViewById(R.id.);
        }
    }
}
