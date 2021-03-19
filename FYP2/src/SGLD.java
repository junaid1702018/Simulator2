import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;

//import java.util.Scanner;
public class SGLD {
	public static int waitTime = 3000;
	public static boolean running = false;
	private String id;
	private float longitude, latitude;
	private double gas, temp;

	public void start() {
		running = true;
		String serverHostname = new String("127.16.0.1");
		// running=true;
		DeviceInfo info = new DeviceInfo();
		// info.color="Yellow";
		info.ID = id;
		info.longitude = longitude;
		info.latitude = latitude;
		// Threading work defined here
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				while (running) {
					Socket echoSocket = null;
					try {
						echoSocket = new Socket("127.16.0.1", 10099);

					} catch (UnknownHostException e) {
						System.out.println("Don't know about host: " + serverHostname);
						System.exit(1);
					} catch (IOException e) {
						System.out.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
						System.exit(1);
					}
					try {
						ObjectOutputStream objectOutput = new ObjectOutputStream(echoSocket.getOutputStream());
						
						// Passed the milliseconds to constructor of Timestamp class
						if(!Enviroment())
						{
							double temp1=gas;
							double temp2=temp;
							int minutes=0;
							java.util.Date date = new java.util.Date();
							Timestamp timestamp1 = new Timestamp(date.getTime());

							Calendar cal = Calendar.getInstance();
							cal.setTimeInMillis(timestamp1.getTime());

							// add a bunch of seconds to the calendar
							cal.add(Calendar.SECOND, 98765);

							while(minutes<5)
							{
								info.gas_sensor = Gas_Sensor()+1500;
								info.temperature_sensor = Temperature_Sensor()+3;
								Date date1 = new Date();
								long time = date1.getTime();
								info.send_time = new Timestamp(time);
								objectOutput.writeObject(info);
								objectOutput.close();
								echoSocket.close();

								Thread.sleep(waitTime);
								// create a second time stamp
								Timestamp timestamp2 = new Timestamp(cal.getTime().getTime());

								long milliseconds = timestamp2.getTime() - timestamp1.getTime();
								int seconds = (int) milliseconds / 1000;
								minutes = (seconds % 3600) / 60;
								seconds = (seconds % 3600) % 60;
								
							}
							gas=temp1;
							temp=temp2;
							
						}
						else
						{
							info.gas_sensor = Gas_Sensor();
							info.temperature_sensor = Temperature_Sensor();
							Date date = new Date();
							long time = date.getTime();
							info.send_time = new Timestamp(time);
							objectOutput.writeObject(info);
							objectOutput.close();
							echoSocket.close();

							Thread.sleep(waitTime);
						}
					
					} catch (InterruptedException e) { // TODO
						// Auto-generated catch block
						e.printStackTrace();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		// start the thread.
		thread.start();
	}

	public SGLD(String id, float lgt, float alt) {
		this.id = id;
		this.longitude = lgt;
		this.latitude = alt;
		gas = (double) (Math.random() * 180);
		temp = (double) (Math.random() * 180);
	}

	public void stopSendingData() {
		running = false;
	}
    public boolean Enviroment()
    {
    	if(Math.random()<0.05)
		{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    	
    }
	public int Gas_Sensor() {
		double data = Math.toRadians(gas);
		gas += 0.005;
		return (int) (9800 * Math.abs(Math.sin(data)) + 200);

	}

	public int Temperature_Sensor() {
		double data = Math.toRadians(temp);
		temp += 1;
		return (int) (4 * Math.abs(Math.sin(data)) + 18);
	}
}