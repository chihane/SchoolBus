package chihane.schoolbus.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import chihane.schoolbus.SchoolBus;
import chihane.schoolbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SchoolBus.getDefault().register(this);
    }

    public void onClick(View view) {
        SchoolBus.getDefault().post(new DefaultEvent("testtesttest"));
        if (SchoolBus.getDefault().isRegistered(this)) {
            SchoolBus.getDefault().unregister(this);
        } else {
            Toast.makeText(MainActivity.this, "unregistered", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onEvent(DefaultEvent event) {
        Toast.makeText(MainActivity.this, event.message, Toast.LENGTH_SHORT).show();
    }
}
