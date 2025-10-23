package api.kokonut.moodzh.api.dto.request;


public record CollectionRequest(
String name,
String description,
Boolean isPrivate
) {
}
