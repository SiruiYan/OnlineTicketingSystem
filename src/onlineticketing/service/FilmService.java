package onlineticketing.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;

import onlineticketing.datasource.FilmMapper;
import onlineticketing.datasource.IdentityMap;
import onlineticketing.domain.Film;
import onlineticketing.onlineticketing.Params;

public class FilmService {

	/**
	 * Create a new film
	 * @param title  the title of the film
	 * @param description  the description of a film
	 * @param director  the director of the film
	 * @param mainCast the main cast of the film
	 * @param genre  the genre of the film
	 * @param hour  the hour of running time
	 * @param minute  the minute of running time
	 */
	public void createFilm(String title, String description, String director,
			String mainCast, String genre, int hour, int minute)
	{
		Duration runningTime = Duration.ofHours(hour);
		runningTime = runningTime.plusMinutes(minute);
		
		Film targetFilm = new Film();
		IdentityMap<Film> filmMap = IdentityMap.getInstance(targetFilm);
		
		//generate a random int from 0 to 999999999 as the id for the new film
		//generate the new id until it does not already exist as a film id
		Random random = new Random();
		int filmId = random.nextInt(Params.MAX_FILM_ID);
		
		while(filmMap.get(Integer.toString(filmId))!=null)
			filmId = random.nextInt(Params.MAX_FILM_ID);
		
		Film film = new Film(filmId, title, description, director, mainCast, 
				genre, runningTime);
		FilmMapper filmMapper = new FilmMapper();
		filmMapper.insert(film);
	}
	
	/**
	 * View all films in the database
	 * @return  the list of all the films
	 */
	public ArrayList<Film> viewAllFilms() {
		ArrayList<Film> filmList = FilmMapper.findAllFilms();
		return filmList;
	}
	
	/**
	 * Find all the information of a film (title, description, running time, 
	 * director, main cast, genre, schedules)
	 * @param id  the input film id
	 * @return  the film object with input film id
	 */
	public Film findFilmByFilmId(String id) {
		Film film = FilmMapper.findFilmById(Integer.parseInt(id));
		if(film == null)
			System.err.println("Cannot find the film with film ID " + id);
		else
			film.getSchedule();
		
		return film;
	}
	
	/**
	 * Edit the basic information of a film (title, description, 
	 * running time, director, main cast, genre)
	 * @param filmId  the film id of the film to be edited
	 * @param title  the new title
	 * @param description  the new description
	 * @param director  the new director
	 * @param mainCast  the new main cast
	 * @param genre  the new genre
	 */
	public void editFilm(String filmId, String title, String description, 
			String director, String mainCast, String genre) {
		
		Film film = FilmMapper.findFilmById(Integer.parseInt(filmId));
		FilmMapper filmMapper = new FilmMapper();
		
		if (film == null)
			System.err.println("The film being editted does not exist");
		else
			film.setTitle(title);
			film.setDescription(description);
			film.setDirector(director);
			film.setDirector(director);
			film.setMainCast(mainCast);
			film.setGenre(genre);
			filmMapper.update(film);

	}
	
	/**
	 * Delete a film
	 * @param filmId  the film id of the film to be deleted
	 */
	public void deleteFilm(String filmId) {
		
		FilmMapper filmMapper = new FilmMapper();
		Film film = FilmMapper.findFilmById(Integer.parseInt(filmId));
		if (film == null)
			System.err.println("The film being deleted does not exist");
		else {
			filmMapper.delete(film);
		}
	}
	
}
