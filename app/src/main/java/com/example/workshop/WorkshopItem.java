package com.example.workshop;
/**
 *Represent work shop item.
 */
public class WorkshopItem implements  Comparable<WorkshopItem>{

    private String name;

    private String image;

    private String description;

    private String text;

    private String video;

    public WorkshopItem(String name, String image, String description, String text, String video) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.text = text;
        this.video = video;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }


    public String getVideo() {
        return video;
    }


    @Override
    public int compareTo(WorkshopItem o) {
        int compare = this.name.compareTo(o.name);
        return compare;
    }
}
