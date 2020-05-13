package es.uca.AutomaticFoodList.Entities;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.TypeDef;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.persistence.jpa.impl.score.buildin.hardsoft.HardSoftScoreHibernateType;

import javax.persistence.Column;

@PlanningSolution // OptaPlanner annotation
@TypeDef(defaultForType = HardSoftScore.class, typeClass = HardSoftScoreHibernateType.class) // Hibernate annotation
public class ListaComidaSolution {

    @Columns(columns = {@Column(name = "hardScore"), @Column(name = "softScore")})
    private HardSoftScore score;

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }
    public void setScore(HardSoftScore score) {
        this.score = score;
    }

}
