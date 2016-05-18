package au.edu.unimelb.da.pacemanextended.multicast.isis;

import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;
import au.edu.unimelb.da.pacemanextended.multicast.simple.SimpleMulticastMessage;
import org.json.simple.JSONObject;

import java.util.UUID;

/**
 * Created by xy on 5/18/2016.
 */
public class IsisMulticastMessage extends SimpleMulticastMessage {

    MessageContainer messageContainer = new MessageContainer();
    IsisReceivedMessageProcessor isisReceivedMessageProcessor ;
    public IsisMulticastMessage(MessageReceiver messageReceiver, MessageSender messageSender) {
        super(messageReceiver, messageSender);


        isisReceivedMessageProcessor = isisReceivedMessageProcessor = new IsisReceivedMessageProcessor(super.messageSender,
                super.messageReceiver, messageContainer);
        isisReceivedMessageProcessor.start();
    }

    @Override
    public String backMessage() {
        return messageContainer.getMsg();
    }

    @Override
    public boolean putMessage(String playerID, String jsonMsg) {
        String uuid = UUID.randomUUID().toString();
        JSONObject json = new JSONObject();
        json.put("playerID", playerID);
        json.put("uuid", uuid); //unique id for each message from game center
        json.put("msgbody", jsonMsg);
        json.put("type", 0);    //type 0, initial the message
        super.putMessage(json.toString());

        return true;
        //return super.messageSender.putMessage(playerID, jsonMsg);
    }

    @Override
    public boolean putMessage(String jsonMsg) {
        String uuid = UUID.randomUUID().toString();
        JSONObject json = new JSONObject();
        json.put("playerID", messageSender.getPlayerID());
        json.put("uuid", uuid); //unique id for each message from game center
        json.put("msgbody", jsonMsg);
        json.put("type", 0);    //type 0, initial the message
        super.putMessage(json.toString());
//        System.out.print(":SENDING");
        //super.backMessage();
        return super.putMessage(json.toString());
    }


}
