package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class SmithJonesRobinson extends Puzzle {
	SmithJonesRobinson() {
		// Properties.
		myName = "SmithJonesRobinson";
		myTitle = "The Smith-Jones-Robinson Classic";

		// Nouns.
		NounType towns = addNounType("City");
		Noun chicago = towns.addNoun("Chicago");
		Noun detroit = towns.addNoun("Detroit");
		Noun halfway = towns.addNoun("Halfway");

		NounType workers = addNounType("Worker");
		Noun fireman = workers.addNoun("fireman");
		Noun brakeman = workers.addNoun("brakeman");
		Noun engineer = workers.addNoun("engineer");

		NounType lastNames = addNounType("Last Name");
		Noun smith = lastNames.addNoun("Smith");
		Noun robinson = lastNames.addNoun("Robinson");
		Noun jones = lastNames.addNoun("Jones");

		NounType businessMen = addNounType("Businessman");
		Noun mrSmith = businessMen.addNoun("Mr. Smith");
		Noun mrRobinson = businessMen.addNoun("Mr. Robinson");
		Noun mrJones = businessMen.addNoun("Mr. Jones");

		// Facts.
		addFact("1", mrRobinson, Is, With, detroit);
		addFact("2", brakeman, Is, With, halfway);
		addFact("4", mrJones, IsNot, With, brakeman);
		addFact("5", smith, IsNot, With, fireman);

		// Rules.
		Rule rule1 = addRule("6", "The businessman with the same name as the brakeman lives in Chicago.");
		rule1.f = (mark) -> {
			int rs = 0;

			// Violation if businessman in Chicago and the brakeman's last name are not the same.
			Noun nameA = Mark.getPairNoun(chicago, businessMen);
			Noun nameB = Mark.getPairNoun(brakeman, lastNames);
			if (nameA != null && nameB != null && nameA.num != nameB.num) return -1;

			// Trigger: (a) If a businessman is in Chicago, then the brakeman has the same last name.
			if (nameA != null && nameB == null) {
				String msg = "The last name of the businessman in Chicago is " + nameA + ".";
				rs = solver.addMarkByRule(mark, rule1, 'a', brakeman, Is, lastNames.nouns.get(nameA.num - 1), msg);
				if (rs != 0) return rs;
			}

			// Trigger: (b) If the brakeman's last name is known, the businessman in Chicago has the same last name. TODO

			return rs;
		};

		// Solution. There are two solutions to this puzzle, but both state the engineer is Smith.
		answer = new int[][]{{0, 2, 1}, {1, 0, 2}, {2, 1, 0}};
	}
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=City, 2=Worker, 3=Last Name, 4=Businessman
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						msg = "The " + noun1.name + " " + (verb == Is ? "lives" : "does not live") + " in " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4: break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + " " + verb.name + " the " + noun2.name;
						break;
					case 3: break;
					case 4: break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						msg = noun1.name + " " + (verb == Is ? "lives" : "does not live") + " in " + noun2.name;
						break;
					case 2:
						msg = noun1.name + " " + (verb == Is ? "lives" : "does not live") + " in the same city as the " + noun2.name;
						break;
					case 3: break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
