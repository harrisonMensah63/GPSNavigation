package com.harryWorld.navigationGPS.map.utils;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.harryWorld.navigationGPS.map.constant.Converter;

import java.util.List;

@Entity
public class Categories {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(Converter.class)
    private List<String> itemList;

    private String itemHeader;
    private int error;
    private String errorMessage;

    @Ignore
    public Categories(int id, List<String> itemList, String itemHeader, int error, String errorMessage) {
        this.id = id;
        this.itemList = itemList;
        this.itemHeader = itemHeader;
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public Categories() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }

    public String getItemHeader() {
        return itemHeader;
    }

    public void setItemHeader(String itemHeader) {
        this.itemHeader = itemHeader;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
