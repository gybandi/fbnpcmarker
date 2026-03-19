package com.gybandi.datascraper.model;

import java.util.Objects;

public class ScrapedData {

    public ScrapedData(String profileUri) {
        this.profileUri = profileUri;
    }

    private String profileUri;

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScrapedData that = (ScrapedData) o;
        return Objects.equals(profileUri, that.profileUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileUri);
    }
}
