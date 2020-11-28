package pl.wwf.nowaste.domain.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.wwf.nowaste.domain.tag.web.TagCreateRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService service;

    @GetMapping("/{id}")
    public Tag findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    public List<Tag> findAll() {
        return service.findAll();
    }

    @PutMapping
    @ResponseStatus(CREATED)
    public Tag create(@RequestBody TagCreateRequest request) {
        return service.create(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


}
