package com.example.workshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class adapter for single work shop item.
 */

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.WorkshopHolder> implements Filterable {

    private Context context;
    private List<WorkshopItem> workshopList;
    private  List<WorkshopItem> workshopAll;

    public WorkshopAdapter(Context context , List workshops){
        this.context = context;
        workshopList = workshops;
        workshopAll = new ArrayList<>(workshops);
    }

    @NonNull
    @Override
    public WorkshopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workshop_item,parent,false);
        return new WorkshopHolder(view);
    }

    /**
     * Value entering to views & click action.
     */
    @Override
    public void onBindViewHolder(@NonNull WorkshopHolder holder, int position) {


            WorkshopItem workshopItem = workshopList.get(position);
            holder.name.setText(workshopItem.getName());
            holder.description.setText(workshopItem.getDescription());
            holder.description.setText(workshopItem.getDescription());
            Glide.with(context).load(workshopItem.getImage()).apply(RequestOptions.circleCropTransform()).into(holder.imageView);

            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,WorkshopDetailActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("name",workshopItem.getName());
                    bundle.putString("image",workshopItem.getImage());
                    bundle.putString("description",workshopItem.getDescription());
                    bundle.putString("text",workshopItem.getText());
                    bundle.putString("video",workshopItem.getVideo());

                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });
    }

    @Override
    public int getItemCount() {
        return workshopList.size();
    }


    /**
     * Search filter method.
     */
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //run on background thread;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<WorkshopItem> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredList.addAll(workshopAll);
            }
            else {
                for (WorkshopItem w: workshopAll) {
                    if (w.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(w);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values=filteredList;

            return filterResults;
        }

        //run on ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            workshopList.clear();
            workshopList.addAll((Collection<? extends WorkshopItem>) results.values);
            notifyDataSetChanged();
        }
    };


    /**
     * Layout items identification.
     */
    public class WorkshopHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,description;
        ConstraintLayout constraintLayout;
        public WorkshopHolder(@NonNull View itemView) {
            super(itemView);

            imageView =  itemView.findViewById(R.id.author_image);
            name = itemView.findViewById(R.id.workshop_name);
            description = itemView.findViewById(R.id.description);
            constraintLayout=itemView.findViewById(R.id.main_layout);
        }
    }

}
