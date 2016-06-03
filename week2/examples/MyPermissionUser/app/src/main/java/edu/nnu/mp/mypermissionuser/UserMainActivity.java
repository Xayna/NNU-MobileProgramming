package edu.nnu.mp.mypermissionuser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserMainActivity extends AppCompatActivity {

    public static final String TEST_ACION = "edu.nnu.mp.mypermission.TEST_ACTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        Button userPerm = (Button) findViewById(R.id.userPermission_btn);
        userPerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an implicit Intent using the Action String TEST_ACTION
                // Launch an Activity that can receive the Intent using Activity.startActivity()

                startActivity(new Intent(TEST_ACION));

            }
        });
    }
}
