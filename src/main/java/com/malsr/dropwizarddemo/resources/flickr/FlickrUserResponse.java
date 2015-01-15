package com.malsr.dropwizarddemo.resources.flickr;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlickrUserResponse {

    @JsonProperty
    private Flickr _flickr;

    public FlickrUserResponse(String userName, String url) {
        this._flickr = new Flickr(userName, url);
    }

    public Flickr get_flickr() {
        return _flickr;
    }

    private class Flickr {
        @JsonProperty
        private User _user;

        private Flickr(String userName, String url) {
            this._user = new User(userName, url);
        }

        public User get_user() {
            return _user;
        }

        private class User {
            @JsonProperty
            private String userName;
            @JsonProperty
            private String _url;

            private User(String userName, String url) {
                this.userName = userName;
                this._url = url;
            }

            public String getUserName() {
                return userName;
            }

            public String get_url() {
                return _url;
            }
        }
    }

    @Override
    public String toString() {
        return "FlickrUserResponse{" +
                "_flickr=" + _flickr +
                '}';
    }
}