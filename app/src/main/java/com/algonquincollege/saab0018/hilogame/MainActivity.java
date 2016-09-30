package com.algonquincollege.saab0018.hilogame;

/**
 *  {A hi-lo game. Generates a number for the user to guess. User only has 10 tries}
 *  Matt Saab {saab0018@algonquinlive.com}
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private static final String ABOUT_DIALOG_TAG;

    static {
        ABOUT_DIALOG_TAG = "About Dialog";

    }

    private int theNumber = 1;
    private int numberOfTries = 0;

    private int generateNumber() {
        Random randomNum = new Random();
        theNumber = randomNum.nextInt(1000) + 1;
        numberOfTries = 0;
        return theNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        generateNumber();

        Button guess = (Button) findViewById( R.id.guessButton );

        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // Reference the userGuessText in the layout.
                EditText guess = (EditText) findViewById( R.id.userGuess );

                // GET the userGuess.
                String userGuess = guess.getText().toString();

                if ( userGuess.isEmpty() ) {
                    guess.setError( "That is not a number" );
                    guess.requestFocus();
                    return;
                }

                // VALIDATE

                int userNumber;
                //parse userNumber
                // The user can only enter a whole number.
                try {
                    userNumber = Integer.parseInt(userGuess);
                } catch( NumberFormatException e ) {
                    guess.setError( "Enter a number." );
                    guess.requestFocus();
                    return;
                }

                if (userNumber > 1000){
                    guess.setError( "Enter a number between 1 and 1000" );
                    guess.requestFocus();
                    return;

                }
                //add a try every time button is clicked
                numberOfTries++;


                if ( userNumber == theNumber && numberOfTries <= 5){
                    Toast.makeText( getApplicationContext(), "Superior Win! try: " + numberOfTries, Toast.LENGTH_SHORT ).show();
                    generateNumber();
                    Toast.makeText( getApplicationContext(), "New Game ", Toast.LENGTH_SHORT ).show();


                } else if (userNumber == theNumber && numberOfTries < 10){
                    Toast.makeText(getApplicationContext(), "Excellent Win! try: " + numberOfTries, Toast.LENGTH_SHORT).show();
                    generateNumber();
                    Toast.makeText( getApplicationContext(), "New Game ", Toast.LENGTH_SHORT ).show();

                } else if ( userNumber > theNumber){
                    Toast.makeText( getApplicationContext(), "Too High! try: " + numberOfTries, Toast.LENGTH_SHORT ).show();

                } else  {
                    Toast.makeText( getApplicationContext(), "Too low! try: " + numberOfTries, Toast.LENGTH_SHORT ).show();

                }


                 // if number of tries exceed 10, notice user that the game is over
                if (numberOfTries >= 10){
                    Toast.makeText( getApplicationContext(), "Out of tries, game over!", Toast.LENGTH_SHORT ).show();
                    generateNumber();
                    Toast.makeText( getApplicationContext(), "New Game ", Toast.LENGTH_SHORT ).show();

                }
                //clear text field after button is clicked
                guess.setText("");




            }
        });



        Button reset = (Button) findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                generateNumber();
                Toast.makeText( getApplicationContext(), "New Game", Toast.LENGTH_SHORT ).show();

            }
        });

        reset.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v ) {

                Toast.makeText( getApplicationContext(), "The Number is: " + theNumber, Toast.LENGTH_SHORT ).show();
              return true;
            }
        });

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

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show( getFragmentManager(), ABOUT_DIALOG_TAG );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

