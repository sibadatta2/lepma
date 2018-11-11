package com.example.dewansh.lepma;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AshaFragmentSuspectRegisteration extends Fragment {
    private static final String TAG = "AshaFragmentSuspectRegi";
    ImageView profile;
    TextView uniqueId,mode,contactTrace;
    EditText name,age,sex,dob,address,relative,aadhar;
    Button register;
    private static final int REQUEST_CODE = 123;
    Spinner spinner,spinner2;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Bitmap bitmap;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    static SuspectObject suspectObject;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    public AshaFragmentSuspectRegisteration() {
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
        View view = inflater.inflate(R.layout.suspect_registeration, container, false);
        register=view.findViewById(R.id.registe_suspect);
        profile=view.findViewById(R.id.profile_image_suspect);
        uniqueId=view.findViewById(R.id.unique_id_suspect);
        mode=view.findViewById(R.id.mode_detection_suspect);
        contactTrace=view.findViewById(R.id.contact_tracing_suspect);
        name=view.findViewById(R.id.name_suspect);
        age=view.findViewById(R.id.age_suspect);

        dob=view.findViewById(R.id.dob_suspect);
        address=view.findViewById(R.id.adderss_suspect);
        relative=view.findViewById(R.id.relative_suspect);
        aadhar=view.findViewById(R.id.relative_suspect);
        String[] ITEMS = {"MALE","FEMALE","OTHER"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner =  view.findViewById(R.id.sex_suspect);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=-1){
                    suspectObject.setSex(spinner.getItemAtPosition(position).toString());

                    Toast.makeText(getActivity(),suspectObject.getSex().toString(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suspectObject.setSex(sex.getText().toString());
                suspectObject.setAadhar(aadhar.getText().toString());
                suspectObject.setAddress(address.getText().toString());
                suspectObject.setAge(age.getText().toString());
                suspectObject.setContactRoot(contactTrace.getText().toString());
                suspectObject.setDob(dob.getText().toString());
                suspectObject.setGps("1.1.1.1");
                suspectObject.setModeDetect(mode.getText().toString());
                suspectObject.setName(name.getText().toString());
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);
                suspectObject.setRegDate(formattedDate);
                c.setMonth(c.getMonth()+1);
                formattedDate = df.format(c);
                suspectObject.setNextAppointment(formattedDate);
            }
        });
        return view;
    }

}
