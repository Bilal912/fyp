package com.fyp.job_clover.Employer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Adapter.MessageAdapter;
import com.fyp.job_clover.Data_Classes.Messages;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmpChatActivity extends AppCompatActivity {
    private String messageRecieverId,messageRecieverName,messageRecieverImage,messSenId;
    private TextView username,userlastseen;
    private CircleImageView userimage;
    private Toolbar chat_toolbar;
    private ImageButton sendMessage, back;
    private EditText inputMessage;
    private FirebaseAuth auth;
    private DatabaseReference RootRef, Rotref;
    private ArrayList<Messages> list;
    private MessageAdapter myAdapter;
    private RecyclerView messagerecyclerlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_chat);
        auth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        Rotref = FirebaseDatabase.getInstance().getReference();


//        messSenId=auth.getCurrentUser().getUid();


        messSenId = auth.getCurrentUser().getUid();


        messageRecieverId =  getIntent().getStringExtra("sek_id");
        Toast.makeText(this,  messSenId + messageRecieverId, Toast.LENGTH_SHORT).show();
        messageRecieverName = getIntent().getStringExtra("name");
        //      messageRecieverImage = getIntent().getExtras().get("visit_image").toString();

        userimage = findViewById(R.id.image);
        username = findViewById(R.id.chatname);
        inputMessage = findViewById(R.id.text_content);
        sendMessage = findViewById(R.id.btn_send);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        messagerecyclerlist = findViewById(R.id.chatrecyclerView);
        list = new ArrayList<>();
        messagerecyclerlist.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MessageAdapter(this,list);
        messagerecyclerlist.setAdapter(myAdapter);

        username.setText(messageRecieverName);
//        Picasso.get().load(messageRecieverImage).placeholder(R.drawable.profile_image).into(userimage);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
                inputMessage.setText("");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        list.clear();
        Rotref.child("Messages").child(messSenId).child(messageRecieverId)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        Messages messages = dataSnapshot.getValue(Messages.class);
                        list.add(messages);
                        myAdapter.notifyDataSetChanged();

                        messagerecyclerlist.smoothScrollToPosition(messagerecyclerlist.getAdapter().getItemCount());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void SendMessage()
    {

        String messageText = inputMessage.getText().toString();
        if (TextUtils.isEmpty(messageText)){
            Toast.makeText(this, "Please write message first", Toast.LENGTH_SHORT).show();
        }
        else {
            String messageSenderRef = "Messages/" + messSenId + "/" + messageRecieverId;
            String messageRecieverRef = "Messages/" + messageRecieverId + "/" + messSenId;

            DatabaseReference userMessageKeyRef = RootRef.child("Messages")
                    .child(messSenId).child(messageRecieverId).push();

            String messagePushID = userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message",messageText);
            messageTextBody.put("type","text");
            messageTextBody.put("from",messSenId);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef + "/" + messagePushID , messageTextBody);
            messageBodyDetails.put(messageRecieverRef + "/" + messagePushID , messageTextBody);

            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Message Sent Successfully ....", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Message Not Sent ....", Toast.LENGTH_SHORT).show();
                    }
                    //inputMessage.setText("");
                }
            });
        }
    }

}
