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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DetermineRole extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("PROFILES");
    private DocumentReference noteRef = db.document("PROFILES/"+user.getUid());
    private TextView textViewData;
    boolean dirty;
    private static final String TAG = "DetermineRole";
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_determine_role);
        dialog = new ProgressDialog(this);
        dialog.show();
        if (user != null) {
            // Name, email address, and profile photo Url
            String uid = user.getUid();

            dirty = false;
            noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String data = "";
                    if(!documentSnapshot.exists()){
                        ASHAObject ashaObject = new ASHAObject();


                        ashaObject.setEmail(user.getEmail());
                        ashaObject.setUID(user.getUid());
                        ashaObject.setName(user.getDisplayName());
                        ashaObject.setApproval("unapproved");
                        ashaObject.setPhoto(user.getPhotoUrl().toString());
                        ashaObject.setType("asha");
                        Toast.makeText(DetermineRole.this, "404! new asha object created",Toast.LENGTH_LONG).show();
                        db.collection("PROFILES").document(user.getUid()).set(ashaObject).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(DetermineRole.this, "request made for registeration", Toast.LENGTH_SHORT).show();
                                        Intent i =new Intent(DetermineRole.this,Profile.class);
                                        startActivity(i);
                                        finish();
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
                    else{
                        ASHAObject ashaObject = documentSnapshot.toObject(ASHAObject.class);
                        ashaObject.setId(documentSnapshot.getId());

                        String approval = ashaObject.getApproval();
                        String email = ashaObject.getEmail();
                        String type = ashaObject.getType();

                        data += "approval: " + approval
                                + "\nemail: " + email + "\ntype: " + type + "\n\n";
                        Log.d("asd",data);
                        if(email.equals(user.getEmail())){
                            Toast.makeText(DetermineRole.this, "mail found",Toast.LENGTH_LONG).show();
                            if(type.equals("admin")){
                                Toast.makeText(DetermineRole.this, "Welcome admin",Toast.LENGTH_LONG).show();
                                dirty= true;
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                Toast.makeText(DetermineRole.this, "Welcome admin",Toast.LENGTH_LONG).show();
                                Intent admin = new Intent(DetermineRole.this,Main3Activity.class);
                                startActivity(admin);
                                finish();
                            }
                            else if(type.equals("asha")){
                                Toast.makeText(DetermineRole.this, "asha",Toast.LENGTH_LONG).show();
                                if(approval.equals("approved")){
                                    Toast.makeText(DetermineRole.this, "Welcome asha",Toast.LENGTH_LONG).show();
                                   // Intent asha = new Intent(DetermineRole.this,Asha.class);
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                    dirty= true;
                                    //startActivity(asha);
                                    finish();
                                }else if(approval.equals("unapproved")) {
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
                    }

                    Log.d(TAG,""+dirty);

                    Log.d(TAG,data);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });

        }else{
            Intent i = new Intent(DetermineRole.this,Login.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}