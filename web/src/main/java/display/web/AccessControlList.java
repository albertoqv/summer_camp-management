package display.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import display.web.javabean.CustomerBean;

public class AccessControlList {
	private Map<String, List<String>> acl;
	
	private static AccessControlList _instance;
	
	private AccessControlList () {
		acl = new HashMap<String, List<String>>();
		
		acl.put("/web/campsManager", Arrays.asList("ADMIN"));
		acl.put("/web/createCamp", Arrays.asList("ADMIN"));
		acl.put("/web/activitiesManager", Arrays.asList("ADMIN"));
		acl.put("/web/createActivity", Arrays.asList("ADMIN"));
		acl.put("/web/scripts/createActivityValidations.js", Arrays.asList("ADMIN"));
		acl.put("/web/monitorsManager", Arrays.asList("ADMIN"));
		acl.put("/web/createMonitor", Arrays.asList("ADMIN"));
		acl.put("/web/assistantsManager", Arrays.asList("ADMIN"));
		acl.put("/web/associateActivityToCamp", Arrays.asList("ADMIN"));
		acl.put("/web/associateSpecialMonitorToCamp", Arrays.asList("ADMIN"));

		
		
		acl.put("/web", Arrays.asList("ADMIN", "ASSISTANT"));
		acl.put("/web/", Arrays.asList("ADMIN", "ASSISTANT"));
		acl.put("/web/index.jsp", Arrays.asList("ADMIN", "ASSISTANT"));
		acl.put("/web/mvc/controller/modifyUserController.jsp", Arrays.asList("ADMIN", "ASSISTANT"));
		acl.put("/web/assets/logo.png", Arrays.asList("ADMIN", "ASSISTANT"));
		acl.put("/web/assets/styles/common.css", Arrays.asList("ADMIN", "ASSISTANT"));
		
		
		acl.put("/web/inscriptions", Arrays.asList("ASSISTANT"));
		acl.put("/web/availableCamps", Arrays.asList("ASSISTANT"));
		acl.put("/web/enroll", Arrays.asList("ASSISTANT"));
		acl.put("/web/deleteInscription", Arrays.asList("ASSISTANT"));
		acl.put("/web/scripts/filterAvailableCamps.js", Arrays.asList("ASSISTANT"));
		
		// acl.put("/web/anywhere", Arrays.asList("ADMIN"));
	}
	
	public static AccessControlList getInstance() {
		if(_instance == null) {
			_instance = new AccessControlList();
		}
		
		return _instance;
	}
	
	public boolean isAllowed(String route, CustomerBean user) {
		List<String> permisions = acl.get(route);
		
		return permisions != null ? permisions.contains(user.getRoleUser()) : false;
	}
}
