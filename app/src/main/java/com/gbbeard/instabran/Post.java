package com.gbbeard.instabran;

class Post {
    public String imageUrl;
    public String username;
    public String description;
    public String key;

    public Post (String key, String imageUrl, String description, String username) {
        this.key = key;
        this.imageUrl = imageUrl;
        this.description = description;
        this.username = username;
    }
}
