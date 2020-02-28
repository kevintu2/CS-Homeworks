
package hw22;
public class Request {
    /**
     * implement the Request class, which should include the necessary information to do statistics
     * over all the requests. e.g., to calculate Tq, one needs to record the arrival time, and finish time
     * of the request
     */
    private int requestId;
    private double arrivalTime;
    private double startServiceTime;
    private double finishServiceTime;
    
    public Request(int id) {
        requestId = id;
    }

    public int getId() {
        return requestId;
    }

    public void setArrivalTime(double time) {
        arrivalTime = time;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setStartServiceTime(double time) {
        startServiceTime = time;
    }

    public double getStartServiceTime() {
        return startServiceTime;
    }

    public void setFinishServiceTime(double time) {
        finishServiceTime = time;
    }

    public double getFinishServiceTime() {
        return finishServiceTime;
    }

    /**
     * Get total response time for this request
     */
    public double getTq() {
        return finishServiceTime - arrivalTime;
    }

    /**
     * Get service time for this request
     */
    public double getTs() {
        return finishServiceTime - startServiceTime;
    }
}
