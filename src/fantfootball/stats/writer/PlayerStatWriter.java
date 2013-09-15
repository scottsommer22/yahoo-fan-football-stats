package fantfootball.stats.writer;

import java.util.List;

/**
 * A type to persist stats.
 */
public interface PlayerStatWriter {

    
    /**
     * Write the stats.
     * 
     * @param stats the stats.
     */
    void write(List<?> stats);
    
}
