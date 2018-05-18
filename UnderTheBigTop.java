package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class UnderTheBigTop extends Puzzle {
	UnderTheBigTop() {
		// Properties.
		myName = "UnderTheBigTop";
		myTitle = "Under The Big Top";

		// Nouns.
		NounType names = addNounType("Name");
		Noun cassandra = names.addNoun("Cassandra");
		Noun delilah = names.addNoun("Delilah");
		Noun esmeralda = names.addNoun("Esmeralda");

		NounType novelties = addNounType("Novelty");
		Noun bearded = novelties.addNoun("Bearded Lady", "Bearded");
		Noun strongest = novelties.addNoun("Strongest Woman", "Strongest");
		Noun tattooed = novelties.addNoun("Tattooed Lady", "Tattoed");

		NounType places = addNounType("Place");
		Noun borneo = places.addNoun("Borneo");
		Noun kadmandu = places.addNoun("Kadmandu");
		Noun patagonia = places.addNoun("Patagonia");

		// Facts.
		addFact("1", getList(tattooed, esmeralda, borneo), IsNot, With);
		addFact("2", patagonia, Is, With, strongest);
		addFact("3", cassandra, IsNot, With, kadmandu);

		// Solution.
		answer = new int[][]{{0, 2, 1}, {0, 1, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Name, 2=Novelty, 3=Place
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg = noun1.name + " " + verb.name + " from " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						msg = "The " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 2: break;
					case 3:
						msg = "The " + noun1.name + " " + verb.name + " from " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = "The woman from " + noun1.name + " " + verb.name + " the " + noun2.name;
						break;
					case 3: break;
				}
				break;
		}

		return msg + ".";
	}
}
