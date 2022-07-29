package com.bhopalplus.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YogaGuideModel {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("google_meet_url")
        @Expose
        private String googleMeetUrl;
        @SerializedName("description_agenda")
        @Expose
        private String descriptionAgenda;
        @SerializedName("contact_address")
        @Expose
        private String contactAddress;
        @SerializedName("contact_number")
        @Expose
        private String contactNumber;
        @SerializedName("Multiple_image")
        @Expose
        private List<MultipleImage> multipleImage = null;
        @SerializedName("Multiple_youtube_link")
        @Expose
        private List<MultipleYoutubeLink> multipleYoutubeLink = null;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getGoogleMeetUrl() {
            return googleMeetUrl;
        }

        public void setGoogleMeetUrl(String googleMeetUrl) {
            this.googleMeetUrl = googleMeetUrl;
        }

        public String getDescriptionAgenda() {
            return descriptionAgenda;
        }

        public void setDescriptionAgenda(String descriptionAgenda) {
            this.descriptionAgenda = descriptionAgenda;
        }

        public String getContactAddress() {
            return contactAddress;
        }

        public void setContactAddress(String contactAddress) {
            this.contactAddress = contactAddress;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public List<MultipleImage> getMultipleImage() {
            return multipleImage;
        }

        public void setMultipleImage(List<MultipleImage> multipleImage) {
            this.multipleImage = multipleImage;
        }

        public List<MultipleYoutubeLink> getMultipleYoutubeLink() {
            return multipleYoutubeLink;
        }

        public void setMultipleYoutubeLink(List<MultipleYoutubeLink> multipleYoutubeLink) {
            this.multipleYoutubeLink = multipleYoutubeLink;
        }

        public class MultipleImage {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("image_id")
            @Expose
            private Integer imageId;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("path")
            @Expose
            private String path;
            @SerializedName("type")
            @Expose
            private String type;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getImageId() {
                return imageId;
            }

            public void setImageId(Integer imageId) {
                this.imageId = imageId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

        }

        public class MultipleYoutubeLink {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("yoga_guide_id")
            @Expose
            private Integer yogaGuideId;
            @SerializedName("url")
            @Expose
            private String url;
            @SerializedName("type")
            @Expose
            private String type;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getYogaGuideId() {
                return yogaGuideId;
            }

            public void setYogaGuideId(Integer yogaGuideId) {
                this.yogaGuideId = yogaGuideId;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
