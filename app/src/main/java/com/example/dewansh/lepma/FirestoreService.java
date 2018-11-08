package com.example.dewansh.lepma;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FirestoreService {

    private static FirestoreService firestoreService = null;
    private static FirebaseFirestore firebaseFirestore = null;
    private static Map<String, Object> user = null;

    private FirestoreService() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public static FirestoreService getInstance() {
        if(firestoreService == null)
            firestoreService = new FirestoreService();
        return firestoreService;
    }

    public Task<DocumentReference> storeUser(ASHAObject user) {
        return firebaseFirestore.collection("PENDING").add(user);
    }

    public Task<QuerySnapshot> getCurrentUser() {
        CollectionReference users = firebaseFirestore.collection("users");
        Query query = users.whereEqualTo("id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        return query.get();
    }

    public static void reset() {
        firestoreService = null;
        firebaseFirestore = null;
        user = null;
    }

    public static void setUser(Map<String, Object> data) {
        user = data;
    }

    public static Map<String, Object> getUser() {
        return user;
    }

    public static void changeField(String field, String value) {
        user.put(field, value);
    }
}