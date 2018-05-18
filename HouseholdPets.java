package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class HouseholdPets extends Puzzle {
	HouseholdPets() {
		// Properties.
		myName = "HouseholdPets";
		myTitle = "Household Pets";

		// Nouns.
		NounType husbands = addNounType("Husband");
		Noun dennis = husbands.addNoun("Dennis");
		Noun dickie = husbands.addNoun("Dickie");
		Noun eamonn = husbands.addNoun("Eamonn");
		Noun kenneth = husbands.addNoun("Kenneth");
		Noun neville = husbands.addNoun("Neville");

		NounType wives = addNounType("Wife");
		Noun ivy = wives.addNoun("Ivy");
		Noun jasmine = wives.addNoun("Jasmine");
		Noun laura = wives.addNoun("Laura");
		Noun marguerite = wives.addNoun("Marguerite");
		Noun rose = wives.addNoun("Rose");

		NounType lastNames = addNounType("Surname");
		Noun barnard = lastNames.addNoun("Barnard");
		Noun hatfield = lastNames.addNoun("Hatfield");
		Noun marchant = lastNames.addNoun("Marchant");
		Noun rushton = lastNames.addNoun("Rushton");
		Noun saville = lastNames.addNoun("Saville");

		NounType dogs = addNounType("Dog");
		Noun cleo = dogs.addNoun("Cleo");
		Noun reggie = dogs.addNoun("Reggie");
		Noun scamp = dogs.addNoun("Scamp");
		Noun shandy = dogs.addNoun("Shandy");
		Noun yorky = dogs.addNoun("Yorky");

		NounType cats = addNounType("Cat");
		Noun claws = cats.addNoun("Claws");
		Noun fluff = cats.addNoun("Fluff");
		Noun sheba = cats.addNoun("Sheba");
		Noun sultan = cats.addNoun("Sultan");
		Noun wally = cats.addNoun("Wally");

		// Facts.
		addFact("1", kenneth, Is, With, reggie);
		addFact("1", jasmine, Is, With, sultan);
		addFact("2", ivy, IsNot, With, getList(sheba, sultan, wally));
		addFact("2", ivy, IsNot, With, getList(dennis, dickie, eamonn));
		addFact("3", scamp, Is, With, claws);
		addFact("3", getList(eamonn, kenneth, neville), IsNot, With, scamp);
		addFact("4", marguerite, Is, With, rushton);
		addFact("4", marguerite, IsNot, With, neville);
		addFact("4", eamonn, Is, With, barnard);
		addFact("4", getList(marguerite, neville, eamonn, shandy), IsNot, With, wally);
		addFact("5", dennis, Is, With, laura);
		addFact("5", getList(dennis, marchant), IsNot, With, yorky);
		addFact("5", dennis, IsNot, With, marchant);
		addFact("5", marchant, Is, With, fluff);
		addFact("5", marchant, IsNot, With, cleo);
		addFact("6", saville, Is, With, shandy);
		addFact("6", getList(saville, dickie), IsNot, With, sheba);

		// Solution.
		answer = new int[][]{{2, 3, 4, 0, 1}, {1, 3, 0, 2, 4}, {0, 2, 4, 1, 3}, {4, 0, 2, 1, 3}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Husband, 2=Wife, 3=Surname, 4=Dog, 5=Cat
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: msg = noun1.name + "'s wife " + verb.name + " " + noun2.name; break;
					case 3: msg = noun1.name + "'s last name " + verb.name + " " + noun2.name; break;
					case 4: msg = noun1.name + "'s dog " + verb.name + " " + noun2.name; break;
					case 5: msg = noun1.name + "'s cat " + verb.name + " " + noun2.name; break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: msg = noun1.name + "'s husband " + verb.name + " " + noun2.name; break;
					case 2: break;
					case 3: msg = noun1.name + "'s last name " + verb.name + " " + noun2.name; break;
					case 4: msg = noun1.name + "'s dog " + verb.name + " " + noun2.name; break;
					case 5: msg = noun1.name + "'s cat " + verb.name + " " + noun2.name; break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: msg = "The " + noun1.name + "'s dog " + verb.name + " " + noun2.name; break;
					case 5: msg = "The " + noun1.name + "'s cat " + verb.name + " " + noun2.name; break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: msg = noun1.name + " and " + noun2.name + " " + (verb == Is ? "are" : "are not") + " in the same household"; break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: msg = noun1.name + " and " + noun2.name + " " + (verb == Is ? "are" : "are not") + " in the same household"; break;
					case 5: break;
				}
				break;
		}

		return msg + ".";
	}
}
