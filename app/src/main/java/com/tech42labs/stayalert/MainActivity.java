package com.tech42labs.stayalert;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tech42labs.stayalert.Utils.Permissions;

import com.tech42labs.stayalert.fragments.AlertFragment;
import com.tech42labs.stayalert.fragments.IncidentReportFragment;
import com.tech42labs.stayalert.fragments.TimerFragment;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_PHOTO = 1;
    Button selectPhoto , startTimer , stopTimer;
    ImageView incidentPhoto;
    TextView timerText;
    String userChoosenTask;

    long startTime = 0L;
    long timeInMilliSeconds = 0L;
    long timeSwap = 0L;
    long updatedTime = 0L;
    Handler customHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*TimerFragment timerFragment = new TimerFragment();
        IncidentReportFragment incidentReportFragment = new IncidentReportFragment();

        FragmentManager manager = getSupportFragmentManager();


        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.container_timer , timerFragment);
        transaction.add(R.id.container_incident , incidentReportFragment);

        transaction.commit();*/





        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        timerText = (TextView) findViewById(R.id.textClock);

        startTimer = (Button) findViewById(R.id.btn_start);
        stopTimer = (Button) findViewById(R.id.btn_stop);


        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimer , 0);
            }
        });

        stopTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwap+= timeInMilliSeconds;
                customHandler.removeCallbacks(updateTimer);

            }
        });

        incidentPhoto = (ImageView) findViewById(R.id.reportPic);
        selectPhoto = (Button) findViewById(R.id.photo);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds =SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwap + timeInMilliSeconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hrs = mins / 60;
                    secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerText.setText("" + hrs + ":" + mins + ":" + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }
    };

    private void takePhoto() {

        final CharSequence[] items = { "Take a Photo", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                boolean result = Permissions.checkPermission(MainActivity.this);

                switch (position)
                {
                    case 0:
                        if(result) {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, REQUEST_CAMERA);
                        }
                        break;
                    case 1:
                        if(result) {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            photoPickerIntent.setType("image/*");
                            photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                        }
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == SELECT_PHOTO) {
                incidentPhoto.setImageURI(data.getData());
            } else if (requestCode == REQUEST_CAMERA) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                incidentPhoto.setImageBitmap(photo);
            }
        }
    }

    private String getFilePath(Intent data) {
        String imagePath;
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getParent().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imagePath = cursor.getString(columnIndex);
        cursor.close();

        return imagePath;

    }



    private void onGalleryResult(Intent data) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent newIntent = new Intent(this , AuthorizationActivity.class);
            startActivity(newIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
