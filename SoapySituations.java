package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class SoapySituations extends Puzzle {
	SoapySituations() {
		// Properties.
		myName = "SoapySituations";
		myTitle = "Soapy Situations";

		// Nouns.
		NounType husbands = addNounType("Husband");
		Noun allen = husbands.addNoun("Allen");
		Noun burke = husbands.addNoun("Burke");
		Noun charles = husbands.addNoun("Charles");
		Noun david = husbands.addNoun("David");
		Noun eli = husbands.addNoun("Eli");

		NounType wives = addNounType("Wife");
		Noun freda = wives.addNoun("Freda");
		Noun gloria = wives.addNoun("Gloria");
		Noun harriet = wives.addNoun("Harriet");
		Noun ingrid = wives.addNoun("Ingrid");
		Noun janet = wives.addNoun("Janet");

		NounType lastNames = addNounType("Last Name");
		Noun kilgore = lastNames.addNoun("Kilgore");
		Noun langley = lastNames.addNoun("Langley");
		Noun massey = lastNames.addNoun("Massey");
		Noun nolan = lastNames.addNoun("Nolan");
		Noun oNeal = lastNames.addNoun("O'Neal");

		NounType fates = addNounType("Fate");
		Noun amnesia = fates.addNoun("amnesia");
		Noun baby = fates.addNoun("switched baby");
		Noun wife = fates.addNoun("first wife");
		Noun haunted = fates.addNoun("haunted house");
		Noun twin = fates.addNoun("evil twin");

		// Facts.
		addFact("1", burke, Is, With, wife);
		addFact("2", janet, IsNot, With, nolan);
		addFact("2", janet, Is, With, twin);
		addFact("3", getList(langley, amnesia, gloria, ingrid, janet), IsNot, With);
		addFact("4", eli, IsNot, With, massey);
		addFact("4", massey, Is, With, haunted);
		addFact("5", getList(allen, eli), IsNot, With, baby);
		addFact("6", david, IsNot, With, freda);
		addFact("7", ingrid, IsNot, With, nolan);
		addFact("8", charles, Is, With, kilgore);
		addFact("9", gloria, Is, With, oNeal);
		addFact("9", gloria, IsNot, With, baby);

		// Solution.
		answer = new int[][]{{3, 1, 4, 2, 0}, {2, 4, 0, 1, 3}, {3, 2, 4, 1, 0}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Husband, 2=Wife, 3=Last Name, 4=Fate
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + "'s wife " + verb.name + " " + noun2.name;
						break;
					case 3:
						msg = noun1.name + " " + verb.name + " Mr. " + noun2.name;
						break;
					case 4:
						msg = noun1.name + "'s fate " + verb.name + " the " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg = noun1.name + " " + verb.name + " Mrs. " + noun2.name;
						break;
					case 4:
						msg = noun1.name + "'s fate " + verb.name + " the " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						msg = "Mrs. " + noun1.name + "'s husband " + verb.name + " " + noun2.name;
						break;
					case 2:
						msg = "Mr. " + noun1.name + "'s wife " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						msg = noun1.name + "'s fate " + verb.name + " the " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = "The one fated with " + noun1.name + " " + verb.name + " married to " + noun2.name; 
						break;
					case 3: break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
