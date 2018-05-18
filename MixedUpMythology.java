package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class MixedUpMythology extends Puzzle {
	MixedUpMythology() {
		// Properties.
		myName = "MixedUpMythology";
		myTitle = "Mixed-Up Mythology";

		// Nouns.
		NounType thrones = addNounType("Throne");
		Noun throne01 = thrones.addNoun("1st");
		Noun throne02 = thrones.addNoun("2nd");
		Noun throne03 = thrones.addNoun("3rd");
		Noun throne04 = thrones.addNoun("4th");
		Noun throne05 = thrones.addNoun("5th");
		Noun throne06 = thrones.addNoun("6th");
		Noun throne07 = thrones.addNoun("7th");
		Noun throne08 = thrones.addNoun("8th");
		Noun throne09 = thrones.addNoun("9th");
		Noun throne10 = thrones.addNoun("10th");
		Noun throne11 = thrones.addNoun("11th");
		Noun throne12 = thrones.addNoun("12th");

		NounType gods = addNounType("God");
		Noun apollo = gods.addNoun("Apollo");
		Noun ares = gods.addNoun("Ares");
		Noun dionysus = gods.addNoun("Dionysus");
		Noun hephaestus = gods.addNoun("Hephaestus");
		Noun hermes = gods.addNoun("Hermes");
		Noun poseidon = gods.addNoun("Poseidon");
		Noun zeus = gods.addNoun("Zeus");
		Noun aphrodite = gods.addNoun("Aphrodite");
		Noun artemis = gods.addNoun("Artemis");
		Noun athena = gods.addNoun("Athena");
		Noun demeter = gods.addNoun("Demeter");
		Noun hera = gods.addNoun("Hera");

		NounType spheres = addNounType("Sphere");
		Noun messenger = spheres.addNoun("messenger god", "Messenger");
		Noun fire = spheres.addNoun("god of fire", "Fire");
		Noun harvest = spheres.addNoun("god of harvest", "Harvest");
		Noun hunt = spheres.addNoun("god of the hunt", "Hunt");
		Noun light = spheres.addNoun("god of light", "Light");
		Noun sea = spheres.addNoun("god of the sea", "Sea");
		Noun wine = spheres.addNoun("god of wine", "Wine");
		Noun love = spheres.addNoun("goddess of love", "Love");
		Noun marriage = spheres.addNoun("goddess of marriage", "Marriage");
		Noun sky = spheres.addNoun("goddess of the sky", "Sky");
		Noun war = spheres.addNoun("goddess of war", "War");
		Noun wisdom = spheres.addNoun("goddess of wisdom", "Wisdom");

		NounType parents = addNounType("Parents");
		Noun cronusRhea1 = parents.addNoun("Cronus/Rhea1");
		Noun cronusRhea2 = parents.addNoun("Cronus/Rhea2");
		Noun cronusRhea3 = parents.addNoun("Cronus/Rhea3");
		Noun cronusRhea4 = parents.addNoun("Cronus/Rhea4");
		Noun foam = parents.addNoun("Foam");
		Noun zeusHead = parents.addNoun("Zeus's Head");
		Noun zeusWife1 = parents.addNoun("Zeus/Wife1");
		Noun zeusWife2 = parents.addNoun("Zeus/Wife2");
		Noun zeusLeto = parents.addNoun("Zeus/Leto");
		Noun zeusMaia1 = parents.addNoun("Zeus/Maia1");
		Noun zeusMaia2 = parents.addNoun("Zeus/Maia2");
		Noun zeusSemele = parents.addNoun("Zeus/Semele");

		// Links.
		Link nextTo = addLink("next to", thrones);
		nextTo.f = SmartLink.getIsNextTo();

		Link justRightOf = addLink("immediately to the right of", thrones);
		justRightOf.f = SmartLink.getIsMoreBy(1);

		Link rightOf = addLink("to the right of", thrones);
		rightOf.f = SmartLink.getIsMoreThan(0);

		Link threeAway = addLink("three thrones from", thrones);
		threeAway.f = SmartLink.getIsOffsetBy(3);

		Link oppositeSide = addLink("on the opposite side of", thrones);
		oppositeSide.f = (noun1, noun2) -> (noun1.num < 6 && noun2.num > 7) || (noun1.num > 7 && noun2.num < 6) ? Is : IsNot;

		// Facts.
		List<Noun> thronesCenter = getList(throne06, throne07);
		List<Noun> thronesBrother = getList(throne05, throne08);

		List<Noun> guys = getList(apollo, ares, dionysus, hephaestus, hermes, poseidon, zeus);
		List<Noun> gals = getList(aphrodite, artemis, athena, demeter, hera);

		List<Noun> spheres1 = getList(messenger, fire, harvest, hunt, light, sea, wine);
		List<Noun> spheres2 = getList(love, marriage, sky, war, wisdom);

		// Parent pairings that are Cronus/Rhea.
		List<Noun> croni = getList(cronusRhea1, cronusRhea2, cronusRhea3, cronusRhea4);

		// Array of nouns for temporary work.
		List<Noun> list;

		// Intro) The first seven spheres are for the gods; the last five are for the goddesses.
		addFact("0", guys, IsNot, With, spheres2);
		addFact("0", gals, IsNot, With, spheres1);


		// Clue 1) There were two generations of immortals represented in the throne room.
		// Four of the immortals were of the older generation; all four were children of Cronus and Rhea.

		// The Old Generation consists of gods/goddesses where the parents are Cronus and Rhea. See clues 1, 4, 6, 12, 14.
		addFact("2", zeus, Is, With, cronusRhea1);


		// Clue 2) The other eight were of the younger generation.
		// Of these, one had no parents as, she was born from the foam of the sea. Another was born from the head of Zeus.

		// Clue 3) The remaining six where born more or less in the usual fashion. All six where children of Zeus, but not all six had the same mother.

		// A goddess was born from the foam of the sea.
		addFact("2", foam, IsNot, With, guys);

		// Clue 4) The two centermost thrones belonged to Zeus and his wife, in one order or the other; on the other side of Zeus sat a brother of his.

		// Only Zeus (Cronus/Rhea1) and his wife (Cronus/Rhea2) sat in the centermost thrones.
		list = Helper.getListExcept(parents.nouns, getList(cronusRhea1, cronusRhea2));
		addFact("4", list, IsNot, With, thronesCenter);

		// Since Zeus's brother sat next to him, Cronus/Rhea4 are either in throne 5 or throne 8.
		list = Helper.getListExcept(thrones.nouns, thronesBrother);
		addFact("4", cronusRhea4, IsNot, With, list);


		// Clue 5) There was exactly one pair of twins among the twelve immortals;
		// they occupied adjacent thrones, but on the opposite side of Zeus from the god of light.

		// Zeus is not the god of light.
		addFact("5", zeus, IsNot, With, light);

		// The twins occupied adjacent thrones (from clue 9, Maia is their mother).
		addFact("5,9", zeusMaia1, Is, nextTo, zeusMaia2);

		// The twins sat on the opposite side of Zeus from the god of light.
		addFact("5", zeusMaia1, Is, oppositeSide, light);


		// Clue 6) The goddess of war and Poseidon's mother were both members of the older generation.
		addFact("6", war, Is, With, cronusRhea3);

		// The parents of Poseidon are either Zeus/Wife1 or Zeus/Wife2.
		list = Helper.getListExcept(parents.nouns, getList(zeusWife1, zeusWife2));
		addFact("6", poseidon, IsNot, With, list);


		// Clue 7) The god of fire wasn't Zeus.
		addFact("7", zeus, IsNot, With, fire);


		// Clue 8) The god of the harvest didn't sit immediately to the right of Hera.
		addFact("8", harvest, IsNot, justRightOf, hera);


		// Clue 9) The eight members of the younger generation of immortals, in order of their thrones from
		// left to right, were: the messenger god (whose mother wasn't Zeus's wife), the god of wine,
		// the goddess born from the foam of the sea (who wasn't the goddess of marriage), an
		// unmarried son of Zeus and his wife, the goddess with only one parent, a twin who was
		// Maia's son, the goddess of wisdom, and Hermes.

		// Left-to-Right: Messenger, Wine, Foam, Unmarried son of Zeus/Wife1, Goddess from Zeus's Head, Son of Zeus/Maia1, Wisdom, Hermes
		addFactsInSequence("9", getList(hermes, wisdom, zeusMaia1, zeusHead, zeusWife1, foam, wine, messenger), Is, rightOf);

		// The parents of the younger generation are not Cronus and Rhea.
		addFact("9", croni, IsNot, With, getList(messenger, wine, wisdom, hermes));

		// The messenger god's mother wasn't Zeus's wife.
		addFact("9", messenger, IsNot, With, getList(zeusWife1, zeusWife2));

		addFact("9", foam, IsNot, With, marriage);

		// Zeus/Wife1 are the parents of an unmarried son.
		addFact("9", zeusWife1, IsNot, With, gals);

		// A goddess was born from Zeus's head.
		addFact("9", zeusHead, IsNot, With, guys);

		// Zeus/Maia1 is the mother of a son who is a twin.
		addFact("9", zeusMaia1, IsNot, With, gals);


		// Clue 10) Aphrodite, who sat just three thrones from Athena, wasn't goddess of marriage or of love.
		addFact("10", aphrodite, Is, threeAway, athena);
		addFact("10", aphrodite, IsNot, With, getList(love, marriage));


		// Clue 11) The god of the hunt, whose mother was Leto, sat at one end of the rows of thrones.
		addFact("11", zeusLeto, Is, With, hunt);

		// The god of the hunt sat in either the first or the last throne.
		list = Helper.getListExcept(thrones.nouns, getList(throne01, throne12));
		addFact("11", hunt, IsNot, With, list);


		// Clue 12) Demeter, who was the goddess of the sky and a daughter of Cronus and Rhea, sat somewhere to the right of the goddess of war.
		addFact("12", demeter, Is, With, getList(sky, cronusRhea2));
		addFact("12", demeter, Is, rightOf, war);


		// Clue 13) One of the gods of the younger generation (who wasn't named Hephaestus) was husband of Athena; he had the same father and mother as Poseidon.
		// From clue 6, Poseidon's parents are Zeus/Wife1.
		addFact("13", hephaestus, IsNot, With, zeusWife2);

		// Zeus/Wife2 are not the parents of a goddess.
		addFact("13", zeusWife2, IsNot, With, gals);

		// From clues 6 and 13, Poseidon is the unmarried son in clue 9.
		addFact("6,9,13", poseidon, Is, With, zeusWife1);


		// Clue 14) Dionysus and Apollo were from different generations; the younger of the two was the god of the harvest.

		// Either Dionysus or Apollo is the son of Chronus4/Rhea4 (and the brother of Zeus).
		// Either Dionysus or Apollo were the god of the harvest.
		list = Helper.getListExcept(gods.nouns, getList(apollo, dionysus));
		addFact("14", cronusRhea4, IsNot, With, list);
		addFact("14", list, IsNot, With, harvest);

		// The god of the harvest is from the younger generation.
		addFact("14", croni, IsNot, With, harvest);

		// Clue 15) The goddess of marriage didn't sit next to Dionysus.
		addFact("15", marriage, IsNot, nextTo, dionysus);

		// Rules.
		Rule rule1 = addRule("14", "Dionysus and Apollo were from different generations.", getList(dionysus, apollo));
		rule1.f = (mark) -> {
			int rs = 0;

			Noun zeusThrone = Mark.getPairNoun(zeus, thrones);
			Noun apolloThrone = Mark.getPairNoun(apollo, thrones);
			Noun dionysusThrone = Mark.getPairNoun(dionysus, thrones);

			if (zeusThrone == null) {
				// Trigger: (a) If Apollo in throne 5/8 and Dionysus is god of harvest, then Zeus is in throne 6/7
				if ((apolloThrone == throne05 || apolloThrone == throne08) && solver.getGridVerb(dionysus, harvest) == Is) {
					zeusThrone = (apolloThrone == throne05) ? throne06 : throne07;
					String msg = "Zeus must sit next to Apollo.";
					print(msg);
					rs = solver.addMarkByRule(mark, rule1, 'a', zeus, Is, zeusThrone, msg);
					if (rs != 0) return rs;
				}
				// Trigger: (b) If Dionysus in throne 5/8 and Apollo is god of harvest, then Zeus is in throne 6/7
				else if ((dionysusThrone == throne05 || dionysusThrone == throne08) && solver.getGridVerb(apollo, harvest) == Is) {
					zeusThrone = (dionysusThrone == throne05) ? throne06 : throne07;
					String msg = "Zeus must sit next to Dionysus.";
					print(msg);
					rs = solver.addMarkByRule(mark, rule1, 'b', zeus, Is, zeusThrone, msg);
					if (rs != 0) return rs;
				}
			}
			else {
				// Get outermost throne next to Zeus where brother sits.
				Noun broThrone = (zeusThrone.num == 6 ? throne05 : throne08);

				// Violation if god sitting in brother's throne is not Apollo or Dionysus.
				Noun bro = Mark.getPairNoun(broThrone, gods);
				if (bro != null && bro != apollo && bro != dionysus) return -1;

				// Violation if Apollo and Dionysus are both sitting, and neither is in brother's throne.
				if (apolloThrone != null && dionysusThrone != null && apolloThrone != broThrone && dionysusThrone != broThrone) return -1;

				// Trigger: (a) if Zeus's throne is known, but the thrones for Apollo and Dionysus are unknown, then they are only two that can be in brother's throne.
				if (apolloThrone == null && dionysusThrone == null) {
					for (Noun god : gods.nouns) {
						if (god == apollo || god == dionysus || god == zeus) continue;
						String msg = "Only Apollo and Dionysus can sit in brother's throne.";
						rs = solver.addMarkByRule(mark, rule1, 'a', god, IsNot, broThrone, msg);
						if (rs != 0) return rs;
					}
				}

				// Trigger: (b) if Apollo is not sitting in the brother's throne, then he must be the god of the harvest
				if (apolloThrone != null && apolloThrone != broThrone) {
					String msg = apollo.name + " must be the god of the " + harvest.name + ".";
					rs = solver.addMarkByRule(mark, rule1, 'b', apollo, Is, harvest, msg);
					if (rs != 0) return rs;

					// Trigger: (b) if Dionysus is not sitting, then Dionysus must sit in the brother's throne.
					if (dionysusThrone == null) {
						msg = dionysus.name + " must sit in " + broThrone.name + ".";
						rs = solver.addMarkByRule(mark, rule1, 'b', dionysus, Is, broThrone, msg);
						if (rs != 0) return rs;
					}
				}

				// Trigger: (c) if Dionysus is not sitting in the brother's throne, then he must be the god of the harvest.
				if (dionysusThrone != null && dionysusThrone != broThrone) {
					String msg = dionysus.name + " must be the god of the " + harvest.name + ".";
					rs = solver.addMarkByRule(mark, rule1, 'c', dionysus, Is, harvest, msg);
					if (rs != 0) return rs;

					// Trigger: (c) If Apollo is not sitting, then Apollo must sit in the brother's throne.
					if (apolloThrone == null) {
						msg = apollo.name + " must sit in " + broThrone.name + ".";
						rs = solver.addMarkByRule(mark, rule1, 'c', apollo, Is, broThrone, msg);
						if (rs != 0) return rs;
					}
				}
			}

			return rs;
		};

		// Solution.
		answer = new int[][]{{7, 3, 1, 9, 2, 6, 10, 5, 8, 0, 11, 4}, {10, 0, 6, 7, 4, 5, 9, 1, 8, 2, 11, 3}, {1, 11, 6, 4, 3, 0, 2, 7, 5, 9, 10, 8}, {1, 11, 6, 4, 3, 0, 2, 7, 5, 9, 10, 8}};
	}

	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Throne, 2=God, 3=Sphere, 4=Parents
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
						msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name;
						else
							msg = noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the child of " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " in the " + noun2.name + " throne";
						break;
					case 2:
						if (link != With)
							msg = "The " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						msg = "The " + noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " the child of " + noun2.name;
						else
							msg = "The " + noun1.name + " " + verb.name + " " + link.name + " the child of " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The child of " + noun1.name + " " + verb.name + " in the " + noun2.name + " throne";
						break;
					case 2:
						if (link == With)
							msg = "The child of " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = "The child of " + noun1.name + " " + verb.name + " the " + noun2.name;
						else
							msg = "The child of " + noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name;
						break;
					case 4:
						msg = "The child of " + noun1.name + " " + verb.name + " " + link.name + " the child of " + noun2.name;
						break;
				}
				break;
		}

		return msg + ".";
	}
}
