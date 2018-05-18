package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class CreditCardShuffle extends Puzzle {
	CreditCardShuffle() {
		// Properties.
		myName = "CreditCardShuffle";
		myTitle = "Credit Card Shuffle";

		// Nouns.
		NounType firstNames = addNounType("First Name");
		Noun martha = firstNames.addNoun("Martha");
		Noun nicki = firstNames.addNoun("Nicki");
		Noun olivia = firstNames.addNoun("Olivia");
		Noun patty = firstNames.addNoun("Patty");
		Noun rhoda = firstNames.addNoun("Rhoda");

		NounType lastNames = addNounType("Last Name");
		Noun stewart = lastNames.addNoun("Stewart");
		Noun trudo = lastNames.addNoun("Trudo");
		Noun underwood = lastNames.addNoun("Underwood");
		Noun vale = lastNames.addNoun("Vale");
		Noun williams = lastNames.addNoun("Williams");

		NounType cards = addNounType("Card");
		Noun amex = cards.addNoun("American Excess", "Amex");
		Noun mrCharge = cards.addNoun("Mr. Charge");
		Noun platinum = cards.addNoun("Platinum");
		Noun recovery = cards.addNoun("Recovery");
		Noun viva = cards.addNoun("VIVA");

		NounType expenses = addNounType("Expense");
		Noun car = expenses.addNoun("car");
		Noun hotel = expenses.addNoun("hotel");
		Noun meals = expenses.addNoun("meals");
		Noun plane = expenses.addNoun("plane tickets", "Plane");
		Noun souvenirs = expenses.addNoun("souvenirs");

		// Facts.
		addFact("1", plane, IsNot, With, recovery);
		addFact("2", mrCharge, Is, With, williams);
		addFact("3", nicki, Is, With, stewart);
		addFact("3", nicki, IsNot, With, souvenirs);
		addFact("4", getList(martha, olivia, amex, trudo), IsNot, With);
		addFact("5", vale, Is, With, meals);
		addFact("5", vale, IsNot, With, viva);
		addFact("6", rhoda, IsNot, With, underwood);
		addFact("7", olivia, Is, With, car);
		addFact("8", martha, IsNot, With, recovery);
		addFact("9", souvenirs, Is, With, amex);
		addFact("10", trudo, IsNot, With, hotel);

		// Solution.
		answer = new int[][]{{3, 0, 4, 2, 1}, {2, 3, 1, 0, 4}, {2, 1, 0, 4, 3}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=First Name, 2=Last Name, 3=Card, 4=Expense
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 3:
						msg = noun1.name + "'s card " + verb.name + " " + noun2.name;
						break;
					case 4:
						msg = noun1.name + "'s expense " + verb.name + " for the " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg = noun1.name + "'s card " + verb.name + " " + noun2.name;
						break;
					case 4:
						msg = noun1.name + "'s expense " + verb.name + " for the " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = "The " + noun1.name + " card " + verb.name + " owned by " + noun2.name;
						break;
					case 3: break;
					case 4: break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg = "The " + noun1.name + " expense " + verb.name + " on the " + noun2.name + " card";
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
