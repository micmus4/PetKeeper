package pl.petkeeper.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Jwiki {
    final String BASE_URL = "https://en.wikipedia.org/api/rest_v1/page/summary/";
    String subject = null;
    String displayTitle = "";
    String imageURL = "";
    String extractText = "";

    public Jwiki(String subject) {
        this.subject = subject;
        this.getData();
    }

    private void getData() {
        OkHttpClient client = new OkHttpClient();
        Request request = (new Request.Builder()).url("https://en.wikipedia.org/api/rest_v1/page/summary/" + this.subject).get().build();

        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(data);
            this.displayTitle = (String) jsonObject.get("displaytitle");
            JSONObject jsonObjectOriginalImage = (JSONObject) jsonObject.get("originalimage");
            this.imageURL = (String) jsonObjectOriginalImage.get("source");
            this.extractText = (String) jsonObject.get("extract");
        } catch (ParseException | IOException var8) {
            var8.printStackTrace();
        }

    }

    public String getDisplayTitle() {
        return this.displayTitle;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public String getExtractText() {
        return this.extractText;
    }
}
