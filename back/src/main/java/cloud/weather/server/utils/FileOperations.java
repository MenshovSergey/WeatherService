package cloud.weather.server.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {

    public List<String> readFileToStringList(String filename, String charset) throws IOException {
        List<String> list = new ArrayList<>();
        LineIterator it = FileUtils.lineIterator(new File(filename), charset);
        try {
            while (it.hasNext()) {
                list.add(it.nextLine());
            }
        } finally {
            LineIterator.closeQuietly(it);
        }

        return list;
    }

    public List<String> readResourceFileToStringList(String filename, String charset) throws IOException {
        return readFileToStringList(getResource(filename), charset);
    }

    private String getResource(String filename) throws IOException {
        return new ClassPathResource(filename).getFile().getAbsolutePath();
    }

}
