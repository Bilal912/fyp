package com.fyp.job_clover.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.fyp.job_clover.Data_Classes.Messages;
import com.fyp.job_clover.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder> {
    private Context context;
    private ArrayList<Messages> list;
    private FirebaseAuth auth;
    private DatabaseReference userRef;

    public MessageAdapter(Context context, ArrayList<Messages> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_messages_layout,parent,false);

        auth = FirebaseAuth.getInstance();
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        String messageSenderId = auth.getCurrentUser().getUid();
        Messages messages = list.get(position);

        String fromuserid = messages.getFrom();
        String frommessagetype = messages.getType();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromuserid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("profile_image")){
                    String recimage = dataSnapshot.child("profile_image").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (frommessagetype.equals("text")){
            holder.recieverMessageText.setVisibility(View.INVISIBLE);


            if (fromuserid.equals(messageSenderId)){
                holder.senderMessageText.setBackgroundResource(R.drawable.sender_messages_layout);
                holder.senderMessageText.setText(messages.getMessage());
            }
            else {
                holder.senderMessageText.setVisibility(View.INVISIBLE);
                holder.recieverMessageText.setVisibility(View.VISIBLE);

                holder.recieverMessageText.setBackgroundResource(R.drawable.reciever_messages_layout);
                holder.recieverMessageText.setText(messages.getMessage());
            }
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
         TextView senderMessageText,recieverMessageText;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText = itemView.findViewById(R.id.sender_message_text);
            recieverMessageText = itemView.findViewById(R.id.reciever_message_text);

        }
    }
}
