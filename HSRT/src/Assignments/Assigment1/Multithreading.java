package Assignments.Assigment1;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Multithreading {

	public static void main(String[] args) {
		ArrayList<String> listOfLinks = readList("/Users/noah/IdeaProjects/Java Programming/HSRT/src/Assignments/Assigment1/pages.txtb");
		testRuntime(listOfLinks,1);
		testRuntime(listOfLinks,2);
		testRuntime(listOfLinks,4);
		testRuntime(listOfLinks,6);
		testRuntime(listOfLinks,8);
		testRuntime(listOfLinks,10);
		testRuntime(listOfLinks,20);
	}

	public static void testRuntime(ArrayList<String> list, int numberOfThreads){
		ArrayList<String> listOfLinks = list;
		ExecutorService execService = Executors.newFixedThreadPool(numberOfThreads);


		ArrayList<Task> tasks = new ArrayList();

		for (int i = 0; i<listOfLinks.size(); i++){
			tasks.add(new Task(listOfLinks.get(i)));
		}
		long startTime = System.currentTimeMillis();
		try {

			execService.invokeAll(tasks);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();
		execService.shutdown();
		float runtime = (endTime - startTime)/1000;

		System.out.println("The execution with " + numberOfThreads + " Threads took " + runtime + " seconds");


	}

	public static class Task implements Callable<Boolean>{

		private final String address;

		public Task(String address){
			this.address = address;
		}

		public Boolean call(){

			long threadId = 0;
			threadId = Thread.currentThread().getId();
			//System.out.println(threadId + ": "+ address);
			return open(address);

		}

	}

	public static boolean open(String address)  {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(address))
				.build();
		HttpResponse<String> response;

		try {

			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			//String testOutput = new String();
			//System.out.println(response);
			//System.out.println("Connection to " + address + " successfully established.");
			return true;


		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList readList(String filepath){
		BufferedReader abc = null;
		//List<String> lines = new ArrayList<String>();
		String line;
		ArrayList<String> lines = new ArrayList<String>();

		try {   //check if file exists
			File myFile = new File(filepath);
			abc = new BufferedReader(new FileReader(myFile));
			try {
				while ((line = abc.readLine()) !=null){
					lines.add(line);
					//System.out.println(line);
				}
			} catch (IOException e){
				e.printStackTrace();
			}

			//System.out.println("Success");
			abc.close();


		} catch (FileNotFoundException e) { //If file doesn't exist
			//e.printStackTrace();
			System.out.println("Error: The required file could not be found");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}


}
