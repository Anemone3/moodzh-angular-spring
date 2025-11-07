package api.kokonut.moodzh.data.api;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PexelsPhotoResponse {
    private Long id;
    private int width;
    private int height;
    private String url;
    private SrcPhotos src;
    private boolean liked;
    private String alt;
}

@Builder
@Data
class SrcPhotos {
    private String original;
    private String large2x;
    private String large;
    private String medium;
    private String small;
    private String portrait;
    private String landscape;
    private String tiny;
}