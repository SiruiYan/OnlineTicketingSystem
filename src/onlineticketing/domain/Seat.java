package onlineticketing.domain;


public class Seat extends DomainObject{
	
	private int screeningRoomId;
	private String seatNumber;
	
	public Seat(int seatId, int screeningRoomId, String seatNumber) {
		super();
		this.id = Integer.toString(seatId);
		this.screeningRoomId = screeningRoomId;
		this.seatNumber = seatNumber;
	}

	public int getSeatId() {
		return Integer.parseInt(this.id);
	}

	public int getScreeningRoomId() {
		return screeningRoomId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

}
