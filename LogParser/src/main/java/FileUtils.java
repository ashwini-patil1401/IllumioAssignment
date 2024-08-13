import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public InputStream getFileAsIOStream(final String fileName)
    {
        InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    public List<String[]> getFileContent(InputStream is, String regex)
    {
        List<String[]> list = new ArrayList<String[]>();
        try
        {

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(regex);
                list.add(tokens);
            }
            is.close();
        } catch(Exception e) {
            System.out.print(e.getMessage());
        }
        return list;
    }

    public void writeContent(List<String> stringList, String filename) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(filename));
            for (String str : stringList) {
                br.write(str + System.lineSeparator());
            }
            br.close();
        } catch (Exception e) {
            System.out.println("file write exception :" + e.getMessage());
        }
    }
}
