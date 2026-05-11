import java.io.*;

public class csvReader
{
        // main method which declares all the variables used in the program
    public static void main(String[] args)
    {

        int maxlines = 100;
        //Arrays which will store day, month, year, hour, minute, sensorID, sensorType, zone, value
        int[] day = new int[maxlines];
        int[] month = new int[maxlines];
        int[] year = new int[maxlines];
        int[] hour = new int[maxlines];
        int[] minute = new int[maxlines];
        String[] sensorID = new String[maxlines];
        String[] sensorType = new String[maxlines];
        String[] zone = new String[maxlines];
        double[] value = new double[maxlines];


        readFile("data.csv", day, month, year, hour, minute, sensorID, sensorType, zone, value);
        System.exit(0);
    }

    public static void readFile(String pFileName, int[] day, int[] month, int[] year, int[] hour, int[] minute, String[] sensorID, String[] sensorType, String[] zone, double[] value)
    {
        FileInputStream fileStream = null;
        InputStreamReader isr;
        BufferedReader bufRdr;
        int lineNum;
        String line;

        try
        {
            fileStream = new FileInputStream(pFileName);
            isr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(isr);
            lineNum = 0;
            line = bufRdr.readLine();
            line = bufRdr.readLine();
            while(line != null)
            {
                lineNum++;
                processLine(line, lineNum, day, month, year, hour, minute, sensorID, sensorType, zone, value);

                System.out.print(line + "\n");
                line = bufRdr.readLine();
            }
            fileStream.close();
        }
        catch(IOException errorDetails)
        {
            if(fileStream != null);
            {
                try
                {
                    fileStream.close();
                }
                catch(IOException ex2)
                {}
            }
            System.out.println("Error in fileProcessing: " + errorDetails.getMessage());
        }
    }


    public static void processLine(String line, int lineNum, int[] day, int[] month, int[] year, int[] hour, int[] minute, String[] sensorID, String[] sensorType, String[] zone, double[] value)
    {
        String[] splitLine;
        splitLine = line.split(",");

        day[lineNum] = Integer.parseInt(splitLine[0]);
        month[lineNum] = Integer.parseInt(splitLine[1]);
        year[lineNum] = Integer.parseInt(splitLine[2]);
        hour[lineNum] = Integer.parseInt(splitLine[3]);
        minute[lineNum] = Integer.parseInt(splitLine[4]);

        sensorID[lineNum] = splitLine[5];
        sensorType[lineNum] = splitLine[6];
        zone[lineNum] = splitLine[7];
        
        value[lineNum] = Double.parseDouble(splitLine[8]);
    }
        

}

