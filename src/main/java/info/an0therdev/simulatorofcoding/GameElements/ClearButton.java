package info.an0therdev.simulatorofcoding.GameElements;

import android.app.ActionBar;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import info.an0therdev.simulatorofcoding.Game;
import info.an0therdev.simulatorofcoding.GameActivity;
import info.an0therdev.simulatorofcoding.R;

public class ClearButton extends AppCompatButton implements AppCompatButton.OnClickListener {

    Game game;

    public ClearButton(Context context){
        super(context);
        game = ((GameActivity)context).game;
        //setWidth(dpToPx(77));
        //setHeight(dpToPx(50));
        setBackgroundResource(R.drawable.button1);
        setText(R.string.del);
        setLayoutParams(new LinearLayout.LayoutParams(dpToPx(110), dpToPx(50)));
        setTextColor(getResources().getColor(android.R.color.holo_green_light));
        setTextSize(13);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        game.inf.setText("");
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
