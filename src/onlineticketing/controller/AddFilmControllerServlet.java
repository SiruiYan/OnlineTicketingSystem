package onlineticketing.controller;

import java.io.IOException;
import java.sql.Blob;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineticketing.service.FilmService;

/**
 * Servlet implementation class AddFilmControllerServlet
 */
@WebServlet("/AddFilmControllerServlet")
public class AddFilmControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFilmControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filmTitle = request.getParameter("film_title");
		String filmDirector = request.getParameter("film_director");
		String filmMaincast = request.getParameter("film_maincast");
		String filmGenre = request.getParameter("film_genre");
		String filmDescription = request.getParameter("film_description");
		int filmRunningHour = Integer.parseInt(request.getParameter("film_runninghour"));
		int filmRunningMin = Integer.parseInt(request.getParameter("film_runningmin")); 
		FilmService filmService = new FilmService();
		filmService.createFilm(filmTitle, filmDescription, filmDirector, 
				filmMaincast, filmGenre, filmRunningHour, filmRunningMin);
		response.getWriter().write("success");
		response.flushBuffer();
	}

}
