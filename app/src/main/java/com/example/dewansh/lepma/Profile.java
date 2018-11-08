package com.example.dewansh.lepma;

import android.app.Activity;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
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
    ASHAObject ashaObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileiamge = findViewById(R.id.profile_image);
        Glide.with(this).load(user.getPhotoUrl()).into(profileiamge);

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
        db.collection("PROFILES").document(user.getUid()).set(ashaObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Profile.this, "profile saved"+ashaObject.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        if (user != null) {
            // Name, email address, and profile photo Url
            String uid = user.getUid();
            ashaObject.setUID(uid);
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




     Button complete = findViewById(R.id.CompleteReg);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ashaObject.setName(name.getText().toString());
                ashaObject.setAge(age.getText().toString());
                ashaObject.setAddress(address.getText().toString());
                ashaObject.setContactno(contactno.getText().toString());
                ashaObject.setAadhar(aadhar.getText().toString());
                ashaObject.setSubcenter(subcenter.getText().toString());
                ashaObject.setPHC(PHC.getText().toString());
                ashaObject.setBlockPHC(blockPHC.getText().toString());
                ashaObject.setDistrict(district.getText().toString());
                ashaObject.setState(state.getText().toString());
                String x = name.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(Profile.this, "You did not enter a Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = age.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(Profile.this, "You did not enter a age", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = address.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(Profile.this, "You did not enter a address", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = contactno.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(Profile.this, "You did not enter a contactno", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = aadhar.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(Profile.this, "You did not enter a aadhar no.", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = subcenter.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(Profile.this, "You did not enter a Subcenter", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = PHC.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(Profile.this, "You did not enter a PHC", Toast.LENGTH_SHORT).show();
                    return;
                }x = blockPHC.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(Profile.this, "You did not enter a blockPHC", Toast.LENGTH_SHORT).show();
                    return;
                }x = district.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(Profile.this, "You did not enter a District", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = state.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(Profile.this, "You did not enter a state", Toast.LENGTH_SHORT).show();
                    return;
                }



                db.collection("PROFILES").document(user.getUid()).set(ashaObject)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Profile.this, "profile saved"+ashaObject.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Profile.this, "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
                /*try{
                    imageBytes= UploadImageService.getImageByteArray(bitmap, ext);
                    UploadImageService uploadImageService = new UploadImageService();
                    uploadImageService.uploadImage(imageBytes)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    ashaObject.setPhoto(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                                    FirebaseFirestore.getInstance()
                                            .collection("PROFILES")
                                            .document(user.getUid().toString())
                                            .set(user, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                *//*updateUserObject();
                                                Intent searchIntent = new Intent(FillDetailsActivity.this, MainActivity.class);
                                                Toasty.success(getApplicationContext(), "Registration Complete.", Toast.LENGTH_LONG, true).show();
                                                startActivity(searchIntent);
                                                finish();*//*
                                                    Toasty.error(getApplicationContext(), "imageupload complete. " , Toast.LENGTH_LONG, true).show();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toasty.error(getApplicationContext(), "Registration Failed. " + e.getLocalizedMessage(), Toast.LENGTH_LONG, true).show();

                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toasty.error(getApplicationContext(), "Registration Failed. " + e.getLocalizedMessage(), Toast.LENGTH_LONG, true).show();

                                }
                            });
                }catch(Exception e){}*/
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                profileiamge.setImageBitmap(bitmap);

                if(selectedImage != null) {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        ext = filePath.substring(filePath.lastIndexOf(".") + 1);
                        ext = ext.toUpperCase();
                        cursor.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void changePictureClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);


    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                try {
                    final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                    if (selectedImage != null) {
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                        for(int i = 0; i < filePathColumn.length; ++i)
                            Log.d("DEBUG ", filePathColumn[i]);

                        // Get image extension
                        String ext = "JPG";

                        try {
                            if (cursor != null && cursor.moveToFirst()) {
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String filePath = cursor.getString(columnIndex);
                                ext = filePath.substring(filePath.lastIndexOf(".") + 1);
                                ext = ext.toUpperCase();
                                cursor.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(!isNetworkAvailable())
                            networkNotAvailable("Profile Picture Updation Failed.");
                        else {
                            *//*final KProgressHUD HUD = getKProgressHUD("Please Wait...");
                            HUD.show();*//*


                            final UploadImageService uploadImageService = new UploadImageService();
                            final String finalExt = ext;

                            // Delete old image and upload new image
                            uploadImageService.deleteImage()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            uploadImageService.uploadImage(bitmap, finalExt)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            FirestoreService.changeField("imageUrl", taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                                                            FirestoreService firestoreService = FirestoreService.getInstance();

                                                            FirebaseFirestore.getInstance()
                                                                    .collection("users")
                                                                    .document(FirestoreService.getUser().get("documentID").toString())
                                                                    .set(new ASHAObject(FirestoreService.getUser()), SetOptions.merge())
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            profileiamge.setImageBitmap(bitmap);
                                                                            Toasty.success(Profile.this, "Profile Picture Successfully Updated.", Toast.LENGTH_LONG, true).show();
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toasty.error(Profile.this, e.getMessage(), Toast.LENGTH_LONG, true).show();
                                                                        }
                                                                    });
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toasty.error(Profile.this, e.getMessage() + e.getLocalizedMessage().toString(), Toast.LENGTH_LONG, true).show();
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toasty.error(Profile.this, e.getMessage(), Toast.LENGTH_LONG, true).show();

                                        }
                                    });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void networkNotAvailable(final String Message) {
       *//* final KProgressHUD HUD = getKProgressHUD("Please Wait...");
        HUD.show();
*//*
        final SweetAlertDialog pDialog = new SweetAlertDialog(Profile.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please Wait....");
        pDialog.setCancelable(false);
        pDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Toasty.error(getActivity(), Message + " Check Your Internet Connection.", Toast.LENGTH_LONG, true).show();
                pDialog.dismiss();
                new SweetAlertDialog(Profile.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Connectivity Issue!")
                        .setContentText(Message)
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        }).show();
            }
        }, 2000);

    }*/
   private byte[] imageBytes;


}
