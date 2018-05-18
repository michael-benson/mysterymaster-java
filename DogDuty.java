package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class DogDuty extends Puzzle {
	final Link walkedEarlierThan, walkedTogether;
	
	// Sex of each dog where 0=female, 1=male.
	private final static int[] dsexes = new int[]{0, 1, 1, 0, 1, 0};

	// Returns true if both dogs (nouns of type sex or breed) have the same sex, otherwise false.
	private static boolean isSameSex(Noun nounA, Noun nounB) {
		return dsexes[nounA.num - 1] == dsexes[nounB.num - 1];
	}

	DogDuty() {
		// Properties.
		myName = "DogDuty";
		myTitle = "Dog Duty";

		// Nouns.
		NounType times = addNounType("Time");
		Noun morning1 = times.addNoun("morning1");
		Noun morning2 = times.addNoun("morning2");
		Noun afternoon1 = times.addNoun("afternoon1");
		Noun afternoon2 = times.addNoun("afternoon2");
		Noun evening1 = times.addNoun("evening1");
		Noun evening2 = times.addNoun("evening2");

		NounType dogs = addNounType("Dog");
		Noun beggare = dogs.addNoun("Beggare");
		Noun grady = dogs.addNoun("Grady");
		Noun inka = dogs.addNoun("Inka");
		Noun kamir = dogs.addNoun("Kamir");
		Noun topol = dogs.addNoun("Topol");
		Noun whiley = dogs.addNoun("Whiley");

		NounType breeds = addNounType("Breed");
		Noun bulldog = breeds.addNoun("bulldog", "Bulldog-F");
		Noun spaniel = breeds.addNoun("cocker spaniel", "Spaniel-M");
		Noun dalmatian = breeds.addNoun("Dalmation", "Dalmatian-M");
		Noun doberman = breeds.addNoun("Doberman pinscher", "Doberman-F");
		Noun shepherd = breeds.addNoun("German shepherd", "Shepherd-M");
		Noun poodle = breeds.addNoun("poodle", "Poodle-F");

		NounType firstNames = addNounType("First Name");
		Noun amy = firstNames.addNoun("Amy");
		Noun dave = firstNames.addNoun("Dave");
		Noun john = firstNames.addNoun("John");
		Noun marie = firstNames.addNoun("Marie");
		Noun sam = firstNames.addNoun("Sam");
		Noun sameFirst = firstNames.addNoun("SameFirst");

		NounType lastNames = addNounType("Last Name");
		Noun baker = lastNames.addNoun("Baker");
		Noun frye = lastNames.addNoun("Frye");
		Noun miller = lastNames.addNoun("Miller");
		Noun wolfe = lastNames.addNoun("Wolfe");
		Noun ziel = lastNames.addNoun("Ziel");
		Noun sameLast = lastNames.addNoun("SameLast");

		// Links.
		walkedEarlierThan = addLink("walked earlier than", times);
		walkedEarlierThan.f = (noun1, noun2) ->
			(noun1.num < 3 && noun2.num > 2) || ((noun1.num == 3 || noun1.num == 4) && noun2.num > 4) ? Is : IsNot;

		walkedTogether = addLink("walked together with", times);
		walkedTogether.f = (noun1, noun2) ->
			(noun1.num == 1 && noun2.num == 2) || (noun1.num == 2 && noun2.num == 1) ||
			(noun1.num == 3 && noun2.num == 4) || (noun1.num == 4 && noun2.num == 3) ||
			(noun1.num == 5 && noun2.num == 6) || (noun1.num == 6 && noun2.num == 5) ? Is : IsNot;

		// Facts.
		List<Noun> guys = getList(dave, john, sam);
		List<Noun> gals = getList(amy, marie);

		List<Noun> dogsM = getList(spaniel, dalmatian, shepherd);
		List<Noun> dogsF = getList(bulldog, doberman, poodle);

		// Intro. One person owns two dogs. To avoid duplicate solutions, don't put SameFirst in the first slot for each walk.
		addFact("0", sameFirst, Is, With, sameLast, "One person owns two dogs, so pair SameFirst with SameLast.");
		addFact("0", sameFirst, IsNot, With, getList(morning1, afternoon1, evening1));

		// Clue 1. Only one of the male dogs is walked with a female dog, and these two dogs belong to the the same owner.
		// See rule 2.

		// Clue 2. Billy walks the male Dalmatian sometime earlier in the day than he walks "Topol", who is also male.
		addFact("2", dalmatian, Is, walkedEarlierThan, topol);
		addFact("2,7", topol, IsNot, With, dogsF);

		// Clue 3. The female poodle (which doesn't belong to Marie) is walked sometime later in the day than "Beggare" (who is a male).
		addFact("3", poodle, IsNot, With, marie);
		addFact("3", beggare, Is, walkedEarlierThan, poodle);
		addFact("3", beggare, IsNot, With, dogsF);

		// Clue 4. Dave only owns one dog, a male.
		addFact("4", dave, IsNot, walkedTogether, sameFirst);
		addFact("4", dave, IsNot, With, dogsF);

		// From clues 1 and 4, Dave's male dog can only be walked with another male dog.
		addFact("1,4", dave, IsNot, walkedTogether, dogsF);

		// Clue 5. "Inka" isn't the poodle.
		addFact("5", inka, IsNot, With, poodle);

		// Clue 6. Billy has worked for Mr. Miller longer than he has for John.
		addFact("6", miller, IsNot, With, gals);
		addFact("6", miller, IsNot, With, john);

		// Clue 7. The female bulldog is walked sometime later in the day than "Grady" (who isn't the Dalmatian).
		addFact("7", grady, Is, walkedEarlierThan, bulldog);
		addFact("7", grady, IsNot, With, dalmatian);

		// Clue 8. John's dog and Ms. Ziel's dog are walked together, but not just after school.
		addFact("8", john, Is, walkedTogether, ziel);
		addFact("8", ziel, IsNot, With, guys);

		List<Noun> list8 = getList(john, ziel);
		addFact("8", list8, IsNot, With, getList(afternoon1, afternoon2));

		// From clues 1, 4, and 8, John's dog and Ms. Ziel's dog are both female.
		addFact("1,4,8", list8, IsNot, With, dogsM);

		// Clue 9. Billy works for Marie (who doesn't own the Dalmatian) sometime earlier in the day than he works for Baker (who doesn't own the Doberman pinscher).
		addFact("9", marie, IsNot, With, dalmatian);
		addFact("9", marie, Is, walkedEarlierThan, baker);
		addFact("9", baker, IsNot, With, doberman);

		// Clue 10. Neither the male cocker spaniel nor the Dalmatian is named "Beggare."
		addFact("10", beggare, IsNot, With, getList(spaniel, dalmatian));

		// Clue 11. "Whiley" (who belongs to a woman) isn't walked with "Kamir."
		addFact("11", whiley, IsNot, With, guys);
		addFact("11", whiley, IsNot, walkedTogether, kamir);

		// Clue 12. John doesn't own the bulldog.
		addFact("12", john, IsNot, With, bulldog);

		// Clue 13. "Beggare" (who isn't the Doberman pinscher) isn't owned by Wolfe.
		addFact("13", beggare, IsNot, With, getList(doberman, wolfe));

		// The two missing sexes are easily derived from clues 3, 10, and 13.
		addFact("3,10,13", beggare, Is, With, shepherd);

		// Rules.
		Rule rule1 = addRule("0", "To avoid duplicates the first names for each walk are sorted by their number.");
		rule1.f = (mark) -> {
			int rs = 0;

			// Violation if both first names for a walk are not in alphabetical order.
			if (mark.verb == Is && mark.noun1.type == times && mark.noun2.type == firstNames) {
				Noun timeA, fnameA;
				Noun timeB, fnameB;
				int i = mark.noun1.num - 1;
				if (i % 2 == 0) {
					timeA = mark.noun1; fnameA = mark.noun2; timeB = times.nouns.get(i + 1);
					fnameB = Mark.getPairNoun(timeB, firstNames);
				}
				else {
					timeB = mark.noun1; fnameB = mark.noun2; timeA = times.nouns.get(i - 1);
					fnameA = Mark.getPairNoun(timeA, firstNames);
				}
				// Make the comparison if both first names are known.
				if (fnameA != null && fnameB != null && fnameA.num > fnameB.num) return -1;
			}

			// Trigger. If there are only two unique first names available for a walk, place them in alphabetical order.
			// Note: SameLast is forced to be in the 2nd slot of each walk.
			for (Noun time1 : times.nouns) {
				if (time1.num % 2 == 0) continue;
				if (Mark.getPairNoun(time1, firstNames) != null) continue;
				Noun time2 = times.nouns.get(time1.num);
				if (Mark.getPairNoun(time2, firstNames) != null) continue;

				List<Noun> names = new ArrayList<>();
				for (Noun name : firstNames.nouns) {
					if (solver.canBeWith(name, time1) || solver.canBeWith(name, time2)) names.add(name);
				}
				if (names.size() != 2) continue;

				String msg = "There are only two first names available for this walk.";
				print(msg);
				if (names.get(0).num < names.get(1).num) {
					rs = solver.addMarkByRule(mark, rule1, ' ', time1, Is, names.get(0), msg); if (rs != 0) return rs;
					rs = solver.addMarkByRule(mark, rule1, ' ', time2, Is, names.get(1), msg); if (rs != 0) return rs;
				}
				else {
					rs = solver.addMarkByRule(mark, rule1, ' ', time1, Is, names.get(1), msg); if (rs != 0) return rs;
					rs = solver.addMarkByRule(mark, rule1, ' ', time2, Is, names.get(0), msg); if (rs != 0) return rs;
				}
			}

			// Trigger. If SameFirst and another first name are both known for a walk, then if there are only two dogs available for a walk, place them in alphabetical order.
			// Note: SameLast is forced to be in the 2nd slot of each walk.
			for (Noun time1 : times.nouns) {
				if (time1.num % 2 == 0) continue;
				Noun fname1 = Mark.getPairNoun(time1, firstNames); if (fname1 == null) continue;
				Noun time2 = times.nouns.get(time1.num);
				Noun fname2 = Mark.getPairNoun(time2, firstNames); if (fname2 != sameFirst) continue;

				List<Noun> names = new ArrayList<>();
				for (Noun name : dogs.nouns) {
					if (solver.canBeWith(name, time1) || solver.canBeWith(name, time2)) names.add(name);
				}
				if (names.size() != 2) continue;

				String msg = "There are only two dogs available for this walk.";
				print(msg);
				if (names.get(0).num < names.get(1).num) {
					rs = solver.addMarkByRule(mark, rule1, ' ', time1, Is, names.get(0), msg); if (rs != 0) return rs;
					rs = solver.addMarkByRule(mark, rule1, ' ', time2, Is, names.get(1), msg); if (rs != 0) return rs;
				}
				else {
					rs = solver.addMarkByRule(mark, rule1, ' ', time1, Is, names.get(1), msg); if (rs != 0) return rs;
					rs = solver.addMarkByRule(mark, rule1, ' ', time2, Is, names.get(0), msg); if (rs != 0) return rs;
				}
			}

			return rs;
		};

		Rule rule2 = addRule("1", "Only the same owner has one male dog walked with a female dog.", getList(sameFirst));
		rule2.f = (mark) -> {
			int rs = 0;

			// Get the first slot of each walk, where SameFirst can only be in the second slot of each walk.
			int n = times.nouns.size();
			for (int i = 0; i < n - 1; i += 2) {
				Noun timeA = times.nouns.get(i);
				Noun timeB = times.nouns.get(i + 1);

				Noun fnameB = Mark.getPairNoun(timeB, firstNames);

				Noun sexA = Mark.getPairNoun(timeA, breeds);
				Noun sexB = Mark.getPairNoun(timeB, breeds);

				if (sexA != null && sexB != null) {
					boolean b = isSameSex(sexA, sexB);

					// Violation if SameFirst walks two dogs of the same gender.
					if ((fnameB == sameFirst) && b) return -1;

					// Violation if two dogs of different genders are walked together and either:
					// a) The fnameB is known and it is not SameFirst.
					// b) SameFirst is with another walk.
					if (!b) {
						if (fnameB != null && fnameB != sameFirst) return -1;
						if (fnameB != sameFirst) {
							timeB = Mark.getPairNoun(sameFirst, times);
							if (timeB != null) return -1;
						}
					}

					// Trigger. If both sexes are the same, then SameFirst is not in this walk.
					if (b) {
						String msg = "SameFirst is not with two dogs of the same sex.";
						rs = solver.addMarkByRule(mark, rule2, ' ', timeB, IsNot, sameFirst, msg);
						if (rs != 0) return rs;
					}
				}

				// Trigger: If SameFirst is in the walk, and one dog's sex is known, then the other dog cannot have this sex.
				if (fnameB == sameFirst) {
					String msg = "Both dogs with SameFirst must have the same sex.";
					if (sexA != null && sexB == null) {
						for (Noun noun : breeds.nouns) {
							if (!isSameSex(sexA, noun)) continue;
							//print("rule 2a trigger for " + timeB + " and " + noun);
							rs = solver.addMarkByRule(mark, rule2, 'a', timeB, IsNot, noun, msg);
							if (rs != 0) return rs;
						}
					}
					if (sexA == null && sexB != null) {
						for (Noun noun : breeds.nouns) {
							if (!isSameSex(sexB, noun)) continue;
							//print("rule 2b trigger for " + timeA + " and " + noun);
							rs = solver.addMarkByRule(mark, rule2, 'b', timeA, IsNot, noun, msg);
							if (rs != 0) return rs;
						}
					}
				}

				// Trigger: If SameFirst is in another walk, and one dog's sex is known, then the other dog must have this sex.
				timeB = Mark.getPairNoun(sameFirst, times);
				if (fnameB != sameFirst && timeB != null) {
					String msg = "Both dogs not with SameFirst must have the opposite sex.";
					if (sexA != null && sexB == null) {
						for (Noun noun : breeds.nouns) {
							if (isSameSex(sexA, noun)) continue;
							rs = solver.addMarkByRule(mark, rule2, 'c', timeB, IsNot, noun, msg);
							if (rs != 0) return rs;
						}
					}
					if (sexA == null && sexB != null) {
						for (Noun noun : breeds.nouns) {
							if (isSameSex(sexB, noun)) continue;
							rs = solver.addMarkByRule(mark, rule2, 'd', timeA, IsNot, noun, msg);
							if (rs != 0) return rs;
						}
					}
				}
			}

			return rs;
		};

		// Marie doesn't own the poodle (clue 3) or the Dalmatian (clue 9). The facts make sure Marie does not own the poodle or the Dalmatian.
		// But if the the poodle or the Dalmatian ends up with SameFirst, then the dog for SameFirst cannot be walked with Maria's dog.
		Rule rule3 = addRule("3,9", "Marie does not own the poodle or the Dalmatian.", getList(sameFirst, marie, dalmatian, poodle));
		rule3.f = (mark) -> {
			int rs = 0;

			// See if the poodle or the Dalmatian belongs to SameFirst.
			Noun breed2 = Mark.getPairNoun(sameFirst, breeds); if (breed2 == null) return rs;
			if (breed2 != poodle && breed2 != dalmatian) return rs;

			// Get the time slot for SameFirst (either morning2, afternoon2, or evening2)
			Noun time2 = Mark.getPairNoun(sameFirst, times); if (time2 == null) return rs;

			// Violation if poodle or Dalmatian is walked at the same time as Marie's dog.
			Noun time1 = Mark.getPairNoun(marie, times);
			if (time1 != null && time1.num == time2.num - 1) return -1;

			// Trigger: If SameFirst's dog is the Dalmatian or the poodle, then Marie's dog was not walked with SameFirst.
			String msg = "Marie's dog is not walked with the Dalmatian or poodle.";
			time1 = times.nouns.get(time2.num - 2);
			//print("rule 3: Maria is not with " + time1);
			rs = solver.addMarkByRule(mark, rule3, 'a', marie, IsNot, time1, msg);
			if (rs != 0) return rs;

			return rs;
		};

		// Whiley belongs to a woman (clue 11). The facts make sure Whiley does not belong to Dave, John, or Sam.
		// But if Whiley ends up with SameFirst, then SameFirst must be on the same walk as a woman.
		Rule rule4 = addRule("11", "Whiley belongs to a woman.", getList(sameFirst, whiley));
		Function<Mark, Integer> f4 = smartRule.getMatchAtLeastOne(rule4, whiley, gals);
		rule4.f = (mark) -> {
			int rs = 0;
			String msg;

			rs = f4.apply(mark);
			if (rs != 0) return rs;

			// See if Whiley belongs to SameFirst
			if (Mark.getPairNoun(sameFirst, dogs) != whiley) return rs;

			// Get the time slot for SameFirst (either morning2, afternoon2, or evening2).
			Noun time2 = Mark.getPairNoun(sameFirst, times);
			if (time2 == null) return rs;

			// Get the time slot for the dog walked with Whiley (either morning1, afternoon1, or evening1).
			Noun time1 = times.nouns.get(time2.num - 2);

			// Violation if Whiley belongs to SameFirst, and the dog walked with Whiley belongs to a man.
			Noun fname1 = Mark.getPairNoun(time1, firstNames);
			if (fname1 == dave || fname1 == john || fname1 == sam) return -1;

			// Trigger. If Whiley belongs to SameFirst, then Whiley is not walked with a man's dog.
			msg = "Whiley belongs to a woman.";
			for (Noun noun : firstNames.nouns) {
				if (noun == amy || noun == marie || noun == sameFirst) continue;
				//print("rule 4: SameFirst is not walked with " + noun);
				rs = solver.addMarkByRule(mark, rule4, 'a', time1, IsNot, noun, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};

		// Solution.
		answer = new int[][]{{0, 3, 1, 4, 5, 2}, {4, 2, 5, 1, 0, 3}, {4, 2, 5, 1, 0, 3}, {3, 1, 4, 5, 0, 2}, {1, 2, 0, 5, 4, 3}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Time, 2=Dog, 3=Breed, 4=First Name, 5=Last Name
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
					case 1: break;
					case 2:
						if (link == walkedTogether)
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
							msg = noun1.name + " " + verb.name + " owned by " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = noun1.name + " " + verb.name + " owned by " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link != With)
							msg = "The " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + "'s owner " + verb.name + " " + noun2.name;
						break;
					case 5: break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + "'s dog " + verb.name + " walked in the " + noun2.name + " time slot";
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s dog " + verb.name + " the " + noun2.name;
						else
							msg = noun1.name + "'s dog " + verb.name + " " + link.name + " the " + noun2.name;
						break;
					case 4: break;
					case 5:
						if (link == walkedTogether)
							msg = noun1.name + "'s dog " + verb.name + " " + link.name + " with " + noun2.name + "'s dog";
						else if (link == walkedEarlierThan)
							msg = noun1.name + "'s dog " + verb.name + " " + link.name + " " + noun2.name + "'s dog";
						break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + "'s dog " + verb.name + " walked in the " + noun2.name + " time slot";
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + "' dog " + verb.name + " the " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s first name " + verb.name + " " + noun2.name;
						break;
					case 5: break;
				}
				break;
		}

		return msg + ".";
	}
}
