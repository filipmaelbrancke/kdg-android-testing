package be.appfoundry.android.testing.model;

import android.text.TextUtils;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filip Maelbrancke
 */
public class Person {

    private String id;
    private String name;
    private String lastName;
    private String realName;
    private String profession;
    private String bio;
    private String imageUri;
    @SerializedName("url")
    private String wikipediaUrl;

    public Person(String id, String name, String lastName, String realName, String profession,
        String bio, String imageUri, String wikipediaUrl) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.realName = realName;
        this.profession = profession;
        this.bio = bio;
        this.imageUri = imageUri;
        this.wikipediaUrl = wikipediaUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRealName() {
        return realName;
    }

    public String getProfession() {
        return profession;
    }

    public String getBio() {
        return bio;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder(name);
        if (!TextUtils.isEmpty(lastName)) {
            sb.append(" ").append(lastName);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("name", name + lastName)
            .add("id", id)
            .toString();
    }
}
