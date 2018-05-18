package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class SuperSurfers extends Puzzle {
	SuperSurfers() {
		// Properties.
		myName = "SuperSurfers";
		myTitle = "Super Surfers";

		// Nouns.
		NounType firstNames = addNounType("First Name");
		Noun tommy = firstNames.addNoun("Tommy");
		Noun rod = firstNames.addNoun("Rod");
		Noun jeff = firstNames.addNoun("Jeff");
		Noun lance = firstNames.addNoun("Lance");
		Noun brad = firstNames.addNoun("Brad");

		NounType lastNames = addNounType("Last Name");
		Noun tomlin = lastNames.addNoun("Tomlin");
		Noun wilkins = lastNames.addNoun("Wilkins");
		Noun rivera = lastNames.addNoun("Rivera");
		Noun oHara = lastNames.addNoun("O'Hara");
		Noun mcCoy = lastNames.addNoun("McCoy");

		NounType nicknames = addNounType("Nickname");
		Noun bigFish = nicknames.addNoun("Big Fish");
		Noun madMan = nicknames.addNoun("Mad Man");
		Noun king = nicknames.addNoun("King");
		Noun roughRider = nicknames.addNoun("Rough Rider");
		Noun typhoon = nicknames.addNoun("Typhoon");

		NounType beaches = addNounType("Beach");
		Noun whitecap = beaches.addNoun("Whitecap");
		Noun greatSurf = beaches.addNoun("Great Surf");
		Noun kahunaCove = beaches.addNoun("Kahuna Cove");
		Noun buryDunes = beaches.addNoun("Bury Dunes");
		Noun angelBeach = beaches.addNoun("Angel Beach");

		// Facts.
		addFact("1", getList(rod, jeff, tomlin, wilkins), IsNot, With);
		addFact("2", wilkins, IsNot, With, bigFish);
		addFact("3", tomlin, IsNot, With, getList(bigFish, whitecap));
		addFact("4", getList(lance, rivera, madMan), IsNot, With);
		addFact("4", madMan, Is, With, greatSurf);
		addFact("5", brad, IsNot, With, king);
		addFact("6", getList(jeff, kahunaCove, rod, whitecap), IsNot, With);
		addFact("7", oHara, IsNot, With, buryDunes);
		addFact("8", getList(mcCoy, kahunaCove, whitecap), IsNot, With);
		addFact("9", getList(king, roughRider, buryDunes), IsNot, With);
		addFact("10", lance, IsNot, With, angelBeach);
		addFact("10", getList(lance, typhoon, king), IsNot, With);
		addFact("11", getList(whitecap, bigFish, king), IsNot, With);
		addFact("12", jeff, IsNot, With, rivera);
		addFact("13", getList(roughRider, oHara, tomlin, wilkins), IsNot, With);

		// Solution.
		answer = new int[][]{{0, 2, 3, 4, 1}, {2, 3, 1, 0, 4}, {2, 4, 1, 3, 0}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=First Name, 2=Last Name, 3=Nickname, 4=Beach
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 3:
						msg = noun1.name + "'s nickname " + verb.name + " " + noun2.name;
						break;
					case 4:
						msg = noun1.name + " " + verb.name + " from " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg = noun1.name + "'s nickname " + verb.name + " " + noun2.name;
						break;
					case 4:
						msg = noun1.name + " " + verb.name + " from " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						msg = noun1.name + " " + verb.name + " from " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						msg = "The " + noun1.name + " surfer " + verb.name + " " + noun2.name;
						break;
					case 2: break;
					case 3:
						msg = "The " + noun1.name + " surfer " + verb.name + " " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}
		return msg + ".";
	}
}
