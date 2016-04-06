package com.roman.walmartapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ItemObject implements Parcelable {
    Bitmap thumbnail;
    String itemName;
    String priceText;
    String captionText;
    String modelText;
    String mItemUrl;

    ItemObject(Bitmap photo_thumbnail, String photo_itemName, String itemPrice, String caption,
               String model, String item_url) {
        this.thumbnail = photo_thumbnail;
        this.itemName = photo_itemName;
        this.priceText = itemPrice;
        this.captionText = caption;
        this.modelText = model;
        this.mItemUrl = item_url;
    }

    protected ItemObject(Parcel in) {
        thumbnail = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        itemName = in.readString();
        priceText = in.readString();
        captionText = in.readString();
        modelText = in.readString();
        mItemUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(thumbnail);
        dest.writeString(itemName);
        dest.writeString(priceText);
        dest.writeString(captionText);
        dest.writeString(modelText);
        dest.writeString(mItemUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ItemObject> CREATOR = new Parcelable.Creator<ItemObject>() {
        @Override
        public ItemObject createFromParcel(Parcel in) {
            return new ItemObject(in);
        }

        @Override
        public ItemObject[] newArray(int size) {
            return new ItemObject[size];
        }
    };
}