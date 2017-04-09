package com.developer.sparsh.baseapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.developer.sparsh.baseapplication.Adapters.InviteAdapter;
import com.developer.sparsh.baseapplication.Classes.AdminContact;

import java.util.ArrayList;

public class InviteActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView invite_recyclerview;
    InviteAdapter adapter = null;
    ArrayList<AdminContact> list = null;
    Button sendInvitaion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        invite_recyclerview = (RecyclerView) findViewById(R.id.invite_recycler_view);
        invite_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<AdminContact>();
        AdminContact adminContact = new AdminContact("ABC","123","a@gmil.com");
        list.add(adminContact);
        list.add(adminContact);
        list.add(adminContact);
        adapter = new InviteAdapter(list);
        invite_recyclerview.setAdapter(adapter);
        sendInvitaion = (Button) findViewById(R.id.send_invitation_button);
        sendInvitaion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        String appId = "kvnekrj";
//        int count = 0;
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).isSelected) {
//                count++;
//            }
//        }
//        Toast.makeText(this, count + "", Toast.LENGTH_SHORT).show();
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("appId",appId);
//            JSONArray invitedArray = new JSONArray();
//            for(int i=0;i<list.size();i++){
//                if(list.get(i).isSelected){
//                    JSONObject invitee = new JSONObject();
//                    invitee.put("name",list.get(i).name);
//                    invitee.put("number",list.get(i).number);
//                    invitee.put("email",list.get(i).email);
//                    invitedArray.put(invitee);
//                }
//            }
//            jsonObject.put("inviteeList",invitedArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d("!@#",jsonObject.toString());
//    }
    }
}
