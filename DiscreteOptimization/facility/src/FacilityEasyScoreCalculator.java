import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class FacilityEasyScoreCalculator implements EasyScoreCalculator<OptaProblem> {
    @Override
    public Score calculateScore(OptaProblem optaProblem) {
        long hardScore = 0;
        for (Facility f : optaProblem.getFacilities()) {
            if (f.getCapacityLeft() < 0)
                hardScore += f.getCapacityLeft();
        }
        for (Customer c : optaProblem.getCustomers()) {
            if (c.getFacility() == null)
                hardScore -= c.getDemand();
        }

        long softScore = Math.round(-totalCost(optaProblem));

        return HardSoftLongScore.of(hardScore, softScore);
    }

    public double totalCost(OptaProblem optaProblem) {
        double totalCost = 0.0;
        for (Facility f : optaProblem.getFacilities()) {
            totalCost += f.cost();
        }
        for (Customer c : optaProblem.getCustomers()) {
            totalCost += c.cost();
        }
        return totalCost;
    }
}