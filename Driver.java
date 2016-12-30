import java.io.*;
import java.util.Scanner;

public class Driver {
  public static void main (String args[]) throws IOException{
    PolyParser parser = new PolyParser();
    if(args.length>1){ //Too many arguments provided
      System.out.println("Too many arguments specified. Please specify a valid argument");
    }

    else if (args.length==1){
      String file = args[0];
      try (FileReader reader = new FileReader(file)) {
        BufferedReader bf = new BufferedReader(reader);

        PrintWriter writer = new PrintWriter(args[0]+".out", "UTF-8"); //Write result to new .out file
        String line = null;
        while((line = bf.readLine())!=null){ //Evaluate each line and write the result
          String result = parser.evaluateExpr(line);
          writer.println(result);
        }
        writer.close();

      }

      catch(IOException e){
        System.out.println("Error opening/creating file. Ensure the file exists and that you have write permission to the current directory");
      }

    }

    else{
      Scanner in = new Scanner (System.in);

      while(in.hasNextLine()){ //Continually prompt user for input until Ctrl-C
        String result = parser.evaluateExpr(in.nextLine());
        System.out.println(result);

    }
  }
}
}
