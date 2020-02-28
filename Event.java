package hw22;
import java.util.PriorityQueue;

public class Event implements Comparable<Event> {
    /**
     * Time that the event happens
     */
    private double time;
    
    private EventType eventType;
    
    public EventType gettype() {
    	return eventType;
    }
    
    public double getTime() {
    	return time;
    }

	/**
	 * @param time when the event starts
	 * @param eventType Monitor or Birth or Death
	 */
	public Event(double time, EventType eventType) {
		this.time = time;
		this.eventType = eventType;
		
	}

    /**
     * 
     * @param schedule contains all the scheduled future events
     * @param state of the simulation (request queue, logging info, etc.)
     * @param timestamp current time in the discrete simulation
     */
    public void function(PriorityQueue<Event> schedule, State state, double timestamp, int k) {	
    	schedule.remove(this);
		switch (eventType) {
		case DEATH:
	    	/**
		     * remove the record of the request from the data structure of requests in the system
		     * Also, collect and compute some statistics.
	    	 */
		    Request req = state.queue.remove();
		    req.setFinishServiceTime(timestamp);
	    
	    	state.totalRequestTime += req.getTq();
		    state.busyTime += req.getTs();
		    state.numCompletedRequests += 1;

		    System.out.printf("R%d DONE: %f\n", req.getId(), req.getFinishServiceTime());
		    /**
		     * look for another blocked event in the queue that wants to execute and schedule it's death.
		     * at this time the waiting request enters processing time.
	    	 */
		    if (state.queue.size() > 0){
				Request nextReq = state.queue.peek();
				nextReq.setStartServiceTime(timestamp);

				System.out.printf("R%d START: %f\n", nextReq.getId(), nextReq.getStartServiceTime());

				/* Schedule the next death event. */
				Event nextDeath = new Event(timestamp + getTimeOfNextDeath(), EventType.DEATH);
				schedule.add(nextDeath);
		    }
	    
	    	break;
	    
	    
		case BIRTH:
	    	/**
		     * add the newly born request to the data structure of requests in the system.
		     */
			Request request = new Request(state.getNextId());
			request.setArrivalTime(timestamp);
			System.out.printf("R%d ARR: %f\n", request.getId(), request.getArrivalTime());
			
			if(state.queue.size() == k) {
				System.out.printf("R%d DROP: %f\n", request.getId(), request.getArrivalTime());
				SimulatorK.dropcounter++;
				
			}
			else {
				state.queue.add(request);
			}
		    /**
		     * if the queue is empty then start executing directly there is no waiting time.
	    	 */
			    if(state.queue.size() == 1) {
					request.setStartServiceTime(timestamp);
					Event event = new Event(timestamp + getTimeOfNextDeath(), EventType.DEATH);
					schedule.add(event);
	
					System.out.printf("R%d START: %f\n", request.getId(), timestamp);
		  	  	}
				
		    /**
		     * schedule the next arrival
		     */
		    Event nextArrival = new Event(timestamp + getTimeOfNextBirth(), EventType.BIRTH);
		    schedule.add(nextArrival);
	    
		    break;
	    
			
		case MONITOR:
		    /**
		     * inspect the data structures describing the simulated system and log them
		     */
		    state.numMonitorEvents += 1;
		    state.totalQueueLen += state.queue.size();
	    
		    /**
		     * Schedule another monitor event following PASTA principle
		     */
		    Event nextMonitor = new Event(timestamp + getTimeOfNextMonitor(), EventType.MONITOR);
		    schedule.add(nextMonitor);
	    
		    break;
		}
    }
    
    
    
    
    
    public void function1(PriorityQueue<Event> schedule, State state, State rstate, double timestamp, int k) {	
    	schedule.remove(this);
		switch (eventType) {
		case DEATH:
			
	    	/**
		     * remove the record of the request from the data structure of requests in the system
		     * Also, collect and compute some statistics.
	    	 */
		    Request req = state.queue.remove();
		    req.setFinishServiceTime(timestamp);
	    
	    	state.totalRequestTime += req.getTq();
		    state.busyTime += req.getTs();
		    state.numCompletedRequests += 1;

		    System.out.printf("R%d DONE 0: %f\n", req.getId(), req.getFinishServiceTime());
		    /**
		     * look for another blocked event in the queue that wants to execute and schedule it's death.
		     * at this time the waiting request enters processing time.
	    	 */
		    if (state.queue.size() > 0){
				Request nextReq = state.queue.peek();
				nextReq.setStartServiceTime(timestamp);

				System.out.printf("R%d START 0: %f\n", nextReq.getId(), nextReq.getStartServiceTime());

				/* Schedule the next death event. */
				Event nextDeath = new Event(timestamp + getTimeOfNextDeath1(), EventType.DEATH);
				schedule.add(nextDeath);
		    }
	    
	    	break;
	    
	    
		case BIRTH:
	    	/**
		     * add the newly born request to the data structure of requests in the system.
		     */
			Request request = new Request(state.getNextId());
			request.setArrivalTime(timestamp);
			System.out.printf("R%d ARR: %f\n", request.getId(), request.getArrivalTime());
			
			if(state.queue.size() == k) {
				
				System.out.printf("R%d REDIR: %f\n", request.getId(), request.getArrivalTime());
				Simulator.dropcounter++;
				rstate.queue.add(request);
				
				 if(rstate.queue.size() == 1) {
						request.setStartServiceTime(timestamp);
						Event event2 = new Event(timestamp + getTimeOfNextDeath2(), EventType.REDIRECTED);
						schedule.add(event2);
		
						System.out.printf("R%d START 1: %f\n", request.getId(), timestamp);
				    }
				
			}
			else {
				state.queue.add(request);
				   if(state.queue.size() == 1) {
						request.setStartServiceTime(timestamp);
						Event event = new Event(timestamp + getTimeOfNextDeath1(), EventType.DEATH);
						schedule.add(event);
		
						System.out.printf("R%d START 0: %f\n", request.getId(), timestamp);
			  	  	}
			}
		    /**
		     * if the queue is empty then start executing directly there is no waiting time.
	    	 */
			 
			   
				
		    /**
		     * schedule the next arrival
		     */
		    Event nextArrival = new Event(timestamp + getTimeOfNextBirth1(), EventType.BIRTH);
		    schedule.add(nextArrival);
	    
		    break;
	    
			
		case MONITOR:
		    /**
		     * inspect the data structures describing the simulated system and log them
		     */
		    state.numMonitorEvents += 1;
		    state.totalQueueLen += state.queue.size();
		    rstate.numMonitorEvents ++;
		    rstate.totalQueueLen+= rstate.queue.size();
	    
		    /**
		     * Schedule another monitor event following PASTA principle
		     */
		    Event nextMonitor = new Event(timestamp + getTimeOfNextMonitor1(), EventType.MONITOR);
		    schedule.add(nextMonitor);
	    
		    break;
		    
		case REDIRECTED:
			
			Request req1 = rstate.queue.remove();
		    req1.setFinishServiceTime(timestamp);
	    
	    	rstate.totalRequestTime += req1.getTq();
		    rstate.busyTime += req1.getTs();

		    rstate.numCompletedRequests += 1;

		    System.out.printf("R%d DONE 1: %f\n", req1.getId(), req1.getFinishServiceTime());
		    
		    if(rstate.queue.size() > 0) {
				Request nextReq = rstate.queue.peek();
				nextReq.setStartServiceTime(timestamp);

				System.out.printf("R%d START 1: %f\n", nextReq.getId(), nextReq.getStartServiceTime());

				/* Schedule the next death event. */
				Event nextDeath2 = new Event(timestamp + getTimeOfNextDeath2(), EventType.REDIRECTED);
				schedule.add(nextDeath2);
		    }
		    
		    break;
			
		}
    }


    /* Make sure the events are sorted according to their happening time. */
    public int compareTo(Event e) {
    	double diff = time - e.getTime();
    	if (diff < 0) {
	    	return -1;
		} else if (diff > 0) {
		    return 1;
		} else {
		    return 0;
		}
    }

    /**
	 * exponential distribution
	 * used by {@link #getTimeOfNextBirth()}, {@link #getTimeOfNextDeath()} and {@link #getTimeOfNextMonitor()} 
	 * @param rate
	 * @return
	 */
	private static double exp(double rate) {
		return (- Math.log(1.0 - Math.random()) / rate);
	}
	
	/**
	 * 
	 * @return time for the next birth event
	 */
	public static double getTimeOfNextBirth() {
		return exp(SimulatorK.lambda);
	}
	public static double getTimeOfNextBirth1() {
		return exp(Simulator.lambda);
	}

	/**
	 * 
	 * @return time for the next death event
	 */
	public static double getTimeOfNextDeath() {
		return exp(1.0/SimulatorK.Ts);
	}
	public static double getTimeOfNextDeath1() {
		return exp(1.0/Simulator.Ts);
	}
	public static double getTimeOfNextDeath2() {
		return exp(1.0/Simulator.Ts2);
	}

	/**
	 * 
	 * @return time for the next monitor event
	 */
	public static double getTimeOfNextMonitor() {
		return exp(SimulatorK.lambda);
	}
	public static double getTimeOfNextMonitor1() {
		return exp(Simulator.lambda);
	}
}