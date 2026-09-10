package display.web.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.exceptions.activity.NotEnoughMonitorsException;
import business.exceptions.camp.NotTheSameLevelException;
import business.exceptions.dao.NotFoundException;
import business.managers.CampsManager;
import display.cli.menus.Common;
import display.web.Container;
import display.web.javabean.MessageBean;

/**
 * Servlet implementation class AssociateActivityToCamp
 */
@WebServlet("/associateActivityToCamp")
public class AssociateActivityToCamp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AssociateActivityToCamp() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/admin/associateActivityToCampView/associateActivityToCampForm.jsp");
		disp.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int campId = Integer.parseInt(request.getParameter("camp_id"));
		String activityName = request.getParameter("activity_name");
		CampsManager campsManager = Container.getInstance().getCampsManager();
		MessageBean messageBean = (MessageBean) request.getSession().getAttribute("messageBean");

		
		try {
			CampDTO selectedCamp = campsManager.getCamp(campId);
			ActivityDTO selectedActivity = campsManager.getActivity(activityName);
			campsManager.registerActivityInACamp(selectedCamp, selectedActivity);
			messageBean.setSuccess("Actividad asociada correctamente.");
		} catch (NotTheSameLevelException e) {
			messageBean.setError("La actividad y el campamento deben de ser del mismo nivel educativo. ");
		} catch (NotFoundException e) {
			messageBean.setError("La actividad o el campamento no existen. ");
		} catch (NotEnoughMonitorsException e) {
			messageBean.setError("La actividad seleccionada no tiene suficientes monitores. ");
		}
		
		messageBean.setUrl("/web/campsManager");
		request.getSession().setAttribute("messageBean", messageBean);
		response.sendRedirect("/web/campsManager");
	}

}
