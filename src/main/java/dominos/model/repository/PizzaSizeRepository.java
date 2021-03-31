package dominos.model.repository;

import dominos.model.pojo.PizzaSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaSizeRepository extends JpaRepository<PizzaSize, Integer> {
}
