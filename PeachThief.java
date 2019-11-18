import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// Done by: Yacin Ismail
// Represents the Peach Thief
public class PeachThief extends Player {

	//Create a PeachThief Object
	public PeachThief(World w, String name, Location location, List<Peach> peaches, int health, RGB rgb) {
		super(w, name, location, peaches, health, rgb);
	}

	@Override
	public void play() {

		// if low health
		if (this.health <= 50){

			// eat a peach

			if (!peaches.isEmpty()) {
				System.out.println(getName() + " ate a peach");
				Peach toEat = getPeach();

				//If the peach is bad then remove health
				if(toEat.bad){
					this.health+= -toEat.ripeness;
				}

				//else add heath
				else{
					this.health+=toEat.ripeness;
				}
				System.out.println(getName() + " ate a peach. New heath: " + health);
			}
		}

		// if health is too low, call for help and wait here and don't move
		if (this.health < 10) {
			getHelp();
			System.out.println(getName() + " has called for help");
			return;
		}

		// this interact with each player
		for (Player p : location.getPlayers()) {
			interact(p);
		}

		// if reached at a peach grove
		if (location.numberOfPeaches() > 0 || peaches.size() >= getMaxLimit()) {

			// get max limit of peaches player can hold
			int maxLimit = getMaxLimit();

			// if cannot take more, then drop
			if (peaches.size() > maxLimit)
				drop(maxLimit);
			else // else take
				take(maxLimit);
		} else {
			move(getPossibleMoves());
		}

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

	// get max limit of peaches the player can hold based on his health
	private int getMaxLimit() {
		if(health<=0){
			return 0;
		}
		else if(health <50){
			return 25;
		}
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

		System.out.println(getName() + " took " + taken + " peaches");
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

	@Override
	public void interact(Player p) {
		// count stolen
		int stolen = 0;

		// first peach or not
		boolean first = true;
		while(p.peaches.size() > 0) {

			double rand = Math.random();

			if(rand <= 0.75) { // steal successful

				// get peach
				Peach peach = p.getPeach();
				if(first)  // first is eaten
					if(peach.bad){
						this.health+= -peach.ripeness;
					}

					//else add heath
					else{
						this.health+=peach.ripeness;
					}
				else
					peaches.add(peach);


				first = false;

				stolen++;
			}
			else
				break;
		}

		if(stolen > 0)
			System.out.println("Peach Thief stole "+stolen+" peaches from " + p.getName());
	}

}