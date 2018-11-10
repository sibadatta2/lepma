package com.example.dewansh.lepma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final String TAG = "fragment1";

    private static final int REQUEST_CODE = 123;
    Spinner spinner,spinner2;
    ImageView profileiamge;
    String ext;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Bitmap bitmap;
    ArrayList<Image> images;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    EditText type,name,age,sex,address,contactno,aadhar,subcenter,PHC,blockPHC,district,state;
    TextView UID;
    static ASHAObject ashaObject;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private ProgressDialog dialog;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileiamge = view.findViewById(R.id.profile_image);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading profile");
        dialog.show();
        UID=view.findViewById(R.id.uniqueId);
        name=view.findViewById(R.id.Name);
        age=view.findViewById(R.id.Age);
        address=view.findViewById(R.id.Address);
        contactno=view.findViewById(R.id.Contact);
        aadhar=view.findViewById(R.id.AADHHAR);
        subcenter=view.findViewById(R.id.SubCenter);
        PHC=view.findViewById(R.id.PHC);
        blockPHC=view.findViewById(R.id.BlockPHC);
        district=view.findViewById(R.id.district);
        state=view.findViewById(R.id.State);
        ashaObject=new ASHAObject();
        db.collection("PROFILES").document(user.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ashaObject=documentSnapshot.toObject(ASHAObject.class);
                        Log.d("asd",ashaObject.toString());
                        name.setText(ashaObject.getName());
                        UID.setText("Unique ID: "+ashaObject.getUID());
                        Glide.with(getActivity().getApplicationContext()).load(user.getPhotoUrl()).into(profileiamge);
                        dialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                        dialog.dismiss();
                    }
                });
        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();
            ashaObject.setEmail(email);
            Uri photoUrl = user.getPhotoUrl();
            ashaObject.setPhoto(photoUrl.toString());
        }
        String[] ITEMS = {"ASHA","ANM","MPW","OTHER"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner =  view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=-1){
                    ashaObject.setType(spinner.getItemAtPosition(position).toString());
                    Toast.makeText(getContext(),ashaObject.getType().toString(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] ITEMS2 = {"Male","Female","OTHER"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ITEMS2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2 =  view.findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=-1){
                    ashaObject.setSex(spinner2.getItemAtPosition(position).toString());
                    Toast.makeText(getContext(),ashaObject.getSex().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        Button complete = view.findViewById(R.id.CompleteReg);
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
                    Toast.makeText(getContext(), "You did not enter a Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = age.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(getContext(), "You did not enter a age", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = address.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(getContext(), "You did not enter a address", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = contactno.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(getContext(), "You did not enter a contactno", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = aadhar.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(getContext(), "You did not enter a aadhar no.", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = subcenter.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(getContext(), "You did not enter a Subcenter", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = PHC.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(getContext(), "You did not enter a PHC", Toast.LENGTH_SHORT).show();
                    return;
                }x = blockPHC.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(getContext(), "You did not enter a blockPHC", Toast.LENGTH_SHORT).show();
                    return;
                }x = district.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(getContext(), "You did not enter a District", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = state.getText().toString();
                if (x.matches("")) {
                    Toast.makeText(getContext(), "You did not enter a state", Toast.LENGTH_SHORT).show();
                    return;
                }



                db.collection("PROFILES").document(user.getUid()).set(ashaObject)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "profile saved"+ashaObject.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });

            }
        });
        return view;
    }
    void upload(Uri uri){
        StorageReference riversRef = storageRef.child(user.getUid()+"/"+"profile");
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("uploading image");
        progressDialog.show();
        UploadTask uploadTask = riversRef.putFile(uri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"unable to upload to server",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                ashaObject.setPhoto(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                profileiamge.setImageBitmap(bitmap);
                upload(selectedImage);



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void changePictureClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);


    }


}
