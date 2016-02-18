package edu.diegod5000.itopapps.services.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by diego-d on 17/02/16.
 */
public class App implements Parcelable {
    private String name;
    private String imageUrl;
    private String summary;
    private String rights;
    private String downloadUrl;
    private String artist;
    private String category;
    private String price;

    public App() {
    }

    public App(JSONObject jsonObject) {
        this.name = jsonObject.optJSONObject("im:name").optString("label");
        this.imageUrl = jsonObject.optJSONArray("im:image").optJSONObject(2).optString("label");
        this.summary = jsonObject.optJSONObject("summary").optString("label");
        this.rights = jsonObject.optJSONObject("rights").optString("label");
        this.downloadUrl = jsonObject.optJSONObject("link").optJSONObject("attributes").optString("href");
        this.artist = jsonObject.optJSONObject("im:artist").optString("label");
        this.category = jsonObject.optJSONObject("category").optJSONObject("attributes").optString("label");

        JSONObject jsonPrice = jsonObject.optJSONObject("im:price").optJSONObject("attributes");
        double price = jsonPrice.optDouble("ammount");
        if (price>0)
        this.price = String.format("%.2f %s", price, jsonPrice.optString("currency"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    protected App(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        summary = in.readString();
        rights = in.readString();
        downloadUrl = in.readString();
        artist = in.readString();
        category = in.readString();
        price = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(summary);
        dest.writeString(rights);
        dest.writeString(downloadUrl);
        dest.writeString(artist);
        dest.writeString(category);
        dest.writeString(price);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<App> CREATOR = new Parcelable.Creator<App>() {
        @Override
        public App createFromParcel(Parcel in) {
            return new App(in);
        }

        @Override
        public App[] newArray(int size) {
            return new App[size];
        }
    };
}