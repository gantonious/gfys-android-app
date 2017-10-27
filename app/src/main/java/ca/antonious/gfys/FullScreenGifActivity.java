package ca.antonious.gfys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullScreenGifActivity extends AppCompatActivity {
    private static String EXTRA_GIF_URL = "FullScreenGifActivity.GifUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_gif);

        ImageView imageView = (ImageView) findViewById(R.id.image);

        Glide.with(this)
                .asGif()
                .load(getIntent().getStringExtra(EXTRA_GIF_URL))
                .into(imageView);
    }

    public static void start(Context from, String gifUrl) {
        Intent intent = new Intent(from, FullScreenGifActivity.class);
        intent.putExtra(EXTRA_GIF_URL, gifUrl);
        from.startActivity(intent);
    }
}
