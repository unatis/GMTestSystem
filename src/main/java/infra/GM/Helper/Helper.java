package infra.GM.Helper;

import com.opencsv.CSVWriter;
import infra.Common.Common;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Helper {

    int SimilarIntentsCount = 0;
    int SimilarEntitiesCount = 0;
    private Common common = null;

    public Helper(Common commonObj) {
        common = commonObj;
    }

    public void calcSimilarIntents() {

        try {


        }
        catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void calcSimilarEntities() {

        try {


        }
        catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void setIntent(int amount) {

        try {

            SimilarIntentsCount = SimilarIntentsCount + amount;
        }
        catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void setEntitie(int amount) {

        try {

            SimilarEntitiesCount = SimilarEntitiesCount + amount;
        }
        catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void getIntent() {

        try {


        }
        catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void getEntitie() {

        try {


        }
        catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void playSound(String soundFile) {

        try {

            File f = new File(soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
        catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

}
