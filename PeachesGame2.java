import java.util.ArrayList;
import java.util.Random;
// Done by: Yacin Ismail
//BearsDen Demo
public class PeachesGame2 {

    public static void main(String[] args) {
	World w = new World();
	w.locations[0][0] = new Home(new Position(0, 0), "Home", new ArrayList<>(), createPeaches(50), w);
	w.home = w.locations[0][0];

	
	// create den
	w.locations[2][2] = new BearsDen(new Position(2, 2), "Bears Den", new ArrayList<>(), new ArrayList<>());
	
	// create a player
	PeachHunter peachHunter = new PeachHunter(w, "Peach Hunter", w.locations[1][1], createPeaches(1), 100, RGB.GREEN);
	w.addPlayer(peachHunter);
	
	// while peacher hunter doesnt fall in bears den
	while(peachHunter.location != w.locations[2][2]) {
	    peachHunter.play();
	}
	
	
    }
    
    private static ArrayList<Peach> createPeaches(int n) {
   	Random random = new Random();

   	ArrayList<Peach> peaches = new ArrayList<>();
   	for (int i = 0; i < n; i++) {
   	    peaches.add(new Peach(random.nextInt(1 + 11)));
   	}

   	return peaches;
       }
}
