import java.util.HashSet;
import java.util.List;
// Done by: Yacin Ismail
// Bears den
public class BearsDen extends Location{

    // players from whom bear has taken peaches
    private HashSet<Player> playersLeftAlone;

    //Creating a bear/ Bear constructor
    public BearsDen(Position p, String description, List<Player> people, List<Peach> peaches) {
        super(p, description, people, peaches);
        playersLeftAlone = new HashSet<>();
    }

    //@Override
    public void enter(Player p) {
        super.enter(p);

        // if player has not given 2 peaches before
        if(!playersLeftAlone.contains(p)) {

            // if player has two peaches
            if(p.peaches.size() >=2 ) {

                // taken and left alone
                playersLeftAlone.add(p);
                p.peaches.remove(0);
                p.peaches.remove(0);

                System.out.println("Bear took two peaches from "+p.getName());
            }
            else {
                // attacks
                System.out.println("Bear attacked "+p.getName());
                p.setHealth(p.getHealth() - 25);
            }
        }
    }







}