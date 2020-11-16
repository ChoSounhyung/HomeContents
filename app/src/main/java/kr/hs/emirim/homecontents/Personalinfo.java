package kr.hs.emirim.homecontents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


public class Personalinfo extends AppCompatActivity {

    private Button personalinfo_button;
    private CheckBox personalinfo_checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);

        init();
        setUp();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void init() {
        personalinfo_button = findViewById(R.id.personalinfo_button);
        personalinfo_checkbox = findViewById(R.id.personalinfo_checkbox);
    }

    private void setUp() {
        personalinfo_button.setOnClickListener(goSignupPAge);
    }

    View.OnClickListener goSignupPAge = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (personalinfo_checkbox.isChecked()) {
                startFirstActivity();
            } else {   //체크 박스가 해제 된 경우
                Toast.makeText(getApplicationContext(), "체크박스를 체크해주세요", Toast.LENGTH_LONG).show();
            }


        }
    };

    private void startFirstActivity() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}