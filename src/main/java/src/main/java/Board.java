package src.main.java;

import javax.swing.*;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Board {

    //TODO: LOOK INTO A METHOD OF SELECTING IMAGES ELSEWHERE ON THE COMPUTER SO THE USER CAN USE THEIR OWN CHESS PIECES
    //Load all piece images here, so that while the board is being instantiated the images for the pieces are as well.
    public ImageIcon whiteRook = new ImageIcon("res/whiteRook.png");
    public ImageIcon whiteKnight = new ImageIcon("res/whiteKnight.png");
    public ImageIcon whiteBishop = new ImageIcon("res/whiteBishop.png");
    public ImageIcon whiteQueen = new ImageIcon("res/whiteQueen.png");
    public ImageIcon whiteKing = new ImageIcon("res/whiteKing.png");
    public ImageIcon whitePawn = new ImageIcon("res/whitePawn.png");

    public ImageIcon blackRook = new ImageIcon("res/blackRook.png");
    public ImageIcon blackKnight = new ImageIcon("res/blackKnight.png");
    public ImageIcon blackBishop = new ImageIcon("res/blackBishop.png");
    public ImageIcon blackQueen = new ImageIcon("res/blackQueen.png");
    public ImageIcon blackKing = new ImageIcon("res/blackKing.png");
    public ImageIcon blackPawn = new ImageIcon("res/blackPawn.png");

    public ImageIcon blank = new ImageIcon("res/Blank.png");

    private Piece[][] boardState = new Piece[8][8];

    public Board() {

        boardState = new Piece[][]{
                {new Piece(PieceInfo.ROOK, PieceInfo.WHITE), new Piece(PieceInfo.KNIGHT, PieceInfo.WHITE), new Piece(PieceInfo.BISHOP, PieceInfo.WHITE), new Piece(PieceInfo.QUEEN, PieceInfo.WHITE), new Piece(PieceInfo.KING, PieceInfo.WHITE), new Piece(PieceInfo.BISHOP, PieceInfo.WHITE), new Piece(PieceInfo.KNIGHT, PieceInfo.WHITE), new Piece(PieceInfo.ROOK, PieceInfo.WHITE)},
                {new Piece(PieceInfo.PAWN, PieceInfo.WHITE), new Piece(PieceInfo.PAWN, PieceInfo.WHITE), new Piece(PieceInfo.PAWN, PieceInfo.WHITE), new Piece(PieceInfo.PAWN, PieceInfo.WHITE), new Piece(PieceInfo.PAWN, PieceInfo.WHITE), new Piece(PieceInfo.PAWN, PieceInfo.WHITE), new Piece(PieceInfo.PAWN, PieceInfo.WHITE), new Piece(PieceInfo.PAWN, PieceInfo.WHITE),},
                {new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece()},
                {new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece()},
                {new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece()},
                {new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece(), new Piece()},
                {new Piece(PieceInfo.PAWN, PieceInfo.BLACK), new Piece(PieceInfo.PAWN, PieceInfo.BLACK), new Piece(PieceInfo.PAWN, PieceInfo.BLACK), new Piece(PieceInfo.PAWN, PieceInfo.BLACK), new Piece(PieceInfo.PAWN, PieceInfo.BLACK), new Piece(PieceInfo.PAWN, PieceInfo.BLACK), new Piece(PieceInfo.PAWN, PieceInfo.BLACK), new Piece(PieceInfo.PAWN, PieceInfo.BLACK),},
                {new Piece(PieceInfo.ROOK, PieceInfo.BLACK), new Piece(PieceInfo.KNIGHT, PieceInfo.BLACK), new Piece(PieceInfo.BISHOP, PieceInfo.BLACK), new Piece(PieceInfo.QUEEN, PieceInfo.BLACK), new Piece(PieceInfo.KING, PieceInfo.BLACK), new Piece(PieceInfo.BISHOP, PieceInfo.BLACK), new Piece(PieceInfo.KNIGHT, PieceInfo.BLACK), new Piece(PieceInfo.ROOK, PieceInfo.BLACK)}
        };
    }

    public Board(Piece[][] _boardState){

        boardState = _boardState;
    }

    public int movePiece(int oldY, int oldX, int newY, int newX){

        /* Return codes:
        0: The move was made.
        1: The move failed.
        2: White wins.
        3: Black wins.
        4: Error.
         */

        Piece oldPiece = boardState[oldY][oldX];

        if(oldPiece.getType() != PieceInfo.BLANK && isMoveValid(oldY, oldX, newY, newX)){

            System.out.println("Applying movement");
            Piece[][] tempBoard = boardState;
            tempBoard[oldY][oldX] = new Piece();
            tempBoard[newY][newX] = oldPiece;

            int kingX = 0; // had to initialize with a value because java was pissed
            int kingY = 0;

            for(int y = 0; y < 7; y++){ // this is bad code //TODO: Make this good code... soon(tm)

                for(int x = 0; x < 7; x++){

                    if(boardState[y][x].getAffiliation() == oldPiece.getAffiliation() && boardState[y][x].getType() == PieceInfo.KING){

                        kingX = x;
                        kingY = y;
                    }
                }
            }

            if(!isKingInCheck(tempBoard, oldPiece.getAffiliation(), kingX, kingY)){

                oldPiece.setHasMoved(true);

                boardState[oldY][oldX] = new Piece();
                boardState[newY][newX] = oldPiece;

                return 0; //was previously true
            }

            for(int y = 0; y < 7; y++){ // this is bad code //TODO: Make this good code... soon(tm)

                for(int x = 0; x < 7; x++){

                    if(boardState[y][x].getAffiliation() != oldPiece.getAffiliation() && boardState[y][x].getType() == PieceInfo.KING){ // this grabs the opposite king

                        kingX = x;
                        kingY = y;
                    }
                }
            }

            if(isKingInCheckMate(kingX, kingY)){ // if opposite king is in check

                switch(boardState[kingY][kingX].getAffiliation()){

                    case BLACK: // black is in checkmate; white won
                        return 2;
                    case WHITE: // white is in checkmate; black won
                        return 3;
                }
            }

            return 1; //was false
        }

        return 1; //was false
    }

    /**validLocalCoords is specific for Knight move calculation, it's a list of where a Knight piece can legally move on the board,
     * and this method is used to take the coordinates from a local perspective and globalize them,
     * so we can use them to move the piece **/
    static final int[][] validLocalCoords = {{-1,2}, {1,2}, {2,1}, {2,-1}, {1,-2}, {-1,-2}, {-2,-1}, {-2,1}}; //This really shouldn't ever be changed

    private boolean isKnightMoveValid(int oldX, int oldY, int newX, int newY) { // TODO: this is more efficient than the old method but i think there are bugs waiting to happen

        int[][] validLocalCoords = {{-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}};
        int[] coordDiff = {oldY - newY, oldX - newX};

        boolean flag = false;

        for(int i = 0; i < validLocalCoords.length; i++)
            if(coordDiff[0] == validLocalCoords[i][0] && coordDiff[1] == validLocalCoords[i][1])
                flag = true;

        return flag;
    }

    public boolean isMoveValid(int oldY, int oldX, int newY, int newX) {

        if(oldY < 0 || oldY > 7 || oldX < 0 || oldX > 7 || newY < 0 || newY > 7 || newX < 0 || newX > 7) // verify in range
            return false;

        if(oldY == newY && oldX == newX) // The same spot is not a valid move
            return false;

        Piece oldPiece = boardState[oldY][oldX];
        Piece newPiece = boardState[newY][newX];

        if(oldPiece.getAffiliation() != newPiece.getAffiliation()){

            int distanceX = abs(newX - oldX);
            int distanceY = abs(newY - oldY);

            int directionX; // had to instantiate these here because IntelliJ doesn't understand how scope works
            int directionY;
            int checkX;
            int checkY;

            boolean imLosingMyMind = true;
            // the entire switch case is dedicated to exposing that a move is illegal, otherwise it'll be treated as legal
            switch(oldPiece.getType()){

                case ROOK:
                    if (distanceX > 0 && distanceY > 0) {
                        return false;
                    }
                    if (distanceX > 0) {
                        for (int i = oldX + (newX-oldX)/(distanceX);  i>0 || i<7; i+=(newX-oldX)/(distanceX) ) {
                            if (boardState[oldY][i].getType() != PieceInfo.BLANK && i!=newX) {
                                return false;
                            }//this if statement checks if something is in the path between the rook and where the selection if the user's selection is on the X axis
                            if (i==newX) break;
                        }
                    }// be gay
                    if (distanceY > 0) {
                        for (int i = oldY + (newY-oldY)/(distanceY);  i>0 || i<7; i+=(newY-oldY)/(distanceY) ) {
                            if (boardState[i][oldX].getType() != PieceInfo.BLANK && i!=newY) {
                                return false;
                            }//this if statement checks if something is in the path between the rook and where the user selected if the selection is on the Y axis
                            if (i==newY) break;
                        }
                    }// be straight

                    break;
                case KNIGHT:
                    // has the weird L moves, can go over enemies and friendlies
                    if(!isKnightMoveValid(oldX, oldY, newX, newY))
                        return false;
                    break;
                case BISHOP:
                    if(Math.abs(oldX-newX) != Math.abs(oldY-newY))
                        return false; // x and y have the same magnitude magnitits
                    //TODO: check to see if the equation (newX-oldX)/((newX-oldX)*-1) could just be declared above switch case and re-used
                    directionX = Math.max(-1, Math.min(1, (oldX-newX)*-1)); // get the direction to iterate
                    directionY = Math.max(-1, Math.min(1, (oldY-newY)*-1));

                    checkX = oldX + directionX;
                    checkY = oldY + directionY;

                    imLosingMyMind = true; // TODO: fix this stupid ass name

                    while(checkX < 8 && checkY < 8 && checkX > 0 && checkY > 0) {

                        if (boardState[checkY][checkX].getType() != PieceInfo.BLANK && checkX != newX && checkY != newY)
                            imLosingMyMind =  false; // Bishop cannot pass through any pieces

                        if(boardState[checkY][checkX].getAffiliation() == oldPiece.getAffiliation() && checkX == newX && checkY == newY)
                            imLosingMyMind =  false; // Bishop cannot end on a piece of the same affiliation

                        if(checkX == newX)
                            break; // Reached destination without issues

                        checkX += directionX; // Iterate to next spot in path
                        checkY += directionY;
                    }

                    if(!imLosingMyMind)
                        return false;

                    break;
                case QUEEN:
                    if(Math.abs(oldX-newX) == Math.abs(oldY-newY)){ // angled move

                        directionX = Math.max(-1, Math.min(1, (oldX-newX)*-1)); // get the direction to iterate
                        directionY = Math.max(-1, Math.min(1, (oldY-newY)*-1));

                        checkX = oldX + directionX;
                        checkY = oldY + directionY;

                        imLosingMyMind = true;

                        while(checkX < 8 && checkY < 8 && checkX > 0 && checkY > 0) {

                            if (boardState[checkY][checkX].getType() != PieceInfo.BLANK && checkX != newX && checkY != newY)
                                imLosingMyMind =  false; // Bishop cannot pass through any pieces

                            if(boardState[checkY][checkX].getAffiliation() == oldPiece.getAffiliation() && checkX == newX && checkY == newY)
                                imLosingMyMind =  false; // Bishop cannot end on a piece of the same affiliation

                            if(checkX == newX)
                                break; // Reached destination without issues

                            checkX += directionX; // Iterate to next spot in path
                            checkY += directionY;
                        }

                        if(!imLosingMyMind)
                            return false;
                    }else{

                        if((distanceX == 0 && distanceY > 0) || (distanceY == 0 && distanceX > 0)){ // sideways move

                            if (distanceX > 0 && distanceY > 0) {
                                return false;
                            }// it appears the code below is re-used from rook math, could possibly be annexed and popped into a separate  method and used for both rook and queen
                            if (distanceX > 0) {
                                for (int i = oldX + (newX-oldX)/(distanceX);  i>0 || i<7; i+=(newX-oldX)/(distanceX) ) {
                                    if (boardState[oldY][i].getType() != PieceInfo.BLANK && i!=newX) {
                                        return false;
                                    }//this if statement checks if something is in the path between the rook and where the selection if the user's selection is on the X axis
                                    if (i==newX) break;
                                }
                            }
                            if (distanceY > 0) {
                                for (int i = oldY + (newY-oldY)/(distanceY);  i>0 || i<7; i+=(newY-oldY)/(distanceY) ) {
                                    if (boardState[i][oldX].getType() != PieceInfo.BLANK && i!=newY) {
                                        return false;
                                    }//this if statement checks if something is in the path between the rook and where the selection if the user's selection is on the Y axis
                                    if (i==newY) break;
                                }
                            }
                        }else
                            return false; // neither move types selected
                    }
                    break;
                case KING:
                    if (distanceX > 1 || distanceY > 1) {
                        return false; // if the king is requested to move further than 1 tile, the move is invalid
                    }
                    if (newPiece.getAffiliation() == oldPiece.getAffiliation()) {
                        return false; // the king cannot move to a position currently occupied by a piece of the same affiliation
                    }

                    for(int y = 0; y < 8; y++){

                        for(int x = 0; x < 8; x++){

                            if(boardState[y][x].getAffiliation() != oldPiece.getAffiliation())
                                if(isMoveValid(y, x, newY, newX))
                                    return false;    // this checks if any opposite pieces can move to the kings new spot. the kind cannot put himself into check.
                        }
                    }
                    break;
                case PAWN:
                    if(newY - oldY > 0 && oldPiece.getAffiliation() == PieceInfo.BLACK) // Pawns cannot move backwards
                        return false;
                    if(newY - oldY < 0 && oldPiece.getAffiliation() == PieceInfo.WHITE)
                        return false;

                    if(!oldPiece.getHasMoved()) { // First move bonus check
                        if (distanceY > 2) {
                            return false;
                        }
                    } else {
                        if (distanceY > 1) {
                            return false;
                        }
                    }
                    // I feel like the math here is actually done incorrectly, since pawns cannot attack directly forward but only to the top right and top left of themselves
                    if(distanceY == 1){ // Taking a piece
                        if(distanceX > 0){
                            if(newPiece.getType() == PieceInfo.BLANK)
                                return false;
                            if(distanceX > 1)
                                return false;
                        }
                    }else
                        if(distanceX > 0)
                            return false;

                    if(distanceX == 0 && distanceY > 0){ // Pawns cannot move forward through another piece

                        if(oldPiece.getAffiliation() == PieceInfo.WHITE){

                            if(boardState[oldY+1][oldX].getType() != PieceInfo.BLANK)
                                return false;
                        }
                        if(oldPiece.getAffiliation() == PieceInfo.BLACK){

                            if(boardState[oldY-1][oldX].getType() != PieceInfo.BLANK)
                                return false;
                        }
                    }

                    break;
                default:
                    System.out.println("Invalid piece detected of type: " + newPiece.getType().toString());
                    break;
            }
            return true;
        }

        return false;
    }

    public boolean isKingInCheck(Piece[][] boardToCheck, PieceInfo kingColor, int kingX, int kingY){

        for(int y = 0; y < 7; y++)
            for(int x = 0; x < 7; x++) // Iterate through the board
                if(boardToCheck[y][x].getType() != PieceInfo.BLANK && kingColor != boardToCheck[y][x].getAffiliation()) // When a piece is found of the opposite color
                    if(isMoveValid(y, x, kingY, kingX)) // If it can move to the king's spot
                        return true; // The king is in check

        return false; // The king is not in check
    }

    public boolean isKingInCheckMate(int kingX, int kingY){ // this is terrible, yes, but its efficient. So suck my nuts.
        //                                  left                          up                                                                                                       top left                                                                                                  right                                                                                                   below                                                                                               bottom right                                                                                             bottom left                                                                                                   top right
        return (isMoveValid(kingX, kingY, kingX-1, kingY) == isMoveValid(kingX, kingY, kingX, kingY-1) && isMoveValid(kingX, kingY, kingX-1, kingY) == isMoveValid(kingX, kingY, kingX-1, kingY-1) && isMoveValid(kingX, kingY, kingX-1, kingY) == isMoveValid(kingX, kingY, kingX+1, kingY) && isMoveValid(kingX, kingY, kingX-1, kingY) == isMoveValid(kingX, kingY, kingX, kingY+1) && isMoveValid(kingX, kingY, kingX-1, kingY) == isMoveValid(kingX, kingY, kingX+1, kingY+1) && isMoveValid(kingX, kingY, kingX-1, kingY) == isMoveValid(kingX, kingY, kingX-1, kingY+1) && isMoveValid(kingX, kingY, kingX-1, kingY) == isMoveValid(kingX, kingY, kingX+1, kingY-1) && !isMoveValid(kingX, kingY, kingX+1, kingY-1) && isKingInCheck(boardState, getPiece(kingX, kingY).getAffiliation(), kingX, kingY));
    }

    public Piece getPiece(int xPos, int yPos) {
        return boardState[xPos][yPos];
    }

    public Piece[][]  getBoardState(){

        return boardState;
    }

    public void setBoardState(Piece[][] _boardState){

        boardState = _boardState;
    }
}
