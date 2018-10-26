package beans;

public class SubCalculator implements Calculator{
    @Override
    public Long calculate(Long leftOperande, Long rightOperande) {
        return leftOperande - rightOperande;
    }
}
