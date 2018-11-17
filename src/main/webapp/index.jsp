<html>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>A Web Page</title>
	<link rel="stylesheet" href="styles/index-style.css">

	<body>
		<div class="page">
		  <div id="tabs" class="tabs"> <!-- Container for current page tabs -->
		      <a id="tab1" href="javascript:void(0);" class="tab-selected" onclick="showSelectedView(this);">
		      	Administration
		      </a><!--

			--><a id="tab2" href="javascript:void(0);" onclick="showSelectedView(this);">
		      	Formular
		      </a>
		  </div>
		  <div id="views">
		  	<div id="tab1-view" class="tab-view">
		  		<form>
		  			<div class="group">
						<input style="width: 100%;" id="formularName" name="formularName" 
						placeholder="Enter the name for this formular" type="text">
					</div>
		  		</form>
			</div>
			<div id="tab2-view" class="tab-view-hide">
				View Text2
			</div>
		</div>
		<script src="scripts/js/index-script.js"></script>
	</body>
</html>
