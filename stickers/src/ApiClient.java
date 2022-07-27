import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ApiClient {

	public static String fetch(String url) {
		try {
			URI uriAddress = URI.create(url);
			var client = HttpClient.newHttpClient();
			var request = HttpRequest.newBuilder(uriAddress).GET().build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			return response.body();
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
