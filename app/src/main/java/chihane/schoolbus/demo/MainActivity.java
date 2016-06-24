package chihane.schoolbus.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import chihane.schoolbus.SchoolBus;
import chihane.schoolbus.Subscribe;
import chihane.schoolbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "schoolbus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Log.d(TAG, "Posting thread id: " + Thread.currentThread().getId() + "");
        SchoolBus.getDefault().postSticky(new DefaultEvent("testtesttest"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SchoolBus.getDefault().register(MainActivity.this);
            }
        }, 1000);
//        if (SchoolBus.getDefault().isRegistered(MainActivity.this)) {
//            SchoolBus.getDefault().unregister(MainActivity.this);
//        } else {
//            Toast.makeText(MainActivity.this, "unregistered", Toast.LENGTH_SHORT).show();
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(DefaultEvent event) {
        Log.d(TAG, "Subscriber thread id: " + Thread.currentThread().getId() + "");
        Toast.makeText(MainActivity.this, event.message, Toast.LENGTH_SHORT).show();
    }
}
