package com.example.dewansh.lepma;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class suspectSearch  extends Fragment {

    private static final String TAG = "suspectSearch";
    private RecyclerView recyclerView;
    private CustomAdapter mAdapter;

    private FirebaseFirestore firestoreDB;
    private ListenerRegistration firestoreListener;


    public suspectSearch() {
        // Required empty public constructor
    }

    public static suspectSearch newInstance(String param1, String param2) {
        suspectSearch fragment = new suspectSearch();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    EditText editTextSearch;
    ArrayList<String> names;
    CustomAdapter adapter;
    List<SuspectObject> suspectObjects = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.suspect_search, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_suspect_ids);
        editTextSearch = (EditText) view.findViewById(R.id.editTextSearch);
/////////////////////////////
        firestoreDB = FirebaseFirestore.getInstance();
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("loading");
        progressDialog.show();
        loadNotesList();
        progressDialog.dismiss();
        firestoreListener = firestoreDB.collection("SUSPECTS")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, "Listen failed!", e);
                            return;
                        }



                        for (DocumentSnapshot doc : documentSnapshots) {
                            SuspectObject suspectObject=null;
                            suspectObject = doc.toObject(SuspectObject.class);
                            suspectObject.setId(doc.getId());
                            Log.d("asd",suspectObject.toString());
                           // if(/*!(ashaObject.getType().equals("admin")) &&*/ ashaObject.getApproval().equals("approved")) {
                            suspectObjects.add(suspectObject);
                            //}
                        }

                        mAdapter = new CustomAdapter(suspectObjects, getContext(), firestoreDB);
                        recyclerView.setAdapter(mAdapter);
                    }
                });

        //////////////////////
       // recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       // adapter = new CustomAdapter(names);

        //recyclerView.setAdapter(adapter);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });
        return view;
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<SuspectObject> filterdNames = new ArrayList<SuspectObject>();

        //looping through existing elements
        Log.d(TAG, String.valueOf(suspectObjects.size()));
        for (SuspectObject s : suspectObjects) {
            //if the existing elements contains the search input
            Log.d(TAG,s.toString());

                String uid = s.getUID().toLowerCase();
                if (uid.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }

        }
        //calling a method of the adapter class and passing the filtered list
        mAdapter.filterList(filterdNames);

    }
    private void loadNotesList() {
        firestoreDB.collection("SUSPECTS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ASHAObject> ashaObjectList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                ASHAObject ashaObject = doc.toObject(ASHAObject.class);
                                ashaObject.setId(doc.getId());
                                Log.d("asd",ashaObject.toString());
                              //  if(/*!(ashaObject.getType().equals("admin")) &&*/ ashaObject.getApproval().equals("approved")){
                                    ashaObjectList.add(ashaObject);
                               // }
                            }

                            mAdapter = new CustomAdapter(suspectObjects, getContext(), firestoreDB);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        firestoreListener.remove();
    }
}