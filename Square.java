public class Square {
    private Piece piece;
    private int row;
    private int col;
    public Square(){
        row=-1;
        col=-1;
        piece=new Piece(" ",-1,-1);
    }

    public Square(int row, int col, Piece piece){
        this.row=row;
        this.col=col;
        this.piece=piece;
    }

    public String toString(){
        return "[row: " + row+ ", col: " + col+ "]";
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setCol(Piece piece){
        this.piece = piece;
    }
}
