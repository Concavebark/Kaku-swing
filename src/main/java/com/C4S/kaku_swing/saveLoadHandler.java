package com.C4S.kaku_swing;

/*
Written by James Koehler

This little chunk of code handles all json file interactions, and some other methods to help out.
This includes saving and loading games from the save file, and saving and loading from the settings file.
There are also methods in here used when decoding and encoding things for the save file.
 */

import com.C4S.LogWriter.LogLevels;
import com.C4S.LogWriter.LogWriter;
import net.jameskoehler.kaku.Board;
import net.jameskoehler.kaku.Piece;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class saveLoadHandler { // NOTE: this assumes the user is on windows, as it uses AppData

    // TODO: os detection to move saving location elsewhere when on linux or other target platforms

    public static void writeToSaveFile(String index, String value){ // this will follow algebraic chess notation

        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();

        AppDirs appDirs = AppDirsFactory.getInstance();

        try(FileReader reader = new FileReader(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/save.json")){

            obj = (JSONObject)parser.parse(reader); // pass the save file into the json object
        }catch(FileNotFoundException e){ // save file can't be located

            LogWriter.log("No save.json file was found. Creating one now...", LogLevels.WARNING);

            File theDir = new File(appDirs.getUserDataDir("kaku-swing",null,"C4 Software"));

            if(!theDir.exists())  // generate our directory in AppData
                if(!theDir.mkdirs())
                    LogWriter.log("Something went wrong creating the AppData directory! This will cause more file system errors!", LogLevels.SEVERE);

            try(FileWriter file = new FileWriter(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/save.json")){

                file.write(obj.toJSONString()); // make the actual file and write the json object to it
            } catch (Exception ee){ // something is preventing file creation

                LogWriter.log("Something went wrong when generating a new save.json.", LogLevels.SEVERE);
            }
        } catch (Exception e ){

            LogWriter.log("An unknown error occurred when reading save.json.", LogLevels.SEVERE);
        }

        obj.put(index, value);

        try(FileWriter file = new FileWriter(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/save.json")){

            file.write(obj.toJSONString());
        } catch (Exception ee){

            LogWriter.log("Something went wrong when saving changes to save.json.", LogLevels.SEVERE);
        }
    }

    public static Piece[][] readFromSaveFile(String index, boolean isWhiteTurn){

        // first we have to read the correct game save from the file
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();

        AppDirs appDirs = AppDirsFactory.getInstance();

        try{

            obj = (JSONObject)parser.parse(new FileReader(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/save.json"));
        } catch (FileNotFoundException e){ // no save! we will start a new game instead

            LogWriter.log("Save file access attempted when no save was present! Starting a new game.", LogLevels.WARNING);
            isWhiteTurn = true; // white goes first
            return new Board().getBoardState(); // this will return the default board state
        } catch(Exception e){

            LogWriter.log("An unknown error occured when reading save.json.", LogLevels.SEVERE);
        }

        //return algebraicToBoardState(obj.get(index).toString()); // TODO: Implement this!
        return new Board().getBoardState(); // This is just a fix to stop errors
    }

    public static void writeToSettingsFile(String index, String value){

        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();

        AppDirs appDirs = AppDirsFactory.getInstance();

        try(FileReader reader = new FileReader(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/settings.json")){
            System.out.println(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/save.json");
            obj = (JSONObject)parser.parse(reader);
        }catch(FileNotFoundException e){

            LogWriter.log("No settings.json file was found. Creating one now...", LogLevels.WARNING);

            int colorA = generateRGBCode(255, 255, 255);
            int colorB = generateRGBCode(0, 0, 0);
            int colorH = generateRGBCode(86, 223, 204);

            obj.put("colorA", colorA);
            obj.put("colorB", colorB);
            obj.put("colorH", colorH);

            File theDir = new File(appDirs.getUserDataDir("kaku-swing",null,"C4 Software"));

            if(!theDir.exists())  // generate our directory in AppData
                if(!theDir.mkdirs())
                    LogWriter.log("Something went wrong creating the AppData directory! This will cause more file system errors!", LogLevels.SEVERE);

            try(FileWriter file = new FileWriter(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/settings.json")){

                file.write(obj.toJSONString());
            } catch (Exception ee){

                LogWriter.log("Something went wrong when generating a new settings.json.", LogLevels.SEVERE);
            }
        } catch (Exception e ){

            LogWriter.log("An unknown error occurred when reading settings.json.", LogLevels.SEVERE);
        }

        obj.put(index, value);

        try(FileWriter file = new FileWriter(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/settings.json")){

            file.write(obj.toJSONString());
        } catch (Exception ee){

            LogWriter.log("Something went wrong when saving changes to settings.json.", LogLevels.SEVERE);
        }
    }

    public static String readFromSettingsFile(String index){

        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();

        AppDirs appDirs = AppDirsFactory.getInstance();

        try{

            obj = (JSONObject)parser.parse(new FileReader(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/settings.json"));
        } catch (FileNotFoundException e){

            LogWriter.log("No settings.json file was found. Creating one now...", LogLevels.WARNING);

            int colorA = generateRGBCode(255, 255, 255);
            int colorB = generateRGBCode(0, 0, 0);
            int colorH = generateRGBCode(86, 223, 204);

            obj.put("colorA", colorA);
            obj.put("colorB", colorB);
            obj.put("colorH", colorH);

            File theDir = new File(appDirs.getUserDataDir("kaku-swing",null,"C4 Software"));

            if(!theDir.exists())  // generate our directory in AppData
                if(!theDir.mkdirs())
                    LogWriter.log("Something went wrong creating the AppData directory! This will cause more file system errors!", LogLevels.SEVERE);

            try(FileWriter file = new FileWriter(appDirs.getUserDataDir("kaku-swing",null,"C4 Software") + "/settings.json")){

                file.write(obj.toJSONString());
            } catch (Exception ee){ /// HEADS UP, THESE REALLY SHOULDN'T BE BLANKET CATCHES FOR EVERY EXCEPTION AND IT SHOULD BE WRITTEN TO THE LOGS THAT AN EXCEPTION IS BEING THROWN

                LogWriter.log("Something went wrong when generating a new settings.json.", LogLevels.SEVERE);
            }
        } catch(Exception e){

            LogWriter.log("An unknown error occured when reading settings.json.", LogLevels.SEVERE);
        }

        return obj.get(index).toString();
    }

    public static int generateRGBCode(int r, int g, int b){

        // change everything to 0s, except the first bit, in r, g, and b
        // then shift g to the second byte, and b to the third byte, leaving one integer with its first three bytes filled with the first byte of the r, g, and b values

        return ((r&255)<<16)|((g&255)<<8)|(b&255);
    }

    private static Piece[][] algebraicToBoardState(String moves){ // TODO: I will make this a feature in kaku

        return new Board().getBoardState(); // this is just a temporary fix to stop errors. remove this!
    }
}
