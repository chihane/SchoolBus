package chihane.schoolbus.demo;

import android.os.Bundle;
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
        SchoolBus.getDefault().register(this);
    }

    public void onClick(View view) {
        Log.d(TAG, "Posting thread id: " + Thread.currentThread().getId() + "");
        SchoolBus.getDefault().post(new DefaultEvent("testtesttest"));
//        if (SchoolBus.getDefault().isRegistered(MainActivity.this)) {
//            SchoolBus.getDefault().unregister(MainActivity.this);
//        } else {
//            Toast.makeText(MainActivity.this, "unregistered", Toast.LENGTH_SHORT).show();
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DefaultEvent event) {
        Log.d(TAG, "Subscriber thread id: " + Thread.currentThread().getId() + "");
        Toast.makeText(MainActivity.this, event.message, Toast.LENGTH_SHORT).show();
    }
}
