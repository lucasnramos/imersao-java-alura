import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    private static Properties properties = new Properties();

    public static void main(String[] args) throws Exception {
        // read API_URL from .properties file
        properties.load(new FileInputStream("application.properties"));
        String url = properties.getProperty("API_URL");

        URI uriAddress = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(uriAddress).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Parse response string as json using Jackson
        ObjectMapper jsonMapper = new ObjectMapper();
        var readValue = jsonMapper.readValue(body, ImdbJsonResponse.class);
        var movieList = readValue.items;

        // Iterate the movieList to print each movie's title, img and rating
        for (Map<String, String> filme : movieList) {
            var rating = getRating(filme.get("imDbRating"));
            System.out.println(AnsiColors.BLUE + "Titulo do Filme: " +
                    filme.get("title"));
            System.out.println(AnsiColors.PURPLE + "Link do Poster: " +
                    filme.get("image"));
            System.out.println(AnsiColors.RED + "Score: " + rating);
            System.out.println();
        }
    }

    private static String getRating(String rating) {
        String halfStep = " 💔 ";
        String fullStep = " ❤️ ";
        String emojiRating = "";
        Float numberRating = Float.parseFloat(rating);
        Boolean ratingHasDecimal = numberRating % 2 != 0;
        Integer intRating = numberRating.intValue();

        for (int i = 0; i < intRating; i++) {
            emojiRating = emojiRating + fullStep;
        }

        if (ratingHasDecimal) {
            emojiRating = emojiRating + halfStep;
        }

        return emojiRating;
    }
}
