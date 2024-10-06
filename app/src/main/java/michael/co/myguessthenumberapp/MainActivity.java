package michael.co.myguessthenumberapp;

import static android.icu.text.DisplayContext.LENGTH_SHORT;
import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import michael.co.model.Guess;

public class MainActivity extends AppCompatActivity {
    
    private     EditText        etLow;
    private     EditText        etHigh;
    private     Button          btnStart;
    private     EditText        etYourGuess;
    private     Button          btnCheck;
    private     TextView        tvAppxOutput;
    private     ImageView       ivReactPicture;

    private     Guess           guessBusinessLogic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        guessBusinessLogic = new Guess();
        initializeViews();
        initializeOnClickListeners();
    }

    private void initializeViews() {
        etLow          = (EditText)     findViewById(R.id.etMinimum);
        etHigh         = (EditText)     findViewById(R.id.etMaximum);
        btnStart       = (Button)       findViewById(R.id.btnStart);
        etYourGuess    = (EditText)     findViewById(R.id.etYourGuess);
        btnCheck       = (Button)       findViewById(R.id.btnCheck);
        tvAppxOutput   = (TextView)     findViewById(R.id.tvAppxOutput);
        ivReactPicture = (ImageView)    findViewById(R.id.ivReactPicture);
    }

    private void initializeOnClickListeners() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (etLow.getText().toString().isEmpty() || etHigh.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Something is missing", LENGTH_LONG).show();
                }
                else{
                    int min = Integer.parseInt(etLow.getText().toString());
                    int max = Integer.parseInt(etHigh.getText().toString());
                    int[] repaired = guessBusinessLogic.repairMinMax(min, max);
                    min = repaired[0];
                    max = repaired[1];
                    if (min >= 10 && max <= 50){
                        guessBusinessLogic.setNumber(min, max);
                        // For testing:
                        //int x = guessBusinessLogic.getNumber();
                        //Toast.makeText(getApplicationContext(), x + "", LENGTH_LONG).show();                  
                        guessBusinessLogic.resetTriesCount();
                        etYourGuess.setText("");
                        tvAppxOutput.setText("Nothing to show");
                        btnCheck.setEnabled(true);
                        ivReactPicture.setImageResource(R.drawable.thinking_face);
                    }
                }
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(etYourGuess.getText().toString());
                int max = guessBusinessLogic.getMax();
                int min = guessBusinessLogic.getMin();
                if (num > max){
                    Toast.makeText(getApplicationContext(),"too big", LENGTH_LONG).show();
                    tvAppxOutput.setText("too big");
                    guessBusinessLogic.MakeTry();
                }
                else if (num < min){
                    Toast.makeText(getApplicationContext(),"too small", LENGTH_LONG).show();
                    tvAppxOutput.setText("too small");
                    guessBusinessLogic.MakeTry();
                }
                else{
                    if (num == guessBusinessLogic.getNumber()){
                        guessBusinessLogic.MakeTry();
                        int howManyTriesCounter = guessBusinessLogic.getTriesCount();
                        tvAppxOutput.setText(howManyTriesCounter + "");
                        if (howManyTriesCounter < 5){
                            ivReactPicture.setImageResource(R.drawable.happy_face);
                        }
                        else if (howManyTriesCounter <= 10){
                            ivReactPicture.setImageResource(R.drawable.neutral_face);
                        }
                        else {
                            ivReactPicture.setImageResource(R.drawable.sad_face);
                        }
                    }
                    else if (num < guessBusinessLogic.getNumber()){
                        guessBusinessLogic.MakeTry();
                        tvAppxOutput.setText("too small +");
                    }
                    else if (num > guessBusinessLogic.getNumber()){
                        guessBusinessLogic.MakeTry();
                        tvAppxOutput.setText("too big +");
                    }
                }
            }
        });
    }
}
