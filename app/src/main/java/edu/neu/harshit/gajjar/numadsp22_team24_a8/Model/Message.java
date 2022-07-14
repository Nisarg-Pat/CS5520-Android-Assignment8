package edu.neu.harshit.gajjar.numadsp22_team24_a8.Model;

public class Message {
    String datetime;
    String username;
    String sticker;

    public Message(String datetime, String username, String sticker) {
        this.datetime = datetime;
        this.username = username;
        this.sticker = sticker;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getUsername() {
        return username;
    }

    public String getSticker() {
        return sticker;
    }
}
