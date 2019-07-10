package onlineticketing.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import onlineticketing.domain.Film;
import onlineticketing.domain.Schedule;
import onlineticketing.service.FilmService;

/**
 * Servlet implementation class ViewFilmControllerServlet
 */
@WebServlet("/ViewFilmControllerServlet")
public class ViewFilmControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewFilmControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		System.out.println(request.getParameter("id"));
		Duration runningTime  = Duration.ofHours(1);
		runningTime = runningTime.plusMinutes(30);
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now();
		Schedule schedule = new Schedule("0",1,startTime,endTime,100,0);
		ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		schedules.add(schedule);
		Film film = new Film(0,"A","B","C","D","E",runningTime,schedules);
		*/
		
		String id = request.getParameter("id");
		FilmService filmService = new FilmService();
		Film film = filmService.findFilmByFilmId(id);
		
		JSONObject listArray=JSONObject.fromObject(film);
		
		response.getWriter().write(listArray.toString());
		response.flushBuffer();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
