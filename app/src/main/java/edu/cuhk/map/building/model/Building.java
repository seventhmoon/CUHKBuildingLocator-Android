package edu.cuhk.map.building.model;

/**
 * Created by fung on 8/30/2015.
 */
public class Building {


    private String mNameChi;
    private String mNameEng;
    private String mAbbr;
    private double mLongtitue;
    private double mLatitude;

    public Building(String abbr, String nameEng, String nameChi, double latitude, double longtitue) {
        mNameChi = nameChi;
        mNameEng = nameEng;
        mAbbr = abbr;
        mLatitude = latitude;
        mLongtitue = longtitue;
    }

    public String getNameChi() {
        return mNameChi;
    }


    public String getNameEng() {
        return mNameEng;
    }


    public String getAbbr() {
        return mAbbr;
    }


    public double getLongtitue() {
        return mLongtitue;
    }


    public double getLatitude() {
        return mLatitude;
    }

}
