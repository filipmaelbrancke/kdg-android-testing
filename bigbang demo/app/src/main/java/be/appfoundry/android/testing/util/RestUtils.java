package be.appfoundry.android.testing.util;

import com.google.gson.stream.JsonReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * Utilities class for tasks related to remoting, demo-mock remoting, etc.
 */
public class RestUtils {

    /**
     * Read an InputStream into a byte-array.
     *
     * @param input input stream to read
     * @return byte-array containing the full inputstream data
     * @throws IOException
     */
    public static byte[] readFullInputStream(final InputStream input) throws IOException {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }

    /**
     * Initialize a Gson JsonReader from a byte-array.
     *
     * @param bytes the input bytes to initialize the jsonreader
     * @return Gson JsonReader
     * @throws UnsupportedEncodingException
     */
    public static JsonReader getJsonReaderForBytes(byte[] bytes) throws UnsupportedEncodingException {
        final String json = new String(bytes, "UTF-8");
        final JsonReader jsonReader = new JsonReader(new StringReader(json));
        jsonReader.setLenient(true);
        return jsonReader;
    }

    /**
     * Initialize a Gson JsonReader from an inputstream.
     *
     * @param inputStream the inputstream to initialize the jsonreader
     * @return Gson JsonReader
     * @throws IOException
     */
    public static JsonReader getJsonReaderForInputStream(final InputStream inputStream) throws IOException {
        final byte[] bytes = readFullInputStream(inputStream);
        final JsonReader jsonReader = getJsonReaderForBytes(bytes);
        return jsonReader;
    }
}
