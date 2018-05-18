package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class StarCrossed extends Puzzle {
	StarCrossed() {
		// Properties.
		myName = "StarCrossed";
		myTitle = "Star-Crossed";

		// Nouns.
		NounType days = addNounType("Day");
		Noun monday = days.addNoun("Monday");
		Noun tuesday = days.addNoun("Tuesday");
		Noun wednesday = days.addNoun("Wednesday");
		Noun thursday = days.addNoun("Thursday");
		Noun friday = days.addNoun("Friday");

		NounType times = addNounType("Time");
		Noun time1030am = times.addNoun("10:30 am");
		Noun time1130am = times.addNoun("11:30 am");
		Noun time1230pm = times.addNoun("12:30 pm");
		Noun time0130pm = times.addNoun("1:30 pm");
		Noun time0230pm = times.addNoun("2:30 pm");

		NounType vehicles = addNounType("Vehicle");
		Noun skateboard = vehicles.addNoun("skateboard");
		Noun bicycle = vehicles.addNoun("bicycle");
		Noun wagon = vehicles.addNoun("wagon");
		Noun scooter = vehicles.addNoun("scooter");
		Noun skates = vehicles.addNoun("skates");

		NounType misses = addNounType("Missed");
		Noun painter = misses.addNoun("painter");
		Noun jogger = misses.addNoun("jogger");
		Noun glassPane = misses.addNoun("glass pane");
		Noun dog = misses.addNoun("dog");
		Noun couple = misses.addNoun("couple");

		NounType hits = addNounType("Hit");
		Noun fountain = hits.addNoun("fountain");
		Noun lamppost = hits.addNoun("lamppost");
		Noun cruiser = hits.addNoun("police car");
		Noun window = hits.addNoun("storefront window", "Window");
		Noun tree = hits.addNoun("tree");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link hours23After = addLink("exactly 23 hours after", times);
		hours23After.f = SmartLink.getIsLessBy(1);

		Link earlierInWeek = addLink("earlier in the week than", days);
		earlierInWeek.f = SmartLink.getIsLessThan(0);

		Link dayAfter = addLink("the day after", days);
		dayAfter.f = SmartLink.getIsMoreBy(1);

		Link laterInDayThan = addLink("later in the day than", times);
		laterInDayThan.f = SmartLink.getIsMoreThan(0);

		// Facts.
		addFact("1", painter, Is, hours23After, lamppost);
		addFact("1", painter, Is, dayAfter, lamppost);

		addFact("2", monday, IsNot, With, time1030am);

		addFact("3", bicycle, Is, earlierInWeek, cruiser);

		addFact("4", painter, IsNot, With, getList(time1030am, time1130am));
		addFact("4", painter, Is, dayAfter, jogger);

		addFact("5", wagon, Is, With, window);
		addFact("5", window, Is, earlierInWeek, painter);
		addFact("5", window, Is, laterInDayThan, painter);

		addFact("6", tree, Is, laterInDayThan, scooter);
		addFact("6", glassPane, Is, earlierInWeek, wagon);

		addFact("7", wagon, Is, earlierInWeek, dog);
		addFact("7", dog, IsNot, With, friday);

		addFact("8", skates, Is, earlierInWeek, couple);
		addFact("8", couple, Is, laterInDayThan, skates);

		// Solution.
		answer = new int[][]{{1, 4, 0, 3, 2}, {4, 2, 3, 1, 0}, {2, 4, 3, 1, 0}, {4, 3, 0, 1, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Day, 2=Time, 3=Vehicle, 4=Missed, 5=Hit
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = noun1.name + "'s accident " + verb.name + " the one at " + noun2.name;
						break;
					case 3: break;
					case 4: break;
					case 5: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4:
						if (link != With)
							msg = "The accident with the " + noun1.name + " " + verb.name + " " + link.name + " the miss with the " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = "The " + noun1.name + " " + (verb == Is ? "hit" : "did not hit") + " the " + noun2.name;
						else
							msg = "The accident with the " + noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name;
						break;
				}
				break;
			case 4:
				msg = "The miss with the " + noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += verb.name + " on " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg += verb.name + " at " + noun2.name;
						break;
					case 3:
						if (link != With)
							msg += verb.name + " " + link.name + " the accident with the " + noun2.name;
						break;
					case 4:
						msg += verb.name + " " + link.name + " missing the " + noun2.name;
						break;
					case 5:
						if (link != With)
							msg += verb.name + " " + link.name + " hitting the " + noun2.name;
						break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link != With)
							msg = "The " + noun1.name + " " + verb.name + " hit " + link.name + " the accident with the " + noun2.name;
						break;
					case 4:
						if (link != With)
							msg = "The " + noun1.name + " " + verb.name + " hit " + link.name + " the miss with the " + noun2.name;
						break;
					case 5: break;
				}
				break;
		}

		return msg + ".";
	}
}
