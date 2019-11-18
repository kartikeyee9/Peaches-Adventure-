import java.util.ArrayList;
import java.util.Random;

// Done by: Harleen Pabla
public class PeachesGame{
  public static void main(String[] args){
    World w = new World();

    w.locations[2][2] = new PeachGrove(new Position(2, 2), new ArrayList<>(), createPeaches(400));
    w.locations[2][0] = new PeachGrove(new Position(2, 0), new ArrayList<>(), createPeaches(200));
    w.addPlayer(new PeachHunter(w, "Peach Hunter 1", w.getHome(), new ArrayList<>(), 50, RGB.BLUE));

    while (w.getHome().numberOfPeaches() < 500) {
      // iterate over all locations in the world
      for (Player p : w.getPlayers()) {
        p.play();
      }

      System.out.println("***************************************************");
      System.out.println("Peaches at home: "+w.getHome().numberOfPeaches());

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