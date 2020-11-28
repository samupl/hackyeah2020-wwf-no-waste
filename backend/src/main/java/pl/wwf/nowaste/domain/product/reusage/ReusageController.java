package pl.wwf.nowaste.domain.product.reusage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.wwf.nowaste.domain.product.reusage.web.ReusageCreateRequest;
import pl.wwf.nowaste.domain.product.reusage.web.ReusageDetails;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/reusage")
@RequiredArgsConstructor
public class ReusageController {

    private final ReusageService service;

    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    public ReusageDetails create(@RequestPart("reusage") ReusageCreateRequest request, @RequestPart("photos") MultipartFile[] photos, Principal principal) {
        return service.create(request, photos, principal);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public ReusageDetails create(@RequestBody ReusageCreateRequest request, Principal principal) {
        return service.create(request, null, principal);
    }

    @PostMapping("/{id}/up")
    public void upVote(@PathVariable Long id) {
        service.upVote(id);
    }

    @PostMapping("/{id}/down")
    public void downVote(@PathVariable Long id) {
        service.downVote(id);
    }

}
