package lycee.httpclient;

public enum ContentType {
	TEXT_PLAIN("text/plain"),
	TEXT_JAVASCRIPT("text/javascript"),
	TEXT_CSV("text/csv"),
	TEXT_HTML("text/html"),
	APPLICATION_FORM("application/x-www-form-urlencoded"),
	APPLICATION_OCTET_STREAM("application/octet-stream"),
	APPLICATION_MS_EXCEL("application/vnd.ms-excel")
	;

	private String type;

	private ContentType(final String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return getType();
	}

}
