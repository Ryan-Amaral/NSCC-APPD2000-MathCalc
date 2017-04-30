package com.w0279488.ryan.mobilemathcalculator;

/**
 * Created by Ryan on 2015-09-28.
 */
public class OperationNumbersPackage {

    private float numRight;
    private float numLeft;
    private char operator;

    public float getNumRight() {
        return numRight;
    }

    public void setNumRight(float numRight) {
        this.numRight = numRight;
    }

    public float getNumLeft() {
        return numLeft;
    }

    public void setNumLeft(float numLeft) {
        this.numLeft = numLeft;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public void extractValues (String equation){
        // list of operators to look for each one
        char[] operators = {'*', '/', '+', '-'};
        int indexOfOperator = 0;

        // index of operator, take only if index in string is > 0 so don't get negative of first number
        for(int i = 0; i < operators.length; i++){
            if ((equation.indexOf(operators[i]) > 0)) {
                setOperator(operators[i]); // set the operator
                indexOfOperator = equation.indexOf(operators[i]);
                break;
            }else if (i == 3){
                setOperator('-'); // set the operator
                indexOfOperator = equation.substring(1).indexOf('-') + 1;
                break;
            }
        }

        // set first number (everything before operator)
        setNumLeft(Float.parseFloat(equation.substring(0, indexOfOperator)));
        // set second number (everything after operator)
        setNumRight(Float.parseFloat(equation.substring(indexOfOperator + 1, equation.length())));
    }
}
