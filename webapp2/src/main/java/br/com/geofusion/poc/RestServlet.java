package br.com.geofusion.poc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/rest", name="RestServlet")
public class RestServlet extends HttpServlet {

	private static final long serialVersionUID = -4718739199311134131L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String method = ((req.getParameter("method") != null)? req.getParameter("method"): "get").toLowerCase();
		
		final String targetUrl = "http://localhost:8080/my-service-two/rest/" + method;
		
//		final CasAuthenticationToken token = (CasAuthenticationToken) req.getUserPrincipal();
//
//		final User user = (User) token.getPrincipal();
//		
//		final String proxyTicket = token.getAssertion().getPrincipal().getProxyTicketFor(targetUrl);
				
//		final String serviceUrl = targetUrl + "?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
		
//		String proxyResponse = get(serviceUrl, method);
				
		resp.getWriter().println();
		resp.getWriter().println("########################## My Service One ##########################");
//		resp.getWriter().println("UserName: " + user.getUsername());
//		resp.getWriter().println("Authorities: " + user.getAuthorities());
//		resp.getWriter().println("Account Non Expired: " + user.isAccountNonExpired());
//		resp.getWriter().println("Account Non Locked: " + user.isAccountNonLocked());
//		resp.getWriter().println("Credentials Non Expired: " + user.isCredentialsNonExpired());
		resp.getWriter().println("Target URL: " + targetUrl);
//		resp.getWriter().println("Service URL: " + serviceUrl);
//		resp.getWriter().println("Proxy Ticket: " + proxyTicket);
//		resp.getWriter().println("Proxy Response: " + proxyResponse);
		resp.getWriter().println("########################## My Service One ##########################");
		resp.getWriter().println();
	}

	private static String get(final String url, final String method) throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException {	
		final URL u = new URL(url);

		final HttpURLConnection httpsConn = (HttpURLConnection) u.openConnection();
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


