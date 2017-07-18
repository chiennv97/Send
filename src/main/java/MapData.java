import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by chiennv on 13/07/2017.
 */
public class MapData {

    public static Model model;
    public static void Map(){
        long start = System.currentTimeMillis();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            //mapdata
            model = objectMapper.readValue(new FileInputStream("data.json"),Model.class);
            //time map data
            long time = System.currentTimeMillis() - start;
            System.out.println("time to map data is " + time);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
