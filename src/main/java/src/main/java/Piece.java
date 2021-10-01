package src.main.java;

import javax.swing.*;

public class Piece{

    // could possibly add a Sprite or character association to be added to each piece when generated on the BoardState
    private ImageIcon chessPieceIcon; // image to display on the checkerboard

    private PieceInfo type;
    private PieceInfo affiliation;

    private boolean hasMoved;

    public Piece() {
        //Default constructor for specifically blank pieces
        type = PieceInfo.BLANK;
    }

    public Piece(PieceInfo _type, PieceInfo _affiliation){
        //Constructor to handle pieces that matter for castling before moving
        type = _type;
        affiliation = _affiliation;

        hasMoved = false;
    }

    public Piece(PieceInfo _type, PieceInfo _affiliation, ImageIcon _chessPieceIcon) {
        type = _type;
        affiliation = _affiliation;
        chessPieceIcon = _chessPieceIcon;
        hasMoved = false;
    }

    public Piece(PieceInfo _type, PieceInfo _affiliation, boolean _hasMoved){
        //Constructor to handle piece update after moving
        type = _type;
        affiliation = _affiliation;
        //this should usually get updated
        hasMoved = _hasMoved;
    }

    public Piece(PieceInfo _type, PieceInfo _affiliation, boolean _hasMoved, ImageIcon _chessPieceIcon) {
        type = _type;
        affiliation = _affiliation;
        hasMoved = _hasMoved;
        chessPieceIcon = _chessPieceIcon;
    }

    public PieceInfo getType() {
        return type;
    }
    public void setType(PieceInfo _type) {
        this.type = _type;
    }

    public PieceInfo getAffiliation() {
        return affiliation;
    }
    public void setAffiliation(PieceInfo _affiliation) {
        this.affiliation = _affiliation;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean _hasMoved) {
        this.hasMoved = _hasMoved;
    }

    public ImageIcon getChessPieceIcon() {return chessPieceIcon; }
    public void setChessPieceIcon(ImageIcon _chessPieceIcon) { this.chessPieceIcon = _chessPieceIcon; }
}
