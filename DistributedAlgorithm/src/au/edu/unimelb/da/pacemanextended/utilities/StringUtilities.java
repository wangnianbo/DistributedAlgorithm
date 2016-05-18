package au.edu.unimelb.da.pacemanextended.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StringUtilities {

	Logger log = Logger.getLogger(StringUtilities.class);
	
	public static boolean isStringNotEmpty(String source){
		if(null == source || source.length() ==0 ){
			return false;
		}
		return true;
	}
	
	public static String getMidUTFString(String source){
//		source = source.substring(source.indexOf("{"));
//		source = source.substring(0, source.lastIndexOf("}")+1);
		return source;
	}
	
	public static boolean islegalUserId(String id){
		Pattern p = Pattern.compile(Constants.CHAT_USER_ID_REQULAR_EXPRESSION);
		 Matcher m = p.matcher(id);
		 return  m.matches();
	}
	
	public static boolean islegalRoomId(String roomid){
		Pattern p = Pattern.compile(Constants.CHAT_ROOM_ID_REQULAR_EXPRESSION);
		 Matcher m = p.matcher(roomid);
		 return  m.matches();
	}
	
	public static void main(String[] args) {
		System.out.println(islegalRoomId("1s11"));
	}
}
