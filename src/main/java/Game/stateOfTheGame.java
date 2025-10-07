package Game;

public class stateOfTheGame {
    private boolean the_game_is_on;
    public boolean isThe_game_on(){
        return the_game_is_on;
    }
    public void set_the_game_is_on(){
        this.the_game_is_on = true;
    }
    public void unset_the_game_is_on(){
        this.the_game_is_on = false;
    }
}
