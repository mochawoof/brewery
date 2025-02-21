import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import java.util.HashMap;

class getSyntaxConstant {
    public static HashMap<String, String> mappings = new HashMap<String, String>() {{
        put("as", SyntaxConstants.SYNTAX_STYLE_ACTIONSCRIPT);
        put("asm", SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
        put("c", SyntaxConstants.SYNTAX_STYLE_C);
        put("clj", SyntaxConstants.SYNTAX_STYLE_CLOJURE);
        put("cpp", SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
        put("cs", SyntaxConstants.SYNTAX_STYLE_CSHARP);
        put("css", SyntaxConstants.SYNTAX_STYLE_CSS);
        put("csv", SyntaxConstants.SYNTAX_STYLE_CSV);
        put("d", SyntaxConstants.SYNTAX_STYLE_D);
        put("dockerfile", SyntaxConstants.SYNTAX_STYLE_DOCKERFILE);
        put("dart", SyntaxConstants.SYNTAX_STYLE_DART);
        put("pas", SyntaxConstants.SYNTAX_STYLE_DELPHI);
        put("dtd", SyntaxConstants.SYNTAX_STYLE_DTD);
        put("for,f90", SyntaxConstants.SYNTAX_STYLE_FORTRAN);
        put("go", SyntaxConstants.SYNTAX_STYLE_GO);
        put("groovy", SyntaxConstants.SYNTAX_STYLE_GROOVY);
        put("htaccess", SyntaxConstants.SYNTAX_STYLE_HTACCESS);
        put("html,htm", SyntaxConstants.SYNTAX_STYLE_HTML);
        put("ini", SyntaxConstants.SYNTAX_STYLE_INI);
        put("java", SyntaxConstants.SYNTAX_STYLE_JAVA);
        put("js", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        put("json", SyntaxConstants.SYNTAX_STYLE_JSON);
        put("jshinitrc", SyntaxConstants.SYNTAX_STYLE_JSON_WITH_COMMENTS);
        put("jsp", SyntaxConstants.SYNTAX_STYLE_JSP);
        put("kt,ktm,kts", SyntaxConstants.SYNTAX_STYLE_KOTLIN);
        put("tex", SyntaxConstants.SYNTAX_STYLE_LATEX);
        put("less", SyntaxConstants.SYNTAX_STYLE_LESS);
        put("lisp", SyntaxConstants.SYNTAX_STYLE_LISP);
        put("lua", SyntaxConstants.SYNTAX_STYLE_LUA);
        put("makefile", SyntaxConstants.SYNTAX_STYLE_MAKEFILE);
        put("md", SyntaxConstants.SYNTAX_STYLE_MARKDOWN);
        put("mxml", SyntaxConstants.SYNTAX_STYLE_MXML);
        put("nsis,nsi", SyntaxConstants.SYNTAX_STYLE_NSIS);
        put("perl", SyntaxConstants.SYNTAX_STYLE_PERL);
        put("php", SyntaxConstants.SYNTAX_STYLE_PHP);
        put("proto", SyntaxConstants.SYNTAX_STYLE_PROTO);
        put("properties", SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE);
        put("py", SyntaxConstants.SYNTAX_STYLE_PYTHON);
        put("rb", SyntaxConstants.SYNTAX_STYLE_RUBY);
        put("rs", SyntaxConstants.SYNTAX_STYLE_RUST);
        
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