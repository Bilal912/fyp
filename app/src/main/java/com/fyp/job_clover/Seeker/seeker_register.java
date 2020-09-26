package com.fyp.job_clover.Seeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.R;
import com.fyp.job_clover.Data_Classes.Seeker_Reg_Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.elasticviews.ElasticButton;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class seeker_register extends AppCompatActivity {

    LinearLayout countrypicker;
    TextView phoneCode;
    ImageView flg;
    ElasticButton Register;
    EditText phone;
    RadioGroup radioGroup;
    String gender = null;
    Spinner spinner;
    ArrayList<String> qualification;
    TextInputEditText Name,Email,Password,Address;
    private DatabaseReference reference;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_register);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Seeker_Data");


        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Address = findViewById(R.id.address);

        qualification = new ArrayList<String>();
        spinner = findViewById(R.id.spinner_id);
        radioGroup=findViewById(R.id.gender_radio);
        Register = findViewById(R.id.register);
        countrypicker=findViewById(R.id.countrypicker);
        phoneCode=findViewById(R.id.phone_code);
        flg=findViewById(R.id.flag);
        phone=findViewById(R.id.phone);

        qualification.add("Matric");
        qualification.add("Inter");
        qualification.add("Graduation");
        qualification.add("Masters");
        qualification.add("Other");

        spinner.setAdapter(new ArrayAdapter<String>(seeker_register.this, android.R.layout.simple_dropdown_item_1line, qualification));

        countrypicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        phoneCode.setText(dialCode);
                        flg.setImageResource(flagDrawableResID);
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.male:
                        gender="Male";
                        break;
                    case  R.id.female:
                        gender="Female";
                        break;
                    case R.id.others:
                        gender="Others";
                        break;
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             if (TextUtils.isEmpty(Name.getText().toString())){
                    Toast.makeText(seeker_register.this, "Name is required", Toast.LENGTH_SHORT).show();
             }
             else if (TextUtils.isEmpty(Email.getText().toString())){
                 Toast.makeText(seeker_register.this, "Email is required", Toast.LENGTH_SHORT).show();
             }
             else if (TextUtils.isEmpty(Address.getText().toString())){
                 Toast.makeText(seeker_register.this, "Address is required", Toast.LENGTH_SHORT).show();
             }
             else if (TextUtils.isEmpty(phone.getText().toString())){
                 Toast.makeText(seeker_register.this, "Phone Number is required", Toast.LENGTH_SHORT).show();
             }
             else if (TextUtils.isEmpty(Password.getText().toString())){
                 Toast.makeText(seeker_register.this, "Password is required", Toast.LENGTH_SHORT).show();
             }
             else if (radioGroup.getCheckedRadioButtonId() == -1){
                 Toast.makeText(seeker_register.this,"Select Gender", Toast.LENGTH_SHORT).show();
             }
             else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
                 Email.setError("Email is not Valid");
                 Email.requestFocus();
             }

             else {

                 final android.app.AlertDialog loading = new ProgressDialog(seeker_register.this);
                 loading.setMessage("Registering...");
                 loading.show();

                 final String email = Email.getText().toString();
                 final String password = Password.getText().toString();

                 auth.createUserWithEmailAndPassword(email,password)
                         .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                             @Override
                             public void onSuccess(AuthResult authResult) {

                                 Seeker_Reg_Data srd = new Seeker_Reg_Data(Name.getText().toString()
                                         ,Email.getText().toString()
                                         ,spinner.getSelectedItem().toString()
                                         ,Address.getText().toString()
                                         ,phoneCode.getText().toString().concat(phone.getText().toString())
                                         ,Password.getText().toString()
                                         ,gender);

                                 reference.child(auth.getCurrentUser().getUid())
                                         .setValue(srd).addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                                         Toast.makeText(seeker_register.this, "Register Successfully.", LENGTH_SHORT).show();
                                         loading.dismiss();
                                         finish();
                                     }
                                 })
                                         .addOnFailureListener(new OnFailureListener() {
                                             @Override
                                             public void onFailure(@NonNull Exception e) {
                                                 loading.dismiss();
                                                 Toast.makeText(seeker_register.this, e.toString(), LENGTH_SHORT).show();
                                             }
                                         });
                             }
                         })
                         .addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 loading.dismiss();
                                 Toast.makeText(seeker_register.this, e.toString(), LENGTH_SHORT).show();
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
