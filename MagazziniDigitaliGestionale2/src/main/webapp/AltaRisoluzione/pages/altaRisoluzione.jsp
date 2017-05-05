<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<fieldset class="altaRisoluzione" style="">
	<legend><s:text name="altaRisoluzione.title" /></legend>

	<p>
		Per poter pubblicare il materiale su Magazzini Digitali sono state predisposte alcune Api e uno spazio di Storaging temporaneo, per poter facilitare il lavore del depositante 
		abbiamo predisposto una applicazione basato su queste regole trasmette il materiale da uno ho pi&ugrave; client del depositante verso il nostro sistema di archiviazione.<br/>
		<br/>
		Per prima cosa se non &egrave; gi&agrave; stato fatto in precedenza il depositante deve fornire allo staff di Magazzini Digitali l'indirizzo IP Pubblico della postazione 
		che tasferir&agrave; il materiale inviando una email all'indirizzo <a href="mailto:info@depositolegale.it">info@depositolegale.it</a><br/>
		<br/>
		Il materiale da pubblicare saranno delle digitalizzazione in formato <b>TIFF non compresso</b> o <b>Jpeg2000</b> corredati da <b>mag</b> o <b>mets</b> e volendo il materiale 
		ricampionato a media e bassa risoluzione il tutto sar&agrave; all'interno di un file <b>Tar.gz</b> posto in una cartella, in una cartella separata dovr&agrave; essere predisposto 
		uno o pi&ugrave; file <b>txt</b> che contiene il nome del file "tar.gz" senza le estensioni.<br/>
		<br/>
		Es.<br/>
		Cartella <b>/volume1/tgz</b> contiene 2 file <b>AAAAAAA.tar.gz</b> e <b>BBBBBBBBB.tar.gz</b><br/>
		Cartella <b>/volume1/coda</b> contiene 1 file <b>0000000.txt</b> con dentro scritto<br/>
		<pre>
		  AAAAAAA
		  BBBBBBBBB
		</pre>
		Dal seguente <a href="/donwload/MD-ClientUpload.tgz">link</a> &egrave; possibile scaricare il programma.<br/>
		Per poter funzionare è necessario installare:<br/>
		<ul>
		  <li>Java 8 o superiori</li>
		  <li>rsync</li>
		</ul>
		<br/>
		Una volta scaricato il file e decompresso &egrave; necessario modificare i seguenti files:<br/>
		<ul>
		  <li>File <b>esegui.sh</b> in cui è necessario valorizzare la variabile di ambiente <b>export MDCLIENT_PWD=</b> indicando la password che verr&agrave;
		utilizzata per il collegamento (password che corrisponde ha quella assegnata al momento della registrazione, nel caso in cui &egrave; stata persa &egrave; possibile richiederla 
		all'indirizzo <a href="mailto:info@depositolegale.it">info@depositolegale.it</a>), nel caso in cui questa variabile non viene valorizzata il programma alla partenza ogni volta 
		richieder&agrave; di indicare la password.</li>
		  <li>Nellla cartella <b>conf</b> &egrave; necessario creare un file <b>clientMD.properties.xml</b> come segue:
		  <pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"&gt;
&lt;properties version="1.0"&gt;
  &lt;comment&gt;File di configurazione del progetto MD Versione 2.x.x&lt;/comment&gt;

  &lt;!-- URL da contattare per poter fare l'autenticazione del Software --&gt;
  &lt;entry key="software.URLAuthentication"&gt;<s:property value="urlAuthentication"/>&lt;/entry&gt;

  &lt;!-- Login del Software --&gt;
  &lt;entry key="software.TD.login"&gt;<s:property escapeHtml="false" value="softwareLogin"/>&lt;/entry&gt;

  &lt;!-- Percorsi locali in cui vengono registrati la lista dei file da elaborare (Obbligatori almeno 1 percorso)--&gt;
  &lt;entry key="pathExcel(0)"&gt;<b>[Path in cui sono stati archiviati i files TXT]</b>&lt;/entry&gt;

  &lt;!-- Percorsi locali in cui verranno registrati gli oggetti Digitali da inviare al Magazzino digitale (Obbligatori almeno 1 persorso)--&gt;
  &lt;entry key="pathDescriptati(0)"&gt;<b>[Path in cui sono stati archiviati i file TAR.GZ]</b>&lt;/entry&gt;

  &lt;!-- Percorso e nome del file rsync per il trasferimento dei dati sul server --&gt;
  &lt;entry key="md.sendRsync.path"&gt;/usr/bin/rsync&lt;/entry&gt;

  &lt;!-- Parametro utilizzato per indicare se cancellare da locale il file originale una volta trasferito sul server --&gt;
  &lt;entry key="md.deleteLocalFile"&gt;false&lt;/entry&gt;

&lt;/properties&gt;
		  </pre>
		</ul>
		
	</p>
</fieldset>