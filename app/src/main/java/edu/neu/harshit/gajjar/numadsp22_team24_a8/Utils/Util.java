package edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.R;

public class Util {

    public static HashMap<String, Integer> stickerIds;

    public static HashMap<String, Integer> getStickerIds(Context ct){
        stickerIds = new HashMap<>();
        stickerIds.put("sticker1", R.drawable.sticker1);
        stickerIds.put("sticker2", R.drawable.sticker2);
        stickerIds.put("sticker3", R.drawable.sticker3);
        stickerIds.put("sticker4", R.drawable.sticker4);
        stickerIds.put("sticker5", R.drawable.sticker5);
        stickerIds.put("sticker6", R.drawable.sticker6);
        stickerIds.put("sticker7", R.drawable.sticker7);
        stickerIds.put("sticker8", R.drawable.sticker8);
        stickerIds.put("sticker9", R.drawable.sticker9);
        stickerIds.put("sticker10", R.drawable.sticker10);

        return stickerIds;
    }

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

    public static String getGMTTimestamp(){
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormatGmt.format(new Date())+"";
    }

    public static String utcToLocalTime(String utc) {
//        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
//        dateFormatGmt.setTimeZone(Calendar.getInstance().getTimeZone());
//        try{
//            Date date = dateFormatGmt.parse("10:07:2022 17:05:51");
//            Log.i("dateis", date.toString());
//        } catch (ParseException e){
//            Log.i("error", "got");
//        }

//        SimpleDateFormat FORMATTER = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
////        FORMATTER.setTimeZone(TimeZone.getTimeZone("GMT"));
//        Date currentDate = FORMATTER.parse("10:07:2022 18:46:42");
//
//
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
//
//        Log.i("mytimezone", tz.getID().toString());
//
//        String s = TimeZone.getTimeZone(TimeZone.getDefault().getID())
//                .getDisplayName(false, TimeZone.SHORT);
//        Log.i("zoneid", s);
//
//        TimeZone myTimeZone = TimeZone.getTimeZone(s);
//        FORMATTER.setTimeZone(myTimeZone);
//        String sDateInMyTime = FORMATTER.format(currentDate);
//        Log.i("dateinutc", sDateInMyTime);


        String inputValue = utc;
        Instant timestamp = Instant.parse(inputValue);
        ZonedDateTime losAngelesTime = timestamp.atZone(ZoneId.of(tz.getID()));
        return losAngelesTime.toString();
    }

    public static String convertTocurrentDateTime(String utc){
        String localTime = utcToLocalTime(utc);
        String[] dateTime = localTime.split("T");
        String[] date = dateTime[0].split("-");
        String[] temp = dateTime[1].split("-");
        String[] time = temp[0].split(":");

        String currentDate = Instant.now().toString().split("T")[0];
        String currentDay = currentDate.split("-")[2];
        String dateToShow = "";

        if(currentDate.equals(dateTime[0])){
            dateToShow += "Today";
        } else if(Integer.valueOf(currentDay) - 1 == Integer.valueOf(date[2])){
            dateToShow += "Yesterday";
        } else {
            dateToShow += date[0] + " ";
            switch (date[1]){
                case "01":
                    dateToShow += "Jan";
                    break;
                case "02":
                    dateToShow += "Feb";
                    break;
                case "03":
                    dateToShow += "Mar";
                    break;
                case "04":
                    dateToShow += "Apr";
                    break;
                case "05":
                    dateToShow += "May";
                    break;
                case "06":
                    dateToShow += "June";
                    break;
                case "07":
                    dateToShow += "July";
                    break;
                case "08":
                    dateToShow += "Aug";
                    break;
                case "09":
                    dateToShow += "Sept";
                    break;
                case "10":
                    dateToShow += "Oct";
                    break;
                case "11":
                    dateToShow += "Nov";
                    break;
                case "12":
                    dateToShow += "Dec";
                    break;
            }

            dateToShow += " " + date[2];
        }

        int t = Integer.valueOf(time[0]);
        if( t <= 11){
            dateToShow += " " + time[0] + ":" + time[1] + " AM";
        } else if(t == 12) {
            dateToShow += " " + time[0] + ":" + time[1] + " PM";
        } else {
            dateToShow += " " + (Integer.valueOf(time[0] )- 12) + ":" + time[1] + " PM";
        }

        return dateToShow;
    }
}
