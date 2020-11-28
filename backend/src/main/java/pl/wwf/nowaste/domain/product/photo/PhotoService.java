package pl.wwf.nowaste.domain.product.photo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
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

    public Set<String> createPhotosUrl(Set<String> photos) {
        if (CollectionUtils.isEmpty(photos)) {
            return Collections.emptySet();
        }
        return photos.stream()
                .map(this::createPhotoUrl)
                .collect(toSet());
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

    public Set<String> uploadFiles(MultipartFile[] photos) {
        if (photos == null || photos.length == 0) {
            return Collections.emptySet();
        }

        return Arrays.stream(photos)
                .filter(Objects::nonNull)
                .map(this::uploadFile)
                .collect(toSet());
    }
}
