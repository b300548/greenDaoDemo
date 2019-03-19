package com.example.xpeng.greendaodemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {
    private CollectionClickListener mClickListener;
    private List<Collection> mCollections;


    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collection, viewGroup, false);

        return new CollectionViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder collectionViewHolder, int i) {
        Collection collection = mCollections.get(i);
        collectionViewHolder.name.setText(collection.getName());
        collectionViewHolder.uuid.setText(collection.getUuid());
        collectionViewHolder.type.setText(collection.getType());

    }

    @Override
    public int getItemCount() {
        return mCollections.size();
    }

    public interface CollectionClickListener{
        void onCollectionClick(int position);
    }

    static class CollectionViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView uuid;
        public TextView type;


        public CollectionViewHolder(@NonNull View itemView, final CollectionClickListener clickListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            uuid = (TextView) itemView.findViewById(R.id.uuid);
            type = (TextView) itemView.findViewById(R.id.type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null){
                        clickListener.onCollectionClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public CollectionAdapter(CollectionClickListener clickListener){
        mClickListener = clickListener;
        mCollections = new ArrayList<>();
    }

    public void setCollections(List<Collection> collections) {
        mCollections = collections;
    }

    public Collection getCollection(int position){
         return mCollections.get(position);
    }
}
