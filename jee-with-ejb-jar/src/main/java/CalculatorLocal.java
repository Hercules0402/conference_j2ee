import javax.ejb.Local;

@Local
public interface CalculatorLocal {
    public Long calculate(Long leftOperande, Long rightOperande);
}
