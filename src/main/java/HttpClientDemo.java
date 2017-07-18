import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.BytesContentProvider;
import org.eclipse.jetty.http.HttpMethod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.zip.GZIPOutputStream;

/**
 * Created by chiennv on 13/07/2017.
 */
public class HttpClientDemo {

    public static void main(String[] args) {
        try{

            //Map data
            MapData.Map();
            //khoi tao httpClient
            HttpClient httpClient = new HttpClient();
            httpClient.start();
            System.out.println(MapData.model.getId());
            //chuyen du lieu sang byte
            byte[] bytes = SerializationUtils.serialize(MapData.model);
            //gzip
            byte[] compressData = gzip(bytes);
            //tinh checksum
            String checksum = Checksum.CalChecksum(compressData);

            //send data, neu nhan response resend thi gui lai
            boolean sendSuccess = false;
            while(sendSuccess == false){
                String send = sendData(httpClient, compressData, checksum);
                System.out.println(send);
                if(send.equals("accept")){
                    sendSuccess = true;
                    System.out.println("send success");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static String sendData(HttpClient httpClient, byte[] compressData, String checksum) throws InterruptedException, ExecutionException, TimeoutException {
        //start time
        long start = System.currentTimeMillis();
        ContentResponse contentResponse2 = httpClient.newRequest("http://localhost:4567/postgzip")
                .method(HttpMethod.POST)
                .content(new BytesContentProvider(compressData), "application/json")
                .param("Checksum", checksum)
                .param("TimeStart", String.valueOf(start))
                .send();
        return contentResponse2.getContentAsString();
    }
    public static byte[] gzip(byte[] bytes) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(bytes.length   );
        GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
        zipStream.write(bytes);
        zipStream.close();
        byteStream.close();
        byte[] compressData = byteStream.toByteArray();
        return compressData;
    }
}
