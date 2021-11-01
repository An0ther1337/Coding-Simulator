package info.an0therdev.simulatorofcoding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences prefs;
    public static int[] bestscore;
    public static int[] complexity;
    MediaPlayer player;
    AlertDialog dialog;
    ReplayView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        prefs = getSharedPreferences("Main", Context.MODE_PRIVATE);
        bestscore = new int[3];
        bestscore[0] = prefs.getInt("score1", 0);
        bestscore[1] = prefs.getInt("score2", 0);
        bestscore[2] = prefs.getInt("score3", 0);
        player = MediaPlayer.create(this, R.raw.main);
        player.setLooping(true);

        ((TextView)findViewById(R.id.bestscore)).setText(getString(R.string.score)
                .replace("$11", getString(R.string.easy))
                .replace("$12", String.valueOf(bestscore[0]))
                .replace("$21", getString(R.string.medium))
                .replace("$22", String.valueOf(bestscore[1]))
                .replace("$31", getString(R.string.hard))
                .replace("$32", String.valueOf(bestscore[2]))
        );

        Typeface typeface = Typeface.createFromAsset(getAssets(),"main.ttf");
        ((TextView)findViewById(R.id.tv2)).setTypeface(typeface);
        ((TextView)findViewById(R.id.tv3)).setTypeface(typeface);
        ((TextView)findViewById(R.id.bestscore)).setTypeface(typeface);
        ((TextView)findViewById(R.id.button)).setTypeface(typeface);
        typeface = Typeface.createFromAsset(getAssets(),"title.ttf");
        ((TextView)findViewById(R.id.tv1)).setTypeface(typeface);

        findViewById(R.id.tv2).startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom));

        if(prefs.getBoolean("playMusic", true)){
            player.start();
        }
        else{
            ((Button)findViewById(R.id.ctrlMusic)).setBackgroundResource(R.drawable.novol);
            ((TextView)findViewById(R.id.tv3)).setText("");
        }

        rv = new ReplayView(this);
        ((RelativeLayout)findViewById(R.id.replay)).addView(rv);
    }

    public void onStart(View v){
        //Toast.makeText(this, "Game start", Toast.LENGTH_LONG).show();
        Typeface tf = Typeface.createFromAsset(getAssets(),"main.ttf");
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setCustomTitle(new TitleView(this));
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.parseColor("#000000"));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        TextView txt = new TextView(MainActivity.this);
        txt.setText(R.string.choosediff);
        txt.setTextColor(Color.WHITE);
        txt.setTypeface(tf);
        txt.setGravity(Gravity.CENTER);
        txt.setTextSize(35);
        layout.addView(txt);
        Button btn1 = new Button(this);
        btn1.setText(R.string.easy);
        btn1.setBackgroundColor(Color.parseColor("#efefef"));
        btn1.setHeight(size.y/10);
        btn1.setTypeface(tf);
        btn1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                complexity = Game.Complexity[0];
                getAD().cancel();
                Intent game = new Intent(MainActivity.this, GameActivity.class);
                startActivity(game);
                finish();
            }
        });
        layout.addView(btn1);
        Button btn2 = new Button(this);
        btn2.setText(R.string.medium);
        btn2.setBackgroundColor(Color.parseColor("#cecece"));
        btn2.setHeight(size.y/10);
        btn2.setTypeface(tf);
        btn2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                complexity = Game.Complexity[1];
                getAD().cancel();
                Intent game = new Intent(MainActivity.this, GameActivity.class);
                startActivity(game);
                finish();
            }
        });
        layout.addView(btn2);
        Button btn3 = new Button(this);
        btn3.setText(R.string.hard);
        btn3.setBackgroundColor(Color.parseColor("#a5a5a5"));
        btn3.setHeight(size.y/10);
        btn3.setTypeface(tf);
        btn3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                complexity = Game.Complexity[2];
                getAD().cancel();
                Intent game = new Intent(MainActivity.this, GameActivity.class);
                startActivity(game);
                finish();
            }
        });
        layout.addView(btn3);
        builder.setView(layout);
        dialog = builder.create();
        dialog.show();
    }

    private AlertDialog getAD(){
        return dialog;
    }

    @Override
    protected void onPause(){
        findViewById(R.id.tv2).clearAnimation();
        player.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        findViewById(R.id.tv2).startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom));
        if(prefs.getBoolean("playMusic", true))
            player.start();
        super.onResume();
    }

    public void controlMusic(View v){
        if(prefs.getBoolean("playMusic", true)){
            player.pause();
            prefs.edit().putBoolean("playMusic", false).apply();
            ((Button)v).setBackgroundResource(R.drawable.novol);
            ((TextView)findViewById(R.id.tv3)).setText("");
        }
        else{
            player.start();
            prefs.edit().putBoolean("playMusic", true).apply();
            ((Button)v).setBackgroundResource(R.drawable.vol);
            ((TextView)findViewById(R.id.tv3)).setText(R.string.musinfo);
        }
    }
}
