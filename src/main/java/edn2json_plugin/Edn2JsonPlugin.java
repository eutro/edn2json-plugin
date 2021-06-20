package edn2json_plugin;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Plugin that adds edn2json for converting EDN to Json.
 */
public class Edn2JsonPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("edn2json-plugin.core"));
        target.getExtensions().create("edn2json", Edn2JsonExtension.class);
    }
}
