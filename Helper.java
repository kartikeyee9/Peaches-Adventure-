import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// Done by: Fiyifoluwa Aboderin
// Helper Player
public class Helper extends Player {

    protected Player playerToHelp; // player to help
    protected boolean helped; // whether helper has helped or not

    // constructor
    public Helper(World w, String name, Location location, List<Peach> peaches, int health, RGB rgb,
	    Player playerToHelp) {
	super(w, name, location, peaches, health, rgb);
	this.playerToHelp = playerToHelp;
    }

    @Override
    public void play() {
	// if helped
	if (!helped) {
	    if(this.location == playerToHelp.location) //if reached player location
		interact(playerToHelp);
	     else
		move(playerToHelp.getLocation().getPosition()); // move
	} else if (helped && this.location != world.getHome()) { // if helped and helper hasnt reached home
	    move(world.getHome().getPosition()); // go to home
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

    @Override
    public void interact(Player p) {
	if (p == playerToHelp) { // if player to help is found
	    // give peaches
	    int given = this.peaches.size();
	    p.peaches.addAll(this.peaches);
	    this.peaches.clear();
	    System.out.println(getName()+" gave "+given+" peaches to "+ playerToHelp);
	    helped = true;
	}
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

}
