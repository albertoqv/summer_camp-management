package display.web.servlets;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Date;

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
import business.exceptions.activity.MonitorIsNotInActivityException;
import business.exceptions.activity.NotEnoughMonitorsException;
import business.exceptions.camp.CampAlreadyRegisteredException;
import business.exceptions.camp.NotTheSameLevelException;
import business.managers.CampsManager;
import business.utilities.Utils;
import display.cli.menus.Common;
import display.web.Container;
import display.web.javabean.CampFormBean;
import display.web.javabean.MessageBean;

@WebServlet("/createCamp")
public class CreateCampServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateCampServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CampFormBean campFormBean = (CampFormBean) request.getSession().getAttribute("campFormBean");
		
		if (campFormBean == null) {
			campFormBean = new CampFormBean();
			request.getSession().setAttribute("campFormBean", campFormBean);
		}
		
		switch (campFormBean.getStage()) {
			case 1: {
				RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/admin/createCampView/campInfoFormView.jsp");
				disp.forward(request, response);
				break;
			}
			case 2: {
				RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/admin/createCampView/campAddActivityFormView.jsp");
				disp.forward(request, response);
				break;
			}
			case 3: {
				RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/admin/createCampView/campAddPrincipalMonitorFormView.jsp");
				disp.forward(request, response);
				break;
			}
			default:
				break;
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		CampFormBean campFormBean = (CampFormBean) request.getSession().getAttribute("campFormBean");
		
		if (campFormBean == null) {
			campFormBean = new CampFormBean();
		}
		
		switch (campFormBean.getStage()) {
			case 1: {
				String startDateStr = request.getParameter("start_date");
				Date startDate = Utils.parseDateBrowserFormat(startDateStr);
				
				String endDateStr = request.getParameter("end_date");
				Date endDate = Utils.parseDateBrowserFormat(endDateStr);
				
				
				String educativeLevelStr = request.getParameter("educative_level");
				EducativeLevel educativeLevel = EducativeLevel.valueOf(educativeLevelStr);
				
				int capacity = Integer.parseInt(request.getParameter("capacity"));
				
				campFormBean.setStart(Utils.getStringDate(startDate));
				campFormBean.setEnd(Utils.getStringDate(endDate));
				campFormBean.setEducativeLevel(educativeLevel.name());
				campFormBean.setCapacity(capacity);
				
				campFormBean.setStage(2);
				
				request.getSession().setAttribute("campFormBean", campFormBean);
				doGet(request, response);
				break;
			}
			case 2: {
				String activityName = request.getParameter("activity_name");
				campFormBean.setActivityName(activityName);
				
				campFormBean.setStage(3);
				request.getSession().setAttribute("campFormBean", campFormBean);
				doGet(request, response);
				break;
			}
			case 3: {
				int monitorId = Integer.parseInt(request.getParameter("monitor_id"));
				
				campFormBean.setMonitorId(monitorId);
				
				CampsManager campsManager = Container.getInstance().getCampsManager();
				int idCamp = campsManager.listOfCamps().size() + 1;
				CampDTO camp = new CampDTO(
						idCamp, 
						Utils.parseDate(campFormBean.getStart()),
						Utils.parseDate(campFormBean.getEnd()),
						EducativeLevel.valueOf(campFormBean.getEducativeLevel()),
						campFormBean.getCapacity()
				);
				
				ActivityDTO selectedActivity = campsManager.getActivity(campFormBean.getActivityName());
				MonitorDTO selectedMonitor = campsManager.getMonitor(campFormBean.getMonitorId());
				
				MessageBean messageBean = (MessageBean) request.getSession().getAttribute("messageBean");

				try {
					campsManager.registerCamp(camp);
					campsManager.registerActivityInACamp(camp, selectedActivity);									
					campsManager.setPrincipalMonitor(camp, selectedMonitor);
					messageBean.setSuccess("Campamento creada correctamente. ");
				} catch (NotTheSameLevelException e) {
					campsManager.deleteCamp(camp);
					messageBean.setError("La actividad y el campamento deben de ser del mismo nivel educativo. ");
				} catch (MonitorIsNotInActivityException e) {
					campsManager.deleteCamp(camp);
					messageBean.setError("El monitor principal no pertenece a ninguna actividad del campamento. ");
				} catch (CampAlreadyRegisteredException e) {
					messageBean.setError("El campamento ya existe. ");
				} catch (NotEnoughMonitorsException e) {
					campsManager.deleteCamp(camp);
					messageBean.setError("La actividad no tiene suficientes monitores para llevarse a cabo. ");
				}
				
				messageBean.setUrl("/web/campsManager");
				request.getSession().setAttribute("messageBean", messageBean);
				
				
				request.getSession().setAttribute("campFormBean", null);
				response.sendRedirect("/web/campsManager");
				break;
			}
			default:
				break;
		}
	}

}
