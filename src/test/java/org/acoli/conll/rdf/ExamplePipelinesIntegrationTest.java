package org.acoli.conll.rdf;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ExamplePipelinesIntegrationTest {
	static class AnalyzeUD {
		@TempDir
		static Path sharedTempDir;

		@BeforeAll
		static void cat() {
			// for file in `find $DATA/ud | grep 'dev.conllu.gz$'`; do gunzip -c $file; done
			// | \
			// egrep '^[0-9]+\s.*$|^$' | # remove multiword tokens
		}

		@Test
		@Order(1)
		void runConllStreamExtractor() {
			String url = "https://github.com/UniversalDependencies/UD_English#";
			String[] args = new String[] { url, "IGNORE", "WORD", "IGNORE", "UPOS", "IGNORE", "IGNORE", "HEAD", "EDGE",
					"IGNORE", "IGNORE" };
			// CoNLLStreamExtractor
		}

		@Test
		@Order(2)
		void runCoNLLRDFUpdater() {
			String[] args = new String[] { "-custom", "-updates", "sparql/remove-IGNORE.sparql",
					"sparql/analyze/UPOS-to-POSsynt.sparql", "sparql/analyze/EDGE-to-POSsynt.sparql",
					"sparql/analyze/consolidate-POSsynt.sparql" };
			// CoNLLRDFUpdater
		}

		@Test
		@Order(3)
		void runCoNLLRDFFormatter() {
			// | ../run.sh CoNLLRDFFormatter -sparqltsv sparql/analyze/eval-POSsynt.sparql \
		}

		// | grep -v '#';

		// TODO analyze-ud.json
	}

	static class ConvertUD {
		// # 1. read
		// for file in `find $DATA/ud | grep 'conllu.gz$'`; do \
		// 	echo $file; 
		// 	filename=$(basename "$file"); 
		// 	tmp=UD_${file#*UD_}; 
		// 	lang=${tmp%-master/*}; 
		// 	gunzip -c $file | \

		// # 2. parse UD data to RDF
		@Test
		void runCoNLLStreamExtractor() {
		// https://github.com/UniversalDependencies/$lang# \
		// 		ID WORD LEMMA UPOS POS FEAT HEAD EDGE DEPS MISC | \
		// 	\
		}
		// # 3. format
		@Test
		void runCoNLLRDFFormatter() {
		// -rdf ID WORD LEMMA UPOS POS FEAT HEAD EDGE DEPS MISC $* > $DATA/ud/UD_English-master/$filename.ttl \
		}
		// ; done

		// TODO convert-ud.json
	}

	static class EmbeddedXMLExample {
		/*
		TENTEN=$1;
		CONLL="$TENTEN".conll
		TTL="$TENTEN".ttl

		if [[ $TENTEN == "" ]]; then
		echo "Provide the path to the TenTen file, please."
		else
		if [ ! -e "$TTL" ]; then
		cat $TENTEN | \
		#    head -n 2500 | \
			./run.sh TenTen2XMLTSV -r | tee "$CONLL" | \
			./run.sh XMLTSV2RDF http://replace.me TOK LEM POS_COARSE POS MORPH HEAD_A HEAD_B LEM_ALT LEM_ALT2 > "$TTL"
		fi;

		## Count the number of XML nodes in the intermediate file.
		echo "Before conversion: $(awk -F'<' 'NF==2' "$CONLL" |\
		grep -c "/>\|<[^/]")"

		## Count the number of XML:data in the resulting file
		echo "After conversion: $(arq --data="$TTL" \
			--query=sparql/count_xml_triples.sparql \
			--results=TSV | grep -v '^?')"
		fi;
		*/
	}

	static class LinkUD {
		/*
		
# 1. read
for file in `find $DATA/ud | grep 'conllu.gz$'`; do \
	filename=$(basename "$file"); 
	tmp=UD_${file#*UD_}; 
	lang=${tmp%-master/*}; 
	TGT=$DATA/ud/ttl/$filename.linked.ttl;
	echo -n convert $file to $TGT 1>&2; 

	gunzip -c $file | \
# 2. parse UD data to RDF
	$ROOT/run.sh \
		CoNLLStreamExtractor \
		https://github.com/UniversalDependencies/$lang# \
		ID WORD LEMMA UPOS POS FEAT HEAD EDGE DEPS MISC | \
	$ROOT/run.sh CoNLLRDFUpdater \
	    -custom \
		-model http://purl.org/olia/owl/experimental/univ_dep/all_from_rdfa/ud-pos-all.owl http://purl.org/olia/ud-pos-all.owl \
		-model http://purl.org/olia/owl/experimental/univ_dep/all_from_rdfa/ud-pos-all-link.rdf http://purl.org/olia/ud-pos-all.owl \
		-updates $SPARQL/link/link-UPOS-simple.sparql \
		   \
	| \
# 3. format
	$ROOT/run.sh CoNLLRDFFormatter -rdf ID WORD LEMMA UPOS POS FEAT HEAD EDGE DEPS MISC $* > $TGT;
done
*/
	}

	static class ParseUD {
/*
# CoNLL-RDF EXAMPLE: rule-based, unlexicalized, cross-tagset parsing
# 
# illustrates complex graph rewriting using the English Universal Dependencies
# - strip original dependency annotation (column label IGNORE and remove-IGNORE.sparql)
# - link the Penn Treebank annotations to the Ontologies of Linguistic Annotation (OLiA)
#   (for linking other annotation schemes see http://purl.org/olia)
# - apply graph rewriting rules that emulate a shift-reduce parser
#   takes only sequence and POS concepts as information
#   note the OLiA concepts for POS are independent from the original string presentation
#   so, this is a cross-tagset unlexicalized parser
#
# note1: this requires an internet connection to work: the ontologies are directly loaded from the LLOD cloud
# 
# note2: This is a showcase built in half a day for the first few UD_en sentences. It is *known* 
#       to be far from perfect, but the point of this script is not coverage, but ease of development ;)
# known issues: appositions (s3), complex verbs (s6), genitive 's (s7), punctuation, etc.
# inherent limitations: this is a deterministic parser
#
# homework: improve coverage

HOME=`echo $0 | sed -e s/'^[^\/]*$'/'.'/g -e s/'\/[^\/]*$'//`;
ROOT=$HOME/..;
DATA=$ROOT/data;
SPARQL=$HOME/sparql;
*/ 

		// gunzip -c $DATA/ud/UD_English-master/en-ud-dev.conllu.gz | \
		// egrep -m 58 '^' | 										# until line 50, we're ok, increase this limit to analyze more data
		// egrep '^[0-9]+\s.*$|^$' |								# remove multiword tokens

		@Test
		void runCoNLLStreamExtractor() {
			// https://github.com/UniversalDependencies/UD_English# \
			// ID WORD LEMMA IGNORE POS IGNORE IGNORE IGNORE IGNORE IGNORE \
		}
		
		@Test
		void runCoNLLRDFUpdater() {
			String[] args = new String[] { "-custom",
					"-model http://purl.org/olia/penn.owl http://purl.org/olia/penn.owl",
					"-model http://purl.org/olia/penn-link.rdf http://purl.org/olia/penn.owl",
					"-model http://purl.org/olia/olia.owl http://purl.org/olia/olia.owl", "-updates",
					"$SPARQL/remove-ID.sparql", "$SPARQL/remove-IGNORE.sparql",

					"$SPARQL/link/link-penn-POS.sparql", "$SPARQL/link/remove-annotation-model.sparql",
					"$SPARQL/link/infer-olia-concepts.sparql",

					"$SPARQL/parse/initialize-SHIFT.sparql", "$SPARQL/parse/REDUCE-english-1.sparql{5}",
					"$SPARQL/parse/REDUCE-english-2.sparql{5}", "$SPARQL/parse/REDUCE-english-3.sparql{5}",
					"$SPARQL/parse/REDUCE-english-4.sparql{3}", "$SPARQL/parse/REDUCE-to-HEAD.sparql" };
		}

		void runCoNLLRDFFormatter() {
			// -grammar 2>&1 	### use -grammar for formatted output (this is made default here)
		}


		// TODO parse-ud.json
	}

	static class ParseUDplusGraphviz {
	}

	static class TreeExample {
	}

	static class XMLExample {
	}
}
