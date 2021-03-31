package dominos.model.repository;

import dominos.model.pojo.PizzaImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaImageRepository extends JpaRepository<PizzaImage, Integer> {
}