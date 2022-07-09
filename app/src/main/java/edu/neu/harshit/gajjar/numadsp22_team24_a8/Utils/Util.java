package edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils;

public class Util {
    public static String generateChatID(String user1, String user2){
        String chatId = "chat_";
        int compare_num = user1.compareTo(user2);
        if(compare_num > 0){
            chatId += user1 + "_" + user2;
        } else {
            chatId += user2 + "_" + user1;
        }
        return chatId;
    }
}
