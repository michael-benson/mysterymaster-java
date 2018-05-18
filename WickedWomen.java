package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class WickedWomen extends Puzzle {
	WickedWomen() {
		// Properties.
		myName = "WickedWomen";
		myTitle = "Wicked Women";

		// Nouns.
		NounType victims = addNounType("Victim");
		Noun victim1 = victims.addNoun("1st");
		Noun victim2 = victims.addNoun("2nd");
		Noun victim3 = victims.addNoun("3rd");
		Noun victim4 = victims.addNoun("4th");

		NounType deeds = addNounType("Deed");
		Noun blackmail = deeds.addNoun("blackmail");
		Noun kidnapping = deeds.addNoun("kidnapping");
		Noun perjury = deeds.addNoun("perjury");
		Noun murder = deeds.addNoun("murder");

		NounType targets = addNounType("Target");
		Noun bankPresident = targets.addNoun("bank president");
		Noun exHusband = targets.addNoun("ex-husband");
		Noun stateSenator = targets.addNoun("state senator");
		Noun tvExecutive = targets.addNoun("TV executive");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link before = addLink("sometime before", victims);
		before.f = SmartLink.getIsLessThan(0);

		// Facts.
		addFact("1", bankPresident, IsNot, With, getList(murder, victim2, victim3));
		addFact("2", perjury, IsNot, With, victim4);
		addFact("3", kidnapping, IsNot, With, getList(bankPresident, tvExecutive));
		addFact("4", exHusband, Is, before, perjury);
		addFactsInSequence("5", getList(blackmail, stateSenator, murder), Is, before);

		// Solution.
		answer = new int[][]{{0, 1, 2, 3}, {0, 1, 2, 3}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Victim, 2=Deed, 3=Target
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The victim of " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "The victim of " + noun1.name + " " + verb.name + " the " + noun2.name;
						else
							msg = "The act of " + noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " the " + noun2.name + " victim";
						break;
					case 2:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " the victim of " + noun2.name;
						else
							msg = "The " + noun1.name + " " + verb.name + " the victim " + link.name + " the act of " + noun2.name;
						break;
					case 3: break;
				}
				break;
		}

		return msg + ".";
	}
}
