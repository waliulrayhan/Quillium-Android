package com.quillium.Model;

public class Follow {
    private String followedBy;
    private long followedAt;

    public Follow(int profile) {
        Profile = profile;
    }

    public String getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(String followedBy) {
        this.followedBy = followedBy;
    }

    public long getFollowedAt(long time) {
        return followedAt;
    }

    public void setFollowedAt(long followedAt) {
        this.followedAt = followedAt;
    }

//===================================================================================================
    public int getProfile() {
        return Profile;
    }

    public void setProfile(int profile) {
        this.Profile = profile;
    }

    int Profile;
    //===================================================================================================
}
