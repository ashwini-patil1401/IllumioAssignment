import java.io.InputStream;
import java.util.*;

public class Compute {

    //Enter the index as seen in flowlog file
    final Integer DST_PORT_INDEX = 8;
    final Integer PROTOCOL_INDEX = 9;

    final String LOG_FILE_NAME = "flowlog.txt";
    final String PORT_PROTOCOL_COUNT_FILE_NAME = "portProtocolCount.txt";
    final String TAG_COUNT_FILE_NAME = "tagCount.txt";
    final String REGEX = " ";

    HashMap<String, Integer> tagMap = new HashMap<String, Integer>();
    HashMap<Key, Integer> protoPortMap = new HashMap<Key, Integer>();
    List<String> tagCountList = new ArrayList<String>();
    List<String> portProtocolCountList = new ArrayList<String>();
    FileUtils instance = null;

    public Compute() {
        instance = new FileUtils();
    }

    private void processData() {

        LookUp lookUp = new LookUp();
        lookUp.createLookUp();
        InputStream is = instance.getFileAsIOStream(LOG_FILE_NAME);
        List<String[]> list = instance.getFileContent(is, REGEX);

        for(String[] s : list) {
            try {
                String tag = lookUp.getTag(s[DST_PORT_INDEX], s[PROTOCOL_INDEX]);
                tagMap.put(tag, tagMap.getOrDefault(tag, 0) + 1);
                Key key = new Key(s[DST_PORT_INDEX], s[PROTOCOL_INDEX]);
                protoPortMap.put(key, protoPortMap.getOrDefault(key, 0) + 1);

            } catch (Exception e) {
                System.out.print("Compute Tags Exception: " + e.getMessage());
            }
        }
    }

    private void generatePortProtocolCount() {
        System.out.println("\n PORT PROTOCOL COUNT: \n");
        for (Map.Entry<Key,Integer> entry : protoPortMap.entrySet()) {
            String s = "" + entry.getKey() + " " +entry.getValue();
            System.out.println(s);
            portProtocolCountList.add(s);
        }
        instance.writeContent(portProtocolCountList, PORT_PROTOCOL_COUNT_FILE_NAME);
    }

    private void generateTagCount() {
        System.out.println("\n TAG COUNT: \n");
        for (Map.Entry<String,Integer> entry : tagMap.entrySet()) {
            String s = "" + entry.getKey() + " " +entry.getValue();
            System.out.println(s);
            tagCountList.add(s);
        }
        instance.writeContent(tagCountList, TAG_COUNT_FILE_NAME);
    }


    public void compute() {
        processData();
        generatePortProtocolCount();
        generateTagCount();
    }
}
