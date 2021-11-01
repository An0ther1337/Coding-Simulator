package info.an0therdev.simulatorofcoding.GameElements;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import info.an0therdev.simulatorofcoding.Game;
import info.an0therdev.simulatorofcoding.GameActivity;
import info.an0therdev.simulatorofcoding.R;

public class InputField extends AppCompatEditText {

    Game game;

    public InputField(Context context){
        super(context);
        game = ((GameActivity)context).game;
        //setWidth(dpToPx(200));
        //setHeight(dpToPx(50));
        setBackgroundColor(Color.BLACK);
        setLayoutParams(new LinearLayout.LayoutParams(dpToPx(300), dpToPx(50)));
        setHint(R.string.hint);
        setHintTextColor(Color.WHITE);
        setInputType(InputType.TYPE_CLASS_TEXT);
        setSingleLine();
        setTextColor(Color.WHITE);
        setTextSize(18);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(game == null)
            return;
        ArrayList<FloatingText> tmp = new ArrayList<>();
        ArrayList<FloatingText> tmpwords = (ArrayList<FloatingText>)game.words.clone();

        for(FloatingText word : tmpwords){
            if(text.toString().toLowerCase().contains(word.getText())) {
                setText("");
                tmp.add(word);
                game.setScore(game.getScore() + 1);
            }
        }
        for(FloatingText tmpp : tmp){
            tmpp.fade = true;
            //tmpwords.remove(tmpp);
        }
        game.words = (ArrayList<FloatingText>)tmpwords.clone();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int pxToDp(int px){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
