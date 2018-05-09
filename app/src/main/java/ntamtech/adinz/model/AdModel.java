package ntamtech.adinz.model;

import android.support.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by bassiouny on 24/04/18.
 */

public class AdModel extends RealmObject {
    @PrimaryKey
    private int id;
    private String url;
    private String type;

    public AdModel() {
    }

    public AdModel(int id, String url, String type) {
        this.id = id;
        this.url = url;
        this.type = type;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
