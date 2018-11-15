public class Test {

	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.out.println("Hello world!");
		}
		else if ((args[0].equals("decrypt") || args[0].equals("d"))) {
			if (args.length == 2) {
				System.out.println(decript(args[1]));
			}
			else if (args.length == 3) {
				System.out.println(decript(args[1], args[2]));
			}
			else {
				System.out.println(String.format("Invalid parameters: %s", String.join(",", args)));
			}
		}
		else if ((args[0].equals("encrypt") || args[0].equals("e")) && args.length == 2) {
			System.out.println(encrypt(args[1]));
		}
		else {
			System.out.println(String.format("Invalid parameters: %s", String.join(",", args)));
		}
	}
	
	private static String decript(String value) {
		return decript(value, null);
	}
	
	private static String decript(String value, String key) {
		if ("help".equals(value)) {
			return "select originalcity from system";
		}
		if ("city".equals(key)) {
			key = "Sacramento";
		}
		return new Tools.Encryption(key).decryptString(value);
	}
	
	private static String encrypt(String value) {
		return new Tools.Encryption().encryptString(value);
	}


}
