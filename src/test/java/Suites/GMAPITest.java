package Suites;

import infra.Common.Common;
import infra.GM.API.API;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertTrue;

public class GMAPITest {

    private static Common common = null;


    @BeforeAll
    public static void setup() {

        common = new Common();

        common.createCSVReport(".//Report//result.csv");
    }

    @BeforeEach
    public void init(){

    }

    @Test
    void GMCLUAPIEmulator(){

        API api = new API(common);

        String[] expectedIntent = { "Update Temperature", "Update Temperature" };
        String[] expectedEntity = { "Decrease temperature", "Increase temperature" };

        JSONArray utterance = api.getUtteranceData(".//Tools//data.json");

        //api.sendUtteranceAPI(utterance);

        for (int i=0; i < utterance.size(); i++) {

            common.Report("-----------utterance number: " + i);

            //reportResultRow.add("please make it cooler");

            JSONObject responce = api.sendSingleUtteranceAPI(utterance.get(i).toString());

            String APIintent = responce.get("API intent").toString();

            common.Report("Received API intent " + APIintent);

            common.reportResultRow.add(APIintent);
            common.reportResultRow.add(APIintent);

            boolean res = api.compareIntent(APIintent, i);
            //boolean res = api.compareIntent(APIintent, expectedIntent[i]);

            //assertTrue(res);

            String APIentity = responce.get("API entity").toString();

            common.Report("Received API entity " + APIentity);

            common.reportResultRow.add(APIentity);
            common.reportResultRow.add(APIentity);

            res = api.compareEntity(APIentity, i);
            //res = api.compareEntity(APIintent, expectedEntity[i]);


            //assertTrue(res);

            common.reportCSVReport(".//Report//result.csv",  common.reportResultRow);

        }



    }

    @AfterEach
    public void teardown() {

        //common.closeApp();
    }

    @AfterAll
    public static void cleanup() {
        //common.stopEmulator(deviceID);
    }
}
