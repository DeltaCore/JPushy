package JPushy;

public class Launcher {
	/**
	 * 
	 * @author Marcel Benning
	 * 
	 */
	public static void main(String[] args){
		System.out.println(Game.name + " V" + Game.version + " is starting ...");
		Core core = new Core(args);
	}
	
}
