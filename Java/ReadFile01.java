import java.util.*;
import java.io.*;

public class ReadFile01
{
    public static void main(String [] args)
    {
        readFile("data09.txt");
    }
    
    public static void readFile(String pFileName) 
    {
        FileInputStream fileStream = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        int lineNum;
        String line;
        try 
        {
            fileStream = new FileInputStream(pFileName);
            rdr        = new InputStreamReader(fileStream);
            bufRdr     = new BufferedReader(rdr);
            lineNum    = 0;
            line       = bufRdr.readLine();
            while(line != null)
            {
                lineNum++;
                System.out.println(line);
                line = bufRdr.readLine();
            }
                fileStream.close();
        }
        catch(IOException errorDetails) 
        {
            if(fileStream != null) 
            {
                try 
                {
                    fileStream.close();
                }
                catch(IOException ex2) 
                { }
            }
            System.out.println("Error in fileProcessing: " + errorDetails.getMessage());
        }
    }
}
