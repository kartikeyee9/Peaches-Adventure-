import java.util.ArrayList;
import java.util.Random;
// Done by: Ikeobi Chigozie Daniel
// Demonstrates how Pit Finder and Peach Hunter interacts
public class PeachesGame5 {

    public static void main(String[] args) {
	World w = new World();
	w.locations[0][0] = new Home(new Position(0, 0), "Home", new ArrayList<>(), createPeaches(50), w);
	w.home = w.locations[0][0];

	w.locations[2][2] = new PeachPit(new Position(2, 2), "Peach Pit", new ArrayList<>(), new ArrayList<>(),
		(Home) w.getHome());

	PitFinder finder = new PitFinder(w, "Pit Finder", w.locations[0][1], new ArrayList<>(), 100, RGB.CYAN);
	w.addPlayer(finder);

	while (finder.allPits.isEmpty()) {
	    finder.play();
	    System.out.println("******************************************");
	}

	PeachHunter peachHunter = new PeachHunter(w, "Peach Hunter 1", finder.getLocation(), createPeaches(100), 100,
		RGB.BLUE);
	w.addPlayer(peachHunter);

	System.out.println(peachHunter.location);
	System.out.println(finder.location);

	finder.play();

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
