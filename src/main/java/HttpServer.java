
import org.apache.commons.lang3.SerializationUtils;
import spark.utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import static spark.Spark.*;
/**
 * Created by chiennv on 13/07/2017.
 */
public class HttpServer {
    public static void main(String[] args) {

        post("/postgzip", (request, response) -> {
            System.out.println(request.contentLength());

            //tinh thoi gian gui
            long time = System.currentTimeMillis();
            String timeStart = request.queryParams("TimeStart");
            long timeSent = time - Long.parseLong(timeStart);
            System.out.println("time send " + timeSent);

            //lay data tu request
            byte[] bytes = request.bodyAsBytes();
            //kiem tra checksum
            String ChecksumSend = request.queryParams("Checksum");
            String ChecksumReCal = Checksum.CalChecksum(bytes);

            //neu dung checksum thi deserialize du lieu, ko dung thi yeu cau gui lai
            if(ChecksumSend.equals(ChecksumReCal)){
                byte[] decompressData = unGzip(bytes);

                //deserialize
                Model model = SerializationUtils.deserialize(decompressData);
                System.out.println(model.getId());
                return "accept";
            }

            return "resend";
        });
    }
    public static byte[] unGzip(byte[] bytes){
        //tao byteStream va giai nen du lieu, copy vao byteStream
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try{
            IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(bytes)), byteStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        byte[] decompressData = byteStream.toByteArray();
        return decompressData;
    }
}
