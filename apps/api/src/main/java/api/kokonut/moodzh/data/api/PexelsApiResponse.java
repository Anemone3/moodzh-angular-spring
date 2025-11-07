package api.kokonut.moodzh.data.api;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PexelsApiResponse {
    private int total_results;
    private int page;
    private int per_page;
    private List<PexelsPhotoResponse> photos;
    private String next_page;
}
