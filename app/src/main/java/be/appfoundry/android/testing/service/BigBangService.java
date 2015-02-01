package be.appfoundry.android.testing.service;

import be.appfoundry.android.testing.model.BigBangCharacter;
import be.appfoundry.android.testing.model.Persons;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Filip Maelbrancke
 */
public interface BigBangService {


    @GET("/app/persons.json")
    public void getPersons(Callback<Persons> callback);

    @GET("/app/persons/{id}.json")
    public void getPersonDetail(@Path("id") String id, Callback<BigBangCharacter> callback);
}
