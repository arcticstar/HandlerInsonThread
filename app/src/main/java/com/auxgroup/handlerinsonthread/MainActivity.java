package com.auxgroup.handlerinsonthread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private static final String UPPER_NUM="upper";
    private EditText et;
    private CalThread callthread;

    class CalThread extends  Thread{
        public Handler mhandler;
        public void run(){
            Looper.prepare();
            mhandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 0x124) {
                        int upper=msg.getData().getInt(UPPER_NUM);
                        List<Integer>nums=new ArrayList<>();
                        outer:
                        for (int i = 0; i <=upper ; i++) {
                            for (int j = 2; j <Math.sqrt(i) ; j++) {
                                if (i !=2&&i%j==0) {
                                    continue outer;
                                }
                            }
                            nums.add(i);
                        }
                        Toast.makeText(MainActivity.this,nums.toString(),Toast.LENGTH_SHORT).show();
                    }

                }
            };
            Looper.loop();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et= (EditText) findViewById(R.id.etNum);
        callthread=new CalThread();
        callthread.start();


    }
    public void cal(View source){
        Message msg=new Message();
        msg.what=0x124;
        Bundle bundle=new Bundle();
        bundle.putInt(UPPER_NUM,Integer.parseInt(et.getText().toString()));
        msg.setData(bundle);
        callthread.mhandler.sendMessage(msg);

    }
}
