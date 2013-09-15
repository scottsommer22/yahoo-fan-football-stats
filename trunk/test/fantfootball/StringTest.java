package fantfootball;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringTest {

    @Test
    public void test() {
        String test = "http://sports.yahoo.com/nfl/players/7203";
        
        String[] tokens = test.split("/");
        
        String key = tokens[tokens.length-1];
        
        assertEquals("7203", key);
        
    }

}
