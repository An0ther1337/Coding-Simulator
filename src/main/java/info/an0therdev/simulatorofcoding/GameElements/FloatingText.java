package info.an0therdev.simulatorofcoding.GameElements;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class FloatingText {
    private int y;
    private int x;
    private String text;
    private int color;
    public final static String[] words = {"password", "connect", "http", "echo", "<?php", "?>", "document", "system", "writeln",
            "println", "hash", "cout", "cin", "printf", "import", "include", "use", "java", "logcat", "ssh", "mysql", "sqlite", "localhost", "curl"};
    public final static int[] colors = {
            Color.parseColor("#42f46b"),
            Color.parseColor("#42f495"),
            Color.parseColor("#6ff7d3"),
            Color.parseColor("#86e0db"),
            Color.parseColor("#00ffd4"),
            Color.parseColor("#00ff6a")
    };
    private Rect rect;
    public Paint paint;
    public boolean fade;
    public int infade;

    public FloatingText(String text, int y, int x, int color, Paint paint) {
        this.infade = 255;
        this.fade = false;
        this.y = y;
        this.x = x;
        this.color = color;
        this.text = text;
        rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        this.paint = paint;
    }

    public Rect getRect() {
        return rect;
    }

    public int getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int fade(){
        infade -= 5;
        if(infade > 5){
            return infade;
        }
        return 0;
    }

}