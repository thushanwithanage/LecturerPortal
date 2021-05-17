package lk.nibm.lecturerportal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lk.nibm.lecturerportal.Adapter.LecturehallAdapter;
import lk.nibm.lecturerportal.Model.LecHallBooking;

public class AssignedHalls extends AppCompatActivity {

    private RecyclerView lectureHallRecycler;
    LecturehallAdapter adapter;

    private DatabaseReference ref;

    private String lecturerId=null;

    private String hallId=null, hallName=null, batch=null, bdate=null, startTime=null, endTime=null;
    private int status=0;

    final List<LecHallBooking> hallList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_assigned_halls );

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        lecturerId = firebaseUser.getUid();

        ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child( "LechallBooking" ).orderByChild( "lecId" ).equalTo( lecturerId );
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    hallId = snapshot.child("lechallId").getValue().toString();
                    batch = snapshot.child("batchName").getValue().toString();
                    bdate = snapshot.child("bookDate").getValue().toString();
                    startTime = snapshot.child("startTime").getValue().toString();
                    endTime = snapshot.child("endTime").getValue().toString();
                    hallName = snapshot.child("lechallName").getValue().toString();

                    status = Integer.parseInt( snapshot.child("status").getValue().toString());

                    if(status == 1)
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
                        LocalDate day = LocalDate.parse(bdate, formatter);
                        LocalDate today = LocalDate.now();

                        LecHallBooking hall = new LecHallBooking();
                        hall.setLechallId( hallId );
                        hall.setLechallName(hallName);
                        hall.setBatchName( batch );
                        hall.setBookDate(bdate);
                        hall.setStartTime(startTime);
                        hall.setEndTime(endTime);

                        if(day.compareTo(today)>=0)
                        {
                            hallList.add(hall);
                        }
                    }
                }
                Collections.sort(hallList, LecHallBooking.DateAscending);
                setUserRecycler(hallList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void setUserRecycler(List<LecHallBooking> lectureHallList)
    {
        lectureHallRecycler = findViewById(R.id.assignedlechalls_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        lectureHallRecycler.setLayoutManager(layoutManager);
        adapter = new LecturehallAdapter(this, lectureHallList);
        lectureHallRecycler.setAdapter(adapter);
    }
}