package lycee.httpclient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class TinyHttpPost extends TinyHttpClient {

	public TinyHttpPost() {
		super(HttpMethod.POST);
	}

	@Override
	protected void processBeforeConnect(final HttpURLConnection connection) {

		connection.setDoOutput(true);
		try (OutputStream os = connection.getOutputStream()) {
			os.write(getEncodedQuery().getBytes());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
