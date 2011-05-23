package fantfootball.stats.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import fantfootball.stats.PlayerStat;

/**
 * This type is a very simple csv writer.  It relies on the toString method of PlayStat to output in CSV format.
 */
public class CsvPlayerStatWriter implements PlayerStatWriter {

    private String fileName = "stats.csv";

    @Override
    public void write(List<PlayerStat> stats) {
        try {
            // Create file
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);

            for (PlayerStat stat : stats) {
                out.write(stat.toString());
                out.newLine();
            }
            
            out.close();
        } catch (Exception e) {// Catch exception if any
            throw new IllegalStateException(e);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
