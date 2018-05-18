package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class CoinsOfTheRealm extends Puzzle {
	CoinsOfTheRealm() {
		// Properties.
		myName = "CoinsOfTheRealm";
		myTitle = "Coins Of The Realm";

		// Nouns.
		NounType coins = addNounType("Coin");
		Noun shilling = coins.addNoun("shilling");
		Noun florin = coins.addNoun("florin");
		Noun halfCrown = coins.addNoun("half-crown");
		Noun crown = coins.addNoun("crown");
		Noun halfSovereign = coins.addNoun("half-sovereign");

		NounType firstNames = addNounType("First Name");
		Noun celia = firstNames.addNoun("Celia");
		Noun dennis = firstNames.addNoun("Dennis");
		Noun kenneth = firstNames.addNoun("Kenneth");
		Noun mary = firstNames.addNoun("Mary");
		Noun paul = firstNames.addNoun("Paul");

		NounType lastNames = addNounType("Surname");
		Noun andrews = lastNames.addNoun("Andrews");
		Noun bennett = lastNames.addNoun("Bennett");
		Noun irwin = lastNames.addNoun("Irwin");
		Noun masters = lastNames.addNoun("Masters");
		Noun walters = lastNames.addNoun("Walters");

		NounType monarchs = addNounType("Monarch");
		Noun anne = monarchs.addNoun("Anne");
		Noun georgeI = monarchs.addNoun("George I");
		Noun georgeIII = monarchs.addNoun("George III");
		Noun victoria = monarchs.addNoun("Victoria");
		Noun edwardVII = monarchs.addNoun("Edward VII");

		// Links.
		Link higherThan = addLink("higher than", coins);
		higherThan.f = SmartLink.getIsMoreThan(0);

		// Facts.
		addFact("1", getList(florin, halfSovereign), IsNot, With, getList(anne, georgeI, georgeIII));

		addFact("2", celia, Is, With, bennett);
		addFact("2", celia, IsNot, With, victoria);
		addFact("2", celia, Is, higherThan, georgeI);

		addFact("3", mary, IsNot, With, getList(anne, victoria));

		addFact("4", irwin, Is, With, halfSovereign);
		addFact("4", andrews, IsNot, With, halfCrown);

		addFact("5", georgeIII, Is, With, walters);
		addFact("5", georgeIII, Is, higherThan, dennis);

		addFact("6", paul, Is, With, anne);
		addFact("6", paul, IsNot, With, crown);

		// Solution.
		answer = new int[][]{{1, 0, 4, 3, 2}, {0, 1, 3, 4, 2}, {1, 4, 0, 2, 3}};
	}

	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Coin, 2=First Name, 3=Last Name, 4=Monarch
		switch (noun1.type.num) {
			case 1:
				msg = "The " + noun1.name;
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg += " " + (verb == Is ? "belongs" : "does not belong") + " to " + noun2.name;
						else
							msg += "'s value " + verb.name + " " + link.name + " the coin belonging to " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg += " " + verb.name + " from " + noun2.name + "'s reign";
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						msg = noun1.name + "'s coin ";
						if (link == With)
							msg += verb.name;
						else
							msg += (verb == Is ? "has" : "does not have") + " a " + link.name;
						msg += " the " + noun2.name;
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s surname " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s coin " + verb.name + " from " + noun2.name + "'s reign";
						else
							msg = noun1.name + "'s coin " + verb.name + " valued " + link.name + " the coin from " + noun2.name + "'s reign";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						msg = noun1.name + "'s coin ";
						if (link == With)
							msg += verb.name;
						else
							msg += (verb == Is ? "has" : "does not have") + " a " + link.name;
						msg += " the " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4: break;
				}
				break;
			case 4:
				msg = "The " + noun1.name + " coin ";
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link != With)
							msg += verb.name + " valued " + link.name + " " + noun2.name + "'s coin";
						break;
					case 3:
						if (link == With)
							msg += (verb == Is ? "belongs" : "does not belong") + " to " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
