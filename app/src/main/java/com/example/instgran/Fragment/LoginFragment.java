package com.example.instgran.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.instgran.Home;
import com.example.instgran.R;
//import com.example.instgran.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class LoginFragment extends Fragment {
    TextInputLayout et_EmailLog, et_passwordLog;
    Button bt_login;
    ProgressBar pb_wait;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //    private SessionManager session;
    String username = " ";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_passwordLog = view.findViewById(R.id.et_logPassword);
        et_EmailLog = view.findViewById(R.id.et_EmailLog);
        bt_login = view.findViewById(R.id.bt_login);
        pb_wait = view.findViewById(R.id.pb_wait);


        pb_wait.setVisibility(View.GONE);


        try {
            bt_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TODO", " Email 12312 " + et_EmailLog.getEditText().getText() + " Email 12312 ");

                    if (et_EmailLog.getEditText().getText().toString().isEmpty() || et_passwordLog.getEditText().getText().toString().isEmpty()) {
                        if (et_EmailLog.getEditText().getText().toString().isEmpty()) {
//                            Toast.makeText(getContext(), "Please, do not leave the email address field blank.", Toast.LENGTH_SHORT).show();

                            if (Locale.getDefault().getLanguage() == "en") {
                                et_EmailLog.setError("Please, do not leave the email address field blank.");
                                et_EmailLog.requestFocus();
                            } else {
                                et_EmailLog.setError("من فضلك ، لا تترك حقل عنوان بريد الألكتروني فارغا.");
                                et_EmailLog.requestFocus();
                            }

                        } else {
                            et_EmailLog.setError(null);
                        }


                        if (et_passwordLog.getEditText().getText().toString().isEmpty()) {
//                            Toast.makeText(getContext(), "Please, do not leave the password field blank.", Toast.LENGTH_SHORT).show();

                            if (Locale.getDefault().getLanguage() == "en") {
                                et_passwordLog.setError("Please, do not leave the password field blank.");
                                et_passwordLog.requestFocus();
                            } else {
                                et_passwordLog.setError("من فضلك ، لا تترك حقل كلمة السر فارغة.");
                                et_passwordLog.requestFocus();
                            }

                        } else {
                            et_passwordLog.setError(null);
                        }

                    } else {


                        pb_wait.setVisibility(View.VISIBLE);
                        Task<AuthResult> result = mAuth.signInWithEmailAndPassword(et_EmailLog.getEditText().getText().toString(), et_passwordLog.getEditText().getText().toString());
                        result.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pb_wait.setVisibility(View.GONE);


                                if (task.isSuccessful()) {
                                    Log.d("TODO", "signInWithEmailAndPassword Success " + task.getResult()
                                            .getUser().getDisplayName());
                                    Intent intent = new Intent(getContext(), Home.class);
//                                    session.setLogin(true);
                                    startActivity(intent);

                                } else {
                                    Log.d("TODO", "signInWithEmailAndPassword Failed " +
                                            task.getException().getLocalizedMessage());

                                    Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

            });
        } catch (Exception e) {

        }


    }


}
