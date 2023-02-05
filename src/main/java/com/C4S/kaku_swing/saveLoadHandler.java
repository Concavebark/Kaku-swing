package com.C4S.kaku_swing;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

// TODO: change all the System out messages to logging

public class saveLoadHandler { // NOTE: this assumes the user is on windows, as it uses AppData

    public static void writeToSaveFile(String index, String value){

        /* TODO: i might make saving boardstates something handled in kaku? either way its gonna be in algebraic; not this shit
        Save format will have a saved game as the key, and the moves made saved in PGN. The engine needs to be able to store all moves made and convert to and from PGN.
        Example:
        "Game 1": "1.Nf3 Nf6 2.c4 g6 3.Nc3 Bg7 4.d4 O-O 5.Bf4 d5 6.Qb3 dxc4 7.Qxc4 c6 8.e4 Nbd7",
        "Game 2": "1.Nf3 Nf6 2.c4 g6 3.Nc3 Bg7 4.d4 O-O 5.Bf4 d5 6.Qb3 dxc4 7.Qxc4 c6 8.e4 Nbd7" <- imagine these games don't have the same moves made. I am lazy.
         */

        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();

        AppDirs appDirs = AppDirsFactory.getInstance();

        try(FileReader reader = new FileReader(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/save.json")){

            obj = (JSONObject)parser.parse(reader); // pass the save file into the json object
        }catch(FileNotFoundException e){ // generate save file since one was not found

            System.out.println("No save.json file was found. Creating one now...");

            File theDir = new File(appDirs.getUserDataDir("kaku-swing",null,"C4 Software"));

            if(!theDir.exists()) // generate our directory in AppData
                theDir.mkdirs();

            try(FileWriter file = new FileWriter(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/save.json")){

                file.write(obj.toJSONString()); // make the actual file and write the json object to it
            } catch (Exception ee){

                System.out.println("Something went wrong when generating a new save.json.");
            }
        } catch (Exception e ){

            System.out.println("An unknown error occurred when reading save.json.");
        }

        obj.put(index, value);

        try(FileWriter file = new FileWriter(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/save.json")){

            file.write(obj.toJSONString());
        } catch (Exception ee){

            System.out.println("Something went wrong when saving changes to save.json.");
        }
    }

    public static void writeToSettingsFile(String index, String value){

        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();

        AppDirs appDirs = AppDirsFactory.getInstance();

        try(FileReader reader = new FileReader(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/settings.json")){

            obj = (JSONObject)parser.parse(reader);
        }catch(FileNotFoundException e){

            System.out.println("No settings.json file was found. Creating one now...");

            int colorA = generateRGBCode(255, 255, 255);
            int colorB = generateRGBCode(0, 0, 0);
            int colorH = generateRGBCode(86, 223, 204);

            obj.put("colorA", colorA);
            obj.put("colorB", colorB);
            obj.put("colorH", colorH);

            File theDir = new File(appDirs.getUserDataDir("kaku-swing",null,"C4 Software"));

            if(!theDir.exists())
                theDir.mkdirs();

            try(FileWriter file = new FileWriter(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/settings.json")){

                file.write(obj.toJSONString());
            } catch (Exception ee){

                System.out.println("Something went wrong when generating a new settings.json.");
            }
        } catch (Exception e ){

            System.out.println("An unknown error occurred when reading settings.json.");
        }

        obj.put(index, value);

        try(FileWriter file = new FileWriter(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/settings.json")){

            file.write(obj.toJSONString());
        } catch (Exception ee){

            System.out.println("Something went wrong when saving changes to settings.json.");
        }
    }

    public static String readFromSettingsFile(String index){

        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();

        AppDirs appDirs = AppDirsFactory.getInstance();

        try{

            obj = (JSONObject)parser.parse(new FileReader(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/settings.json"));
        } catch (FileNotFoundException e){

            System.out.println("No settings.json file was found. Creating one now...");

            int colorA = generateRGBCode(255, 255, 255);
            int colorB = generateRGBCode(0, 0, 0);
            int colorH = generateRGBCode(86, 223, 204);

            obj.put("colorA", colorA);
            obj.put("colorB", colorB);
            obj.put("colorH", colorH);

            File theDir = new File(appDirs.getUserDataDir("kaku-swing",null,"C4 Software"));

            if(!theDir.exists())
                theDir.mkdirs();

            try(FileWriter file = new FileWriter(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/settings.json")){

                file.write(obj.toJSONString());
            } catch (Exception ee){

                System.out.println("Something went wrong when generating a new settings.json.");
            }
        } catch(Exception e){

            System.out.println("An unknown error occured when reading settings.json.");
        }

        return obj.get(index).toString();
    }

    public static int generateRGBCode(int r, int g, int b){

        // change everything to 0s, except the first bit, in r, g, and b
        // then shift g to the second byte, and b to the third byte, leaving one integer with its first three bytes filled with the first byte of the r, g, and b values

        return ((r&255)|((g&255)<<8)|(b&255)<<16);
    }
}
