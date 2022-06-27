package com.example.instgran.ImageMyApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.instgran.Model.ModelUser;
import com.example.instgran.ViewPager.ViewPagerLoginSignUp;
import com.example.instgran.databinding.ActivityImageUploadProfileUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Locale;


public class ImageUploadProfileUser extends AppCompatActivity {
    private static final int REQUEST_CODE = 100;
    ActivityImageUploadProfileUserBinding binding;
    Uri image;

//    String stringURI;
//    FirebaseDatabase mData;
//    DatabaseReference mRef;
//    SignUpFragment signUpFragment;
//    ModelUserSign userSign;

    StorageReference storageReference;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageUploadProfileUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.pbWaitImg.setVisibility(View.GONE);
        binding.btBackLogIn.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("users");
        storageReference = FirebaseStorage.getInstance().getReference().child("userImage");

        if (Locale.getDefault().getLanguage() == "en") {
            setTitle("Upload Image");
        } else {
            setTitle("رفع الصورة");
        }


        binding.btSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        binding.btUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image == null) {
                    if (Locale.getDefault().getLanguage() == "en") {
                        Toast.makeText(ImageUploadProfileUser.this, "Please Select Image Profile", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(ImageUploadProfileUser.this,
                                "يرجى اختيار صورة شخصية لك", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    binding.btBackLogIn.setVisibility(View.VISIBLE);
                    binding.pbWaitImg.setVisibility(View.VISIBLE);


                    uploadImage();
                }
            }
        });

        binding.btBackLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                String uriPhoto = image.toString();
//                Log.d("TODO ", uriPhoto + "this is uri ");
                Intent intent = new Intent(getApplicationContext(), ViewPagerLoginSignUp.class);
//                intent.putExtra("image",uriPhoto);
                startActivity(intent);

            }
        });

    }


    private void uploadImage() {

        storageReference.child(mUser.getUid()).putFile(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storageReference.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            HashMap hashMap = new HashMap();
                            Intent i = getIntent();
                            ModelUser modelUser = (ModelUser) i.getSerializableExtra("dataUser");
                            String nameUser = modelUser.getUserName();
                            String id = modelUser.getId();
                            hashMap.put("imageProfile", uri.toString());
                            hashMap.put("nameuser", nameUser);
                            hashMap.put("id", id);


                            mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    binding.pbWaitImg.setVisibility(View.GONE);
                                    if (Locale.getDefault().getLanguage() == "en") {
                                        Toast.makeText(ImageUploadProfileUser.this, "Operation accomplished successfully", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Toast.makeText(ImageUploadProfileUser.this,
                                                "تمت العملية بنجاح", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ImageUploadProfileUser.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }
                    });

                }
            }
        });

//        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        UploadTask uploadTask = FirebaseStorage.getInstance().getReference("userImage/" + userUid).putFile(image);
//
//
//
//        uploadTask
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                binding.pbWaitImg.setVisibility(View.GONE);
//                Toast.makeText(ImageUploadProfileUser.this, "Operation accomplished successfully", Toast.LENGTH_SHORT).show();
//
//            }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ImageUploadProfileUser.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });


    }


    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null && resultCode == RESULT_OK) {

            image = data.getData();
            binding.ivImgUser.setImageURI(image);


        }
    }

}