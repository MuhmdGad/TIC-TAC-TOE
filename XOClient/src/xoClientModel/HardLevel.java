package xoClientModel;

import javafx.scene.control.Button;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class HardLevel {

    public static class Move {

        public int row, col;
    }

    public static String player, opponent;

    public static Boolean isMoveLeft(Button board[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().equals("")) {
                    return true;
                }
            }
        }
        return false;
    }

    static int minimax(Button board[][], int depth, Boolean isMax) {

        int score = Evaluation.evaluate(board);
        if (score == 10) {
            return score;
        }
        if (score == -10) {
            return score;
        }
        if (isMoveLeft(board) == false) {
            return 0;
        }

        if (isMax) {
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].getText().equals("")) {
                        board[i][j].setText(player);
                        best = Math.max(best, minimax(board, depth + 1, !isMax));
                        board[i][j].setText("");
                    }
                }
            }
            return best;

        } else {
            int best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].getText().equals("")) {
                        board[i][j].setText(opponent);
                        best = Math.min(best, minimax(board, depth + 1, !isMax));
                        board[i][j].setText("");
                    }
                }
            }
            return best;
        }
    }

    public static Move findBestMove(Button board[][]) {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().equals("")) {
                    board[i][j].setText(player);
                    int moveVal = minimax(board, 0, false);
                    board[i][j].setText("");
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }
}
