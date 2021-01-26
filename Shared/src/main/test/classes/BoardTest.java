package classes;

import Enums.SquareState;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testPlaceSquare(){
        Board board = new Board();
        board.placeSquare(new Position(0,0), SquareState.Cross);
        assertEquals(SquareState.Cross, board.getBoard()[0][0]);
    }

    @Test
    public void testPlaceSquareTaken(){
        Board board = new Board();
        board.placeSquare(new Position(0,0), SquareState.Cross);
        assertEquals(false, board.placeSquare(new Position(0,0), SquareState.Cross));
    }

    @Test
    public void testMovesAvailable(){
        Board board = new Board();
        board.placeSquare(new Position(0,0), SquareState.Cross);
        assertEquals(true, board.anyMovesAvailable());
    }

    @Test
    public void testMovesAvailableFalse(){
        Board board = new Board();
        board.setSquareAt(new Position(0,0), SquareState.Cross);
        board.setSquareAt(new Position(0,1), SquareState.Circle);
        board.setSquareAt(new Position(0,2), SquareState.Cross);
        board.setSquareAt(new Position(1,0), SquareState.Circle);
        board.setSquareAt(new Position(1,1), SquareState.Cross);
        board.setSquareAt(new Position(1,2), SquareState.Circle);
        board.setSquareAt(new Position(2,0), SquareState.Circle);
        board.setSquareAt(new Position(2,1), SquareState.Cross);
        board.setSquareAt(new Position(2,2), SquareState.Circle);
        assertEquals(false, board.anyMovesAvailable());
    }

}
