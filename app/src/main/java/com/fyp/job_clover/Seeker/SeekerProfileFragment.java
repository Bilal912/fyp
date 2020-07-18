package com.fyp.job_clover.Seeker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Data_Classes.Seeker_Reg_Data;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeekerProfileFragment extends Fragment {
    private TextView email,password;
    private EditText name,phone,qualification,gender,address;
    private String sname,semail,sphone,squalification,sgender,saddress,spassword;
    private ElasticButton updateProfile;
    private DatabaseReference reference,databaseReference;
    private FirebaseAuth auth;

    public SeekerProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_seeker_profile, container, false);

        auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Seeker_Data").child(uid);
        databaseReference = FirebaseDatabase.getInstance().getReference("Seeker_Data").child(uid);


        name = v.findViewById(R.id.seeker_profile_name);
        email = v.findViewById(R.id.seeker_profile_email);
        phone = v.findViewById(R.id.seeker_profile_phone);
        qualification = v.findViewById(R.id.seeker_profile_qualification);
        gender = v.findViewById(R.id.seeker_profile_gender);
        address = v.findViewById(R.id.seeker_profile_address);
        password = v.findViewById(R.id.seeker_profile_password);
        updateProfile = v.findViewById(R.id.seeker_update_profile_btn);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Seeker_Reg_Data srd = dataSnapshot.getValue(Seeker_Reg_Data.class);
                    name.setText(srd.seeker_name);
                    email.setText(srd.seeker_email);
                    phone.setText(srd.seeker_phone);
                    qualification.setText(srd.seeker_qualification);
                    gender.setText(srd.seeker_gender);
                    address.setText(srd.seeker_address);
                    password.setText(srd.seeker_password);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sname = name.getText().toString();
                semail = email.getText().toString();
                sphone = phone.getText().toString();
                squalification = qualification.getText().toString();
                sgender = gender.getText().toString();
                saddress = address.getText().toString();
                spassword = password.getText().toString();

                Seeker_Reg_Data sd = new Seeker_Reg_Data(sname,semail,squalification,saddress,sphone,spassword,sgender);


                databaseReference.setValue(sd).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return v;
    }

}
