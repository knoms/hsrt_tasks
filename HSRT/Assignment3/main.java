package Assignments.Assignment3;

import hs.rt.SubscriberImpl;

import java.rmi.RemoteException;

public class main {


	public static void main(String[] args) {
		SubscriberImpl sub;
		{
			try {
				sub = new SubscriberImpl("Namia");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}


}
