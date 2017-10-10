package ateam.com.stories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class MainActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {

    private StoriesProgressView storiesProgressView;
    int[] resources = {R.mipmap.ic_launcher,R.mipmap.ic_launcher_round,R.mipmap.blood,R.mipmap.bloodbank};
    ImageView image;
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        storiesProgressView.setStoriesCount(resources.length); // <- set stories
        storiesProgressView.setStoryDuration(1200L); // <- set a story duration
        storiesProgressView.setStoriesListener(this); // <- set listener
        storiesProgressView.startStories(); // <- start progress
        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        image.setImageResource(resources[counter]);

        // bind reverse view
        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });

        // bind skip view
        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
    }

    @Override
    public void onNext() {

        image.setImageResource(resources[++counter%resources.length]);
    }

    @Override
    public void onPrev() {
        if (counter - 1 < 0) return;
        image.setImageResource(resources[--counter%resources.length]);
    }

    @Override
    public void onComplete() {
        if (counter == resources.length - 1) {
            image.setImageResource(resources[0]);
            storiesProgressView.startStories();
        }
    }

    @Override
    protected void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }
}