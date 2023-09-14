package infra.GM.API;

import infra.Common.Common;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class API {

    private Common common = null;
    private JSONObject jsonObjectData;

    public API(Common commonObj) {
        common = commonObj;
    }

    public JSONArray getUtteranceData(String filePath) {

        JSONArray utteranceList = null;

        try {

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));

            jsonObjectData = (JSONObject) obj;

            utteranceList = (JSONArray) jsonObjectData.get("Utterance");

            common.Report("Reading Utterances from config");

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

        return utteranceList;
    }

    public void sendUtteranceAPI(JSONArray utteranceList) {
        try {

            Iterator<JSONObject> iterator = utteranceList.iterator();
            while (iterator.hasNext()) {

                common.Report("Sending Utterance to BlackBox named: " + iterator.next());

                JSONObject intentJson = new JSONObject();

                if(iterator.next().equals("please make it cooler")){

                    intentJson.put("API intent", "Update Temperature");
                    intentJson.put("API entity", "Decrease temperature");

                }if(iterator.next().equals("I’m cold")){

                    intentJson.put("API intent", "Update Temperature");
                    intentJson.put("API entity", "Decrease temperature");
                }

            }

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

    }

    public JSONObject sendSingleUtteranceAPI(String utterance) {

        JSONObject responce = null;

        try {

            common.Report("Sending Utterance to BlackBox named: " + utterance);

            common.reportResultRow.add(utterance);

            responce = new JSONObject();

            if(utterance.equals("please make it cooler")){

                responce.put("API intent", "Update Temperature");
                responce.put("API entity", "Decrease temperature");

            }if(utterance.equals("I’m cold")){

                responce.put("API intent", "Update Temperature");
                responce.put("API entity", "Decrease temperature");
            }

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

        return responce;
    }

    public Boolean compareIntent(String receivedIntent, int expectedIndex) {

        try {

            JSONArray expectedResults = (JSONArray) jsonObjectData.get("Actual intent");

            if(receivedIntent.equals(expectedResults.get(expectedIndex))){
                common.Report("Received Intent \""+receivedIntent+"\" equals to expected \"" + expectedResults.get(expectedIndex)+"\"");
                common.reportResultRow.add("Similar");
                return true;
            }else{
                common.Report("ERROR: Received Intent \""+receivedIntent+"\" Not equals to expected \"" + expectedResults.get(expectedIndex)+"\"");
                common.reportResultRow.add("Non-similar");
            }

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

        return false;
    }

    public Boolean compareEntity(String receivedEntity, int expectedIndex) {

        try {

            JSONArray expectedResults = (JSONArray) jsonObjectData.get("Actual entity");

            if(receivedEntity.equals(expectedResults.get(expectedIndex))){
                common.Report("Received Intent \""+receivedEntity+"\" equals to expected \"" + expectedResults.get(expectedIndex)+"\"");
                common.reportResultRow.add("Similar");
                return true;
            }else{
                common.Report("ERROR: Received Entity \""+receivedEntity+"\" Not equals to expected \"" + expectedResults.get(expectedIndex)+"\"");
                common.reportResultRow.add("Non-similar");
            }

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

        return false;
    }

    public Boolean compareIntent(String receivedIntent, String expectedIntend) {

        try {

            JSONArray expectedResults = (JSONArray) jsonObjectData.get("Actual intent");

            if(receivedIntent.equals(expectedIntend)){
                common.Report("Received Intent \""+receivedIntent+"\" equals to expected \"" + expectedIntend+"\"");
                common.reportResultRow.add("Similar");
                return true;
            }else{
                common.Report("ERROR: Received Intent \""+receivedIntent+"\" Not equals to expected \"" + expectedIntend+"\"");
                common.reportResultRow.add("Non-similar");
            }

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

        return false;
    }

    public Boolean compareEntity(String receivedEntity, String expectedEntity) {

        try {

            JSONArray expectedResults = (JSONArray) jsonObjectData.get("Actual entity");

            if(receivedEntity.equals(expectedEntity)){
                common.Report("Received Intent \""+receivedEntity+"\" equals to expected \"" + expectedEntity+"\"");
                common.reportResultRow.add("Similar");
                return true;
            }else{
                common.Report("ERROR: Received Entity \""+receivedEntity+"\" Not equals to expected \"" + expectedEntity+"\"");
                common.reportResultRow.add("Non-similar");
            }

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

        return false;
    }

}
