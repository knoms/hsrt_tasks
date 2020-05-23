package Assignments.Assigment2;

import hs.rt.vs.CalculationTask;
import java.io.IOException;
import java.net.Socket;

public class SocketTest {




	public static void main(String[] args) throws IOException, ClassNotFoundException {

					for (int i=0; i<5;i++){
							int n = i+1;
							if (runProgram()){
								System.out.println("Berechnung "+ n + " war korrekt.");
							}
							else {
								System.out.println("Berechnung "+ n + " war falsch. Abbruch");
								break;
							}
					}

	}
	public static boolean runProgram() throws IOException, ClassNotFoundException {
		Socket socket = null;
		Boolean success = false;
		String serverAddress = "134.103.216.90";

		try {
			socket = new Socket(serverAddress, 11223);
			//System.out.println("Aufbau der Verbindung");
			try (TCPSocket tcpSocket = new TCPSocket(new Socket(serverAddress, 11223))) {

				CalculationTask task = (CalculationTask) tcpSocket.receiveObject();
				//System.out.println(task.getFirstValue());
				//System.out.println(task.getSecondValue());
				//System.out.println(task.getResult());
				calculate(task);
				System.out.println("Calculated result: " + task.getResult());
				tcpSocket.sendObject(task);
				String response = tcpSocket.readLine();
				if (response.startsWith("correct")) success = true;
				System.out.println("Server response: " + response);

			} catch (Exception e) {
				System.out.println("Abbruch");
				System.out.println(e);
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Verbindung nicht möglich");
			e.printStackTrace();
		}
		return success;
	}

	public static void calculate(CalculationTask task){
		double a = task.getFirstValue();
		double b = task.getSecondValue();
		task.setResult(Math.pow(a*b,2));
	}


}
