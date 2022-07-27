import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImdbContentExtractor implements ContentExtractor {

	@Override
	public List<Content> extract(String jsonContent) {
		ObjectMapper jsonMapper = new ObjectMapper();

		// get the list of movies and as a list and print the results
		List<Content> contentList = new ArrayList<Content>();

		try {
			// extracs json and reads the list of movies
			// Using a mock API
			ImdbJsonResponse readValue = jsonMapper.readValue(jsonContent, ImdbJsonResponse.class);
			var movieList = readValue.items;

			// Create a new list of Content
			for (Map<String, String> movie : movieList) {
				String title = movie.get("title");
				String imageUrl = getFullImageUrl(movie.get("url"));
				var content = new Content(title, imageUrl);
				System.out.println(content);
				contentList.add(content);
			}

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return contentList;
	}

	private String getFullImageUrl(String url) {
		String pattern = "._V1";
		Boolean isFullHdUrl = url.indexOf(pattern) == -1;

		if (isFullHdUrl)
			return url;
		else
			return url.substring(0, url.indexOf(pattern)) + url.substring(url.length() - 4);
	}

}
