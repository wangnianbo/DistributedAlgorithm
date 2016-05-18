package au.edu.unimelb.da.pacemanextended.multicast.isis;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * Hold back queue to store message temporarily
 * Created by xy on 5/18/2016.
 */
public class MessageContainer {

    //use arraylist to store the message, Linkedlist could also be chosen but easy to get errors
    ArrayList<String> msgList;

    public MessageContainer() {
        msgList = new ArrayList<>();
    }

    //push message into list when the first type of message is received
    public synchronized void pushMsg(String Message){
//        System.out.println("Message: "+Message);
        msgList.add(Message);
    }


    //reorder queue according to their timestamp
    public synchronized void agreeMsg(String agreeSeq){
//        System.out.println("agreeSeq: "+agreeSeq);
        int size = msgList.size();
        String msg;
        JSONParser parser = new JSONParser();
        JSONObject objectInList, objectIn;

        //remove same uuid message from hold back queue
        for(int i = 0; i < size; i++){
            msg = msgList.get(i);
            try {
                objectInList = (JSONObject) parser.parse(msg);
                objectIn = (JSONObject) parser.parse(agreeSeq);
                if(objectInList.get("uuid").toString().equals(objectIn.get("uuid").toString())) {
                    msgList.remove(i);
                    size--;
                    i--;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //add the new message into the queue in a proper position
        if(msgList.isEmpty()){
            msgList.add(agreeSeq);
        }
        else{
            for(int j = 0; j < msgList.size(); j++){
                msg = msgList.get(j);
                try {
                    objectInList = (JSONObject) parser.parse(msg);
                    objectIn = (JSONObject) parser.parse(agreeSeq);
                    int tspIn = Integer.parseInt(objectIn.get("atsp").toString());
                    if(objectInList.containsKey("atsp")){
                        int tspInList = Integer.parseInt(objectInList.get("atsp").toString());
                        if(tspIn < tspInList){
                            if(j == 0)
                                msgList.add(j, agreeSeq);
                            else
                                msgList.add(j - 1, agreeSeq);
                            return;
                        }
                    }
                    else{
                        int tspInList = Integer.parseInt(objectInList.get("ptsp").toString());
                        if(tspIn < tspInList){
                            if(j == 0)
                                msgList.add(j, agreeSeq);
                            else
                                msgList.add(j - 1, agreeSeq);
                            return;
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            msgList.add(agreeSeq);
            return;
        }

    }

    //retrieve message from from hold back queue and deliver it
    public  String getMsg() {
        while (!hasMsg()) {
//            System.out.println("while (!hasMsg()) {");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String res = msgList.get(0);
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(res);
//            System.out.println(object.get("msgbody").toString());
            synchronized (msgList){
                msgList.remove(0);
            }

            return object.get("msgbody").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //check whether the queue is empty
    public synchronized boolean hasMsg(){
        if(msgList.isEmpty())
            return false;
        String msg = msgList.get(0);
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(msg);
            if(object.containsKey("atsp"))
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
