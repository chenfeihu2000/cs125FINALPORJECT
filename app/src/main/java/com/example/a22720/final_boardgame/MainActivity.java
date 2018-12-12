package com.example.a22720.final_boardgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Range;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //board size
    final static int maxN = 15;
    // develop the cell array
    private ImageView[][] ivCell = new ImageView[maxN][maxN];
    private Context context;
    private Drawable[] drawCell = new Drawable[4];

    private Button btnPlay;
    private TextView tvTurn;
    private int[][] valueCell = new int[maxN][maxN];
    private int winner_PLay;
    private boolean firstMove;
    private int xMove, yMove;
    private int turnPlay;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        setListen();
        loadResources();
        designBoardGame();
    }

    private void setListen() {
        btnPlay = (Button) findViewById(R.id.btnPlay);
        tvTurn = (TextView) findViewById(R.id.tvTurn);

        btnPlay.setText("Play Game");
        tvTurn.setText("Press button Play Game");

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initial_game();
                playing_game();
            }
        });

    }

    private void initial_game() {
        firstMove = true;
        winner_PLay = 0;
        for (int i = 0; i < maxN; i++) {
            for (int j = 0; j < maxN; j++) {
                ivCell[i][j].setImageDrawable(drawCell[0]);
                valueCell[i][j] = 0;
            }
        }
    }

    private void playing_game() {
        Random r = new Random();
        turnPlay = r.nextInt(2) + 1;

        if (turnPlay == 1) {
            Toast.makeText(context, "Player1 play first", Toast.LENGTH_SHORT).show();
            playerTurn();
        } else {
            Toast.makeText(context, "PLayer2 play first", Toast.LENGTH_SHORT).show();;
            player2Turn();
        }
    }

    private void player2Turn() {
        tvTurn.setText("Butter");
        isClicked = false;
    }

    private void playerTurn() {
        tvTurn.setText("Apple");
        isClicked = false;
    }

    private void loadResources() {
        drawCell[3] = context.getResources().getDrawable(R.drawable.keyboard_cell);
        drawCell[0] = null;
        drawCell[1] = context.getResources().getDrawable(R.drawable.black);
        drawCell[2] = context.getResources().getDrawable(R.drawable.white);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isClicked;
    @SuppressLint("NewApi")
    private void designBoardGame() {
        int sizeofCell = Math.round(ScreenWidth()/maxN);
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(sizeofCell * maxN, sizeofCell * 1);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(sizeofCell, sizeofCell);

        LinearLayout linBoardGame = findViewById(R.id.linBoardGame);

        for (int i = 0; i < maxN; i++) {
            LinearLayout linRow = new LinearLayout(context);
            for (int j = 0; j < maxN; j ++) {
                ivCell[i][j] = new ImageView(context);
                ivCell[i][j].setBackground(drawCell[3]);
                final int x = i;
                final int y = j;
                ivCell[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (turnPlay == 1 || !isClicked) {
                            isClicked = true;
                            xMove = x;
                            yMove = y;
                            make_a_move();
                        }
                    }
                });
                linRow.addView(ivCell[i][j], lpCell);
            }
            linBoardGame.addView(linRow, lpRow);
        }
    }

    private void make_a_move() {
        ivCell[xMove][yMove].setImageDrawable(drawCell[turnPlay]);
        if (turnPlay == 1) {
            turnPlay = 3 - turnPlay;
            player2Turn();
        } else {
            turnPlay = 3 - turnPlay;
            playerTurn();
        }
    }

    private float ScreenWidth() {
        Resources resources = context.getResources();
        DisplayMetrics dn = resources.getDisplayMetrics();
        return dn.widthPixels;
    }
}
