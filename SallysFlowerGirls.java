package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class SallysFlowerGirls extends Puzzle {
	SallysFlowerGirls() {
		// Properties.
		myName = "SallysFlowerGirls";
		myTitle = "Sally's Flower Girls";

		// Nouns.
		NounType slots = addNounType("Order");
		Noun slot1 = slots.addNoun("1st");
		Noun slot2 = slots.addNoun("2nd");
		Noun slot3 = slots.addNoun("3rd");
		Noun slot4 = slots.addNoun("4th");

		NounType firstNames = addNounType("First Name");
		Noun erica = firstNames.addNoun("Erica");
		Noun mandy = firstNames.addNoun("Mandy");
		Noun nicole = firstNames.addNoun("Nicole");
		Noun staci = firstNames.addNoun("Staci");

		NounType lastNames = addNounType("Last Name");
		Noun brown = lastNames.addNoun("Brown");
		Noun clark = lastNames.addNoun("Clark");
		Noun jones = lastNames.addNoun("Jones");
		Noun smith = lastNames.addNoun("Smith");

		NounType colors = addNounType("Color");
		Noun blue = colors.addNoun("blue");
		Noun pink = colors.addNoun("pink");
		Noun white = colors.addNoun("white");
		Noun yellow = colors.addNoun("yellow");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link before = addLink("before", slots);
		before.f = SmartLink.getIsLessThan(0);

		// Facts.
		addFact("2", mandy, Is, before, jones);
		addFact("3", pink, IsNot, With, getList(nicole, slot1));
		addFactsInSequence("4", getList(erica, clark, yellow), Is, before);
		addFact("5", nicole, IsNot, With, blue);
		addFact("6", pink, Is, before, white);
		addFact("7", nicole, Is, before, brown);
		addFact("8", clark, IsNot, With, pink);

		// Solution.
		answer = new int[][]{{1, 0, 2, 3}, {3, 2, 1, 0}, {0, 1, 2, 3}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Order, 2=First Name, 3=Last Name, 4=Color
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
						if (link != With)
							msg = noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name + " girl";
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "carried" : "did not carry") + " the " + noun2.name + " flowers.";
						else
							msg = noun1.name + " " + (verb == Is ? "carried" : "did not carry") + " the " + noun2.name + " flowers.";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + " girl " + (verb == Is ? "carried" : "did not carry") + " the " + noun2.name + " flowers.";
						else
							msg = "The " + noun1.name + " girl " + verb.name + " " + link.name + " the girl with the " + noun2.name + " flowers";
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The girl who carried " + noun1.name + " flowers " + verb.name + " " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = "The girl who carried " + noun1.name + " flowers " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						msg = "The girl with " + noun1.name + " flowers " + verb.name + " " + link.name + " the girl with " + noun2.name + " flowers";
						break;
				}
				break;
		}

		return msg + ".";
	}
}
