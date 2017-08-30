package ab.byteshiftserverv1.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ab.byteshiftserverv1.model.Beacon;
import ab.byteshiftserverv1.model.User;
import ab.byteshiftserverv1.model.Visit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by arlen on 8/30/17.
 */

public class ApiService {
    public static void getBeacons(Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.SERVER_PATH).newBuilder();
        urlBuilder.addPathSegment(Constants.BEACON_ROUTE);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public ArrayList<Beacon> processBeaconResults(Response response){
        ArrayList<Beacon> beacons = new ArrayList<>();

        try{
            String jsonData = response.body().string();
            if (response.isSuccessful()){
                JSONArray resultsJSON = new JSONArray(jsonData);
                for (int i = 0; i < resultsJSON.length(); i++){
                    JSONObject beaconJSON = resultsJSON.getJSONObject(i);
                    String beaconName = beaconJSON.getString("name");
                    String beaconId = beaconJSON.getString("id");
                    String beaconIdentity = beaconJSON.getString("identification_number");
                    Beacon beacon = new Beacon(beaconName, beaconId, beaconIdentity);
                    beacons.add(beacon);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return beacons;
    }

    public static void getUsers(Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.SERVER_PATH).newBuilder();
        urlBuilder.addPathSegment(Constants.USER_ROUTE);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public ArrayList<User> processUserResults(Response response){
        ArrayList<User> users = new ArrayList<>();

        try{
            String jsonData = response.body().string();
            if (response.isSuccessful()){
                JSONArray resultsJSON = new JSONArray(jsonData);
                for (int i = 0; i < resultsJSON.length(); i++){
                    JSONObject userJSON = resultsJSON.getJSONObject(i);
                    String firstName = userJSON.getString("first_name");
                    String lastName = userJSON.getString("last_name");
                    String email = userJSON.getString("email");
                    String id = userJSON.getString("id");
                    String status = userJSON.getString("status");
                    User user = new User(firstName, lastName, email, id, status);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }
    public static void getVisits(Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.SERVER_PATH).newBuilder();
        urlBuilder.addPathSegment(Constants.VISIT_ROUTE);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public ArrayList<Visit> processVisitResults(Response response){
        ArrayList<Visit> visits = new ArrayList<>();

        try{
            String jsonData = response.body().string();
            if (response.isSuccessful()){
                JSONArray resultsJSON = new JSONArray(jsonData);
                for (int i = 0; i < resultsJSON.length(); i++){
                    JSONObject visitJSON = resultsJSON.getJSONObject(i);
                    String day = visitJSON.getString("day");
                    String enterTime = visitJSON.getString("enter_time");
                    String exitTime = visitJSON.getString("exit_time");
                    String userId = visitJSON.getString("user_id");
                    String beaconId = visitJSON.getString("beacon_id");
                    Visit visit = new Visit(day, enterTime, exitTime, userId, beaconId);
                    visits.add(visit);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return visits;
    }
}
