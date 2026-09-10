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
import business.dtos.MonitorDTO;
import business.exceptions.activity.NotEnoughMonitorsException;
import business.exceptions.camp.IsNotAnSpecialEducator;
import business.exceptions.camp.NotTheSameLevelException;
import business.exceptions.camp.SpecialMonitorAlreadyRegisterException;
import business.exceptions.dao.NotFoundException;
import business.managers.CampsManager;
import display.web.Container;
import display.web.javabean.MessageBean;

/**
 * Servlet implementation class AssociateSpecialMonitorToCampServlet
 */
@WebServlet("/associateSpecialMonitorToCamp")
public class AssociateSpecialMonitorToCampServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AssociateSpecialMonitorToCampServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/admin/associateSpecialMonitorToCampView/associateSpecialMonitorToCampForm.jsp");
		disp.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int campId = Integer.parseInt(request.getParameter("camp_id"));
		int monitorId = Integer.parseInt(request.getParameter("monitor_id"));
		
		CampsManager campsManager = Container.getInstance().getCampsManager();
		MessageBean messageBean = (MessageBean) request.getSession().getAttribute("messageBean");

		
		try {
			CampDTO selectedCamp = campsManager.getCamp(campId);
			MonitorDTO selectedMonitor = campsManager.getMonitor(monitorId);
			campsManager.setSpecialMonitor(selectedCamp, selectedMonitor);
			messageBean.setSuccess("Monitor especial asociado correctamente.");
		} catch (SpecialMonitorAlreadyRegisterException e) {
			messageBean.setError("El monitor especial no puede pertenecer a ninguna actividad. ");
		} catch (NotFoundException e) {
			messageBean.setError("El monitor o el campamento no existen. ");
		} catch (IsNotAnSpecialEducator e) {
			messageBean.setError("El monitor seleccionado no es un educador especial. ");
		}
		
		messageBean.setUrl("/web/campsManager");
		request.getSession().setAttribute("messageBean", messageBean);
		response.sendRedirect("/web/campsManager");
	}

}
