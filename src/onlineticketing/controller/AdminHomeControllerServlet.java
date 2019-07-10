package onlineticketing.controller;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import onlineticketing.domain.Film;
import onlineticketing.service.FilmService;

/**
 * Servlet implementation class AdminHomeControllerServlet
 */
@WebServlet("/AdminHomeControllerServlet")
public class AdminHomeControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminHomeControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmService filmService = new FilmService();
		List<Film> films = filmService.viewAllFilms();
		
		JSONArray listArray=JSONArray.fromObject(films);
		
		response.getWriter().write(listArray.toString());
		response.flushBuffer();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filmId = request.getParameter("id");
		FilmService filmService = new FilmService();
		filmService.deleteFilm(filmId);
		response.sendRedirect("AdminHomePage.html");
	}

}
