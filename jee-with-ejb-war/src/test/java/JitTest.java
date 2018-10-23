import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Ignore
public class JitTest {



    @Test
    public void jitTesting() throws InterruptedException {
        int cpt = 0;
        while(cpt < 10){
            this.doSomething();
            TimeUnit.SECONDS.sleep(1L);
            cpt++;
        }
    }

    private final static void empty() {}
    private final static void dead() {
        String bar = "Bar";
    }

    private void doSomething() {
        long debut = System.currentTimeMillis();
        for(int i = 0; i < 10_000_000;i++)
            empty();
        System.out.println("Time : "+(System.currentTimeMillis() - debut) + " ms");
    }
}
