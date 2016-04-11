package mvm.rya.indexing.pcj.matching;

import mvm.rya.indexing.external.tupleSet.SimpleExternalTupleSet;

import org.junit.Assert;
import org.junit.Test;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.algebra.Projection;
import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.query.parser.ParsedQuery;
import org.openrdf.query.parser.sparql.SPARQLParser;

public class PCJOptimizerUtilitesTest {

	@Test
	public void testValidPCJ() throws MalformedQueryException {

		String query1 = ""//
				+ "SELECT ?e ?c " //
				+ "{" //
				+ "  ?e a ?c . "//
				+ "}";//

		String query2 = ""//
				+ "SELECT ?a ?b ?m" //
				+ "{" //
				+ "  ?a a ?b . "//
				+ "  OPTIONAL {?a <uri:talksTo> ?m}  . "//
				+ "}";//

		String query3 = ""//
				+ "SELECT ?a ?b ?m" //
				+ "{" //
				+ "  ?a a ?b . "//
				+ "  ?a <uri:talksTo> ?m  . "//
				+ "}";//

		String query4 = ""//
				+ "SELECT ?a ?b ?m ?n" //
				+ "{" //
				+ "  ?a a ?b . "//
				+ "  OPTIONAL {?a <uri:talksTo> ?m}  . "//
				+ "}";//

		String query5 = ""//
				+ "SELECT ?e ?c " //
				+ "{" //
				+ "Filter(?e = <uri:s1>) " //
				+ "  ?e a ?c . "//
				+ "}";//

		String query6 = ""//
				+ "SELECT ?e ?c " //
				+ "{" //
				+ "Filter(?f = <uri:s1>) " //
				+ "  ?e a ?c . "//
				+ "  ?c <uri:p1> <uri:o1> " //
				+ "}";//

		SPARQLParser parser = new SPARQLParser();
		ParsedQuery pq1 = parser.parseQuery(query1, null);
		ParsedQuery pq2 = parser.parseQuery(query2, null);
		ParsedQuery pq3 = parser.parseQuery(query3, null);
		ParsedQuery pq4 = parser.parseQuery(query4, null);
		ParsedQuery pq5 = parser.parseQuery(query5, null);
		ParsedQuery pq6 = parser.parseQuery(query6, null);
		TupleExpr te1 = pq1.getTupleExpr();
		TupleExpr te2 = pq2.getTupleExpr();
		TupleExpr te3 = pq3.getTupleExpr();
		TupleExpr te4 = pq4.getTupleExpr();
		TupleExpr te5 = pq5.getTupleExpr();
		TupleExpr te6 = pq6.getTupleExpr();

		SimpleExternalTupleSet pcj1 = new SimpleExternalTupleSet(
				(Projection) te1);
		SimpleExternalTupleSet pcj2 = new SimpleExternalTupleSet(
				(Projection) te2);
		SimpleExternalTupleSet pcj3 = new SimpleExternalTupleSet(
				(Projection) te3);
		SimpleExternalTupleSet pcj4 = new SimpleExternalTupleSet(
				(Projection) te4);
		SimpleExternalTupleSet pcj5 = new SimpleExternalTupleSet(
				(Projection) te5);
		SimpleExternalTupleSet pcj6 = new SimpleExternalTupleSet(
				(Projection) te6);

		Assert.assertEquals(false , PCJOptimizerUtilities.isPCJValid(pcj1));
		Assert.assertEquals(true , PCJOptimizerUtilities.isPCJValid(pcj2));
		Assert.assertEquals(true , PCJOptimizerUtilities.isPCJValid(pcj3));
		Assert.assertEquals(false , PCJOptimizerUtilities.isPCJValid(pcj4));
		Assert.assertEquals(true , PCJOptimizerUtilities.isPCJValid(pcj5));
		Assert.assertEquals(false , PCJOptimizerUtilities.isPCJValid(pcj6));

	}

}
