import java.io.*;
import java.util.Scanner;

public class MediFiles {
    public static void main(String args[])
    {
        try{
            // Reading file and getting no. of files to be generated
            String inputfile = "/Users/khalayadean/Documents/Code/Hackathons/MediLibDir/MediLib/MedDocs/mtsamples.csv"; //  Source File Name.
            double nol = 1000.0; //  No. of lines to be split and saved in each output file.
            File file = new File(inputfile);
            Scanner scanner = new Scanner(file);
            int count = 0;
            while (scanner.hasNextLine())
            {
                scanner.nextLine();
                count++;
            }
            System.out.println("Lines in the file: " + count);     // Displays no. of lines in the input file.

            double temp = (count/nol);
            int temp1=(int)temp;
            int nof=0;
            if(temp1==temp)
            {
                nof=temp1;
            }
            else
            {
                nof=temp1+1;
            }
            System.out.println("No. of files to be generated :"+nof); // Displays no. of files to be generated.

            //---------------------------------------------------------------------------------------------------------

            // Actual splitting of file into smaller files

            FileInputStream fstream = new FileInputStream(inputfile); DataInputStream in = new DataInputStream(fstream);

            BufferedReader br = new BufferedReader(new InputStreamReader(in)); String strLine;

            for (int j=1;j<=nof;j++)
            {
                FileWriter fstream1 = new FileWriter("/Users/khalayadean/Documents/Code/Hackathons/MediLibDir/MediLib/MedDocs/MedDoc"+j+".csv");     // Destination File Location
                BufferedWriter out = new BufferedWriter(fstream1);
                for (int i=1;i<=nol;i++)
                {
                    strLine = br.readLine();
                    if (strLine!= null)
                    {
                        out.write(strLine);
                        if(i!=nol)
                        {
                            out.newLine();
                        }
                    }
                }
                out.close();
            }

            in.close();
        }catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }

    }

//
//    public static void myFunction(int lines, int files) throws FileNotFoundException, IOException{
//
//        String inputfile = "/Users/khalayadean/Documents/Code/Hackathons/MediLibDir/MediLib/MedDocs/mtsamples.csv";
//        BufferedReader br = new BufferedReader(new FileReader(inputfile)); //reader for input file intitialized only once
//        String strLine = null;
//        for (int i=1;i<=files;i++) {
//            FileWriter fstream1 = new FileWriter("/Users/khalayadean/Documents/Code/Hackathons/MediLibDir/MediLib/MedDocs/FileNumber_"+i+".csv"); //creating a new file writer.
//            BufferedWriter out = new BufferedWriter(fstream1);
//            for(int j=0;j<lines;j++){   //iterating the reader to read only the first few lines of the csv as defined earlier
//                strLine = br.readLine();
//                if (strLine!= null) {
//                    String strar[] = strLine.split(",");
//                    out.write(strar[0]);   //acquring the first column
//                    out.newLine();
//                }
//            }
//            out.close();
//        }
//    }
//
//    public static void main(String args[])
//    {
//        try{
//            int lines = 2;  //set this to whatever number of lines you need in each file
//            int count = 0;
//            String inputfile = "/Users/khalayadean/Documents/Code/Hackathons/MediLibDir/MediLib/MedDocs/mtsamples.csv";
//            File file = new File(inputfile);
//            Scanner scanner = new Scanner(file);
//            while (scanner.hasNextLine()) {  //counting the lines in the input file
//                scanner.nextLine();
//                count++;
//            }
//            System.out.println(count);
//            int files=0;
//            if((count%lines)==0){
//                files= (count/lines);
//            }
//            else{
//                files=(count/lines)+1;
//            }
//            System.out.println(files); //number of files that shall eb created
//
//            myFunction(lines,files);
//        }
//
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        //first read the file
//        String inputfile = "/Users/khalayadean/Documents/Code/Hackathons/MediLibDir/MediLib/MedDocs/mtsamples.csv";
//        BufferedReader br = new BufferedReader(new FileReader(inputfile));
//        //create the first file which will have 1000 lines
//        File file = new File("/Users/khalayadean/Documents/Code/Hackathons/MediLibDir/MediLib/MedDocs/FileNumber_"+1+".csv");
//        FileWriter fstream1 = new FileWriter(file);
//        BufferedWriter out = new BufferedWriter(fstream1);
//        String line="";
//        //count the number of line
//        int count=1;
//        int file_Number=2;
//        while ((line = br.readLine()) != null)
//        {
//            //if the line is divided by 1000 then create a new file with file count
//            if(count % 1000 == 0)
//            {
//                File newFile = new File("/Users/khalayadean/Documents/Code/Hackathons/MediLibDir/MediLib/MedDocs/FileNumber_"+file_Number+".csv");
//                fstream1 = new FileWriter(newFile);
//                file_Number++;
//                out = new BufferedWriter(fstream1);
//            }
//            if(line.indexOf(",")!=-1)
//                line=line.substring(0, line.indexOf(","));
//            out.write(line);
//            out.newLine();
//            count++;
//        }

    }
