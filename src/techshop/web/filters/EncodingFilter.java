package techshop.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Encoding filter
 */
public class EncodingFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(EncodingFilter.class);

	private String encoding;

	@Override
	public void init(FilterConfig config) throws ServletException {
		LOG.debug("Filter initialization starts");
		encoding = config.getInitParameter("encoding");
		LOG.trace("Encoding from web.xml ==> " + encoding);
		if (encoding == null) {
			LOG.trace("No encoding in web.xml, set UTF-8");
			encoding = "UTF-8";
		}
		LOG.debug("Filter initialization finished");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("Filter starts");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		LOG.trace("Request uri ==> " + httpRequest.getRequestURI());

		String requestEncoding = request.getCharacterEncoding();
		if (requestEncoding == null) {
			LOG.trace("Request encoding = null, set encoding ==> " + encoding);
			request.setCharacterEncoding(encoding);
		}

		response.setContentType("text/html; charset=" + encoding);
		response.setCharacterEncoding(encoding);

		LOG.debug("Filter finished");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		LOG.debug("Filter destruction starts");
		LOG.debug("Filter destruction finished");
	}

}
