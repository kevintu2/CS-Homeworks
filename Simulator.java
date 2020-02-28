package hw22;
public class Simulator {
    /* Average arrival rate of requests (requests per millisecond). */
    static double lambda;
    static int k;
    /* Average service time at the server (milliseconds). */
    static double Ts;
    static double Ts2;
    public static double dropcounter = 0;

    public static void simulate(double time) {
        Controller.runSimulation1(time, lambda, Ts, k, Ts2);
    }
    
    public static void main(String args[]) {
        /* Simulation time (milliseconds). */
        double time = Double.parseDouble(args[0]);

        /* Average arrival rate of requests (requests per millisecond). */
        lambda = Double.parseDouble(args[1]);

        /* Average service time at the server (milliseconds). */
        Ts = Double.parseDouble(args[2]);
        
        k = Integer.parseInt(args[3]);
        
        Ts2 = Double.parseDouble(args[4]);

    	simulate(time);
    }
}