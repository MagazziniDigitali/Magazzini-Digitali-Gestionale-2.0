/**
 * 
 */
package it.depositolegale.gestionale.verifica.preRegistrazione;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import it.depositolegale.gestionale.verifica.preRegistrazione.elab.ElabGoogle;
import mx.randalf.hibernate.exception.HibernateUtilException;

/**
 * @author massi
 *
 */
public class ReadGoogle {

	private Logger log = Logger.getLogger(ReadGoogle.class);

	/** Application name. */
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/sheets.googleapis.com-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/sheets.googleapis.com-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	private String login = null;

	private String password = null;

	private String urlConfirm = null;

	public ReadGoogle(String login, String password, String urlConfirm) {
		this.login = login;
		this.password = password;
		this.urlConfirm = urlConfirm;
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private Credential authorize(File clientSecret) throws FileNotFoundException, IOException {
		// Load client secrets.
		InputStream in = null;
		GoogleClientSecrets clientSecrets = null;
		GoogleAuthorizationCodeFlow flow = null;
		Credential credential = null;

		try {
			in = new FileInputStream(clientSecret);
			// in =ReadGoogle.class.getResourceAsStream("/client_secret.json");
			clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

			// Build flow and trigger user authorization request.
			flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
					.setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
			log.debug("\n"+"Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return credential;
	}

	/**
	 * Build and return an authorized Sheets API client service.
	 * 
	 * @return an authorized Sheets API client service
	 * @throws IOException
	 */
	private Sheets getSheetsService(File clientSecret) throws IOException {
		Credential credential = authorize(clientSecret);
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
				.build();
	}

	public static void main(String[] args) {
		ReadGoogle readGoogle = null;

		try {
			if (args.length==3) {
				readGoogle = new ReadGoogle("noreply@depositolegale.it", "ov6Uojiejai5",
						"http://md-gestionale.test.bncf.lan/MagazziniDigitaliGestionale/Home.action?checkId=");
				System.out.println("Ricomincia da: " + readGoogle.analizza(2, new File(args[0]), args[1], args[2]));
			} else {
				System.out.println("E' necessario indicare i seguenti parametri");
				System.out.println("1) Nome del file clientSecret");
				System.out.println("2) La chiave della pagina spreadsheetId");
				System.out.println("3) Nome della pagina");
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (HibernateUtilException e) {
			e.printStackTrace();
		}
	}

	public int analizza(int nRow, File clientSecret, String spreadsheetId, String page) throws
	// IOException,
	HibernateException, HibernateUtilException {
		Sheets service = null;
		List<Object> row = null;
		ElabGoogle elabGoogle = null;

		try {
			// Apro la connessione con il documento.
			service = getSheetsService(clientSecret);

			while (true) {
				row = readRow(service, nRow, spreadsheetId, page);
				if (row == null) {
					break;
				} else {
					elabGoogle = new ElabGoogle(row, nRow, login, password, urlConfirm);
					elabGoogle.analize();
					nRow++;
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			// throw e;
		} catch (HibernateException e) {
			throw e;
		} catch (HibernateUtilException e) {
			throw e;
		}
		return nRow;
	}

	/**
	 * Metodo utilizzato per leggere la riga richiesta nel foglio
	 * 
	 * @param service
	 * @param nRow
	 * @return
	 * @throws IOException
	 */
	private List<Object> readRow(Sheets service, int nRow, String spreadsheetId, String page) throws IOException {

		String range = null;
		ValueRange response = null;
		List<List<Object>> rows = null;
		List<Object> row = null;
		try {

			// chiave identificativa del documento da analizzare, che possiamo ricavare
			// dall'url del documento:
			// https://docs.google.com/spreadsheets/d/1tlNppLw7686Z5ybbgTM7aO0c6xGrFhQZP-_1X_ICZA4/edit
			// https://docs.google.com/spreadsheets/d/1j_kfgIqVGg1Luvb7nXuBs-KOveF1wmSBImoSG5mSLf4/edit#gid=1121719036
			// spreadsheetId = "1tlNppLw7686Z5ybbgTM7aO0c6xGrFhQZP-_1X_ICZA4";
			// spreadsheetId = "1j_kfgIqVGg1Luvb7nXuBs-KOveF1wmSBImoSG5mSLf4";
			// Coll: https://docs.google.com/spreadsheets/d/1eY65s2A38GBC9-HpgPbaBNtWtI-eaG3jOWL9n88_ZP4/edit#gid=1554018670

			// chiave utilizzata per il calcolo delle informazioni
			// range = "'Risposte del modulo 1'!A"+nRow+":V"+nRow;
			range = (page == null || page.trim().equals("") ? "" : page + "!") + "A" + nRow + ":Z" + nRow;

			response = service.spreadsheets().values().get(spreadsheetId, range).execute();
			rows = response.getValues();
			if (rows != null && rows.size() > 0) {
				row = rows.get(0);
			}
		} catch (IOException e) {
			throw e;
		}
		return row;
	}

}
