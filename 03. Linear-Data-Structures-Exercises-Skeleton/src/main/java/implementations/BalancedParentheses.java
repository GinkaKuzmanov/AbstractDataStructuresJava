package implementations;

import interfaces.Solvable;

import java.util.ArrayDeque;


public class BalancedParentheses implements Solvable {
    private final String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        //{[()]}
        ArrayDeque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < parentheses.length(); i++) {
            deque.push(this.parentheses.charAt(i));
        }

        while(!deque.isEmpty()){
            int leftBracket = deque.removeLast();
            int rightBracket = deque.removeFirst();
            if(leftBracket + 1 != rightBracket && leftBracket + 2 != rightBracket){
                return false;
            }
        }

        return true;
    }
}
