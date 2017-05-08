package com.tech42labs.stayalert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by mari on 3/20/17.
 */

public class AuthorizationActivity extends Activity {


    CheckBox checkBox;

    Button submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
        checkBox = (CheckBox) findViewById(R.id.terms);
        submit = (Button) findViewById(R.id.submit);
        String checkBoxText = "I Agree the <a href='http://www.redbus.in/mob/mTerms.aspx' > Terms and Conditions</a>";

        checkBox.setText(Html.fromHtml(checkBoxText));
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorizationActivity.this , MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext() , "Registered Successfully" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
