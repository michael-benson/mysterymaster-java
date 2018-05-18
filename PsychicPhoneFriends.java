package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-07
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class PsychicPhoneFriends extends Puzzle {
	PsychicPhoneFriends() {
		// Properties.
		myName = "PsychicPhoneFriends";
		myTitle = "Psychic Phone Friends";

		// Nouns.
		NounType birthdays = addNounType("Birthday");
		Noun day02 = birthdays.addNoun("2nd");
		Noun day07 = birthdays.addNoun("7th");
		Noun day12 = birthdays.addNoun("12th");
		Noun day17 = birthdays.addNoun("17th");
		Noun day22 = birthdays.addNoun("22nd");
		Noun day27 = birthdays.addNoun("27th");

		NounType callers = addNounType("Caller");
		Noun beau = callers.addNoun("Beau");
		Noun evelyn = callers.addNoun("Evelyn");
		Noun isaac = callers.addNoun("Isaac");
		Noun leila = callers.addNoun("Leila");
		Noun payton = callers.addNoun("Payton");
		Noun wade = callers.addNoun("Wade");

		NounType signs = addNounType("Sign");
		Noun aries = signs.addNoun("Aries");
		Noun libra = signs.addNoun("Libra");
		Noun cancer = signs.addNoun("Cancer");
		Noun gemini = signs.addNoun("Gemini");
		Noun pisces = signs.addNoun("Pisces");
		Noun taurus = signs.addNoun("Taurus");

		NounType psychics = addNounType("Psychic");
		Noun cleo = psychics.addNoun("Cleo");
		Noun fabian = psychics.addNoun("Fabian");
		Noun hermes = psychics.addNoun("Hermes");
		Noun natalya = psychics.addNoun("Natalya");
		Noun tabitha = psychics.addNoun("Tabitha");
		Noun zanzibar = psychics.addNoun("Zanzibar");

		NounType badFortunes = addNounType("Bad Fortune");
		Noun milk = badFortunes.addNoun("spill milk");
		Noun train = badFortunes.addNoun("miss train");
		Noun cold = badFortunes.addNoun("catch cold");
		Noun shirt = badFortunes.addNoun("tear shirt");
		Noun laces = badFortunes.addNoun("trip on laces");
		Noun bug = badFortunes.addNoun("swallow bug");

		NounType goodFortunes = addNounType("Good Fortune");
		Noun lottery = goodFortunes.addNoun("win the lottery", "Lottery");
		Noun spouse = goodFortunes.addNoun("meet spouse", "Spouse");
		Noun brother = goodFortunes.addNoun("reunite with brother", "Brother");
		Noun car = goodFortunes.addNoun("receive new car", "Car");
		Noun watch = goodFortunes.addNoun("find gold watch", "Watch");
		Noun promotion = goodFortunes.addNoun("get a promotion", "Promotion");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link after = addLink("after", birthdays);
		after.f = SmartLink.getIsMoreThan(0);

		// Facts.
		addFact("1", libra, IsNot, With, getList(day07, milk));
		addFact("1", milk, IsNot, With, day17);
		addFact("1", fabian, Is, With, evelyn);

		addFact("2", isaac, Is, With, cancer);
		addFact("2", zanzibar, Is, With, lottery);
		addFact("2", leila, Is, With, day02);

		addFact("3", payton, Is, after, day12);
		addFact("3", payton, Is, With, spouse);
		addFact("3", payton, IsNot, With, train);
		addFact("3", wade, Is, With, cold);
		addFact("3", wade, IsNot, With, cleo);

		addFact("4", gemini, IsNot, With, natalya);
		addFact("4", gemini, Is, With, shirt);
		addFact("4", pisces, IsNot, With, day12);
		addFact("4", day22, IsNot, With, wade);
		addFact("4", day22, Is, With, brother);

		addFact("5", natalya, IsNot, With, getList(isaac, car));
		addFact("5", day27, IsNot, With, payton);
		addFact("5", day27, Is, With, laces);

		addFact("6", taurus, IsNot, With, getList(natalya, bug));
		addFact("6", bug, Is, With, watch);
		addFact("6", libra, IsNot, With, getList(day02, promotion));

		addFact("7", cleo, IsNot, With, getList(cancer, gemini, milk));
		addFact("7", pisces, IsNot, With, day07);
		addFact("7", zanzibar, IsNot, With, day02);

		// Rules.
		Rule rule1 = addRule("2", "Hermes predicted the future of either the Gemini or the person who was born on the 12th (or both).");
		rule1.f = smartRule.getMatchAtLeastOne(rule1, hermes, getList(gemini, day12));

		Rule rule2 = addRule("7", "The Pisces is either the one who spoke to Zanzibar or Payton (or both).");
		rule2.f = smartRule.getMatchAtLeastOne(rule2, pisces, getList(zanzibar, payton));

		// Solution.
		answer = new int[][]{{3, 5, 1, 4, 2, 0}, {5, 0, 1, 3, 2, 4}, {0, 3, 1, 2, 4, 5}, {1, 2, 5, 3, 0, 4}, {3, 5, 4, 1, 2, 0}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Birthday, 2=Caller, 3=Sign, 4=Psychic, 5=Bad Fortune, 6=Good Fortune
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = "The caller born on the " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4: break;
					case 5:
						if (link == With)
							msg = "The caller born on the " + noun1.name + " " + verb.name + " destined to " + noun2.name;
						break;
					case 6:
						if (link == With)
							msg = "The caller born on the " + noun1.name + " " + verb.name + " destined to " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " born on the " + noun2.name;
						else
							msg = noun1.name + " " + verb.name + " born " + link.name + " the " + noun2.name;
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s sign " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s psychic " + verb.name + " " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = noun1.name + "'s misfortune " + verb.name + " to " + noun2.name;
						break;
					case 6:
						if (link == With)
							msg = noun1.name + " " + verb.name + " destined to " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " born on the " + noun2.name;
						break;
					case 2: break;
					case 3:
						break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + "'s psychic " + verb.name + " " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = "The " + noun1.name + "'s misfortune " + verb.name + " to " + noun2.name;
						break;
					case 6:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " destined to " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the psychic for the caller born on the " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = noun1.name + " " + verb.name + " " + noun2.name + "'s psychic";
						break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the psychic for the " + noun2.name;
						break;
					case 4: break;
					case 5:
						if (link == With)
							msg = noun1.name + "'s prediction " + verb.name + " to " + noun2.name;
						break;
					case 6:
						if (link == With)
							msg = noun1.name + "'s prediction " + verb.name + " to " + noun2.name;
						break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The one to " + noun1.name + " " + verb.name + " born on the " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
					case 6:
						if (link == With)
							msg = "The person to " + noun1.name + " " + verb.name + " destined to " + noun2.name;
						break;
				}
				break;
			case 6:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
					case 6: break;
				}
				break;
		}

		return msg + ".";
	}
}
