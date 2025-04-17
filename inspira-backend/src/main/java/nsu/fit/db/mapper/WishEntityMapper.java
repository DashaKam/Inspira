package nsu.fit.db.mapper;

import nsu.fit.db.entity.WishEntity;
import nsu.fit.domain.model.Wish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface WishEntityMapper {

    Wish entityToWish(WishEntity wish);

    WishEntity wishToEntity(Wish wish);
}
