package xoClientModel;

import javafx.scene.control.Button;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Evaluation {

    public static int winRow;
    public static int winCol;

    public static int evaluate(Button b[][]) {

        for (int row = 0; row < 3; row++) {
            if (b[row][0].getText().equals(b[row][1].getText()) && b[row][1].getText().equals(b[row][2].getText())) {

                if (b[row][0].getText().equals(HardLevel.player)) {
                    winRow = row;
                    winCol = 123;
                    return 10;

                } else if (b[row][0].getText().equals(HardLevel.opponent)) {
                    winRow = row;
                    winCol = 123;
                    return -10;
                }

            }
        }

        for (int col = 0; col < 3; col++) {
            if (b[0][col].getText().equals(b[1][col].getText()) && b[1][col].getText().equals(b[2][col].getText())) {

                if (b[0][col].getText().equals(HardLevel.player)) {
                    winRow = 123;
                    winCol = col;
                    return 10;
                } else if (b[0][col].getText().equals(HardLevel.opponent)) {
                    winRow = 123;
                    winCol = col;
                    return -10;
                }

            }
        }

        if (b[0][0].getText().equals(b[1][1].getText()) && b[1][1].getText().equals(b[2][2].getText())) {

            if (b[0][0].getText().equals(HardLevel.player)) {
                winRow = 20;
                winCol = 20;
                return 10;
            } else if (b[0][0].getText().equals(HardLevel.opponent)) {
                winRow = 20;
                winCol = 20;
                return -10;
            }

        }

        if (b[0][2].getText().equals(b[1][1].getText()) && b[1][1].getText().equals(b[2][0].getText())) {
            if (b[0][2].getText().equals(HardLevel.player)) {
                winRow = 40;
                winCol = 40;
                return 10;
            } else if (b[0][2].getText().equals(HardLevel.opponent)) {
                winRow = 40;
                winCol = 40;
                return -10;
            }

        }
        return 0;
    }
}
