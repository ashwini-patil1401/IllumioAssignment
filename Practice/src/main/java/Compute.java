import java.io.*;
import java.util.*;

public class Compute {

    //Enter the index as seen in flowlog file
    final Integer DST_PORT_INDEX = 6;
    final Integer PROTOCOL_INDEX = 7;

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
            String strLine = null;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                String[] s = strLine.split(REGEX);
                int len = s.length;
                int status_index = len - 2;
                if(s[status_index].equals("ACCEPT")) {
                    String tag = lookUp.getTag(s[DST_PORT_INDEX], s[PROTOCOL_INDEX]);
                    tagMap.put(tag, tagMap.getOrDefault(tag, 0) + 1);
                    String protocol = s[PROTOCOL_INDEX];
                    Key key = new Key(s[DST_PORT_INDEX], protocol);
                    protoPortMap.put(key, protoPortMap.getOrDefault(key, 0) + 1);
                }
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
 PORT PROTOCOL COUNT:

 68 17 1
 22 17 1
 43418 6 1
 31 6 1
 443 17 1
 43416 6 2

 TAG COUNT:

 sv_P2 2
 SV_P3 1
 sv_P1 1
 Untagged 3

 * */
