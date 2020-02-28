package hw22;
import java.util.PriorityQueue;

public class Controller {
    
    /**
     * initialize the schedule with a birth event and a monitor event
     * @return a schedule with two events
     */
    public static PriorityQueue<Event> initSchedule() {
		PriorityQueue<Event> schedule = new PriorityQueue<Event>();

		schedule.add(new Event(Event.getTimeOfNextBirth(), EventType.BIRTH));
		schedule.add(new Event(Event.getTimeOfNextMonitor(), EventType.MONITOR));
	
		return schedule;
    }
    public static PriorityQueue<Event> initSchedule1() {
		PriorityQueue<Event> schedule = new PriorityQueue<Event>();

		schedule.add(new Event(Event.getTimeOfNextBirth1(), EventType.BIRTH));
		schedule.add(new Event(Event.getTimeOfNextMonitor1(), EventType.MONITOR));
		return schedule;
    }
    
    public static void runSimulation(double simulationTime, double lambda, double Ts, int k) {
    	/**
		 * declare the data structures that hold the state of the system
		 */
		State state = new State();
		PriorityQueue<Event> schedule = initSchedule();
		double time = 0, maxTime = simulationTime;
		while(time < maxTime) {
			Event event = schedule.remove();
			time = event.getTime();
			event.function(schedule, state, time, k);
		}
	
		/**
		 * output the statistics over the simulated system
		 */
		System.out.printf("UTIL: %f\n", state.busyTime / simulationTime);
		System.out.printf("QLEN: %f\n", state.totalQueueLen / state.numMonitorEvents);
		System.out.printf("TRESP: %f\n", state.totalRequestTime / state.numCompletedRequests);
		System.out.println("DROPPED: " +  SimulatorK.dropcounter);
    }
    public static void runSimulation1(double simulationTime, double lambda, double Ts, int k, double Ts2) {
    	/**
		 * declare the data structures that hold the state of the system
		 */
    	State rstate = new State();
		State state = new State();
		PriorityQueue<Event> schedule = initSchedule1();
		double time = 0, maxTime = simulationTime;
		while(time < maxTime) {
			Event event = schedule.remove();
			time = event.getTime();
			event.function1(schedule, state, rstate, time, k);
		}
	
		/**
		 * output the statistics over the simulated system
		 */
		System.out.printf("UTIL 0: %f\n", state.busyTime / simulationTime);
		System.out.printf("UTIL 1: %f\n", rstate.busyTime / simulationTime);
		System.out.printf("QLEN 0: %f\n", state.totalQueueLen / state.numMonitorEvents);
		System.out.printf("QLEN 1: %f\n", rstate.totalQueueLen / rstate.numMonitorEvents);
		System.out.printf("TRESP: %f\n", (rstate.totalRequestTime + state.totalRequestTime) / (state.numCompletedRequests + rstate.numCompletedRequests));
		System.out.println("REDIRECTED: " +  Simulator.dropcounter);
    }
}