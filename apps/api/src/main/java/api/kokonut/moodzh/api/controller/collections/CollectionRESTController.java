package api.kokonut.moodzh.api.controller.collections;


import api.kokonut.moodzh.api.dto.request.CollectionRequest;
import api.kokonut.moodzh.api.dto.response.CollectionResponse;
import api.kokonut.moodzh.core.services.collections.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
public class CollectionRESTController {

    private final CollectionService collectionService;


    @PostMapping
    public ResponseEntity<CollectionResponse> createCollection(@RequestBody CollectionRequest collectionRequest, @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(collectionService.createCollection(collectionRequest,user.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<CollectionResponse>> getUserCollections(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(collectionService.getUserCollections(user.getUsername()));
    }

}
