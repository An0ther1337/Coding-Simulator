package info.an0therdev.simulatorofcoding;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import info.an0therdev.simulatorofcoding.GameElements.FloatingText;

public class ReplayView extends SurfaceView implements SurfaceHolder.Callback {

    ReplayThread thread;

    public ReplayView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new ReplayThread(getHolder());
        thread.start();
        System.out.println("create()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        while(thread.isAlive())
            thread.interrupt();
        System.out.println("destroy()");
    }

    class ReplayThread extends Thread {

        Paint paint;
        ArrayList<FloatingText> words;
        private SurfaceHolder surfaceHolder;
        Random rand;

        public ReplayThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            paint = new Paint();
            paint.setTextSize(40);
            words = new ArrayList<>();
            rand = new Random();
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
        /*String word = replay[i];
        p.setColor(Integer.parseInt(word.split(":")[3]));
        canvas.drawText(word.split(":")[0], Integer.parseInt(word.split(":")[1]), Integer.parseInt(word.split(":")[2])+f, p);
        f += 0.01;
        if(f >= 1){
            i++;
            f = 0;
        }
        if(i >= replay.length){
            i = 0;
        }*/

            if(rand.nextInt(100) == 1 && (words.size() != 0 ? (words.get(words.size()-1).getY() - words.get(words.size()-1).getRect().height() > 0) : true)){
                FloatingText ft = new FloatingText(
                        FloatingText.words[rand.nextInt(FloatingText.words.length)],
                        0,
                        rand.nextInt(getWidth()/100)*100,
                        FloatingText.colors[rand.nextInt(FloatingText.colors.length)],
                        paint
                );
                if(ft.getX()+ft.getRect().width() >= getWidth()){
                    ft.setX(ft.getX()-ft.getRect().width());
                }
                words.add(ft);
            }
            ArrayList<FloatingText> tmp = new ArrayList<>();
            for(FloatingText word : words){
                if(word.getY() > getHeight()){
                    tmp.add(word);
                }
                word.setY(word.getY()+2);
                paint.setColor(word.getColor());
                canvas.drawText(word.getText(), word.getX(), word.getY(), paint);
            }
            for(FloatingText tmpp : tmp){
                words.remove(tmpp);
            }
        }
    }
}
