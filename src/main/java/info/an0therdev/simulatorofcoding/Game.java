package info.an0therdev.simulatorofcoding;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import info.an0therdev.simulatorofcoding.GameElements.ClearButton;
import info.an0therdev.simulatorofcoding.GameElements.DrawView;
import info.an0therdev.simulatorofcoding.GameElements.FloatingText;
import info.an0therdev.simulatorofcoding.GameElements.InputField;
import info.an0therdev.simulatorofcoding.GameElements.ScoreView;

public class Game {

    // массив из трех сложностей
    // первый элемент сложности - частота генерации (чем меньше, тем чаще)
    // второй элемент сложности - скорость (чем больше, тем быстрее)
    final public static int[][] Complexity = {{200,1},{100,2},{50,3}};

    public ClearButton cb;
    public InputField inf;
    public DrawView draw;
    public ScoreView sv;

    // список слов, которые на экране
    public ArrayList<FloatingText> words;
    // текущая сложность
    public int[] complexity;
    // объект рандома
    private Random rand;
    // момент последнего создания текста
    private long last;
    // счёт
    private int score;

    public Game(){
        complexity = MainActivity.complexity;
        words = new ArrayList<>();
        rand = new Random();
    }

    public void setGameElements(ClearButton cb, InputField inf, DrawView draw, ScoreView sv){
        this.cb = cb;
        this.inf = inf;
        this.draw = draw;
        this.sv = sv;
    }

    public void start(){
        draw.getHolder().addCallback(draw);
        setScore(0);
    }

    public int getScore(){
        return score;
    }

    public void setScore(int n){
        score = n;
        sv.setText(sv.getContext().getString(R.string.nowscore).replace("$1", String.valueOf(n)));
    }

    public boolean canSpawn(){
        return rand.nextInt(complexity[0]) == 1 && System.currentTimeMillis() - last > 100 && (words.size() != 0 ? (words.get(words.size()-1).getY() - words.get(words.size()-1).getRect().height() > 0) : true);
    }

    public void spawnNewFText(Paint paint){
        last = System.currentTimeMillis();
        FloatingText ft = new FloatingText(
                FloatingText.words[rand.nextInt(FloatingText.words.length)],
                0,
                rand.nextInt(draw.getWidth()/100)*100,
                FloatingText.colors[rand.nextInt(FloatingText.colors.length)],
                paint
        );
        if(ft.getX()+ft.getRect().width() >= draw.getWidth()){
            ft.setX(ft.getX()-ft.getRect().width());
        }
        words.add(ft);
    }

    public void gameOver(){
        final GameActivity main = (GameActivity) cb.getContext();
        draw.getHolder().removeCallback(draw);
        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                main.findViewById(R.id.gameover).setVisibility(View.VISIBLE);
                inf.setVisibility(View.GONE);
                cb.setVisibility(View.GONE);
                sv.setVisibility(View.GONE);
                ((TextView)main.findViewById(R.id.textView3)).setText(main.getString(R.string.yourscore).replace("$1", String.valueOf(getScore())));
                main.findViewById(R.id.textView4).startAnimation(AnimationUtils.loadAnimation(main, R.anim.zoom));

                InputMethodManager imm = (InputMethodManager) main.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inf.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                SharedPreferences prefs = main.getSharedPreferences("Main", Context.MODE_PRIVATE);
                //System.out.println(prefs.getInt("score"+complexity[1], 123));
                if(getScore() > prefs.getInt("score"+complexity[1], 0)){
                    main.findViewById(R.id.textView4).setVisibility(View.VISIBLE);
                    prefs.edit().putInt("score"+complexity[1], getScore()).apply();
                }
            }
        });
    }

}
