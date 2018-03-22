package teamk.xtrex;

import java.io.*;
import java.nio.file.Paths;

/**
 * Opens a text file and logs information to an output log.
 * 
 * @author Connor Harris
 * @version Sprint 3 
 */

public class LogWriter {
    private BufferedWriter bw = null;
    private FileWriter fw = null;
    private String LogPath;
    
    /**
     *  Logging statrs ir stops the log file with the given name
     * 
     * @param boolean true to start - false to stop
     * @param String filename .txt
     * 
     */
    public void Logging (boolean start, String filename) {
    LogPath = Paths.get(".").toAbsolutePath().normalize().toString();
    if (start) {
        try {
            fw = new FileWriter(LogPath + File.separator + filename);
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        try {
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    }
    
    /**
     *  Writes the given string to file
     * 
     * @param String log input
     * 
     */
    public void Logger (String log) {
        try {
            bw.write(log);
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *  Writes the given byte array to file
     * 
     * @param String log input
     * 
     */
    public void Logger (byte[] dirs) {
        for ( int i = 0; i < dirs.length; i++ ) {
            try {
                bw.write(dirs[i]);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}