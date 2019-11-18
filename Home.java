import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
// Done by: Fiyifoluwa Aboderin
// Home Location
public class Home extends Location{
    
    private HashMap<Player, Integer> peachesBrought; // peaches bought by each playr
    private HashMap<Player, Player> helpers; // current helpers in game 
    protected HashSet<Location> pits; // pits
    private World world;

    public Home(Position p, String description, List<Player> people, List<Peach> peaches, World w) {
	super(p, description, people, peaches);
	peachesBrought = new HashMap<>();
	helpers = new HashMap<>();
	this.world = w;
	pits = new HashSet<>();
    }
    
    
    // add a peach and record who brought that
    public void addPeach(Peach p, Player player) {
        super.addPeach(p);
        if(!peachesBrought.containsKey(p))
            peachesBrought.put(player, 0);
        
        peachesBrought.put(player, peachesBrought.get(player) + 1);

    }
    
    
    @Override
    public void callForHelp(Player p, Location location) {
	// if no peaches at home, dont send help
	if(numberOfPeaches() == 0)
	    return;
	
	// if one helper has already been dispatched
	if(helpers.containsKey(p))
	    return;
	
	
	// generate random number of peaches to send
	int numPeaches = new Random().nextInt(Math.min(10, numberOfPeaches()));
	if(numPeaches == 0)
	    numPeaches++;
	
	
	// add peaches to list
	List<Peach> peaches = new ArrayList<>();
	for (int i = 0; i < numPeaches; i++) {
	    peaches.add(getPeach());
	}
	
	
	// create helper and send
	Helper helper= new Helper(world, "Helper", location, peaches, 100, RGB.GREEN, p);
	helpers.put(p, helper);
	world.addPlayer(helper);
	enter(helper);
    }
    
    @Override
    public void enter(Player p) {
        super.enter(p);
        
        // if helper has reached home back after helping
        if(p instanceof Helper && ((Helper) p).helped) {
            helpers.remove(((Helper)p).playerToHelp);
        }
    }

   
    
}
