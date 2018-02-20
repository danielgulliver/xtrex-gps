package aprogrammerisneverlate.xtrex;

import java.io.*;
import java.nio.file.Paths;

public class FileWritter {
    private static BufferedWriter bw = null;
    private static FileWriter fw = null;
    private static String LogPath;
    
    public void Logging () {
        LogPath = Paths.get(".").toAbsolutePath().normalize().toString();
        Logging(true); 
    }
    
        static void Logging (boolean start) {
        LogPath = Paths.get(".").toAbsolutePath().normalize().toString();
        if (start) {
            try {
                fw = new FileWriter(LogPath + "\\log.txt");
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
    
    public void Logger (String log) {
        try {
            bw.write(log);
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
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
/**
public void writeGridToFile() throws IOException {
            FileWriter fw = new FileWriter("D:/GridArrayFile.txt");
            try {
                for(int i = 0; i < 100; i++){
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(GridArray[i]);
                    bw.close();
                }
            }

            catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error");
            }
            finally {
                //TODO
                System.out.println("Finished");
            }
    }
public void writeGridToFile() throws IOException {
    try(BufferedWriter bw = new BufferedWriter(new FileWriter("D:/GridArrayFile.txt"))) {
        for(int i = 0; i < 100; i++){
            bw.write(Integer.toString(GridArray[i]));
       }
        bw.flush();
     }
     catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
     }
     finally {
            System.out.println("Finished");
     }
}
*/