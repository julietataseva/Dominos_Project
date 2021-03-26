package dominos.model.repository;

import dominos.model.pojo.AdditionalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalProductRepository extends JpaRepository<AdditionalProduct, Integer> {

    AdditionalProduct findByName(String name);
}
