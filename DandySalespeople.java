package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class DandySalespeople extends Puzzle {
	DandySalespeople() {
	// Properties.
	myName = "DandySalespeople";
	myTitle = "Dandy Salespeople";

	// Nouns.
	NounType months = addNounType("Month");
	Noun january = months.addNoun("January");
	Noun february = months.addNoun("February");
	Noun march = months.addNoun("March");
	Noun april = months.addNoun("April");
	Noun may = months.addNoun("May");
	Noun june = months.addNoun("June");
	Noun july = months.addNoun("July");
	Noun august = months.addNoun("August");
	Noun september = months.addNoun("September");
	Noun october = months.addNoun("October");
	Noun november = months.addNoun("November");
	Noun december = months.addNoun("December");

	NounType firstNames = addNounType("First Name");
	Noun beverly = firstNames.addNoun("Beverly");
	Noun bruce = firstNames.addNoun("Bruce");
	Noun carmen = firstNames.addNoun("Carmen");
	Noun gary = firstNames.addNoun("Gary");
	Noun janice = firstNames.addNoun("Janice");
	Noun marcia = firstNames.addNoun("Marcia");
	Noun millie = firstNames.addNoun("Millie");
	Noun paulette = firstNames.addNoun("Paulette");
	Noun sam = firstNames.addNoun("Sam");
	Noun steven = firstNames.addNoun("Steven");
	Noun stuart = firstNames.addNoun("Stuart");
	Noun willa = firstNames.addNoun("Willa");

	NounType lastNames = addNounType("Last Name");
	Noun carter = lastNames.addNoun("Carter");
	Noun chase = lastNames.addNoun("Chase");
	Noun foote = lastNames.addNoun("Foote");
	Noun harper = lastNames.addNoun("Harper");
	Noun jackson = lastNames.addNoun("Jackson");
	Noun norris = lastNames.addNoun("Norris");
	Noun prince = lastNames.addNoun("Prince");
	Noun sanders = lastNames.addNoun("Sanders");
	Noun straw = lastNames.addNoun("Straw");
	Noun swan = lastNames.addNoun("Swan");
	Noun vance = lastNames.addNoun("Vance");
	Noun vasquez = lastNames.addNoun("Vasquez");

	NounType ages = addNounType("Age");
	Noun ageT01 = ages.addNoun("A01");
	Noun ageT02 = ages.addNoun("A02");
	Noun ageT03 = ages.addNoun("A03");
	Noun ageT04 = ages.addNoun("A04");
	Noun ageT05 = ages.addNoun("A05");
	Noun ageT06 = ages.addNoun("A06");
	Noun ageT07 = ages.addNoun("A07");
	Noun ageT08 = ages.addNoun("A08");
	Noun ageT09 = ages.addNoun("A09");
	Noun ageT10 = ages.addNoun("A10");
	Noun ageT11 = ages.addNoun("A11");
	Noun ageT12 = ages.addNoun("A12");

	NounType cars = addNounType("Car");
	Noun accord = cars.addNoun("Accord");
	Noun aerostar = cars.addNoun("Aerostar");
	Noun caprice = cars.addNoun("Caprice");
	Noun civic = cars.addNoun("Civic");
	Noun corsica = cars.addNoun("Corsica");
	Noun grandPrix = cars.addNoun("Grand Prix");
	Noun lumina = cars.addNoun("Lumina");
	Noun previa = cars.addNoun("Previa");
	Noun sentra = cars.addNoun("Sentra");
	Noun silhouette = cars.addNoun("Silhouette");
	Noun skylark = cars.addNoun("Skylark");
	Noun taurus = cars.addNoun("Taurus");

	NounType cities = addNounType("City");
	Noun bell = cities.addNoun("Bell");
	Noun canoga = cities.addNoun("Canoga");
	Noun compton = cities.addNoun("Compton");
	Noun downey = cities.addNoun("Downey");
	Noun gardena = cities.addNoun("Gardena");
	Noun hollywood = cities.addNoun("Hollywood");
	Noun inglewood = cities.addNoun("Inglewood");
	Noun laPuente = cities.addNoun("La Puente");
	Noun lomita = cities.addNoun("Lomita");
	Noun maywood = cities.addNoun("Maywood");
	Noun torrance = cities.addNoun("Torrance");
	Noun whittier = cities.addNoun("Whittier");

	// Verbs.
	IsNot.name = "was not";
	Is.name = "was";

	// Links.
	Link before = addLink("earlier in the year than", months);
	before.f = SmartLink.getIsLessThan(0);

	Link oneMonthBefore = addLink("one month before", months);
	oneMonthBefore.f = SmartLink.getIsLessBy(1);

	Link sixMonthsBefore = addLink("six months before", months);
	sixMonthsBefore.f = SmartLink.getIsLessBy(6);

	// Facts and rules.
	List<Noun>  guys = getList(bruce, gary, sam, steven, stuart);
	List<Noun>  gals = getList(beverly, carmen, janice, marcia, millie, paulette, willa);

	// Intro. Each age is assigned to a month, where Jan has A01, Feb has A02, etc.
	addFactsOneToOne("0", months, Is, With, ages);

	// Clue 1. No two winners are the same age.
	// This is rule 4. All other ages are determined by rules as well.


	// Clue 2. The Taurus was won by a man.
	addFact("2", taurus, IsNot, With, gals);


	// Clue 3. Carmen Vasquez is twice as old as the woman from the Lomita lot, who won the month before Paulette did.
	addFact("3", carmen, Is, With, vasquez);
	addFact("3", carmen, IsNot, With, lomita);
	addFact("3", lomita, Is, oneMonthBefore, paulette);
	addFact("3", lomita, IsNot, With, guys);


	// Clue 4. The man who won the Caprice is half as old as the woman from Maywood, who is in turn two years older than the woman from La Puente.
	// No woman won the Caprice.
	addFact("4", caprice, IsNot, With, gals);
	addFact("4", maywood, IsNot, With, guys);
	addFact("4", laPuente, IsNot, With, guys);


	// Clue 5. The February winner, who works in the Downey lot, is twice as old as Prince and three times as old as Stuart (who won the Accord).
	addFact("5", getList(february, prince, stuart), IsNot, With);
	addFact("5", february, Is, With, downey);
	addFact("5", stuart, Is, With, accord);


	// Clue 6. Janice won later in the year than did the man from the Torrance lot (who isn't 20 years old); he in turn won later in the year than someone else.
	addFact("6", torrance, Is, before, janice);
	addFact("6", torrance, IsNot, With, january);
	addFact("6", torrance, IsNot, With, gals);


	// Clue 7. The Hollywood winner is older than the Whittier winner.

	// Clue 8. The 50-year-old won the Previa.


	// Clue 9. The last names of the March and April winners, the cities where the September and October winners work,
	// the names of the cars won by the January, February, and July winners, and the first name of the May winner all
	// begin with the same letter.

	// From just looking at the cities, the first letter must be 'C'!

	// The last names of the March and April winners must begin with 'C'.
	addFactsStartsWith("9", march, lastNames, false, 'C');
	addFactsStartsWith("9", april, lastNames, false, 'C');

	// The cities where the September and October winners work must begin with 'C'.
	addFactsStartsWith("9", september, cities, false, 'C');
	addFactsStartsWith("9", october, cities, false, 'C');

	// The names of the cars won in January, February, and July must begin with 'C'.
	// TODO: If needed, I could specify that the Caprice, Civic, and Corsica can only be won in January, February, and July.
	addFactsStartsWith("9", january, cars, false, 'C');
	addFactsStartsWith("9", february, cars, false, 'C');
	addFactsStartsWith("9", july, cars, false, 'C');

	// The first name of the May winner must begin with 'C'.
	addFactsStartsWith("9", may, firstNames, false, 'C');

	// Clue 10. The first names of Chase and Norris start with the same letter.
	// There must be two first names starting with the same letter for Chase and Norris.
	addFact("10", getList(chase, norris), IsNot, With, getList(carmen, gary, janice, paulette, willa));


	// Clue 11. Carter didn't win the Lumina.
	addFact("11", carter, IsNot, With, lumina);


	// Clue 12. The winner of the Skylark (which was awarded before the Sentra was) is twice as old as the man who won in November.
	addFact("12", skylark, Is, before, sentra);
	addFact("12", november, IsNot, With, skylark);
	addFact("12", november, IsNot, With, gals);


	// Clue 13. Marcia won her car six months before someone won the Aerostar.
	addFact("13", marcia, Is, sixMonthsBefore, aerostar);


	// Clue 14. The Maywood woman didn't win the Skylark.
	addFact("14", maywood, IsNot, With, skylark);

	// Clue 15. Mr. Jackson is ten years younger than Paulette, who is half as old as Ms. Harper.
	addFact("15", paulette, IsNot, With, harper);
	addFact("15", jackson, IsNot, With, gals);
	addFact("15", harper, IsNot, With, guys);


	// Clue 16. Sanders (who is twice as old as Straw) won a car one month after Steven did and one month before the Compton winner did.
	addFactsInSequence("16", getList(steven, sanders, compton), Is, oneMonthBefore);


	// Clue 17. These five people won cars in five consecutive months, from earliest to last: Mr. Foote,
	// the Civic winner, Willa, the Inglewood salesperson (who isn't Beverly), and the 46-year-old.
	addFactsInSequence("17", getList(foote, civic, willa, inglewood), Is, oneMonthBefore);
	addFact("17", inglewood, IsNot, With, beverly);
	addFact("17", foote, IsNot, With, gals);


	// Clue 18. These four people won cars in four consecutive months, from earliest to last:
	// the Silhouette winner, Ms. Vance, the woman from Bell, and the 26-year-old.
	addFactsInSequence("18", getList(silhouette, vance, bell), Is, oneMonthBefore);
	addFact("18", vance, IsNot, With, guys);
	addFact("18", bell, IsNot, With, guys);


	// Clue 19. Willa doesn't work in La Puente.
	addFact("19", willa, IsNot, With, laPuente);


	// Clue 20. Millie (who isn't Chase) is half as old as the woman who works in Gardena.
	addFact("20", millie, IsNot, With, getList(chase, gardena));
	addFact("20", gardena, IsNot, With, guys);


	// Clue 21. Each of the two men who won the Aerostar and the Grand Prix has a first and last name that start with the same letter.
	List<Noun>  cars21 = getList(aerostar, grandPrix);
	addFact("21", gals, IsNot, With, cars21);

	// Looking at the first and last names, the first letter must be 'S' for the two men!
	// The first name of the men who won the Aerostar and Grand Prix starts with 'S'.
	addFact("21", getList(bruce, gary), IsNot, With, cars21);

	// The last name of the men who won the Aerostar and Grid prix starts with 'S'.
	addFactsStartsWith("21", aerostar, lastNames, false, 'S');
	addFactsStartsWith("21", grandPrix, lastNames, false, 'S');

	// The last names for Sam and Steven must start with 'S'.
	addFactsStartsWith("21", sam, lastNames, false, 'S');
	addFactsStartsWith("21", steven, lastNames, false, 'S');


	// Clue 22. Bruce is half as old as Ms. Swan.
	addFact("22", swan, IsNot, With, guys);

	// Analysis facts.
	addFact("A1", prince, IsNot, With, october, "Ms. Prince is not the October winner.", false);
	addFact("A1", harper, IsNot, With, october, "Ms. Harper is not the October winner.", false);
	addFact("A1", swan, IsNot, With, october, "Ms. Swan is not the October winner.", false);
	addFact("A2", prince, IsNot, With, december, "Ms. Prince is not the December winner.", false);
	addFact("A2", harper, IsNot, With, december, "Ms. Harper is not the December winner.", false);
	addFact("A3", november, IsNot, With, torrance, "The November winner is not from Torrance.", false);

	// Rules.
	Rule rule1 = addRule("10", "The first names of Chase and Norris start with the same letter.", getList(chase, norris));
	Rule rule2 = addRule("21", "The man who won the Aerostar has a first and last name that start with the same letter.", getList(aerostar));
	Rule rule3 = addRule("21", "The man who won the Grand Prix has a first and last name that start with the same letter.", getList(grandPrix));
	Rule rule4 = addRule("0", "Enforces all clues regarding age.");

	// The first names of Chase and Norris start with the same letter (clue 10).
	rule1.f = (mark) -> {
		int rs = 0;

		Noun nounA = Mark.getPairNoun(chase, firstNames);  // Get first name of Chase
		Noun nounB = Mark.getPairNoun(norris, firstNames); // Get first name of Norris

		// Violation if the first names of Chase and Norris do not start with the same letter.
		if (nounA != null && nounB != null) {
			char c1 = nounA.name.charAt(0); // Get starting letter of Chase's first name
			char c2 = nounB.name.charAt(0); // Get starting letter of Norris's first name
			if (c1 != c2) return -1;
		}
		// Trigger if Chase's first name is known, then Norris's first name must start with the same letter.
		else if (nounA != null) {
			Noun noun1 = norris;
			// Get starting letter of Chase's first name
			char c1 = nounA.name.charAt(0);
			// Eliminate first names for Norris.
			for (Noun noun : firstNames.nouns) {
				char c2 = noun.name.charAt(0);
				if (c1 != c2) {
					String msg = "Since " + chase.name + "'s first name starts with a '" + c1 + "', then " + noun1.name + "'s first name cannot be " + noun.name + ".";
					rs = solver.addMarkByRule(mark, rule1, 'a', noun1, IsNot, noun, msg);
					if (rs != 0) return rs;
				}
			}
		}
		// Trigger if Norris's first name is known, then Chase's first name must start with the same letter.
		else if (nounB != null) {
			Noun noun1 = chase;
			// Get starting letter of Norris's first name
			char c2 = nounB.name.charAt(0);
			// Eliminate first names for Chase.
			for (Noun noun : firstNames.nouns) {
				char c1 = noun.name.charAt(0);
				if (c1 != c2) {
					String msg = "Since " + norris.name + "'s first name starts with a '" + c2 + "', then " + noun1.name + "'s first name cannot be " + noun.name + ".";
					rs = solver.addMarkByRule(mark, rule1, 'b', noun1, IsNot, noun, msg);
					if (rs != 0) return rs;
				}
			}
		}

		// Trigger if Chase's first name cannot start with 'B' then neither can Norris's first name.
		if (solver.getGridVerb(chase, beverly) == IsNot && solver.getGridVerb(chase, bruce) == IsNot) {
			Noun noun1 = norris;
			for (int i = 0; i < 2; i++) {
				Noun noun2 = firstNames.nouns.get(i);
				String msg = "Since " + chase.name + "'s first name cannot start with 'B', " + noun1.name + "'s first name is not " + noun2.name + ".";
				rs = solver.addMarkByRule(mark, rule1, 'c', noun1, IsNot, noun2, msg);
				if (rs != 0) return rs;
			}
		}

		// Trigger if Norris's first name cannot start with 'B' then neither can Chase's first name.
		if (solver.getGridVerb(norris, beverly) == IsNot && solver.getGridVerb(norris, bruce) == IsNot) {
			Noun noun1 = chase;
			for (int i = 0; i < 2; i++) {
				Noun noun2 = firstNames.nouns.get(i);
				String msg = "Since " + norris.name + "'s first name cannot start with 'B', " + noun1.name + "'s first name is not " + noun2.name + ".";
				rs = solver.addMarkByRule(mark, rule1, 'd', noun1, IsNot, noun2, msg);
				if (rs != 0) return rs;
			}
		}

		// Trigger if Chase's first name cannot start with 'M' then neither can Norris's first name.
		if (solver.getGridVerb(chase, marcia) == IsNot && solver.getGridVerb(chase, millie) == IsNot) {
			Noun noun1 = norris;
			for (int i = 5; i < 7; i++) {
				Noun noun2 = firstNames.nouns.get(i);
				String msg = "Since " + chase.name + "'s first name cannot start with 'M', " + noun1.name + "'s first name is not " + noun2.name + ".";
				rs = solver.addMarkByRule(mark, rule1, 'e', noun1, IsNot, noun2, msg);
				if (rs != 0) return rs;
			}
		}

		// Trigger if Norris's first name cannot start with 'M' then neither can Chase's first name.
		if (solver.getGridVerb(norris, marcia) == IsNot && solver.getGridVerb(norris, millie) == IsNot) {
			Noun noun1 = chase;
			for (int i = 5; i < 7; i++) {
				Noun noun2 = firstNames.nouns.get(i);
				String msg = "Since " + norris.name + "'s first name cannot start with 'M', " + noun1.name + "'s first name is not " + noun2.name + ".";
				rs = solver.addMarkByRule(mark, rule1, 'f', noun1, IsNot, noun2, msg);
				if (rs != 0) return rs;
			}
		}

		// Trigger if Chase's first name cannot start with 'S' then neither can Norris's first name.
		if (solver.getGridVerb(chase, sam) == IsNot && solver.getGridVerb(chase, steven) == IsNot && solver.getGridVerb(foote, stuart) == IsNot) {
			Noun noun1 = norris;
			for (int i = 8; i < 11; i++) {
				Noun noun2 = firstNames.nouns.get(i);
				String msg = "Since " + chase.name + "'s first name cannot start with 'M', " + noun1.name + "'s first name is not " + noun2.name + ".";
				rs = solver.addMarkByRule(mark, rule1, 'g', noun1, IsNot, noun2, msg);
				if (rs != 0) return rs;
			}
		}

		// Trigger if Norris's first name cannot start with 'S' then neither can Chase's first name.
		if (solver.getGridVerb(norris, sam) == IsNot && solver.getGridVerb(norris, steven) == IsNot && solver.getGridVerb(chase, stuart) == IsNot) {
			Noun noun1 = chase;
			for (int i = 8; i < 11; i++) {
				Noun noun2 = firstNames.nouns.get(i);
				String msg = "Since " + norris.name + "'s first name cannot start with 'M', " + noun1.name + "'s first name is not " + noun2.name + ".";
				rs = solver.addMarkByRule(mark, rule1, 'h', noun1, IsNot, noun2, msg);
				if (rs != 0) return rs;
			}
		}
		return rs;
	};

	// The man who won the Aerostar has a first and last name that start with the same letter (clue 21).
	// Violation if first and last names of Aerostar winner do not start with the same letter.
	rule2.f = (mark) -> {
		int rs = 0;
		Noun nounA = Mark.getPairNoun(aerostar, firstNames); // Get first name of Aerostar winner
		Noun nounB = Mark.getPairNoun(aerostar, lastNames);  // Get last  name of Aerostar winner

		if (nounA != null && nounB != null) {
			char c1 = nounA.name.charAt(0);
			char c2 = nounB.name.charAt(0);
			if (c1 != c2) return -1;
		}
		return rs;
	};

	// The man who won the Grand Prix has a first and last name that start with the same letter (clue 21).
	rule3.f = (mark) -> {
		int rs = 0;
		Noun nounA = Mark.getPairNoun(grandPrix, firstNames); // Get first name of Grand Prix winner
		Noun nounB = Mark.getPairNoun(grandPrix, lastNames);  // Get last  name of Grand Prix winner

		// Violation if first and last names of Grand Prix winner do not start with the same letter.
		if (nounA != null && nounB != null) {
			char c1 = nounA.name.charAt(0);
			char c2 = nounB.name.charAt(0);
			if (c1 != c2) return -1;
		}
		return rs;
	};

	// Enforces all clues regarding age.
	rule4.f = (mark) -> {
		int rs = 0;
		Rule rule = rule4;
		boolean checkAgesFlag = false;

		// Sets the age of the November winner to 20 when all of the marks have been entered.
		if (mark.num == solver.getMaxMarks()) {
			mark.addPlacer(ageT11, 20);
		}

		char c1, c2;
		int age1, age2;
		String txt1, txt2;
		Noun nounA, nounB;
		int maxNouns = ages.nouns.size();

		do {
			checkAgesFlag = false;

			// Clue 1. No two winners are the same age.
			// Violation if two ages are the same.
			for (int i1 = 0; i1 < maxNouns - 1; i1++) {
				txt1 = ages.nouns.get(i1).name;
				if (txt1.charAt(0) == 'A') continue;
				age1 = Helper.toInt(txt1);
				for (int i2 = i1 + 1; i2 < maxNouns; i2++) {
					txt2 = ages.nouns.get(i2).name;
					if (txt2.charAt(0) == 'A') continue;
					age2 = Helper.toInt(txt2);
					if (age1 == age2) return -1;
				}
			}

			// Clue 3. Carmen Vasquez is twice as old as the woman from the Lomita lot, who won the month before Paulette did.
			// Note: Carmen is 46 years old, so the woman from Lomita is 23.

			// Carmen is twice as old as the woman from Lomita.
			nounA = Mark.getPairNoun(carmen, ages); // Get age of Carmen
			nounB = Mark.getPairNoun(lomita, ages); // Get age of Lomita woman

			// Violation if Carmen's age is not even.
			if (Helper.isNotDivisibleBy(nounA, 2)) return -1;

			// Violation if Carmen is not twice as old as the woman from Lomita.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are numbers, must have age1 = 2*age2.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (age1 != 2 * age2) return -1;
				}
				// If Carmen's age is a number, then set age2 = age1/2.
				// MEB 2016-04-27 corrected c2 != 'A'.
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = age1 / 2;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If Lomita woman's age is a number, then set age1 = 2*age2.
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = 2 * age2;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}

			// Clue 4. The man who won the Caprise is half as old as the woman from Maywood, who is in turn two years older than the woman from La Puente.

			// The man who won the Caprice is half as old as the woman from Maywood.
			nounA = Mark.getPairNoun(caprice, ages); // Get age of Caprice winner
			nounB = Mark.getPairNoun(maywood, ages); // Get age of Maywood woman

			// Violation if Maywood woman's age is not even.
			if (Helper.isNotDivisibleBy(nounB, 2)) return -1;

			// Violation if Caprice winner is not half as old as Maywood winner.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are numbers, must have 2*age1 = age2.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (2 * age1 != age2) return -1;
				}
				// If Caprice winner's age is a number, then set age2 = 2*age1.
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = 2 * age1;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If Maywood winner's age is a number, then set age1 = age2/2
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = age2 / 2;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}

			// The woman from Maywood is two years older than the woman from La Puente.
			nounA = Mark.getPairNoun(maywood, ages);  // Get age of Maywood woman
			nounB = Mark.getPairNoun(laPuente, ages); // Get age of La Puente woman

			// Violation if Maywood winner is not two years older than La Pente winner.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are numbers, must have age1 = age2 + 2.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (age1 != age2 + 2) return -1;
				}
				// If La Puente winner's age is a number, then set age2 = age1 - 2.
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = age1 - 2;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If Maywood winner's age is a number, then set age1 = age2 + 2
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = age2 + 2;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}


			// Clue 5. The February winner, who works in the Downey lot, is twice as old as Prince and three times as old as Stuart (who won the Accord).

			// The February winner is twice as old as Prince.
			nounA = Mark.getPairNoun(february, ages); // Get age of February winner
			nounB = Mark.getPairNoun(prince, ages);   // Get age of Prince

			// Violation if the February winner's age is not even.
			if (Helper.isNotDivisibleBy(nounA, 2)) return -1;

			// Violation if February winner is not twice as old as Prince.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are numbers, must have age1 = 2*age2.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (age1 != 2 * age2) return -1;
				}
				// If February winner's age is a number, then set age2 = age1/2.
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = age1 / 2;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If Prince's age is a number, then set age1 = 2*age2.
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = 2 * age2;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}

			// The February winner is three times as old as Stuart.
			nounA = Mark.getPairNoun(february, ages); // Get age of February winner
			nounB = Mark.getPairNoun(stuart, ages);   // Get age of Stuart

			// Violation if the February winner's age is not divisible by three. This means the February winner is not 20.
			if (Helper.isNotDivisibleBy(nounA, 3)) return -1;

			// Violation if February winner is not three times as old as Stuart.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are a number, must have age1 = 3*age2.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (age1 != 3 * age2) return -1;
				}
				// If February winner's age is a number, then set age2 = age1/3.
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = age1 / 3;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If Stuart's age is a number, then set age1 = 3*age2.
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = 3 * age2;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}


			// Clue 6. The man from Torrance is not 20 years old.
			nounA = Mark.getPairNoun(torrance, ages);
			if (nounA != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				if (c1 != 'A') {
					age1 = Helper.toInt(txt1);
					if (age1 == 20) return -1;
				}
			}

			// Clue 7) The Hollywood winner is older than the Whittier winner.
			nounA = Mark.getPairNoun(hollywood, ages); // Get age of Hollywood winner
			nounB = Mark.getPairNoun(whittier, ages);  // Get age of Whittier winner

			// Violation if the Hollywood winner is not older than the Whittier winner.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (age1 <= age2) return -1;
				}
			}

			// Clue 8. The 50-year-old won the Previa.
			nounA = Mark.getPairNoun(previa, ages);
			if (nounA != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				if (c1 != 'A') {
					age1 = Helper.toInt(txt1);
					if (age1 != 50) return -1;
				}
				else {
					mark.addPlacer(nounA, 50);
					checkAgesFlag = true;
				}
			}

			// Clue 12. The winner of the Skylark (which was awarded before the Sentra was) is twice as old as the man who won in November.

			// The winner of the Skylark is twice as old as the man who won in November.
			nounA = Mark.getPairNoun(skylark, ages);  // Get age of Skylark winner
			nounB = Mark.getPairNoun(november, ages); // Get age of man who won in November

			// Violation if the Skylark winner's age is not even.
			if (Helper.isNotDivisibleBy(nounA, 2)) return -1;

			// If November winner's age is known, then the Skylark's age is twice that.
			// See if there is an existing age that can be paired with the Skylark.
			// Also, eliminate known ages that are not correct.
			if (nounA == null && nounB != null && nounB.name.charAt(0) != 'A') {
				String msg = "The winner of the Skylark is twice as old as the November winner.";
				age2 = Helper.toInt(nounB.name);
				for (Noun noun : ages.nouns) {
					if (noun.name.charAt(0) == 'A') continue;
					age1 = Helper.toInt(noun.name);
					if (age1 != 2 * age2)
						rs = solver.addMarkByRule(mark, rule4, ' ', noun, IsNot, skylark, msg);
					else
						rs = solver.addMarkByRule(mark, rule4, ' ', noun, Is, skylark, msg);
					if (rs != 0) return rs;
				}
			}

			// 2016-07-12 The age for the Skylark winner must be even.
			if (nounA == null) {
				String msg = "The age for the Skylark winner must be even.";
				for (Noun noun : ages.nouns) {
					if (noun.name.charAt(0) == 'A') continue;
					int age = Helper.toInt(noun.name);
					if (age % 2 != 0) {
						rs = solver.addMarkByRule(mark, rule4, ' ', skylark, IsNot, noun, msg);
						if (rs != 0) return rs;
					}
				}
			}

			// Violation if the Skylark winner is not twice as old as the November winner.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are numbers, must have age1 = 2*age2.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (age1 != 2 * age2) return -1;
				}
				// If Skylark winner's age is a number, then set age2 = age1/2.
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = age1 / 2;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If November winner's age is a number, then set age1 = 2*age2.
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = 2 * age2;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}


			// Clue 15. Mr. Jackson is ten years younger than Paulette, who is half as old as Ms. Harper.

			// Mr. Jackson is ten years younger than Paulette.
			nounA = Mark.getPairNoun(jackson, ages);  // Get age of Mr. Jackson
			nounB = Mark.getPairNoun(paulette, ages); // Get age of Paulette

			// Violation if Mr. Jackson is not ten years younger than Paulette.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are numbers, must have age1 = age2 - 10.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (age1 != age2 - 10) return -1;
				}
				// If Mr. Jackson's age is a number, then set age2 = age1 + 10.
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = age1 + 10;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If Paulette's age is a number, then set age1 = age2 - 10.
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = age2 - 10;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}

			// Paulette is half as old as Ms. Harper.
			nounA = Mark.getPairNoun(paulette, ages); // Get age of Paulette
			nounB = Mark.getPairNoun(harper, ages);   // Get age of Ms. Harper

			// Violation if Ms. Harper's age is not even.
			if (Helper.isNotDivisibleBy(nounB, 2)) return -1;

			// Violation if Paulette is not half as old as Ms. Harper.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are numbers, must have 2*age1 = age2.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (2 * age1 != age2) return -1;
				}
				// If Paulette's age is a number, then set age2 = 2*age1
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = 2 * age1;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If Ms. Harper's age is a number, then set age1 = age2/2.
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = age2 / 2;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}

			// Clue 16. Sanders (who is twice as old as Straw) won a car one month after Steven did and one month before the Compton winner did.
			// Note: Straw is 26 years old, so Sanders is 52.
			nounA = Mark.getPairNoun(sanders, ages); // Get age of Sanders
			nounB = Mark.getPairNoun(straw, ages);   // Get age of Straw

			// Violation if Sander's age is not even.
			if (Helper.isNotDivisibleBy(nounA, 2)) return -1;

			// Violation if Sanders is not twice as old as Straw.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are numbers, must have age1 = 2*age2.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (age1 != 2 * age2) return -1;
				}
				// If Sander's age is a number, then set age2 = age1/2.
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = age1 / 2;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If Straw's age is a number, then set age1 = 2*age2.
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = 2 * age2;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}

			// Clue 17. The Inglewood salesperson won the month before the 46-year-old.
			nounA = Mark.getPairNoun(inglewood, months);
			if (nounA != null && nounA.num < maxNouns) {
				nounB = ages.nouns.get(nounA.num);
				txt2 = nounB.name; c2 = txt2.charAt(0);
				if (c2 != 'A') {
					age2 = Helper.toInt(txt2);
					if (age2 != 46) return -1;
				}
				else {
					mark.addPlacer(nounB, 46);
					checkAgesFlag = true;
				}
			}

			// Clue 18. The woman from Bell won the month before the 26-year-old.
			nounA = Mark.getPairNoun(bell, months);
			if (nounA != null && nounA.num < maxNouns) {
				nounB = ages.nouns.get(nounA.num);
				txt2 = nounB.name; c2 = txt2.charAt(0);
				if (c2 != 'A') {
					age2 = Helper.toInt(txt2);
					if (age2 != 26) return -1;
				}
				else {
					mark.addPlacer(nounB, 26);
					checkAgesFlag = true;
				}
			}

			// Clue 20. Millie (who isn't Chase) is half as old as the woman from Gardena.
			nounA = Mark.getPairNoun(millie, ages);  // Get age of Millie
			nounB = Mark.getPairNoun(gardena, ages); // Get age of woman in Gardena

			// Violation if the Gardena woman's age is not even.
			if (Helper.isNotDivisibleBy(nounB, 2)) return -1;

			// If Millie's age is known, see if an existing age can be paired with the woman from Gardena.
			if (nounA != null && nounA.name.charAt(0) != 'A' && nounB == null) {
				String msg = "The woman from Gardena is twice as old as Millie.";
				age1 = Helper.toInt(nounA.name);
				for (Noun noun : ages.nouns) {
					if (noun.name.charAt(0) == 'A') continue;
					age2 = Helper.toInt(noun.name);
					if (age2 != 2 * age1)
						rs = solver.addMarkByRule(mark, rule4, ' ', noun, IsNot, gardena, msg);
					else
						rs = solver.addMarkByRule(mark, rule4, ' ', noun, Is, gardena, msg);
					if (rs != 0) return rs;
					checkAgesFlag = true;
				}
			}

			// Violation if Millie is not half as old as the Gardena winner.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are numbers, must have 2*age1 = age2.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (2 * age1 != age2) return -1;
				}
				// If Millie's age is a number, then set age2 = 2*age1.
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = 2 * age1;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If Gardena winner's age is a number, then set age1 = age2/2.
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = age2 / 2;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}

			// Clue 22. Bruce is half as old as Ms. Swan.
			nounA = Mark.getPairNoun(bruce, ages);  // Get age of Bruce
			nounB = Mark.getPairNoun(swan, ages);   // Get age of Ms. Swan

			// Violation if Ms. Swan's age is not even.
			if (Helper.isNotDivisibleBy(nounB, 2)) return -1;

			// Violation if Bruce is not half as old as Ms. Swan.
			if (nounA != null && nounB != null) {
				txt1 = nounA.name; c1 = txt1.charAt(0);
				txt2 = nounB.name; c2 = txt2.charAt(0);

				// If both ages are numbers, must have 2*age1 = age2.
				if (c1 != 'A' && c2 != 'A') {
					age1 = Helper.toInt(txt1);
					age2 = Helper.toInt(txt2);
					if (2 * age1 != age2) return -1;
				}
				// If Bruce's age is a number, then set age2 = 2*age1.
				else if (c1 != 'A' && c2 == 'A') {
					age1 = Helper.toInt(txt1);
					age2 = 2 * age1;
					mark.addPlacer(nounB, age2);
					checkAgesFlag = true;
				}
				// If Ms. Swan's age is a number, then set age1 = age2/2.
				else if (c1 == 'A' && c2 != 'A') {
					age2 = Helper.toInt(txt2);
					age1 = age2 / 2;
					mark.addPlacer(nounA, age1);
					checkAgesFlag = true;
				}
			}

		} while (checkAgesFlag);

		return rs;
	};

	// Solution. Needs 9/34 assumptions.
	answer = new int[][]{{3, 5, 11, 1, 2, 6, 7, 9, 8, 0, 10, 4}, {2, 3, 0, 1, 11, 10, 6, 8, 7, 5, 4, 9}, {4, 5, 1, 6, 2, 7, 8, 3, 9, 10, 0, 11}, {2, 3, 7, 11, 9, 6, 4, 1, 5, 10, 0, 8}, {5, 3, 9, 6, 4, 8, 0, 10, 1, 2, 11, 7}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Month, 2=First Name, 3=Last Name, 4=Age, 5=Car, 6=City
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = "The " + noun1.name + " winner " + verb.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = "The last name of the " + noun1.name + " winner " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + " winner " + verb.name + " " + noun2.name + " years old";
						break;
					case 5:
						if (link == With)
							msg = "The car won in " + noun1.name + " " + verb.name + " the " + noun2.name;
						break;
					case 6:
						if (link == With)
							msg = "The " + noun1.name + " winner " + verb.name + " from " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						else
							msg = noun1.name + " " + (verb == Is ? "won" : "did not win") + " " + link.name + " " + noun2.name;
						break;
					case 4: break;
					case 5:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "won" : "did not win") + " the " + noun2.name;
						else
							msg = noun1.name + " " + (verb == Is ? "won" : "did not win") + " " + link.name + " someone won the " + noun2.name;
						break;
					case 6:
						if (link == With)
							msg = noun1.name + " " + verb.name + " from " + noun2.name;
						else
							msg = noun1.name + " " + (verb == Is ? "won" : "did not win") + " " + link.name + " the " + noun2.name + " salesperson";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = noun1.name + "'s first name " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4: break;
					case 5:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "won" : "did not win") + " the " + noun2.name;
						else
							msg = noun1.name + " " + (verb == Is ? "won" : "did not win") + " " + link.name + " the " + noun2.name + " winner";
						break;
					case 6:
						if (link != With)
							msg = noun1.name + " " + (verb == Is ? "won" : "did not win") + " " + link.name + " the " + noun2.name + " winner";
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
					case 6: break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = "The " + noun1.name + " winner " + verb.name + " " + noun2.name;
						else
							msg = "The " + noun1.name + " winner " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = "The " + noun1.name + " winner " + verb.name + " " + noun2.name;
						if (link != With)
							msg = "The " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 4: break;
					case 5:
						msg = "The " + noun1.name + " " + verb.name + " awarded " + link.name + " the " + noun2.name;
						break;
					case 6: break;
				}
				break;
			case 6:
				msg = "The " + noun1.name + " salesperson ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += verb.name + " the " + noun2.name + " winner";
						break;
					case 2:
						if (link == With)
							msg += verb.name + " " + noun2.name;
						else
							msg += (verb == Is ? "won" : "did not win") + " " + link.name + " " + noun2.name;
						break;
					case 3: break;
					case 4: break;
					case 5:
						if (link == With)
							msg += verb.name + " the " + noun2.name + " winner";
						break;
					case 6: break;
				}
				break;
		}

		return msg + ".";
	}
}
