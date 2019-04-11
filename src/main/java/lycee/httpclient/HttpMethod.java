package lycee.httpclient;

public enum HttpMethod {
	GET("GET"),
	POST("POST"),
	;

	private String method;

	private HttpMethod(final String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	@Override
	public String toString() {
		return getMethod();
	}

}
