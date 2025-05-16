///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.github.jknack:handlebars:4.4.0

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;

class GithubJavaMagic {
    public static void main(String... args) throws Exception {
        final var workingPath = Paths.get(args[0]).toAbsolutePath().normalize();

        final var templateContent = Files.readString(Paths.get("template.hbs"));

        final var handlebars = new Handlebars();
        final var template = handlebars.compileInline(templateContent);

        final Map<String, Object> context = Map.of(
                "timestamp", System.currentTimeMillis(),
                "previousPageContents", Files.readString(workingPath.resolve("index.html")));

        final var output = template.apply(context);

        Files.writeString(workingPath.resolve("index.html"), output, StandardCharsets.UTF_8);
    }
}
