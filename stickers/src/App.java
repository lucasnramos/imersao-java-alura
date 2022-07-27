import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
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

        // Parse response string as json using Jackson
        var body = ApiClient.fetch(url);

        // get the list of movies and as a list and print the results
        ObjectMapper jsonMapper = new ObjectMapper();
        var readValue = jsonMapper.readValue(body, ImdbJsonResponse.class);
        // printMovieList(movieList);

        // Class 2 - generate images for Stickers
        var movieList = readValue.items;

        // instantiate new sticker factory
        StickerFactory factory = new StickerFactory();
        for (int i = 0; i < movieList.size(); i++) {
            var movie = movieList.get(i);
            var movieTitle = movie.get("title");
            var movieRating = Float.parseFloat(movie.get("imDbRating"));
            InputStream inputStream = new URL(movie.get("image")).openStream();
            factory.create(inputStream, movieTitle, movieRating);
        }
    }

    private static void printMovieList(List<Map<String, String>> movieList) {
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
