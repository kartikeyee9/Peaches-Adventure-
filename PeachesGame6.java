import java.util.ArrayList;
import java.util.Random;
// Done by: Ikeobi Chigozie Daniel
// Demos who a Pit Finder finds pit and report back to home
public class PeachesGame6 {
    
    
    public static void main(String[] args) throws InterruptedException {
	World w = new World();
	w.locations[0][0] = new Home(new Position(0, 0), "Home",  new ArrayList<>(), createPeaches(50), w);
	w.home = w.locations[0][0];
	
	w.locations[2][2] = new PeachPit(new Position(2, 2), "Peach Pit", new ArrayList<>(), new ArrayList<>(), (Home) w.getHome());
	
	
	PitFinder finder = new PitFinder(w, "Pit Finder", w.locations[0][1], new ArrayList<>(), 100, RGB.CYAN);
	w.addPlayer(finder);
	
	
	while(finder.pendingRevealHome.isEmpty()) {
	    finder.play();
	    System.out.println("******************************************");
	    
	}
	
	
	
	while(!finder.pendingRevealHome.isEmpty()) {
	    finder.play();
	    System.out.println("******************************************");
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
