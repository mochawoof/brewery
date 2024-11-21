import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import java.util.HashMap;

class getSyntaxConstant {
    public static HashMap<String, String> mappings = new HashMap<String, String>() {{
        put("java", SyntaxConstants.SYNTAX_STYLE_JAVA);
        put("properties", SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE);
        put("css", SyntaxConstants.SYNTAX_STYLE_CSS);
        put("csv", SyntaxConstants.SYNTAX_STYLE_CSV);
        put("dockerfile", SyntaxConstants.SYNTAX_STYLE_DOCKERFILE);
        put("json", SyntaxConstants.SYNTAX_STYLE_JSON);
        put("md", SyntaxConstants.SYNTAX_STYLE_MARKDOWN);
        put("sh", SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL);
        put("bat", SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL);
        put("yaml", SyntaxConstants.SYNTAX_STYLE_YAML);
    }};
    public static String get(String extension) {
        String got = mappings.get(extension);
	if (got != null) {
		return got;
	} else {
		return SyntaxConstants.SYNTAX_STYLE_NONE;
	}
    }
}