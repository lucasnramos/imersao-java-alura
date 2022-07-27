import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NasaContentExtractor implements ContentExtractor {
	public List<Content> extract(String jsonContent) {
		var parser = new JsonParser();
		List<Map<String, String>> attributeList = parser.parse(jsonContent);
		List<Content> contentList = new ArrayList<Content>();

		for (Map<String, String> attribute : attributeList) {
			String title = attribute.get("title");
			String imageUrl = attribute.get("url");
			var content = new Content(title, imageUrl);
			contentList.add(content);
		}
		System.out.println("Content List\n" + contentList);

		return contentList;
	}
}
