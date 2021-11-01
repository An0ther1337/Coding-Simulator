package info.an0therdev.simulatorofcoding.GameElements;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.an0therdev.simulatorofcoding.Game;
import info.an0therdev.simulatorofcoding.GameActivity;
import info.an0therdev.simulatorofcoding.R;

public class ScoreView extends AppCompatTextView {

    Game game;

    public ScoreView(Context context) {
        super(context);
        game = ((GameActivity)context).game;
        setTextColor(Color.YELLOW);
        setTextSize(20);
        setLayoutParams(new LinearLayout.LayoutParams(dpToPx(100), dpToPx(50)));
        setTypeface(Typeface.createFromAsset(context.getAssets(),"main.ttf"));
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
