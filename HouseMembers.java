package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class HouseMembers extends Puzzle {
	HouseMembers() {
		// Properties.
		myName = "HouseMembers";
		myTitle = "House Members";

		// Nouns.
		NounType districts = addNounType("District");
		Noun district1 = districts.addNoun("1st");
		Noun district2 = districts.addNoun("2nd");
		Noun district3 = districts.addNoun("3rd");
		Noun district4 = districts.addNoun("4th");
		Noun district5 = districts.addNoun("5th");

		NounType firstNames = addNounType("First Name");
		Noun bo = firstNames.addNoun("Bo");
		Noun cass = firstNames.addNoun("Cass");
		Noun john = firstNames.addNoun("John");
		Noun newt = firstNames.addNoun("Newt");
		Noun shirley = firstNames.addNoun("Shirley");

		NounType lastNames = addNounType("Last Name");
		Noun vanHorn = lastNames.addNoun("Van Horn");
		Noun kaplan = lastNames.addNoun("Kaplan");
		Noun juarez = lastNames.addNoun("Juarez");
		Noun garrity = lastNames.addNoun("Garrity");
		Noun orlando = lastNames.addNoun("Orlando");

		NounType towns = addNounType("Home Town");
		Noun palisades = towns.addNoun("Palisades");
		Noun stMarys = towns.addNoun("St. Mary's City", "St. Mary's");
		Noun gravesend = towns.addNoun("Gravesend");
		Noun summerset = towns.addNoun("Summerset");
		Noun minersville = towns.addNoun("Minersville");

		NounType years = addNounType("Years");
		Noun years24 = years.addNoun("24");
		Noun years16 = years.addNoun("16");
		Noun years12 = years.addNoun("12");
		Noun years08 = years.addNoun("8");
		Noun years04 = years.addNoun("4");

		// Links.
		Link twiceAsLong = addLink("twice as long as", years);
		twiceAsLong.f = (noun1, noun2) -> noun1.num == 1 && noun2.num == 3 || noun1.num == 2 && noun2.num == 4 || noun1.num == 4 && noun2.num == 5 ? Is : IsNot;

		Link togetherClue1a = addLink("together-clue1a", years);
		togetherClue1a.f = (noun1, noun2) ->
		noun1.num == 1 && noun2.num == 5 || noun1.num == 2 && noun2.num == 3 ||
		noun1.num == 2 && noun2.num == 5 || noun1.num == 3 && noun2.num == 2 ||
		noun1.num == 3 && noun2.num == 4 || noun1.num == 4 && noun2.num == 3 ||
		noun1.num == 5 && noun2.num < 3 ? Is : IsNot;

		Link togetherClue1b = addLink("together-clue1b", years);
		togetherClue1b.f = (noun1, noun2) ->
		noun1.num == 1 && noun2.num == 2 || noun1.num == 1 && noun2.num == 3 ||
		noun1.num == 2 && noun2.num != 2 || noun1.num == 3 && noun2.num < 3 ||
		noun1.num == 3 && noun2.num == 5 || noun1.num == 4 && noun2.num == 2 ||
		noun1.num == 4 && noun2.num == 5 || noun1.num == 5 && noun2.num > 1 && noun2.num < 5 ? Is : IsNot;

		Link togetherClue2a = addLink("together-clue2a", years);
		togetherClue2a.f = (noun1, noun2) ->
		noun1.num == 2 && noun2.num == 4 || noun1.num == 3 && noun2.num == 5 ||
		noun1.num == 4 && noun2.num == 2 || noun1.num == 4 && noun2.num == 5 ||
		noun1.num == 5 && noun2.num == 3 || noun1.num == 5 && noun2.num == 4 ? Is : IsNot;

		Link togetherClue2b = addLink("together-clue2b", years);
		togetherClue2b.f = (noun1, noun2) ->
		noun1.num == 2 && noun2.num == 1 || noun1.num == 3 && noun2.num == 2 ||
		noun1.num == 4 && noun2.num == 1 || noun1.num == 4 && noun2.num == 3 ||
		noun1.num == 5 && noun2.num == 2 || noun1.num == 5 && noun2.num == 3 ? Is : IsNot;

		// Facts.
		// Clue 1. Rep. Van Horn and the 1st District representative have together served as many years as Shirley and the St. Mary's City representative together.
		addFact("1", getList(vanHorn, district1, shirley, stMarys), IsNot, With);

		addFact("1", vanHorn, Is, togetherClue1a, district1);
		addFact("1", shirley, Is, togetherClue1a, stMarys);
		addFact("1", getList(vanHorn, district1), Is, togetherClue1b, getList(shirley, stMarys));

		// Clue 2. The representative from Gravesend has served as many years as Rep. Kaplan and the 3rd District representative together.
		addFact("2", getList(gravesend, kaplan, district3), IsNot, With);

		addFact("2", kaplan, Is, togetherClue2a, district3);
		addFact("2", getList(kaplan, district3), Is, togetherClue2b, gravesend);

		// Clue 3. John, Cass, and the representative from the 5th District are all on the House Natural Resources Committee.
		addFact("3", getList(john, cass, district5), IsNot, With);

		// Clue 4. Rep. Juarez has served twice as long as the 2nd District legislator (who isn't the Summerset native).
		addFact("4", juarez, Is, twiceAsLong, district2);
		addFact("4", district2, IsNot, With, summerset);

		// Clue 5. The Minersville resident has been a House member half as long as Bo.
		addFact("5", bo, Is, twiceAsLong, minersville);

		// Clue 6. Rep. Garrity (whose constituency isn't the 1st District) has been in the House for 12 years.
		addFact("6", garrity, IsNot, With, district1);
		addFact("6", garrity, Is, With, years12);

		// Clue 7. The one from Gravesend doesn't represent the 4th District.
		addFact("7", gravesend, IsNot, With, district4);

		// Clue 8. Rep. Van Horn (who isn't the most junior of the five) isn't the 3rd District legislator.
		addFact("8", vanHorn, IsNot, With, getList(years04, district3));

		// Clue 9. Rep. Kaplan isn't the 1st District representative.
		addFact("9", kaplan, IsNot, With, district1);

		// Clue 10. Shirley and the 4th District legislator were delegates to their party's last national convention.
		addFact("10", shirley, IsNot, With, district4);

		// Clue 11. John and Rep. Orlando are both fishing boat captains when they aren't working as legislators.
		addFact("11", john, IsNot, With, orlando);

		// Solution.
		answer = new int[][]{{1, 0, 4, 2, 3}, {4, 1, 2, 3, 0}, {4, 0, 3, 1, 2}, {4, 3, 1, 2, 0}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=District, 2=First Name, 3=Last Name, 4=Hometown, 5=Years
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = "The " + noun1.name + " District representative " + verb.name + " " + noun2.name;
						else
							msg = "The " + noun1.name + " District representative " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + " District representative " + verb.name + " from " + noun2.name;
						else
							msg = "The " + noun1.name + " District representative " + verb.name + " " + link.name + " the representative from " + noun2.name;

						break;
					case 5: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name + " District representative";
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + verb.name + " from " + noun2.name;
						else
							msg = noun1.name + "'s service " + verb.name + " " + link.name + " the " + noun2.name + " representative";
						break;
					case 5: break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name + " District representative";
						else
							msg = "Rep. " + noun1.name + "'s service " + verb.name + " " + link.name + " the " + noun2.name + " District representative";
						break;
					case 2:
						if (link == With)
							msg = "Rep. " + noun1.name + "'s first name " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name + " representative";
						else
							msg = noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name + " representative";
						break;
					case 5:
						if (link == With)
							msg = "Rep. " + noun1.name + "'s service " + verb.name + " " + noun2.name + " years.";
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The one from " + noun1.name + " " + verb.name + " the " + noun2.name + " District representative";
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "The one from " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 4: break;
					case 5: break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
				}
				break;
		}

		return msg + ".";
	}
}
