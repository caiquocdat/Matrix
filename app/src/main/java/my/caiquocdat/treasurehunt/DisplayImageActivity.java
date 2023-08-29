package my.caiquocdat.treasurehunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import my.caiquocdat.treasurehunt.databinding.ActivityDisplayImageBinding;
import my.caiquocdat.treasurehunt.databinding.ActivityMainBinding;

public class DisplayImageActivity extends AppCompatActivity {
    private ActivityDisplayImageBinding activityDisplayImageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisplayImageBinding = ActivityDisplayImageBinding.inflate(getLayoutInflater());
        View view = activityDisplayImageBinding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("imageUri");
        Uri imageUri = Uri.parse(imageUriString);
    }
}