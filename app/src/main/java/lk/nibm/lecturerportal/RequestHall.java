package lk.nibm.lecturerportal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
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
import java.util.Calendar;

import lk.nibm.lecturerportal.Model.LecHallBooking;

public class RequestHall extends AppCompatActivity {

    private DatabaseReference ref;

    private String lecturerId=null, lechallId=null, lechallName=null, lmonth=null, ldate=null, stime=null, etime=null, stime2=null, etime2=null, bdate=null;
    private int mDate, mMonth, mYear, mb=0, eb=0, bb=0;

    private MaterialButton btnSave;
    private Button btnMorning, btnEvening;

    private TextView hallName, reqDate;
    private AutoCompleteTextView bacthName;

    private int morning=0, evening=0;

    Dialog myDialog, myDialog2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_request_hall );

        Intent i =getIntent();
        lechallId = i.getStringExtra( "lechallId" );
        lechallName = i.getStringExtra( "lechallName" );

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        lecturerId = firebaseUser.getUid();

        hallName = findViewById( R.id.txtLechall );
        reqDate = findViewById( R.id.reqdate );
        bacthName = (AutoCompleteTextView)findViewById( R.id.acbatchName );

        btnMorning = findViewById( R.id.btn1 );
        btnEvening = findViewById( R.id.btn2 );

        btnMorning.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morning++;
                if(morning> 0 && morning%2==1)
                {
                    btnMorning.setTextColor( Color.parseColor("#FFBB86FC") );
                }
                else
                {
                    btnMorning.setTextColor( Color.parseColor("#889AAF") );
                }
            }
        } );

        btnEvening.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evening++;
                if(evening> 0 && evening%2==1)
                {
                    btnEvening.setTextColor( Color.parseColor("#FFBB86FC") );
                }
                else
                {
                    btnEvening.setTextColor( Color.parseColor("#889AAF") );
                }
            }
        } );

        hallName.setText( lechallName );

        String[] batchNames = new String[5];
        batchNames[0]="HDSE21.1F"; batchNames[1]="DCSD21.2F"; batchNames[2]="DSE20.1F"; batchNames[3]="HDCBIS21.1F"; batchNames[4]="BSCCOMP20.2P";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,batchNames);

        bacthName.setAdapter(adapter);

        btnSave = findViewById( R.id.btnReqHall );

        btnSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref = FirebaseDatabase.getInstance().getReference( "LechallBooking" );

                checkDateAvailability();
            }
        } );

        reqDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar Cal = Calendar.getInstance();
                mDate = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestHall.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {

                        if(month+1<10)
                        {
                            lmonth = "0" + String.valueOf( month+1 );
                        }
                        else
                        {
                            lmonth = String.valueOf( month+1 );
                        }
                        if(date<10)
                        {
                            ldate = "0" + String.valueOf( date );
                        }
                        else
                        {
                            ldate = String.valueOf( date );
                        }

                        reqDate.setText(ldate+"-"+lmonth+"-"+year);

                    }
                },mYear,mMonth,mDate);
                datePickerDialog.show();
            }
        });
    }

    private void saveRecord()
    {
        ref = FirebaseDatabase.getInstance().getReference( "LechallBooking" );

        LecHallBooking book1 = new LecHallBooking(lechallId, lechallName, reqDate.getText().toString(), stime, etime, bacthName.getText().toString(), 0, lecturerId);

        String recId= ref.push().getKey();

        ref.child( recId ).setValue( book1 ).addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText( getApplicationContext(), "Lecture hall requested successfully", Toast.LENGTH_LONG ).show();
                showSuccessMsg("Lecture hall requested successfully");
            }
        } );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkDate()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        LocalDate day = LocalDate.parse(reqDate.getText().toString(), formatter);
        LocalDate today = LocalDate.now();

        if(day.compareTo(today)>=0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkDateAvailability()
    {
        String b_date = reqDate.getText().toString();
        ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child( "LechallBooking" ).orderByChild( "lechallId" ).equalTo( lechallId );
        bdate=null;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    stime2 = snapshot.child("startTime").getValue().toString();
                    etime2 = snapshot.child("endTime").getValue().toString();

                    bdate = snapshot.child("bookDate").getValue().toString();

                    if(bdate.equals( b_date ))
                    {
                        if(stime2.equals( "08:30" ) && etime2.equals( "15:30" ))
                        {
                            bb = 1;
                        }
                        else if(stime2.equals( "08:30" ) && etime2.equals( "11:30" ))
                        {
                            mb = 1;
                        }
                        else if(stime2.equals( "12:30" ) && etime2.equals( "15:30" ))
                        {
                            eb = 1;
                        }
                    }
                }
                //btnSave.setText( "" + bb + eb + mb );
                checkData();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkData()
    {
        if(TextUtils.isEmpty( lechallName ))
        {
            //Toast.makeText( getApplicationContext(), "Lecture hall name cannot be empty", Toast.LENGTH_LONG ).show();
            showErrorMsg("Lecture hall name cannot be empty");
        }
        else if(TextUtils.isEmpty(bacthName.getText().toString()))
        {
            //Toast.makeText( getApplicationContext(), "Please select student batch", Toast.LENGTH_LONG ).show();
            showErrorMsg("Please select student batch");
        }
        else if(TextUtils.isEmpty(reqDate.getText().toString()))
        {
            //Toast.makeText( getApplicationContext(), "Please select a date", Toast.LENGTH_LONG ).show();
            showErrorMsg("Please select a date");
        }
        else if(!checkDate())
        {
            //Toast.makeText( getApplicationContext(), "Please select an upcomming date", Toast.LENGTH_LONG ).show();
            showErrorMsg("Please select an upcomming date");
        }
        else if(morning==0 && evening==0)
        {
            //Toast.makeText( getApplicationContext(), "Please select a time slot", Toast.LENGTH_LONG ).show();
            showErrorMsg("Please select a time slot");
        }
        else
        {
            if((morning>0 && morning%2==1) && (evening>0 && evening%2==1))
            {
                if(bb>=1)
                {
                    //Toast.makeText( getApplicationContext(), "Lecture hall booked for both sessions", Toast.LENGTH_LONG ).show();
                    showErrorMsg("Lecture hall booked for both sessions");
                }
                else if(mb>=1)
                {
                    //Toast.makeText( getApplicationContext(), "Lecture hall booked for morning session", Toast.LENGTH_LONG ).show();
                    showErrorMsg("Lecture hall booked for morning session");
                }
                else if(eb>=1)
                {
                    //Toast.makeText( getApplicationContext(), "Lecture hall booked for evening session", Toast.LENGTH_LONG ).show();
                    showErrorMsg("Lecture hall booked for evening session");
                }
                else
                {
                    stime = "08:30";
                    etime = "15:30";
                    saveRecord();
                }
            }
            else if((morning>0 && morning%2==1) && (evening%2==0))
            {
                if(bb>=1 || mb>=1)
                {
                    //Toast.makeText( getApplicationContext(), "Lecture hall booked for morning session", Toast.LENGTH_LONG ).show();
                    showErrorMsg("Lecture hall booked for morning session");
                }
                else
                {
                    stime = "08:30";
                    etime = "11:30";
                    saveRecord();
                }
            }
            if((evening>0 && evening%2==1) && (morning%2==0))
            {
                if(bb>=1 || eb>=1)
                {
                    //Toast.makeText( getApplicationContext(), "Lecture hall booked for evening session", Toast.LENGTH_LONG ).show();
                    showErrorMsg("Lecture hall booked for evening session");
                }
                else
                {
                    stime = "12:30";
                    etime = "15:30";
                    saveRecord();
                }
            }
        }
    }

    private void showErrorMsg(String error)
    {
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.error_message);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));

        ImageView close = (ImageView) myDialog.findViewById(R.id.leClose);
        TextView value = (TextView) myDialog.findViewById( R.id.txtle );
        Button btnOk = (Button)myDialog.findViewById( R.id.msgOk );

        Window window = myDialog.getWindow();
        window.setGravity( Gravity.BOTTOM );

        window.setLayout( WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT );

        myDialog.getWindow().getAttributes().windowAnimations = R.style.Animation_Design_BottomSheetDialog;

        value.setText( error );

        myDialog.show();

        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        } );

        btnOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        } );
    }

    private void showSuccessMsg(String msg)
    {
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.success_message);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));

        ImageView close = (ImageView) myDialog.findViewById(R.id.leClose3);
        TextView value = (TextView) myDialog.findViewById( R.id.txtle3 );
        Button btnOk = (Button)myDialog.findViewById( R.id.msgOk3 );

        Window window = myDialog.getWindow();
        window.setGravity( Gravity.BOTTOM );

        window.setLayout( WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT );

        myDialog.getWindow().getAttributes().windowAnimations = R.style.Animation_Design_BottomSheetDialog;

        value.setText( msg );

        myDialog.show();

        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        } );

        btnOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        } );
    }
}