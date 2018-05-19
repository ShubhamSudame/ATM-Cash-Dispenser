import java.util.*;
public class Bank {
	static Scanner input;
	private TreeMap<Integer, Integer> currencyNotes = new TreeMap<>();
	private int balance;
	private ArrayList<Integer> collectionNotes;
	private int lastFailed, noteKinds;
	private boolean lastChoice;

	private Bank() {
		currencyNotes.put(2000, 0);
		currencyNotes.put(500, 0);
		currencyNotes.put(200, 0);
		currencyNotes.put(100, 0);
		currencyNotes.put(50, 0);
		currencyNotes.put(20, 0);
		currencyNotes.put(10, 0);
		currencyNotes.put(5, 0);
		currencyNotes.put(1, 0);
		balance = 0;
	}

	private int default_menu() {
		int choiceCode;
		try {
			System.out.println("Enter Choice Code:");
			System.out.println("1. Withdraw Cash");
			System.out.println("2. Add Cash");
			System.out.println("3. Cancel");
			System.out.printf("Choice : ");
			choiceCode = input.nextInt();
		}
		catch(InputMismatchException exception) {
			choiceCode = -1;
		}
		return choiceCode;
	}

	private void choiceTask(int choiceCode) {
		switch (choiceCode) {
		case 1:
			if (balance == 0)
				System.out.println("Cannot Withdraw cash: Balance = 0");
			else {
				withdrawCash();
				lineBreak();
			}	
			break;
		case 2:
			System.out.println("Adding Currency:");
			addCash();
			lastChoice = false;
			lineBreak();
			break;
		case 3:
			input.close();
			lineBreak();
			System.exit(3);
		default:
			System.out.println("Wrong Choice!");
			input.close();
			lineBreak();
			System.exit(4);
		}
	}

	public static void main(String[] args) {
		int choiceCode;
		input = new Scanner(System.in);
		Bank atm = new Bank();
		while (true) {
			choiceCode = atm.default_menu();
			atm.choiceTask(choiceCode);
			choiceCode = 0;
		}
	}

	private void addCash() {
		int value, original;
		for (Integer key : currencyNotes.descendingKeySet()) {
			try {
				System.out.printf("%d : ", key);
				value = input.nextInt();
			}
			catch(InputMismatchException exception) {
				System.out.println("Invalid Input.");
				return;
			}
			if(value < 0) {
				return;
			}
			original = currencyNotes.get(key);
			currencyNotes.put(key, original + value);
			balance += key * value;
		}
		System.out.println("Available PIGGY BANK cash: ");
		for (Integer key : currencyNotes.descendingKeySet()) {
			int keyValue = currencyNotes.get(key);
			System.out.println(key + " : "+keyValue);
		}
		System.out.println("Available cash in PIGGY BANK: " + balance);
	}

	private void withdrawCash() {
		int totalAmount, amount;
		try {
			System.out.printf("Enter amount: ");
			totalAmount = input.nextInt();
		}
		catch(InputMismatchException exception) {
			System.out.println("Invalid amount");
			return;
		}
		if(totalAmount < 0) {
			System.out.println("Invalid Amount.");
			return;
		}
		if (totalAmount > balance)
			System.out.println("Not enough balance present!");
		else {
			int keyChecked = 0;
			amount = totalAmount;
			lastChoice = false;
			lastFailed = 0;
			collectionNotes = new ArrayList<>();
			TreeMap<Integer, Integer> originalCurrencyNotes = new TreeMap<>();
			originalCurrencyNotes.putAll(currencyNotes.descendingMap());
			NavigableMap<Integer, Integer> sortedCurrencyNotes = currencyNotes.descendingMap();
			while(amount != 0) {
				amount = computeOperation(amount, keyChecked, sortedCurrencyNotes);
			
				if (amount == 0) {
					System.out.println("Collect your Cash: ");
					for (Integer key : currencyNotes.descendingKeySet()) {
						int value = (originalCurrencyNotes.get (key) - currencyNotes.get(key));
						if(value != 0)
							System.out.println(key + " : " +value);
					}
					
					System.out.println("Available PIGGY BANK cash: ");
					for (Integer key : currencyNotes.descendingKeySet()) {
						int value = currencyNotes.get(key);
						System.out.println(key + " : " +value);
					}
					balance -= totalAmount;
					System.out.println("Available cash in PIGGY BANK: " + balance);
					noteKinds = 0;
					break;
				} 
				else if(amount == -1) {
					System.out.println("Denominations not sufficient to dispense given amount. Try a different amount.");
					break;
				}
				else {
					amount = backTracking(amount, keyChecked, originalCurrencyNotes, sortedCurrencyNotes);
				}
			}
		}
	}
	private int computeOperation(int amount, int keyChecked,NavigableMap<Integer, Integer> sortedCurrencyNotes) {
		if(!(cashDispenser(amount, sortedCurrencyNotes, lastFailed))){
			return -1;
		}
		while (amount != 0 && keyChecked < sortedCurrencyNotes.size()) {
			for (Map.Entry<Integer, Integer> entry : sortedCurrencyNotes.entrySet()) {
				Integer currentNote = entry.getKey();
				Integer currentNoteCount = entry.getValue();
				boolean execute, thisPartRan = false;
				if(lastChoice)
					execute = (currentNote <= amount);
				else if (noteKinds > 1)
					execute = (currentNote <= amount);
				else
					execute = (currentNote < amount);
				while(execute) {	
					if (currentNoteCount != 0 && currentNote != lastFailed) {
						collectionNotes.add(currentNote);
						currentNoteCount--;
						amount -= collectionNotes.get(collectionNotes.size() - 1);
						currencyNotes.put(collectionNotes.get(collectionNotes.size() - 1), currencyNotes.get(collectionNotes.get(collectionNotes.size() - 1)) - 1);	
					if((noteKinds > 0)) 
						execute = (currentNote <= amount);
					else if(lastChoice)
						execute = (currentNote <= amount);
					else 
						execute = (currentNote < amount);
					thisPartRan = true;
					}
					else
						break;
				}
				if(thisPartRan)
					noteKinds++;
				thisPartRan = false;
				keyChecked++;
			}
		}
	return amount;
	}
	private int backTracking(int amount, int keyChecked, TreeMap<Integer, Integer> originalCurrencyNotes, NavigableMap<Integer, Integer> sortedCurrencyNotes) {
		noteKinds--;
		keyChecked = 0;
		lastChoice = true;
		if(collectionNotes.size() != 0)
			lastFailed = collectionNotes.remove(collectionNotes.size() - 1);
		int originalCount = originalCurrencyNotes.get(lastFailed), currentCount = currencyNotes.get(lastFailed);
		while(currencyNotes.get(lastFailed) != originalCount){
			currentCount = currencyNotes.get(lastFailed) + 1;
			currencyNotes.put(lastFailed, currentCount);
			amount += lastFailed * 1;
			if(collectionNotes.size() != 0)
				lastFailed = collectionNotes.remove(collectionNotes.size() - 1);
		}
		return amount;
	}
	
	private boolean cashDispenser(int totalAmount, NavigableMap<Integer, Integer> sortedCurrencyNotes, int lastFailed) {
		int amount = totalAmount, sum = 0;
		boolean flag = false;
		for (Map.Entry<Integer, Integer> entry : sortedCurrencyNotes.entrySet()) {
			Integer currentNote = entry.getKey();
			Integer currentNoteCount = entry.getValue();
			if(amount >= currentNote && currentNote != lastFailed) {
				int requiredNoteCount = amount / currentNote;
				if(currentNoteCount >= requiredNoteCount) {
					if(currentNote != lastFailed)
						amount %= currentNote;
					flag |= true;
				}
				else {
					flag |= false;
				}
			}
			sum += (currentNote * currentNoteCount);
		}
		if(lastChoice) {
			if(amount != 0) {
				flag &= false;
			}
		}
		if(sum >= totalAmount) {
			flag |= true;
		}
		return flag;
	}
	private void lineBreak() {
		System.out.println("--------------------------------------------------------------------------------");
	}
}

