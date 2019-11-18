import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
// Done by: Harleen Pabla
// Represents the Peach Hunter
public class PeachHunter extends Player {

    // position of groves remembered by Peach Hunter
    protected Set<Position> groves;
    protected HashSet<Location> pits = new HashSet<>();

    public PeachHunter(World w, String name, Location location, List<Peach> peaches, int health, RGB rgb) {
	super(w, name, location, peaches, health, rgb);
	groves = new HashSet<>(); // create empty set
	pits = new HashSet<>();
    }

   

    @Override
    public void play() {

	// if low health
	if (this.health <= 50) {
	    // System.out.println(getName() + " has low health: " + health);

	    // eat a peach
	    if (!peaches.isEmpty()) {
		Peach toEat = getPeach();
		this.health += toEat.bad ? -toEat.ripeness : toEat.ripeness;
		System.out.println(getName() + " ate a peach. New heath: " + health);
	    }
	}

	// if health is too low, call for help and wait here and dont move
	if (this.health < 10) {
	    getHelp();
	    //System.out.println(getName() + " has called for help");
	    return;
	}

	// if player has more than 50 peaches, move towards home
	if (this.peaches.size() > 50) {

	    // if home reached
	    if (this.location == world.getHome()) {
		drop(0); // drop until no peaches are with the player
	    } else {
		move(world.getHome().getPosition());
	    }
	} else {
	    // if reached at a peach grove
	    if (location instanceof PeachGrove && location.numberOfPeaches() > 0) {

		// try to save location, if saved that means new grove found
		if (groves.add(location.getPosition()))
		    System.out.println(getName() + " found a  Peach Grove at " + location.getPosition());

		// get max limit of peaches player can hold
		int maxLimit = getMaxLimit();

		// if cannot take more, then drop
		if (peaches.size() > maxLimit)
		    drop(maxLimit);
		else // else take
		    take(maxLimit);
	    } else {
		// if no grove found yet
		if (groves.isEmpty()) {
		    // move randomly

		    move(getPossibleMoves());
		} else {
		    // move towards target grove
		    Position target = groves.iterator().next();
		    move(groves.iterator().next());
		}
	    }

	}

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

    // get max limit of peaches the player can hold based on his health
    private int getMaxLimit() {
	if (health <= 0)
	    return 0;
	else if (health < 50)
	    return 25;
	else
	    return 100;
    }

    // take peaches
    private void take(int maxLimit) {
	int taken = 0;

	// take peaches until not possible
	while (peaches.size() < maxLimit && location.numberOfPeaches() != 0) {
	    this.peaches.add(location.getPeach());
	    taken++;
	}

	// if peach grove is empty now
	if (location.numberOfPeaches() == 0) {
	    groves.remove(location.getPosition());

	}

	System.out.println(getName() + " took " + taken + " peaches");
    }

    @Override
    public void interact(Player p) {
	if (p instanceof PitFinder) {
	    PitFinder pitFinder = (PitFinder) p;
	    pits.add(pitFinder.getUnrevealedLocation(this));
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

}
