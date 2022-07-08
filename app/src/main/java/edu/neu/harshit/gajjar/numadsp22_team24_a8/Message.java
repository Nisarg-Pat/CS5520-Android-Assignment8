package edu.neu.harshit.gajjar.numadsp22_team24_a8;


public class Message {
    public String recipient;
    public String sender;
    public String stickerID;

    public Message(){}

    public Message(String receipient, String sender, String stickerID){
        this.recipient = receipient;
        this.sender = sender;
        this.stickerID = stickerID;
    }

    @Override
    public String toString() {
        return "Message{" +
                "recipient='" + recipient + '\'' +
                ", sender='" + sender + '\'' +
                ", stickerID='" + stickerID + '\'' +
                '}';
    }
}
