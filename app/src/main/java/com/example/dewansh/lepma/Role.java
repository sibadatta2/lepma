package com.example.dewansh.lepma;
import com.google.firebase.firestore.Exclude;
public class Role {
    private String documentId;
    private String id;
    private String role;

    public Role() {
    }

    public Role(String documentId, String id, String role) {
        this.documentId = documentId;
        this.id = id;
        this.role = role;
    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}