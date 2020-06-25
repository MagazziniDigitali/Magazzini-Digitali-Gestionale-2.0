/**
 * 
 */
package it.depositolegale.gestionale.verifica.preRegistrazione.elab;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.naming.NamingException;

import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.preRegistrazione.MDPreRegistrazioneBusiness;
import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.dao.MDPreRegistrazioneDAO;
import it.bncf.magazziniDigitali.database.dao.MDUtentiDAO;
import it.bncf.magazziniDigitali.database.dao.RegioniDAO;
import it.bncf.magazziniDigitali.database.entity.MDIstituzione;
import it.bncf.magazziniDigitali.database.entity.MDPreRegistrazione;
import it.bncf.magazziniDigitali.database.entity.MDUtenti;
import it.bncf.magazziniDigitali.utils.email.SendEmail;
import mx.randalf.hibernate.exception.HibernateUtilException;

/**
 * @author massi
 *
 */
public class ElabGoogle extends SendEmail{

	private int DATA_PRESCRIZIONE = 0; // Informazioni cronologiche

	private int UTENTE_EMAIL = 1; // Indirizzo email

	private int UTENTE_NOME = 2; // Nome

	private int UTENTE_COGNOME = 3; // Cognome

	private int UTENTE_CODICEFISCALE = 4; // Codice Fiscale

	private int UTENTE_NOTE = 5; // Note

	private int ISTITUZIONE_PIVA = 6; // Partita Iva / Codice Fiscale

	private int ISTITUZIONE_NOME = 7; // Denominazione

	private int ISTITUZIONE_INDIRIZZO = 8; // Indirizzo

	private int REGIONE = 9; // Regione di appartenenza

	private int ISTITUZIONE_URL = 10; // URL istituzionale

	private int ISTITUZIONE_TELEFONO = 11; // Telefono

	private int ISTITUZIONE_NOME_CONTATTO = 12; // Nome contatto

	private int ISTITUZIONE_NOTE = 13; // Note

	private int ALTA_RISOLUZIONE = 14; // Copia digitale ad alta risoluzione (da
										// progetti di digitalizzazione
										// istituzionali)

	private int TESI_DOTTORATO = 15; // Tesi di dottorato

	private int TESI_DOTTORATO_URL = 16; // Tesi di dottorato
	
	private int RIVISTE_APERTE = 17; // Riviste ad accesso Aperto

	private int RIVISTE_APERTE_URL = 18; // Riviste ad accesso Aperto

	private int RIVISTE_RISTRETTE = 19; // Riviste ad accesso Ristretto
	private int RIVISTE_RISTRETTE_URL = 20; // Riviste ad accesso Ristretto

	private int EBOOK_APERTE = 21; // Ebook accesso Aperto

	private int EBOOK_RISTRETTE = 22; // Ebook accesso Ristretto

	private int NBN = 23; // Ebook accesso Ristretto
	private int ALTRO = 24; // Altro (si prega di specificare)

	private int AUTORIZZAZIONE_TRAT = 25; // Autorizzazione al trattamento dei dati personali (in assenza dell'autorizzazione al trattamento non sarà possibile effettuare il deposito) 

	private List<Object> row = null;

	private Integer progressivo = 0;

	private String urlConfirm = null;

	/**
	 * 
	 */
	public ElabGoogle(List<Object> row, Integer progressivo, String login, String password, String urlConfirm) {
		super(login, password);
		this.row = row;
		this.progressivo = progressivo;
		this.urlConfirm = urlConfirm;
	}

	public static void main(String[] args){
		ElabGoogle elabGoogle = null;
		Vector<Object> row = new Vector<Object>();
		
		try {
			row.add("");
			row.add("massimiliano.randazzo@gmail.com");
			row.add("Massimiliano");
			row.add("Randazzo");
			elabGoogle = new ElabGoogle(row, null, "noreply@depositolegale.it", "ov6Uojiejai5", "http://md-gestionale.test.bncf.lan/MagazziniDigitaliGestionale/Home.action?checkId=");
			elabGoogle.sendMsg("AAAAAAAAAAAA", "massimiliano.randazzo@gmail.com", "Massimiliano", "Randazzo", "http://gestionaletest.depositolegale.it/");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void analize() throws HibernateException, HibernateUtilException {
		MDUtentiDAO mdUtentiDAO = null;
		MDUtenti mdUtenti = null;
		MDIstituzioneDAO mdIstituzioneDAO = null;
		MDIstituzione mdIstituzione = null;
		MDPreRegistrazioneDAO mdPreRegistrazioneDAO = null;
		MDPreRegistrazione mdPreRegistrazione = null;
		Timestamp dataPreIscrizione = null;
		GregorianCalendar gc = null;
		String[] st = null;
		String[] stData = null;
		String[] stOre = null;
		String id = null;

		try {
			st = ((String) row.get(DATA_PRESCRIZIONE)).split(" ");
			stData = st[0].split("/");
			stOre = st[1].split("\\.");
			// 21/04/2017 20.02.15
			gc = new GregorianCalendar(new Integer(stData[2]), new Integer(stData[1]) - 1, new Integer(stData[0]),
					new Integer(stOre[0]), new Integer(stOre[1]), new Integer(stOre[2]));

			dataPreIscrizione = new Timestamp(gc.getTimeInMillis());

			mdPreRegistrazioneDAO = new MDPreRegistrazioneDAO();
			mdPreRegistrazione = mdPreRegistrazioneDAO.findByProgressivo(progressivo, dataPreIscrizione);
			if (mdPreRegistrazione == null) {
				mdUtentiDAO = new MDUtentiDAO();

				mdUtenti = mdUtentiDAO.findByCodiceFiscale((String) row.get(UTENTE_CODICEFISCALE));

				if (mdUtenti != null
						&& mdUtenti.getCodiceFiscale().equals(((String) row.get(UTENTE_CODICEFISCALE)).toUpperCase())) {
					sendMsgError();
				} else {
					if (row.size()>AUTORIZZAZIONE_TRAT &&
							(row.get(AUTORIZZAZIONE_TRAT) != null && !((String) row.get(AUTORIZZAZIONE_TRAT)).trim().equals(""))) {
						mdIstituzioneDAO = new MDIstituzioneDAO();
						mdIstituzione = mdIstituzioneDAO.findByPIva((String) row.get(ISTITUZIONE_PIVA));
						id = registra(dataPreIscrizione, mdIstituzione);
						sendMsg(id, (String)row.get(UTENTE_EMAIL), (String)row.get(UTENTE_NOME), 
								(String)row.get(UTENTE_COGNOME), urlConfirm);
					} else {
						sendMsgErrorNoAutorzzata();
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new HibernateException(e.getMessage(), e);
		} catch (HibernateException e) {
			throw e;
		} catch (HibernateUtilException e) {
			throw e;
		} catch (MessagingException e) {
			throw new HibernateException(e.getMessage(), e);
		}
	}

	private void sendMsgErrorNoAutorzzata() throws MessagingException {
		sendMsg((String)row.get(UTENTE_EMAIL), "Magazzini Digitali - Esito registrazione", 
				corpoMsg("<br/>Gentile "+row.get(UTENTE_NOME)+" "+row.get(UTENTE_COGNOME)+",<br/>"
				+ "</br/>Ma &egrave; necessario dare l'autorizzazione al trattamenro dei dati personali per proseguire con la registrazione.<br/>"+
				"<br/>"));
	}

	public  void sendMsg(String id, String email, String nome, String cognome, String urlConfirm) throws MessagingException {
		sendMsg(email, "Magazzini Digitali - Esito registrazione", 
				corpoMsg("<br/>Gentile "+nome+" "+cognome+",<br/>"
				+ "</br/>benvenuto/a su <a href=\"http://www.depositolegale.it/\">depositolegale.it</a>.<br/>"
				+"Per completare la registrazione &egrave; necessario confermare il suo indirizzo di posta elettronica attraverso questo <a href=\""+urlConfirm+id+"\">link</a>.<br/>"+
				"<br/>"+
				"In caso di mancata conferma, trascorse 48 ore, la richiesta di registrazione verr&agrave; annullata.<br/>"+
				"<br/>"+
				"Per qualsiasi informazione pu&ograve; contattarci all'indirizzo info@depositolegale.it.<br/>" + 
				"<br/>"));
	}

	private void sendMsgError() throws MessagingException {
		sendMsg((String)row.get(UTENTE_EMAIL), "Magazzini Digitali - Esito registrazione", 
				corpoMsg("<br/>Gentile "+row.get(UTENTE_NOME)+" "+row.get(UTENTE_COGNOME)+",<br/>"
				+ "</br/>siamo spiacenti ma il Codice Fiscale operatore da lei utilizzato risulta gi&agrave; registrato.<br/>"+
				"Le chiedisamo cortesemente di verificare e, in caso di necessit&agrave; pu&ograve; contattarci all'indirizzo info@depositolegale.it.<br/>" + 
				"<br/>"));
	}

	private String registra(Timestamp dataPreIscrizione, MDIstituzione mdIstituzione)
			throws HibernateException, HibernateUtilException {
		MDPreRegistrazioneBusiness mdPreRegistrazioneBusiness = null;
		RegioniDAO regioniDAO = null;
		HashTable<String, Object> dati = null;
		String id = null;
		String istituzionePIva = null;
		DecimalFormat df11 = new DecimalFormat("00000000000"); 

		try {
			mdPreRegistrazioneBusiness = new MDPreRegistrazioneBusiness();

			dati = new HashTable<String, Object>();
			dati.put("progressivo", progressivo);
			dati.put("dataPreIscrizione", dataPreIscrizione);
			dati.put("utenteEmail", (String) row.get(UTENTE_EMAIL));
			dati.put("utenteNome", (String) row.get(UTENTE_NOME));
			dati.put("utenteCognome", (String) row.get(UTENTE_COGNOME));
			dati.put("utenteCodiceFiscale", (String) row.get(UTENTE_CODICEFISCALE));
			if (row.get(UTENTE_NOTE) != null && !((String) row.get(UTENTE_NOTE)).trim().equals("")) {
				dati.put("utenteNote", (String) row.get(UTENTE_NOTE));
			}
			
			istituzionePIva = (String) row.get(ISTITUZIONE_PIVA);
			if (istituzionePIva.length()==16){
				dati.put("istituzionePIva", istituzionePIva);
			} else {
				try{
					dati.put("istituzionePIva", ""+df11.format(new Long(istituzionePIva)));
				} catch (IllegalArgumentException e){
					dati.put("istituzionePIva", istituzionePIva);
				}
			}
			dati.put("istituzioneNome", (String) row.get(ISTITUZIONE_NOME));
			dati.put("istituzioneIndirizzo", (String) row.get(ISTITUZIONE_INDIRIZZO));

			regioniDAO = new RegioniDAO();
			dati.put("idRegione", regioniDAO.findByNomeRegione((String) row.get(REGIONE)));
			dati.put("istituzioneUrl", (String) row.get(ISTITUZIONE_URL));
			if (row.get(ISTITUZIONE_TELEFONO) != null && !((String) row.get(ISTITUZIONE_TELEFONO)).trim().equals("")) {
				dati.put("istituzioneTelefono", (String) row.get(ISTITUZIONE_TELEFONO));
			}
			if (row.get(ISTITUZIONE_NOME_CONTATTO) != null
					&& !((String) row.get(ISTITUZIONE_NOME_CONTATTO)).trim().equals("")) {
				dati.put("istituzioneNomeContatto", (String) row.get(ISTITUZIONE_NOME_CONTATTO));
			}
			if (row.get(ISTITUZIONE_NOTE) != null && !((String) row.get(ISTITUZIONE_NOTE)).trim().equals("")) {
				dati.put("istituzioneNote", (String) row.get(ISTITUZIONE_NOTE));
			}
			dati.put("altaRisoluzione", (((String) row.get(ALTA_RISOLUZIONE)).trim().equalsIgnoreCase("Si") ? 1 : 0));
			dati.put("tesiDottorato", (((String) row.get(TESI_DOTTORATO)).trim().equalsIgnoreCase("Si") ? 1 : 0));
			if (row.size()>RIVISTE_APERTE &&
					(row.get(RIVISTE_APERTE) != null && !((String) row.get(RIVISTE_APERTE)).trim().equals(""))) {
				dati.put("rivisteAperte", (String) row.get(RIVISTE_APERTE));
			}
			if (row.size()>RIVISTE_RISTRETTE &&
					(row.get(RIVISTE_RISTRETTE) != null && !((String) row.get(RIVISTE_RISTRETTE)).trim().equals(""))) {
				dati.put("rivisteRistrette", (String) row.get(RIVISTE_RISTRETTE));
			}
			if (row.size()>EBOOK_APERTE &&
					(row.get(EBOOK_APERTE) != null && !((String) row.get(EBOOK_APERTE)).trim().equals(""))) {
				dati.put("ebookAperte", (String) row.get(EBOOK_APERTE));
			}
			if (row.size()>EBOOK_RISTRETTE &&
					(row.get(EBOOK_RISTRETTE) != null && !((String) row.get(EBOOK_RISTRETTE)).trim().equals(""))) {
				dati.put("ebookRistrette", (String) row.get(EBOOK_RISTRETTE));
			}
			if (row.size()>ALTRO &&
					(row.get(ALTRO) != null && !((String) row.get(ALTRO)).trim().equals(""))) {
				dati.put("altro", (String) row.get(ALTRO));
			}
			dati.put("emailValidata", 0);
			if (mdIstituzione != null) {
				dati.put("idIstituzione", mdIstituzione);
			}

			id = mdPreRegistrazioneBusiness.save(dati);
		} catch (HibernateException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw new HibernateException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new HibernateException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new HibernateException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			throw new HibernateException(e.getMessage(), e);
		} catch (SecurityException e) {
			throw new HibernateException(e.getMessage(), e);
		} catch (HibernateUtilException e) {
			throw e;
		} catch (NamingException e) {
			throw new HibernateException(e.getMessage(), e);
		}

		return id;
	}

}
