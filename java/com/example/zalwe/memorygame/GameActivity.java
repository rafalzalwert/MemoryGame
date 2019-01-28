package com.example.zalwe.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private GridLayout gridLayout;
    private int size;
    private int numbersOfButtons;
    private Card[] cards;
    private Card selectedCard;
    private Card secondSelectedCard;
    private List<Image> imageList;
    private boolean isBusy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        DbHelper dbHelper = new DbHelper(getBaseContext());
        final DbOperations dbOperations = new DbOperations(dbHelper);

        imageList = dbOperations.getImageFromDb();
        gridLayout = findViewById(R.id.gridLayout);
        init();
        createCards();
        shuffleImages();
    }

    private void init(){
        Intent intent = getIntent();
        size = intent.getIntExtra(MainActivity.SIZE,0) / 2;
        gridLayout.setColumnCount(size);
        gridLayout.setRowCount(2);
        numbersOfButtons = size * 2;
        cards = new Card[numbersOfButtons];
    }

    private void createCards(){
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < size; j++){
                Card card = new Card(this,i,j);
                card.setId(View.generateViewId());
                card.setOnClickListener(this);
                cards[i * size + j] = card;
                gridLayout.addView(card);
            }
        }
    }

    private void shuffleImages(){
        Collections.shuffle(imageList);

        for (int n = 0; n<numbersOfButtons; n++){
            cards[n].setDefaulImage(imageList.get(n));
        }
    }
    @Override
    public void onClick(View v) {
        if(isBusy) {
            return;
        }

        Card card = (Card) v;

        if(card.isMatching()) {
            return;
        }

        if(selectedCard == null) {
            selectedCard = card;
            selectedCard.reverse();
            return;
        }

        if(selectedCard.getId() == card.getId()) {
            return;
        }

        if(Arrays.equals(selectedCard.getImage().getImage(), card.getImage().getImage())) {
            pairFound(card);
            if(shouldGameEnd()) {
                endGame();
            }
        } else {
            pairNotFound(card);
        }
    }

    private void pairFound(Card card) {
        card.reverse();

        card.setMatched();
        selectedCard.setMatched();

        selectedCard.setEnabled(false);
        card.setEnabled(false);

        selectedCard = null;
    }

    private void pairNotFound(Card card) {
        secondSelectedCard = card;
        secondSelectedCard.reverse();
        isBusy = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                secondSelectedCard.reverse();
                selectedCard.reverse();
                secondSelectedCard = null;
                selectedCard = null;
                isBusy = false;
            }
        }, 1000);
    }

    private void endGame() {
        Toast.makeText(this, "Congrats! You have won!", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }

    private boolean shouldGameEnd() {
        List<Boolean> booleans = new LinkedList<>();
        for(Card card : cards) {
            booleans.add(card.isMatching());
        }
        return areAllTrue(booleans);
    }

    private boolean areAllTrue(List<Boolean> booleans) {
        for(boolean b : booleans) if(!b) return false;
        return true;
    }
}
