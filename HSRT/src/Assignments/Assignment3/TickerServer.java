package Assignments.Assignment3;


import hs.rt.RandomNewsObject;
import hs.rt.RandomNewsTicker;
import hs.rt.Subscriber;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class TickerServer extends UnicastRemoteObject implements RandomNewsTicker{
	private static NewsList newsList;
	private List<Subscriber> subscriberList;
	//
	public TickerServer() throws RemoteException {
		super();
		subscriberList = Collections.synchronizedList(new ArrayList<Subscriber>());
	}
	//
	public static void main(String[] args) {
		if (args.length >= 1)
		{
			System.setProperty("java.rmi.server.hostname", args[0]);
		}
		try {
			// Runs a RMI registry on the RMI default port 1099
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			TickerServer ticker = new TickerServer();
			newsList = new NewsList();
			// creating the real object on which to remote method are executed on.
			registry.rebind("RandomNewsTicker", ticker);
			System.out.println("Registry lookup: " + registry.lookup("RandomNewsTicker"));
			System.out.println("RandomNewsTicker-Server started");
			System.out.println("Waiting for connections");
			Timer timer = new Timer();
			TimerTask newsShot = new TimerTask(){
				@Override
				public void run() {
						try {
							if(!ticker.subscriberList.isEmpty()) {
								System.out.println("Sending out random news to " + ticker.subscriberList.size() + " recipients.");
								for (Subscriber sub : ticker.subscriberList) {
									sub.displayRandomNews(newsList.giveRandomNews());
								}
							}
						} catch (RemoteException e) {
							e.printStackTrace();
						}
				}
			};
			timer.schedule(newsShot, 1000, 1000);
		}
		catch(Exception e)
		{
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	@Override
	public boolean subscribe(Subscriber subscriber) throws RemoteException {
			int size = subscriberList.size() + 1;
			System.out.println("New user subscribed. Subscriber count: " + size);
			return subscriberList.add(subscriber);
	}
	@Override
	public boolean unsubscribe(Subscriber subscriber) throws RemoteException {
		int size = subscriberList.size() - 1;
		System.out.println("User unsubscribed. Subscriber count: " + size);
		if (size==0){
			System.out.println("Waiting for connections...");
		}
		return subscriberList.remove(subscriber);
	}
	@Override
	public boolean publishRandomNewsObject(RandomNewsObject randomNewsObject) throws RemoteException {
		newsList.appendObject(randomNewsObject);
		System.out.println("Received News by: "+ randomNewsObject.getAuthor());
		return true;
	}



}
