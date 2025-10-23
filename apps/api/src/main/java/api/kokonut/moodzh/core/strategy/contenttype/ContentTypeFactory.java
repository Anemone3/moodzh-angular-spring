package api.kokonut.moodzh.core.strategy.contenttype;

import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentTypeFactory {
    private final Map<String, IContentTypeStrategy> contentTypeStrategy;

    public IContentTypeStrategy get(String contentType) {
        IContentTypeStrategy contentTypeImpl = contentTypeStrategy.get(contentType);
        if (Objects.isNull(contentTypeImpl)) {
            throw new IllegalArgumentException("Unsupported content type strategy.");
        }

        return contentTypeImpl;
    }
}
