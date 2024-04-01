package com.example.pocketcashier.utilitaries;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.pocketcashier.R;

public class MenuTitle extends LinearLayout {


    //public static ColorFilter iconFilter = new PorterDuffColorFilter(ContextCompat.getColor(new View, R.color.button_orange), PorterDuff.Mode.SRC_IN);
    public MenuTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MenuTitle(Context context, String title, String description) {
        super(context);
        init(context);
        setTitleAndDescription(title, description);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.menu_title, this, true);
    }

    public void setTitleAndDescription(String title, String description) {
        TextView titleField = findViewById(R.id.menu_title);
        //TextView descriptionField = findViewById(R.id.menu_title_description);
        titleField.setText(title);
        //descriptionField.setText(description);
    }
}
