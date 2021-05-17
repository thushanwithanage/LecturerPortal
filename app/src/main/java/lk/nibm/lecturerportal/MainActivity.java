package lk.nibm.lecturerportal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView assignedHalls, requestHall, studentAttendance, floorMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Hide Status Bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView( R.layout.activity_main );

        assignedHalls = findViewById( R.id.assignedhalls );
        requestHall = findViewById( R.id.requesthall );
        studentAttendance = findViewById( R.id.studentattendance );
        floorMap = findViewById( R.id.floormap );

        assignedHalls.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AssignedHalls.class);
                startActivity( i );
            }
        } );

        requestHall.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewHalls.class);
                startActivity( i );
            }
        } );

        studentAttendance.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Attendance.class);
                startActivity( i );
                finish();
            }
        } );

        floorMap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapHome.class);
                startActivity( i );
            }
        } );

    }
}