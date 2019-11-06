package com.wafihasan.wafers;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.wafihasan.wafers.MainActivity.EXTRA_CREATOR;
import static com.wafihasan.wafers.MainActivity.EXTRA_LIKES;
import static com.wafihasan.wafers.MainActivity.EXTRA_SIZE;
import static com.wafihasan.wafers.MainActivity.EXTRA_URL;
import static com.wafihasan.wafers.MainActivity.EXTRA_VIEWS;

public class DetailsActivity extends AppCompatActivity {

    public WallpaperManager wallpaperManager;
    public Button wallpaperButton, saveButton;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        likeCount = intent.getIntExtra(EXTRA_LIKES, 0);
        viewsCount = intent.getIntExtra(EXTRA_VIEWS, 0);
        imgSize = intent.getStringExtra(EXTRA_SIZE);
        creatorName = intent.getStringExtra(EXTRA_CREATOR);
        wallpaperURL = intent.getStringExtra(EXTRA_URL);

        saveButton = findViewById(R.id.saveWallButton);
        wallpaperButton = findViewById(R.id.setWallButton);
        ImageView imageView = findViewById(R.id.imageView);
        dialogButton = findViewById(R.id.infoButton);

        if (Util.isNetworkAvailable(this)) {
            Picasso.get().load(wallpaperURL).fit().centerInside().into(imageView);

        }
        Toast.makeText(this, wallpaperURL, Toast.LENGTH_LONG).show();

        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNetworkAvailable(DetailsActivity.this)) {
                    Picasso.get().load(wallpaperURL).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            try {
                                new AsyncTaskRunner().execute(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

                }

            }
        });
        wallpaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNetworkAvailable(DetailsActivity.this)) {
                    Picasso.get().load(wallpaperURL).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            try {
                                wallpaperManager.setBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
                }
            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public void SaveBitmap(Bitmap ImageBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Wafers");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("ddss").format(new Date());
        String fname = "Wallpaper_" + timeStamp + ".png";

        File file = new File(myDir, fname);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOut);
            fileOut.flush();
            fileOut.close();

            Snackbar.make(dialogButton, "Wallpaper Saved", Snackbar.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(dialogButton, "Unable to Save Wallpaper", Snackbar.LENGTH_LONG).show();

        }
    }


    private class AsyncTaskRunner extends AsyncTask<Bitmap, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Bitmap[] p1) {

            SaveBitmap(p1[0]);

            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(ctx,
                    "Just a Sec",
                    "Saving Wallpaper");
        }


        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }


    }


}
