package lycee.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class TinyHttpClient {

	protected String url;

	protected String encoding;

	protected ContentType contentType;

	protected final HttpMethod method;

	protected final Map<String, String> parameters;

	protected final HttpResponse httpResponse;

	/**
	 * CONSTRUCTOR
	 * @param method HTTP Method (GET, POST, ...)
	 */
	protected TinyHttpClient(final HttpMethod method) {
		url = null;
		httpResponse = new HttpResponse();
		parameters = new HashMap<>();
		this.method = Objects.requireNonNull(method);
		contentType = ContentType.APPLICATION_FORM;
		encoding ="UTF-8";
	}

	public void setUrl(final String url) {
		this.url = Objects.requireNonNull(url);
	}

	public void setParameters(final Map<String, String> params) {
		parameters.putAll(Objects.requireNonNull(params));
	}

	public HttpResponse call() throws IOException {
		return _call0(method);
	}

	abstract protected void processBeforeConnect(HttpURLConnection connection);

	protected HttpResponse _call0(final HttpMethod method)
			throws IOException
	{
		final URL target = new URL(makeUrl());
		final HttpURLConnection conn = (HttpURLConnection) target.openConnection();

		conn.setRequestMethod(method.getMethod());
		conn.setRequestProperty("Content-Type", contentType.getType());

		processBeforeConnect(conn);

		try {
			conn.connect();

			final HttpResponse response = new HttpResponse();
			response.setHeaders(conn.getHeaderFields());
			response.setHttpStatus(conn.getResponseCode());

			final int len = conn.getContentLength();
			if(len>0) {
				final StringBuilder builder = new StringBuilder(len);
				try(InputStream is = conn.getInputStream();
						InputStreamReader isr = new InputStreamReader(is);
						BufferedReader reader = new BufferedReader(isr)) {
					final char[] cbuf = new char[1024];
					while( reader.read(cbuf)>0 ) {
						builder.append(cbuf);
					}
				}
				response.setBody(builder.toString());
			}
			return response;
		}finally {
			conn.disconnect();
		}
	}

	private String makeUrl() {
		final String result = url;
		if(HttpMethod.GET==method) {
			url += "?" + getEncodedQuery();
		}
		return result;
	}

	protected String getEncodedQuery() {
		return parameters.entrySet()
				.stream()
				.map(e -> String.format("%s=%s", e.getKey(), encode(e.getValue(), encoding)))
				.collect(Collectors.joining("&"));
	}

	private String encode(final String value, final String encoding) {
		try {
			return URLEncoder.encode(value, encoding);
		} catch (final UnsupportedEncodingException e) {
			// TODO 例外をエスカレすべき？
			return "";
		}
	}
}
