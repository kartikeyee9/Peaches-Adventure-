
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
// Done by: Ikeobi Chigozie Daniel
// Player that find pits
public class PitFinder extends Player {

    protected HashMap<Player, HashSet<Location>> revealedLocations; // revealed locations to players
    protected HashSet<Location> pendingRevealHome; // locations pending to be revealed to home
    protected HashSet<Location> allPits; // all piuts found

    public PitFinder(World w, String name, Location location, List<Peach> peaches, int health, RGB rgb) {
	super(w, name, location, peaches, health, rgb);
	revealedLocations = new HashMap<>();
	pendingRevealHome = new HashSet<>();
	allPits = new HashSet<>();
    }

    @Override
    public void play() {
	// if low health
	if (this.health <= 50) {
	    // eat a peach
	    if (!peaches.isEmpty()) {
		System.out.println(getName() + " ate a peach");
		Peach toEat = getPeach();
		this.health += toEat.bad ? -toEat.ripeness : toEat.ripeness;
		System.out.println(getName() + " ate a peach. New heath: " + health);
	    }
	}

	if (health < 10) {
	    getHelp();
	    return;
	}
	
	// interact with each player
	for (Player p : location.getPlayers()) {
	    interact(p);
	}
	
	
	

	// if peach pit found and not discovered before
	if (location instanceof PeachPit && !allPits.contains(location)) {
	    allPits.add(location);
	    pendingRevealHome.add(location);
	} else if (location instanceof Home && !pendingRevealHome.isEmpty()) { // if home and there are location to reveal
	    ((Home)location).pits.addAll(pendingRevealHome);
	    allPits.addAll(pendingRevealHome);
	    pendingRevealHome.clear();
	    System.out.println(getName()+ " revealed locations of Peach Pits to home location");
	} 
	
	if (health < 30) {
	    drop(10);
	}
		
	
	// move randomly if no new pit found
	if(pendingRevealHome.isEmpty()){
	    move(getPossibleMoves());
	} else {
	    // move towards home if a new pit is found
	    move(world.getHome().getPosition());
	}

	

    }

    // drop peaches to reach the desired limit
    private void drop(int maxLimit) {

	int dropped = 0;

	while (peaches.size() > maxLimit) {
	    if(location instanceof Home)
		((Home)location).addPeach(this.getPeach(), this);
	    else
		location.addPeach(this.getPeach());
	    dropped++;
	}

	System.out.println(getName() + " dropped " + dropped + " peaches");

    }
    
    
    // helper method to make a random move to reach towrd the position
    private void move(Position pos) {
	ArrayList<Integer> possibleMoves = getPossibleMoves(pos);
	move(possibleMoves);
    }

    // helper method to make a move in one of the randomly chosen possible moves
    private void move(ArrayList<Integer> possibleMoves) {
	int move = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
	super.move(move);
    }

    // get valid possible moves by the player
    private ArrayList<Integer> getPossibleMoves() {
	ArrayList<Integer> moves = new ArrayList<>();

	if (location.getPosition().getX() > 0)
	    moves.add(Directions.UP);
	if (location.getPosition().getX() < world.locations.length - 1)
	    moves.add(Directions.DOWN);
	if (location.getPosition().getY() > 0)
	    moves.add(Directions.LEFT);
	if (location.getPosition().getY() < world.locations[0].length - 1)
	    moves.add(Directions.RIGHT);

	return moves;
    }

    // get valid possible moves to reach the desired location
    private ArrayList<Integer> getPossibleMoves(Position toPosition) {
	Location current = this.location;
	int rowDiff = toPosition.getX() - current.getPosition().getX();
	int colDiff = toPosition.getY() - current.getPosition().getY();

	ArrayList<Integer> moves = new ArrayList<>();

	if (rowDiff < 0)
	    moves.add(Directions.UP);
	else if (rowDiff > 0)
	    moves.add(Directions.DOWN);

	if (colDiff < 0)
	    moves.add(Directions.LEFT);
	else if (colDiff > 0)
	    moves.add(Directions.RIGHT);

	return moves;
    }
    
    @Override
    public void interact(Player p) {
	// if interacting for first time
	if(!revealedLocations.containsKey(p))
	    revealedLocations.put(p, new HashSet<>());
	    
	
	// if peach hunter and has atleast 5 peaches and there are some location to reveal
	if(p instanceof PeachHunter && p.peaches.size() >= 5 && allPits.size() != revealedLocations.get(p).size()) {
	    // take 5 peaches
	    for (int i = 0; i < 5; i++) {
		peaches.add(p.getPeach());
	    }
	    
	    
	    p.interact(this);
	    
	    System.out.println(getName()+ " revealed a Peach Pit location to " + p.getName() + " for 5 peaches");
	}
    }
    
    
    // get unrevealed location for a player
    public Location getUnrevealedLocation(Player p){
	HashSet<Location> alreadyRevealed = revealedLocations.get(p);
	for (Location location : allPits) {
	    if(!alreadyRevealed.contains(location)) {
		revealedLocations.get(p).add(location);
		return location;
	    }
	}
	
	return null;
    }

}
