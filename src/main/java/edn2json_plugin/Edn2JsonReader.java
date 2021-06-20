package edn2json_plugin;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.*;

/**
 * A FilterReader that reads EDN and transforms it to Json, before writing it back out.
 */
public class Edn2JsonReader extends FilterReader {
    private static final Gson GSON = new Gson();
    private static final IFn READ = Clojure.var("clojure.edn", "read");
    private static final IFn EDN2JSON = Clojure.var("edn2json-plugin.core", "edn->json");

    /**
     * The Gson object used for writing.
     */
    public Gson gson = GSON;

    private boolean initialized = false;
    private final Reader oldIn;

    /**
     * Creates a new filtered reader.
     *
     * @param in a Reader object providing the underlying stream.
     * @throws NullPointerException if {@code in} is {@code null}
     */
    public Edn2JsonReader(Reader in) {
        super(new PipedReader());
        oldIn = in;
    }

    private void maybeInit() {
        if (initialized) return;
        initialized = true;
        Object value = READ.invoke(new PushbackReader(oldIn));
        try (PipedWriter writer = new PipedWriter((PipedReader) this.in)) {
            gson.toJson((JsonElement) EDN2JSON.invoke(value), writer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public int read() throws IOException {
        maybeInit();
        return super.read();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        maybeInit();
        return super.read(cbuf, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        maybeInit();
        return super.skip(n);
    }

    @Override
    public boolean ready() throws IOException {
        maybeInit();
        return super.ready();
    }

    @Override
    public boolean markSupported() {
        maybeInit();
        return super.markSupported();
    }

    @Override
    public void mark(int readAheadLimit) throws IOException {
        maybeInit();
        super.mark(readAheadLimit);
    }

    @Override
    public void reset() throws IOException {
        maybeInit();
        super.reset();
    }

    @Override
    public void close() throws IOException {
        maybeInit();
        oldIn.close();
        super.close();
    }
}
