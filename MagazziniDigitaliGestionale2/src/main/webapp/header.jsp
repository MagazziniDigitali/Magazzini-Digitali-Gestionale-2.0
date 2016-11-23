<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<a class="lMagazziniDigitali ui-link" href="http://www.depositolegale.it/">
<img class="iMagazziniDigitali" src="https://upload.wikimedia.org/wikipedia/commons/3/35/Crystal_Clear_device_blockdevice.png" title="Magazzini Digitali" alt="Magazzini Digitali">
</a>
<fieldset class="titleIstituto" style="">
<s:if test="showMenu()">
<legend>
<s:property value="istituto" />
</legend>
<b><s:property value="utente" /></b>
</s:if>
</fieldset>
<fieldset class="urlIstituto" style="">
<legend>
<a href="http://www.bncf.firenze.sbn.it/">
<img class="urlIstituto" src="http://www.bncf.firenze.sbn.it/img/logo-bncf.jpg">
</a>
</legend>
</fieldset>
<s:if test="showMenu()">
  <nav>
  <div class="container">
    <ul>
      <li><a href="Home.action">Home</a></li>
      <s:if test="showMenuAdmin()">
	      <li><a href="#" onclick="return false;">Agents</a>
	        <ul>
	          <li><a href="Software.action">Software</a></li>
	          <li><a href="Istituti.action">Istituzioni</a></li>
	          <li><a href="Utenti.action">Utenti</a></li>
	          <li><a href="Nodi.action">Nodi</a></li>
	          <li><a href="CompositionLevel.action">Livello Composizione</a></li>
	        </ul>
	      </li>
	      <li><a href="Rigths.action">Rights</a></li>
	      <li><a href="Event.action">Event</a></li>
      </s:if>
      <li><a href="#">Begit</a></li>
    </ul>
  </div>
  </nav>
  <script>
  $('nav li').hover(
		  function() {
		    $('ul', this).stop().slideDown(200);
		  },
		  function() {
		    $('ul', this).stop().slideUp(200);
		  }
		);

  </script>
<!-- 
  <ul id="nav">
  <li><div>Menu</div>
	  <ul>
<s:if test="showMenuAdmin()">
	  <li><div>Amministratore</div>
	    <ul>
	      <li><div>Item 3-1</div></li>
	      <li><div>Item 3-2</div></li>
	      <li><div>Item 3-3</div></li>
	      <li><div>Item 3-4</div></li>
	      <li><div>Item 3-5</div></li>
	    </ul>
	  </li>
</s:if>
	  <li><div>Begit</div></li>
	  </ul>
  </li>
</ul>
 
<script>
$( "#nav" ).menu();
</script>
 -->
</s:if>
