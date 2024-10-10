package com.example.lab1calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.faendir.rhino_android.RhinoAndroidHelper;
import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTV, solutionTV;
    MaterialButton buttonClear, buttonAC, buttonOpenP, buttonCloseP, buttonDot;

    MaterialButton button0, button1, button2, button3, button4;
    MaterialButton button5, button6, button7, button8, button9;

    MaterialButton buttonMul, buttonDiv, buttonAdd, buttonSub, buttonEq;

    /**
     * Creates the buttons and text fields
     * @param savedInstanceState
     */
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

        resultTV = findViewById(R.id.resultTV);
        solutionTV = findViewById(R.id.solutionTV);

        assignID(buttonClear, R.id.buttonClear);
        assignID(buttonAC, R.id.buttonAC);
        assignID(buttonOpenP, R.id.buttonOpenP);
        assignID(buttonCloseP, R.id.buttonCloseP);
        assignID(buttonDiv, R.id.buttonDiv);
        assignID(buttonMul, R.id.buttonMul);
        assignID(buttonAdd, R.id.buttonAdd);
        assignID(buttonSub, R.id.buttonSub);
        assignID(buttonDot, R.id.buttonDot);
        assignID(buttonEq, R.id.buttonEq);
        assignID(button0, R.id.button0);
        assignID(button1, R.id.button1);
        assignID(button2, R.id.button2);
        assignID(button3, R.id.button3);
        assignID(button4, R.id.button4);
        assignID(button5, R.id.button5);
        assignID(button6, R.id.button6);
        assignID(button7, R.id.button7);
        assignID(button8, R.id.button8);
        assignID(button9, R.id.button9);
    }

    /**
     * Assigns the button to an id
     * @param btn button that will be assigned an id
     * @param id id of button
     */
    void assignID(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);

    }

    /**
     * Updates resultTV and solutionTV
     * @param view the view of the calculator
     */
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTV.getText().toString();

        if(buttonText.equals("AC")){
            solutionTV.setText("0");
            resultTV.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solutionTV.setText(resultTV.getText());
            return;
        }
        if(buttonText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
        }else{
            if(dataToCalculate.equals("0")){
                dataToCalculate = buttonText;
            }else {
                dataToCalculate += buttonText;
            }
        }

        solutionTV.setText(dataToCalculate);

        String finalResult = getResults(dataToCalculate);
        if(!finalResult.equals("Err")) {
            resultTV.setText(finalResult);
        }
    }

    /**
     * Converts an expression into a number
     * @param data An expression in string form
     * @return The result in String form
     */
    String getResults(String data){
        try {
            RhinoAndroidHelper rhinoAndroidHelper = new RhinoAndroidHelper(this);
            Context context = rhinoAndroidHelper.enterContext();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

        }catch (Exception e){
            return "Err";
        }
    }
}