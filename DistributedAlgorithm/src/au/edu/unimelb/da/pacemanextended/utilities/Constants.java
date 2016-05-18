package au.edu.unimelb.da.pacemanextended.utilities;

import java.math.BigInteger;


public class Constants {

	public final static String DEFAULT_CHATROOM = "MainHall";
	
	public final static String NEW_IDENTITY = "newidentity";
	
	public final static String IDENTIY_CHANGE = "identitychange";
	
	public final static String MESSAGE = "message";
	
	public final static String CREATE_ROOM = "createroom";
	
	public final static String LIST_ROOM_COMMAND = "list";
	
	public final static String LIST_ROOM_MESSAGE_TYPE = "roomlist";
	
	public final static String ROOM_CHANGE_COMMAND = "join";
	
	public final static String ROOM_CHANGE_MESSAGE_TYPE = "roomchange";
	
	public final static String ROOM_CONTENTS_MESSAGE_TYPE = "roomcontents";
	
	public final static String ROOM_CONTENTS_COMMAND = "who";
	
	public final static String NEW_SIGNUP = "signUp";
	
	public static final String LOGIN = "login";
	
	public final static String KICK_USER = "kick";
	
	public final static String DELETE_ROOM = "delete";
	
	public final static String QUIT = "quit";
	
	//
	public final static String 	KEY_EXCHANGE = "publicKey";
	
	public final static String CHAT_USER_ID_REQULAR_EXPRESSION = "^[a-zA-Z][a-zA-Z0-9]{2,20}$";
	
	public final static String CHAT_ROOM_ID_REQULAR_EXPRESSION = "^[a-zA-Z][a-zA-Z0-9]{2,31}$";

	public static final int SECURITY_LEVEL = 2048;

	public static final BigInteger GENERATOR = new BigInteger("5");

	public static final String GUEST = "guest";

	
	
	public static boolean KEYWORD_FLAGE = false;
	
	
}
