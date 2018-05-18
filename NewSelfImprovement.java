package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class NewSelfImprovement extends Puzzle {
	private final NounType oldSeats, firstNames, lastNames, colors, newSeats;
	
	// Special. Returns the function used for nextTo and newNextTo.
	private BiFunction<Noun, Noun, Verb> getIsNextTo() {
		return (Noun noun1, Noun noun2) -> (noun1.num == noun2.num - 1) || (noun1.num == noun2.num + 1)
			|| (noun1.num == 1 && noun2.num == 10) || (noun1.num == 10 && noun2.num == 1) ? Is : IsNot;
	}
	
	// Special. Returns the function used for directlyAcrossFrom and newDirectlyAcrossFrom.
	private BiFunction<Noun, Noun, Verb> getIsDirectlyAcrossFrom() {
		return (Noun noun1, Noun noun2) -> (noun1.num == 1 && noun2.num == 6) || (noun1.num == 6 && noun2.num == 1)
			 || (noun1.num == 2 && noun2.num == 10) || (noun1.num == 10 && noun2.num == 2)
			 || (noun1.num == 3 && noun2.num == 9) || (noun1.num == 9 && noun2.num == 3)
			 || (noun1.num == 4 && noun2.num == 8) || (noun1.num == 8 && noun2.num == 4)
			 || (noun1.num == 5 && noun2.num == 7) || (noun1.num == 7 && noun2.num == 5) ? Is : IsNot;
	}
	
	NewSelfImprovement() {
		// Properties.
		myName = "NewSelfImprovement";
		myTitle = "New Self-Improvement Committee";

		// Nouns.
		oldSeats = addNounType("Old Seat");
		Noun oldSeat01 = oldSeats.addNoun("01-old");
		Noun oldSeat02 = oldSeats.addNoun("02-old");
		Noun oldSeat03 = oldSeats.addNoun("03-old");
		Noun oldSeat04 = oldSeats.addNoun("04-old");
		Noun oldSeat05 = oldSeats.addNoun("05-old");
		Noun oldSeat06 = oldSeats.addNoun("06-old");
		Noun oldSeat07 = oldSeats.addNoun("07-old");
		Noun oldSeat08 = oldSeats.addNoun("08-old");
		Noun oldSeat09 = oldSeats.addNoun("09-old");
		Noun oldSeat10 = oldSeats.addNoun("10-old");

		firstNames = addNounType("First Name");
		Noun judy = firstNames.addNoun("Judy");
		Noun brian = firstNames.addNoun("Brian");
		Noun frank = firstNames.addNoun("Frank");
		Noun james = firstNames.addNoun("James");
		Noun john = firstNames.addNoun("John");
		Noun karen = firstNames.addNoun("Karen");
		Noun pam = firstNames.addNoun("Pam");
		Noun sam = firstNames.addNoun("Sam");
		Noun shelli = firstNames.addNoun("Shelli");
		Noun susan = firstNames.addNoun("Susan");

		lastNames = addNounType("Last Name");
		Noun holland = lastNames.addNoun("Holland");
		Noun baker = lastNames.addNoun("Baker");
		Noun freeman = lastNames.addNoun("Freeman");
		Noun jones = lastNames.addNoun("Jones");
		Noun karnell = lastNames.addNoun("Karnell");
		Noun madson = lastNames.addNoun("Madson");
		Noun parks = lastNames.addNoun("Parks");
		Noun smith = lastNames.addNoun("Smith");
		Noun snow = lastNames.addNoun("Snow");
		Noun strom = lastNames.addNoun("Strom");

		colors = addNounType("Color");
		Noun jcolor = colors.addNoun("J-Color");
		Noun black = colors.addNoun("black");
		Noun orange = colors.addNoun("orange");
		Noun green = colors.addNoun("green");
		Noun red = colors.addNoun("red");
		Noun purple = colors.addNoun("purple");
		Noun brown = colors.addNoun("brown");
		Noun yellow = colors.addNoun("yellow");
		Noun white = colors.addNoun("white");
		Noun blue = colors.addNoun("blue");

		newSeats = addNounType("New Seat");
		Noun newSeat01 = newSeats.addNoun("01-new");
		Noun newSeat02 = newSeats.addNoun("02-new");
		Noun newSeat03 = newSeats.addNoun("03-new");
		Noun newSeat04 = newSeats.addNoun("04-new");
		Noun newSeat05 = newSeats.addNoun("05-new");
		Noun newSeat06 = newSeats.addNoun("06-new");
		Noun newSeat07 = newSeats.addNoun("07-new");
		Noun newSeat08 = newSeats.addNoun("08-new");
		Noun newSeat09 = newSeats.addNoun("09-new");
		Noun newSeat10 = newSeats.addNoun("10-new");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link nextTo = addLink("next to", oldSeats);
		nextTo.f = getIsNextTo();

		Link immediatelyRightOf = addLink("immediately right of", oldSeats);
		immediatelyRightOf.f = (noun1, noun2) ->
			(noun1.num + 1 == noun2.num) || (noun1.num == 10 && noun2.num == 1) ? Is : IsNot;

		Link directlyAcrossFrom = addLink("directly across from", oldSeats);
		directlyAcrossFrom.f = getIsDirectlyAcrossFrom();

		Link onSameSideAs = addLink("on the same side as", oldSeats);
		onSameSideAs.f = (noun1, noun2) ->
			(noun1.num > 1 && noun1.num < 6 && noun2.num > 1 && noun2.num < 6 && noun1.num != noun2.num) ||
			(noun1.num > 6 && noun2.num > 6 && noun1.num != noun2.num) ? Is : IsNot;

		Link newNextTo = addLink("next to", newSeats);
		newNextTo.f = getIsNextTo();

		Link newDirectlyAcrossFrom = addLink("directly across from", newSeats);
		newDirectlyAcrossFrom.f = getIsDirectlyAcrossFrom();

		// Facts.
		List<Noun> guys = getList(brian, frank, james, john, sam);
		List<Noun> gals = getList(karen, pam, shelli, susan);

		// Intro. Judy always sat in seat one.
		addFact("0", oldSeat01, Is, With, getList(judy, holland, jcolor, newSeat01));

		// Intro. After lunch, only Judy sat in the same seat.
		for (int i = 1; i < oldSeats.nouns.size(); i++) {
			Noun oldSeat = oldSeats.nouns.get(i);
			Noun newSelf = newSeats.nouns.get(i);
			addFact("0", oldSeat, IsNot, With, newSelf);
		}

		// Clue 1. No one has the same first and last initials.
		addFactsIsNotFirstChar("1", firstNames, lastNames, true);

		// Clue 2. After lunch, no one sat by a person they had sat by before.

		// Clue 3. Before lunch, no woman sat by another woman. Judy is in seat 1.
		// The other women are not in the even seats (2, 4, 6, 8, 10), and the men are not in the odd seats (3, 5, 7, 9).
		addFact("3", gals, IsNot, With, getList(oldSeat02, oldSeat04, oldSeat06, oldSeat08, oldSeat10));
		addFact("3", guys, IsNot, With, getList(oldSeat03, oldSeat05, oldSeat07, oldSeat09));

		// Clue 4. Before lunch, Shelli sat between Jones and the one in black.
		addFact("4", shelli, Is, nextTo, getList(jones, black));
		addFact("4", jones, IsNot, With, black);

		// Clue 5. John was dressed in orange.
		addFact("5", john, Is, With, orange);

		// Clue 6. At one time, Ms. Karnell sat by the one in green.
		addFact("6", karnell, IsNot, With, guys);

		// Clue 7. Before lunch, Judy recalled the one in red sat immediately to her right, and the one in green sat immediately to her left.
		addFact("7", red, Is, With, oldSeat10);
		addFact("7", green, Is, With, oldSeat02);

		// Clue 8. After lunch, Judy noticed that James sat immediately to her right, and Mr. Baker sat immediately to her left.

		// After lunch, James sat immediately to the right of Judy.
		addFact("8", james, Is, With, newSeat10);

		// After lunch, Mr. Baker sat immediately to the left of Judy.
		addFact("8", baker, Is, With, newSeat02);

		// Mr. Baker is not a woman.
		addFact("8", baker, IsNot, With, gals);

		// Clue 9. At one time, Snow (who didn't wear green), sat next to Strom.
		addFact("9", snow, IsNot, With, green);

		// Clue 10. The one in purple never sat directly across from the one in brown (who wasn't Pam).
		addFact("10", purple, IsNot, directlyAcrossFrom, brown);
		addFact("10", purple, IsNot, newDirectlyAcrossFrom, brown);
		addFact("10", pam, IsNot, With, brown);

		// Clue 11. Karen (who is not Freeman) never sat by another woman.
		addFact("11", karen, IsNot, With, freeman);
		addFact("11", karen, IsNot, newNextTo, getList(judy, pam, shelli, susan));

		// Clue 12. Jones was not dressed in red.
		addFact("12", jones, IsNot, With, red);

		// Clue 13. Before lunch, Parks sat directly across from Judy.
		addFact("13", parks, Is, With, oldSeat06);

		// Clue 14. After lunch, the one in yellow had moved one chair to his right.
		addFact("14", yellow, IsNot, With, gals);

		// Clue 15. Susan (who never sat by the one in green) and Freeman sat directly across from each other both before and after lunch.
		addFact("15", susan, IsNot, nextTo, green);
		addFact("15", susan, IsNot, newNextTo, green);
		addFact("6,15", susan, IsNot, With, karnell);
		addFact("15", susan, Is, directlyAcrossFrom, freeman);
		addFact("15", susan, Is, newDirectlyAcrossFrom, freeman);

		// Clue 16. After lunch, the one in white did not sit where the one in black had sat, nor where the one in orange had sat.

		// Clue 17. Before lunch, Shelli was sitting on the same side of the table as the one in red.
		addFact("17", shelli, Is, onSameSideAs, red);

		// Clue 18. Karen was not wearing blue or brown.
		addFact("18", karen, IsNot, With, getList(blue, brown));

		// Clue 19. Before lunch, Freeman was sitting next to Brian (who did not wear yellow).
		addFact("19", freeman, Is, nextTo, brian);
		addFact("19", brian, IsNot, With, yellow);

		// Rules.
		Rule rule1 = addRule("2", "After lunch, no one sat by a person they had sat by before..");
		Rule rule2 = addRule("6", "At one time, Ms. Karnell sat by the one in green.", getList(karnell, green));
		Rule rule3 = addRule("9", "At one time, Snow sat next to Strom.", getList(snow, strom));
		Rule rule4 = addRule("14", "After lunch, the one in yellow had moved one chair to his right.", getList(yellow));
		Rule rule5 = addRule("16", "After lunch, the one in white did not sit where the one in black had sat.", getList(white, black));
		Rule rule6 = addRule("16", "After lunch, the one in white did not sit where the one in orange had sat.", getList(white, orange));

		// Define function for each rule.
		char ch = ' ';

		// After lunch, no one sat next to a person they had sat next to before lunch (clue 2).
		rule1.f = (mark) -> {
			int rs = 0;

			// Violation:
			// If last mark has seat noun1 with new seat noun2
			// and noun1 is next to seat noun3, and new seat noun2 is next to new seat noun4
			// then seat noun3 cannot be with new seat noun4.
			if (mark.noun1.type == oldSeats && mark.verb == Is && mark.noun2.type == newSeats) {
				for (Noun noun3 : oldSeats.nouns) {
					if (nextTo.f.apply(mark.noun1, noun3) != Is) continue;

					for (Noun noun4 : newSeats.nouns) {
						if (nextTo.f.apply(mark.noun2, noun4) != Is) continue;

						String msg = noun3.name + " is not with " + noun4.name + ".";
						rs = solver.addMarkByRule(mark, rule1, ch, noun3, IsNot, noun4, msg);
						if (rs != 0) return rs;
					}
				}
			}
			return rs;
		};


		// At one time, Ms. Karnell sat next to the one in green (clue 6).
		rule2.f = (mark) -> {
			int rs = 0;

			// Violation if both old seats and new seats for Karnell and green are not next to each other.
			Noun oldSeat1 = Mark.getPairNoun(karnell, oldSeats); if (oldSeat1 == null) return rs;
			Noun oldSeat2 = Mark.getPairNoun(green, oldSeats); if (oldSeat2 == null) return rs;

			Noun newSeat1 = Mark.getPairNoun(karnell, newSeats);
			Noun newSeat2 = Mark.getPairNoun(green, newSeats);
			if (newSeat1 != null && newSeat2 != null) {
				if (nextTo.f.apply(oldSeat1, oldSeat2) == IsNot && nextTo.f.apply(newSeat1, newSeat2) == IsNot) return -1;
			}

			// Trigger: If old seats for Karnell and green are not next to each other, and both do not have new seats,
			// then green's new seats must be next to at least one possible new seat for Karnell.
			if (nextTo.f.apply(oldSeat1, oldSeat2) == Is) return rs;
			if (newSeat1 != null || newSeat2 != null) return rs;

			for (Noun slot2 : newSeats.nouns) {
				if (solver.getGridVerb(slot2, green) != Maybe) continue;
				// See if any new seat for Karnell can be next to newSeat1.
				boolean ok = false;
				for (Noun slot1 : newSeats.nouns) {
					if (slot1 == slot2) continue;
					if (solver.getGridVerb(slot1, karnell) != Maybe) continue;
					if (nextTo.f.apply(slot1, slot2) == Is) {
						ok = true;
						break;
					}
				}
				if (!ok) {
					String msg = slot2.name + " is not " + nextTo.name + " any new seat for " + karnell.name + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule2, ch, slot2, IsNot, green, msg);
					if (rs != 0) return rs;
				}
			}

			return rs;
		};

		// At one time, Snow, sat next to Strom (clue 9).
		rule3.f = (mark) -> {
			int rs = 0;

			// Violation if old seats and new seats for Snow and Strom are not next to each other.
			Noun oldSeat1 = Mark.getPairNoun(snow, oldSeats);
			Noun oldSeat2 = Mark.getPairNoun(strom, oldSeats);
			Noun newSeat1 = Mark.getPairNoun(snow, newSeats);
			Noun newSeat2 = Mark.getPairNoun(strom, newSeats);

			if (oldSeat1 != null && oldSeat2 != null) {
				if (newSeat1 != null && newSeat2 != null) {
					if (nextTo.f.apply(oldSeat1, oldSeat2) == IsNot && nextTo.f.apply(newSeat1, newSeat2) == IsNot) return -1;
				}
			}

			// Don't invoke trigger if they can sit next to each other before lunch.
			if (solver.maybeRelated(snow, nextTo, strom)) return rs;


			// When one has a new seat, see if the other can be placed.
			if (newSeat1 != null && newSeat2 == null) {
				// Snow has a new seat, so see if there is exactly one seat next to Snow for Strom.
				Noun seat2 = null;
				int cnt = 0;
				for (Noun seat : newSeats.nouns) {
					if (nextTo.f.apply(newSeat1, seat) == IsNot) continue;
					if (solver.getGridVerb(seat, strom) != Maybe) continue;
					seat2 = seat;
					if (++cnt > 1) break;
				}
				if (cnt == 1) {
					String msg = seat2.name + " is the only one " + nextTo.name + " " + snow.name + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule3, ch, seat2, Is, strom, msg);
					if (rs != 0) return rs;
				}
			}
			else if (newSeat1 == null && newSeat2 != null) {
				// Strom has a new seat, so see if there is exactly one seat next to Strom for Snow.
				Noun seat2 = null;
				int cnt = 0;
				for (Noun seat : newSeats.nouns) {
					if (nextTo.f.apply(seat, newSeat2) == IsNot) continue;
					if (solver.getGridVerb(seat, snow) != Maybe) continue;
					seat2 = seat;
					if (++cnt > 1) break;
				}
				if (cnt == 1) {
					String msg = seat2.name + " is the only one " + nextTo.name + " " + strom.name + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule3, ch, seat2, Is, snow, msg);
					if (rs != 0) return rs;
				}
			}

			// 1. Each possible new seat for Snow must be next to at least one possible new seat for Strom.
			for (Noun slot1 : newSeats.nouns) {
				if (solver.getGridVerb(slot1, snow) != Maybe) continue;
				boolean ok = false;
				for (Noun slot2 : newSeats.nouns) {
					if (slot1 == slot2) continue;
					if (solver.getGridVerb(slot2, strom) != Maybe) continue;
					if (nextTo.f.apply(slot1, slot2) == Is) {
						ok = true;
						break;
					}
				}

				// Need to check this again.
				if (Mark.getPairNounNum(snow, newSeats) > 0 || Mark.getPairNounNum(strom, newSeats) > 0) break;

				if (!ok) {
					String msg = slot1.name + " will not be " + nextTo.name + " " + strom.name + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule3, ch, slot1, IsNot, snow, msg);
					if (rs != 0) return rs;
				}
			}

			// 2. Each possible new seat for Strom must be next to at least one possible new seat for Snow.
			for (Noun slot2 : newSeats.nouns) {
				if (solver.getGridVerb(slot2, strom) != Maybe) continue;
				boolean ok = false;
				for (Noun slot1 : newSeats.nouns) {
					if (slot1 == slot2) continue;
					if (solver.getGridVerb(slot1, snow) != Maybe) continue;
					if (nextTo.f.apply(slot1, slot2) == Is) {
						ok = true;
						break;
					}
				}
			// Need to check this again.
				if (Mark.getPairNounNum(snow, newSeats) > 0 || Mark.getPairNounNum(strom, newSeats) > 0) break;

				if (!ok) {
					String msg = snow.name + " will not be " + nextTo.name + " " + slot2.name + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule3, ch, slot2, IsNot, strom, msg);
					if (rs != 0) return rs;
				}
			}

			return rs;
		};

		// After lunch, the one in yellow had moved one chair to his right (clue 14).
		rule4.f = (mark) -> {
			int rs = 0;

			// Violation if yellow's new seat is not immediately right of yellow's old seat.
			Noun a = Mark.getPairNoun(yellow, oldSeats);
			Noun b = Mark.getPairNoun(yellow, newSeats);
			if (a != null && b != null && immediatelyRightOf.f.apply(b, a) == IsNot) return -1;

			// Trigger if yellow (noun2) is assigned a seat (noun1), then yellow's new seat (noun3) is immediately right of it.
			if (mark.noun1.type == oldSeats && mark.verb == Is && mark.noun2 == yellow) {
				// This works since seat 1 is not possible.
				int i = mark.noun1.num - 2;
				Noun noun3 = newSeats.nouns.get(i);
				String msg = yellow.name + " is in " + noun3.name + ".";
				rs = solver.addMarkByRule(mark, rule4, ch, mark.noun2, Is, noun3, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};

		// After lunch, the one in white did not sit where the one in black had sat (clue 16).
		rule5.f = (mark) -> {
			int rs = 0;

			// Violation if Black's old seat is the same as White's new seat.
			int a = Mark.getPairNounNum(black, oldSeats);
			int b = Mark.getPairNounNum(white, newSeats);
			if (a > 0 && b > 0 && a == b) return -1;

			// Trigger if Black (noun2) is assigned a seat (noun1), then White is not in that new seat (noun3).
			if (mark.noun1.type == oldSeats && mark.verb == Is && mark.noun2 == black) {
				Noun noun3 = newSeats.getNoun(mark.noun1.num);
				String msg = white.name + " is not in " + noun3.name + ".";
				rs = solver.addMarkByRule(mark, rule5, ch, white, IsNot, noun3, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};

		// After lunch, the one in white did not sit where the one in orange had sat (clue 16).
		rule6.f = (mark) -> {
			int rs = 0;

			// Violation if Orange's old seat is the same as White's new seat.
			int a = Mark.getPairNounNum(orange, oldSeats);
			int b = Mark.getPairNounNum(white, newSeats);
			if (a > 0 && b > 0 && a == b) return -1;

			// Trigger if Orange (noun2) is assigned a seat (noun1), then White is not in that new seat (noun3).
			if (mark.noun1.type == oldSeats && mark.verb == Is && mark.noun2 == orange) {
				Noun noun3 = newSeats.getNoun(mark.noun1.num);
				String msg = white.name + " is not in " + noun3.name + ".";
				rs = solver.addMarkByRule(mark, rule6, ch, white, IsNot, noun3, msg);
				if (rs != 0) return rs;
			}
			return rs;
		};

		// Solution.
		answer = new int[][]{{0, 1, 6, 4, 5, 3, 8, 7, 9, 2}, {0, 9, 2, 1, 8, 6, 4, 3, 5, 7}, {0, 3, 8, 2, 5, 1, 9, 7, 6, 4}, {0, 4, 8, 1, 5, 9, 3, 6, 2, 7}};
	}

	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Old Seat, 2=First Name, 3=Last Name, 4=Color, 5=New Seat
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = "Before lunch, the person in seat " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = "Before lunch, the person in seat " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = "Before lunch, the person in seat " + noun1.name + " " + verb.name + " wearing " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = "After lunch, the person previously in seat " + noun1.name + " " + verb.name + " in seat " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "Before lunch, " + noun1.name + " " + verb.name + " in seat " + noun2.name;
						break;
					case 2:
						if (link.nounType == newSeats)
							msg = "After lunch, " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						else if (link.nounType == oldSeats)
							msg = "Before lunch, " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						else if (link.nounType == newSeats)
							msg = "After lunch, " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + verb.name + " wearing " + noun2.name;
						else if (link.nounType == oldSeats)
							msg = "Before lunch, " + noun1.name + " " + verb.name + " " + link.name + " the one wearing " + noun2.name;
						else if (link.nounType == newSeats)
							msg = "After lunch, " + noun1.name + " " + verb.name + " " + link.name + " the one wearing " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = "After lunch, " + noun1.name + " " + verb.name + " in seat " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "Before lunch, " + noun1.name + " " + verb.name + " in seat " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = noun1.name + "'s first name " + verb.name + " " + noun2.name;
						else if (link.nounType == oldSeats)
							msg = "Before lunch, " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + verb.name + " wearing " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = "After lunch, " + noun1.name + " " + verb.name + " in seat " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "Before lunch, the one wearing " + noun1.name + " " + verb.name + " in seat " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = "The one wearing " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link.nounType == oldSeats)
							msg = "Before lunch, the one wearing " + noun1.name + " " + verb.name + " " + link.name + " the one wearing " + noun2.name;
						if (link.nounType == newSeats)
							msg = "After lunch, the one wearing " + noun1.name + " " + verb.name + " " + link.name + " the one wearing " + noun2.name;
						break;
					case 5: break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "After lunch, the person in seat " + noun1.name + " " + verb.name + " previously in seat " + noun2.name;
						break;
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
