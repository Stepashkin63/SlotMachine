package ru.stepashkin.androidslotmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import ru.stepashkin.androidslotmachine.ImageViewScrolling.IEventEnd;
import ru.stepashkin.androidslotmachine.ImageViewScrolling.ImageViewScrolling;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    ImageView button;
    ImageViewScrolling image, image2, image3;
    TextView txt_score;

    int count_done = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        button = (ImageView) findViewById(R.id.button);

        image = (ImageViewScrolling) findViewById(R.id.image);
        image2 = (ImageViewScrolling) findViewById(R.id.image2);
        image3 = (ImageViewScrolling) findViewById(R.id.image3);

        txt_score = (TextView) findViewById(R.id.txt_score);

        image.setEventEnd(MainActivity.this);
        image2.setEventEnd(MainActivity.this);
        image3.setEventEnd(MainActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.SCORE >= 50) {
                    button.setVisibility(View.VISIBLE);
                    image.setValueRandom(new Random().nextInt(5),
                            new Random().nextInt((15 - 5) + 1) + 5);
                    image2.setValueRandom(new Random().nextInt(5),
                            new Random().nextInt((15 - 5) + 1) + 5);
                    image3.setValueRandom(new Random().nextInt(5),
                            new Random().nextInt((15 - 5) + 1) + 5);

                    Common.SCORE -= 50;
                    txt_score.setText(String.valueOf(Common.SCORE));
                } else {
                    Toast.makeText(MainActivity.this, "You not enough money!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void eventEnd(int result, int count) {
        if (count_done < 2) {
            count_done++;
        } else {
            button.setVisibility(View.VISIBLE);

            count_done = 0;

            if (image.getValue() == image2.getValue() && image2.getValue() == image3.getValue()) {
                Toast.makeText(this, "You win big prize!", Toast.LENGTH_SHORT).show();
                Common.SCORE += 300;
                txt_score.setText(String.valueOf(Common.SCORE));
            } else if (image.getValue() == image2.getValue() ||
                    image2.getValue() == image3.getValue() ||
                    image.getValue() == image3.getValue()) {
                Toast.makeText(this, "You win small prize!", Toast.LENGTH_SHORT).show();
                Common.SCORE += 100;
                txt_score.setText(String.valueOf(Common.SCORE));
            }
            else {
                Toast.makeText(this, "You lose!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}