package com.example.a01.house;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Invite extends AppCompatActivity {
    String cname,orderguid,code,id;
    ImageButton imageButton;
    TextView txv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        cname=Log.name;
        orderguid=getIntent().getStringExtra("orderguid");
        code=getIntent().getStringExtra("code");
        id=getIntent().getStringExtra("store_id");
        imageButton=findViewById(R.id.imgv_share);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(Intent.ACTION_SEND);
                //ComponentName cn = new ComponentName("jp.naver.line.android", "jp.naver.line.android.activity.selectchat.SelectChatActivity");
                myIntent.setType("text/plain");
                String body="你的朋友:"+cname+"\n邀請你加入訂單:"+orderguid+"\n輸入眾樂樂code:"+code+"\n一起訂餐吧!!";
                String sub="Your subject here";
                //myIntent.setComponent(cn);
                //myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent,"邀請好友"));
            }
        });
        txv=findViewById(R.id.order);
        txv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Invite.this, OrderPage.class);
                Bundle bundle=new Bundle();
                bundle.putString("store_id",id);
                bundle.putString("orderguid",orderguid);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}
