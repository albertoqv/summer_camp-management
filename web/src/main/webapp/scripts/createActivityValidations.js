function validateThereAreEnoughtSelectedMonitors(neededMonitorsStr, event) {
	const neededMonitors = 1 * neededMonitorsStr
	const monitorsSelect = document.getElementById("monitors_select")
	
	
	let n = 0
	for(let children of monitorsSelect.children) {
		if (children.selected) {
			n++
		}
	}
	
	if(n !== neededMonitors){
		alert("Debe seleccionar " + neededMonitors + " monitores");
    	event.preventDefault();
	}
	
}