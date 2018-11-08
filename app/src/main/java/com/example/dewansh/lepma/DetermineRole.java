package com.example.dewansh.lepma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DetermineRole extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("IDS");
    private DocumentReference noteRef = db.document("IDS/REGISTERED");
    private TextView textViewData;
    boolean dirty;
    private static final String TAG = "DetermineRole";
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_determine_role);
        textViewData = findViewById(R.id.text_view_data);
        dialog = new ProgressDialog(this);
        dialog.show();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            ImageView imageView = (ImageView) findViewById(R.id.my_image_view);

            Glide.with(this).load(photoUrl).into(imageView);
            dirty = false;
            notebookRef.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            String data = "";

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Role note = documentSnapshot.toObject(Role.class);
                                note.setDocumentId(documentSnapshot.getId());

                                String documentId = note.getDocumentId();
                                String title = note.getId();
                                String description = note.getRole();

                                data += "ID: " + documentId
                                        + "\nTitle: " + title + "\nDescription: " + description + "\n\n";
                                if(title.equals(user.getEmail())){
                                    if(description.equals("admin")){
                                        dirty= true;
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                        Toast.makeText(DetermineRole.this, "Welcome admin",Toast.LENGTH_LONG).show();
                                        Intent admin = new Intent(DetermineRole.this,Admin.class);
                                        startActivity(admin);
                                        finish();
                                    }
                                    else if(description.equals("asha")){

                                            Toast.makeText(DetermineRole.this, "Welcome asha",Toast.LENGTH_LONG).show();
                                            Intent asha = new Intent(DetermineRole.this,Asha.class);
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                            dirty= true;
                                            startActivity(asha);
                                            finish();

                                    }else if(description.equals("pending-asha")){

                                        Toast.makeText(DetermineRole.this, "Your request is still pending",Toast.LENGTH_LONG).show();
                                        Intent asha = new Intent(DetermineRole.this,Profile.class);
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                        dirty= true;
                                        startActivity(asha);
                                        finish();

                                    }
                                }
                            }
                            Log.d(TAG,""+dirty);
                            if (dirty== false){
                                // user is new ,create a new entry in pending requests
                                Role role = new Role();
                                role.setRole("pending-asha");
                                role.setId(user.getEmail());
                                Toast.makeText(DetermineRole.this, "404",Toast.LENGTH_LONG).show();
                                db.collection("IDS").document("Pending").set(role).
                                         addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(DetermineRole.this, "request made for registeration", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(DetermineRole.this, "Error!", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, e.toString());
                                            }
                                        });

                            }
                            textViewData.setText(data);
                            Log.d(TAG,data);
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });

        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    public void loadNotes(View v) {

    }
}
