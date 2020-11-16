import java.util.*;
import java.lang.*;
public class MAFIA {
    public static Scanner sc=new Scanner(System.in);
    public interface Cloneable{
    }
    static class player{
        String category;
        int hp;
        int id;
        int status;
        player(){

        }
        player(String category,int hp,int id){
            this.category=category;
            this.hp=hp;
            this.id=id;
            this.status=1;
        }

        private String getCategory(){
            return this.category;
        }
        private int getHp() {
            return this.hp;
        }
        private int getId(){
            return this.id;
        }
        private int getStatus(){
            return this.status;
        }
        private void setStatus(int status){
            this.status=status;
        }
        private void setHp(int hp){
            this.hp=hp;
        }
        private void addhp(int hp){
            this.hp=this.hp+hp;
        }
        private void subhp(int hp){
            if(hp>this.hp){
                this.hp=0;
            }
            else {
            this.hp=this.hp-hp;}
        }
        private void simulator(){

        }
        public int random_player(player[] players,int n){
            int p;
            Random rand=new Random();
            p=rand.nextInt(n);

            if(players[p].getStatus()==1){
                return p+1;
            }
            else {
                return random_player(players,n);
            }
        }
        public void print_players(player[] players,int n){
            for(int i=0;i<n;i++){
                if(players[i].getCategory().equals("Mafia")){
                    int x=i+1;
                    System.out.print("Player["+x+"] ");
                }
            }
            System.out.print("were Mafias.");
            System.out.println();
            for(int i=0;i<n;i++){
                if(players[i].getCategory().equals("Detective")){
                    int x=i+1;
                    System.out.print("Player["+x+"] ");
                }
            }
            System.out.print("were Detectives.");
            System.out.println();
            for(int i=0;i<n;i++){
                if(players[i].getCategory().equals("Healer")){
                    int x=i+1;
                    System.out.print("Player["+x+"] ");
                }
            }
            System.out.print("were Healer.");
            System.out.println();
            for(int i=0;i<n;i++){
                if(players[i].getCategory().equals("Commoner")){
                    int x=i+1;
                    System.out.print("Player["+x+"] ");
                }
            }
            System.out.print("were Commoners.");
            System.out.println();
        }

    }

    static class mafia extends player implements Cloneable{
        player p;
        mafia(String cat,int hp,int id){
            p=new player(cat,hp,id);
        }
        mafia(){

        }
        private void print_clone(){
            System.out.println("");
        }
        public mafia clone(){
            try{
                mafia copy = (mafia) super.clone();
                return copy;

            } catch (CloneNotSupportedException e){
                return null;
            }

        }

        private void simulator(int num_mafia,int num_detective,int num_healer,int num_commoner,player[] players,int n,int id){
            int round=1;
            int player_count=n;
            int others=num_detective+num_healer+num_commoner;
            while (true){
                if(num_mafia==others){
                    System.out.println("Mafias have won");
                    //print players
                    super.print_players(players,n);
                    break;
                }
                else if(num_mafia==0){
                    System.out.println("Mafias have Lost.");
                    //print players
                    super.print_players(players,n);
                    break;
                }
                else if(players[id-1].getStatus()==-1){
                    System.out.println("Round "+round+":");
                    System.out.print(player_count+" players are remaining: ");
                    for(int i=0;i<n;i++){
                        if(players[i].getStatus()==1){
                            System.out.print("Player"+players[i].getId()+" , ");
                        }
                    }
                    System.out.println();
                    System.out.println("Detectives have chosen a player to test.");
                    System.out.println("Healers have chosen someone to heal.");
                    System.out.println("--End of actions--");
                    int detect=random_player(players,n);
                    int heal=random_player(players,n);
                    if(players[detect - 1].getCategory().equals("Mafia")){
                        System.out.println("Player"+detect+" has died.");
                        players[detect-1].setStatus(-1);
                        player_count=player_count-1;
                        num_mafia=num_mafia-1;
                    }
                    players[heal-1].addhp(500);
                    int vote_out=random_player(players,n);
                    System.out.println("Player["+vote_out+"] has been voted out.");
                    players[vote_out-1].setStatus(-1);
                    player_count=player_count-1;
                    if(players[vote_out-1].getCategory().equals("Mafia")){
                        num_mafia=num_mafia-1;
                    }
                    else {
                        others=others-1;
                    }
                }
                else{
                    System.out.println("Round "+round+":");
                    System.out.print(player_count+" players are remaining: ");
                    for(int i=0;i<n;i++){
                        if(players[i].getStatus()==1){
                            System.out.print("Player"+players[i].getId()+" , ");
                        }
                    }
                    System.out.println();
                    int target;
                    System.out.print("Choose a Target: ");
                    target=sc.nextInt();
                    System.out.println();
                    System.out.println("Detectives have chosen a player to test.");
                    System.out.println("Healers have chosen someone to heal.");
                    System.out.println("--End of actions--");
                    int detect=random_player(players,n);
                    int heal=random_player(players,n);
                    if(players[detect - 1].getCategory().equals("Mafia")){
                        System.out.println("Player"+detect+" has died.");
                        players[detect-1].setStatus(-1);
                        player_count=player_count-1;
                        num_mafia=num_mafia-1;
                    }
                    else if(target==heal){
                        int mafia_health=0;
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Mafia")){
                                mafia_health=mafia_health+players[i].getHp();
                            }
                        }
                        int X=players[target-1].getHp();
                        int damage=X/num_mafia;
                        //int residue=0;
                        if(mafia_health<X){
                            players[target-1].subhp(mafia_health);
                            players[target-1].addhp(500);
                            for(int i=0;i<n;i++){
                                if(players[i].getCategory().equals("Mafia")){
                                    if(players[i].getHp()<damage){
                                        players[i].setHp(0);
                                    }
                                    else{
                                        players[i].subhp(damage);
                                    }
                                }
                            }
                        }
                        else{
                            players[target-1].setHp(0);
                            players[target-1].setStatus(-1);
                            player_count=player_count-1;
                            others=others-1;
                            for(int i=0;i<n;i++){
                                if(players[i].getCategory().equals("Mafia")){
                                    if(players[i].getHp()<damage){
                                        players[i].setHp(0);
                                    }
                                    else{
                                        players[i].subhp(damage);
                                    }
                                }
                            }
                        }
                    }
                    else{
                        int X=players[target-1].getHp();
                        int damage=X/num_mafia;
                        players[heal-1].addhp(500);
                        players[target-1].setStatus(-1);
                        player_count=player_count-1;
                        others=others-1;
                        System.out.println("Player["+target+"] has Died.");
                        int mafia_health=0;
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Mafia")){
                                mafia_health=mafia_health+players[i].getHp();
                            }
                        }
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Mafia")){
                                if(players[i].getHp()<damage){
                                    players[i].setHp(0);
                                }
                                else{
                                    players[i].subhp(damage);
                                }
                            }
                        }
                    }
                    int vote=0;
                    System.out.println("Select a person to vote out: ");
                    vote=sc.nextInt();
                    int rand_vote=random_player(players,n);
                    if(rand_vote==vote){
                        System.out.println("-- Vote tied --");
                    }
                    else{
                        System.out.println("Player["+vote+"] has been voted out.");
                        players[vote-1].setStatus(-1);
                        player_count=player_count-1;
                        if(players[vote-1].getCategory().equals("Mafia")){
                            num_mafia=num_mafia-1;
                        }
                        else {
                            others=others-1;
                        }
                    }
                }
                System.out.println("End of Round "+round);
                round=round+1;
            }

        }
    }
    static class detective extends player implements Cloneable{
        player p;
        detective(String cat,int hp,int id){
            p=new player(cat,hp,id);
        }
        public detective clone(){
            try{
                detective copy = (detective) super.clone();
                return copy;

            } catch (CloneNotSupportedException e){
                return null;
            }

        }
        private void simulator(int num_mafia,int num_detective,int num_healer,int num_commoner,player[] players,int n,int id){
            int round=1;
            int player_count=n;
            int others=num_detective+num_healer+num_commoner;
            while(true){
                if(num_mafia==others){
                    System.out.println("Mafias have won");
                    //print players
                    super.print_players(players,n);
                    break;
                }
                else if(num_mafia==0){
                    System.out.println("Mafias have Lost.");
                    //print players
                    super.print_players(players,n);
                    break;
                }
                else{
                    System.out.println("Round "+round+":");
                    System.out.print(player_count+" players are remaining: ");
                    for(int i=0;i<n;i++){
                        if(players[i].getStatus()==1){
                            System.out.print("Player"+players[i].getId()+" , ");
                        }
                    }
                    System.out.println();
                    if(players[id-1].getStatus()==-1){
                        System.out.println("Mafias have chosen their target.");
                        System.out.println("Detectives have chosen the person to detect.");
                        System.out.println("Healers have chosen the person to heal.");
                        System.out.println("-- End of Actions --");
                        int mafia_target=random_player(players,n);
                        int heal_target=random_player(players,n);
                        if(mafia_target==heal_target){
                            int mafia_health=0;
                            for(int i=0;i<n;i++){
                                if(players[i].getCategory().equals("Mafia")){
                                    mafia_health=mafia_health+players[i].getHp();
                                }
                            }
                            int X=players[mafia_health-1].getHp()/num_mafia;
                            if(mafia_health<players[heal_target-1].getHp()){
                                players[heal_target-1].subhp(mafia_health);
                                players[heal_target-1].addhp(500);
                                for(int i=0;i<n;i++){
                                    if(players[i].getCategory().equals("Mafia")){
                                        if(players[i].getHp()>X){
                                            players[i].subhp(X);
                                        }
                                        else{
                                            players[i].setHp(0);
                                        }
                                    }
                                }
                                System.out.println("No One Died.");
                            }
                            else{
                                players[heal_target-1].setHp(500);
                                for(int i=0;i<n;i++){
                                    if(players[i].getCategory().equals("Mafia")){
                                        if(players[i].getHp()>X){
                                            players[i].subhp(X);
                                        }
                                        else{
                                            players[i].setHp(0);
                                        }
                                    }
                                }
                                System.out.println("No One Died.");
                            }
                        }
                        else{
                            players[heal_target-1].addhp(500);
                            System.out.println("Player["+mafia_target+"] has died.");
                            players[mafia_target-1].setStatus(-1);
                            player_count=player_count-1;
                            others=others-1;
                            if(players[mafia_target-1].getCategory().equals("Detective")){
                                num_detective=num_detective-1;
                            }
                            if(players[mafia_target-1].getId()==id){
                                players[id-1].setStatus(-1);
                                num_detective=num_detective-1;
                            }
                        }
                        if(num_detective!=0){
                            int detect=random_player(players,n);
                            if(players[detect-1].getCategory().equals("Mafia")){
                                System.out.println("Player["+detect+"] was voted out.");
                                players[detect-1].setStatus(-1);
                                player_count=player_count-1;
                                num_mafia=num_mafia-1;
                            }
                        }

                    }
                    else{
                        System.out.println("Mafias have chosen their target.");
                        int test_player;
                        boolean cond = false;
                        System.out.print("Choose a player to test: ");
                        test_player=sc.nextInt();
                        if(players[test_player-1].getCategory().equals("Detective")){
                            System.out.println("You cannot test a detective.");
                            System.out.print("Choose a player to test: ");
                            test_player=sc.nextInt();
                        }
                        else{
                            //check player
                            if(players[test_player-1].getCategory().equals("Mafia")){
                                System.out.println("Player["+test_player+"] is a Mafia");
                                cond=true;
                            }
                            else{
                                System.out.println("Player["+test_player+"] is not a Mafia");
                                cond=false;
                            }
                        }
                        System.out.println("Healers have chosen someone to heal.");
                        System.out.println("-- End of Actions --");
                        int mafia_target=random_player(players,n);
                        int heal_target=random_player(players,n);
                        if(mafia_target==heal_target){
                            int mafia_health=0;
                            for(int i=0;i<n;i++){
                                if(players[i].getCategory().equals("Mafia")){
                                    mafia_health=mafia_health+players[i].getHp();
                                }
                            }
                            int X=players[heal_target-1].getHp()/num_mafia;
                            if(mafia_health<players[heal_target-1].getHp()){
                                players[heal_target-1].subhp(mafia_health);
                                players[heal_target-1].addhp(500);
                                for(int i=0;i<n;i++){
                                    if(players[i].getCategory().equals("Mafia")){
                                        if(players[i].getHp()>X){
                                            players[i].subhp(X);
                                        }
                                        else{
                                            players[i].setHp(0);
                                        }
                                    }
                                }
                                System.out.println("No One Died.");
                            }
                            else{
                                players[heal_target-1].setHp(500);
                                for(int i=0;i<n;i++){
                                    if(players[i].getCategory().equals("Mafia")){
                                        if(players[i].getHp()>X){
                                            players[i].subhp(X);
                                        }
                                        else{
                                            players[i].setHp(0);
                                        }
                                    }
                                }
                                System.out.println("No One Died.");
                            }
                        }
                        else{
                            players[heal_target-1].addhp(500);
                            System.out.println("Player["+mafia_target+"] has died.");
                            players[mafia_target-1].setStatus(-1);
                            player_count=player_count-1;
                            others=others-1;
                            if(players[mafia_target-1].getCategory().equals("Detective")){
                                num_detective=num_detective-1;
                            }
                            if(players[mafia_target-1].getId()==id){
                                players[id-1].setStatus(-1);
                                num_detective=num_detective-1;
                            }
                        }
                        if(cond){
                            System.out.println("Player["+test_player+"] has been voted out.");
                            players[test_player-1].setStatus(-1);
                            player_count=player_count-1;
                            num_mafia=num_mafia-1;
                        }
                        int vote_out;
                        System.out.println("Select a person to vote out:");
                        vote_out=sc.nextInt();
                        int r=random_player(players,n);
                        if(r==vote_out){
                            System.out.println("-- Vote Tied --");
                        }
                        else {
                            System.out.println("Player["+vote_out+"] has been voted out");
                            player_count=player_count-1;
                            players[vote_out-1].setStatus(-1);
                            if(players[vote_out - 1].getCategory().equals("Mafia")){
                                num_mafia=num_mafia-1;
                            }
                            else {
                                others=others-1;
                            }
                            if(players[vote_out-1].getCategory().equals("Detective")){
                                num_detective=num_detective-1;
                            }
                            if(players[vote_out-1].getId()==id){
                                players[id-1].setStatus(-1);
                                num_detective=num_detective-1;
                            }
                        }
                    }
                }
                System.out.println("End of Round "+round);
                round=round+1;
            }
        }
    }
    static class healer extends player implements Cloneable{
        player p;
        healer(String cat,int hp,int id){
            p=new player(cat,hp,id);
        }
        public healer clone(){
            try{
                healer copy = (healer) super.clone();
                return copy;

            } catch (CloneNotSupportedException e){
                return null;
            }

        }
        private void simulator(int num_mafia,int num_detective,int num_healer,int num_commoner,player[] players,int n,int id){
            int round=1;
            int player_count=n;
            int others=num_detective+num_healer+num_commoner;
            while(true){
                if(num_mafia==others){
                    System.out.println("Mafias have won");
                    //print players
                    super.print_players(players,n);
                    break;
                }
                else if(num_mafia==0){
                    System.out.println("Mafias have Lost.");
                    //print players
                    super.print_players(players,n);
                    break;
                }
                else if(players[id-1].getStatus()==-1){
                    System.out.println("Mafias have chosen the target.");
                    System.out.println("Detectives have chosen a player to test.");
                    System.out.println("Healers have chosen the person to heal.");
                    int mafia_target=random_player(players,n);
                    int detect_target=random_player(players,n);

                        if(num_healer!=0){
                        int heal_target=random_player(players,n);
                            if(mafia_target==heal_target){
                                int mafia_health=0;
                                for(int i=0;i<n;i++){
                                    if(players[i].getCategory().equals("Mafia")){
                                        mafia_health=mafia_health+players[i].getHp();
                                    }
                                }
                                int X=players[mafia_health-1].getHp()/num_mafia;
                                if(mafia_health<players[heal_target-1].getHp()){
                                    players[heal_target-1].subhp(mafia_health);
                                    players[heal_target-1].addhp(500);
                                    for(int i=0;i<n;i++){
                                        if(players[i].getCategory().equals("Mafia")){
                                            if(players[i].getHp()>X){
                                                players[i].subhp(X);
                                            }
                                            else{
                                                players[i].setHp(0);
                                            }
                                        }
                                    }
                                    System.out.println("No One Died.");
                                }
                                else{
                                    players[heal_target-1].setHp(500);
                                    for(int i=0;i<n;i++){
                                        if(players[i].getCategory().equals("Mafia")){
                                            if(players[i].getHp()>X){
                                                players[i].subhp(X);
                                            }
                                            else{
                                                players[i].setHp(0);
                                            }
                                        }
                                    }
                                    System.out.println("No One Died.");
                                }
                            }
                        else {
                            players[heal_target-1].addhp(500);
                            players[mafia_target-1].setStatus(-1);
                            player_count=player_count-1;
                            others=others-1;
                            System.out.println("Player["+mafia_target+"] has died.");
                        }
                    }
                    else{
                        players[mafia_target-1].setStatus(-1);
                        player_count=player_count-1;
                        others=others-1;
                        System.out.println("Player["+mafia_target+"] died.");
                    }
                    if(players[detect_target-1].getCategory().equals("Mafia")){
                        System.out.println("Player["+detect_target+"] is a Mafia.");
                        System.out.println("Player["+detect_target+"] was voted out.");
                        players[detect_target-1].setStatus(-1);
                        player_count=player_count-1;
                        num_mafia=num_mafia-1;
                    }
                    else {
                        System.out.println("Player["+detect_target+"] is not a Mafia.");
                    }
                }
                else{
                    System.out.println("Mafias have chosen the target.");
                    System.out.println("Detectives have chosen a player to test.");
                    System.out.print("Choose the Player to heal: ");
                    int heal_target=sc.nextInt();
                    int mafia_target=random_player(players,n);
                    int detect_target=random_player(players,n);
                    System.out.println("-- End of Actions --");
                    if(mafia_target==heal_target){
                        int mafia_health=0;
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Mafia")){
                                mafia_health=mafia_health+players[i].getHp();
                            }
                        }
                        int X=players[heal_target-1].getHp()/num_mafia;
                        if(mafia_health<players[heal_target-1].getHp()){
                            players[heal_target-1].subhp(mafia_health);
                            players[heal_target-1].addhp(500);
                            for(int i=0;i<n;i++){
                                if(players[i].getCategory().equals("Mafia")){
                                    if(players[i].getHp()>X){
                                        players[i].subhp(X);
                                    }
                                    else{
                                        players[i].setHp(0);
                                    }
                                }
                            }
                            System.out.println("No One Died.");
                        }
                        else{
                            players[heal_target-1].setHp(500);
                            for(int i=0;i<n;i++){
                                if(players[i].getCategory().equals("Mafia")){
                                    if(players[i].getHp()>X){
                                        players[i].subhp(X);
                                    }
                                    else{
                                        players[i].setHp(0);
                                    }
                                }
                            }
                            System.out.println("No One Died.");
                        }
                    }
                    else {
                        System.out.println("Player["+mafia_target+"] has died.");
                        players[mafia_target-1].setStatus(-1);
                        player_count=player_count-1;
                        others=others-1;
                        players[heal_target-1].addhp(500);
                        if(players[mafia_target-1].getCategory().equals("Healer")){
                            num_healer=num_healer-1;
                        }
                    }

                    if(players[detect_target - 1].getCategory().equals("Mafia")){
                        System.out.println("Player["+detect_target+"] was voted out.");
                        players[detect_target-1].setStatus(-1);
                        player_count=player_count-1;
                        num_mafia=num_mafia-1;
                    }
                    int vote_out;
                    System.out.println("Select a person to vote out:");
                    vote_out=sc.nextInt();
                    int r=random_player(players,n);
                    if(r==vote_out){
                        System.out.println("-- Vote Tied --");
                    }
                    else {
                        System.out.println("Player["+vote_out+"] has been voted out");
                        player_count=player_count-1;
                        players[vote_out-1].setStatus(-1);
                        if(players[vote_out - 1].getCategory().equals("Mafia")){
                            num_mafia=num_mafia-1;
                        }
                        else {
                            others=others-1;
                        }
                        if(players[vote_out-1].getCategory().equals("Healer")){
                            num_healer=num_healer-1;
                        }
                        if(players[vote_out-1].getId()==id){
                            players[id-1].setStatus(-1);
                            num_healer=num_healer-1;
                        }
                    }
                }
                System.out.println("End of Round "+round);
                round=round+1;
            }
        }
    }
    static class commoner extends player implements Cloneable{
        player p;
        commoner(String cat,int hp,int id){
            p=new player(cat,hp,id);
        }
        public commoner clone(){
            try{
                commoner copy = (commoner) super.clone();
                return copy;

            } catch (CloneNotSupportedException e){
                return null;
            }

        }
        private void simulator(int num_mafia,int num_detective,int num_healer,int num_commoner,player[] players,int n,int id){
            int round=1;
            int player_count=n;
            int others=num_detective+num_healer+num_commoner;
            while(true){
                if(num_mafia==others){
                    System.out.println("Mafias have won");
                    //print players
                    super.print_players(players,n);
                    break;
                }
                else if(num_mafia==0){
                    System.out.println("Mafias have Lost.");
                    //print players
                    super.print_players(players,n);
                    break;
                }
                else if(players[id-1].getStatus()==-1){
                    System.out.println("Mafias have chosen their target.");
                    System.out.println("Detectives have chosen the player to detect.");
                    System.out.println("Healers have chosen the player to heal.");
                    System.out.println("--End of Actions--");
                    int mafia_target=random_player(players,n);
                    int heal_target=random_player(players,n);
                    int detect_target=random_player(players,n);
                    if(num_healer!=0){
                        if(mafia_target==heal_target){
                            int mafia_health=0;
                            for(int i=0;i<n;i++){
                                if(players[i].getCategory().equals("Mafia")){
                                    mafia_health=mafia_health+players[i].getHp();
                                }
                            }
                            int X=players[mafia_health-1].getHp()/num_mafia;
                            if(mafia_health<players[heal_target-1].getHp()){
                                players[heal_target-1].subhp(mafia_health);
                                players[heal_target-1].addhp(500);
                                for(int i=0;i<n;i++){
                                    if(players[i].getCategory().equals("Mafia")){
                                        if(players[i].getHp()>X){
                                            players[i].subhp(X);
                                        }
                                        else{
                                            players[i].setHp(0);
                                        }
                                    }
                                }
                                System.out.println("No One Died.");
                            }
                            else{
                                players[heal_target-1].setHp(500);
                                for(int i=0;i<n;i++){
                                    if(players[i].getCategory().equals("Mafia")){
                                        if(players[i].getHp()>X){
                                            players[i].subhp(X);
                                        }
                                        else{
                                            players[i].setHp(0);
                                        }
                                    }
                                }
                                System.out.println("No One Died.");
                            }
                        }
                        else {
                            players[heal_target-1].addhp(500);
                            players[mafia_target-1].setStatus(-1);
                            player_count=player_count-1;
                            others=others-1;
                            System.out.println("Player["+mafia_target+"] has died.");
                        }
                    }
                    else{
                        players[mafia_target-1].setStatus(-1);
                        player_count=player_count-1;
                        others=others-1;
                        System.out.println("Player["+mafia_target+"] died.");
                    }
                    if(players[detect_target-1].getCategory().equals("Mafia")){
                        System.out.println("Player["+detect_target+"] is a Mafia.");
                        System.out.println("Player["+detect_target+"] was voted out.");
                        players[detect_target-1].setStatus(-1);
                        player_count=player_count-1;
                        num_mafia=num_mafia-1;
                    }
                    else {
                        System.out.println("Player["+detect_target+"] is not a Mafia.");
                    }
                }
                else{
                    System.out.println("Mafias have chosen their target.");
                    System.out.println("Detectives have chosen the player to detect.");
                    System.out.println("Healers have chosen the player to heal.");
                    System.out.println("--End of Actions--");
                    int mafia_target=random_player(players,n);
                    int heal_target=random_player(players,n);
                    int detect_target=random_player(players,n);
                    if(mafia_target==heal_target){
                        if(mafia_target==heal_target){
                            int mafia_health=0;
                            for(int i=0;i<n;i++){
                                if(players[i].getCategory().equals("Mafia")){
                                    mafia_health=mafia_health+players[i].getHp();
                                }
                            }
                            int X=players[heal_target-1].getHp()/num_mafia;
                            if(mafia_health<players[heal_target-1].getHp()){
                                players[heal_target-1].subhp(mafia_health);
                                players[heal_target-1].addhp(500);
                                for(int i=0;i<n;i++){
                                    if(players[i].getCategory().equals("Mafia")){
                                        if(players[i].getHp()>X){
                                            players[i].subhp(X);
                                        }
                                        else{
                                            players[i].setHp(0);
                                        }
                                    }
                                }
                                System.out.println("No One Died.");
                            }
                            else{
                                players[heal_target-1].setHp(500);
                                for(int i=0;i<n;i++){
                                    if(players[i].getCategory().equals("Mafia")){
                                        if(players[i].getHp()>X){
                                            players[i].subhp(X);
                                        }
                                        else{
                                            players[i].setHp(0);
                                        }
                                    }
                                }
                                System.out.println("No One Died.");
                            }
                        }
                    }
                    else{
                        players[heal_target-1].addhp(500);
                        players[mafia_target-1].setStatus(-1);
                        player_count=player_count-1;
                        others=others-1;
                        System.out.println("Player["+mafia_target+"] has died.");
                        if(players[mafia_target-1].getCategory().equals("Commoner")){
                            num_commoner=num_commoner-1;
                        }
                    }
                    if(players[detect_target-1].getCategory().equals("Mafia")){
                        player_count=player_count-1;
                        num_mafia=num_mafia-1;
                    }
                    int vote_out;
                    System.out.println("Select a person to vote out:");
                    vote_out=sc.nextInt();
                    int r=random_player(players,n);
                    if(r==vote_out){
                        System.out.println("-- Vote Tied --");
                    }
                    else {
                        System.out.println("Player["+vote_out+"] has been voted out");
                        player_count=player_count-1;
                        players[vote_out-1].setStatus(-1);
                        if(players[vote_out - 1].getCategory().equals("Mafia")){
                            num_mafia=num_mafia-1;
                        }
                        else {
                            others=others-1;
                        }
                        if(players[vote_out-1].getCategory().equals("Commoner")){
                            num_commoner=num_commoner-1;
                        }
                        if(players[vote_out-1].getId()==id){
                            players[id-1].setStatus(-1);
                            num_commoner=num_commoner-1;
                        }
                    }
                }
                System.out.println("End of Round "+round);
                round=round+1;
            }
        }
    }

    public static int value(int ch,int range){
        Random rand=new Random();
        int x;
        if(ch==4){
            x=rand.nextInt(4);
            return x+1;
        }
        x=rand.nextInt(range);
        return x+1;
    }

    public static void refresh_id(int n,player[] players){
        for(int i=0;i<n/5;i++){
            players[i]=new player("Mafia",2500,i+1);
        }
        for(int i=n/5;i<2*(n/5);i++){
            players[i]=new player("Detective",800,i+1);
        }
        for(int i=2*(n/5);i<(2*(n/5)+Math.max(1,n/10));i++){
            players[i]=new player("Healer",800,i+1);
        }
        for(int i=(2*(n/5)+Math.max(1,n/10));i<n;i++){
            players[i]=new player("Commoner",1000,i+1);
        }
    }

    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        while (true){
            System.out.println("Welcome to Mafia");
            System.out.println("Enter Number of Players: ");
            int n=in.nextInt();
            if(n>=6) {
                System.out.println("Choose a Character");
                System.out.println("1) Mafia");
                System.out.println("2) Detective");
                System.out.println("3) Healer");
                System.out.println("4) Commoner");
                System.out.println("5) Assign Randomly");
                int num_mafia=n/5;
                int num_detective=n/5;
                int num_healer=Math.max(1,n/10);
                int num_commoner=n-(num_detective+num_healer+num_mafia);
                int choice = in.nextInt();
                player[] players=new player[n];
                for(int i=0;i<n;i++){
                    players[i]=new player();
                }
                refresh_id(n,players);
                int pos=0;
                if (choice == 1) {
                    for(int i=0;i<n;i++){
                        if(players[i].getCategory().equals("Mafia")){
                            pos=players[i].getId();
                            break;
                        }
                    }
                    System.out.println("You are Player"+pos);
                    System.out.println("You are a Mafia. Other Mafias are:");
                    for(int i=0;i<n;i++){
                        if(players[i].getCategory().equals("Mafia") && players[i].getId()!=pos){
                            System.out.print("Player["+players[i].getId()+"] ");
                        }
                    }
                    mafia m=new mafia("Mafia",players[pos-1].getHp(),players[pos-1].getId());
                    mafia copy=(mafia)m.clone();
                    m.simulator(num_mafia,num_detective,num_healer,num_commoner,players,n,pos);
                } else if (choice == 2) {
                    for(int i=0;i<n;i++){
                        if(players[i].getCategory().equals("Detective")){
                            pos=players[i].getId();
                            break;
                        }
                    }
                    System.out.println("You are Player"+pos);
                    System.out.println("You are a Detective. Other Detectives are:");
                    for(int i=0;i<n;i++){
                        if(players[i].getCategory().equals("Detective") && players[i].getId()!=pos){
                            System.out.print("Player["+players[i].getId()+"] ");
                        }
                    }
                    detective d=new detective("Detective",players[pos-1].getHp(),players[pos-1].getId());
                    detective copy=(detective)d.clone();
                    d.simulator(num_mafia,num_detective,num_healer,num_commoner,players,n,pos);
                } else if (choice == 3) {

                    for(int i=0;i<n;i++){
                        if(players[i].getCategory().equals("Healer")){
                            pos=players[i].getId();
                            break;
                        }
                    }
                    System.out.println("You are Player"+pos);
                    if(num_healer==1){
                        System.out.println("You are a Healer.");
                    }
                    else {
                        System.out.println("You are a Detective. Other Detectives are:");
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Healer") && players[i].getId()!=pos){
                                System.out.print("Player["+players[i].getId()+"] ");
                            }
                        }
                    }
                    healer h=new healer("Healer",players[pos-1].getHp(),players[pos-1].getId());
                    healer copy=(healer)h.clone();
                    h.simulator(num_mafia,num_detective,num_healer,num_commoner,players,n,pos);
                } else if (choice == 4) {

                    for(int i=0;i<n;i++){
                        if(players[i].getCategory().equals("Commoner")){
                            pos=players[i].getId();
                            break;
                        }
                    }
                    System.out.println("You are Player"+pos);
                    System.out.println("You are a Commoner.");
                    commoner c=new commoner("Commoner",players[pos-1].getHp(),players[pos-1].getId());
                    commoner copy=(commoner)c.clone();
                    c.simulator(num_mafia,num_detective,num_healer,num_commoner,players,n,pos);
                }
                else if (choice == 5) {
                    int cat=value(4,4);
                    if(cat==1){
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Mafia")){
                                pos=players[i].getId();
                                break;
                            }
                        }
                        System.out.println("You are Player"+pos);
                        System.out.println("You are a Mafia. Other Mafias are:");
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Mafia") && players[i].getId()!=pos){
                                System.out.print("Player["+players[i].getId()+"] ");
                            }
                        }
                        mafia m=new mafia("Mafia",players[pos-1].getHp(),players[pos-1].getId());
                        mafia copy=(mafia)m.clone();
                        m.simulator(num_mafia,num_detective,num_healer,num_commoner,players,n,pos);
                    }
                    else if(cat==2){
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Detective")){
                                pos=players[i].getId();
                                break;
                            }
                        }
                        System.out.println("You are Player"+pos);
                        System.out.println("You are a Detective. Other Detectives are:");
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Detective") && players[i].getId()!=pos){
                                System.out.print("Player["+players[i].getId()+"] ");
                            }
                        }
                        detective d=new detective("Detective",players[pos-1].getHp(),players[pos-1].getId());
                        detective copy=(detective)d.clone();
                        d.simulator(num_mafia,num_detective,num_healer,num_commoner,players,n,pos);
                    }
                    else if(cat==3){
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Healer")){
                                pos=players[i].getId();
                                break;
                            }
                        }
                        System.out.println("You are Player"+pos);
                        if(num_healer==1){
                            System.out.println("You are a Healer.");
                        }
                        else {
                            System.out.println("You are a Healer. Other Healers are:");
                            for(int i=0;i<n;i++){
                                if(players[i].getCategory().equals("Healer") && players[i].getId()!=pos){
                                    System.out.print("Player["+players[i].getId()+"] ");
                                }
                            }
                        }
                        healer h=new healer("Healer",players[pos-1].getHp(),players[pos-1].getId());
                        healer copy=(healer)h.clone();
                        h.simulator(num_mafia,num_detective,num_healer,num_commoner,players,n,pos);
                    }
                    else if(cat==4){
                        for(int i=0;i<n;i++){
                            if(players[i].getCategory().equals("Commoner")){
                                pos=players[i].getId();
                                break;
                            }
                        }
                        System.out.println("You are Player"+pos);
                        System.out.println("You are a Commoner.");
                        commoner c=new commoner("Commoner",players[pos-1].getHp(),players[pos-1].getId());
                        commoner copy=(commoner)c.clone();
                        c.simulator(num_mafia,num_detective,num_healer,num_commoner,players,n,pos);
                    }
                } else {
                    System.out.println("Invalid Choice !!");
                    System.out.println("Enter Choice Again: ");
                }
            }
            else {
                System.out.println("Number of Players must be >=6");
                System.out.println("-----------------------------");
                System.out.println();
            }
        }
    }
}

