package br.com.geofusion.poc;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Collection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.util.WebUtils;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.CasClient.CasProtocol;
import org.pac4j.core.client.Clients;

import io.buji.pac4j.ClientRealm;

import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.mgt.SecurityManager;

public class CasProxyAuthenticationServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Destroying CAS Proxy Authentication Servlet Context Listener");
		ThreadContext.unbindSecurityManager(); 
		ThreadContext.remove(); 
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Initializing CAS Proxy Authentication Servlet Context Listener");
		CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ) ); 
		final WebEnvironment wm = WebUtils.getRequiredWebEnvironment(arg0.getServletContext()); 
		final WebSecurityManager wsm = wm.getWebSecurityManager(); 
		ThreadContext.bind(wsm); 

		final SecurityManager mgr = SecurityUtils.getSecurityManager(); 

		final Collection<Realm> realms = ((RealmSecurityManager) mgr).getRealms(); 

		for (final Realm realm : realms) { 
			if (realm.getClass().isAssignableFrom(ClientRealm.class)) { 
				final Clients clients = ((ClientRealm) realm).getClients(); 
				final CasClient client = (CasClient) clients.findClient("CasClient"); 
				client.setCasProtocol(CasProtocol.CAS20_PROXY); 
			} 
		} 


	}

}
