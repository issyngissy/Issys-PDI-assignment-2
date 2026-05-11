import java.io.*;

public class studentMarks
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        int maxStudents = 20;
        String[] names = new String[maxStudents];
        int[] studentIDs = new int[maxStudents];
        double[] marks = new double[maxStudents];
        int studentCount = 0;

        System.out.println("Which file would you like to read")
        fileName = sc.nextLine();

        readFile(fileName, names, studentIDs, marks);
    }

    public static void readFile(String fileName, String[] names, int[] studentIDs, double[] marks);
    {
        try
        {
            fileStream = new FileInputStream(fileName)
            isr = new InputStreamReader(fileStream)
            bufRdr = new BufferedReader(isr);
            lineNum = 0;
            line = bufRdr.readLine();
            while(line != null)
            {
                lineNum++;
                processLine(String line, lineNum, names, studentIDs, marks)

                System.out.print(line);
                line = bufRdr.readLine();
            }
            fileStream.close()
        }
        catch(IOException errorDetails)
        {
            if(fileStream != null)
            {
            }
        }
    }

    public static void processLine(String line, int lineNum, names[], studentIDs[], marks[]);
    {
        String[] splitLine;
        splitLine = line.split(",");

        names[lineNum] = splitLine[0];
        studentIDs[lineNum] = Integer.parseInt(splitLine[1]);
        marks[lineNum] = Double.parseDouble(splitLine[2]);
    }
}