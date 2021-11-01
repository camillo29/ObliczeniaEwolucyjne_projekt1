package app.classes.Managers;

import java.io.*;

public class FileManager {
    File file;
    FileWriter fw;
    BufferedWriter bw;
    PrintWriter pw;

    public FileManager(String filePath){
        file = new File(filePath);
        try {
            fw = new FileWriter(filePath, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            pw.print("");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void writeToFile(String message){
        pw.println(message);
        pw.flush();
    }

    public void closeFile(){
        try {
            pw.close();
            bw.close();
            fw.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
