package edn2json_plugin;

import org.gradle.api.Action;
import org.gradle.api.file.ContentFilterable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

/**
 * Extension that allows for converting EDN resources to Json.
 */
public class Edn2JsonExtension {
    /**
     * Convert some EDN resource.
     *
     * @param filterable The resource to convert.
     * @return The parameter.
     */
    public ContentFilterable invoke(ContentFilterable filterable) {
        return filterable.filter(Edn2JsonReader.class);
    }

    /**
     * Convert some EDN resource, configuring the Gson that will be used.
     *
     * @param filterable The action to configure the builder with.
     * @return The parameter.
     */
    public ContentFilterable invoke(ContentFilterable filterable, Action<GsonBuilder> builderAction) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        builderAction.execute(gsonBuilder);

        HashMap<String, Object> properties = new HashMap<>();
        Gson gson = gsonBuilder.create();
        properties.put("gson", gson);
        return filterable.filter(properties, Edn2JsonReader.class);
    }
}
