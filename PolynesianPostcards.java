package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class PolynesianPostcards extends Puzzle {
	PolynesianPostcards() {
		// Properties.
		myName = "PolynesianPostcards";
		myTitle = "Polynesian Postcards";

		// Nouns.
		NounType friends = addNounType("Friend");
		Noun andy = friends.addNoun("Andy");
		Noun charlotte = friends.addNoun("Charlotte");
		Noun rick = friends.addNoun("Rick");
		Noun sally = friends.addNoun("Sally");
		Noun tom = friends.addNoun("Tom");

		NounType islands = addNounType("Island");
		Noun boraBora = islands.addNoun("Bora Bora");
		Noun huahine = islands.addNoun("Huahine");
		Noun moorea = islands.addNoun("Moorea");
		Noun raiatea = islands.addNoun("Raiatea");
		Noun tahiti = islands.addNoun("Tahiti");

		NounType postcards = addNounType("Postcard");
		Noun beach = postcards.addNoun("beach");
		Noun palmTrees = postcards.addNoun("palm trees");
		Noun seashells = postcards.addNoun("seashells");
		Noun fish = postcards.addNoun("tropical fish", "Fish");
		Noun waterfall = postcards.addNoun("waterfall");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Facts.
		List<Noun> list1 = getList(andy, rick, tahiti);
		addFact("1", list1, IsNot, With);
		addFact("1", list1, IsNot, With, getList(palmTrees, fish));

		List<Noun> list2 = getList(huahine, raiatea, tahiti);
		addFact("2", getList(charlotte, fish), IsNot, With, list2);
		addFact("2", fish, IsNot, With, getList(charlotte, sally));

		addFact("3", getList(andy, waterfall, raiatea), IsNot, With);

		addFact("4", palmTrees, IsNot, With, moorea);

		addFact("5", rick, IsNot, With, seashells);

		// Solution.
		answer = new int[][]{{1, 0, 3, 4, 2}, {2, 1, 0, 4, 3}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Friend, 2=Island, 3=PostCard
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + "'s postcard " + verb.name + " from " + noun2.name;
						break;
					case 3:
						msg = noun1.name + "'s postcard " + (verb == Is ? "did" : "did not") + " show the " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg = "The postcard from " + noun1.name + " " + (verb == Is ? "did" : "did not") + " show the " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						msg = "The postcard showing the " + noun1.name + " " + verb.name + " received by " + noun2.name;
						break;
					case 2:
						msg = "The postcard showing the " + noun1.name + " " + verb.name + " from " + noun2.name;
						break;
					case 3: break;
				}
				break;
		}

		return msg + ".";
	}
}
