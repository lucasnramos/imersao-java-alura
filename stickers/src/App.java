import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    static private final String apiUrl = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";

    public static void main(String[] args) throws Exception {
        // read API_URL from properties file
        String url = apiUrl;
        // Creates new URI object to build a new HTTP Client and request
        URI uriAddress = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(uriAddress).GET().build();
        // Sends a get request to the API and retrieves body
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        var parser = new JsonParser();
        List<Map<String, String>> movieList = parser.parse(body);

        // reading only first 3 for easy debug
        for (int i = 0; i < 3; i++) {
            var filme = movieList.get(i);
            System.out.println("Titulo do Filme: " + filme.get("title"));
            System.out.println("Link do Poster: " + filme.get("image"));
            System.out.println("Score: " + filme.get("imDbRating"));
            System.out.println();
        }

        // prints all movies
        // for (Map<String, String> filme : movieList) {
        // System.out.println("Titulo do Filme: " + filme.get("title"));
        // System.out.println("Link do Poster: " + filme.get("image"));
        // System.out.println("Score: " + filme.get("imDbRating"));
        // System.out.println();
        // }
    }
}
