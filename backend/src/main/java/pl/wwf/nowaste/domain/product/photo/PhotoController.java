package pl.wwf.nowaste.domain.product.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping(value = "/{name}")
    public byte[] findImage(@PathVariable String name) throws IOException {
        return photoService.findFile(name);
    }
}
