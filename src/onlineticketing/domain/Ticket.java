package onlineticketing.domain;

public class Ticket extends DomainObject {
	
	private boolean isSold;
	private int seatId;
	private int screeningRoomId;
	private String seatNumber;
	private int orderId;
	private String scheduleId;
	
	public Ticket() {
		
	}
	
	public Ticket(String ticketId, int seatId, String scheduleId) {
		super();
		this.id = ticketId;
		this.isSold = false;
		this.seatId = seatId;
		this.scheduleId = scheduleId;
	}
	
	public Ticket(String ticketId, boolean isSold, int seatId, int orderId, String scheduleId) {
		super();
		this.id = ticketId;
		this.isSold = isSold;
		this.seatId = seatId;
		this.orderId = orderId;
		this.scheduleId = scheduleId;
	}
	
	public int isSold() {
		return (isSold ? 1 : 0 );
	}

	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}

	public int getSeatId() {
		return seatId;
	}

	public int getScreeningRoomId() {
		return screeningRoomId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getScheduleId() {
		return scheduleId;
	}
	
}
