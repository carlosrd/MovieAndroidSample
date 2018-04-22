package com.carlosrd.movieapp.data.remote.model;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ConfigurationResponse {

    @SerializedName("images")
    private ImageConfig imagesConfig;

    public ConfigurationResponse(ImageConfig imagesConfig) {
        this.imagesConfig = imagesConfig;
    }

    public ImageConfig getImagesConfig() {
        return imagesConfig;
    }

    public class ImageConfig {

        @SerializedName("base_url")
        private String baseURL;

        @SerializedName("secure_base_url")
        private String secureBaseURL;

        @SerializedName("backdrop_sizes")
        private ArrayList<String> backdropSizes;

        @SerializedName("poster_sizes")
        private ArrayList<String> posterSizes;

        public ImageConfig(String baseURL, String secureBaseURL,
                                     ArrayList<String> backdropSizes, ArrayList<String> posterSizes) {
            this.baseURL = baseURL;
            this.secureBaseURL = secureBaseURL;
            this.backdropSizes = backdropSizes;
            this.posterSizes = posterSizes;
        }

        public String getBaseURL() {
            return baseURL;
        }

        public String getSecureBaseURL() {
            return secureBaseURL;
        }

        public ArrayList<String> getBackdropSizes() {
            return backdropSizes;
        }

        public ArrayList<String> getPosterSizes() {
            return posterSizes;
        }
    }

}
