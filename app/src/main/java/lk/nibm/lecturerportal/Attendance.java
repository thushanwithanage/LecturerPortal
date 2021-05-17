package lk.nibm.lecturerportal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lk.nibm.lecturerportal.Adapter.HallAdapter;
import lk.nibm.lecturerportal.Model.AtdAttendance;
import lk.nibm.lecturerportal.Model.LectureHall;

public class Attendance extends AppCompatActivity {

    private DatabaseReference ref;

    private MaterialButton btnView;

    int dse=0, dcsd=0, hdse=0;

    String stdId=null, batch=null;

    final List<AtdAttendance> attendancesList = new ArrayList<>();

    final List<LectureHall> lectureHalls = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_attendance );

        btnView = findViewById( R.id.btnAttendance );

        LocalDate today = LocalDate.now();

        String[] date2 = String.valueOf(today).split("-");

        String cdate = date2[2] + "-" + date2[1] + "-" + date2[0];

        ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child( "Attendance" ).orderByChild( "rec_Date" ).equalTo( cdate );
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    stdId = snapshot.child("std_Id").getValue().toString();
                    setData(stdId);
                }

                for(AtdAttendance rec : attendancesList)
                {
                    if(rec.getBatchName().equals( "DSE" ))
                    {
                        dse++;
                    }
                    else if(rec.getBatchName().equals( "DCSD" ))
                    {
                        dcsd++;
                    }
                    else if(rec.getBatchName().equals( "HDSE" ))
                    {
                        hdse++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btnView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Attendance.this, ViewAttendance.class);
                i.putExtra( "dse", String.valueOf( dse ) );
                i.putExtra( "dcsd", String.valueOf( dcsd ) );
                i.putExtra( "hdse", String.valueOf( hdse ) );
                startActivity( i );
                finish();
            }
        } );
    }

    private void setData(String sId)
    {
        ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child( "Student" ).orderByChild( "std_Id" ).equalTo(Integer.parseInt(sId));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    batch = snapshot.child("std_Batch").getValue().toString();

                    AtdAttendance l1 = new AtdAttendance(sId, batch);
                    attendancesList.add( l1 );

                    LectureHall hall = new LectureHall();
                    hall.setHallId( "1" );
                    hall.setHallName( batch );
                    hall.setFloorNo( sId );
                    lectureHalls.add(hall);

                    if(batch.equals( "DSE" ))
                    {
                        dse++;
                    }
                    else if(batch.equals( "DCSD" ))
                    {
                        dcsd++;
                    }
                    else if(batch.equals( "HDSE" ))
                    {
                        hdse++;
                    }
                }

                for(AtdAttendance rec : attendancesList)
                {
                    if(rec.getBatchName().equals( "DSE" ))
                    {
                        dse++;
                    }
                    else if(rec.getBatchName().equals( "DCSD" ))
                    {
                        dcsd++;
                    }
                    else if(rec.getBatchName().equals( "HDSE" ))
                    {
                        hdse++;
                    }
                }
                btnView.setVisibility( View.VISIBLE );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}