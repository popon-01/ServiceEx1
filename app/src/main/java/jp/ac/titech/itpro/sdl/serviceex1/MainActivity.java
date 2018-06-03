package jp.ac.titech.itpro.sdl.serviceex1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.StringBufferInputStream;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private BroadcastReceiver bcReceiver;
    private IntentFilter bcFilter;

    public final static String ACTION_ANSWER = "jp.ac.titech.itpro.sdl.serviceex1.action.ANSWER";
    public final static String EXTRA_ANSWER = "jp.ac.titech.itpro.sdl.serviceex1.extra.ANSWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate in " + Thread.currentThread());
        setContentView(R.layout.activity_main);

        bcReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d(TAG, "onReceive: " + action);
                if (action == null) return;
                switch (action) {
                    case ACTION_ANSWER :
                        String result = intent.getStringExtra(EXTRA_ANSWER);
                        Log.d(TAG, String.format("receive result in %s : %s",
                                Thread.currentThread(), result));
                        popToast(result);
                }
            }
        };

        bcFilter = new IntentFilter(ACTION_ANSWER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        registerReceiver(bcReceiver, bcFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        unregisterReceiver(bcReceiver);
    }

    public void pressTest1(View v) {
        testService1();
    }

    public void pressTest2(View v) {
        testService2();
    }

    public void pressTest3(View v) { testService3(); }

    public void popToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void testService1() {
        Log.d(TAG, "testService1 in " + Thread.currentThread());
        Intent intent = new Intent(this, TestService1.class);
        intent.putExtra(TestService1.EXTRA_MYARG, "Hello, Service1");
        startService(intent);
    }

    private void testService2() {
        Log.d(TAG, "testService2 in " + Thread.currentThread());
        Intent intent = new Intent(this, TestService2.class);
        intent.putExtra(TestService2.EXTRA_MYARG, "Hello, Service2");
        startService(intent);
    }

    private void testService3(){
        Log.d(TAG, "testService3 in " + Thread.currentThread());
        Intent intent = new Intent(this, TestService3.class);
        String myArg3 = "PING";
        intent.putExtra(TestService3.EXTRA_MYARG, myArg3);
        popToast(myArg3);
        startService(intent);
    }

}
