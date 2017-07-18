import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by chiennv on 12/07/2017.
 */
public class GenData {
    public static Model model;
    public static void Gen(){

        try{
            long start = System.currentTimeMillis();
//            PrintWriter out = new PrintWriter("data.txt");
            ObjectMapper objectMapper = new ObjectMapper();

            UUID random;
            Random random1 = new Random();
            model = new Model();

            random = UUID.randomUUID();
            int randomNumber = random1.nextInt(1000) +1;
            model.setId(random);
            for(int i=0; i<1000; i++){
                model.setComment(RandomStringUtils.random(16000,true, false));
                model.setFriend(RandomStringUtils.random(16000,true, false));
                model.setIdCustom(randomNumber);
                model.setLike(RandomStringUtils.random(16000,true, false));
                model.setMoney(randomNumber);
                model.setName(RandomStringUtils.random(16000,true, false));
                model.setStatus(RandomStringUtils.random(16000,true, false));
                model.setToDo(RandomStringUtils.random(16000,true, false));
                System.out.println(i);
            }
            objectMapper.writeValue(new FileOutputStream("data.json"),model);
            long time = System.currentTimeMillis() - start;
            System.out.println("GenData in " + time + " ms");

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        GenData.Gen();


    }

}
