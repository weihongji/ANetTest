public class Test {

	public static void main(String[] args) {
		if (args == null || args.length == 0 || getParam(args[0]).equals("help")) {
			System.out.println("args: decrypt|encrypt");
		}
		else if ((getParam(args[0]).equals("decrypt") || getParam(args[0]).equals("d"))) {
			if (args.length == 1) {
				System.out.println(decript("help"));
			}
			else if (args.length == 2) {
				System.out.println(decript(args[1]));
			}
			else if (args.length == 3) {
				System.out.println(decript(args[1], args[2]));
			}
			else {
				System.out.println(String.format("Invalid decryption parameters: %s", String.join(",", args)));
			}
		}
		else if ((getParam(args[0]).equals("encrypt") || getParam(args[0]).equals("e"))) {
			if (args.length == 1) {
				System.out.println(encrypt("help"));
			}
			else if (args.length == 2) {
				System.out.println(encrypt(args[1]));
			}
			else if (args.length == 3) {
				System.out.println(encrypt(args[1], args[2]));
			}
			else {
				System.out.println(String.format("Invalid encryption parameters: %s", String.join(",", args)));
			}
		}
		else {
			System.out.println(String.format("Invalid parameters: %s", String.join(",", args)));
		}
	}
	
	private static String getParam(String p) {
		if (p == null ||p.isEmpty()) {
			return "";
		}
		else if (p.startsWith("-")) {
			return p.substring(1);
		}
		else {
			return p;
		}
	}
	
	private static String decript(String value) {
		return decript(value, null);
	}
	
	private static String decript(String value, String key) {
		if ("help".equals(value)) {
			return "args: decript/d value [key]\nkey: select originalcity from system";
		}
		if ("city".equals(key)) {
			key = "Sacramento";
		}
		return new Tools.Encryption(key).decryptString(value);
	}
	
	private static String encrypt(String value) {
		return encrypt(value, null);
	}
	
	private static String encrypt(String value, String key) {
		if ("help".equals(value)) {
			return "args: encrypt/e value [key]\nkey: select originalcity from system";
		}
		if ("city".equals(key)) {
			key = "Sacramento";
		}
		return new Tools.Encryption(key).encryptString(value);
	}


}
