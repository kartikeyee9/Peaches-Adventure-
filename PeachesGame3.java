import java.util.ArrayList;
import java.util.Random;
// Done by: Yacin Ismail
//PeachThief demo
// Test how thief steals peaches
public class PeachesGame3 {

    public static void main(String[] args) {
	World w = new World();
	w.locations[0][0] = new Home(new Position(0, 0), "Home", new ArrayList<>(), createPeaches(50), w);
	w.home = w.locations[0][0];
	
	//create player and thief ant same place
	w.addPlayer(new PeachHunter(w, "Peach Hunter",  w.locations[1][1], createPeaches(100), 100, RGB.BLUE));
	PeachThief thief = new PeachThief(w, "Peach Theif", w.locations[1][1], new ArrayList<>(), 1000, RGB.BLUE);
	w.addPlayer(thief);
	
	// play thief
	// note that in some runs, thief may fail to steal due to random probability
	thief.play();
    }
    
    private static ArrayList<Peach> createPeaches(int n) {
	Random random = new Random();
	
	ArrayList<Peach> peaches = new ArrayList<>();
	for (int i = 0; i < n; i++) {
	    peaches.add(new Peach(random.nextInt(1+ 11)));
	}
	
	return peaches;
    }
}
