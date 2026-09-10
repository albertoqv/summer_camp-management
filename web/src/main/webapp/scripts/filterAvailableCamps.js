const sinceInput = document.getElementById("since")
const untilInput = document.getElementById("until")
const educativeLevelInput = document.getElementById("educative_level")
const capacityInput = document.getElementById("capacity")


var listSinceFilterDeletions = []
var listUntilFilterDeletions = []
var listEducativeLevelFilterDeletions = []
var listCapacityFilterDeletions = []


sinceInput.addEventListener("change", () => {
	let date = new Date(sinceInput.value);
	let camps = getCamps()
	
	listSinceFilterDeletions = []
	camps.forEach(camp => {
		if(camp.startDate < date) {
			listSinceFilterDeletions.push(camp)
		}
	})	
	
	applyFilters()
})

untilInput.addEventListener("change", () => {
	let date = new Date(untilInput.value);
	let camps = getCamps()
	
	listUntilFilterDeletions = []
	camps.forEach(camp => {
		if(camp.startDate > date) {
			listUntilFilterDeletions.push(camp)
		}
	})	
	
	applyFilters()

})

educativeLevelInput.addEventListener("change", () => {
	let camps = getCamps()
	
	listEducativeLevelFilterDeletions = []
	camps.forEach(camp => {
		if(!camp.educativeLevel.includes(educativeLevelInput.value)) {
			listEducativeLevelFilterDeletions.push(camp)
		}
	})	
	
	applyFilters()
})

capacityInput.addEventListener("change", () => {
	let capacity = parseInt(capacityInput.value)
	let camps = getCamps()
	
	listCapacityFilterDeletions = []
	camps.forEach(camp => {
		if(camp.availableInscriptions < capacity) {
			listCapacityFilterDeletions.push(camp)
		}
	})	
	
	applyFilters()
})


function getCamps() {	
	const campDetailsDivs = document.querySelector('#list_of_available_camps').querySelectorAll('.camp_details_div');
	const camps = []
	
	campDetailsDivs.forEach(campDetailsDiv => {
		const stringDate = campDetailsDiv.querySelector(".camp_details_start_date").innerText
		const date = new Date(parseInt(stringDate.split("/")[2]), parseInt(stringDate.split("/")[1])-1, parseInt(stringDate.split("/")[0]));
		
		camps.push({
			div: campDetailsDiv,
			startDate: date,
			educativeLevel: campDetailsDiv.querySelector(".camp_details_educative_level").innerText,
			availableInscriptions: parseInt(campDetailsDiv.querySelector(".camp_details_available_inscriptions").innerText)
		})
	})
	
	return camps
}


function applyFilters() {
	let camps = getCamps()
	
	camps.forEach(camp => {
		camp.div.style.display = ""
	})
	
	listSinceFilterDeletions.forEach(camp => {
		camp.div.style.display = "none"
	})
	listUntilFilterDeletions.forEach(camp => {
		camp.div.style.display = "none"
	})
	listEducativeLevelFilterDeletions.forEach(camp => {
		camp.div.style.display = "none"
	})
	listCapacityFilterDeletions.forEach(camp => {
		camp.div.style.display = "none"
	})
}