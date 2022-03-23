package xyz.yuanmo.springboot.core.util;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import java.io.IOException;
import java.io.Reader;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/3/23 09:15 23
 * @since 1.0
 **/
public interface ResourceUtils {
    /**
     * reader
     *
     * @param resource
     * @return
     */
    static String getContent(Resource resource) {
        try {
            return getContent(resource, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * base
     *
     * @param resource
     * @param encoding
     * @return
     * @throws IOException
     */
    static String getContent(Resource resource, String encoding) throws IOException {
        EncodedResource encodedResource = new EncodedResource(resource, encoding);
        // 字符输入流
        try (Reader reader = encodedResource.getReader()) {
            return IOUtils.toString(reader);
        }
    }
}
