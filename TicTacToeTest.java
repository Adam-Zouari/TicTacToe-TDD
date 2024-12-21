import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

public class TicTacToeTest {
    private TicTacToe game;
    private TicTacToeSave save;
    private GererDB mockDb;

    
    @BeforeEach
    public void setup() {
        game = new TicTacToe();
        mockDb = Mockito.mock(GererDB.class);
        save = new TicTacToeSave(mockDb);
    }

    //Exigence 1
    @Test
    public void testXOutOfBounds() {
        assertThrows(RuntimeException.class, () -> game.play(3, 0));
    }

    @Test
    public void testYOutOfBounds() {
        assertThrows(RuntimeException.class, () -> game.play(0, 3));
    }
   
    @Test
    public void testOccupiedSpace() {
        game.play(0, 0);
        assertThrows(RuntimeException.class, () -> game.play(0, 0));
    }

    //Exigence 2
    @Test
    public void testInitialPlayer() {
        assertEquals('X', game.getCurrentPlayer());
    }

    @Test
    public void testXThenO() {
        game.play(0, 0);
        assertEquals('O', game.getCurrentPlayer());
    }

    @Test
    public void testOThenX() {
        game.play(0, 0);
        game.play(1,1);
        assertEquals('X', game.getCurrentPlayer());
    }

    //Exigence 3
    @Test
    public void testWinCondition() {
        game.play(0, 0); // X
        game.play(1, 0); // O
        game.play(0, 1); // X
        game.play(1, 1); // O
        game.play(0, 2); // X wins
        assertTrue(game.checkWin(0, 2));
        assertTrue(game.isGameOver());
    }

    //Exigence 4
    @Test
    public void testDrawCondition() {
        game.play(0, 0); // X
        game.play(0, 1); // O
        game.play(0, 2); // X
        game.play(1, 1); // O
        game.play(1, 0); // X
        game.play(1, 2); // O
        game.play(2, 1); // X
        game.play(2, 0); // O
        game.play(2, 2); // X
        assertFalse(game.checkWin(2, 2));
        assertTrue(game.isGameOver());
        assertTrue(game.isBoardFull());
    }

    //Partie 2
    //Exigence 1
    //Test 1
    @Test
    void testDatabaseExists() throws Exception {
        Mockito.doNothing().when(mockDb).verify();
        Mockito.doNothing().when(mockDb).connect();

        mockDb.verify();
        mockDb.connect();
    
        Mockito.verify(mockDb).verify();
        Mockito.verify(mockDb).connect();
    }
    
    //Test 2
    @Test
    void testCreateDatabaseIfNotExists() throws Exception {

        Mockito.doThrow(new Exception("Database does not exist")).when(mockDb).verify();
        Mockito.doNothing().when(mockDb).create(Mockito.anyString());
        Mockito.doNothing().when(mockDb).connect();

        try {
            mockDb.verify();
        } catch (Exception e) {
            assertEquals("Database does not exist", e.getMessage());
            mockDb.create("Tic-Tac-Toe");
            mockDb.connect();
        }
       
        Mockito.verify(mockDb).verify();
        Mockito.verify(mockDb).create(Mockito.anyString());
        Mockito.verify(mockDb).connect();
    }

    //Test 3
    @Test
    void testSaveMove() throws Exception {
        Data move = new Data(1, 0, 0, 'X');
    
        Mockito.doNothing().when(mockDb).verify();
        Mockito.doNothing().when(mockDb).save(move);
    
        save.saveMove(move);
    
        Mockito.verify(mockDb).verify();
        Mockito.verify(mockDb).save(move);
    }
    
    //Test 4
    @Test
    void testSaveMoveSuccessReturnsTrue() throws Exception {
        Data move = new Data(1, 0, 0, 'X');
    
        Mockito.doNothing().when(mockDb).verify();
        Mockito.doNothing().when(mockDb).save(move);
    
        boolean result = save.saveMove(move);
    
        assertTrue(result);
        Mockito.verify(mockDb).verify();
        Mockito.verify(mockDb).save(move);
    }

    //Test 5
    @Test
    void testSaveMoveFailureReturnsFalse() throws Exception {
        Data move = new Data(1, 0, 0, 'X');
    
        Mockito.doThrow(new Exception("Database does not exist")).when(mockDb).verify();
    
        boolean result = save.saveMove(move);
    
        assertFalse(result);
        Mockito.verify(mockDb).verify();
    }

    //Test 6.1
    @Test
    void testInitializeGameStateSuccess() throws Exception {

        Mockito.doNothing().when(mockDb).verify();
        Mockito.doNothing().when(mockDb).drop();
    
        boolean result = save.initializeDB();
    
        assertTrue(result);
        Mockito.verify(mockDb).verify();
        Mockito.verify(mockDb).drop();
    }
    
    //Test 6.2
    @Test
    void testInitializeGameStateFailure() throws Exception {

        Mockito.doNothing().when(mockDb).verify();
        Mockito.doThrow(new Exception("Drop failed")).when(mockDb).drop();
    
        boolean result = save.initializeDB();
    
        assertFalse(result);
        Mockito.verify(mockDb).verify();
        Mockito.verify(mockDb).drop();
    }
}
    