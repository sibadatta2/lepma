package com.example.dewansh.lepma;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import fr.ganfra.materialspinner.MaterialSpinner;

import static android.app.Activity.RESULT_OK;
import static java.security.AccessController.getContext;

public class Profile extends AppCompatActivity {
    private static final int REQUEST_CODE = 123;
Spinner spinner,spinner2;
ImageView profileiamge;
    String ext;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Bitmap bitmap;
    ArrayList<Image> images;
    private static final String TAG = "Profile";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    EditText type,name,age,sex,address,contactno,aadhar,subcenter,PHC,blockPHC,district,state;
    TextView UID;
    static ASHAObject ashaObject;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileiamge = findViewById(R.id.profile_image);
        ashaObject=(ASHAObject) getIntent().getSerializableExtra("ASHAObject");
        UID=findViewById(R.id.uniqueId);
        name=findViewById(R.id.Name);
        age=findViewById(R.id.Age);
        address=findViewById(R.id.Address);
        contactno=findViewById(R.id.Contact);
        aadhar=findViewById(R.id.AADHHAR);
        subcenter=findViewById(R.id.SubCenter);
        PHC=findViewById(R.id.PHC);
        blockPHC=findViewById(R.id.BlockPHC);
        district=findViewById(R.id.district);
        state=findViewById(R.id.State);
        ashaObject=new ASHAObject();
        name.setText(ashaObject.getName());
        UID.setText("Unique ID: "+ashaObject.getUID());
        Glide.with(Profile.this).load(user.getPhotoUrl()).into(profileiamge);
        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();
            ashaObject.setEmail(email);
            Uri photoUrl = user.getPhotoUrl();
            ashaObject.setPhoto(photoUrl.toString());
        }
        String[] ITEMS = {"ASHA","ANM","MPW","OTHER"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner =  findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=-1){
                ashaObject.setType(spinner.getItemAtPosition(position).toString());
                    Toast.makeText(Profile.this,ashaObject.getType().toString(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] ITEMS2 = {"Male","Female","OTHER"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2 =  findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=-1){
                    ashaObject.setSex(spinner2.getItemAtPosition(position).toString());
                    Toast.makeText(Profile.this,ashaObject.getSex().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        name.setText(ashaObject.getName());
        age.setText(ashaObject.getAge());
        address.setText(ashaObject.getAddress());
        contactno.setText(ashaObject.getContactno());
        aadhar.setText(ashaObject.getAadhar());
        subcenter.setText(ashaObject.getSubcenter());
        PHC.setText(ashaObject.getPHC());
        blockPHC.setText(ashaObject.getBlockPHC());
        district.setText(ashaObject.getDistrict());
        state.setText(ashaObject.getState());

    }




}
