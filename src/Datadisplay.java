import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Datadisplay {
    public static String csvdisplayer(String datasource){
        String data = datasource;
        BufferedReader reader = null;
        String line =" ";
        try{
            reader = new BufferedReader(new FileReader(data));
            while((line = reader.readLine())!= null){
                String[] row = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
                for(String index: row){
                    System.out.printf("%-40s",index);
                }
                System.out.println();
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return data;
    }
}
