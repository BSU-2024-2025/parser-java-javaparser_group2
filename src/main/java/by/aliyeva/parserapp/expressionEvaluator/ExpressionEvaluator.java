package by.aliyeva.parserapp.expressionEvaluator;

import java.util.Stack;

public class ExpressionEvaluator {
public static int evaluateAndShowResult(String expression) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        try {
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);

                // Skip whitespace
                if (Character.isWhitespace(c)) {
                    continue;
                }

                // Check for comment
                if (c == '/' && i + 1 < expression.length() && expression.charAt(i + 1) == '/') {
                    while (i < expression.length() && expression.charAt(i) != '\n') {
                        i++;
                    }
                    continue;
                }

                if (Character.isDigit(c)) {
                    int num = 0;
                    while (Character.isDigit(c)) {
                        num = num * 10 + (c - '0');
                        i++;
                        if (i < expression.length()) {
                            c = expression.charAt(i);
                        } else {
                            break;
                        }
                    }
                    i--;
                    values.push(num);
                } else if (c == '(') {
                    operators.push(c);
                } else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        if (values.size() < 2) throw new Exception("Invalid expression");
                        values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                    }
                    operators.pop();
                } else if (isOperator(c)) {
                    if ((c == '-' || c == '+') && (i == 0 || expression.charAt(i - 1) == '(' || isOperator(expression.charAt(i - 1)))) {
                        if (i + 1 < expression.length() && (expression.charAt(i + 1) == '+' || expression.charAt(i + 1) == '-')) {
                            throw new Exception("Invalid expression");
                        }
                        values.push(0);
                        operators.push(c);
                    } else {
                        while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                            if (values.size() < 2) throw new Exception("Invalid expression");
                            values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                        }
                        operators.push(c);
                    }
                } else {
                    throw new Exception("Invalid character");
                }
            }
            while (!operators.isEmpty()) {
                if (values.size() < 2) throw new Exception("Invalid expression");
                values.push(applyOp(operators.pop(), values.pop(), values.pop()));
            }
            return values.pop();
        } catch (Exception e) {
            throw new RuntimeException("Error evaluating expression", e);
        }
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static boolean hasPrecedence(char op1, char op2) {
        int precedence1 = getPrecedence(op1);
        int precedence2 = getPrecedence(op2);

        return precedence1 <= precedence2;
    }

    public static int getPrecedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    public static int applyOp(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
        }
        return 0;
    }
}



