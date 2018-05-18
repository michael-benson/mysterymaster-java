package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class IslandHopping extends Puzzle {
	IslandHopping() {
		// Properties.
		myName = "IslandHopping";
		myTitle = "Island Hopping";

		// Nouns.
		NounType days = addNounType("Day");
		Noun tuesday = days.addNoun("Tuesday");
		Noun wednesday = days.addNoun("Wednesday");
		Noun thursday = days.addNoun("Thursday");
		Noun friday = days.addNoun("Friday");
		Noun saturday = days.addNoun("Saturday");

		NounType wives = addNounType("Wife");
		Noun beryl = wives.addNoun("Beryl");
		Noun imogene = wives.addNoun("Imogene");
		Noun lydia = wives.addNoun("Lydia");
		Noun natalie = wives.addNoun("Natalie");
		Noun sophie = wives.addNoun("Sophie");

		NounType husbands = addNounType("Husband");
		Noun grant = husbands.addNoun("Grant");
		Noun webb = husbands.addNoun("Webb");
		Noun clark = husbands.addNoun("Clark");
		Noun jordan = husbands.addNoun("Jordan");
		Noun kirby = husbands.addNoun("Kirby");

		NounType locations = addNounType("Location");
		Noun cathedral = locations.addNoun("cathedral");
		Noun monument = locations.addNoun("monument");
		Noun pits = locations.addNoun("sulphur pits");
		Noun tidePool = locations.addNoun("tide pool");
		Noun picnic = locations.addNoun("picnic");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link laterThan = addLink("later than", days);
		laterThan.f = SmartLink.getIsMoreThan(0);

		Link dayAfter = addLink("the day after", days);
		dayAfter.f = SmartLink.getIsMoreBy(1);

		// Facts.
		addFact("1", imogene, Is, With, webb);
		addFact("2", lydia, Is, With, cathedral);
		addFact("2", lydia, Is, laterThan, monument);
		addFact("3", pits, Is, With, saturday);
		addFact("4", clark, IsNot, With, saturday);
		addFact("4", clark, Is, laterThan, jordan);
		addFact("4", jordan, Is, With, tidePool);
		addFact("5", kirby, IsNot, With, natalie);
		addFact("5", kirby, Is, With, thursday);
		addFact("6", tidePool, Is, laterThan, picnic);
		addFact("7", sophie, Is, dayAfter, imogene);

		// Solution.
		answer = new int[][]{{1, 4, 0, 2, 3}, {1, 3, 4, 2, 0}, {4, 3, 1, 0, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Day, 2=Wife, 3=Husband, 4=Location
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + "'s trip " + verb.name + " " + link.name + " the trip by " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " married to " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "visited" : "did not visit") + " the " + noun2.name;
						else
							msg = noun1.name + "'s trip " + (verb == Is ? "was" : "was not") + " " + link.name + " the visit to the " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + "'s visit " + verb.name + " on " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = noun1.name + " " + verb.name + " married to " + noun2.name;
						break;
					case 3:
						msg = noun1.name + "'s visit " + verb.name + " " + link.name + " " + noun2.name + "'s trip";
						break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s visit " + verb.name + " to the " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The trip to the " + noun1.name + " " + verb.name + " on " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4:
						msg = "The " + noun1.name + " " + verb.name + " visited " + link.name + " the " + noun2.name;
						break;
				}
				break;
		}

		return msg + ".";
	}
}
