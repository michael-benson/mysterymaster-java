package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class OhChristmasTree extends Puzzle {
	OhChristmasTree() {
		// Properties.
		myName = "OhChristmasTree";
		myTitle = "Oh Christmas Tree";

		// Nouns.
		NounType gifts = addNounType("Gift");
		Noun giftA = gifts.addNoun("A");
		Noun giftB = gifts.addNoun("B");
		Noun giftC = gifts.addNoun("C");
		Noun giftD = gifts.addNoun("D");

		NounType colors = addNounType("Color");
		Noun blue = colors.addNoun("blue");
		Noun gold = colors.addNoun("gold");
		Noun green = colors.addNoun("green");
		Noun red = colors.addNoun("red");

		NounType girls = addNounType("Girl");
		Noun alison = girls.addNoun("Alison");
		Noun nicola = girls.addNoun("Nicola");
		Noun rebecca = girls.addNoun("Rebecca");
		Noun zoe = girls.addNoun("Zoe");

		NounType ages = addNounType("Age");
		Noun age06 = ages.addNoun("6");
		Noun age08 = ages.addNoun("8");
		Noun age10 = ages.addNoun("10");
		Noun age12 = ages.addNoun("12");

		// Links.
		Link justLeftOf = addLink("immediately to the left of", gifts);
		justLeftOf.f = SmartLink.getIsLessBy(1);

		// Facts.
		addFact("1", alison, Is, With, age10);
		addFact("1", alison, IsNot, With, blue);
		addFact("1", alison, Is, justLeftOf, age08);
		addFact("2", red, Is, With, age12);
		addFact("2", age12, IsNot, With, zoe);
		addFact("3", nicola, Is, With, green);
		addFact("3", nicola, IsNot, With, getList(giftB, giftC));
		addFact("4", giftB, Is, With, age06);

		// Solution.
		answer = new int[][]{{3, 0, 1, 2}, {2, 3, 0, 1}, {3, 0, 2, 1}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Gift, 2=Color, 3=Girl, 4=Age
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "Gift " + noun1.name + " " + verb.name + " for the " + noun2.name + "-year-old";
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The gift wrapped in " + noun1.name + " " + verb.name + " for the " + noun2.name + "-year-old";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + "'s gift " + verb.name + " gift " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = noun1.name + "'s gift " + verb.name + " wrapped in " + noun2.name + " paper";
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name + "-year-old";
						else
							msg = noun1.name + "'s gift " + verb.name + " " + link.name + " her " + noun2.name + "-year-old sister's gift";
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "The " + noun1.name + "-year-old " + verb.name + " " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
