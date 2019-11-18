
import java.util.ArrayList;
import java.util.Random;

// Done by: Fiyifoluwa Aboderin
//Home and Helper demo
// Tester to check how help is dispatched and reach the player
public class PeachesGame4 {
    public static void main(String[] args) {
	World w = new World();
	
	w.locations[0][0] = new Home(new Position(0, 0), "Home",  new ArrayList<>(), createPeaches(50), w);
	w.home = w.locations[0][0];
	
	// create peach hunter with low health
	Player peachHunter = new PeachHunter(w, "Peach Hunter", w.locations[1][1], new ArrayList<>(), 9, RGB.BLUE) ;
	w.addPlayer(peachHunter);
	
	
	
	// while peach hunter has low health
	while(peachHunter.health == 9) {
	    ArrayList<Player> players = new ArrayList<>(w.getPlayers());
	    for (Player p : players) {
		p.play();
	    }
	    
	    System.out.println("***************************************************");

	}

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