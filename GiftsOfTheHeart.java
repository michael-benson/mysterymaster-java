package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class GiftsOfTheHeart extends Puzzle{
	GiftsOfTheHeart() {
		// Properties.
		myName = "GiftsOfTheHeart";
		myTitle = "Gifts Of The Heart";

		// Nouns.
		NounType names = addNounType("Child");
		Noun forest = names.addNoun("Forest");
		Noun holly = names.addNoun("Holly");
		Noun kelly = names.addNoun("Kelly");
		Noun moss = names.addNoun("Moss");
		Noun olive = names.addNoun("Olive");

		NounType items = addNounType("Item");
		Noun aluminum = items.addNoun("aluminum");
		Noun brownGlass = items.addNoun("brown glass");
		Noun greenGlass = items.addNoun("green glass");
		Noun paper = items.addNoun("paper");
		Noun plastic = items.addNoun("plastic");

		NounType projects1 = addNounType("Project1");
		Noun birdseed = projects1.addNoun("birdseed");
		Noun catFood = projects1.addNoun("cat food");
		Noun pineCones = projects1.addNoun("pine cones");
		Noun popcorn = projects1.addNoun("popcorn");
		Noun towels = projects1.addNoun("towels");

		NounType projects2 = addNounType("Project2");
		Noun cannedFood = projects2.addNoun("canned food");
		Noun cookies = projects2.addNoun("cookies");
		Noun magazines = projects2.addNoun("magazines");
		Noun snow = projects2.addNoun("snow");
		Noun toys = projects2.addNoun("toys");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Facts.
		addFact("1", getList(forest, cookies, towels), IsNot, With);
		addFact("1", forest, Is, With, paper);
		addFact("2", popcorn, Is, With, magazines);
		addFact("2", holly, IsNot, With, popcorn);
		addFact("2", holly, Is, With, greenGlass);
		addFact("3", getList(olive, towels, brownGlass), IsNot, With);
		addFact("3", olive, Is, With, cannedFood);
		addFact("4", paper, Is, With, pineCones);
		addFact("4", paper, IsNot, With, toys);
		addFact("5", catFood, IsNot, With, aluminum);
		addFact("5", kelly, IsNot, With, aluminum);
		addFact("6", moss, Is, With, birdseed);

		// Solution.
		answer = new int[][]{{3, 2, 1, 0, 4}, {2, 4, 3, 0, 1}, {3, 4, 2, 1, 0}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Child, 2=Item, 3=Project1, 4=Project2
		switch(noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + " " + (verb == Is ? "collected" : "did not collect") + " " + noun2.name;
						break;
					case 3:
						msg = noun1.name + "'s project " + verb.name + " " + noun2.name;
						break;
					case 4:
						msg = noun1.name + "'s project " + verb.name + " " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg = "The child who collected " + noun1.name + " " + verb.name + " the one with the " + noun2.name + " project";
						break;
					case 4:
						msg = "The child who collected " + noun1.name + " " + verb.name + " the one with the " + noun2.name + " project";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = "The child with the " + noun1.name + " project " + (verb == Is ? "collected" : "did not collect") + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						msg = "One child's projects " + (verb == Is ? "are" : "are not") + " " + noun1.name + " and " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = "The child with the " + noun1.name + " project " + (verb == Is ? "collected" : "did not collect") + " " + noun2.name;
						break;
					case 3:
						msg = "One child's projects " + (verb == Is ? "are" : "are not") + " " + noun1.name + " and " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
