package onlineticketing.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onlineticketing.domain.Film;
import onlineticketing.domain.Schedule;
import onlineticketing.service.FilmService;
import onlineticketing.service.ScheduleService;

/**
 * Servlet implementation class EditFilmControllerServlet
 */
@WebServlet("/EditFilmControllerServlet")
public class EditFilmControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ScheduleService scheduleService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditFilmControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		scheduleService = new ScheduleService();
		if (action.equals("basic")) {
			getBasicInfo(request, response);
		} else if (action.equals("schedule")) {
			getScheduleInfo(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equals("basic")) {
			postBasicInfo(request, response);
		} else if (action.equals("addSchedule")) {
			addSchedule(request, response);
		} else if (action.equals("deleteSchedule")) {
			deleteSchedule(request, response);
		} else if (action.equals("editSchedule")) {
			editSchedule(request, response);
		} else if (action.equals("saveSchedule")) {
			saveSchedule(request, response);
		}
	}
	
	private void getBasicInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		FilmService filmService = new FilmService();
		Film film = filmService.findFilmByFilmId(id);
		
		JSONObject filmJson=JSONObject.fromObject(film);
		
		response.getWriter().write(filmJson.toString());
		response.flushBuffer();
	}
	
	private void getScheduleInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now();
		Schedule schedule = new Schedule("0",1,startTime,endTime,100,0);
		ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		schedules.add(schedule);*/
		String id = request.getParameter("id");
		FilmService filmService = new FilmService();
		Film film = filmService.findFilmByFilmId(id);
		ArrayList<Schedule> schedules = film.getSchedule();
		
		JSONArray scheduleList=JSONArray.fromObject(schedules);
		
		response.getWriter().write(scheduleList.toString());
		response.flushBuffer();
	}
	
	private void postBasicInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filmId = request.getParameter("film_id");
		String filmTitle = request.getParameter("film_title");
		String filmDirector = request.getParameter("film_director");
		String filmMaincast = request.getParameter("film_maincast");
		String filmGenre = request.getParameter("film_genre");
		String filmDescription = request.getParameter("film_description");
		FilmService filmService = new FilmService();
		filmService.editFilm(filmId, filmTitle, filmDescription,
				filmDirector, filmMaincast, filmGenre);
		response.getWriter().write("success");
		response.flushBuffer();
	}
	
	private void addSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int filmId = Integer.parseInt(request.getParameter("film_id"));
		int screeningRoomId = Integer.parseInt(request.getParameter("screening_room"));
		float price = Float.parseFloat(request.getParameter("price"));
		int startDay = Integer.parseInt(request.getParameter("start_day"));
		int startMonth = Integer.parseInt(request.getParameter("start_month"));
		int startYear = Integer.parseInt(request.getParameter("start_year"));
		int startHour = Integer.parseInt(request.getParameter("start_hour"));
		int startMin = Integer.parseInt(request.getParameter("start_min"));
		LocalDateTime startTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMin);
		int endDay = Integer.parseInt(request.getParameter("end_day"));
		int endMonth = Integer.parseInt(request.getParameter("end_month"));
		int endYear = Integer.parseInt(request.getParameter("end_year"));
		int endHour = Integer.parseInt(request.getParameter("end_hour"));
		int endMin = Integer.parseInt(request.getParameter("end_min"));
		LocalDateTime endTime = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMin);
		
		/*
		System.out.println(filmId);
		System.out.println(screeningRoomId);
		System.out.println(price);
		System.out.println(startTime);
		System.out.println(endTime);
		*/
		String scheduleId = 
				scheduleService.createSchedule(filmId, screeningRoomId, startTime, endTime, price);
		
		response.getWriter().write(scheduleId);
		response.flushBuffer();
	}
	
	private void deleteSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String scheduleId = request.getParameter("id");
		
		System.out.println(scheduleId);
		scheduleService.deleteSchedule(scheduleId);
		
		
		response.getWriter().write("success");
		response.flushBuffer();
	}
	
	private void editSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String scheduleId = request.getParameter("schedule_id");
		float price = Float.parseFloat(request.getParameter("price"));
		int startDay = Integer.parseInt(request.getParameter("start_day"));
		int startMonth = Integer.parseInt(request.getParameter("start_month"));
		int startYear = Integer.parseInt(request.getParameter("start_year"));
		int startHour = Integer.parseInt(request.getParameter("start_hour"));
		int startMin = Integer.parseInt(request.getParameter("start_min"));
		LocalDateTime startTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMin);
		int endDay = Integer.parseInt(request.getParameter("end_day"));
		int endMonth = Integer.parseInt(request.getParameter("end_month"));
		int endYear = Integer.parseInt(request.getParameter("end_year"));
		int endHour = Integer.parseInt(request.getParameter("end_hour"));
		int endMin = Integer.parseInt(request.getParameter("end_min"));
		LocalDateTime endTime = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMin);
		
		/*
		System.out.println(scheduleId);
		System.out.println(price);
		System.out.println(startTime);
		System.out.println(endTime);*/
		scheduleService.updateSchedule(scheduleId, startTime, endTime, price);
		
		response.getWriter().write("success");
		response.flushBuffer();
	}

	private void saveSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		scheduleService.saveChanges();
		
		response.getWriter().write("success");
		response.flushBuffer();
	}
}
