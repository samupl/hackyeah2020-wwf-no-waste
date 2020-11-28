package pl.wwf.nowaste.domain.product.reusage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.wwf.nowaste.domain.product.reusage.web.ReusageCreateRequest;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/reusage")
@RequiredArgsConstructor
public class ReusageController {

    private final ReusageService service;

    @PutMapping
    @ResponseStatus(CREATED)
    public Reusage create(@RequestBody ReusageCreateRequest request, Principal principal) {
        return service.create(request, principal);
    }

    @PostMapping("/{id}/up")
    public void upVote(@PathVariable Long id) {
        service.upVote(id);
    }

    @PostMapping("/{id}/down")
    public void downVote(@PathVariable Long id) {
        service.upVote(id);
    }

}
