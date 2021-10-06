package src.main.java;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.ByteBuffer;

public class saveLoadHandler {

    public static void writeToSaveFile(String index, String value[]){

        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();

        try(FileReader reader = new FileReader("settings.json")){

            obj = (JSONObject)parser.parse(reader);
        }catch(FileNotFoundException e){

            System.out.println("No settings.json file was found. Creating one now...");

            int colorA = generateRGBCode(255, 255, 255);
            int colorB = generateRGBCode(0, 0, 0);
            int colorH = generateRGBCode(86, 223, 204);

            obj.put("colorA", colorA);
            obj.put("colorB", colorB);
            obj.put("colorH", colorH);

            try(FileWriter file = new FileWriter("settings.json")){

                file.write(obj.toJSONString());
            } catch (Exception ee){

                System.out.println("Something went wrong when generating a new settings.json.");
            }
        } catch (Exception e ){

            System.out.println("An unknown error occurred when reading settings.json.");
        }

        obj.put(index, value);

        try(FileWriter file = new FileWriter("settings.json")){

            file.write(obj.toJSONString());
        } catch (Exception ee){

            System.out.println("Something went wrong when saving changes to settings.json.");
        }
    }

    public static Object readFromSaveFile(String index){

        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();

        try{

            obj = (JSONObject)parser.parse(new FileReader("settings.json"));
        } catch (FileNotFoundException e){

            System.out.println("No settings.json file was found. Creating one now...");

            int colorA = generateRGBCode(255, 255, 255);
            int colorB = generateRGBCode(0, 0, 0);
            int colorH = generateRGBCode(86, 223, 204);

            obj.put("colorA", colorA);
            obj.put("colorB", colorB);
            obj.put("colorH", colorH);

            try(FileWriter file = new FileWriter("settings.json")){

                file.write(obj.toJSONString());
            } catch (Exception ee){

                System.out.println("Something went wrong when generating a new settings.json.");
            }
        } catch(Exception e){

            System.out.println("An unknown error occured when reading settings.json.");
        }

        return obj.get(index);
    }

    public static int generateRGBCode(int r, int g, int b){

        byte bytes[] = new byte[4]; // create a container for desired bytes

        byte rb[] = ByteBuffer.allocate(4).putInt(r).array(); // convert integers to array forms of their bytes
        byte gb[] = ByteBuffer.allocate(4).putInt(g).array();
        byte bb[] = ByteBuffer.allocate(4).putInt(g).array();

        bytes[1] = rb[3]; // fill bytes with the first byte of every integer
        bytes[2] = gb[3];
        bytes[3] = bb[3];

        return ByteBuffer.wrap(bytes).getInt(); // convert byte array to integer and return it
    }
}