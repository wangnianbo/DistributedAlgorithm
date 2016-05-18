
public class test extends Thread {

	public static void main(String[] args) {
		test test1 = new test();

		test1.start();
	}
	
	public void run(){
		while(true){
		System.out.println("2");
		
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
