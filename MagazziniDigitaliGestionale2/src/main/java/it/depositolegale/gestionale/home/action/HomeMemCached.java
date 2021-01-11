/**
 * 
 */
package it.depositolegale.gestionale.home.action;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.bncf.magazziniDigitali.database.dao.MDUtentiDAO;
import it.bncf.magazziniDigitali.database.entity.MDUtenti;
import it.depositolegale.gestionale.user.action.LoginAction;
import it.depositolegale.www.errorMsg.ErrorType_type;
import it.depositolegale.www.utenti.Utenti;
import mx.randalf.configuration.Configuration;
import mx.randalf.configuration.exception.ConfigurationException;
import mx.randalf.hibernate.exception.HibernateUtilException;
import net.spy.memcached.MemcachedClient;

/**
 * @author massi
 *
 */
public class HomeMemCached extends LoginAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2920572470620898490L;

	/**
	 * 
	 */
	public HomeMemCached() {
	}

	protected String readMemCached(String sid, String result)
			throws ConfigurationException, MDConfigurationException, IOException, HibernateUtilException {
		MemcachedClient mcc = null;
		InetSocketAddress isa = null;
		String hostname = null;
		Integer port = 0;
		String sid_value = null;
//		String nome = null;
//		String cognome = null;
//		String istituto = null;
//		String ruolo = null;
		String[] st = null;
		String[] st2 = null;
		MDUtentiDAO mdUtentiDAO = null;
		List<MDUtenti> mdUtentis = null;
		it.depositolegale.utenti.MDUtenti mdUtenti = new it.depositolegale.utenti.MDUtenti();
		HttpServletRequest request = null;
		String ipClient = null;
		Utenti utenti = null;

		try {
			hostname = Configuration.getValueDefault("memcached.host", "127.0.0.1");
			port = mdConfiguration.getConfigInteger("memcached.port");
			isa = new InetSocketAddress(hostname, (port == null ? 11211 : port));
			mcc = new MemcachedClient(isa);

			sid_value = (String) mcc.get(sid);
			System.out.println("sid_value: " + sid_value);
			if (sid_value != null) {
				st = sid_value.split(";");
				for (String value : st) {
					st2 = value.split("\\|");
					switch (st2[0]) {
//					case "name":
//						nome = readValue(st[1]);
//						break;
//
//					case "surname":
//						cognome = readValue(st[1]);
//						break;

					case "username":
						username = readValue(st2[1]);
						break;
//
//					case "istituzione":
//						istituto = readValue(st[1]);
//						break;
//
//					case "role":
//						ruolo = readValue(st[1]);
//						break;

					default:
						break;
					}
				}
			}

			// name|s:7:"Gestore";surname|s:5:"Lumsa";username|s:5:"lumsa";istituzione|s:25:"Università
			// di Roma LUMSA";role|s:17:"admin_istituzione";

			// name|s:7:"Gestore"; Nome
			// surname|s:5:"Lumsa"; Cognome
			// username|s:5:"lumsa"; Login
			// istituzione|s:25:"Università di Roma LUMSA"; Istituto
			// role|s:17:"admin_istituzione"; Ruolo

			if (username != null) {
				mdUtentiDAO = new MDUtentiDAO();
				mdUtentis = mdUtentiDAO.find(username, null, null, null);

				if (mdUtentis != null && mdUtentis.size() == 1 && mdUtentis.get(0).getLogin().equals(username)) {
					password = mdUtentis.get(0).getPassword();
					request = ServletActionContext.getRequest();

					// is client behind something?
					ipClient = request.getHeader("X-FORWARDED-FOR");
					if (ipClient == null) {
						ipClient = request.getRemoteAddr();
					}
					utenti = mdUtenti.checkUtenti(LoginAction.mdConfiguration, ipClient, username, password, false);
					if (utenti != null) {
						if (utenti.getErrorMsg() != null && utenti.getErrorMsg().length > 0) {
							for (int x = 0; x < utenti.getErrorMsg().length; x++) {
								if (utenti.getErrorMsg()[x].getErrorType().equals(ErrorType_type.LOGINERROR)
										|| utenti.getErrorMsg()[x].getErrorType()
												.equals(ErrorType_type.PASSWORDERROR)) {
									addFieldError("username", getText("username.required"));
									addFieldError("password", getText("password.required"));
								} else {
									addActionError(utenti.getErrorMsg()[x].getMsgError());
								}
							}
							result = LOGIN;
						} else {
							initSession(utenti, sid);
							readSession(ActionContext.getContext().getSession());

							result = HOME;
						}
					} else {
						addActionError(getText("authentication.faild"));
						result = LOGIN;
					}
				} else {
					addActionError(getText("authentication.faild"));
					result = LOGIN;
				}
			} else {
				result = LOGIN;
			}
		} catch (ConfigurationException e) {
			throw e;
		} catch (MDConfigurationException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (HibernateException e) {
			throw e;
		} catch (HibernateUtilException e) {
			throw e;
		} finally {
			if (mcc != null) {
				mcc.shutdown();
			}
		}

		return result;
	}

	private String readValue(String value) {
		String[] st = null;
		String result = null;

		st = value.split(":");
		if (st.length == 3) {
			result = st[2];
			result = result.substring(1, result.length() - 1);
		}
		return result;
	}

}
