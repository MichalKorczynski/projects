import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Minesweeper {

    private class MineTile extends JButton { //one field in game
        int r;
        int c;

        public MineTile(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    int tileSize = 70; // size of tiles
    int numRows = 8; // number of rows
    int numColumns = 8; // number of columns
    int boardWidth = numColumns * tileSize; // width of the board
    int boardHeight = numRows * tileSize; // width of the board

    JFrame frame = new JFrame("Minesweeper"); //window declaraton

    // declaration of the game elements
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    // 2D array containing all tiles in the game
    MineTile[][] board = new MineTile[numRows][numColumns];
    // List of the tiles with the bomb
    ArrayList<MineTile> mineList;

    int tilesClicked = 0;
    boolean gameOver = false;

    Minesweeper() {
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial",Font.BOLD,25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(numRows,numColumns));
        frame.add(boardPanel);

        for(int r = 0; r < numRows; r++) {
            for(int c = 0; c < numColumns; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(0,0,0,0));
                tile.setFont(new Font("Arial Unicode MS",Font.PLAIN, 45));
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed( MouseEvent e) {
                        if(gameOver) {
                            return;
                        }
                        MineTile tile = (MineTile) e.getSource();

                        //left click
                        if(e.getButton() == MouseEvent.BUTTON1) {
                            if(tile.getText() == "") {
                                if(mineList.contains(tile)) {
                                    revealMines();
                                }
                                else {
                                    checkMine(tile.r,tile.c);
                                }
                            }
                        }
                        else if(e.getButton()==MouseEvent.BUTTON3) {
                            if(tile.getText()=="" && tile.isEnabled()) {
                                tile.setText("🚩");
                            }
                            else if(tile.getText() =="🚩") {
                                tile.setText("");
                            }
                        }
                    }
                });
                boardPanel.add(tile);
            }
        }
        frame.setVisible(true);

        setMines();
    }

    void setMines() { // function setting bomb on tiles
        mineList = new ArrayList<MineTile>();

        Random random = new Random();
        int r;
        int c;
        for(int i = 0; i <= 9; i++) {
            c = random.nextInt(0,8);
            r = random.nextInt(0,8);
            MineTile tile = board[r][c];
            if(!mineList.contains(tile)) {
                mineList.add(tile);
            }
            else {
                i--;
                continue;
            }
        }
    }

    void revealMines() { // function revealing mines after loosing the game
        for(int i = 0; i < mineList.size();i++) {
            MineTile tile = mineList.get(i);
            tile.setText("💣");
        }
        gameOver = true;
        boardPanel.setBackground(Color.red);
        textLabel.setText("GAME OVER");
    }

    void checkMine(int r, int c) {
        if(r < 0 || r >=numRows || c < 0 || c>= numColumns) { // if there is no tile like r c then returning 0 found tiles
            return;
        }

        MineTile tile = board[r][c]; //one tile from the list
        if(!tile.isEnabled()) {
            return;
        }
        tile.setEnabled(false); // disable the tile that is checked so the user can't click it again
        tilesClicked+=1;

        int minesFound = 0; // counter showin how many mines are around the tile

        // cheking top 3
        minesFound += countMine(r-1,c-1); // function counting mines
        minesFound += countMine(r-1,c);
        minesFound += countMine(r-1,c+1);
        // cheking left and right

        minesFound += countMine(r,c-1);
        minesFound += countMine(r,c+1);

        // cheking bottom 3
        minesFound += countMine(r+1,c-1);
        minesFound += countMine(r+1,c);
        minesFound += countMine(r+1,c+1);

        if(minesFound>0) { // Displaying the number of found mines
            tile.setText(Integer.toString(minesFound));
        }
        else { // Displaying empty string in case of not finding any mines 
            tile.setText("");

            // cheking top 3
            checkMine(r-1,c-1); // function counting mines
            checkMine(r-1,c);
            checkMine(r-1,c+1);
            // cheking left and right

            checkMine(r,c-1);
            checkMine(r,c+1);

            // cheking bottom 3
            checkMine(r+1,c-1);
            checkMine(r+1,c);
            checkMine(r+1,c+1);
        }

        if(tilesClicked == numRows *numColumns - mineList.size()) {
            gameOver = true;
            boardPanel.setBackground(Color.green);
            textLabel.setText("You won!");
        }
    }

    int countMine(int r, int c) { // function counting mines around the tile
        if(r < 0 || r >=numRows || c < 0 || c>= numColumns) { // if there is no tile like r c then returning 0 found tiles
            return 0;
        }
        if(mineList.contains(board[r][c])) { // else if there is a mine on checked tile returning 1
            return 1;
        }
        return 0;
    }
}
