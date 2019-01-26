package com.example.zalwe.memorygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.widget.GridLayout;

public class Card extends AppCompatButton {

    boolean isClicked = false;
    boolean isMatching = false;

    private Image front;
    private final Drawable back;

    public Card(Context context , int cloumns , int rows) {
        super(context);
        this.back = context.getDrawable(R.drawable.crystal_clear_action_apply);

        setBackground(back);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(rows),
                GridLayout.spec(cloumns));
        layoutParams.width = (int) getResources().getDisplayMetrics().density * 70;
        layoutParams.height = (int) getResources().getDisplayMetrics().density * 70;
        setLayoutParams(layoutParams);
    }

    boolean isMatching() {
        return isMatching;
    }

    void setMatched() {
        isMatching = true;
    }

    Image getImage() {

        return front;
    }

    public void setFront(Image image) {
        front = image;
    }

    void flip() {
        if(isMatching) {
            return;
        }
        if(isClicked) {
            setBackground(back);
            isClicked = false;
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(front.getImage(),
                    0,front.getImage().length);
            Drawable drawable = new BitmapDrawable(getResources(),bitmap);
            setBackground(drawable);
            isClicked = true;
        }
    }
}
