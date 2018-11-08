package com.example.dewansh.lepma;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Admin extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("PROFILES");

    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);



        Query query = FirebaseFirestore.getInstance()
                .collection("PROFILES")
                .orderBy("timestamp")
                .limit(50);

        FirestoreRecyclerOptions<ASHAObject> options = new FirestoreRecyclerOptions.Builder<ASHAObject>()
                .setQuery(query, ASHAObject.class)
                .build();

        adapter = new NoteAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycleradmin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                ASHAObject note = documentSnapshot.toObject(ASHAObject.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(Admin.this,
                        note.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
