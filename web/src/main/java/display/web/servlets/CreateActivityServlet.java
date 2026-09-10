package display.web.servlets;

import java.util.Arrays;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.enums.EducativeLevel;
import business.enums.TimeSlot;
import business.exceptions.activity.ActivityAlreadyExistException;
import business.exceptions.activity.ActivityNotFoundException;
import business.exceptions.activity.MaxMonitorsAddedException;
import business.exceptions.activity.MonitorIsNotInActivityException;
import business.exceptions.activity.NotEnoughMonitorsException;
import business.exceptions.camp.CampAlreadyRegisteredException;
import business.exceptions.camp.NotTheSameLevelException;
import business.exceptions.monitor.MonitorNotFoundException;
import business.managers.CampsManager;
import business.utilities.Utils;
import display.cli.menus.Common;
import display.web.Container;
import display.web.javabean.ActivityFormBean;
import display.web.javabean.CampFormBean;
import display.web.javabean.MessageBean;

@WebServlet("/createActivity")
public class CreateActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateActivityServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActivityFormBean activityFormBean = (ActivityFormBean) request.getSession().getAttribute("activityFormBean");
		
		if (activityFormBean == null) {
			activityFormBean = new ActivityFormBean();
			request.getSession().setAttribute("activityFormBean", activityFormBean);
		}
		
		switch (activityFormBean.getStage()) {
			case 1: {
				RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/admin/createActivityView/activityInfoFormView.jsp");
				disp.forward(request, response);
				break;
			}
			case 2: {
				RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/admin/createActivityView/activityAddNeededMonitorsFormView.jsp");
				disp.forward(request, response);
				break;
			}
			default:
				break;
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		ActivityFormBean activityFormBean = (ActivityFormBean) request.getSession().getAttribute("activityFormBean");
		
		if (activityFormBean == null) {
			activityFormBean = new ActivityFormBean();
			request.getSession().setAttribute("activityFormBean", activityFormBean);
		}
		
		switch (activityFormBean.getStage()) {
			case 1: {
				String activityName = request.getParameter("activity_name");
				
				String educativeLevelStr = request.getParameter("educative_level");
				EducativeLevel educativeLevel = EducativeLevel.valueOf(educativeLevelStr);
				
				String timeSlotStr = request.getParameter("time_slot");
				TimeSlot timeSlot = TimeSlot.valueOf(timeSlotStr);
				
				int capacity = Integer.parseInt(request.getParameter("capacity"));
				
				int neededMonitors = Integer.parseInt(request.getParameter("needed_monitors"));
				
				activityFormBean.setName(activityName);
				activityFormBean.setEducativeLevel(educativeLevelStr);
				activityFormBean.setTimeSlot(timeSlotStr);
				activityFormBean.setCapacity(capacity);
				activityFormBean.setNeededMonitors(neededMonitors);
				
				activityFormBean.setStage(2);
				request.getSession().setAttribute("activityFormBean", activityFormBean);
				doGet(request, response);
				break;
			}
			case 2: {
				List<String> listOfMonitors = Arrays.asList((request.getParameterValues("selected_monitors")));
				
				if (listOfMonitors.size() != activityFormBean.getNeededMonitors()) {
					request.getSession().setAttribute("createActivityError", "La actividad requiere " + activityFormBean.getNeededMonitors() + " monitores exactamente para ser creada.");
				}

				CampsManager campsManager = Container.getInstance().getCampsManager();
				
				ActivityDTO activity = new ActivityDTO(
						activityFormBean.getName(),
						EducativeLevel.valueOf(activityFormBean.getEducativeLevel()),
						TimeSlot.valueOf(activityFormBean.getTimeSlot()),
						activityFormBean.getCapacity(),
						activityFormBean.getNeededMonitors()
				);
				MessageBean messageBean = (MessageBean) request.getSession().getAttribute("messageBean");

				try {
					campsManager.registerActivity(activity);
					for(String monitorId : listOfMonitors) {
						campsManager.linkMonitorWithActivity(activity.getActivityName(), Integer.valueOf(monitorId));
					}
					messageBean.setSuccess("Actividad creada correctamente. ");
				} catch (ActivityAlreadyExistException e) {
					messageBean.setError("La actividad ya existe. ");
				} catch (ActivityNotFoundException e) {
					messageBean.setError("Error al asociar monitores. ");
				} catch (MonitorNotFoundException e) {
					campsManager.deleteActivity(activity);
					messageBean.setError("El monitor seleccionado no existe. ");
				} catch (MaxMonitorsAddedException e) {
					campsManager.deleteActivity(activity);
					messageBean.setError("La actividad requiere " + activityFormBean.getNeededMonitors() + " monitores exactamente para ser creada.");
				}
				
				messageBean.setUrl("/web/activitiesManager");
				request.getSession().setAttribute("messageBean", messageBean);
				
				request.getSession().setAttribute("activityFormBean", null);
				response.sendRedirect("/web/activitiesManager");
				break;
			}
			default:
				break;
		}
	}

}
