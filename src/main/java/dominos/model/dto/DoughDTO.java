package dominos.model.dto;

import dominos.model.pojo.Dough;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DoughDTO {
    private Dough.DoughType type;
}