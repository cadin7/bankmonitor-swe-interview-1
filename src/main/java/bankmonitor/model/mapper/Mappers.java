package bankmonitor.model.mapper;

import java.util.Collection;
import java.util.List;

public interface Mappers<S, T> {
    default S toApi(T source) {
        return toApiMapper().of(source);
    }

    default List<S> toApi(Collection<T> source) {
        return toApiMapper().of(source);
    }

    ModelMapper<T, S> toApiMapper();
}
