package lk.nibm.lecturerportal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ActivityColombo extends AppCompatActivity {

    private ImageView gfloor, ffloor, sfloor, tflorr, frfloor;
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

        setContentView( R.layout.activity_colombo );

        gfloor = findViewById( R.id.gfloor );
        ffloor = findViewById( R.id.firstfloor );
        sfloor = findViewById( R.id.secondfloor );
        tflorr = findViewById( R.id.thirdfloor );
        frfloor = findViewById( R.id.forthfloor );

        gfloor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( ActivityColombo.this, GroundFloor.class );
                startActivity( i );
            }
        } );

        ffloor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( ActivityColombo.this, FirstFloor.class );
                startActivity( i );
            }
        } );

        sfloor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( ActivityColombo.this, SecondFloor.class );
                startActivity( i );
            }
        } );

        tflorr.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( ActivityColombo.this, ThirdFloor.class );
                startActivity( i );
            }
        } );

        frfloor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( ActivityColombo.this, ForthFloor.class );
                startActivity( i );
            }
        } );

    }
}