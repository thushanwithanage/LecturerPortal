package lk.nibm.lecturerportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    Dialog myDialog;

    private EditText txtemail, txtpass;
    private ImageView btnLogin;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    String email = null;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

        setContentView( R.layout.activity_login );

        txtemail = findViewById( R.id.lecId );
        txtpass = findViewById( R.id.lecPass );
        btnLogin = findViewById( R.id.signin );

        auth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btnLogin.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                email = txtemail.getText().toString();
                String pass = txtpass.getText().toString();

                if(TextUtils.isEmpty( email ) || !email.matches(emailPattern) ){
                    //Toast.makeText( LoginActivity.this, "Invalid email address", Toast.LENGTH_LONG ).show();
                    showErrorMsg("Invalid email address");}

                else if(TextUtils.isEmpty( email ) || TextUtils.isEmpty( pass ) ){
                    //Toast.makeText( LoginActivity.this, "Password field cannot be empty", Toast.LENGTH_LONG ).show();
                    showErrorMsg("Please enter password");}

                else
                {
                    auth.signInWithEmailAndPassword( email, pass ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent i = new Intent( LoginActivity.this, MainActivity.class );
                                startActivity( i );
                                finish();
                            }
                            else
                            {
                                //Toast.makeText( LoginActivity.this, "Login failed ", Toast.LENGTH_LONG ).show();
                                showErrorMsg("Login Failed");
                            }
                        }
                    } );
                }
            }
        } );
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
}