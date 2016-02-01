package br.com.geofusion.poc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.pac4j.cas.profile.CasProxyProfile;

@WebServlet(value = "/rest", name = "RestServlet")
public class RestServlet extends HttpServlet {

	private static final long serialVersionUID = -4718739199311134131L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = "https://gfn-tix-8krp172.onmaps.com.br/webapp2/protected";

		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		CasProxyProfile profile = principals.byType(CasProxyProfile.class).iterator().next();

		String ticket = profile
				.getProxyTicketFor("https://gfn-tix-8krp172.onmaps.com.br/webapp2/callback");
		System.out.println("+++++++++++ " + "ticket = " + ticket);

		URL obj = new URL(url + "&ticket=" + ticket);

		resp.getWriter().println(get(obj, "GET"));
	}

	private static String get(final URL url, final String method)
			throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException {

		final HttpURLConnection httpsConn = (HttpURLConnection) url.openConnection();
		httpsConn.setConnectTimeout(5000);
		httpsConn.setReadTimeout(5000);
		httpsConn.setRequestMethod(method.toUpperCase());
		httpsConn.setDoOutput(true);

		final int code = httpsConn.getResponseCode();

		final InputStreamReader inputStreamReader = new InputStreamReader(httpsConn.getInputStream());
		final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		final StringBuilder sb = new StringBuilder();
		String inputLine = "";
		while ((inputLine = bufferedReader.readLine()) != null) {
			sb.append(inputLine);
		}

		final String body = sb.toString();

		return body;
	}
}
