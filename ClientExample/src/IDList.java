import java.util.Vector;
import java.io.Serializable;

public class IDList implements Serializable {
	Vector<String> idList = null; 
	
	public IDList() {
		idList = new Vector<String>();
	}
}
