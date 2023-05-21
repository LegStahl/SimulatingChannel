package channel;

import java.util.Arrays;
import java.util.Scanner;



public class ChannelSimulation {
	
	private byte[] polGx = null;
	
	private byte[] vectorEr = null;
	
	private byte[] vectorMessage = null;
	
	private byte maxDegG = 0;
	
	private byte maxMessageDeg = 0;
	
	private byte k = 0;
	
	private int tempForGenerating = 0;
	
	private int running = 0;
	
	private Scanner scanner;
	
	public ChannelSimulation(byte k, byte maxDegG, byte maxMessageDeg) {
		this.k = k;
		
		this.maxDegG = maxDegG;
		
		this.maxMessageDeg = maxMessageDeg;
		
		scanner = new Scanner(System.in);
	}
	
	public void creatingMessage() {
		System.out.println("Input coef for message");
		vectorMessage = new byte[maxMessageDeg + 1];
		for (int i = vectorMessage.length - 1; i >= 0; i-- ) {
			System.out.print("X ^ " + i + ":");
			vectorMessage[i] = scanner.nextByte();
		}
	}
	
	public byte[] polynomDevided(byte[] devidend) {
		int check = devidend.length - 1;
		for(int i = devidend.length - 1; i >= 0; i--) {
			if(devidend[i] == 1) {
				check = i;
				break;
			}
		}
		byte[] remainder = new byte[devidend.length];
		if(devidend.length - 1 >= polGx.length -1) {
			for(int i = devidend.length - 1; i >= 0 && check >= polGx.length - 1; i -- ) {
				
				if(devidend[i] != 0) {
					byte coef = (byte)(i - (polGx.length - 1));
					System.out.println(Arrays.toString(devidend) + " Devid");
					for(int j = polGx.length - 1; j >= 0; j--  ) {
						if(polGx[j] != 0)
							remainder[coef + j] = 1;
					}
					for(int j = 0; j < remainder.length; j ++ ) {
						
						devidend[j] = (byte)((devidend[j] + remainder[j]) % 2);
						
					}
					for(int j = devidend.length - 1; j >= 0; j --) {
						if(devidend[j] != 0) {
							check = j;
							break;
						}
						else if( j == 0 && devidend[j] == 0 ) {
							check = 0;
						}
					}
					remainder = new byte[devidend.length];
				}
			}
			return devidend;
		}
		else {
			return devidend;
		}
	
	}
	
	private boolean checkDecoder(byte[] vector) {
		for(byte index : vector) {
			if(index ==1) {
				return false;
			}
		}
		return true;
	}
	
	public byte[] getMessage() {
		return vectorMessage;
	}
	
	public byte[] getPolynomG() {
		return polGx;
	}
	
	public byte[] getVectorMistakes() {
		return vectorEr;
	}
	
	public boolean simulation() {
	
		byte[] vectorForFinding = new byte[maxMessageDeg + maxDegG + 1];
		System.out.println(vectorForFinding.length);
		for(int i = 0; i < vectorMessage.length; i++) {
			if(vectorMessage[i] == 1) {
				vectorForFinding[i + maxDegG] = 1;
			}
		}
		byte[] vectorForSaving = vectorForFinding.clone(); 
		System.out.println("For dev " + Arrays.toString(vectorForFinding));
		byte[] Cx = polynomDevided(vectorForFinding);
		System.out.println("Vector Cx" + Arrays.toString(Cx));
		for(int i = 0; i < vectorForSaving.length; i++) {
			vectorForFinding[i] = (byte) (((byte)vectorForSaving[i] + (byte)Cx[i]) % 2);
		}
		System.out.println("Vector 'a' before mistakes " + Arrays.toString(vectorForFinding));
		byte[] vectorM = addErrors(vectorForFinding);
		System.out.println("Vector 'b' after mistakes " + Arrays.toString(vectorM));
		byte[] check = polynomDevided(vectorM);
		//vectorEr = new byte[vectorEr.length];
		tempForGenerating =(int) Math.pow(2, vectorEr.length);
		if(checkDecoder(check)) {
			System.out.println("Everything is allright!");
			return true;
		}else {
			System.out.println("Something went wrong...");
			return false;
		}
		
	}
	
	private byte[] addErrors(byte[] a) {
		for(int i = 0; i < a.length; i++) {
			a[i] = (byte)(((byte)a[i] + (byte)vectorEr[i]) % 2);
		}
		return a;
	}
	
	public void polominalGx() {
		
		System.out.println("Input coef for g(x)");
		polGx = new byte[maxDegG + 1];
		for(int i = polGx.length - 1; i >= 0; i--) {
			System.out.print("X ^ " + i + ": ");
			polGx[i] = scanner.nextByte();
		}
	}
	
	public void creatingError() {
		System.out.println("Input error vector ");
		vectorEr = new byte[maxMessageDeg + maxDegG + 1];
		for(int i = vectorEr.length - 1; i >= 0; i--) {
			System.out.print("X ^ " + i + ": ");
			vectorEr[i] = scanner.nextByte();
		}
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("PolG(x): ");
		for(byte i : polGx) {
			stringBuilder.append(i + " ");
		}
		stringBuilder.append("\nVectorEr: ");
		for(byte i : vectorEr) {
			stringBuilder.append(i + " ");
		}
		stringBuilder.append("\nVectorMessage: ");
		for(byte i : vectorMessage) {
			stringBuilder.append(i + " ");
		}
		
		return stringBuilder.toString();
	}
	
	public byte[] generateErrors() {
		vectorEr = new byte[vectorEr.length];
		if(running < tempForGenerating - 1) {
			running = running + 1;
			String str = Integer.toBinaryString(running);
			StringBuilder str2 = new StringBuilder(str);
			str = str2.reverse().toString();
			//System.out.println(str);
			int temp = 1;
			for(int i = 0; i < str.length(); i++) {
				if(str.charAt(i) == '1') {
					vectorEr[i] = 1;
				}
			}
		}
		System.out.println(Arrays.toString(vectorEr));
		return vectorEr;
	}
	
}
