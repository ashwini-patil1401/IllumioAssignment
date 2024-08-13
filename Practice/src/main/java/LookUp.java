import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class LookUp {

    //Enter the index as seen in lookup file
    final Integer PORT_INDEX = 0;
    final Integer PROTOCOL_INDEX = 1;
    final Integer TAG_INDEX = 2;

    final static String FILE_NAME = "lookup.csv";
    final static String REGEX = ",";

    HashMap<Key, String> map  = new HashMap<Key, String>();

    /**
     * Read 'lookup.csv' and populate hashmap with key as dstport
     * It will map to another map with protocol as key and tag as value
     * */
    public void createLookUp() {
        FileUtils instance = new FileUtils();
        try{
            InputStream is = instance.getFileAsIOStream(FILE_NAME);
            List<String[]> list = instance.getFileContent(is, REGEX);
            for(String[] s :list) {
                Key key = new Key(s[PORT_INDEX], s[PROTOCOL_INDEX]);
                if(!map.containsKey(key)) {
                    map.put(key, s[TAG_INDEX]);
                }
                //System.out.println("tag: " + map.get(key));
            }
        } catch (Exception e) {
            System.out.print("Create LookUp Exception: " + e.getMessage());
        }
    }


    /**
     * @param port : dstport read from log
     * @param protocol : protocol field read from log
     * Lookup the corresponding tag value and return the same
     * */
    public String getTag(String port, String protocol) {
        Key key = new Key(port, protocol);
        if(map.containsKey(key)) {
            //System.out.println("port: "+port +" protocol: "+protocol + " tag: " + map.get(key));
            return map.get(key);
        }

        return "Untagged";
    }

}
