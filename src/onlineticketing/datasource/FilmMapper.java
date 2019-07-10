package onlineticketing.datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;

import onlineticketing.domain.DomainObject;
import onlineticketing.domain.Film;
import onlineticketing.domain.Schedule;

public class FilmMapper implements DataMapper{
	
	/**
	 * create a new film in database
	 * @param obj  the film to be created
	 */
	public void insert(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof Film) : "obj is not a film object";
		Film film = (Film)obj;
		
		Film targetFilm = new Film();
		IdentityMap<Film> filmMap = IdentityMap.getInstance(targetFilm);
		
		String createFilmString = "INSERT INTO ONLINETICKETING.FILMS"
				+ "(FILMID, TITLE, DESCRIPTION, RUNNINGTIME, DIRECTOR,"
				+ "MAINCAST, GENRE) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement createStatement = DBConnection.prepare(createFilmString);
		
		try {
			createStatement.setInt(1, film.getFilmId());
			createStatement.setString(2, film.getTitle());
			createStatement.setString(3, film.getDescription());
			createStatement.setString(4, film.getRunningTime().toString());
			createStatement.setString(5, film.getDirector());
			createStatement.setString(6, film.getMainCast());
			createStatement.setString(7, film.getGenre());
			createStatement.execute();
			System.out.println(createStatement.toString());
			
			DBConnection.close(createStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		filmMap.put(film.getId(), film);
	}
	
	/**
	 * Find all films in the database
	 * @return a list all the film objects in the database
	 */
	public static ArrayList<Film> findAllFilms() {
		
		String findAllFilmsString = "SELECT * FROM ONLINETICKETING.FILMS";
		PreparedStatement findAllStatement = DBConnection.prepare(findAllFilmsString);
		ArrayList<Film> filmList = new ArrayList<Film>();
		
		Film targetFilm = new Film();
		IdentityMap<Film> filmMap = IdentityMap.getInstance(targetFilm);
		
		try {
			ResultSet rs = findAllStatement.executeQuery();
			
			while(rs.next()) {
				Film film = loadFilm(rs);
				
				targetFilm = filmMap.get(film.getId());
				if(targetFilm == null) {
					filmMap.put(film.getId(), film);
					filmList.add(film);
				} else {
					filmList.add(targetFilm);
				}
				
			}
			DBConnection.close(findAllStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return filmList;
	}
	
	
	/**
	 * Find a film by film ID
	 * @param filmId the input film ID
	 * @return the film which has the film ID passed in, returns null if 
	 * 		   there does not exist a user with the film ID passed in
	 */
	public static Film findFilmById(int filmId) {
		
		Film targetFilm = new Film();
		IdentityMap<Film> filmMap = IdentityMap.getInstance(targetFilm);
		targetFilm = filmMap.get(Integer.toString(filmId));
		if(targetFilm != null)
			return targetFilm;
		
		String findAllFilmsString = "SELECT * FROM ONLINETICKETING.FILMS "
				+ "WHERE FILMID = " + filmId;
		PreparedStatement findAllStatement = DBConnection.prepare(findAllFilmsString);
		Film film = null;
		
		try {
			ResultSet rs = findAllStatement.executeQuery();
			while(rs.next()) {
				film = loadFilm(rs);
			}
			DBConnection.close(findAllStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return film;
	}

	
	/**
	 * Update the basic information (title, description, running time, 
	 * director, main cast, genre) of a film
	 * @param obj  the film object with updated information
	 */
	@Override
	public void update(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof Film) : "obj is not a film object";
		Film film = (Film)obj;
		
		Film targetFilm = new Film();
		IdentityMap<Film> filmMap = IdentityMap.getInstance(targetFilm);
		
		String updateFilmInfoString = "UPDATE ONLINETICKETING.FILMS "
				+ "SET TITLE = ?, DESCRIPTION = ?, "
				+ "RUNNINGTIME = ?, DIRECTOR = ?, "
				+ "MAINCAST = ?, GENRE = ? WHERE FILMID ="
				+ film.getFilmId();
		PreparedStatement updateStatement = DBConnection.prepare(updateFilmInfoString);
		
		try {
			updateStatement.setString(1, film.getTitle());
			updateStatement.setString(2, film.getDescription());
			updateStatement.setString(3, film.getRunningTime().toString());
			updateStatement.setString(4, film.getDirector());
			updateStatement.setString(5, film.getMainCast());
			updateStatement.setString(6, film.getGenre());
			updateStatement.execute();
			System.out.println(updateStatement.toString());
			
			DBConnection.close(updateStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		filmMap.put(film.getId(), film);
	}

	@Override
	public void delete(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof Film) : "obj is not a film object";
		Film film = (Film)obj;
		
		Film targetFilm = new Film();
		IdentityMap<Film> filmMap = IdentityMap.getInstance(targetFilm);
		
		ArrayList<Schedule> scheduleList = ScheduleMapper.findScheduleByFilmId(film.getFilmId());
		ScheduleMapper scheduleMapper = new ScheduleMapper();
		for (Schedule schedule : scheduleList) {
			scheduleMapper.delete(schedule);
		}
		
		String deleteFilmString = "DELETE FROM ONLINETICKETING.FILMS "
				+ "WHERE FILMID = " + film.getFilmId();
		PreparedStatement deleteStatement = DBConnection.prepare(deleteFilmString);
		
		try {
			deleteStatement.execute();
			System.out.println(deleteStatement.toString());
			
			DBConnection.close(deleteStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		filmMap.put(film.getId(), null);
	}
	
	/**
	 * Generate a film object from a resultset
	 * @param rs the resultset of an film
	 * @return the film object generated by the resultset
	 */
	public static Film loadFilm(ResultSet rs) {
		
		Film film = null;
		
		try {
			
			int filmId = rs.getInt("FILMID");
			String title = rs.getString("TITLE");
			String description = rs.getString("DESCRIPTION");
			Duration runningTime = Duration.parse(rs.getString("RUNNINGTIME"));
			String director = rs.getString("DIRECTOR");
			String mainCast = rs.getString("MAINCAST");
			String genre =  rs.getString("GENRE");
			
			film = new Film(filmId, title, description, director, mainCast, genre, runningTime);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return film;
	}

}
