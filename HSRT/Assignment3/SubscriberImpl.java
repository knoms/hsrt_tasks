package Assignments.Assignment3;


import hs.rt.RandomNewsObject;
import hs.rt.RandomNewsTicker;
import hs.rt.Subscriber;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SubscriberImpl extends UnicastRemoteObject implements Subscriber {
	public final String name;

	public SubscriberImpl(String name) throws RemoteException {
		this.name = name;
	}

	public void displayRandomNews(RandomNewsObject randomNewsObject) throws RemoteException {
		System.out.println(String.format("\"%s\"\n-%s\ncreated: %tc\n\n", randomNewsObject.getMessage(), randomNewsObject.getAuthor(), randomNewsObject.getCreationDate()));
	}

	public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
		String IP_CLIENT;
		String IP_SERVER;
		if (args.length != 2) {
			IP_CLIENT = "localhost";
			IP_SERVER = "localhost";
		}
		else {
			IP_CLIENT = args[0];
			IP_SERVER = args[1];
		}
		System.setProperty("java.rmi.server.hostname", IP_CLIENT);
		RandomNewsTicker ticker = (RandomNewsTicker)Naming.lookup("rmi://" + IP_SERVER + "/RandomNewsTicker");
		System.out.println("Try to push a random new to the server.");
		RandomNewsObject newsObject = new RandomNewsObject("Trump will candidate for presidentship in Mexico.", "Foxxx News");
		ticker.publishRandomNewsObject(newsObject);
		Subscriber me = new hs.rt.SubscriberImpl("AutomatedClient");
		System.out.println("Subscribe to new ticker for 15 seconds.");
		ticker.subscribe(me);
		Thread.sleep(15000L);
		System.out.println("Unsubscribe from new ticker");
		ticker.unsubscribe(me);
		System.out.println("Successful run. Congrats!");
		System.exit(0);
	}
}
