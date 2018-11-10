package com.example.dewansh.lepma;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ApprovedRequests extends Fragment {
    private static final String TAG = "fragment1";

    private RecyclerView recyclerView;
    private approvedRecyclerAdapter mAdapter;

    private FirebaseFirestore firestoreDB;
    private ListenerRegistration firestoreListener;

    public ApprovedRequests() {
        // Required empty public constructor
    }

    public static ApprovedRequests newInstance(String param1, String param2) {
        ApprovedRequests fragment = new ApprovedRequests();
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
        View view = inflater.inflate(R.layout.fragment_approved_requests, container, false);
        recyclerView = view.findViewById(R.id.rvNoteList_approved);
        firestoreDB = FirebaseFirestore.getInstance();

        loadNotesList();

        firestoreListener = firestoreDB.collection("PROFILES")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, "Listen failed!", e);
                            return;
                        }

                        List<ASHAObject> ashaObjectList = new ArrayList<>();

                        for (DocumentSnapshot doc : documentSnapshots) {
                            ASHAObject ashaObject = doc.toObject(ASHAObject.class);
                            ashaObject.setId(doc.getId());
                            Log.d("asd",ashaObject.toString());
                            if(/*!(ashaObject.getType().equals("admin")) &&*/ ashaObject.getApproval().equals("approved")) {
                            ashaObjectList.add(ashaObject);
                             }
                        }

                        mAdapter = new approvedRecyclerAdapter(ashaObjectList, getContext(), firestoreDB);
                        recyclerView.setAdapter(mAdapter);
                    }
                });


        return view;
    }
    private void loadNotesList() {
        firestoreDB.collection("PROFILES")
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
                                if(/*!(ashaObject.getType().equals("admin")) &&*/ ashaObject.getApproval().equals("approved")){
                                ashaObjectList.add(ashaObject);
                                }
                            }

                            mAdapter = new approvedRecyclerAdapter(ashaObjectList, getContext(), firestoreDB);
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
