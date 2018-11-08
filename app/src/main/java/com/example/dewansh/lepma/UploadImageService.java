package com.example.dewansh.lepma;


import android.graphics.Bitmap;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
public class UploadImageService {

    private UploadTask uploadTask;

     FirebaseStorage storage;
     StorageReference storageRef;
     StorageReference imageRef;

    public UploadImageService() {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://lepma-7baf7.appspot.com");
        imageRef = storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");
    }

    public UploadTask uploadImage(Bitmap bitmap, String ext) {
        uploadTask = imageRef.putBytes(getImageByteArray(bitmap, ext));
        return uploadTask;
    }

    public UploadTask uploadImage(byte[] data) {
        uploadTask = imageRef.putBytes(data);
        return uploadTask;
    }

    public static byte[] getImageByteArray(Bitmap bitmap, String ext) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if(ext.equals("PNG"))
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        else
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        return outputStream.toByteArray();
    }

    public Task<Void> deleteImage() {
        return imageRef.delete();
    }
}
