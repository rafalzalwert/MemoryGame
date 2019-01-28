package com.example.zalwe.memorygame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String SIZE = "MainActivity.SIZE";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    Button buttonTakePicture;
    Button buttonAdd;
    Button buttonHistory;
    Button buttonPlay;
    ImageView imageView;
    List<Image> imageArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper dbHelper = new DbHelper(getBaseContext());
        final DbOperations dbOperations = new DbOperations(dbHelper);
        imageArrayList = dbOperations.getImageFromDb();
        buttonTakePicture = findViewById(R.id.buttonTakePicture);
        buttonAdd = findViewById(R.id.buttonAdd);
        imageView = findViewById(R.id.imageView);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonHistory = findViewById(R.id.buttonHistory);



        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent();
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePicture(dbOperations);
            }
        });
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSavedPicture();
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageArrayList = dbOperations.getImageFromDb();
                int size = imageArrayList.size();
                startGameActivity(size);
            }
        });
    }

    private void savePicture(DbOperations dbOperations) {
        byte[] image = getImageInBytes();
        imageView.setDrawingCacheEnabled(false);
        dbOperations.putDataIntoSqliteDb(image);
        dbOperations.putDataIntoSqliteDb(image);
        Toast.makeText(MainActivity.this,"Image saved", Toast.LENGTH_LONG).show();
    }

    private byte[] getImageInBytes() {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void showSavedPicture(){
        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(intent);
    }

    private void takePictureIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView.setMaxWidth(200);
            imageView.setImageBitmap(thumbnail);

        }
    }

    private void startGameActivity(int columns){
        Intent intent = new Intent(MainActivity.this,GameActivity.class);

        intent.putExtra(SIZE,columns);

        startActivity(intent);
    }
}
