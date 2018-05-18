package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class StudentLogic extends Puzzle {
	StudentLogic() {
		// Properties.
		myName = "StudentLogic";
		myTitle = "Student Logic";

		// Nouns.
		NounType students = addNounType("Student");
		Noun jack = students.addNoun("Jack");
		Noun kate = students.addNoun("Kate");
		Noun mary = students.addNoun("Mary");
		Noun paul = students.addNoun("Paul");

		NounType contests = addNounType("Contest");
		Noun math    = contests.addNoun("math");
		Noun logic   = contests.addNoun("logic");
		Noun french  = contests.addNoun("French");
		Noun english = contests.addNoun("English");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Rules.
		char ch = ' ';

		// Four students competed in math, logic, French and English contests
		// Jack thinks Paul won the logic award
		// Kate thinks Mary won the English award
		// Mary thinks Jack did not win the math title
		// Paul thinks Kate won the French trophy

		// The winners of the math and logic contests were right
		// The winners of the French and English contests were wrong

		// If Jack won in math, then Paul won in logic. Jack cannot win in logic.
		// If Kate won in math or logic, then Mary won in English.
		// If Mary won in math or logic, then Jack did not win in math.
		// If Paul won in math or logic, then Kate won in French.
		Rule rule1 = addRule("", "The winners of the math logic contests were right.");
		rule1.f = (mark) -> {
			int rs = 0;
			Rule rule = rule1;
			Verb verb = mark.verb;
			Noun contest = mark.noun2;
			String msg = rule.name;

			if (contest != math && contest != logic) return rs;

			if (verb == Is) {
				if (mark.noun1 == jack && contest == logic) return -1;
				if (mark.noun1 == jack) rs = solver.addMarkByRule(mark, rule, ch, paul, verb, logic, msg);
				if (mark.noun1 == kate) rs = solver.addMarkByRule(mark, rule, ch, mary, verb, english, msg);
				if (mark.noun1 == mary) rs = solver.addMarkByRule(mark, rule, ch, jack, IsNot, math, msg);
				if (mark.noun1 == paul) rs = solver.addMarkByRule(mark, rule, ch, kate, verb, french, msg);
			}

			return rs;
		};

		// If Jack won in French or English, then Paul did not win in logic.
		// If Kate won in French or English, then Mary did not win in English.
		// If Mary won in French or English, then Jack won in math.
		// If Paul won in French or English, then Kate did not win in French.
		Rule rule2 = addRule("", "The winners of the French and English contests were wrong.");
		rule2.f = (mark) -> {
			int rs = 0;
			Rule rule = rule2;
			Verb verb = mark.verb;
			Noun contest = mark.noun2;
			String msg = rule.name;

			if (contest != french && contest != english) return rs;

			if (verb == Is) {
				if (mark.noun1 == jack) rs = solver.addMarkByRule(mark, rule, ch, paul, IsNot, logic, msg);
				if (mark.noun1 == kate) rs = solver.addMarkByRule(mark, rule, ch, mary, IsNot, english, msg);
				if (mark.noun1 == mary) rs = solver.addMarkByRule(mark, rule, ch, jack, verb, math, msg);
				if (mark.noun1 == paul) rs = solver.addMarkByRule(mark, rule, ch, kate, IsNot, french, msg);
			}

			return rs;
		};

		// Solution.
		answer = new int[][]{{3, 2, 1, 0}};
	}
}
