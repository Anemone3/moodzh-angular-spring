package api.kokonut.moodzh.api.controller.photos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.kokonut.moodzh.core.services.pexel.PexelsApiService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/photos")
@AllArgsConstructor
public class PhotoRESTController {

    private final PexelsApiService pexelsApiService;

    @GetMapping("/search")
    public ResponseEntity<?> searchPhotos(@RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok("photos search" + query + " page= " + page);
    }

    @GetMapping("/curated")
    public ResponseEntity<?> getCuratedPhotos(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(pexelsApiService.curatedPhotos(page));
    }

}