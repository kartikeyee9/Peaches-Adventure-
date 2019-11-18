import java.util.HashMap;
import java.util.List;
// Done byl: Ikeobi Chigozie Daniel
// A peach pit
public class PeachPit extends Location{
    
    // count of each of fallen players
    private HashMap<Player, Integer> fallenPlayers;
    private Home home; // home location

    public PeachPit(Position p, String description, List<Player> people, List<Peach> peaches, Home home) {
	super(p, description, people, peaches);
	this.home = home;
	fallenPlayers = new HashMap<>();
    }
    
    @Override
    public void enter(Player p) {
        super.enter(p);
        
        // if layer has fallen for first time
        if(!fallenPlayers.containsKey(p))
            fallenPlayers.put(p, 0);
        
        // increment count
        fallenPlayers.put(p, fallenPlayers.get(p) + 1);
        
        // reduce health by half
        p.setHealth(p.getHealth() / 2);
        
        
        // if more than two times, send player home
        if(fallenPlayers.get(p) >= 2) {
            exit(p);
            home.enter(p);
        }
        
    }
    

}
