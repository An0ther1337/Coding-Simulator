package info.an0therdev.simulatorofcoding.GameElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

import info.an0therdev.simulatorofcoding.Game;
import info.an0therdev.simulatorofcoding.GameActivity;
import info.an0therdev.simulatorofcoding.MainActivity;
import info.an0therdev.simulatorofcoding.R;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    DrawThread thread;
    Game game;

    public DrawView(Context main){
        super(main);
        game = ((GameActivity)main).game;
        //setBackgroundColor(Color.BLACK);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(600)));
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new DrawThread(this);
        thread.start();
        System.out.println("create()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        while(thread.isAlive())
            thread.interrupt();
        System.out.println("destroy()");
    }

    /*public void EndGame(){
        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                main.findViewById(R.id.gameover).setVisibility(View.VISIBLE);
                main.findViewById(R.id.enter).setVisibility(View.GONE);
                main.findViewById(R.id.delete).setVisibility(View.GONE);
            }
        });
    }*/

    class DrawThread extends Thread {

        Paint paint;
        Game game;
        private SurfaceHolder surfaceHolder;
        private DrawView main;

        public DrawThread(DrawView main) {
            this.main = main;
            this.surfaceHolder = main.getHolder();
            paint = new Paint();
            paint.setTextSize(40);
            paint.setColor(Color.WHITE);
            game = main.game;
        }

        @Override
        public void run() {
            Canvas canvas;
            System.out.println("run()");
            while(!isInterrupted()) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;
                    canvas.drawColor(Color.BLACK);
                    onDraw(canvas);
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        public void onDraw(Canvas canvas) {
            if(game.canSpawn()){
                game.spawnNewFText(paint);
                //System.out.println("spawn"+game.words.size());
            }
            ArrayList<FloatingText> tmp = new ArrayList<>();
            ArrayList<FloatingText> tmpwords = (ArrayList<FloatingText>)game.words.clone();

            for(FloatingText word : tmpwords){
                if(word.getY() > getHeight()){
                    tmp.add(word);
                    game.gameOver();
                    interrupt();
                }
                word.setY(word.getY()+game.complexity[1]);
                paint.setColor(word.getColor());
                if(word.fade){
                    paint.setAlpha(word.infade);
                }
                canvas.drawText(word.getText(), word.getX(), word.getY(), paint);
            }
            for(FloatingText word : tmpwords) {
                if (word.fade) {
                    word.infade = word.fade();
                    if (word.infade == 0) {
                        tmp.add(word);
                        word.fade = false;
                    }
                }
            }
            for(FloatingText tmpp : tmp){
                tmpwords.remove(tmpp);
            }
            game.words = (ArrayList<FloatingText>)tmpwords.clone();
        }
    }
}