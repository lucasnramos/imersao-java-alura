import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
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
        String jsonContent = new ApiClient().fetch(url);
        ContentExtractor nasaExtractor = new NasaContentExtractor();
        // ContentExtractor movieExtractor = new NasaContentExtractor();
        List<Content> contentList = nasaExtractor.extract(jsonContent);
        var factory = new StickerFactory();
        for (Content content : contentList) {
            String imgUrl = content.getImageUrl();
            String title = content.getTitle();
            InputStream inputStream = new URL(imgUrl).openStream();
            String fileName = title + ".png";
            factory.create(inputStream, fileName);
            System.out.println(title);
            System.out.println();
        }
    }
}
