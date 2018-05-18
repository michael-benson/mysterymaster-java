package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class AirTurbulence extends Puzzle{
	AirTurbulence() {
		// Properties.
		myName = "AirTurbulence";
		myTitle = "Air Turbulence";

		// Nouns.
		NounType runways = addNounType("Runway");
		Noun runway2 = runways.addNoun("2");
		Noun runway3 = runways.addNoun("3");
		Noun runway4 = runways.addNoun("4");
		Noun runway6 = runways.addNoun("6");

		NounType officials = addNounType("Official");
		Noun mayor = officials.addNoun("mayor");
		Noun attorney = officials.addNoun("attorney");
		Noun justice = officials.addNoun("justice");
		Noun dogcatcher = officials.addNoun("dogcatcher");

		NounType acts = addNounType("Grooming");
		Noun beardTrim = acts.addNoun("beard-trim");
		Noun manicure = acts.addNoun("manicure");
		Noun shampoo = acts.addNoun("shampoo");
		Noun dental = acts.addNoun("teeth-clean");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link oneMoreThan = addLink("one more than", runways);
		oneMoreThan.f = (noun1, noun2) -> (noun1.num == noun2.num + 1 && noun1.num != 4) ? Is : IsNot;

		Link higherThan = addLink("higher-numbered than", runways);
		higherThan.f = SmartLink.getIsMoreThan(0);

		Link twiceThan = addLink("twice higher than", runways);
		twiceThan.f = (noun1, noun2) -> (noun1.num == 3 && noun2.num == 1) || (noun1.num == 4 && noun2.num == 2) ? Is : IsNot;

		// Facts.
		addFact("1", attorney, IsNot, With, runway3);
		addFact("2", shampoo, Is, oneMoreThan, manicure);
		addFact("3", mayor, Is, higherThan, dogcatcher);
		addFact("4", justice, Is, twiceThan, dental);
		addFact("5", attorney, IsNot, With, beardTrim);

		// Solution.
		answer = new int[][]{{1, 3, 2, 0}, {3, 1, 2, 0}};
	}

	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
		String lname = link == With ? " " : " " + link.name + " ";

		// Types: 1=Runway, 2=Official, 3=Act
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
						msg += verb.name + lname + "on runway " + noun2.name;
						break;
					case 2:
						msg += verb.name + " on a runway" + lname + "the " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg += (verb == Is ? "did" : "did not") + " have the " + noun2.name;
						else
							msg += verb.name + " on a runway" + lname + "the " + noun2.name;
						break;
				}
				break;
			case 3:
				msg = "The " + noun1.name + " ";
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg += verb.name + " on a runway" + lname + "the " + noun2.name;
						break;
				}
				break;
		}

		return msg + ".";
	}
}
