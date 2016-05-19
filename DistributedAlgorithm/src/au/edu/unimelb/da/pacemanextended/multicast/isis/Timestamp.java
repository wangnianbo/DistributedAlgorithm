package au.edu.unimelb.da.pacemanextended.multicast.isis;

/**
 * Logical lock for total ordering
 * Created by xy on 5/18/2016.
 */
public class Timestamp {
    private int tsp;

    //update logical lock and ensure increment
    public synchronized void increaseTsp(int msgts) throws InterruptedException {
        tsp = Math.max(msgts, tsp) + 1;
    }

    public synchronized void increaseTsp(){
        tsp++;
    }

    public Timestamp() {
        tsp = 0;
    }

    public Timestamp(int tsp) {
        this.tsp = tsp;
    }

    public int gettsp() {
        return tsp;
    }

    public void settsp(int tsp) {
        this.tsp = tsp;
    }
}
