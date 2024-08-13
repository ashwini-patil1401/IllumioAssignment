import java.io.*;
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
    LookUp lookUp = null;

    public Compute() {
        instance = new FileUtils();
        lookUp = new LookUp();
    }

    private void processLogData() {
        try {
            InputStream is = instance.getFileAsIOStream(LOG_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String strLine;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                String[] s = strLine.split(REGEX);
                String tag = lookUp.getTag(s[DST_PORT_INDEX], s[PROTOCOL_INDEX]);
                tagMap.put(tag, tagMap.getOrDefault(tag, 0) + 1);
                Key key = new Key(s[DST_PORT_INDEX], s[PROTOCOL_INDEX]);
                protoPortMap.put(key, protoPortMap.getOrDefault(key, 0) + 1);
            }
            is.close();

        } catch (Exception e) {
            System.out.print("Exception in getFileContent: " + e.getMessage());
        }

    }

    private void generatePortProtocolCount() {
        System.out.println("\n PORT PROTOCOL COUNT: \n");
        for (Map.Entry<Key,Integer> entry : protoPortMap.entrySet()) {
            String s = entry.getKey() + " " +entry.getValue();
            System.out.println(s);
            portProtocolCountList.add(s);
        }
        instance.writeContent(portProtocolCountList, PORT_PROTOCOL_COUNT_FILE_NAME);
    }

    private void generateTagCount() {
        System.out.println("\n TAG COUNT: \n");
        for (Map.Entry<String,Integer> entry : tagMap.entrySet()) {
            String s = entry.getKey() + " " +entry.getValue();
            System.out.println(s);
            tagCountList.add(s);
        }
        instance.writeContent(tagCountList, TAG_COUNT_FILE_NAME);
    }


    public void compute() {

        lookUp.createLookUp();

        processLogData();
        generatePortProtocolCount();
        generateTagCount();
    }
}


/**
 * OUTPUT:
 *
 *  PORT PROTOCOL COUNT:
 *
 * 68 udp 1
 * 43418 udp 2
 * 5001 tcp 2
 * 5001 udp 1
 * 43416 tcp 2
 *
 *  TAG COUNT:
 *
 * sv_P2 1
 * sv_P1 2
 * sv_P4 2
 * Untagged 3
 *
 * Process finished with exit code 0
 * */
