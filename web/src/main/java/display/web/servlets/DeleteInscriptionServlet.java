package display.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.managers.InscriptionManager;
import display.web.Container;

/**
 * Servlet implementation class DeleteInscriptionServlet
 */
@WebServlet("/deleteInscription")
public class DeleteInscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteInscriptionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/web");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inscriptionId = request.getParameter("inscriptionId");
		
		if (inscriptionId != null) {
			InscriptionManager inscriptionManager = Container.getInstance().getInscriptionManager();
			inscriptionManager.deleteInscription(inscriptionId);
		}
		
		doGet(request, response);
	}

}
