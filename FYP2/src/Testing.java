
import java.io.*;
public interface Testing {
	public static void main(String[] args) throws IOException {

		File file = new File("C:\\Users\\Junaid\\eclipse-workspace\\FYP2\\Devices.txt");
		BufferedReader reader =new BufferedReader(new FileReader(file));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      //records.add(line);
	    	String[] arrOfStr=line.split(" ");
	    	String id=arrOfStr[0];
	    	float longitude =Float.valueOf(arrOfStr[1]).floatValue();
	    	float latitude=Float.valueOf(arrOfStr[2]).floatValue();
	    	SGLD device = new SGLD(id, longitude, latitude);
			device.start();
	    }
	    reader.close();
	}

}
