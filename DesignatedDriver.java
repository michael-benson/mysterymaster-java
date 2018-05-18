package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class DesignatedDriver extends Puzzle {
	DesignatedDriver() {
		// Properties.
		myName = "DesignatedDriver";
		myTitle = "The Designated Driver";

		// Nouns.
		NounType cards = addNounType("Card");
		Noun ace = cards.addNoun("ace");
		Noun king = cards.addNoun("king");
		Noun queen = cards.addNoun("queen");
		Noun jack = cards.addNoun("jack");
		Noun ten = cards.addNoun("ten");

		NounType names = addNounType("Name");
		Noun jerry = names.addNoun("Jerry");
		Noun sylvia = names.addNoun("Sylvia");
		Noun george = names.addNoun("George");
		Noun amanda = names.addNoun("Amanda");
		Noun john = names.addNoun("John");

		NounType roles = addNounType("Occupation");
		Noun broker = roles.addNoun("broker");
		Noun attorney = roles.addNoun("attorney");
		Noun doctor = roles.addNoun("doctor");
		Noun mechanic = roles.addNoun("mechanic");
		Noun banker = roles.addNoun("banker");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link higherThan = addLink("higher than", cards);
		higherThan.f = SmartLink.getIsLessThan(0);

		// Facts.
		addFact("0", jerry, IsNot, With, broker);
		addFact("1", sylvia, Is, higherThan, attorney);
		addFact("1", sylvia, IsNot, With, ace);
		addFactsInSequence("2", getList(broker, george, doctor), Is, higherThan);
		addFact("3", amanda, Is, higherThan, getList(john, george));
		addFact("3", amanda, IsNot, With, ace);
		addFactsInSequence("4", getList(banker, attorney, mechanic), Is, higherThan);
		addFact("5", mechanic, IsNot, With, ten);

		// Solution.
		answer = new int[][]{{0, 1, 3, 2, 4}, {4, 0, 1, 3, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Card, 2=Name, 3=Occupation
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
							msg = noun1.name + "'s card " + verb.name + " the " + noun2.name;
						break;
					case 2:
						msg = noun1.name + "'s card " + verb.name + " " + link.name + " " + noun2.name + "'s card";
						break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name;
						else
							msg = noun1.name + "'s card " + verb.name + " " + link.name + " the " + noun2.name + "'s card";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The " + noun1.name + "'s card " + verb.name + " the " + noun2.name;
						break;
					case 2:
						if (link != With)
							msg = "The " + noun1.name + "'s card " + verb.name + " " + link.name + " " + noun2.name + "'s card";
						break;
					case 3:
						msg = "The " + noun1.name + "'s card " + verb.name + " " + link.name + " the " + noun2.name + "'s card";
						break;
				}
				break;
		}

		return msg + ".";
	}
}
