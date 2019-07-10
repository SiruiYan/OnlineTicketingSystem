package onlineticketing.domain;

import java.time.Duration;
import java.util.ArrayList;

import onlineticketing.datasource.ScheduleMapper;

public class Film extends DomainObject{

	private String title;
	private String description;
	private String director;
	private String mainCast;
	private String genre;
	private Duration runningTime;
	private ArrayList<Schedule> schedule;
	private boolean scheduleLoaded;
	
	public Film() {
		
	}
	
	/**
	 * Lazy initialization
	 * @param filmId
	 * @param title
	 * @param description
	 * @param director
	 * @param mainCast
	 * @param genre
	 * @param runningTime
	 */
	public Film(int filmId, String title, String description, String director, String mainCast,
			String genre, Duration runningTime) {
		super();
		this.id = Integer.toString(filmId);
		this.title = title;
		this.description = description;
		this.director = director;
		this.mainCast = mainCast;
		this.genre = genre;
		this.runningTime = runningTime;
		this.scheduleLoaded = false;
	}

	public int getFilmId() {
		return Integer.parseInt(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getMainCast() {
		return mainCast;
	}

	public void setMainCast(String mainCast) {
		this.mainCast = mainCast;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Duration getRunningTime() {
		return runningTime;
	}
	
	
	/**
	 * Get the schedule of a film, load the schedules of the film from 
	 * the database if the schedule is not loaded
	 * @return  schedules of the film
	 */
	public ArrayList<Schedule> getSchedule() {
		if(!scheduleLoaded) {
			int filmId = Integer.parseInt(id);
			this.schedule = ScheduleMapper.findScheduleByFilmId(filmId);
			this.scheduleLoaded = true;
		}
		return schedule;
	}
	
	/**
	 * Reload the schedule of the film when the schedule of a film changes 
	 */
	public void reloadSchedule() {
		int filmId = Integer.parseInt(id);
		this.schedule = ScheduleMapper.findScheduleByFilmId(filmId);
		this.scheduleLoaded = true;
	}
	
}
