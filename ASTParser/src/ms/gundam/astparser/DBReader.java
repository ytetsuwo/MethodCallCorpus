package ms.gundam.astparser;

import java.io.File;
import java.util.List;

public class DBReader {
	public static void main(String args[]) {
		DBReader reader = new DBReader();
		if (args.length != 3) {
			System.err.println("Specify DB diretory, Classname and Methodname");
			return;
		}
		File dbDir = new File(args[0]);
		if (!dbDir.exists()) {
			System.err.println("No such direcotry" + args[0]);
			return;
		}
		reader.read(dbDir, args[1], args[2]);
	}

	private void read(File dbDir, String classname, String methodname) {
		DB db = new DB();
		db.open(dbDir, true);
		List<Value> list = db.get(classname, methodname);
		for (Value v : list) {
			System.out.println(v.toString());
		}
	}
}
