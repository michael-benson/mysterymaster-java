package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class ExtraKeys extends Puzzle {
	ExtraKeys() {
		// Properties.
		myName = "ExtraKeys";
		myTitle = "Extra Keys";

		// Nouns.
		NounType slots = addNounType("Order");
		Noun slot1 = slots.addNoun("1st");
		Noun slot2 = slots.addNoun("2nd");
		Noun slot3 = slots.addNoun("3rd");
		Noun slot4 = slots.addNoun("4th");
		Noun slot5 = slots.addNoun("5th");

		NounType firstNames = addNounType("First Name");
		Noun john = firstNames.addNoun("John");
		Noun linda = firstNames.addNoun("Linda");
		Noun mary = firstNames.addNoun("Mary");
		Noun sam = firstNames.addNoun("Sam");
		Noun susan = firstNames.addNoun("Susan");

		NounType lastNames = addNounType("Last Name");
		Noun jones = lastNames.addNoun("Jones");
		Noun larkin = lastNames.addNoun("Larkin");
		Noun mills = lastNames.addNoun("Mills");
		Noun silkwood = lastNames.addNoun("Silkwood");
		Noun smith = lastNames.addNoun("Smith");

		NounType keys = addNounType("Key");
		Noun boat = keys.addNoun("boat");
		Noun car = keys.addNoun("car");
		Noun garage = keys.addNoun("garage");
		Noun house = keys.addNoun("house");
		Noun office = keys.addNoun("office");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link beforeButNotJust = addLink("before, but not just before", slots);
		beforeButNotJust.f = SmartLink.getIsLessThan(1);

		Link before = addLink("before", slots);
		before.f = SmartLink.getIsLessThan(0);

		// Facts.
		List<Noun> guys = getList(john, sam);

		addFactsIsNotFirstChar("1", firstNames, lastNames, true);

		List<Noun> endSlots = getList(slot1, slot5);
		addFactsOneToOne("2", endSlots, Is, With, getList(boat, car));
		addFact("2", endSlots, IsNot, With, guys);

		addFact("3", sam, Is, beforeButNotJust, john);
		addFact("4", john, IsNot, With, house);
		addFact("5", smith, IsNot, With, garage);
		addFact("6", susan, Is, With, office);
		addFact("7", garage, Is, before, mills);
		addFact("8", larkin, IsNot, With, office);

		// Solution.
		answer = new int[][]{{2, 3, 4, 0, 1}, {4, 1, 0, 3, 2}, {0, 3, 4, 2, 1}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Order, 2=First Name, 3=Last Name, 4=Key
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = "The " + noun1.name + " customer " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + " customer " + (verb == Is ? "did" : "did not") + " duplicate the " + noun2.name + " key";
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = "The last name of " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "did" : "did not") + " duplicate the " + noun2.name + " key";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "did" : "did not") + " duplicate the " + noun2.name + " key";
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link != With)
							msg = "The " + noun1.name + " key " + verb.name + " duplicated " + link.name + " " + noun2.name + " got a key";
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
