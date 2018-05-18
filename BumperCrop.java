package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class BumperCrop extends Puzzle {
	BumperCrop() {
		// Properties.
		myName = "BumperCrop";
		myTitle = "Bumper Crop";

		// Nouns.
		NounType slots = addNounType("Order");
		Noun slot1 = slots.addNoun("1st");
		Noun slot2 = slots.addNoun("2nd");
		Noun slot3 = slots.addNoun("3rd");
		Noun slot4 = slots.addNoun("4th");

		NounType vehicles = addNounType("Vehicle");
		Noun truck = vehicles.addNoun("truck");
		Noun car = vehicles.addNoun("car");
		Noun van = vehicles.addNoun("van");
		Noun wagon = vehicles.addNoun("wagon");

		NounType stickers = addNounType("Bumper Sticker");
		Noun murphy = stickers.addNoun("MURPHY WAS AN OPTIMIST", "MURPHY");
		Noun radioactive = stickers.addNoun("RADIO ACTIVE", "RADIO ACTIVE");
		Noun gotMalled = stickers.addNoun("I GOT MALLED!", "I GOT MALLED");
		Noun dottedLine = stickers.addNoun("TEAR ALONG DOTTED LINE!", "DOTTED LINE");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link ahead = addLink("ahead of", slots);
		ahead.f = SmartLink.getIsLessThan(0);

		// Facts.
		addFact("1", truck, IsNot, With, slot1);
		addFact("1", truck, Is, ahead, murphy);
		addFact("2", car, Is, ahead, radioactive);
		addFact("3", gotMalled, Is, With, slot4);
		addFactsInSequence("4", getList(dottedLine, van, wagon), Is, ahead);

		// Solution.
		answer = new int[][]{{1, 0, 2, 3}, {3, 1, 0, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Order, 2=Vehicle, 3=Bumper Sticker.
		// Links: 0=With, 1=ahead.
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
				}
				break;
			case 2:
				msg = "The " + noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += verb.name + " " + noun2.name + " in line";
						break;
					case 2:
						msg += verb.name + " " + link.name + " the " + noun2.name;
						break;
					case 3:
						if (link.num == 1)
							msg += verb.name + " " + link.name + " the vehicle whose bumper read " + noun2.name;
						break;
				}
				break;
			case 3:
				msg = "The vehicle whose bumper read " + noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += verb.name + " " + noun2.name + " in line";
						break;
					case 2:
						if (link.num == 1)
							msg += verb.name + " " + link.name + " the " + noun2.name;
						break;
					case 3: break;
				}
				break;
		}

		return msg + ".";
	}
}
