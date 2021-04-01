package dominos.model.dto;

import dominos.model.pojo.Dough;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class DoughDTO {
    private int id;
    private Dough.DoughType type;

    public DoughDTO(int id, Dough.DoughType doughType) {
        this.id = id;
        this.type = doughType;
    }

    public DoughDTO(Dough dough) {
        this.id = dough.getId();
        this.type = dough.getType();
    }
}