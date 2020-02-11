package com.example.mick.dockandroidlogin.sampling;

/**
 * Created by Mick on 2/5/2018.
 */

public class SpinnerObject {

    private  String databaseId;
    private String databaseValue;

    public SpinnerObject ( String databaseId , String databaseValue ) {
        this.databaseId = databaseId;
        this.databaseValue = databaseValue;
    }

    public String getId () {
        return databaseId;
    }

    public String getValue () {
        return databaseValue;
    }

    @Override
    public String toString () {
        return databaseValue;
    }

}
