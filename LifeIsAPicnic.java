package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class LifeIsAPicnic extends Puzzle {
	LifeIsAPicnic() {
		// Properties.
		myName = "LifeIsAPicnic";
		myTitle = "Life Is A Picnic";

		// Nouns.
		NounType slots = addNounType("Class");
		Noun senior = slots.addNoun("senior");
		Noun junior = slots.addNoun("junior");
		Noun sophomore = slots.addNoun("sophomore");
		Noun freshman = slots.addNoun("freshman");

		NounType firstNames = addNounType("First Name");
		Noun billie = firstNames.addNoun("Billie");
		Noun josephine = firstNames.addNoun("Josephine");
		Noun marilyn = firstNames.addNoun("Marilyn");
		Noun priscilla = firstNames.addNoun("Priscilla");

		NounType lastNames = addNounType("Last Name");
		Noun arthur = lastNames.addNoun("Arthur");
		Noun jamison = lastNames.addNoun("Jamison");
		Noun morgan = lastNames.addNoun("Morgan");
		Noun sounder = lastNames.addNoun("Sounder");

		NounType towns = addNounType("Home Town");
		Noun boise = towns.addNoun("Boise");
		Noun minneapolis = towns.addNoun("Minneapolis");
		Noun stLouis = towns.addNoun("St. Louis");
		Noun tallahassee = towns.addNoun("Tallahassee");

		NounType roles = addNounType("Responsibility");
		Noun beverages = roles.addNoun("beverages");
		Noun entertainment = roles.addNoun("entertainment");
		Noun mainDishes = roles.addNoun("main dishes");
		Noun salads = roles.addNoun("salads");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link higherThan = addLink("in a higher class than", slots);
		higherThan.f = SmartLink.getIsLessThan(0);

		// Facts.
		addFact("1", jamison, IsNot, With, freshman);
		addFact("1", jamison, Is, With, entertainment);
		addFact("2", marilyn, Is, With, sounder);
		addFactsInSequence("2", getList(marilyn, josephine, billie), Is, higherThan);
		addFact("3", junior, IsNot, With, priscilla);
		addFact("3", junior, Is, With, boise);
		addFact("4", senior, IsNot, With, getList(stLouis, beverages));
		addFact("5", salads, IsNot, With, getList(boise, freshman));
		addFact("5", priscilla, Is, higherThan, salads);
		addFact("6", sophomore, Is, With, tallahassee);
		addFact("7", billie, IsNot, With, getList(arthur, beverages));

		// Solution.
		answer = new int[][]{{3, 2, 1, 0}, {1, 3, 0, 2}, {1, 0, 3, 2}, {1, 0, 3, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Class, 2=First Name, 3=Last Name, 4=Hometown, 5=Responsibility
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " from " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " in charge of " + noun2.name;
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
							msg = noun1.name + " " + verb.name + " Ms " + noun2.name;
						break;
					case 4: break;
					case 5:
						if (link == With)
							msg = noun1.name + " " + verb.name + " in charge of " + noun2.name;
						else
							msg = noun1.name + " " + verb.name + " " + link.name + " the woman in charge of " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "Ms " + noun1.name + " " + verb.name + " a " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5:
						if (link == With)
							msg = "Ms " + noun1.name + " " + verb.name + " in charge of " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The woman in charge of " + noun1.name + " " + verb.name + " the " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The woman in charge of " + noun1.name + " " + verb.name + " from " + noun2.name;
						break;
					case 5: break;
				}
				break;
		}

		return msg + ".";
	}
}
