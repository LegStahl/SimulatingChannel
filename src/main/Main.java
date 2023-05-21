package main;

import channel.*;

import java.util.*;

public class Main {
	public static void main(String[] argv) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Input k");
		byte k = scanner.nextByte();
		System.out.print("Input max degree of g(x):");
		byte maxDegG = scanner.nextByte();
		System.out.print("Input message max degree it can be more or less than k ");
		byte maxMessageDeg = scanner.nextByte();
		ChannelSimulation simulator = new ChannelSimulation(k, maxDegG, maxMessageDeg);
//		byte[] devidend = {1, 0, 0, 1, 0, 0, 1};
//		devidend = simulator.polynomDevided(devidend);
//		for(byte i : devidend) {
//			System.out.print(i + " ");
//		}
		simulator.polominalGx();
		simulator.creatingMessage();
		simulator.creatingError();
		simulator.simulation();
		
		AlterChannelSimulation alter = new AlterChannelSimulation(k, maxDegG, maxMessageDeg, simulator.getVectorMistakes(), simulator.getMessage(), simulator.getPolynomG());
		System.out.println(simulator);
		System.out.println(alter);
		alter.simulation();
		int countOfNotSimilar = 0;
		int count = 0;
		List<byte[]> listOfSimul = new ArrayList<>();
		List<byte[]> listOfAlterSimul = new ArrayList<>();
		for(int i  = 0; i < Math.pow(2, 7); i ++) {
			if(i == 10) {
				System.out.println("Heelo");
			}
			System.out.println(Arrays.toString(alter.generateErrors()));
			System.out.println(Arrays.toString(simulator.generateErrors()));
			if(alter.simulation() != simulator.simulation()) {
				 countOfNotSimilar++;
				 listOfSimul.add(simulator.getVectorMistakes());
				 listOfAlterSimul.add(alter.getVectorMistakes());
			}
			count++;
		}
		System.out.println("The number of tries " + count + " The number of not similar result " + countOfNotSimilar);
		System.out.println("On the left is classical simulator and on the right is the alternative way ");
		for(int i = 0; i < listOfSimul.size(); i++) {
			System.out.println(Arrays.toString(listOfSimul.get(i)) + "     " + Arrays.toString(listOfAlterSimul.get(i)));
			
		}
		//System.out.println(simulator);
		
	}
}
