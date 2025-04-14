package nsu.fit.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.fit.db.entity.WishEntity;
import nsu.fit.db.mapper.WishEntityMapper;
import nsu.fit.db.repository.WishRepository;
import nsu.fit.domain.model.Wish;
import nsu.fit.domain.port.WishRepositoryPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishRepositoryService implements WishRepositoryPort {

    private final WishRepository wishRepository;
    private final WishEntityMapper wishEntityMapper;

    @Override
    public Wish save(Wish wish) {
        WishEntity wishToSave = wishEntityMapper.wishToEntity(wish);
        return wishEntityMapper.entityToWish(wishRepository.save(wishToSave));
    }
}
