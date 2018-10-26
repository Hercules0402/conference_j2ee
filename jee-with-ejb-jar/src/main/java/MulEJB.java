import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class MulEJB implements CalculatorLocal{
    @EJB
    private MulEJB mulEJB;

    @Override
    public Long calculate(Long leftOperande, Long rightOperande) {
        return mulEJB.calculate(leftOperande, rightOperande);
    }
}
