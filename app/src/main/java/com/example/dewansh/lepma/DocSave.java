package com.example.dewansh.lepma;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.paging.FirestoreDataSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class DocSave extends AppCompatActivity {
    private static final String TAG = "DocSave";
    String title1;
    String desc;
    EditText Title;
    EditText Desc;
    Button save,load;
    TextView rettitle,retdesc,textViewData;
    FirebaseFirestore db =  FirebaseFirestore.getInstance();
    ListenerRegistration noteListener;
    DocumentReference noteref = db.collection("Notebook").document("first note");
    private CollectionReference notebookRef = db.collection("Notebook");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_save);
    }
}