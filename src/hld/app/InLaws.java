package hld.app;

import org.gedcom4j.comparators.IndividualByLastNameFirstNameComparator;
import org.gedcom4j.exception.GedcomParserException;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.PersonalName;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.query.Finder;
import org.gedcom4j.relationship.RelationshipCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InLaws {
    private static final String DUDASH_PURE = "H:\\IdeaProjects\\hld\\resources\\20180402_DudashPure.ged";
    private static final String DUDASH_CORE = "H:\\IdeaProjects\\hld\\resources\\20180402_DudashCore.ged";

    /**
     * Main method
     *
     * @param args
     *            command line arguments - ignored
     * @throws IOException
     *             if there is a problem reading the GEDCOM data
     * @throws GedcomParserException
     *             if the GEDCOM file cannot be parsed
     */
    public static void main(String[] args) throws IOException,
            GedcomParserException {
        // Make a relationship calculator instance
        RelationshipCalculator rc = new RelationshipCalculator();

        // Load the GEDCOM
        GedcomParser gp = new GedcomParser();
        gp.load(DUDASH_PURE);
        Gedcom g = gp.getGedcom();

        // Find the starting person
        Finder f = new Finder(g);
        List<Individual> results = f.findByName("Dudash",
                "Heather Lynne");
        Individual startingPerson = results.get(0);

        // Building list of people related to person of interest.
        // Start with all the person's ancestors
        Set<Individual> ancestorList = startingPerson.getAncestors();
        System.out.println("ancestor size=" + ancestorList.size());
        checkPrefix(false, ancestorList);

        Set<Individual> descendantList = new HashSet<>();
        for (Individual individual : ancestorList) {
            descendantList.addAll(individual.getDescendants());
        }
        System.out.println("descendant size=" + descendantList.size());
        checkPrefix(false, descendantList);

        // Get a list of everyone and sort them last name first
        List<Individual> everybody = new ArrayList<>(g.getIndividuals().values());
        everybody.sort(new IndividualByLastNameFirstNameComparator());

        // Now go through the list of everybody, and write out their names if
        // they are not in the list of relatives of the person of interest
        Set<Individual> inlawList = new HashSet<>();
        for (Individual i : everybody) {
            if (!ancestorList.contains(i) && !descendantList.contains(i)) {
                inlawList.add(i);
            }
        }
        System.out.println(inlawList.size() + " in-law(s)");
        checkPrefix(true, inlawList);
    }

    private static void checkPrefix(boolean withSTOP, Set<Individual> peopleList) {
        int x = 0;
        for (Individual i : peopleList) {
            if (i.getNames() != null && !i.getNames().isEmpty()) {
                for (PersonalName pn : i.getNames()) {
                    if ((withSTOP && !pn.isInLaw())
                            || (!withSTOP && pn.isInLaw())) {
                        System.out.println(pn);
                        x++;
                    }
                }
            }
        }
        if (x > 0) {
            System.out.println(x + " people " + (withSTOP ? "without" : "with") + " 'STOP' prefix");
        }
    }
}
