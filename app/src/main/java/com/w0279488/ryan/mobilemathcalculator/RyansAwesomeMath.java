package com.w0279488.ryan.mobilemathcalculator;

/**
 * Created by w0279488 on 9/29/2015.
 */
public class RyansAwesomeMath {

    public static float solveEquation(float leftNum, float rightNum, char operator){
        float result = 0;

        switch (operator){
            case '*':
                result = leftNum * rightNum;
                break;
            case '/':
                result = leftNum / rightNum;
                break;
            case '+':
                result = leftNum + rightNum;
                break;
            case '-':
                result = leftNum - rightNum;
                break;
            default:
                break;
        }

        return result;
    }
}
