package onlineticketing.domain;

import java.time.LocalDateTime;
import java.util.List;

import onlineticketing.datasource.ScreeningRoomMapper;
import onlineticketing.datasource.UnitOfWork;

public class Schedule extends DomainObject{

	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private ScreeningRoom screeningRoom;
	private float price;
	private int filmId;
	private List<Ticket> tickets;

	public Schedule(){
		
	}
	
	public Schedule(String scheduleId, LocalDateTime startTime, LocalDateTime endTime, float price) {
		super();
		this.id = scheduleId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
	}
	
	public Schedule(String scheduleId, int screeningRoomId, LocalDateTime startTime,
			LocalDateTime endTime,  float price, int filmId) {
		super();
		this.id = scheduleId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.filmId = filmId;
		this.screeningRoom = ScreeningRoomMapper.findScreeningRoomById(screeningRoomId);
	}
	
	public Schedule(String scheduleId, int screeningRoomId, int filmId, LocalDateTime startTime, 
			LocalDateTime endTime, float price) {
		super();
		this.id = scheduleId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.filmId = filmId;
		this.screeningRoom = ScreeningRoomMapper.findScreeningRoomById(screeningRoomId);
		UnitOfWork.getCurrent().registerNew(this);
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
		UnitOfWork.getCurrent().registerDirty(this);
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
		UnitOfWork.getCurrent().registerDirty(this);
	}

	public int getScreeningRoomId() {
		return screeningRoom.getScreeningRoomId();
	}

	public ScreeningRoom getScreeningRoom() {
		return screeningRoom;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
		UnitOfWork.getCurrent().registerDirty(this);
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public int getFilmId() {
		return filmId;
	}
	
}
