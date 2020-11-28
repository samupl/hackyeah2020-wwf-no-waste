package pl.wwf.nowaste.domain.product.reusage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.wwf.nowaste.domain.category.CategoryService;
import pl.wwf.nowaste.domain.product.ProductService;
import pl.wwf.nowaste.domain.product.photo.PhotoService;
import pl.wwf.nowaste.domain.product.reusage.web.ReusageCreateRequest;
import pl.wwf.nowaste.domain.product.reusage.web.ReusageDetails;
import pl.wwf.nowaste.domain.tag.TagService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;
import static pl.wwf.nowaste.web.PrincipalUtils.getAuthor;
import static pl.wwf.nowaste.web.ValidationUtils.check;
import static pl.wwf.nowaste.web.ValidationUtils.checkNotNull;

@Service
@RequiredArgsConstructor
public class ReusageService {

    private final static int DEFAULT_VOTE_COUNT = 0;

    private final ReusageRepository repository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final ProductService productService;
    private final PhotoService photoService;

    public ReusageDetails create(ReusageCreateRequest request, MultipartFile[] photos, Principal principal) {
        validateRequest(request);

        final Set<String> photoIds = photoService.uploadFiles(photos);

        final Reusage save = repository.save(Reusage.builder()
                .date(LocalDateTime.now())
                .title(request.getTitle())
                .author(getAuthor(principal))
                .description(request.getDescription())
                .upVotes(DEFAULT_VOTE_COUNT)
                .downVotes(DEFAULT_VOTE_COUNT)
                .photos(photoIds)
                .categories(categoryService.findAllByIdsIn(request.getCategories()))
                .tags(tagService.findAllByIdIn(request.getTags()))
                .build());

        return createReusageDetails(save);
    }

    public void upVote(Long id) {
        validateId(id);

        final Reusage reusage = repository.getOne(id);
        reusage.setDownVotes(reusage.getDownVotes()+1);
        repository.save(reusage);
    }

    public void downVote(Long id) {
        validateId(id);

        final Reusage reusage = repository.getOne(id);
        reusage.setDownVotes(reusage.getDownVotes()+1);
        repository.save(reusage);
    }

    private void validateId(Long id) {
        checkNotNull(id, "Request ID is not present");
        check(repository.existsById(id), "Reusage sample is not present");
    }


    private void validateRequest(ReusageCreateRequest request) {
        checkNotNull(request, "Request is not present");
        checkNotNull(request.getTitle(), "Request Title is not present");
        checkNotNull(request.getDescription(), "Request Description is not present");
        checkNotNull(request.getProductId(), "Request Product ID is not present");
        check(productService.existById(request.getProductId()), "Request Product doesn't exist");
        check(!isEmpty(request.getTags()), "Request Tags is not present");
    }

    private ReusageDetails createReusageDetails(Reusage reusage) {
        return ReusageDetails.builder()
                .id(reusage.getId())
                .date(reusage.getDate())
                .title(reusage.getTitle())
                .author(reusage.getAuthor())
                .description(reusage.getDescription())
                .upVotes(reusage.getUpVotes())
                .downVotes(reusage.getDownVotes())
                .photosUrl(photoService.createPhotosUrl(reusage.getPhotos()))
                .categories(reusage.getCategories())
                .tags(reusage.getTags())
                .build();
    }

}
