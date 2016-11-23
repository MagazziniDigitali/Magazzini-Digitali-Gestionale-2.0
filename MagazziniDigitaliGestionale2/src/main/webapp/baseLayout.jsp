<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
    <!-- Gestione menu  -->
    <!-- link rel="stylesheet" href="http://code.jquery.com/ui/1.12.0-rc.2/themes/smoothness/jquery-ui.css"/ 
    <link rel="stylesheet" href="style/ui-lightness/jquery-ui.css"/>
 <style>
  .ui-menu {
    width: 200px;
  }
  </style>
    <link rel="stylesheet" href="style/Dysplay.css"/>
    -->

<link href="style/display/metro/blue/jtable.css" rel="stylesheet" type="text/css" />
<link href="style/display/jquery-ui-1.10.3.custom.css" rel="stylesheet"
	type="text/css" />

    <link rel="stylesheet" href="style/MenuBar.css"/>
    <link rel="stylesheet" href="style/BaseLayout.css"/>
    <link rel="stylesheet" href="style/Home.css"/>
    <link rel="stylesheet" href="style/HomeDetail.css"/>
    <link rel="stylesheet" href="style/Login.css"/>
    <link rel="stylesheet" href="style/Istituti.css"/>

    <script src="script/jquery-1.11.3.js"></script>
    <script src="script/1.12.0-rc.2/jquery-ui.min.js"></script>

<script src="script/display/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<script src="script/display/jquery.jtable.js" type="text/javascript"></script>

  </head>
  <body>
	<div id="header" class="header">
		<tiles:insertAttribute name="header" />
	</div>
	<div id="body" class="body">
		<tiles:insertAttribute name="body" />
	</div>
	<div id="footer" class="footer">
		<tiles:insertAttribute name="footer" />
	</div>
  </body>
</html>
