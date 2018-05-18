package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class AstrophysicsConference extends Puzzle {
	AstrophysicsConference() {
		// Properties.
		myName = "AstrophysicsConference";
		myTitle = "Astrophysics Conference";

		// Nouns.
		NounType talks = addNounType("Talk");
		Noun talk1 = talks.addNoun("1st");
		Noun talk2 = talks.addNoun("2nd");
		Noun talk3 = talks.addNoun("3rd");
		Noun talk4 = talks.addNoun("4th");
		Noun talk5 = talks.addNoun("5th");

		NounType firstNames = addNounType("First Name");
		Noun tom = firstNames.addNoun("Tom");
		Noun laura = firstNames.addNoun("Laura");
		Noun ed = firstNames.addNoun("Ed");
		Noun christa = firstNames.addNoun("Christa");
		Noun samuel = firstNames.addNoun("Samuel");

		NounType lastNames = addNounType("Last Name");
		Noun jones = lastNames.addNoun("Jones");
		Noun gray = lastNames.addNoun("Gray");
		Noun howe = lastNames.addNoun("Howe");
		Noun flynn = lastNames.addNoun("Flynn");
		Noun ives = lastNames.addNoun("Ives");

		NounType subjects = addNounType("Subject");
		Noun bigBang = subjects.addNoun("the big bang theory", "Big Bang");
		Noun pulsars = subjects.addNoun("pulsars");
		Noun holes = subjects.addNoun("black holes");
		Noun stellar = subjects.addNoun("stellar evolution", "Stellar");
		Noun quasars = subjects.addNoun("quasars");

		NounType attendances = addNounType("Attendance");
		Noun attendance1 = attendances.addNoun("A1");
		Noun attendance2 = attendances.addNoun("A2");
		Noun attendance3 = attendances.addNoun("A3");
		Noun attendance4 = attendances.addNoun("A4");
		Noun attendance5 = attendances.addNoun("A5");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Facts.
		List<Noun> guys = getList(tom, ed, samuel);
		List<Noun> gals = getList(laura, christa);

		// Intro. Each attendance is assigned to a talk, where talk1 has A1, talk2 has A2, etc.
		addFactsOneToOne("0", talks, Is, With, attendances);

		// Intro. One speaker was Mr. Jones.
		addFact("0", jones, IsNot, With, gals);

		// Clue 1. The first talk of the day was on the big bang theory.
		addFact("1", talk1, Is, With, bigBang);

		// Clue 2. Laura drew 24 listeners, more than any other speaker.

		// Clue 3. No two men spoke consecutively. They gave talks 1, 3, 5, in some order.
		addFact("3", guys, IsNot, With, getList(talk2, talk4));

		// Clue 4. As many people attended the talk on pulsars as attended both the third speech and Gray's talk combined.
		addFact("4", getList(pulsars, talk3, gray), IsNot, With);

		// Clue 5. Howe's talk was attended by twice as many people as Ed's.
		addFact("5", ed, IsNot, With, howe);

		// Clue 6. The last two speakers drew (not necessarily respectively) the largest and smallest numbers of attendees.
		// From clue 2, Laura gave either the 4th or 5th talk.
		addFact("2,6", laura, IsNot, With, getList(talk1, talk2, talk3));

		// Clue 7. The talk about black holes drew half as many listeners as the second speech.
		addFact("7", talk2, IsNot, With, holes);

		// Clue 8. The talk on stellar evolution (which wasn't the one given by Christa Flynn) didn't draw the fewest attendees of the day.
		addFact("8", christa, Is, With, flynn);
		addFact("8", christa, IsNot, With, stellar);

		// Clue 9. The number of people who attended Samuel's talk was as much less than the number who attended the talk on quasars as it was more than the number who attended Ives's talk.
		addFact("9", getList(samuel, quasars, ives), IsNot, With);

		// Samuel's talk was not the largest or smallest.
		addFact("9", samuel, IsNot, With, getList(talk4, talk5));

		// Rules.
		// Clue 10. A different number of people attended each talk.
		Rule rule1 = addRule("10", "All clues concerning attendance for last mark.");
		Rule rule2 = addRule("10", "All clues concerning attendance before last mark.");
		Rule rule3 = addRule("5,7,10", "If Howe has the largest talk, then Ed did not talk on black holes.", getList(howe, attendance5, ed, holes));
		Rule rule4 = addRule("4,7,10", "If Christa gave the 2nd talk, then it was not on pulsars.", getList(christa, talk2, pulsars));
		Rule rule5 = addRule("4,5,10", "If talk on pulsars was highest, then Ed is not 3rd or Gray.", getList(pulsars, attendance5, ed, talk3, gray));

		// A different number of people attended each talk (clues 2, 4, 5, 7, 9, 10).
		// Note: The equations in this puzzle MUST be solved using a matrix!
		// Violation if X[i] = X[j] or smallest/largest values are exceeded using matrix where Ax=b, solve for X = Inverse(A)b.
		rule1.f = (mark) -> {
			int rs = 0;
			Rule rule = rule1;

			// See if the mark is the last one.
			if (mark.num < solver.getMaxMarks()) return rs;
			//print("AstrophysicsConference rule 1 mark.num=" + mark.num + " solver.numMarks=" + solver.numMarks + " puzzle.maxMarks=" + puzzle.maxMarks);

			// Initialize augmented array.
			int n = 5;
			double[][] a = new double[n][n + 1];

			// Row 1. Laura drew 24 listeners (clue 2).
			int i0 = Mark.getPairNounNum(laura, attendances);
			if (i0 > 0) a[0][i0 - 1] = 1;
			a[0][5] = 24;

			// Row 2. Talk on Pulsars - 3rd Talk - Gray's Talk = 0 (clue 4).
			int i1 = Mark.getPairNounNum(pulsars, attendances);
			int i2 = Mark.getPairNounNum(talk3, attendances);
			int i3 = Mark.getPairNounNum(gray, attendances);
			if (i1 > 0) a[1][i1 - 1] = 1;
			if (i2 > 0) a[1][i2 - 1] = -1;
			if (i3 > 0) a[1][i3 - 1] = -1;

			// Row 3. Howe's talk - 2*(Ed's Talk) = 0 (clue 5).
			i1 = Mark.getPairNounNum(howe, attendances);
			i2 = Mark.getPairNounNum(ed, attendances);
			if (i1 > 0) a[2][i1 - 1] = 1;
			if (i2 > 0) a[2][i2 - 1] = -2;

			// Row 4. 2*(Talk on Black holes) - 2nd Talk = 0 (clue 7).
			i1 = Mark.getPairNounNum(holes, attendances);
			i2 = Mark.getPairNounNum(talk2, attendances);
			if (i1 > 0) a[3][i1 - 1] = 2;
			if (i2 > 0) a[3][i2 - 1] = -1;

			// Row 5. Quasars - 2*(Samuel's Talk) + Ive's Talk = 0 (clue 9).
			i1 = Mark.getPairNounNum(quasars, attendances);
			i2 = Mark.getPairNounNum(samuel, attendances);
			i3 = Mark.getPairNounNum(ives, attendances);
			if (i1 > 0) a[4][i1 - 1] = 1;
			if (i2 > 0) a[4][i2 - 1] = -2;
			if (i3 > 0) a[4][i3 - 1] = 1;

			// Solve equations using augmented matrix A and return solution in array X.
			double[] X = Helper.solveEquations(a);
			//print("AstrophysicsConference rule 1 X=" + X);

			// Update attendances except for the last one (24). All attendance numbers must be integers.
			for (int i = 0; i < n; i++) {
				//print("X[" + i + "]=" + X[i]);
				if (X[i] < 0) {
					rs = -1;
				}
				else {
					int num = (int) Math.round(X[i]);
					Noun noun = attendances.nouns.get(i);
					if (noun.name.charAt(0) == 'A') {
						mark.addPlacer(noun, num);
					}
				}
			}
			if (rs != 0) return rs;

			// Ensure talk with smallest attendance really is smallest.
			// Ensure talk with largest  attendance really is largest.
			int j0 = 9 - i0;
			//print("i0=" + i0 + " j0=" + j0);
			for (int i = 0; i < n; i++) {
				if (X[i] < X[j0 - 1]) return -1; // check smallest attendance
				if (X[i] > X[i0 - 1]) return -1; // check largest  attendance
			}

			// Ensure all given attendances are different.
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i != j && X[i] > -1 && X[j] > -1 && X[i] == X[j]) return -1;
				}
			}

			return rs;
		};

		rule2.f = (mark) -> {
			int rs = 0;
			Rule rule = rule2;

			// Ignore if all of the marks have been entered.
			if (mark.num == solver.getMaxMarks()) return rs;

			Noun noun1, noun2, noun3;
			String txt1, txt2, txt3;
			char c1, c2, c3;
			int num1, num2, num3;
			boolean loopFlag;

			// Largest and smallest attendances, or null.
			Noun lauraAttendance = null;
			Noun smallAttendance = null;

			do {
				loopFlag = false;

				// Clue 6. The last two speakers drew (not necessarily respectively) the largest and smallest numbers of attendees.
				// If Laura is with A4 or A5, then the smallest attendance is the other one.
				lauraAttendance = Mark.getPairNoun(laura, attendances);
				if (lauraAttendance != null) {
					smallAttendance = attendances.nouns.get(9 - lauraAttendance.num - 1);
				}

				// Clue 2. Laura drew 24 listeners, more than any other speaker.
				noun1 = lauraAttendance;
				if (noun1 != null) {
					txt1 = noun1.name; c1 = txt1.charAt(0);
					if (c1 != 'A') {
						num1 = Integer.parseInt(txt1);
						if (num1 != 24) return -1;
					}
					else {
						mark.addPlacer(noun1, 24);
						loopFlag = true;
						//print("loop1 mark=" + mark.num + " c1=" + c1 + " txt1=" + txt1);
					}
				}

				// Clue 4. As many people attended the talk on pulsars as attended both the third speech and Gray's talk combined.
				// pulsars = talk3 + gray.
				noun1 = Mark.getPairNoun(talk3, attendances);
				noun2 = Mark.getPairNoun(pulsars, attendances);
				noun3 = Mark.getPairNoun(gray, attendances);

				// The talk on pulsars was not the smallest.
				if (smallAttendance != null) {
					if (noun2 != null) {
						if (noun2 == smallAttendance) return -1;
					}
					else {
						String msg = "The talk on pulsars was not the smallest.";
						rs = solver.addMarkByRule(mark, rule2, 'a', pulsars, IsNot, smallAttendance, msg);
						if (rs != 0) return rs;
					}
				}

				// Gray's talk was not the largest.
				if (lauraAttendance != null) {
					if (noun3 != null) {
						if (noun3 == lauraAttendance) return -1;
					}
					else {
						String msg = "Gray's talk was not the largest.";
						rs = solver.addMarkByRule(mark, rule2, 'b', gray, IsNot, lauraAttendance, msg);
						if (rs != 0) return rs;
					}
				}

				if (noun1 != null && noun2 != null && noun3 != null) {
					txt1 = noun1.name; c1 = txt1.charAt(0);
					txt2 = noun2.name; c2 = txt2.charAt(0);
					txt3 = noun3.name; c3 = txt3.charAt(0);

					// If all are numeric, verify num1 = num2 - num3.
					if (c1 != 'A' && c2 != 'A' && c3 != 'A') {
						num1 = Integer.parseInt(txt1);
						num2 = Integer.parseInt(txt2);
						num3 = Integer.parseInt(txt3);
						if (num1 != num2 - num3) return -1;
					}
					// If num1, num2 are numeric, set num3 = num2 - num1.
					else if (c1 != 'A' && c2 != 'A' && c3 == 'A') {
						num1 = Integer.parseInt(txt1);
						num2 = Integer.parseInt(txt2);
						num3 = num2 - num1;
						mark.addPlacer(noun3, num3);
						loopFlag = true;
					}
					// If num1, num3 are numeric, set num2 = num1 + num3.
					else if (c1 != 'A' && c2 == 'A' && c3 != 'A') {
						num1 = Integer.parseInt(txt1);
						num3 = Integer.parseInt(txt3);
						num2 = num1 + num3;
						mark.addPlacer(noun2, num2);
						loopFlag = true;
					}
					// If num2, num3 are numeric, set num1 = num2 - num3.
					else if (c1 == 'A' && c2 != 'A' && c3 != 'A') {
						num2 = Integer.parseInt(txt2);
						num3 = Integer.parseInt(txt3);
						num1 = num2 - num3;
						mark.addPlacer(noun1, num1);
						loopFlag = true;
					}
				}

				// Clue 5. Howe's talk was attended by twice as many people as Ed's.
				// howe = 2 * ed.
				noun1 = Mark.getPairNoun(howe, attendances);
				noun2 = Mark.getPairNoun(ed, attendances);

				// Howe's talk was not the smallest.
				if (smallAttendance != null) {
					if (noun1 != null) {
						if (noun1 == smallAttendance) return -1;
					}
					else {
						String msg = "Howe's talk was not the smallest.";
						rs = solver.addMarkByRule(mark, rule2, 'c', howe, IsNot, smallAttendance, msg);
						if (rs != 0) return rs;
					}
				}

				// Ed's talk was not the largest. Don't check since Laura has the largest attendance!

				if (noun1 != null && noun2 != null) {
					txt1 = noun1.name; c1 = txt1.charAt(0);
					txt2 = noun2.name; c2 = txt2.charAt(0);

					// If all are numeric, verify num1 = 2*num2.
					if (c1 != 'A' && c2 != 'A') {
						num1 = Integer.parseInt(txt1);
						num2 = Integer.parseInt(txt2);
						if (num1 != 2 * num2) return -1;
					}
					// If num1 is numeric, set num2 = num1/2.
					else if (c1 != 'A' && c2 == 'A') {
						num1 = Integer.parseInt(txt1);
						if (num1 % 2 != 0) return -1;
						num2 = num1 / 2;
						mark.addPlacer(noun2, num2);
						loopFlag = true;
					}
					// If num2 is numeric, set num1 = 2*num2.
					else if (c1 == 'A' && c2 != 'A') {
						num2 = Integer.parseInt(txt2);
						num1 = 2 * num2;
						mark.addPlacer(noun1, num1);
						loopFlag = true;
					}
				}

				// Clue 7. The talk about black holes drew half as many listeners as the second speech.
				// talk2 = 2 * holes
				noun1 = Mark.getPairNoun(talk2, attendances);
				noun2 = Mark.getPairNoun(holes, attendances);

				// The 2nd talk was not the smallest. Don't check since the 4th or 5th talk is the smallest!

				// The talk on black holes was not the largest.
				if (lauraAttendance != null) {
					if (noun2 != null) {
						if (noun2 == lauraAttendance) return -1;
					}
					else {
						String msg = "The talk on black holes was not the largest.";
						rs = solver.addMarkByRule(mark, rule2, 'd', holes, IsNot, lauraAttendance, msg);
						if (rs != 0) return rs;
					}
				}

				if (noun1 != null && noun2 != null) {
					txt1 = noun1.name; c1 = txt1.charAt(0);
					txt2 = noun2.name; c2 = txt2.charAt(0);

					// If all are numeric, verify num1 = 2*num2.
					if (c1 != 'A' && c2 != 'A') {
						num1 = Integer.parseInt(txt1);
						num2 = Integer.parseInt(txt2);
						if (num1 != 2 * num2) return -1;
					}
					// If num1 is numeric, set num2 = num1/2.
					else if (c1 != 'A' && c2 == 'A') {
						num1 = Integer.parseInt(txt1);
						if (num1 % 2 != 0) return -1;
						num2 = num1 / 2;
						mark.addPlacer(noun2, num2);
						loopFlag = true;
					}
					// If num2 is numeric, set num1 = 2*num2.
					else if (c1 == 'A' && c2 != 'A') {
						num2 = Integer.parseInt(txt2);
						num1 = 2 * num2;
						mark.addPlacer(noun1, num1);
						loopFlag = true;
					}
				}

				// Clue 8. The talk on stellar evolution (which wasn't the one given by Christa Flynn) didn't draw the fewest attendees of the day.
				// The talk on stellar evolution was not the smallest.
				noun1 = Mark.getPairNoun(stellar, attendances);
				if (smallAttendance != null) {
					if (noun1 != null) {
						if (noun1 == smallAttendance) return -1;
					}
					else {
						String msg = "The talk on stellar evolution was not the smallest.";
						rs = solver.addMarkByRule(mark, rule2, 'e', stellar, IsNot, smallAttendance, msg);
						if (rs != 0) return rs;
					}
				}

				// Clue 9. The number of people who attended Samuel's talk was as much less than the number who attended the talk on quasars
				// as it was more than the number who attended Ives's talk. quasars > samuel > ives.
				// sam = quasars - x and sam = ives + x, or 2 * samuel = quasars + ives
				noun1 = Mark.getPairNoun(samuel, attendances);
				noun2 = Mark.getPairNoun(quasars, attendances);
				noun3 = Mark.getPairNoun(ives, attendances);

				// Quasars was not the smallest (or 2nd smallest).
				if (smallAttendance != null) {
					if (noun2 != null) {
						if (noun2 == smallAttendance) return -1;
					}
					else {
						String msg = "The talk on stellar evolution was not the smallest.";
						rs = solver.addMarkByRule(mark, rule2, 'f', quasars, IsNot, smallAttendance, msg);
						if (rs != 0) return rs;
					}
				}

				// Ive's talk was not the largest.
				if (lauraAttendance != null) {
					if (noun3 != null) {
						if (noun3 == lauraAttendance) return -1;
					}
					else {
						String msg = "Ive's talk was not the largest.";
						rs = solver.addMarkByRule(mark, rule2, 'g', ives, IsNot, lauraAttendance, msg);
						if (rs != 0) return rs;
					}
				}

				if (noun1 != null && noun2 != null && noun3 != null) {
					txt1 = noun1.name; c1 = txt1.charAt(0);
					txt2 = noun2.name; c2 = txt2.charAt(0);
					txt3 = noun3.name; c3 = txt3.charAt(0);

					// If all are numeric, verify 2*num1 = num2 + num3.
					if (c1 != 'A' && c2 != 'A' && c3 != 'A') {
						num1 = Integer.parseInt(txt1);
						num2 = Integer.parseInt(txt2);
						num3 = Integer.parseInt(txt3);
						if (2 * num1 != num2 + num3) return -1;
					}
					// If num1, num2 are numeric, set num3 = 2*num1 - num2.
					else if (c1 != 'A' && c2 != 'A' && c3 == 'A') {
						num1 = Integer.parseInt(txt1);
						num2 = Integer.parseInt(txt2);
						num3 = 2 * num1 - num2;
						mark.addPlacer(noun3, num3);
						loopFlag = true;
					}
					// If num1, num3 are numeric, set num2 = 2*num1 - num3.
					else if (c1 != 'A' && c2 == 'A' && c3 != 'A') {
						num1 = Integer.parseInt(txt1);
						num3 = Integer.parseInt(txt3);
						num2 = 2 * num1 - num3;
						mark.addPlacer(noun2, num2);
						loopFlag = true;
					}
					// If num2, num3 are numeric, set num1 = (num2 + num3)/2.
					else if (c1 == 'A' && c2 != 'A' && c3 != 'A') {
						num2 = Integer.parseInt(txt2);
						num3 = Integer.parseInt(txt3);
						if ((num2 + num3) % 2 != 0) return -1;
						num1 = (num2 + num3) / 2;
						mark.addPlacer(noun1, num1);
						loopFlag = true;
					}
				}

			} while (loopFlag);

			return rs;
		};

		// If Howe has the largest talk, then Ed did not talk on black holes.
		rule3.f = (mark) -> {
			int rs = 0;

			if (mark.noun1 == laura && mark.verb == Is && mark.noun2 == howe) {
				String msg = "If Howe has the largest talk, then Ed did not talk on black holes.";
				rs = solver.addMarkByRule(mark, rule3, ' ', ed, IsNot, holes, msg);
			}
			return rs;
		};

		// If Christa gave the 2nd talk, then it was not on pulsars.
		rule4.f = (mark) -> {
			int rs = 0;

			if (mark.noun1 == talk2 && mark.verb == Is && mark.noun2 == christa) {
				String msg = "If Christa gave the 2nd talk, then it was not on pulsars.";
				rs = solver.addMarkByRule(mark, rule4, ' ', christa, IsNot, pulsars, msg);
			}
			return rs;
		};

		// If talk on pulsars was highest, then Ed is not 3rd or Gray.
		rule5.f = (mark) -> {
			int rs = 0;
			String msg;

			if (mark.noun1 == laura && mark.verb == Is && mark.noun2 == pulsars) {
				msg = "If talk on pulsars was highest, then Ed did not talk 3rd.";
				rs = solver.addMarkByRule(mark, rule5, 'a', ed, IsNot, talk3, msg);
				if (rs != 0) return rs;
				msg = "If talk on pulsars was highest, then Ed is not Gray.";
				rs = solver.addMarkByRule(mark, rule5, 'b', ed, IsNot, gray, msg);
			}
			return rs;
		};

		// Solution.
		answer = new int[][]{{2, 3, 4, 1, 0}, {4, 3, 0, 2, 1}, {0, 4, 3, 1, 2}, {0, 1, 2, 4, 3}};
	}

	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Talk, 2=First Name, 3=Last Name, 4=Subject, 5=Attendance
		switch (noun1.type.num) {
			case 1:
				msg = "The " + noun1.name + " talk ";
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg += verb.name + " given by " + noun2.name;
						break;
					case 4:
						msg += verb.name + " on " + noun2.name;
						break;
					case 5:
						msg += (verb == Is ? "had " : "did not have ") + noun2.name + " in attendance";
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						msg = noun1.name + " " + (verb == Is ? "gave" : "did not give") + " the " + noun2.name + " talk";
						break;
					case 2: break;
					case 3:
						msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 4:
						msg = noun1.name + " " + (verb == Is ? "gave" : "did not give") + " the talk on " + noun2.name;
						break;
					case 5: break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4: break;
					case 5: break;
				}
				break;
			case 4:
				msg = "The talk on " + noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						msg += verb.name + " " + noun2.name;
						break;
					case 2: break;
					case 3:
						msg += verb.name + " given by " + noun2.name;
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
