package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class NestedDolls extends Puzzle {
	NestedDolls() {
		// Properties.
		myName = "NestedDolls";
		myTitle = "Nested Dolls";

		// Nouns.
		NounType sizes = addNounType("Size");
		Noun size1 = sizes.addNoun("1st"); // Largest
		Noun size2 = sizes.addNoun("2nd");
		Noun size3 = sizes.addNoun("3rd");
		Noun size4 = sizes.addNoun("4th");
		Noun size5 = sizes.addNoun("5th"); // Smallest

		NounType names = addNounType("Name");
		Noun louise = names.addNoun("Louise");
		Noun maria = names.addNoun("Maria");
		Noun nancy = names.addNoun("Nancy");
		Noun olivia = names.addNoun("Olivia");
		Noun priscilla = names.addNoun("Priscilla");

		NounType colors = addNounType("Color");
		Noun blue = colors.addNoun("blue");
		Noun pink = colors.addNoun("pink");
		Noun red = colors.addNoun("red");
		Noun white = colors.addNoun("white");
		Noun yellow = colors.addNoun("yellow");

		// Links.
		Link smallerThan = addLink("smaller than", sizes);
		smallerThan.f = SmartLink.getIsMoreThan(0);

		Link justSmallerThan = addLink("just smaller than", sizes);
		justSmallerThan.f = SmartLink.getIsMoreBy(1);

		// Facts.
		addFact("1", nancy, Is, With, white);
		addFact("1", nancy, IsNot, With, getList(size5, size1));
		addFact("2", maria, Is, justSmallerThan, blue);
		addFact("3", louise, Is, justSmallerThan, red);
		addFact("4", olivia, Is, smallerThan, yellow);
		addFact("5", size3, IsNot, With, getList(red, white, yellow));

		// Solution.
		answer = new int[][]{{4, 2, 3, 1, 0}, {4, 3, 0, 2, 1}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Size, 2=Name, 3=Color
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "The " + noun1.name + " doll " + verb.name + " " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name + " doll";
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name + " doll";
						else
							msg = noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name + " doll";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
				}
				break;
		}

		return msg + ".";
	}
}
