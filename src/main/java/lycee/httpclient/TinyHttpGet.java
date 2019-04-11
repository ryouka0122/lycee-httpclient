package lycee.httpclient;

import java.net.HttpURLConnection;

public class TinyHttpGet extends TinyHttpClient {

	protected TinyHttpGet() {
		super(HttpMethod.GET);
	}

	@Override
	protected void processBeforeConnect(final HttpURLConnection connection) {
		// TODO Auto-generated method stub
	}

}
