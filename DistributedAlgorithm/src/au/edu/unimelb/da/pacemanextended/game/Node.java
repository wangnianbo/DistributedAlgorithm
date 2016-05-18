/*
 * represent the node
 */
package au.edu.unimelb.da.pacemanextended.game;

import java.util.Random;

public class Node {

	public int state = 2; // 2= follower 1= candidate 0= leader
	public int term = 0; // vote term
	public int voteCount = 0; // vote number
	public boolean signal = false; // true become candidate
	public boolean reset = false; // reset time out
	public int phase = 0; // phase of node
	public String[] a = new String[10]; // store the content
	public long[] b = new long[10]; // store the timestamp
	public String msg = ""; // every node output message
	public String backMsg = ""; // every node input message

	// initialize the node
	public Node() {
		for (int i = 0; i < 10; i++) {
			a[i] = "";
		}
		for (int i = 0; i < 10; i++) {
			b[i] = 0;
		}
	}

	// insert the content and timestamp into array
	public void insert(String content, String timestamp) {

		long time = Long.valueOf(timestamp);

		// insert
		for (int i = 0; i < 10; i++) {
			if (a[i].equals("")) {
				a[i] = content;
				b[i] = time;
				break;
			}
		}

		// bubblesort to sort array
		for (int i = 0; i < 10; i++) {

			long temp;
			String tempContent;
			for (int j = 0; j < 10 - i - 1; j++) {
				if (b[j] > b[j + 1] && b[j] != 0 && b[j + 1] != 0) {
					// exchange
					temp = b[j];
					b[j] = b[j + 1];
					b[j + 1] = temp;

					tempContent = a[j];
					a[j] = a[j + 1];
					a[j + 1] = tempContent;
				}
			}
		}

	}

	// generate the random time
	public long Election_timeout() {
		Random random = new Random();
		return 150 + random.nextInt(150);
	}

}
