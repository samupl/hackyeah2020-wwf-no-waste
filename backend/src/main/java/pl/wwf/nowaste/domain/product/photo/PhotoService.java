package pl.wwf.nowaste.domain.product.photo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import static java.lang.String.format;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.logging.log4j.util.Strings.isEmpty;
import static pl.wwf.nowaste.web.ValidationUtils.check;

@Service
@RequiredArgsConstructor
public class PhotoService {

    @Value("${server.file.storage}")
    private String filePath;

    @Value("${server.url}")
    private String serverUrl;

    public String createPhotoUrl(String id) {
        if (isEmpty(id)) {
            return null;
        }
        return format("%s/image/%s", serverUrl, id);
    }

    public byte[] findFile(String name) throws IOException {
        final File file = new File(format("%s/%s", filePath, name));
        check(file.exists(), "Photo Doesn't exists.");

        return IOUtils.toByteArray(new FileInputStream(file));
    }

    public String uploadFile(MultipartFile file) {
        if (file == null) {
            return null;
        }
        final String fileId = format("%s.%s", UUID.randomUUID().toString(), getExtension(file.getOriginalFilename()));
        final File fileToSave = new File(format("%s/%s", filePath, fileId));
        try {
            file.transferTo(fileToSave);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not save photo due to: "+e.getMessage());
        }
        return fileId;
    }
}
