package ntamtech.adinz.utils;

import android.content.Context;
import android.webkit.URLUtil;

import com.downloader.Error;
import com.downloader.OnDownloadListener;

import java.io.File;
import java.util.List;

import ntamtech.adinz.controller.HomeController;
import ntamtech.adinz.database.DataBaseOperation;
import ntamtech.adinz.interfaces.CompleteInterface;
import ntamtech.adinz.model.AdModel;

public class DownloadFiles {

    private int adsSize = 0;
    private int iteration = 0;
    private List<AdModel> ads;
    private HomeController controller;
    private Context context;

    public DownloadFiles(Context context) {
        this.context = context;
    }

    public void getAdsAndDownloadFiles(CompleteInterface anInterface){
        // get all ads from data base
        DataBaseOperation baseOperation = new DataBaseOperation();
        ads = baseOperation.getAllAds();
        adsSize = ads.size();
        downloadFile(anInterface);
    }

    private void downloadFile(final CompleteInterface anInterface) {
        if(iteration >= adsSize) {
            anInterface.onComplete();
            return;
        }
        AdModel item = ads.get(iteration);
        // get image file
        String fileName = URLUtil.guessFileName(item.getAdUrl(), null, null);
        // file path
        String path;
        // detect type
        if(item.getTypeId() == Constant.IMAGE_AD){
            path =  getHomeController().imagePath ;
        }else if(item.getTypeId() == Constant.VIDEO_AD) {
            path = getHomeController().videoPath;
        }else {
            iteration++;
            downloadFile(anInterface);
            return;
        }
        File file = new File(path+fileName);
        if(!file.exists()){
            getHomeController().loadFile(item.getAdUrl(), path, new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    iteration++;
                    downloadFile(anInterface);
                }

                @Override
                public void onError(Error error) {
                    iteration++;
                    downloadFile(anInterface);
                }
            });
        }else {
            // file existed so i will search second file
            iteration++;
            downloadFile(anInterface);
        }
    }
    private HomeController getHomeController(){
        if(controller == null)
            controller = new HomeController(context);
        return controller;
    }
}
