import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;


public class TicTacToeTest2 {
    private TicTacToe game;
    private GererDB mockDb;

    
    @BeforeEach
    public void setup() {
        mockDb = Mockito.mock(GererDB.class);
        game = new TicTacToe(mockDb);
    }
    
    //Partie 2
    //Exigence 2
    //Test 1
    @Test
    void testDatabaseCreationOnNewGame() throws Exception {
    
        Mockito.doNothing().when(mockDb).create(Mockito.anyString());
        Mockito.doNothing().when(mockDb).connect();
        Mockito.doNothing().when(mockDb).verify();
    
        game.getDB().verify();
    
        Mockito.verify(mockDb).create(Mockito.anyString());
        Mockito.verify(mockDb).connect();
        Mockito.verify(mockDb).verify();
    }
    
    //Test 2
    @Test
    void testDataSavedForEachMove() throws Exception {
    
        Data move = new Data(0, 1, 1, 'X');
        Mockito.doNothing().when(mockDb).verify();
        Mockito.doNothing().when(mockDb).save(move);
        
        game.play(1, 1);
    
        Mockito.verify(mockDb).save(Mockito.argThat(data -> 
            data.getTurn() == 0 && 
            data.getX() == 1 && 
            data.getY() == 1 && 
            data.getPlayer() == 'X'
        ));
        Mockito.verify(mockDb).verify();
    }
    
    //Test 3
    @Test
    void testSaveMoveFailure() throws Exception {
    
        Mockito.doThrow(new Exception("Failed to save move!")).when(mockDb).save(Mockito.any(Data.class));
    
    
        Exception exception = assertThrows(RuntimeException.class, () -> game.play(1, 1));
    
        assertEquals("Failed to save move!", exception.getMessage());
    
        Mockito.verify(mockDb).save(Mockito.any(Data.class));
    }

}
