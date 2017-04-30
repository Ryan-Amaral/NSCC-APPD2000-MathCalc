package com.w0279488.ryan.mobilemathcalculator;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // constants and controls
    private final static String TEXT_ON_DISPLAY = "TEXT_ON_DISPLAY"; // key for display text
    private String currentTextOnDisplay; // the text that is on the display

    private EditText edtTextDisplay; // the main display on calculator
    private Button btnClearAll; // clear all button
    private Button btnRemove; // remove last character
    private Button btn0; // the 0 button
    private Button btn1; // the 1 button
    private Button btn2; // the 2 button
    private Button btn3; // the 3 button
    private Button btn4; // the 4 button
    private Button btn5; // the 5 button
    private Button btn6; // the 6 button
    private Button btn7; // the 7 button
    private Button btn8; // the 8 button
    private Button btn9; // the 0 button
    private Button btnDecimal; // the . button
    private Button btnMultiply; // the * button
    private Button btnDivide; // the / button
    private Button btnPlus; // the + button
    private Button btnMinus; // the - button
    private Button btnEquals; // the = button

    // flags for knowing what is currently in the display,
    // for knowing what buttons are ok to press etc.
    private boolean flagFirstNumReady = false; // if the first number is in
    private boolean flagSecondNumReady = false; // if the second number is in
    private boolean flagOperatorPlaced = false; // if an operator was placed
    private boolean flagNegativeUsed1 = false; // if a negative symbol was used before the first number
    private boolean flagNegativeUsed2 = false; // if a negative symbol was used before the second number
    private boolean flagDecimalUsed1 = false; // if a decimal was used in the first number
    private boolean flagDecimalUsed2 = false; // if a decimal was used in the second number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if app started or restored
        if(savedInstanceState == null){ // app just started
            currentTextOnDisplay = "";
        }else{ // app was restored
            currentTextOnDisplay = savedInstanceState.getString(TEXT_ON_DISPLAY);
        }

        // instantiate the controls with existing controls
        edtTextDisplay = (EditText)findViewById(R.id.edtTxtDisplay);
        btnClearAll = (Button)findViewById(R.id.btnClearAll);
        btnRemove = (Button)findViewById(R.id.btnRemove);
        btn0 = (Button)findViewById(R.id.btn0);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);
        btnDecimal = (Button)findViewById(R.id.btnDecimal);
        btnMultiply = (Button)findViewById(R.id.btnMultiply);
        btnDivide = (Button)findViewById(R.id.btnDivide);
        btnPlus = (Button)findViewById(R.id.btnPlus);
        btnMinus = (Button)findViewById(R.id.btnMinus);
        btnEquals = (Button)findViewById(R.id.btnEquals);

        // set click listeners to all buttons
        btnClearAll.setOnClickListener(new CalculatorButtonOnClickListener('c')); // c denotes clear all
        btnRemove.setOnClickListener(new CalculatorButtonOnClickListener('r')); // r denotes remove character
        btn0.setOnClickListener(new CalculatorButtonOnClickListener('0'));
        btn1.setOnClickListener(new CalculatorButtonOnClickListener('1'));
        btn2.setOnClickListener(new CalculatorButtonOnClickListener('2'));
        btn3.setOnClickListener(new CalculatorButtonOnClickListener('3'));
        btn4.setOnClickListener(new CalculatorButtonOnClickListener('4'));
        btn5.setOnClickListener(new CalculatorButtonOnClickListener('5'));
        btn6.setOnClickListener(new CalculatorButtonOnClickListener('6'));
        btn7.setOnClickListener(new CalculatorButtonOnClickListener('7'));
        btn8.setOnClickListener(new CalculatorButtonOnClickListener('8'));
        btn9.setOnClickListener(new CalculatorButtonOnClickListener('9'));
        btnDecimal.setOnClickListener(new CalculatorButtonOnClickListener('.'));
        btnMultiply.setOnClickListener(new CalculatorButtonOnClickListener('*'));
        btnDivide.setOnClickListener(new CalculatorButtonOnClickListener('/'));
        btnPlus.setOnClickListener(new CalculatorButtonOnClickListener('+'));
        btnMinus.setOnClickListener(new CalculatorButtonOnClickListener('-'));
        btnEquals.setOnClickListener(new CalculatorButtonOnClickListener('='));
    }

    // Set flags based on what is currently in the display.
    private void setButtonFlags(){
        flagFirstNumReady = false;//
        flagSecondNumReady = false;//
        flagOperatorPlaced = false;//
        flagNegativeUsed1 = false;//
        flagNegativeUsed2 = false;//
        flagDecimalUsed1 = false;//
        flagDecimalUsed2 = false;//

        String displayText = edtTextDisplay.getText().toString(); // cache the display string value
        if(displayText.length() > 0){
            for(int i = 0; i < displayText.length(); i++) {
                // look for negative of first number or first num
                if (i == 0) {
                    if(displayText.charAt(0) == '-'){
                        flagNegativeUsed1 = true;
                    }else if(Character.isDigit(displayText.charAt(0))){
                        flagFirstNumReady = true;
                    }else if(displayText.charAt(0) == '.'){
                        flagDecimalUsed1 = true;
                    }
                }

                if(displayText.charAt(i) == '*' || displayText.charAt(i) == '/' ||
                        displayText.charAt(i) == '+' || displayText.charAt(i) == '-' &&
                        flagFirstNumReady && !flagOperatorPlaced){
                    flagOperatorPlaced = true;
                }else if(!flagFirstNumReady && Character.isDigit(displayText.charAt(i))){
                    flagFirstNumReady = true;
                }else if(flagOperatorPlaced && !flagSecondNumReady && displayText.charAt(i) == '-'){
                    flagNegativeUsed2 = true;
                }else if(flagOperatorPlaced && Character.isDigit(displayText.charAt(i))){
                    flagSecondNumReady = true;
                }else if(flagOperatorPlaced && !flagDecimalUsed2 && displayText.charAt(i) == '.'){
                    flagDecimalUsed2 = true;
                }else if(!flagOperatorPlaced && !flagDecimalUsed1 && displayText.charAt(i) == '.'){
                    flagDecimalUsed1 = true;
                }
            }
        }
    }

    // Disable and enable buttons as needed.
    private void buttonsDisableEnable(){
        // numbers always enabled

        // enable/disable minus button
        if(!flagFirstNumReady && flagNegativeUsed1){
            btnMinus.setEnabled(false);
        }else if(!flagOperatorPlaced && flagDecimalUsed1 && !flagFirstNumReady){
            btnMinus.setEnabled(false);
        }else if(!flagFirstNumReady && !flagNegativeUsed1){
            btnMinus.setEnabled(true);
        }else if(flagOperatorPlaced && !flagSecondNumReady && flagDecimalUsed2){
            btnMinus.setEnabled(false);
        }else if(flagFirstNumReady && flagOperatorPlaced && !flagSecondNumReady && flagNegativeUsed2){
            btnMinus.setEnabled(false);
        }else if(flagFirstNumReady && flagOperatorPlaced && !flagSecondNumReady && !flagNegativeUsed2){
            btnMinus.setEnabled(true);
        }else if(flagFirstNumReady && !flagOperatorPlaced){
            btnMinus.setEnabled(true);
        }else if(flagFirstNumReady && flagOperatorPlaced && !flagSecondNumReady && flagNegativeUsed2){
            btnMinus.setEnabled(false);
        }else if(!flagFirstNumReady && flagDecimalUsed1){
            btnMinus.setEnabled(false);
        }else if(flagFirstNumReady && flagDecimalUsed1 && !flagOperatorPlaced){
            btnMinus.setEnabled(false);
        }else if(flagFirstNumReady && flagOperatorPlaced && !flagNegativeUsed2){
            btnMinus.setEnabled(false);
        }

        // enable/disable decimal button
        if(!flagFirstNumReady && flagDecimalUsed1){
            btnDecimal.setEnabled(false);
        }else if(flagFirstNumReady && flagDecimalUsed1 && !flagOperatorPlaced){
            btnDecimal.setEnabled(false);
        }else if(!flagSecondNumReady && !flagOperatorPlaced && !flagDecimalUsed1){
            btnDecimal.setEnabled(true);
        }else if(flagOperatorPlaced && !flagDecimalUsed2){
            btnDecimal.setEnabled(true);
        }else if(flagOperatorPlaced && flagDecimalUsed2){
            btnDecimal.setEnabled(false);
        }

        // enable/disable other operators
        if(flagFirstNumReady && !flagOperatorPlaced){
            btnMultiply.setEnabled(true);
            btnDivide.setEnabled(true);
            btnPlus.setEnabled(true);
        }else if(flagOperatorPlaced || !flagFirstNumReady){
            btnMultiply.setEnabled(false);
            btnDivide.setEnabled(false);
            btnPlus.setEnabled(false);
        }

        if(flagSecondNumReady){
            btnMultiply.setEnabled(true);
            btnDivide.setEnabled(true);
            btnPlus.setEnabled(true);
            btnMinus.setEnabled(true);
        }

        // enable/disable equals
        btnEquals.setEnabled(flagSecondNumReady);
    }

    // Adds the character to the display.
    private void addCharacter(char character){
        // clear screen if error is on it
        if(edtTextDisplay.getText().toString().contains("Error")){
            clearDisplay();
        }

        // solve equation first if operator was pressed and is second
        if((character == '*' || character == '/' || character == '+' || character == '-') &&
                flagSecondNumReady){
            solveEquation();
        }
        edtTextDisplay.getText().append(character);
    }

    // Clears the display
    private void clearDisplay(){
        edtTextDisplay.getText().clear();
    }

    // Removes most recently added character from screen.
    private void removeCharacter(){
        if(edtTextDisplay.getText().toString().contains("Error")){
            clearDisplay();
        }
        // remove character if there is at-least one character
        if(edtTextDisplay.getText().toString().length() > 0){
            String tmpTxt = edtTextDisplay.getText().toString();
            edtTextDisplay.setText(tmpTxt.substring(0, tmpTxt.length() - 1));
        }
    }

    // Solves the equation and puts only answer in display.
    private void solveEquation(){
        // get numbers and sign out of text
        float numLeft;
        float numRight;
        char operator;

        // get actual values from this class
        OperationNumbersPackage opNumsPack = new OperationNumbersPackage();
        opNumsPack.extractValues(edtTextDisplay.getText().toString());

        numLeft = opNumsPack.getNumLeft();
        numRight = opNumsPack.getNumRight();
        operator = opNumsPack.getOperator();

        if(operator == '/' && numRight == 0){
            edtTextDisplay.setText("Error: can't divide by 0");
        }else {
            // get and format answer
            float answer = RyansAwesomeMath.solveEquation(numLeft, numRight, operator); // send off to math class
            //float rndAnswer = ((float)Math.round(answer * 100000f) / 100000f); // the final answer in appropriate format
            String strAnswer = Float.toString(answer);
            edtTextDisplay.setText(strAnswer); // send answer to display
        }
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

    // save the string in the display to the persisting save instance state bundle
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_ON_DISPLAY, currentTextOnDisplay);
    }

    // Custom listener to pass along parameter so only one listener is needed.
    private class CalculatorButtonOnClickListener implements View.OnClickListener{

        char buttonChar;
        // constructor to gather the passed char
        public CalculatorButtonOnClickListener(char buttonChar){
            this.buttonChar = buttonChar;
        }

        @Override
        public void onClick(View v)
        {
            if(buttonChar == '='){ // if equal pressed solve equation, solve equation
                solveEquation();
            }else if(buttonChar == 'c'){ // if clear pressed completely clear screen
                clearDisplay();
            }else if(buttonChar == 'r'){ // if remove pressed remove most recent character
                removeCharacter();
            }else {
                addCharacter(buttonChar);
            }

            setButtonFlags(); // set flags for the current text
            buttonsDisableEnable(); // with current state of buttons find which ones to enable and disable
        }
    }



}
