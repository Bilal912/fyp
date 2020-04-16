package com.fyp.job_clover.Employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.job_clover.R;
import com.fyp.job_clover.Seeker.seeker_forget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.skydoves.elasticviews.ElasticButton;

public class emp_forget extends AppCompatActivity {
    EditText editText;
    ElasticButton button;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_forget);

        auth = FirebaseAuth.getInstance();

        button = findViewById(R.id.reset);
        editText = findViewById(R.id.email);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editText.getText().toString())){
                    Toast.makeText(emp_forget.this, "Email is required", Toast.LENGTH_SHORT).show();
                }
                else {

                    String Email = editText.getText().toString();
                    auth.sendPasswordResetEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(emp_forget.this, "Email Sent", Toast.LENGTH_SHORT).show();

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(emp_forget.this,  e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }

    public void back(View view) {
        finish();
    }
}
