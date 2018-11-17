function showSelectedView(element){
	
	var views=document.getElementById("views").children;
	var tabs=document.getElementById("tabs").getElementsByTagName('a');
	var numberOfTabs=tabs.length;
	var numberOfViews=views.length;
	if(numberOfTabs==numberOfViews){
		for(var i=0;i<numberOfViews;i++){
			if(views[i].classList.contains('tab-view')){
				views[i].classList.remove('tab-view');
				views[i].classList.add('tab-view-hide');
				break;
			}
		}
		for(var i=0;i<numberOfTabs;i++){
			if(tabs[i].classList.contains('tab-selected')){
				tabs[i].classList.remove('tab-selected');
				break;
			}
		}
		for(var i=0;i<numberOfTabs;i++){
			if(element.id===tabs[i].id){
				views[i].classList.remove('tab-view-hide');
				views[i].classList.add('tab-view');
				element.classList.add('tab-selected');
				break;
			}
		}
	}
	else{
		alert("Bad HTML: Tab or tab view missing!");
	}
}