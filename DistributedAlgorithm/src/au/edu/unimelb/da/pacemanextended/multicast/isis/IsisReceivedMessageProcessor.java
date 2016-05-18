package au.edu.unimelb.da.pacemanextended.multicast.isis;

import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

/**
 * Created by xy on 5/18/2016.
 */
public class IsisReceivedMessageProcessor extends
        Thread {

    MessageReceiver messageReceiver;
    MessageSender messageSender;
    MessageContainer messageContainer        ;

    //every thread keeps two logical lock, i.e proposed sequence number and agreed sequence number
    Timestamp ptsp, atsp;
    //store propose numbers from each socket for a specific message;
    //key stands for message id, and value stands for proposed number from each socket
    HashMap<String, ArrayList<Integer>> proposedList = new HashMap<>();
    public IsisReceivedMessageProcessor(MessageSender messageSender, MessageReceiver messageReceiver, MessageContainer messageContainer){
        this.messageContainer = messageContainer;
        this.messageReceiver = messageReceiver;
        this.messageSender = messageSender;
        ptsp = new Timestamp(0);
        atsp = new Timestamp(0);
    }
    public void run(){
        while (true){
            String messageString = messageReceiver.backMessage();
//            System.out.println("IsisReceivedMessageProcessor: "+messageString);
            JSONParser parser = new JSONParser();
            JSONObject object = null;
            try {
                 object = (JSONObject)parser.parse(messageString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final int type = Integer.parseInt(object.get("type").toString());
            final String playerID = object.get("playerID").toString();
            final String uuid = object.get("uuid").toString();
            final int socketNumber = messageReceiver.getServerSocketList().size();
            //first message, attach message uid and sender information
            if(type == 0){
                ptsp.settsp(Math.max(ptsp.gettsp(), atsp.gettsp()) + 1);
                object.put("ptsp", ptsp.gettsp());
                object.remove("type");
                object.put("type", 1);              // update type to 1, i.e send proposed number to sender
                messageContainer.pushMsg(object.toString());//put message to hold back queue
                messageSender.putMessage(playerID, object.toString());  //send proposed number to sender
            }else if (type == 1){
                //second message, priority proposed number to reach an agreement
                if(!this.proposedList.isEmpty() && this.proposedList.get(uuid) != null) {
                    ArrayList<Integer> list = this.proposedList.get(uuid);
                    list.add(Integer.parseInt(object.get("ptsp").toString()));
                    if (list.size() == socketNumber) {
                        //receive all message from every socket, compute the max
                        object.put("atsp", Collections.max(list));
                        object.remove("type");
                        object.put("type", 2);
                        messageSender.putMessage(object.toString());
                        this.proposedList.remove(uuid);
                    } else {
                        this.proposedList.put(uuid, list);
                    }
                }
                else{
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(Integer.parseInt(object.get("ptsp").toString()));
                    proposedList.put(uuid, list);
                }
            }else if (type == 2) {
                final int agreeNumber = Integer.parseInt(object.get("atsp").toString());
                final int promoNumber = Integer.parseInt(object.get("ptsp").toString());
                this.atsp.settsp(Math.max(atsp.gettsp(), agreeNumber));
                     //agree number not equal to proposed number, update holdback queue
                    messageContainer.agreeMsg(messageString);    //remove original message and add the new message according to atsp

            }

        }
    }
}
