package beans;

public class AddCalculator implements Calculator{
    @Override
    public Long calculate(Long leftOperande, Long rightOperande) {
        return leftOperande + rightOperande;
    }
}
