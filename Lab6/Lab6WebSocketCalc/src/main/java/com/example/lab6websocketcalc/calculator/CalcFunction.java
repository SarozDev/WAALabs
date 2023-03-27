package com.example.lab6websocketcalc.calculator;

public class CalcFunction {
    public static Double calculateResult(String input) throws NumberFormatException{
        if(input.length() == 0){
            return 0.0;
        }

        Double prevResult = null;
        Character prevOperator = null;

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0;i<input.length();i++){
            char c = input.charAt(i);

            if(Character.isDigit(c)){
                stringBuilder.append(c);
            }

            if(!Character.isDigit(c) || (i == input.length() -1)){
                double number = Double.parseDouble(stringBuilder.toString());

                if(prevResult == null){
                    prevResult = number;
                }else{
                    prevResult = performOperation(prevOperator, prevResult, number);
                }

                if(i < input.length() -1){
                    prevOperator = c;
                }

                stringBuilder = new StringBuilder();
            }
        }

        return prevResult;
    }

    private static double performOperation(char operator, double prevNumber, double currentNumber){

        return switch (operator) {
            case '+' -> prevNumber + currentNumber;
            case '-' -> prevNumber - currentNumber;
            case '*' -> prevNumber * currentNumber;
            case '/' -> prevNumber / currentNumber;
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }
}
