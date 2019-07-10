package onlineticketing.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import onlineticketing.datasource.ScheduleMapper;
import onlineticketing.datasource.UnitOfWork;
import onlineticketing.domain.Film;
import onlineticketing.domain.Schedule;

public class ScheduleService {
	
	private ArrayList<Schedule> newSchedules;
	private ArrayList<Schedule> updatedSchedules;
	private ArrayList<String> deletedSchedules;
	
	//the hash map of all the new ids for film, 
	//the key is film id, value is the list of all new ids
	private Map<String, ArrayList<String>> newIdMap;
	
	public ScheduleService() {
		newSchedules = new ArrayList<Schedule>();
		updatedSchedules = new ArrayList<Schedule>();
		deletedSchedules = new ArrayList<String>();
		newIdMap = new HashMap<String, ArrayList<String>>();
	}
	
	/** 
	 * Add the information of a new schedule into the new schedules list, 
	 * the new schedules will be added to the database after all changes are saved
	 * @param filmId
	 * @param screeningRoomId
	 * @param startTime
	 * @param endTime
	 * @param price
	 * @return the schedule ID of the new schedule
	 */
	public String createSchedule(int filmId, int screeningRoomId, LocalDateTime startTime,
			LocalDateTime endTime, float price) {
		FilmService filmService = new FilmService();
		Film film = filmService.findFilmByFilmId(Integer.toString(filmId));
		String scheduleId = getNextScheduleId(film);
		Schedule schedule = new Schedule(scheduleId, screeningRoomId, startTime, endTime, price, filmId);
		newSchedules.add(schedule);
		ArrayList<String> newIdList = newIdMap.get(film.getId());
		if(newIdList == null)
			newIdList = new ArrayList<String>();
		newIdList.add(scheduleId);
		newIdMap.put(film.getId(), newIdList);
		return scheduleId;
	}
	
	/**
	 * Add the schedule with updated information into the updated schedules list,
	 * the updated schedules information will be updated to the database after all changes are saved
	 * @param scheduleId
	 * @param startTime
	 * @param endTime
	 * @param price
	 */
	public void updateSchedule(String scheduleId, LocalDateTime startTime,
			LocalDateTime endTime, float price) {
		Schedule schedule = new Schedule(scheduleId, startTime, endTime, price);
		updatedSchedules.add(schedule);
	}
	
	/**
	 * Add the schedule id of all the schedules should be deleted into the deleted schedules list,
	 * the deleted schedules will be deleted in the database after all changes are saved
	 * @param shceduleId
	 */
	public void deleteSchedule(String shceduleId) {
		deletedSchedules.add(shceduleId);
	}
	
	/**
	 * Create all the schedule in new schedule list, update all the schedule in the 
	 * updated schedule list, delete all the schedules with the id in deleted list
	 */
	public void saveChanges() {
		UnitOfWork.newCurrent();
	
		for (Schedule schedule : newSchedules) {
			new Schedule(schedule.getId(), schedule.getScreeningRoomId(), 
					schedule.getFilmId(), schedule.getStartTime(), 
					schedule.getEndTime(), schedule.getPrice());
		}
		UnitOfWork.getCurrent().commit();
		
		for (Schedule updatedSchedule : updatedSchedules) {
			Schedule schedule = ScheduleMapper.findScheduleByScheduleId(updatedSchedule.getId());
			schedule.setStartTime(updatedSchedule.getStartTime());
			schedule.setEndTime(updatedSchedule.getEndTime());
			schedule.setPrice(updatedSchedule.getPrice());
		}
		UnitOfWork.getCurrent().commit();
		
		for (String scheduleId : deletedSchedules) {
			System.out.println(scheduleId);
			Schedule schedule = ScheduleMapper.findScheduleByScheduleId(scheduleId);
			UnitOfWork.getCurrent().registerDeleted(schedule);
		}
		
		UnitOfWork.getCurrent().commit();
	}
	
	/**
	 * Get the next schedule id of a film
	 * @param film
	 * @return  the next schedule id
	 */
	public String getNextScheduleId(Film film) {
		int nextScheduleNum = 1;
		ArrayList<String> newIdList = newIdMap.get(film.getId());
		
		if(film.getSchedule() == null && (newIdList.isEmpty() || newIdList == null))
			nextScheduleNum = 1;
		else {
			ArrayList<Schedule> schedules = film.getSchedule();
			for (Schedule schedule : schedules) {
				String scheduleId = schedule.getId();
				int scheduleNum = getScheduleNum(scheduleId);
				if (scheduleNum >= nextScheduleNum)
					nextScheduleNum = scheduleNum + 1;
			}
			if (newIdList != null) {
				for (String newId : newIdList) {
					int scheduleNum = getScheduleNum(newId);
					if (scheduleNum >= nextScheduleNum)
						nextScheduleNum = scheduleNum + 1;
				}
			}
		}
			
		int filmId = film.getFilmId();
		String nextScheduleId = filmId + "S" + nextScheduleNum;
		
		return nextScheduleId;
	}
	
	/**
	 * Parse the schedule number from a schedule id
	 * @param scheduleId
	 * @return  the schedule number parsed from input schedule ID
	 */
	public int getScheduleNum(String scheduleId) {
		StringTokenizer tokenizer = new StringTokenizer(scheduleId, "S");
		tokenizer.nextToken();
		int scheduleNum = Integer.parseInt(tokenizer.nextToken());
		return scheduleNum;
	}
}
