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
    private Dough.DoughType type;

    public DoughDTO(Dough.DoughType doughType){
        this.type = doughType;
    }
}