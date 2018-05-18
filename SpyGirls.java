package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class SpyGirls extends Puzzle {
	SpyGirls() {
		// Properties.
		myName = "SpyGirls";
		myTitle = "Spy Girls";

		// Nouns.
		NounType slots = addNounType("Rank");
		Noun agent1 = slots.addNoun("1st");
		Noun agent2 = slots.addNoun("2nd");
		Noun agent3 = slots.addNoun("3rd");
		Noun agent4 = slots.addNoun("4th");
		Noun agent5 = slots.addNoun("5th");

		NounType firstNames = addNounType("First Name");
		Noun jane = firstNames.addNoun("Jane");
		Noun sylvia = firstNames.addNoun("Sylvia");
		Noun hillary = firstNames.addNoun("Hillary");
		Noun amanda = firstNames.addNoun("Amanda");
		Noun kate = firstNames.addNoun("Kate");

		NounType lastNames = addNounType("Last Name");
		Noun brookes = lastNames.addNoun("Brookes");
		Noun adams = lastNames.addNoun("Adams");
		Noun darling = lastNames.addNoun("Darling");
		Noun miller = lastNames.addNoun("Miller");
		Noun cooper = lastNames.addNoun("Cooper");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link higherThan = addLink("higher than", slots);
		higherThan.f = SmartLink.getIsLessThan(0);

		// Facts.
		addFact("0", jane, IsNot, With, brookes);
		addFact("1", sylvia, Is, higherThan, adams);
		addFact("1", sylvia, IsNot, With, agent1);
		addFactsInSequence("2", getList(brookes, hillary, darling), Is, higherThan);
		addFact("3", amanda, Is, higherThan, getList(kate, hillary));
		addFact("3", amanda, IsNot, With, agent1);
		addFactsInSequence("4", getList(cooper, adams, miller), Is, higherThan);
		addFact("5", miller, IsNot, With, agent5);

		// Solution.
		answer = new int[][]{{0, 1, 3, 2, 4}, {4, 0, 1, 3, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Agent, 2=First Name, 3=Last Name
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
							msg = noun1.name + " " + verb.name + " ranked " + noun2.name;
						break;
					case 2:
						msg = noun1.name + " " + verb.name + " ranked " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " Agent " + noun2.name;
						else
							msg = noun1.name + " " + verb.name + " ranked " + link.name + " Agent " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "Agent " + noun1.name + " " + verb.name + " ranked " + noun2.name;
						break;
					case 2:
						if (link != With)
							msg = "Agent " + noun1.name + " " + verb.name + " ranked " + link.name + " " + noun2.name;
						break;
					case 3:
						msg = "Agent " + noun1.name + " " + verb.name + " ranked " + link.name + " Agent " + noun2.name;
						break;
				}
				break;
		}

		return msg + ".";
	}
}
