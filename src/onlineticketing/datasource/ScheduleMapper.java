package onlineticketing.datasource;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import onlineticketing.domain.DomainObject;
import onlineticketing.domain.Film;
import onlineticketing.domain.Schedule;
import onlineticketing.domain.Seat;
import onlineticketing.domain.Ticket;

public class ScheduleMapper implements DataMapper{

	/** 
	 * Create a new schedule in database
	 * @param obj  the schedule object to be created
	 */
	@Override
	public void insert(DomainObject obj) {
		assert !(obj instanceof Schedule) : "obj is not a schedule object";
		Schedule schedule = (Schedule)obj;
		
		Schedule targetSchedule = new Schedule();
		IdentityMap<Schedule> scheduleMap = IdentityMap.getInstance(targetSchedule);
		
		String createScheduleString = "INSERT INTO ONLINETICKETING.SCHEDULES"
				+ "(SCHEDULEID, SCREENINGROOMID, STARTTIME, ENDTIME, PRICE, FILMID) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement createStatement = DBConnection.prepare(createScheduleString);
		
		try {
			createStatement.setString(1, schedule.getId());
			createStatement.setInt(2, schedule.getScreeningRoomId());
			createStatement.setObject(3, schedule.getStartTime());
			createStatement.setObject(4, schedule.getEndTime());
			createStatement.setFloat(5, schedule.getPrice());
			createStatement.setInt(6, schedule.getFilmId());
			createStatement.execute();
			System.out.println(createStatement.toString());
			
			DBConnection.close(createStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scheduleMap.put(schedule.getId(), schedule);
		Film film = FilmMapper.findFilmById(schedule.getFilmId());
		film.reloadSchedule();
		
		//insert tickets for a schedule
		ArrayList<Seat> seats = schedule.getScreeningRoom().getSeats();
		String scheduleId = schedule.getId();
		TicketMapper ticketMapper = new TicketMapper();
		for(Seat seat : seats) {
			String ticketId = scheduleId + "T" + seat.getSeatId();
			Ticket ticket = new Ticket(ticketId, seat.getSeatId(), scheduleId);
			ticketMapper.insert(ticket);
		}
	}
	
	
	/**
	 * Update the schedule information
	 * @param schedule  the schedule object with updated information
	 */
	@Override
	public void update(DomainObject obj) {
		assert !(obj instanceof Schedule) : "obj is not a schedule object";
		Schedule schedule = (Schedule)obj;
		
		Schedule targetSchedule = new Schedule();
		IdentityMap<Schedule> scheduleMap = IdentityMap.getInstance(targetSchedule);
		
		String updateScheduleString = "UPDATE ONLINETICKETING.SCHEDULES SET"
				+ " STARTTIME = ?, ENDTIME = ?, PRICE = ?"
				+ " WHERE SCHEDULEID = '" + schedule.getId() + "'";
		PreparedStatement updateStatement = DBConnection.prepare(updateScheduleString);
		
		try {
			updateStatement.setObject(1, schedule.getStartTime());
			updateStatement.setObject(2, schedule.getEndTime());
			updateStatement.setFloat(3, schedule.getPrice());
			updateStatement.execute();
			System.out.println(updateStatement.toString());
			
			DBConnection.close(updateStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scheduleMap.put(schedule.getId(), schedule);
	}
	
	
	/**
	 * Delete a schedule in database
	 * @param obj  the schedule to be deleted
	 */
	@Override
	public void delete(DomainObject obj) {
		assert !(obj instanceof Schedule) : "obj is not a schedule object";
		Schedule schedule = (Schedule)obj;
		
		Schedule targetSchedule = new Schedule();
		IdentityMap<Schedule> scheduleMap = IdentityMap.getInstance(targetSchedule);
		
		//delete tickets for a schedule
		ArrayList<Ticket> ticketList = TicketMapper.findTicketsByScheduleId(schedule.getId());
		TicketMapper ticketMapper = new TicketMapper();
		for(Ticket ticket : ticketList) {
			ticketMapper.delete(ticket);
		}
		
		String deleteScheduleString = "DELETE FROM ONLINETICKETING.SCHEDULES "
				+ "WHERE SCHEDULEID = '" + schedule.getId() + "'";
		PreparedStatement deleteStatement = DBConnection.prepare(deleteScheduleString);
		
		try {
			deleteStatement.execute();
			System.out.println(deleteStatement.toString());
			
			DBConnection.close(deleteStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scheduleMap.put(schedule.getId(), null);
		Film film = FilmMapper.findFilmById(schedule.getFilmId());
		film.reloadSchedule();
	}
	
	/**
	 * Find all the schedules for a film
	 * @param filmId  the input film ID
	 * @return a list of all schedules for the film with input film ID
	 */
	public static ArrayList<Schedule> findScheduleByFilmId(int filmId){
		
		Schedule targetSchedule = new Schedule();
		IdentityMap<Schedule> scheduleMap = IdentityMap.getInstance(targetSchedule);
		
		String findSchedulesString = "SELECT * FROM ONLINETICKETING.SCHEDULES "
				+ "WHERE FILMID = " + filmId;
		PreparedStatement findStatement = DBConnection.prepare(findSchedulesString);
		ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
		
		try {
			ResultSet rs = findStatement.executeQuery();
			
			while(rs.next()) {
				Schedule schedule = loadSchedule(rs);
				
				targetSchedule = scheduleMap.get(schedule.getId());
				if (targetSchedule == null) {
					scheduleMap.put(schedule.getId(), schedule);
					scheduleList.add(schedule);
				} else {
					scheduleList.add(targetSchedule);
				}
					
			}
			DBConnection.close(findStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return scheduleList;
	}
	
	/**
	 * Find a schedule by schedule ID
	 * @param scheduleId  the input schedule ID
	 * @return a schedule object with the input schedule ID
	 */
	public static Schedule findScheduleByScheduleId(String scheduleId){
		
		Schedule targetSchedule = new Schedule();
		IdentityMap<Schedule> scheduleMap = IdentityMap.getInstance(targetSchedule);
		targetSchedule = scheduleMap.get(scheduleId);
		if (targetSchedule != null)
			return targetSchedule;
		
		
		String findSchedulesString = "SELECT * FROM ONLINETICKETING.TICKETS "
				+ "WHERE SCHEDULEID = '" + scheduleId + "'";
		PreparedStatement findStatement = DBConnection.prepare(findSchedulesString);
		Schedule schedule = null;
		
		try {
			ResultSet rs = findStatement.executeQuery();
			while(rs.next()) {
				schedule = loadSchedule(rs);
			}
			
			DBConnection.close(findStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return schedule;
	}
	
	/**
	 * Generate a schedule object from a resultset
	 * @param rs  the resultset of a schedule
	 * @return the schedule object generated by the resultset
	 */
	public static Schedule loadSchedule(ResultSet rs) {
		Schedule schedule = null;
		
		try {
			String scheduleId = rs.getString("SCHEDULEID");
			int screeningRoomId = rs.getInt("SCREENINGROOMID");
			LocalDateTime startTime = convertToLocalDateTimeViaSqlTimestamp(rs.getDate("STARTTIME"));
			LocalDateTime endTime = convertToLocalDateTimeViaSqlTimestamp(rs.getDate("ENDTIME"));
			float price = rs.getFloat("PRICE");
			int filmId = rs.getInt("FILMID");
			
			schedule = new Schedule(scheduleId, screeningRoomId, startTime, endTime, price, filmId);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return schedule;
	}
	
	/**
	 * Convert Date type to LocalDateTime type
	 * @param dateToConvert  the date to be converted
	 * @return  the converted LocalDateTime
	 */
	public static LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
	    return new java.sql.Timestamp(
	      dateToConvert.getTime()).toLocalDateTime();
	}
	
}
