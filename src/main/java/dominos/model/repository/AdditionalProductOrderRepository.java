package dominos.model.repository;

import dominos.model.pojo.AdditionalProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalProductOrderRepository extends JpaRepository<AdditionalProductOrder, Integer> {
}