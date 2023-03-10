import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Main{
    public static void main(String [] args){
        StartupBust gameOne = new StartupBust();
        gameOne.runGame();




    }
}

class StartupBust{

    Startup sOne = new Startup();
    Startup sTwo = new Startup();
    Startup sThree = new Startup();

    Helper helper = new Helper();

    int guessCount = 0;

    void runGame(){
        System.out.println("Let's sink some Startups!");

        sOne.setName("Theranos");
        sTwo.setName("JuiceIt");
        sThree.setName("Hike");

        sOne.setLocation(helper.giveLocation());
        sTwo.setLocation(helper.giveLocation());
        sThree.setLocation(helper.giveLocation());
        System.out.println(helper.getOccupied());

        while(!(helper.getOccupied().isEmpty())){
            int g = helper.input();
            guessCount += 1;
            System.out.println();
            sOne.checkYourself(g, helper.getOccupied(), helper);
            sTwo.checkYourself(g, helper.getOccupied(), helper);
            sThree.checkYourself(g, helper.getOccupied(), helper);
            System.out.println();
        }
        System.out.println("\u001B[34m" + "All Startups are dead!" + "\u001B[0m");
        System.out.println("\u001B[35m" + "You took " + guessCount + " guesses" + "\u001B[0m");
    }
}

class Startup{
    private String name;
    private ArrayList<Integer> location = new ArrayList<>();
    private boolean dead = false;
    void setName(String aName){
        name = aName;
    }
    void setLocation(ArrayList<Integer> aLocation){
        location = aLocation;
    }
    void checkYourself(int guess, ArrayList<Integer> occupied, Helper helper){
        if(dead){
            return;
        }
        if(location.contains(guess)){
            helper.remove(guess);
            location.remove(location.indexOf(guess));
            if(location.isEmpty()){
                System.out.println("\u001B[31m" +"It's a kill! " + "You sunk " + name+"\u001B[0m");
                dead = true;
                return;
            }
            System.out.println("\u001B[33m"+name + " got " + "Hit"+"\u001B[0m");
            return;
        }
        System.out.println("\u001B[36m" +"You missed " + name+"\u001B[0m");
    }
}

class Helper{
    private final ArrayList<Integer> occupied = new ArrayList<>();
    ArrayList<Integer> getOccupied(){return occupied;}
    int input(){
        Scanner in = new Scanner(System.in);
        System.out.print("\u001B[32m" + "Enter a guess : " + "\u001B[0m");
        String a = in.next();
        return ((int)(a.charAt(0)) - 97) * 7 + ((int)(a.charAt(1)) -48);
    }
    void remove(int g){
        occupied.remove(occupied.indexOf(g));
    }

    ArrayList<Integer> giveLocation(){
        ArrayList<Integer> ans = new ArrayList<>();
        while(true){
            final int a = (int)(Math.random() * 49);
            if(!occupied.contains(a)){
                ans.add(a);
                ArrayList<Integer> rum = new ArrayList<>();
                rum.add(a + 2);
                rum.add(a - 2);
                rum.add(a + 14);
                rum.add(a - 14);
                for(int i = 0; i < 4; i ++){
                    if(occupied.contains(rum.get(i))){
                        rum.set(i, -2);
                    }
                    if(rum.get(i) < 0 || rum.get(i) > 48){
                        rum.set(i, -2);
                    }
                    if(rum.get(i) == a + 2 || rum.get(i) == a - 2){
                        if(rum.get(i) / 7 != a / 7){
                            rum.set(i, -2);
                        }
                    }
                }
                while(rum.contains(-2)){
                    rum.remove(rum.indexOf(-2));
                }
                if(!(rum.isEmpty())){
                    int x = rum.get((int)(Math.random() * rum.size()));
                    if (x == a + 2 && !(occupied.contains(a + 1))) {
                        ans.add(a + 1);
                        ans.add(x);
                        occupied.addAll(ans);
                        return ans;
                    }
                    if (x == a - 2 && !(occupied.contains(a - 1))) {
                        ans.add(a - 1);
                        ans.add(x);
                        occupied.addAll(ans);
                        return ans;
                    }
                    if (x == a + 14 && !(occupied.contains(a + 7))) {
                        ans.add(a + 7);
                        ans.add(x);
                        occupied.addAll(ans);
                        return ans;
                    }
                    if (x == a -14 && !(occupied.contains(a - 7))) {
                        ans.add(a - 7);
                        ans.add(x);
                        occupied.addAll(ans);
                        return ans;
                    }
                }
            }
        }
    }
}