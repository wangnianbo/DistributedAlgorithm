package au.edu.unimelb.da.pacemanextended.game;
import java.util.Random;


public class Node {

	// 2= follower 1= candidate 0= leader
	public int state = 2;
	public int term = 0;
	public int voteCount = 0;
	public boolean signal = false; // true become candidate 
	public boolean reset = false;  // reset time out
	public int number_node = 4;
	public int phase = 0;
	public String[] a = new String[10];
	public long[] b = new long[10];
	public int id;
	public String msg = ""; // every node message
	public String backMsg ="";
	
	public Node(){
		for(int i=0; i<10; i++){
			a[i]="";
		}
		for(int i=0; i<10; i++){
			b[i]= 0;
		}
	}
	
	public void insert(String content, String timestamp){
		
		long time =Long.valueOf(timestamp);
		
		// insert
		for(int i=0; i<10; i++){
			if(a[i].equals("")){
				a[i]=content;
				b[i]=time;
				break;
			}	
		}
		
		// bubblesort
		 for (int i = 0; i <10; i++) {   
			 
	            long temp;
	            String tempContent;
	            for (int j = 0; j < 10 - i - 1; j++) {   
	                if (b[j] > b[j + 1] && b[j]!=0&&b[j+1]!=0) {   
	                    //½»»»   
	                    temp = b[j];   
	                    b[j] = b[j + 1];   
	                    b[j + 1] = temp;  
	                    
	                    tempContent = a[j];
	                    a[j] = a[j+1];
	                    a[j+1] = tempContent;
	                }   
	            }   
	        }    
		
	}
	
	public long Election_timeout() {
		Random random = new Random();
		return 150 + random.nextInt(150);
	}

}
