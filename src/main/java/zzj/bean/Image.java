package zzj.bean;

import java.sql.Date;

public class Image {

    private int imageId;
    private String imageName;
    private String imageRawFormat;
    private byte[] imageRawData;
    private byte[] imageThumbData;
    private String imagedescription;
    private Date imageUploadDate;
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public String getImageRawFormat() {
        return imageRawFormat;
    }
    public void setImageRawFormat(String imageRawFormat) {
        this.imageRawFormat = imageRawFormat;
    }
    public byte[] getImageRawData() {
        return imageRawData;
    }
    public void setImageRawData(byte[] imageRawData) {
        this.imageRawData = imageRawData;
    }
    public byte[] getImageThumbData() {
        return imageThumbData;
    }
    public void setImageThumbData(byte[] imageThumbData) {
        this.imageThumbData = imageThumbData;
    }
    public String getImagedescription() {
        return imagedescription;
    }
    public void setImagedescription(String imagedescription) {
        this.imagedescription = imagedescription;
    }
    public Date getImageUploadDate() {
        return imageUploadDate;
    }
    public void setImageUploadDate(Date imageUploadDate) {
        this.imageUploadDate = imageUploadDate;
    }
    
    
}
