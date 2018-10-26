//import fr.ig2i.calculator.Calculator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.wildfly.naming.client.WildFlyInitialContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Ignore
public class OutOfMemTest {

    // Calculator multiplicateur;

    @Before
    public void setUp() throws Exception{
        /*
        Hashtable<String,String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        env.put(Context.PROVIDER_URL,"remote+http://localhost:8080");
        Context context = new InitialContext(env);
        multiplicateur = (Calculator) context.lookup("jee-with-ejb-ear-1.0-SNAPSHOT/jee-with-ejb-ejb-jar-1.0-SNAPSHOT/MultiplicateurEJB!fr.ig2i.calculator.Calculator");
        */
    }

    @Test
    public void testOutOfMem() throws Exception{
        /*
        int nbThread = 20;
        Executor exec = Executors.newFixedThreadPool(nbThread);
        for(int i=0; i< nbThread; i++){
            exec.execute(() -> {
                long leftLimit = 10L;
                long rightLimit = 100L;
                while(true){
                    long left = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
                    long right = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
                    multiplicateur.calculer(left,right);
                }
            });
        }
        ((ExecutorService) exec).awaitTermination(20L, TimeUnit.MINUTES);
        */
    }
}
