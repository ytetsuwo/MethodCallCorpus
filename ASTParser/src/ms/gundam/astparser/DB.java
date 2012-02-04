package ms.gundam.astparser;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class DB {
	private Environment myEnv;
    private Database afterDb;
    private Database beforeDb;
    private Database classCatalogDb;

    // Needed for object serialization
    private StoredClassCatalog classCatalog;
    private EntryBinding<Value> dataBinding;

    public boolean open(File envHome, boolean readOnly) {
        // Database and environment configuration omitted for brevity
        // Instantiate an environment and database configuration object
        EnvironmentConfig myEnvConfig = new EnvironmentConfig();
        DatabaseConfig myDbConfig = new DatabaseConfig();

        // Configure the environment and databases for the read-only
        // state as identified by the readOnly parameter on this 
        // method call.
        myEnvConfig.setReadOnly(readOnly);
        myDbConfig.setReadOnly(readOnly);

        // If the environment is opened for write, then we want to be
        // able to create the environment and databases if 
        // they do not exist.
        myEnvConfig.setAllowCreate(!readOnly);
        myDbConfig.setAllowCreate(!readOnly);
        
//        myDbConfig.setSortedDuplicates(true);

        // Instantiate the Environment. This opens it and also possibly
        // creates it.
        myEnv = new Environment(envHome, myEnvConfig);

		// Now create and open our databases.
        afterDb = myEnv.openDatabase(null, "AfterDB", myDbConfig);
        beforeDb = myEnv.openDatabase(null, "BeforeDB", myDbConfig);

        // Open the class catalog db. This is used to
        // optimize class serialization.
        myDbConfig.setSortedDuplicates(false);
        classCatalogDb = myEnv.openDatabase(null, "ClassCatalogDB", myDbConfig);

        // Create our class catalog
        classCatalog = new StoredClassCatalog(classCatalogDb);
        dataBinding = new SerialBinding<Value>(classCatalog, Value.class);
        
        return true;
	}
	
	public boolean put(String keyclassname, String keymethodname, String classname, String methodname) {
       // Need a serial binding for the data

/*
        Value theValue = new Value();
        theValue.setClassname(classname);
        theValue.setMethodname(methodname);
        theValue.setCount(1);
*/
		
        final String keyName = keyclassname + "#" + keymethodname + "!" + classname + "#"+ methodname;
        final EntryBinding<Integer> IntegerBinding = TupleBinding.getPrimitiveBinding(Integer.class);

        DatabaseEntry theKey = null;
        DatabaseEntry theData = new DatabaseEntry();
        Integer count = 1;
		try {
        	theKey = new DatabaseEntry(keyName.getBytes("UTF-8"));
        } catch (IOException willNeverOccur) {}

		// 
		if (afterDb.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
			// Recreate the data String.
			 count = IntegerBinding.entryToObject(theData);
			 count++;
		}

		// Convert the Vendor object to a DatabaseEntry object
        // using our SerialBinding
//        dataBinding.objectToEntry(theValue, theData);
        Integer myInt = new Integer(count);
        IntegerBinding.objectToEntry(myInt, theData);
        
        // Put it in the database. These puts are transactionally
        // protected (we're using autocommit).
        afterDb.put(null, theKey, theData);

		return true;
	}
	
	public Integer get(String keyclassname, String keymethodname) {
		final String keyName = keyclassname + "#" + keymethodname;
        final EntryBinding<Integer> IntegerBinding = TupleBinding.getPrimitiveBinding(Integer.class);
		Integer count = null;
		Cursor cursor = null;
		try {
			cursor = afterDb.openCursor(null, null);

			DatabaseEntry theKey = new DatabaseEntry(keyName.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();
    
			OperationStatus ret = cursor.getSearchKey(theKey, theData, LockMode.DEFAULT);

			if (cursor.count() > 1) {
				while (ret == OperationStatus.SUCCESS) {
					String keyString = new String(theKey.getData(), "UTF-8"); 
					count = count + IntegerBinding.entryToObject(theData);
System.out.println(keyString + count);					
					ret = cursor.getNext(theKey, theData, LockMode.DEFAULT);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return count;
	}

	public void close() {
        if (myEnv != null) {
            try {
                afterDb.close();
                beforeDb.close();
                classCatalogDb.close();
                myEnv.close();
            } catch(DatabaseException dbe) {
                System.err.println("Error closing MyDbEnv: " + dbe.toString());
                System.exit(-1);
            }
        }		
	}
}
