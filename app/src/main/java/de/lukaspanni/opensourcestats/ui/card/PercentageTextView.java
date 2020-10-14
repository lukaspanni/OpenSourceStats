package de.lukaspanni.opensourcestats.ui.card;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.lukaspanni.opensourcestats.R;

import java.text.DecimalFormat;

public class PercentageTextView extends androidx.appcompat.widget.AppCompatTextView {

    private static DecimalFormat decimalFormat = new DecimalFormat("+##.##%;-##.##%");


    public PercentageTextView(Context context) {
        super(context);
    }

    public PercentageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPercentage(float percentage) {
        setText(decimalFormat.format(percentage));
        int color;
        if (percentage < 0) color = getContext().getColor(R.color.textRed);
        else if(percentage > 0) color = getContext().getColor(R.color.textGreen);
        else color = getContext().getColor(R.color.textBlack);
        setTextColor(color);
    }

}
