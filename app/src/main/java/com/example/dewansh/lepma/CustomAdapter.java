package com.example.dewansh.lepma;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


    private List<SuspectObject> suspectObjectsList;

    private Context context;
    private FirebaseFirestore firestoreDB;
    public CustomAdapter(List<SuspectObject> notesList, Context context, FirebaseFirestore firestoreDB) {

        this.suspectObjectsList = notesList;
        this.context = context;
        this.firestoreDB = firestoreDB;
        Toast.makeText(context,suspectObjectsList.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final SuspectObject note = suspectObjectsList.get(position);
        holder.textViewName.setText("NAME: "+note.getName());
        holder.UID.setText("UID: "+note.getUID());
        Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return suspectObjectsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName,UID;
        ImageView photo;
        ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            UID=(TextView)itemView.findViewById(R.id.suspectID);
            photo=(ImageView)itemView.findViewById(R.id.suspectimage);
        }
    }
    public void filterList(ArrayList<SuspectObject> filterdNames) {
        this.suspectObjectsList = filterdNames;
        notifyDataSetChanged();
    }
}
