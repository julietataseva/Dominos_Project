package dominos.model.repository;

import dominos.model.pojo.AdditionalProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalProductImageRepository extends JpaRepository<AdditionalProductImage, Integer> {
}