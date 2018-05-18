package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class WinterBreaks extends Puzzle {
	WinterBreaks() {
		// Properties.
		myName = "WinterBreaks";
		myTitle = "Winter Breaks";

		// Nouns.
		NounType names = addNounType("Name");
		Noun bob = names.addNoun("Bob");
		Noun donald = names.addNoun("Donald");
		Noun gerry = names.addNoun("Gerry");
		Noun michael = names.addNoun("Michael");
		Noun roger = names.addNoun("Roger");

		NounType towns = addNounType("Hometown");
		Noun hartlepool = towns.addNoun("Hartlepool");
		Noun lynmouth = towns.addNoun("Lynmouth");
		Noun stoke = towns.addNoun("Stoke");
		Noun welshpool = towns.addNoun("Welshpool");
		Noun yarmouth = towns.addNoun("Yarmouth");

		NounType areas = addNounType("Area");
		Noun austrianAlps = areas.addNoun("Austrian Alps");
		Noun cairngorms = areas.addNoun("Cairngorms");
		Noun frenchAlps = areas.addNoun("French Alps");
		Noun italianAlps = areas.addNoun("Italian Alps");
		Noun swissAlps = areas.addNoun("Swiss Alps");

		NounType breaks = addNounType("Break");
		Noun collarbone = breaks.addNoun("collarbone");
		Noun rightArm = breaks.addNoun("right arm");
		Noun rightLeg = breaks.addNoun("right leg");
		Noun leftArm = breaks.addNoun("left arm");
		Noun leftLeg = breaks.addNoun("left leg");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Facts.
		addFact("1", collarbone, Is, With, austrianAlps);
		addFact("2", stoke, IsNot, With, getList(rightArm, leftArm, gerry));
		addFact("2", gerry, IsNot, With, getList(rightLeg, leftLeg));
		addFact("3", roger, IsNot, With, cairngorms);
		addFact("4", bob, Is, With, lynmouth);
		addFact("4", bob, IsNot, With, collarbone);
		addFact("5", michael, IsNot, With, welshpool);
		addFact("5", michael, Is, With, rightArm);
		addFact("6", yarmouth, Is, With, italianAlps);
		addFact("7", donald, Is, With, frenchAlps);
		addFact("7", donald, IsNot, With, getList(leftArm, leftLeg));
		addFact("8", leftLeg, Is, With, hartlepool);

		// Solution.
		answer = new int[][]{{1, 2, 3, 4, 0}, {1, 2, 0, 3, 4}, {3, 2, 0, 1, 4}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Name, 2=Hometown, 3=Area, 4=Break
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + " " + verb.name + " from " + noun2.name;
						break;
					case 3:
						msg = noun1.name + " " + verb.name + " in the " + noun2.name;
						break;
					case 4:
						msg = noun1.name + " " + (verb == Is ? "broke" : "did not break") + " his " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						msg = "The man from " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 2: break;
					case 3:
						msg = "The man from " + noun1.name + " " + verb.name + " in the " + noun2.name;
						break;
					case 4:
						msg = "The man from " + noun1.name + " " + (verb == Is ? "broke" : "did not break") + " his " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = "The man who broke his " + noun1.name + " " + verb.name + " from " + noun2.name;
						break;
					case 3:
						msg = "The " + noun1.name + " " + verb.name + " broken in the " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
