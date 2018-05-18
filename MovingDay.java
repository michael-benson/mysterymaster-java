package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class MovingDay extends Puzzle {
	MovingDay() {
		// Properties.
		myName = "MovingDay";
		myTitle = "Moving Day";

		// Nouns.
		NounType days = addNounType("Day");
		Noun monday = days.addNoun("Monday");
		Noun tuesday = days.addNoun("Tuesday");
		Noun wednesday = days.addNoun("Wednesday");
		Noun thursday = days.addNoun("Thursday");
		Noun friday = days.addNoun("Friday");

		NounType husbands = addNounType("Husband");
		Noun max = husbands.addNoun("Max");
		Noun lew = husbands.addNoun("Lew");
		Noun wade = husbands.addNoun("Wade");
		Noun harold = husbands.addNoun("Harold");
		Noun andy = husbands.addNoun("Andy");

		NounType wives = addNounType("Wife");
		Noun rhonda = wives.addNoun("Rhonda");
		Noun joan = wives.addNoun("Joan");
		Noun emma = wives.addNoun("Emma");
		Noun velma = wives.addNoun("Velma");
		Noun clair = wives.addNoun("Clair");

		NounType lastNames = addNounType("Last Name");
		Noun platt = lastNames.addNoun("Platt");
		Noun summers = lastNames.addNoun("Summers");
		Noun reynolds = lastNames.addNoun("Reynolds");
		Noun burch = lastNames.addNoun("Burch");
		Noun oldham = lastNames.addNoun("Oldham");

		NounType streets = addNounType("Street");
		Noun laurelLane = streets.addNoun("Laurel Lane", "Laurel");
		Noun jasperRoad = streets.addNoun("Jasper Road", "Jasper");
		Noun dogwoodStreet = streets.addNoun("Dogwood Street", "Dogwood");
		Noun rosemontDrive = streets.addNoun("Rosemont Drive", "Rosemont");
		Noun nutleyCircle = streets.addNoun("Nutley Circle", "Nutley");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link before = addLink("before", days);
		before.f = SmartLink.getIsLessThan(0);

		// Facts.
		addFact("1", getList(lew, joan, platt, laurelLane, friday), IsNot, With);
		addFact("2", wade, IsNot, With, joan);
		addFact("2", getList(wade, summers, jasperRoad), IsNot, With);
		addFact("3", harold, IsNot, With, getList(dogwoodStreet, reynolds));
		addFactsInSequence("4", getList(andy, rosemontDrive, joan), Is, before);
		addFact("5", emma, IsNot, With, burch);
		addFactsInSequence("5", getList(dogwoodStreet, burch, wade), Is, before);
		addFact("6", nutleyCircle, Is, before, velma);
		addFact("7", oldham, IsNot, With, jasperRoad);
		addFact("8", getList(andy, clair, platt), IsNot, With);
		addFact("8", clair, IsNot, With, summers);

		// Solution.
		answer = new int[][]{{1, 4, 2, 3, 0}, {4, 0, 2, 1, 3}, {4, 3, 0, 1, 2}, {2, 0, 3, 4, 1}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Day, 2=Husband, 3=Wife, 4=Last Name, 5=Street
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "moved" : "did not move") + " on " + noun2.name;
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s wife " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "moved" : "did not move") + " to " + noun2.name;
						else
							msg = noun1.name + " " + (verb == Is ? "moved" : "did not move") + " " + link.name + " the couple on " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "moved" : "did not move") + " on " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "moved" : "did not move") + " to " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The " + noun1.name + " couple " + (verb == Is ? "moved" : "did not move") + " on " + noun2.name;
						break;
					case 2:
						if (link != With)
							msg = "The " + noun1.name + " couple " + (verb == Is ? "moved" : "did not move") + " " + link.name + " " + noun2.name;
						break;
					case 3: break;
					case 4: break;
					case 5:
						if (link == With)
							msg = "The " + noun1.name + " couple " + (verb == Is ? "moved" : "did not move") + " to " + noun2.name;
						break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The couple on " + noun1.name + " " + (verb == Is ? "moved" : "did not move") + " on " + noun2.name;
						break;
					case 2: break;
					case 3:
						if (link != With)
							msg = "The couple on " + noun1.name + " " + (verb == Is ? "moved" : "did not move") + " " + link.name + " " + noun2.name;
						break;
					case 4:
						if (link != With)
							msg = "The couple on " + noun1.name + " " + (verb == Is ? "moved" : "did not move") + " " + link.name + " the " + noun2.name + " couple";
						break;
					case 5: break;
				}
				break;
		}

		return msg + ".";
	}
}
