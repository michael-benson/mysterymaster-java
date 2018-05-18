package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-07
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class Big5GameRangers extends Puzzle {
	Big5GameRangers() {
		// Properties.
		myName = "Big5GameRangers";
		myTitle = "Big 5 Game Rangers";

		// Nouns.
		NounType locations = addNounType("Location");
		Noun locationA = locations.addNoun("A");
		Noun locationB = locations.addNoun("B");
		Noun locationC = locations.addNoun("C");
		Noun locationD = locations.addNoun("D");
		Noun locationE = locations.addNoun("E");
		Noun locationF = locations.addNoun("F");
		Noun locationG = locations.addNoun("G");
		Noun locationH = locations.addNoun("H");
		Noun locationI = locations.addNoun("I");
		Noun locationJ = locations.addNoun("J");

		NounType camps = addNounType("Camp");
		Noun buffalo = camps.addNoun("buffalo");
		Noun elephant = camps.addNoun("elephant");
		Noun leopard = camps.addNoun("leopard");
		Noun lion = camps.addNoun("lion");
		Noun rhino = camps.addNoun("rhino");
		Noun eagle = camps.addNoun("eagle");
		Noun hornbill = camps.addNoun("hornbill");
		Noun owl = camps.addNoun("owl");
		Noun stork = camps.addNoun("stork");
		Noun vulture = camps.addNoun("vulture");

		NounType firstNames = addNounType("First Name");
		Noun anton = firstNames.addNoun("Anton");
		Noun bern = firstNames.addNoun("Bernadette");
		Noun colin = firstNames.addNoun("Colin");
		Noun delia = firstNames.addNoun("Delia");
		Noun ethan = firstNames.addNoun("Ethan");
		Noun freda = firstNames.addNoun("Freda");
		Noun greg = firstNames.addNoun("Greg");
		Noun hilda = firstNames.addNoun("Hilda");
		Noun ian = firstNames.addNoun("Ian");
		Noun julia = firstNames.addNoun("Julia");

		NounType lastNames = addNounType("Last Name");
		Noun aarons = lastNames.addNoun("Aarons");
		Noun bester = lastNames.addNoun("Bester");
		Noun chuene = lastNames.addNoun("Chuene");
		Noun dooby = lastNames.addNoun("Dooby");
		Noun ebony = lastNames.addNoun("Ebony");
		Noun frank = lastNames.addNoun("Frank");
		Noun govender = lastNames.addNoun("Govender");
		Noun hedges = lastNames.addNoun("Hedges");
		Noun ivanov = lastNames.addNoun("Ivanov");
		Noun jackson = lastNames.addNoun("Jackson");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link nextTo = addLink("next to", locations);
		nextTo.f = (noun1, noun2) ->
		(noun1.num <= 5 && noun2.num <= 5 && Math.abs(noun1.num - noun2.num) == 1) ||
		(noun1.num >= 6 && noun2.num >= 6 && Math.abs(noun1.num - noun2.num) == 1) ? Is : IsNot;

		Link sameSide = addLink("on the same side as", locations);
		sameSide.f = (noun1, noun2) ->
		(noun1.num <= 5 && noun2.num <= 5) || (noun1.num >= 6 && noun2.num >= 6) ? Is : IsNot;

		// Facts.
		List<Noun> campsAtoE = getList(locationA, locationB, locationC, locationD, locationE);
		List<Noun> campsFtoJ = getList(locationF, locationG, locationH, locationI, locationJ);
		List<Noun> animalCamps = getList(buffalo, elephant, leopard, lion, rhino);
		List<Noun> birdCamps = getList(eagle, hornbill, owl, stork, vulture);
		List<Noun> guys = getList(anton, colin, ethan, greg, ian);
		List<Noun> gals = getList(bern, delia, freda, hilda, julia);

		// Clue 1. No person's first name, last name, or location of camp starts with the same Noun[]ter.
		addFactsIsNotFirstChar("1", firstNames, lastNames, true);
		addFactsIsNotFirstChar("1", firstNames, locations, true);
		addFactsIsNotFirstChar("1", lastNames, locations, true);

		// Clue 2. The five female rangers are:  Freda (who runs a "bird" camp), Ms Bester (who does not run the elephant camp),
		// the one running the lion camp, the one at camp F, and Ms Chuene (who runs the eagle camp).
		List<Noun> list2 = getList(freda, bester, lion, locationF, chuene);
		addFact("2", list2, IsNot, With);
		addFact("2", list2, IsNot, With, guys);
		addFact("2", freda, IsNot, With, animalCamps);
		addFact("2", bester, IsNot, With, elephant);
		addFact("2", chuene, Is, With, eagle);

		// Clue 3. The five male rangers are: the one running the rhino camp, the one at camp B, Mr. Ebony, Greg Ivanov, and the one running the stork camp (who is not Colin).
		List<Noun> list3 = getList(rhino, locationB, ebony, greg, stork);
		addFact("3", list3, IsNot, With);
		addFact("3", list3, IsNot, With, gals);
		addFact("3", greg, Is, With, ivanov);
		addFact("3", colin, IsNot, With, stork);

		// Clue 4. The five rangers who run the Big 5 animal camps are: Govender, the ranger at camp B, Mr. Dooby at camp G, Delia, and Ms Frank.
		List<Noun> list4 = getList(govender, locationB, dooby, delia, frank);
		addFact("4", list4, IsNot, With);
		addFact("4", list4, IsNot, With, birdCamps);
		addFact("4", dooby, IsNot, With, gals);
		addFact("4", dooby, Is, With, locationG);
		addFact("4", frank, IsNot, With, guys);

		// Clue 5. The five rangers who run the Big 5 bird camps are: Greg, Ms Aarons, Bernadette, the one running the stork camp, and the ranger at camp A.
		List<Noun> list5 = getList(greg, aarons, bern, stork, locationA);
		addFact("5", list5, IsNot, With);
		addFact("5", list5, IsNot, With, animalCamps);

		// Clue 6. The five rangers at camp A - E are: the ranger at camp A, the ranger at camp E,
		// the ranger running the vulture camp, Freda (who is not the ranger at camp C), and Ethan (who runs an animal camp).
		List<Noun> list6 = getList(locationA, locationE, vulture, freda, ethan);
		addFact("6", list6, IsNot, With);
		addFact("6", list6, IsNot, With, campsFtoJ);
		addFact("6", freda, IsNot, With, locationC);
		addFact("6", ethan, IsNot, With, birdCamps);

		// Clue 7. The five rangers at camp F - J are: the ranger at camp F, the ranger at camp J,
		// Delia (who does not run the lion camp), Anton, and Jackson (who does not run the rhino camp).
		List<Noun> list7 = getList(locationF, locationJ, delia, anton, jackson);
		addFact("7", list7, IsNot, With);
		addFact("7", list7, IsNot, With, campsAtoE);
		addFactsOneToOne("7", getList(delia, jackson), IsNot, With, getList(lion, rhino));

		// Clue 8. Colin (who does not run the hornbill camp), Ethan (whose camp is not next to Ian), Bernadette,
		// and Hilda's (whose camp is not next to Freda) camps are not next to a member of the opposite sex.
		addFact("8", colin, IsNot, With, hornbill);
		addFactsOneToOne("8", getList(ethan, hilda), IsNot, nextTo, getList(ian, freda));
		addFact("8", getList(colin, ethan), IsNot, nextTo, gals);
		addFact("8", getList(bern, hilda), IsNot, nextTo, guys);

		// Clue 9. The owl and hornbill camps are run by rangers on the same side of the entrance, but by rangers of opposite genders.
		addFact("9", owl, Is, sameSide, hornbill);

		// Rules.
		// Genders where 0=male, 1=female for noun type 3=FirstName
		int[] genders = new int[]{0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
		String[] groupNames = new String[]{"men", "women"};

		Rule rule1 = addRule("9", "The owl and hornbill camps are run by rangers of opposite genders.");
		rule1.f = smartRule.getInOppositeGroup(rule1, owl, hornbill, firstNames, genders, "gender", groupNames);

		Rule rule2 = addRule("10", "The elephant and buffalo camps are run by rangers of the same gender.");
		rule2.f = smartRule.getInSameGroup(rule2, elephant, buffalo, firstNames, genders, "gender", groupNames);

		// Solution.
		answer = new int[][]{{7, 2, 9, 6, 5, 1, 4, 8, 0, 3}, {2, 4, 6, 5, 1, 9, 0, 8, 3, 7}, {4, 7, 8, 0, 2, 6, 3, 9, 1, 5}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Location, 2=Camp, 3=First Name, 4=Last Name
		switch (noun1.type.num) {
			case 1:
				msg = "Location " + noun1.name + " ";
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg += verb.name + " the " + noun2.name + " camp";
						break;
					case 3:
						if (link == With)
							msg += verb.name + " run by " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg += verb.name + " run by " + noun2.name;
						break;
				}
				break;
			case 2:
				msg = "The " + noun1.name + " camp ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += verb.name + " at location " + noun2.name;
						break;
					case 2:
						msg += verb.name + " " + link.name + " as the " + noun2.name + " camp";
						break;
					case 3:
						if (link == With)
							msg += verb.name + " run by " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg += verb.name + " run by " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " at location " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "runs" : "does not run") + " the " + noun2.name + " camp";
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " at location " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "runs" : "does not run") + " the " + noun2.name + " camp";
						break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s first name " + verb.name + " " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
