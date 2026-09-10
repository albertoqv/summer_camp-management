package display.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.dtos.AssistantDTO;
import business.dtos.InscriptionDTO;
import business.enums.InscriptionType;
import business.exceptions.inscription.AfterLateTimeException;
import business.exceptions.inscription.AfterStartTimeException;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.exceptions.inscription.MaxAssistantExcededException;
import business.exceptions.inscription.NeedToAddAnSpecialMonitorException;
import business.exceptions.inscription.WrongEducativeLevelException;
import business.managers.InscriptionManager;
import business.utilities.Utils;
import display.web.Container;
import display.web.javabean.ActivityFormBean;
import display.web.javabean.CustomerBean;
import display.web.javabean.EnrollFormBean;
import display.web.javabean.MessageBean;

/**
 * Servlet implementation class CampsServlet
 */
@WebServlet("/enroll")
public class EnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EnrollServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnrollFormBean enrollFormBean = (EnrollFormBean) request.getSession().getAttribute("enrollFormBean");
		
		if (enrollFormBean == null) {
			enrollFormBean = new EnrollFormBean();
			request.getSession().setAttribute("enrollFormBean", enrollFormBean);
		}
		
		switch (enrollFormBean.getStage()) {
			case 1: {
				RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/assistant/availableCampsView.jsp");
				disp.forward(request, response);
				break;
			}
			case 2: {
				RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/assistant/enroll/availableCampsViewConfirmLatelyInscription.jsp");
				disp.forward(request, response);
				break;
			}
			default:
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnrollFormBean enrollFormBean = (EnrollFormBean) request.getSession().getAttribute("enrollFormBean");
		
		if (enrollFormBean == null) {
			enrollFormBean = new EnrollFormBean();
			request.getSession().setAttribute("enrollFormBean", enrollFormBean);
		}
		
		InscriptionType type = null;
		int campId = -1;
		
		switch (enrollFormBean.getStage()) {
			case 1: {
				String typeStr = request.getParameter("type");
				type = InscriptionType.valueOf(typeStr);
				
				String campIdStr = request.getParameter("camp_id");
				campId = Integer.valueOf(campIdStr);
				
				enrollFormBean.setCampId(campId);
				enrollFormBean.setType(type);
				
				request.getSession().setAttribute("enrollFormBean", enrollFormBean);
				
				InscriptionManager inscriptionManager = Container.getInstance().getInscriptionManager();
				Date currentDate = Utils.getCurrentDate();
				

				if (!inscriptionManager.willBeEarly(campId, currentDate)) {
					enrollFormBean.setStage(2);
					doGet(request, response);
					return;
				}
				break;
			}
			case 2: {
				String wasConfirmedStr = request.getParameter("wasConfirmed");
				boolean wasConfirmed = wasConfirmedStr != null && wasConfirmedStr.equals("true");
				
				if (!wasConfirmed) {
					enrollFormBean.setStage(1);
					doGet(request, response);
					return;
				}

				type = enrollFormBean.getType();
				campId = enrollFormBean.getCampId();

				enrollFormBean.setStage(1);
				request.getSession().setAttribute("enrollFormBean", enrollFormBean);

				break;
			}
			default:
				break;
		}

		enroll(campId, type, request);
		
		doGet(request, response);
	}
	
	private void enroll(int campId, InscriptionType type, HttpServletRequest request) {
		MessageBean messageBean = (MessageBean) request.getSession().getAttribute("messageBean");

		if (campId == -1 || type == null) {
			messageBean.setError("Faltan parametros para realizar la inscripción. ");
			return;
		}
		
		InscriptionManager inscriptionManager = Container.getInstance().getInscriptionManager();
		
		CustomerBean customerBean = (CustomerBean) request.getSession().getAttribute("customerBean");
		Date currentDate = Utils.getCurrentDate();
		
		
		AssistantDTO assistant = Container.getInstance().getUserManager().getAssistantInfo(customerBean.getEmailUser());
		
		InscriptionDTO inscription;

		
		try {
			inscription = inscriptionManager.enroll(assistant.getId(), campId, currentDate, type == InscriptionType.PARTIAL, assistant.isRequireSpecialAttention());
			messageBean.setSuccess("Inscripción realizada correctamente. Precio: " + inscription.getPrice() + " euros. ");
		} catch (WrongEducativeLevelException e) {
			messageBean.setError("El nivel educativo de este campamento no es adecuado para el asistente. ");
		} catch (NeedToAddAnSpecialMonitorException e) {
			messageBean.setError("Es necesario añadir un monitor de atención especial antes de inscribir al asistente. ");
		} catch (AssistantAlreadyEnrolledException e) {
			messageBean.setError("Este asistente ya está inscrito en el campamento. ");
		} catch (MaxAssistantExcededException e) {
			messageBean.setError("No hay plazas libres para inscribir al asistente a todas las actividades de la modalidad seleccionada del campamento. ");
		} catch (AfterStartTimeException e) {
			messageBean.setError("El campamento ya ha comenzado. ");
		} catch (AfterLateTimeException e) {
			messageBean.setError("No es posible inscribirse a un campamento si no se hace con al menos 48h de antelación. ");
		}
		
		messageBean.setUrl("/web/activitiesManager");
		request.getSession().setAttribute("messageBean", messageBean);
	}

}
