package util;

public class Timer extends Thread
{
	private long time;
	
	public Timer(long miliSeconds)
	{
		time = miliSeconds * 1000000;
		start();
	}
	
	public void run()
	{
		long timeBefore, timeNow;
		while(time > 0)
		{
			timeBefore = System.nanoTime();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timeNow = System.nanoTime();
			time -= timeNow - timeBefore;
		}
		time = 0;
		//System.out.println("done");
	}
	
	public long timeLeftMiliSeconds()
	{
		return time / 1000000;
	}
	
	public boolean isDone()
	{
		if(time <= 0)
			return true;
		return false;
	}
}
