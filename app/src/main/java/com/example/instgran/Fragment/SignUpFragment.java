package com.example.instgran.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.instgran.ImageMyApp.ImageUploadProfileUser;
import com.example.instgran.Model.ModelUser;
import com.example.instgran.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Locale;

public class SignUpFragment extends Fragment {
    TextInputLayout et_username, et_EmailSign, et_passwordSign, et_confirmPassword;
    Button bt_nextSign;
    ProgressBar pb_waitSign;
    ModelUser data;
    String username;


//    onFragmentSetUsernameListener mListener = null;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();


//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof onFragmentSetUsernameListener) {
//            mListener = (onFragmentSetUsernameListener) context;
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_username = view.findViewById(R.id.et_username);
        et_EmailSign = view.findViewById(R.id.et_EmailSign);
        et_passwordSign = view.findViewById(R.id.et_passwordSign);
        bt_nextSign = view.findViewById(R.id.bt_nextSing);
        pb_waitSign = view.findViewById(R.id.pb_waitSign);
        et_confirmPassword = view.findViewById(R.id.et_confirmPasswordSign);

        pb_waitSign.setVisibility(View.GONE);

//        String title = "Sing UP";
//        Bundle data = new Bundle();
//        data.putString("dataTitleSign",title);
//        ViewPagerLoginSignUp activity = (ViewPagerLoginSignUp) getActivity();
//        activity.setTitleSign(data);


        bt_nextSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (et_username.getEditText().getText().toString().isEmpty() || et_EmailSign.getEditText().getText().toString().isEmpty() || et_passwordSign.getEditText().getText().toString().isEmpty()
                        || et_confirmPassword.getEditText().getText().toString().isEmpty() || !(et_passwordSign.getEditText().getText().toString().equals(et_confirmPassword.getEditText().getText().toString()))) {
                    if (et_username.getEditText().getText().toString().isEmpty()) {
//
//                        Toast.makeText(getContext(), "Please, do not leave the user name field blank.", Toast.LENGTH_SHORT).show();
                        if (Locale.getDefault().getLanguage() == "en") {
                            et_username.setError("Please, do not leave the user name field blank.");
                            et_username.requestFocus();
                        } else {
                            et_username.setError("من فضلك ، لا تترك حقل أسم المستخدم فارغا.");
                            et_username.requestFocus();
                        }

                    } else {
                        et_username.setError(null);

                    }


                    if (et_EmailSign.getEditText().getText().toString().isEmpty()) {
//                        Toast.makeText(getContext(), "Please, do not leave the email address field blank.", Toast.LENGTH_SHORT).show();
                        if (Locale.getDefault().getLanguage() == "en") {
                            et_EmailSign.setError("Please, do not leave the email address field blank.");
                            et_EmailSign.requestFocus();
                        } else {
                            et_EmailSign.setError("من فضلك ، لا تترك حقل عنوان بريد الألكتروني فارغا.");
                            et_EmailSign.requestFocus();
                        }

                        ;
                    } else {
                        et_EmailSign.setError(null);
                    }


                    if (et_passwordSign.getEditText().getText().toString().isEmpty()) {
//                        Toast.makeText(getContext(), "Please, do not leave the password field blank.", Toast.LENGTH_SHORT).show();

                        if (Locale.getDefault().getLanguage() == "en") {
                            et_passwordSign.setError("Please, do not leave the password field blank.");
                            et_passwordSign.requestFocus();
                        } else {
                            et_passwordSign.setError("من فضلك ، لا تترك حقل كلمة السر فارغة.");
                            et_passwordSign.requestFocus();
                        }

                    } else {
                        et_passwordSign.setError(null);
                    }


                    if (et_confirmPassword.getEditText().getText().toString().isEmpty()) {
//                        Toast.makeText(getContext(), "Please, do not leave the password field blank.", Toast.LENGTH_SHORT).show();

                        if (Locale.getDefault().getLanguage() == "en") {
                            et_confirmPassword.setError("Please, do not leave the confirm password field blank.");
                            et_confirmPassword.requestFocus();
                        } else {
                            et_confirmPassword.setError("من فضلك ، لا تترك حقل تأكيد كلمة السر  فارغة.");
                            et_confirmPassword.requestFocus();
                        }

                    } else {
                        et_confirmPassword.setError(null);
                    }


                    if (!(et_confirmPassword.getEditText().toString().equals(et_passwordSign.getEditText().toString()))) {
//                        Toast.makeText(getContext(), "Confirm password does not match", Toast.LENGTH_SHORT).show();


                        if (Locale.getDefault().getLanguage() == "en") {
                            et_confirmPassword.setError("Confirm password does not match");
                            et_confirmPassword.requestFocus();
                        } else {
                            et_confirmPassword.setError("تأكيد كلمة المرور، لا نتطابق.");
                            et_confirmPassword.requestFocus();
                        }


                    } else {
                        et_confirmPassword.setError(null);

                    }

                } else {


                    pb_waitSign.setVisibility(View.VISIBLE);
                    username = et_username.getEditText().getText().toString();
                    Task<AuthResult> result = mAuth.createUserWithEmailAndPassword(et_EmailSign.getEditText().getText().toString(), et_passwordSign.getEditText().getText().toString());

                    result.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()

                                        .setDisplayName(username).build();

                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("TODO", "User profile updated.");
                                        }
                                    }
                                });
                            }

                        }
                    });


//                    if (mListener != null) {
//                        data = new Model();
//                        String username = et_username.getText().toString();
//                        Log.d("TODO", "The user name is 1 " + et_username.getText().toString());
//                        data.setUserName(username);
//                        mListener.onFragmentSetUsername(data);
//                    }`

                    result.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            pb_waitSign.setVisibility(View.GONE);
                            Intent intent = new Intent(getContext(), ImageUploadProfileUser.class);
                            data = new ModelUser();
                            data.setUserName(et_username.getEditText().getText().toString());
                            String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            data.setId(userUid);
                            intent.putExtra("dataUser", data);
                            startActivity(intent);


                        }
                    });

                    result.addOnFailureListener(OnFailureListener);

                }
            }
        });


    }


    private OnFailureListener OnFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            pb_waitSign.setVisibility(View.GONE);

            Log.d("TODO", "OnFailureListener : " + e.getLocalizedMessage());
            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    };


//    public interface onFragmentSetUsernameListener {
//        public void onFragmentSetUsername(Model model);
//    }


}
