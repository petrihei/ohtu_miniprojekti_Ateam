package tekstikayttis;
import java.util.Scanner;


/**
 * Created by hanna-leena on 17/11/17.
 */
public class KonsoliIO implements IO{
    private Scanner lukija;

    public KonsoliIO(){
        this.lukija = new Scanner(System.in);
    }

    @Override
    public String nextLine() {
        String s = lukija.nextLine();
        return s;
    }

    @Override
    public void print(String m) {
        System.out.println(m);
    }
}
