package onlineticketing.domain;

import java.util.ArrayList;

public class ScreeningRoom extends DomainObject{

	private ArrayList<Seat> seats;
	
	public ScreeningRoom() {
		
	}
	
	public ScreeningRoom(int screeningRoomId, ArrayList<Seat> seats) {
		super();
		this.id = Integer.toString(screeningRoomId);
		this.seats = seats;
	}

	public int getScreeningRoomId() {
		return Integer.parseInt(id);
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

}
