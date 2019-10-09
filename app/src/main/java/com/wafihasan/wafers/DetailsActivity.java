package com.wafihasan.wafers;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.wafihasan.wafers.MainActivity.EXTRA_CREATOR;
import static com.wafihasan.wafers.MainActivity.EXTRA_LIKES;
import static com.wafihasan.wafers.MainActivity.EXTRA_SIZE;
import static com.wafihasan.wafers.MainActivity.EXTRA_URL;
import static com.wafihasan.wafers.MainActivity.EXTRA_VIEWS;

public class DetailsActivity extends AppCompatActivity
{

    public WallpaperManager wallpaperManager;
    public Button wallpaperButton;
    public Button dialogButton;
    public BottomSheetDialog bottomSheetDialog;
    public TextView tv_likes, tv_creator, tv_views, tv_imgSize;
    Context ctx = DetailsActivity.this;

    int likeCount;
    int viewsCount;

    String creatorName;
    String imgSize;
    String wallpaperURL;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        likeCount = intent.getIntExtra(EXTRA_LIKES, 0);
        viewsCount = intent.getIntExtra(EXTRA_VIEWS, 0);
        imgSize = intent.getStringExtra(EXTRA_SIZE);
        creatorName = intent.getStringExtra(EXTRA_CREATOR);
        wallpaperURL = intent.getStringExtra(EXTRA_URL);

        ImageView imageView = findViewById(R.id.imageView);
        dialogButton = findViewById(R.id.infoButton);

        Picasso.get().load(wallpaperURL).fit().centerInside().into(imageView);
        Toast.makeText(this, wallpaperURL, Toast.LENGTH_LONG).show();

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

        dialogButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bottomSheetDialog = new BottomSheetDialog(ctx);
                bottomSheetDialog.setContentView(R.layout.dialog);
                tv_creator = bottomSheetDialog.findViewById(R.id.tv_user);
                tv_likes = bottomSheetDialog.findViewById(R.id.tv_likes);
                tv_views = bottomSheetDialog.findViewById(R.id.tv_views);
                tv_imgSize = bottomSheetDialog.findViewById(R.id.tv_imgSize);

                tv_creator.setText(creatorName);
                tv_likes.setText(likeCount + " likes");
                tv_views.setText(viewsCount + " views");
                tv_imgSize.setText("Size " + imgSize);
                bottomSheetDialog.show();
            }
        });
    }
}
