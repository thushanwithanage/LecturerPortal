package lk.nibm.lecturerportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lk.nibm.lecturerportal.Adapter.HallAdapter;
import lk.nibm.lecturerportal.Adapter.LecturehallAdapter;
import lk.nibm.lecturerportal.Model.LecHallBooking;
import lk.nibm.lecturerportal.Model.LectureHall;

public class ViewHalls extends AppCompatActivity {

    private RecyclerView lectureHallRecycler;
    HallAdapter adapter;

    private DatabaseReference ref;

    final List<LectureHall> lectureHalls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_halls );

        ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child( "LectureHall" );
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    LectureHall hall = new LectureHall();
                    hall.setHallId( snapshot.child("L_hallno").getValue().toString() );
                    hall.setHallName( snapshot.child("L_hallname").getValue().toString() );
                    hall.setFloorNo( snapshot.child("L_floorno").getValue().toString() );
                    lectureHalls.add(hall);
                }
                Collections.sort(lectureHalls, LectureHall.FloorAescending);
                setUserRecycler(lectureHalls);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void setUserRecycler(List<LectureHall> lectureHallList)
    {
        lectureHallRecycler = findViewById(R.id.lechalls_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        lectureHallRecycler.setLayoutManager(layoutManager);
        adapter = new HallAdapter(this, lectureHallList);
        lectureHallRecycler.setAdapter(adapter);
    }
}