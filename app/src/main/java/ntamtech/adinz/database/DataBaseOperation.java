package ntamtech.adinz.database;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ntamtech.adinz.controller.HomeController;
import ntamtech.adinz.model.AdDriverZoneModel;
import ntamtech.adinz.model.AdModel;
import ntamtech.adinz.model.DriverAdModel;
import ntamtech.adinz.model.ZoneModel;

public class DataBaseOperation {


    // save all of ads and zones in database
    public void insertZoneListAndAdList(AdDriverZoneModel adDriverZoneModel) {
        Realm realm = Realm.getDefaultInstance();
        if(!realm.isInTransaction())
            realm.beginTransaction();
        RealmResults<AdModel> rows = realm.where(AdModel.class).findAll();
        rows.deleteAllFromRealm();
        realm.insertOrUpdate(adDriverZoneModel.getZoneModels());
        realm.insertOrUpdate(adDriverZoneModel.getAdModels());
        realm.commitTransaction();
    }

    // save all of ads and zones in database
    public void insertAdList(final List<AdModel> adModels) {
        Realm realm = Realm.getDefaultInstance();
        if(!realm.isInTransaction())
            realm.beginTransaction();
        realm.insertOrUpdate(adModels);
        realm.commitTransaction();
        realm.close();
    }

    public List<ZoneModel> getAllZone() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ZoneModel> results = realm.where(ZoneModel.class).findAll();
        return new ArrayList<>(results);
    }

    public List<AdModel> getAllAds() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<AdModel> results = realm.where(AdModel.class).findAll();
        return new ArrayList<>(results);
    }

    public List<DriverAdModel> getAllDriverAdModelLimit() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<DriverAdModel> results = realm.where(DriverAdModel.class).findAll();
        if (results.size() > HomeController.SYNC_PER_COUNT)
            return results.subList(0, HomeController.SYNC_PER_COUNT);
        else
            return results.subList(0, results.size());
        //return new ArrayList<>(results);
    }

    public void removeAllDriverAdModel(final long startId) {

        Realm realm = Realm.getDefaultInstance();
        final long endId = startId + HomeController.SYNC_PER_COUNT;
        realm.beginTransaction();
        RealmResults<DriverAdModel> results = realm.where(DriverAdModel.class)
                .between("id", startId, endId).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public List<AdModel> getAllAdsBetweenTwoIds(long id1, long id2) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<AdModel> results = realm.where(AdModel.class).between("id", id1, id2).findAll();
        return new ArrayList<>(results);
    }

    public void insertDriverAdModel(DriverAdModel driverAdModel) {
        Realm realm = Realm.getDefaultInstance();
        if(!realm.isInTransaction())
            realm.beginTransaction();
        Number number = realm.where(DriverAdModel.class).max("id");
        if (number != null){
            long autoIncrementId = number.longValue();
            autoIncrementId++;
            driverAdModel.setId(autoIncrementId);
        }
        realm.copyToRealm(driverAdModel);
        realm.commitTransaction();
    }

    public int getDriverAdModelSize() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(DriverAdModel.class).findAll().size();
    }

}
