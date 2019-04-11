package lycee.httpclient;

import java.util.List;
import java.util.Map;

/**
 * <b>Http Response</b>
 * <p>
 * </p>
 * @author ryouka0122
 *
 */
public class HttpResponse {

	// ================================================
	// HTTP Status
	//
	private int httpStatus;

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(final int httpStatus) {
		this.httpStatus = httpStatus;
	}


	// ================================================
	// Response Headers
	//
	private Map<String, List<String>> headers;

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public List<String> getHeader(final String key) {
		return headers.get(key);
	}

	public void setHeaders(final Map<String, List<String>> headers) {
		this.headers = headers;
	}


	// ================================================
	// Response Body
	//
	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(final String body) {
		this.body = body;
	}


}
