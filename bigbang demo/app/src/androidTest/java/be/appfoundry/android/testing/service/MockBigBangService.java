package be.appfoundry.android.testing.service;

import android.content.Context;
import be.appfoundry.android.testing.R;
import be.appfoundry.android.testing.model.BigBangCharacter;
import be.appfoundry.android.testing.model.Persons;
import be.appfoundry.android.testing.util.RestUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import retrofit.Callback;
import retrofit.http.Path;

/**
 * @author Filip Maelbrancke
 */
public class MockBigBangService implements BigBangService {

    private static final int HTTP_STATUS_OK = 200;
    private static final String JSON_PERSONS = "persons.json";
    private static final String JSON_PERSON = "howard.json";

    private final Context context;
    private final Gson gson;

    public MockBigBangService(final Context context) {
        this.context = context;
        this.gson = new GsonBuilder().create();
    }

    @Override
    public void getPersons(Callback<Persons> callback) {
        InputStream in = context.getResources().openRawResource(R.raw.persons);
        try {
            Persons persons = gson.fromJson(RestUtils.getJsonReaderForInputStream(in), Persons.class);
            callback.success(persons, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPersonDetail(@Path("id") String id, Callback<BigBangCharacter> callback) {
        InputStream in = context.getResources().openRawResource(R.raw.howard);
        try {
            BigBangCharacter bigBangCharacter = gson.fromJson(RestUtils.getJsonReaderForInputStream(in), BigBangCharacter.class);
            callback.success(bigBangCharacter, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
