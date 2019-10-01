package com.wafihasan.wafers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class DetailsActivity extends AppCompatActivity
{

    public WallpaperManager wallpaperManager;
    public Button wallpaperButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String hiresURL = intent.getStringExtra("fullHDURL");
        final String wallpaperURL = intent.getStringExtra("fullHDURL");
        ImageView imageView = findViewById(R.id.imageView);

        Picasso.get().load(hiresURL).fit().centerInside().into(imageView);
        Toast.makeText(this, hiresURL, Toast.LENGTH_LONG).show();

        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        wallpaperButton = findViewById(R.id.setWallButton);
        wallpaperButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Picasso.get().load(wallpaperURL).into(new Target()
                {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                    {
                        try
                        {
                            wallpaperManager.setBitmap(bitmap);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable)
                    {
                        e.printStackTrace();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable)
                    {

                    }
                });
            }
        });
    }
}
