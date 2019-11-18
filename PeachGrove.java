import java.util.HashMap;
import java.util.List;

//Done by: Harleen Pabla
// Represents a peach grove
public class PeachGrove extends Location {

    // constants 
    private static final double STING_PROB = 0.5;
    private static final int HEALTH_LOSS_PER_STING = 5;

    // records number of entries of each player
    private HashMap<Player, Integer> entryCount;

    
    
    public PeachGrove(Position p, List<Player> people, List<Peach> peaches) {
	super(p, "Peach Grove", people, peaches);
	entryCount = new HashMap<>();
	
    }

    

    @Override
    public void enter(Player p) {
	// enter the location
	super.enter(p);
	
	// add the entry t map if not present
	if(!entryCount.containsKey(p))
	    entryCount.put(p, 0);
	
	// Increment entry count
	entryCount.put(p, entryCount.get(p) + 1);

	
	// sting
	int nEntry = entryCount.get(p);
	for (int i = 0; i < nEntry; i++) {
	    if (Math.random() <= STING_PROB) {
		p.setHealth(p.getHealth() - HEALTH_LOSS_PER_STING);
		System.out.println("Bee has stung " + p.getName() + " at " + position + " to reduce health to "+p.getHealth());
	    }
	}

    }

}
