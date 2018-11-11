package com.example.dewansh.lepma;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    private List<ASHAObject> ashaObjectList;

    private Context context;
    private FirebaseFirestore firestoreDB;

    public NoteRecyclerViewAdapter(List<ASHAObject> notesList, Context context, FirebaseFirestore firestoreDB) {
        this.ashaObjectList = notesList;
        this.context = context;
        this.firestoreDB = firestoreDB;
        Toast.makeText(context,ashaObjectList.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int itemPosition = position;
        final ASHAObject note = ashaObjectList.get(itemPosition);

        holder.title.setText(note.getName());
        holder.content.setText(note.getContactno());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveAsha(note);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(note.getId(), itemPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ashaObjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;
        ImageView edit;
        ImageView delete;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvTitle);
            content = view.findViewById(R.id.tvContent);

            edit = view.findViewById(R.id.ivEdit);
            delete = view.findViewById(R.id.ivDelete);
        }
    }

    /* private void updateNote(Note note) {
         Intent intent = new Intent(context, NoteActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         intent.putExtra("UpdateNoteId", note.getId());
         intent.putExtra("UpdateNoteTitle", note.getTitle());
         intent.putExtra("UpdateNoteContent", note.getContent());
         context.startActivity(intent);
     }
 */
    void approveAsha(ASHAObject ashaObject){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference noteRef = db.document("PROFILES/"+ashaObject.getUID());
        noteRef.update("approval","approve");

    }
    private void deleteNote(String id, final int position) {
        firestoreDB.collection("PROFILES")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ashaObjectList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, ashaObjectList.size());
                        Toast.makeText(context, "Note has been deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}