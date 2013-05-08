import java.util.Random;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class to hash a Password using a random number of rounds, which results in an
 * effort per guess in a bruteforce attack that is higher than the average
 * effort to check a password
 * 
 * @author Adrian Aulbach
 */
public class RandomRoundsHashing {

	/**
	 * Main method to use the class from the command line (to test it or as a
	 * bridge to other programming-languages)
	 * 
	 * @param args
	 *            Arguments: "-h [password] [salt] [initialRounds] [maxRounds]"
	 *            to hash a password or "-c [password] [salt] [initialRounds]
	 *            [maxRounds] [hash]" to check a hash
	 * @throws NoSuchAlgorithmException
	 *             if the hashing-algorithm can't be found
	 */
	public static void main(String args[]) throws NoSuchAlgorithmException {
		String manual = "Arguments:\n-h [password] [salt] [initialRounds] [maxRounds] to hash a password or \n "
				+ " -c [password] [salt] [initialRounds] [maxRounds] [hash] to check a hash";

		if (args.length >= 5) {// args[] must contain at least 5 elements,
								// otherwise it isn't a valid call
			String password = args[1];
			String salt = args[2];
			int initialRounds = (new Integer(args[3])).intValue();
			int maxRounds = (new Integer(args[4])).intValue();

			if (args[0].equals("-h")) {// hash a password
				System.out.println(hash(password, salt, initialRounds,
						maxRounds));
			} else if (args[0].equals("-c") && args.length >= 6) { // check a
																	// password/hash
																	// combination
				String hash = args[5];
				System.out.println(check(password, salt, initialRounds,
						maxRounds, hash));
			} else {
				// arguments not valid, print manual
				System.out.println("Invalid Arguments:");
				System.out.println(manual);
			}
		} else {
			// print manual
			System.out.println(manual);
		}
	}

	/**
	 * Creates a new hash for a password
	 * 
	 * @param password
	 *            Password to hash
	 * @param salt
	 *            salt to hash the password with
	 * @param initialRounds
	 *            rounds of hashing that always have to be done
	 * @param maxRounds
	 *            maximal number of hashing-rounds
	 * @return a new hash for the given Password
	 * @throws NoSuchAlgorithmException
	 */
	public static String hash(String password, String salt, int initialRounds,
			int maxRounds) throws NoSuchAlgorithmException {
		Random r = new Random();

		String hash = "";

		// hash the password for a random number of rounds between initialRounds
		// and maxRounds
		for (int i = 0; i < initialRounds
				+ r.nextInt(maxRounds - initialRounds); i++) {
			// hash Password for the given number of rounds
			hash = sha512(password + salt + hash);
		}

		return hash;
	}

	/**
	 * Checks whether the given hash is a valid hash of the given password
	 * 
	 * @param password
	 *            password to hash
	 * @param salt
	 *            salt to hash the password with
	 * @param initialRounds
	 *            rounds of hashing that always have to be done
	 * @param maxRounds
	 *            maximal number of hashing-rounds
	 * @param hash
	 *            hash that the password will be compared with
	 * @return true if the given hash is a valid hash of the given password,
	 *         otherwise false
	 * @throws NoSuchAlgorithmException
	 *             if the SHA-512 algorithm can't be found
	 */
	public static boolean check(String password, String salt,
			int initialRounds, int maxRounds, String hash)
			throws NoSuchAlgorithmException {
		String newHash = "";

		// hashPassword for the initial number of rounds
		for (int i = 0; i < initialRounds; i++) {
			newHash = sha512(password + salt + newHash);
		}

		// hash and Check if newHash==hash, return true if it's the case
		for (int i = initialRounds; i < maxRounds; i++) {
			newHash = sha512(password + salt + newHash);
			if (newHash.equals(hash))
				return true;
		}

		// the password is not valid
		return false;
	}

	/**
	 * Hashes the given text (String) using the SHA-512 algorithm
	 * 
	 * @param text
	 *            String to hash
	 * @return SHA-512 hash of the String 'text'
	 * @throws NoSuchAlgorithmException
	 *             if the SHA-512-algorithm can't be found
	 */
	private static String sha512(String text) throws NoSuchAlgorithmException {
		MessageDigest sha512 = MessageDigest.getInstance("SHA-512");

		// hash the text
		BigInteger hash = new BigInteger(1, sha512.digest(text.getBytes()));

		// return hash as hex-value
		return hash.toString(16);// Replace 16 by another number to represent
									// the hash with a different base (eg. 36 to
									// use the whole alpabet + numbers)
	}
}
