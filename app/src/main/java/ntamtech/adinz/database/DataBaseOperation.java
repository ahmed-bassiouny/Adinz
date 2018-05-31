package ntamtech.adinz.database;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ntamtech.adinz.model.AdDriverZoneModel;
import ntamtech.adinz.model.AdModel;
import ntamtech.adinz.model.DriverAdModel;
import ntamtech.adinz.model.ZoneModel;

public class DataBaseOperation {

    private Realm realm;

    public DataBaseOperation() {
        realm = Realm.getDefaultInstance();
    }

    // save all of ads and zones in database
    public void insertZoneListAndAdList(AdDriverZoneModel adDriverZoneModel) {
        realm.beginTransaction();
        RealmResults<AdModel> rows = realm.where(AdModel.class).findAll();
        rows.deleteAllFromRealm();
        realm.insertOrUpdate(adDriverZoneModel.getZoneModels());
        realm.insertOrUpdate(adDriverZoneModel.getAdModels());
        realm.commitTransaction();
        realm.close();
    }
    public List<ZoneModel> getAllZone(){
        RealmResults<ZoneModel> results = realm.where(ZoneModel.class).findAll();
        return new ArrayList<>(results);
    }
    public List<AdModel> getAllAds(){
        RealmResults<AdModel> results = realm.where(AdModel.class).findAll();
        return new ArrayList<>(results);
    }
    public List<DriverAdModel> getAllDriverAdModel(){
        RealmResults<DriverAdModel> results = realm.where(DriverAdModel.class).findAll();
        return new ArrayList<>(results);
    }
    public void removeAllDriverAdModel(){
        realm.beginTransaction();
        RealmResults<DriverAdModel> results = realm.where(DriverAdModel.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public List<AdModel> getAllAdsBetweenTwoIds(long id1,long id2){
        RealmResults<AdModel> results = realm.where(AdModel.class).between("id",id1,id2).findAll();
        return new ArrayList<>(results);
    }

    public void insertOrUpdateDriverAdModel(DriverAdModel driverAdModel) {
        realm.beginTransaction();
        realm.insertOrUpdate(driverAdModel);
        realm.commitTransaction();
        realm.close();
    }
}
