package nsu.fit.domain.port;

import nsu.fit.domain.model.Wish;

public interface WishRepositoryPort {
    Wish save(Wish wish);
}
