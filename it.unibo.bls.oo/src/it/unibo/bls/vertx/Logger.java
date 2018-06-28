package it.unibo.bls.vertx;

public class Logger {
	
	private static boolean active = false;
	
	public static void activate() {
		active = true;
	}
	
	public static void deactivate() {
		active = false;
	}

	public static void log(final Class<?> who, final String msg) {
		if(active) {
			System.out.println("[" + who.getSimpleName() + "] " + msg);
		}
	}
}
