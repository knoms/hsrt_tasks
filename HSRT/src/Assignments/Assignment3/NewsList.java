package Assignments.Assignment3;

import hs.rt.RandomNewsObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class NewsList {
	private ArrayList<RandomNewsObject> list = new ArrayList();

	public NewsList(){
		this.randomFill();
	}

	private void randomFill(){
		RandomNewsObject one = new RandomNewsObject("Erste Testnachricht", "Bill Gates");
		RandomNewsObject two = new RandomNewsObject("Zweite Testnachricht", "Ernest Hemingway");
		RandomNewsObject three = new RandomNewsObject("Dritte Testnachricht", "Hagrid");
		RandomNewsObject four = new RandomNewsObject("Vierte Testnachricht", "Ronald McDonald");
		RandomNewsObject five = new RandomNewsObject("Fuenfte Testnachricht", "Lukas Podolski");
		this.list.addAll(Arrays.asList(one,two,three,four));
	}

	public boolean importList(ArrayList importList){
		list = importList;
		return true;
	}

	public boolean appendObject(RandomNewsObject newsObject){
		list.add(newsObject);
		return true;
	}

	public RandomNewsObject giveRandomNews(){
		int randomInt = ThreadLocalRandom.current().nextInt(0, list.size());
		return list.get(randomInt);
	}



}
