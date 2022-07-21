import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class App {
    static String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    static String appConfigPath = rootPath + "application.properties";
    protected static Properties applicationProperties = new Properties();

    public static void main(String[] args) throws Exception {
        applicationProperties.load(new FileInputStream(appConfigPath));
        String url = applicationProperties.getProperty("API_URL");
        URI uriAddress = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(uriAddress).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> movieList = parser.parse(body);

        // exibir e manipular os dados
        for (Map<String, String> filme : movieList) {
            System.out.println(filme.get("title"));
            System.out.println(filme.get("image"));
            System.out.println(filme.get("imDbRating"));
            System.out.println();
        }
    }
}
