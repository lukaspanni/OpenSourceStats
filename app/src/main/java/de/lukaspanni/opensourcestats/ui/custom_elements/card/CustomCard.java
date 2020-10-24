package de.lukaspanni.opensourcestats.ui.custom_elements.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lukaspanni.opensourcestats.R;

public abstract class CustomCard extends LinearLayout {

    public CustomCard(@NonNull Context context) {
        this(context, null);
    }

    public CustomCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        applyAttributes(attrs);
    }

    public CustomCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        applyAttributes(attrs);
    }

    public CustomCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        applyAttributes(attrs);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(getCardLayout(), this);
        initViews();
    }

    protected int getCardLayout() {
        return R.layout.card_component;
    }

    protected abstract void initViews();

    private void applyAttributes(AttributeSet attrs) {
        TextView titleView = findViewById(R.id.overview_title);
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCard);
        CharSequence text = arr.getText(R.styleable.CustomCard_android_text);
        if (text != null) {
            titleView.setText(text.toString());
        }
        arr.recycle();
    }
}
