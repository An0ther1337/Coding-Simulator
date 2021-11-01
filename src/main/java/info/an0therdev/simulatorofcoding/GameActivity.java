package info.an0therdev.simulatorofcoding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.text.Layout;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import info.an0therdev.simulatorofcoding.GameElements.ClearButton;
import info.an0therdev.simulatorofcoding.GameElements.DrawView;
import info.an0therdev.simulatorofcoding.GameElements.FloatingText;
import info.an0therdev.simulatorofcoding.GameElements.InputField;
import info.an0therdev.simulatorofcoding.GameElements.ScoreView;

public class GameActivity extends AppCompatActivity {

    LinearLayout llayout;
    public Game game;
    MediaPlayer player;
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        prefs = getSharedPreferences("Main", Context.MODE_PRIVATE);
        player = MediaPlayer.create(this, R.raw.main);
        player.setLooping(true);
        if(prefs.getBoolean("playMusic", true)){
            player.start();
        }
        llayout = findViewById(R.id.mainlayout);
        RelativeLayout rlayout = new RelativeLayout(this);
        LinearLayout llayout2 = new LinearLayout(this);
        llayout2.setOrientation(LinearLayout.HORIZONTAL);
        game = new Game();
        DrawView draw = new DrawView(this);
        InputField inf = new InputField(this);
        ClearButton cb = new ClearButton(this);
        ScoreView sv = new ScoreView(this);
        rlayout.addView(draw);
        rlayout.addView(sv);

        llayout.addView(rlayout);

        llayout2.addView(inf);
        llayout2.addView(cb);

        llayout.addView(llayout2);

        llayout.setOnClickListener(new LinearLayout.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(findViewById(R.id.gameover).getVisibility() == View.VISIBLE){
                    Intent game = new Intent(GameActivity.this, MainActivity.class);
                    startActivity(game);
                    finish();
                }
            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(),"title.ttf");
        ((TextView)findViewById(R.id.textView2)).setTypeface(typeface);
        typeface = Typeface.createFromAsset(getAssets(),"main.ttf");
        ((TextView)findViewById(R.id.textView3)).setTypeface(typeface);
        ((TextView)findViewById(R.id.textView4)).setTypeface(typeface);

        game.setGameElements(cb, inf, draw, sv);
        game.start();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        player.pause();
        game.gameOver();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(prefs.getBoolean("playMusic", true))
            player.start();
        super.onResume();
    }
}