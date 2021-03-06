package com.fii.or.simplex;

import com.fii.or.simplex.exceptions.UnfeasibleSolutionException;
import com.fii.or.simplex.model.LinearProgramInputTable;
import com.fii.or.simplex.model.LinearProgramStandardFormTable;
import com.fii.or.simplex.solvers.SimplexSolver;
import com.fii.or.simplex.transformers.SimplexStandardFormTransformer;
import com.fii.or.simplex.solvers.SimplexTwoRuleSolver;
import com.fii.or.simplex.utils.SimplexTableReader;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class SimplexMain {
    public static void main(String[] args) {
//        System.out.println("Seminar example");
//        new SimplexMain().solveLinearProgram("/Users/andrluc/Documents/facultate/or/Simplex/src/main/java/com/fii/or/simplex/data/twophase//seminar_example.txt");
//        System.out.println("--------------");
//
//        System.out.println("First example");
//        new SimplexMain().solveLinearProgram("/Users/andrluc/Documents/facultate/or/Simplex/src/main/java/com/fii/or/simplex/data/twophase//ex1.txt");
//        System.out.println("--------------");
//
//        System.out.println("Second example");
//        new SimplexMain().solveLinearProgram("/Users/andrluc/Documents/facultate/or/Simplex/src/main/java/com/fii/or/simplex/data/twophase//ex2.txt");
//        System.out.println("--------------");
//
//        System.out.println("Third example");
//        new SimplexMain().solveLinearProgram("/Users/andrluc/Documents/facultate/or/Simplex/src/main/java/com/fii/or/simplex/data/twophase//ex3.txt");
//        System.out.println("--------------");
//
//        System.out.println("Fourth example");
//        new SimplexMain().solveLinearProgram("/Users/andrluc/Documents/facultate/or/Simplex/src/main/java/com/fii/or/simplex/data/twophase//ex4.txt");
        new SimplexMain().solveLinearProgram("/Users/andrluc/Documents/facultate/or/Simplex/src/main/java/com/fii/or/simplex/data/integer//ex3debug.txt");

        System.out.println("--------------"); }

    void solveLinearProgram(String filePath) {
        /* Read the table as as text. */
        LinearProgramInputTable linearProgramInputTable = SimplexTableReader.readTable(filePath);

        /* Convert the problem to the standard form. */
        LinearProgramStandardFormTable linearProgramStandardFormTable =
                SimplexStandardFormTransformer.transformToStandardForm(linearProgramInputTable);

        /* Check if it has an initial solution based on slack variables. */
        List<List<Double>> simplexTable;
        if (SimplexTwoRuleSolver.needsTransformation(linearProgramStandardFormTable)) {
            List<Double> solution = null;
            try {
                solution = SimplexTwoRuleSolver.solve(linearProgramStandardFormTable);
            } catch (UnfeasibleSolutionException e) {
                System.out.println("Unfeasible solution");
            }
            System.out.println(solution);
        } else {
            simplexTable = Lists.newArrayList(
                    linearProgramStandardFormTable.getRestrictions()
                            .stream()
                            .map(Lists::newArrayList)
                            .collect(Collectors.toList()
                            )
            );

            simplexTable.add(linearProgramStandardFormTable.getObjectiveFunction());

            System.out.println(simplexTable);

            new SimplexSolver().applySimplex(simplexTable);
        }
    }
}
