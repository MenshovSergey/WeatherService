package cloud.weather.server.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;

public class UriUtil {
    public static String extractParamFromUri(URI uri, String param) {

        return Optional.ofNullable(UriComponentsBuilder.fromUri(uri)
                                           .build()
                                           .getQueryParams()
                                           .toSingleValueMap()
                                           .get(param))
                .orElse("")
                .trim();
    }

    public static URI replaceParamFromUri(URI from, URI to) {
        String newUriString = to.toString();
        if (newUriString.contains("?")) {
            newUriString = newUriString.substring(0, newUriString.indexOf("?"));
        }
        return URI.create(newUriString + "?" + from.getQuery());
    }

    public static boolean equalsUriPath(URI u1, URI u2) {
        String path1 = u1.getPath();
        if (!path1.endsWith("/")) {
            path1 = path1 + "/";
        }
        String path2 = u2.getPath();
        if (!path2.endsWith("/")) {
            path2 = path2 + "/";
        }
        return path1.equals(path2);
    }

    public static String encode(String s, String encoding) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, encoding);
    }

    public static String decode(String s, String encoding) throws UnsupportedEncodingException {
        return URLDecoder.decode(s, encoding);
    }

}
