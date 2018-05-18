package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class WeekendTrekkies extends Puzzle {
	WeekendTrekkies() {
		// Properties.
		myName = "WeekendTrekkies";
		myTitle = "Weekend Trekkies";

		// Nouns.
		NounType ages = addNounType("Age");
		Noun age1 = ages.addNoun("1st"); // Oldest
		Noun age2 = ages.addNoun("2nd");
		Noun age3 = ages.addNoun("3rd");
		Noun age4 = ages.addNoun("4th");
		Noun age5 = ages.addNoun("5th");
		Noun age6 = ages.addNoun("6th"); // Youngest

		NounType boys = addNounType("Boy");
		Noun carlos = boys.addNoun("Carlos");
		Noun abdul = boys.addNoun("Abdul");
		Noun barney = boys.addNoun("Barney");
		Noun doHyong = boys.addNoun("Do Hyong");
		Noun elijah = boys.addNoun("Elijah");
		Noun felix = boys.addNoun("Felix");

		NounType roles = addNounType("Character");
		Noun mumu = roles.addNoun("Mumu");
		Noun drMcRoy = roles.addNoun("Dr. McRoy");
		Noun mrSperk = roles.addNoun("Mr. Sperk");
		Noun chekhout = roles.addNoun("Chekhout");
		Noun spotty = roles.addNoun("Spotty");
		Noun captain = roles.addNoun("Cap Crick");

		NounType aliens = addNounType("Alien");
		Noun ukvor = aliens.addNoun("Ukvor");
		Noun xyptrx = aliens.addNoun("Xyptrx");
		Noun tmalyf = aliens.addNoun("Tmalyf");
		Noun zrkont = aliens.addNoun("Zrkont");
		Noun yqtak = aliens.addNoun("Yqtak");
		Noun wxptn = aliens.addNoun("Wxptn");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link justOlderThan = addLink("just older than", ages);
		justOlderThan.f = SmartLink.getIsLessBy(1);

		Link olderThan = addLink("older than", ages);
		olderThan.f = SmartLink.getIsLessThan(0);

		// Facts.
		addFactsInSequence("1", getList(carlos, mumu, xyptrx, abdul, tmalyf, drMcRoy), Is, justOlderThan);
		addFact("2", mrSperk, Is, olderThan, barney);
		addFact("2", mrSperk, Is, With, zrkont);
		addFact("3", getList(chekhout, spotty), IsNot, With, xyptrx);
		addFact("3", spotty, IsNot, With, tmalyf);
		addFact("4", doHyong, IsNot, With, xyptrx);
		addFact("5", chekhout, Is, olderThan, elijah);
		addFact("5", elijah, IsNot, With, getList(xyptrx, yqtak));
		addFact("6", felix, IsNot, With, getList(captain, chekhout, wxptn, yqtak));

		// Solution.
		answer = new int[][]{{0, 5, 2, 1, 3, 4}, {2, 0, 5, 4, 3, 1}, {3, 0, 1, 4, 2, 5}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Age, 2=Boy, 3=Character, 4=Alien
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
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s character " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s enemy " + verb.name + " " + noun2.name;
						else
							msg = noun1.name + " " + verb.name + " " + link.name + " the boy who battles " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link != With)
							msg = "The boy who plays " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = noun1 + " " + (verb == Is ? "battles" : "does not battle") + " " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link != With)
							msg = "The boy who battles " + noun1.name + " " + verb.name + " " + link.name + " the boy who plays " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
