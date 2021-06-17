<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 
<a class="lMagazziniDigitali ui-link"
	href="http://www.depositolegale.it/"> <img
	class="iMagazziniDigitali"
	src="https://upload.wikimedia.org/wikipedia/commons/3/35/Crystal_Clear_device_blockdevice.png"
	title="Magazzini Digitali" alt="Magazzini Digitali">
</a>
 -->
<fieldset class="titleIstituto" style="">
	<s:if test="showMenu()">
		<legend>
			<s:property value="istituto" />
		</legend>
		<b><s:property value="utente" /></b>
	</s:if>
</fieldset>
<!-- 
<fieldset class="urlIstituto" style="">
	<legend>
		<a href="http://www.librari.beniculturali.it/opencms/opencms/it/">
			<img class="urlIstituto" title="mibac" src="./image/logo_dgbid.jpg" alt="mibac" width="170" height="59">
		</a>
		<a href="http://www.bncf.firenze.sbn.it">
			<img class="urlIstituto" title="bncf" src="http://www.bncf.firenze.sbn.it/img/logo-bncf.jpg" alt="bncf" width="121" height="59">
		</a>
		<a href="http://www.bncrm.librari.beniculturali.it/">
			<img class="urlIstituto" title="bncr" src="./image/bncr.jpg" alt="bncr" width="88" height="59">
		</a>
		<a href="http://marciana.venezia.sbn.it/">
			<img class="urlIstituto" title="marciana" src="./image/marciana.gif" alt="marciana" width="63" height="59">
		</a>
	</legend>
</fieldset>
 -->
<s:if test="showMenu()">
  <nav>
    <div class="container">
      <ul class="nav site-nav">
        <li><a href="Home.action">Monitor</a></li>
        <s:if test="showMenuSuperAdmin()">
          <s:if test="showMenuAdmin()">
            <li class="flyout"><a href="#" onclick="return false;">Admin</a>
              <ul class="flyout-content nav stacked">
                <li class="flyout-alt"><a href="#" onclick="return false;">Agents</a>
                  <ul class="flyout-content nav stacked">
                    <s:if test="showMenuAdminMD()">
                      <li><a href="ConfigDefaults.action">Configurazione Software default</a></li>
                    </s:if>
                    <li><a href="Software.action">Software</a></li>
<!-- 
                    <s:if test="showMenuDepositanti()">
                      <li><a href="Istituti.action">Depositanti</a></li>
                    </s:if>
 -->
                    <s:if test="showMenuBiblioteche()">
                      <li><a href="Biblioteche.action">Biblioteche</a></li>
                    </s:if>
<!-- 
                    <li><a href="Utenti.action">Admin &amp; Operatori</a></li>
                    <li><a href="PreRegistrazione.action">Pre Registrazione</a></li>
 -->
                  </ul>
                </li>
                <s:if test="showMenuAdminMD()">
                  <li><a href="Rigths.action">Rights</a></li>
                  <li class="flyout-alt"><a href="#" onclick="return false;">Tabelle</a>
                    <ul class="flyout-content nav stacked">
                      <li><a href="Event.action">Event</a></li>
                      <li><a href="Nodi.action">Nodi</a></li>
                      <li><a href="CompositionLevel.action">Livello Composizione</a></li>
                      <li><a href="ModalitaAccesso.action">Modalit&agrave; Accesso</a></li>
                    </ul>
                  </li>
                </s:if>
              </ul>
            </li>
          </s:if>
<!-- 
          <li class="flyout"><a href="#" onclick="return false;">Tools</a>
            <ul class="flyout-content nav stacked">
              <s:if test="!showMenuAdminMD()">
                <s:if test="showMenuAltaRisoluzione()">
                  <li><a href="<s:property value="urlAltaRisoluzione" />">Alta Risoluzione</a></li>
                </s:if>
                <s:if test="showMenuBagit()">
                  <li><a href="http://md-front02.test.bncf.lan:5000/" target="_blank">Book Deposit</a></li>
                </s:if>
              </s:if>
              <li><a href="ChangePassword.action">Cambio Password</a></li>
            </ul>
          </li>
 -->
 <!-- Arge 15/06.2021 Logout temporaneo
          <li><a href="Home.action?logout=true">Logout</a></li>
 -->

        </s:if>
      </ul>
    </div>
  </nav>
</s:if>
