package beans;

public class MulCalculator implements Calculator{
    @Override
    public Long calculate(Long leftOperande, Long rightOperande) {
        return leftOperande * rightOperande;
    }
}
