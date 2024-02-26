package analyzer.code.utils;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import analyzer.code.App;

public class JteTemplateEngine {

    public static final String FLASH_MESSAGE_KEY = "flash-message";
    public static final String FLASH_TYPE_KEY = "flash-type";

    public static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver(Environment.TEMPLATES_FOLDER_NAME, classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

}
